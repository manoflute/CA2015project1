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
wire EXHarzard_rs, MEMHarzard_rs, EXHarzard_rt, MEMHarzard_rt;
assign EXHarzard_rs = EX_MEM_WB_i && 
  (EX_MEM_RegDst_i != 5'd0) && 
  (EX_MEM_RegDst_i == ID_EX_RSaddr_i) ;
assign EXHarzard_rt = EX_MEM_WB_i && 
  (EX_MEM_RegDst_i != 5'd0) && 
  (EX_MEM_RegDst_i == ID_EX_RTaddr_i) ;
assign MEMHarzard_rs = MEM_WB_RegWrite_i &&
  (MEM_WB_RegDst_i != 5'd0) &&
  (MEM_WB_RegDst_i != ID_EX_RSaddr_i);
assign MEMHarzard_rt = MEM_WB_RegWrite_i &&
  (MEM_WB_RegDst_i != 5'd0) &&
  (MEM_WB_RegDst_i != ID_EX_RTaddr_i);

assign select_ALU_data1_o = (EXHarzard_rs)? 2'b10: (MEMHarzard_rs)? 2'b01: 2'b00;
assign select_ALU_data2_o = (EXHarzard_rt)? 2'b10: (MEMHarzard_rt)? 2'b01: 2'b00;
endmodule
