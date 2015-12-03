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
endmodule
