`include "PC.v"                     //done
`include "Control.v"                //done
`include "Hazard_Detection_Unit.v"  //done
`include "Instruction_Memory.v"     //done
`include "Pipeline_IF_ID.v"         //done TODO:flush_i unused  
`include "Adder32.v"                //done
`include "Registers.v"              //done TODO:posedge?
`include "Sign_Extend_16to32.v"     //done 
`include "Pipeline_ID_EX.v"         //done 
`include "ALU.v"                    //done 
`include "ALU_Control.v"            //done  
`include "Forwarding_Unit.v"        //done 
`include "Pipeline_EX_MEM.v"        //done
`include "Data_Memory.v"            //done TODO:MemRead unused
`include "Pipeline_MEM_WB.v"        //done 

module CPU
(
    clk_i, 
    rst_i,
    start_i
);

input       clk_i;
input       rst_i;
input       start_i;

wire[31:0] PC_pc_o, instruction, Add_pc_o;

wire[7:0] Control_ID_EX_o;
wire Control_PC_i_mux_o;
wire Control_branch_o;
Control Control(
    .Op_i(instruction[31:26]),
    .ID_EX_o(Control_ID_EX_o),
    .PC_i_mux_o(Control_PC_i_mux_o),
    .branch_o(Control_branch_o)
);

wire Hazard_Detection_Unit_PC_o, Hazard_Detection_Unit_IF_ID_o, Hazard_Detection_Unit_Control_mux_o;
Hazard_Detection_Unit Hazard_Detection_Unit(
    .instruction_i(instruction),
    .ID_EX_RTaddr_i(ID_EX_RegDst_data1_o),
    .ID_EX_M_i(ID_EX_M_o),
    .PC_o(Hazard_Detection_Unit_PC_o),
    .IF_ID_o(Hazard_Detection_Unit_IF_ID_o),
    .Control_mux_o(Hazard_Detection_Unit_Control_mux_o)
);

wire select_add_pc;
assign select_add_pc = (Registers_eq && Control_branch_o);
wire [31:0] pc_add4_selected, pc_jump;
assign pc_add4_selected = (select_add_pc)? Add_pc_o:ADD_o;//TODO: swap position?
assign pc_jump = {pc_add4_selected[31:28], instruction[25:0]<<2};

PC PC(
    .clk_i(clk_i),
    .rst_i(rst_i),
    .start_i(start_i),
    .hazard_pc_i(Hazard_Detection_Unit_PC_o),
    .pc_i((Control_PC_i_mux_o)? pc_jump:pc_add4_selected),
    .pc_o(PC_pc_o)
);

Adder32 Add_pc(
    .data1_i(PC_pc_o),
    .data2_i(32'd4),
    .data_o(Add_pc_o)
);

wire[31:0] Instruction_Memory_instr_o;
Instruction_Memory Instruction_Memory(
    .addr_i(PC_pc_o), 
    .instr_o(Instruction_Memory_instr_o)
);

wire[31:0] IF_ID_pc_add4_o;
Pipeline_IF_ID Pipeline_IF_ID(
    .clk_i(clk_i),
    .hazard_IF_ID_i(Hazard_Detection_Unit_IF_ID_o),
    .pc_add4_i(Add_pc_o),
    .instruction_i(Instruction_Memory_instr_o),
    .flush_i(Control_PC_i_mux_o || select_add_pc),
    .pc_add4_o(IF_ID_pc_add4_o),
    .instruction_o(instruction)
);

wire[31:0] ADD_o;
Adder32 ADD(
    .data1_i(IF_ID_pc_add4_o),
    .data2_i(ext_o<<<2),//TODO: logical? arithmetic?
    .data_o(ADD_o)
);

wire[31:0] Registers_RSdata_o, Registers_RTdata_o;
Registers Registers
(
    .clk_i(clk_i),
    .RSaddr_i(instruction[25:21]),
    .RTaddr_i(instruction[20:16]),
    .RDaddr_i(MEM_WB_RegDst_o), 
    .RDdata_i(RDdata_selected),
    .RegWrite_i(MEM_WB_RegWrite_o), 
    .RSdata_o(Registers_RSdata_o), 
    .RTdata_o(Registers_RTdata_o)
);
wire Registers_eq;
assign Registers_eq = (Registers_RSdata_o == Registers_RTdata_o);

wire[31:0] ext_o;
Sign_Extend_16to32 Sign_Extend_16to32(
    .data_i(instruction[15:0]),
    .data_o(ext_o)    
);

wire[1:0]   ID_EX_WB_o, ID_EX_M_o;
wire        ID_EX_ALUSrc_o, ID_EX_RegDst_o;
wire[1:0]   ID_EX_ALU_op_o;
wire[31:0]  ID_EX_RSdata_o, ID_EX_RTdata_o,  ID_EX_immediate_o;
wire[4:0]   ID_EX_RSdata_forward_o, ID_EX_RTdata_forward_o, ID_EX_RegDst_data1_o, ID_EX_RegDst_data2_o;
Pipeline_ID_EX Pipeline_ID_EX(
    .clk_i(clk_i),
    .pipeline_info_i((Hazard_Detection_Unit_Control_mux_o)?8'b0:Control_ID_EX_o),
    .pc_add4_i(ADD_o),
    .RSdata_i(Registers_RSdata_o),
    .RTdata_i(Registers_RTdata_o),
    .immediate_i(ext_o),
    .RSaddr_i(instruction[25:21]),
    .RTaddr_i(instruction[20:16]),
    .RDaddr_i(instruction[15:11]),

    .WB_o(ID_EX_WB_o),
    .M_o(ID_EX_M_o),
    .ALUSrc_o(ID_EX_ALUSrc_o),
    .ALU_op_o(ID_EX_ALU_op_o),
    .RegDst_o(ID_EX_RegDst_o),
    .RSdata_o(ID_EX_RSdata_o),
    .RTdata_o(ID_EX_RTdata_o),
    .immediate_o(ID_EX_immediate_o),
    .RSdata_forward_o(ID_EX_RSdata_forward_o),
    .RTdata_forward_o(ID_EX_RTdata_forward_o),
    .RegDst_data1_o(ID_EX_RegDst_data1_o),          //TODO:naming
    .RegDst_data2_o(ID_EX_RegDst_data2_o)
);
wire [1:0] FW_select_ALU_data1_o, FW_select_ALU_data2_o;
Forwarding_Unit Fowarding_Unit(
   .ID_EX_RSaddr_i(ID_EX_RSdata_forward_o), 
   .ID_EX_RTaddr_i(ID_EX_RTdata_forward_o),
   .EX_MEM_RegDst_i(EX_MEM_RegDst_o),
   .EX_MEM_WB_i(EX_MEM_WB_o),
   .MEM_WB_RegDst_i(MEM_WB_RegDst_o),
   .MEM_WB_RegWrite_i(MEM_WB_RegWrite_o),

   .select_ALU_data1_o(FW_select_ALU_data1_o),
   .select_ALU_data2_o(FW_select_ALU_data2_o)
);
wire[31:0] ALU_data1_i, ALUSrc_data, ALU_data_o;
assign ALU_data1_i = (FW_select_ALU_data1_o[1])? EX_MEM_addr_o:
                     (FW_select_ALU_data1_o[0])? RDdata_selected: 
                                                 ID_EX_RSdata_o;
assign ALUSrc_data = (FW_select_ALU_data2_o[1])? EX_MEM_addr_o:
                     (FW_select_ALU_data2_o[0])? RDdata_selected: 
                                                 ID_EX_RTdata_o;
ALU ALU(
    .data1_i(ALU_data1_i),
    .data2_i((ID_EX_ALUSrc_o)? ID_EX_immediate_o: ALUSrc_data),
    .ALUCtrl_i(ALU_Control_ALU_Ctrl_o),
    .data_o(ALU_data_o)     
);
wire[2:0] ALU_Control_ALU_Ctrl_o;
ALU_Control ALU_Control(
  .immediate_i(ID_EX_immediate_o),
  .ALU_op_i(ID_EX_ALU_op_o),
  .ALU_Ctrl_o(ALU_Control_ALU_Ctrl_o)
);

wire[1:0]     EX_MEM_WB_o;
wire          EX_MEM_MemRead_o, EX_MEM_MemWrite_o;
wire[31:0]    EX_MEM_addr_o, EX_MEM_write_data_o;
wire[4:0]     EX_MEM_RegDst_o;
Pipeline_EX_MEM Pipeline_EX_MEM(
    .clk_i(clk_i),
    .WB_i(ID_EX_WB_o),
    .M_i(ID_EX_M_o),
    .ALU_data_i(ALU_data_o),
    .ALUSrc_data_i(ALUSrc_data),
    .RegDst_i((ID_EX_RegDst_o)?ID_EX_RegDst_data2_o:ID_EX_RegDst_data1_o),

    .WB_o(EX_MEM_WB_o),
    .MemRead_o(EX_MEM_MemRead_o),
    .MemWrite_o(EX_MEM_MemWrite_o),
    .addr_o(EX_MEM_addr_o),
    .write_data_o(EX_MEM_write_data_o),
    .RegDst_o(EX_MEM_RegDst_o)
);
wire[31:0] Data_Memory_data_o;
Data_Memory Data_Memory(
    .clk_i(clk_i),
    .MemWrite_i(EX_MEM_MemWrite_o),
    .MemRead_i(EX_MEM_MemRead_o),
    .addr_i(EX_MEM_addr_o),
    .write_data_i(EX_MEM_write_data_o),

    .data_o(Data_Memory_data_o)
);
wire        MEM_WB_RegWrite_o, MEM_WB_MemtoReg_o;
wire[4:0]   MEM_WB_RegDst_o;
wire[31:0]  MEM_WB_data_o, MEM_WB_addr_o;
Pipeline_MEM_WB Pipeline_MEM_WB(
    .clk_i(clk_i),
    .WB_i(EX_MEM_WB_o),
    .data_i(Data_Memory_data_o),
    .addr_i(EX_MEM_addr_o),
    .RegDst_i(EX_MEM_RegDst_o),

    .RegWrite_o(MEM_WB_RegWrite_o),
    .MemtoReg_o(MEM_WB_MemtoReg_o),
    .data_o(MEM_WB_data_o),
    .addr_o(MEM_WB_addr_o),
    .RegDst_o(MEM_WB_RegDst_o)
);
wire[31:0] RDdata_selected;
assign RDdata_selected = (MEM_WB_MemtoReg_o)? MEM_WB_addr_o:MEM_WB_data_o;

endmodule
