module Pipeline_EX_MEM(
    clk_i,
    WB_i,
    M_i,
    ALU_data_i,
    ALUSrc_data_i,
    RegDst_i,

    WB_o,
    MemRead_o,
    MemWrite_o,
    addr_o,
    write_data_o,
    RegDst_o
);
input           clk_i;
input[1:0]      WB_i, M_i;
input[31:0]     ALU_data_i;
input[31:0]     ALUSrc_data_i;
input[4:0]      RegDst_i;

output reg[1:0]   WB_o;
output reg        MemRead_o, MemWrite_o;
output reg[31:0]  addr_o, write_data_o;
output reg[4:0]   RegDst_o;

always @(posedge clk_i) begin
  WB_o <= WB_i;
  MemRead_o <= M_i[1];
  MemWrite_o <= M_i[0];
  addr_o <= ALU_data_i;
  write_data_o <= ALUSrc_data_i;
  RegDst_o <= RegDst_i;
end 
endmodule
