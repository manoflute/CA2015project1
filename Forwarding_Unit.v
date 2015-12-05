module Forwarding_Unit(
  ID_EX_RSaddr_i, 
  ID_EX_RTaddr_i,
  EX_MEM_RegDst_i,
  EX_MEM_WB_i,
  MEM_WB_RegDst_i,
  MEM_WB_RegWrite_i,

  select_ALU_data1_o,
  select_ALU_data2_o
);
input[4:0]  ID_EX_RSaddr_i, ID_EX_RTaddr_i, EX_MEM_RegDst_i, MEM_WB_RegDst_i;
input       MEM_WB_RegWrite_i;
input[1:0]  EX_MEM_WB_i;

output[1:0] select_ALU_data1_o, select_ALU_data2_o;
wire EXHazard_rs, MEMHazard_rs, EXHazard_rt, MEMHazard_rt;
assign EXHazard_rs  = EX_MEM_WB_i[1] && 
                      (EX_MEM_RegDst_i != 5'd0) && 
                      (EX_MEM_RegDst_i == ID_EX_RSaddr_i) ;
assign EXHazard_rt  = EX_MEM_WB_i[1] && 
                      (EX_MEM_RegDst_i != 5'd0) && 
                      (EX_MEM_RegDst_i == ID_EX_RTaddr_i) ;
assign MEMHazard_rs = MEM_WB_RegWrite_i &&
                      (MEM_WB_RegDst_i != 5'd0) &&
                      (MEM_WB_RegDst_i == ID_EX_RSaddr_i);
assign MEMHazard_rt = MEM_WB_RegWrite_i &&
                      (MEM_WB_RegDst_i != 5'd0) &&
                      (MEM_WB_RegDst_i == ID_EX_RTaddr_i);

assign select_ALU_data1_o = (EXHazard_rs)? 2'b10: (MEMHazard_rs)? 2'b01: 2'b00;
assign select_ALU_data2_o = (EXHazard_rt)? 2'b10: (MEMHazard_rt)? 2'b01: 2'b00;
endmodule
