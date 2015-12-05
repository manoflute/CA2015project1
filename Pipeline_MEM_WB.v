module Pipeline_MEM_WB(
    clk_i,
    WB_i,
    data_i,
    addr_i,
    RegDst_i,

    RegWrite_o,
    MemtoReg_o,
    data_o,
    addr_o,
    RegDst_o
);
input           clk_i;
input[1:0]      WB_i;
input[31:0]     data_i, addr_i;
input[4:0]      RegDst_i;

output reg          RegWrite_o, MemtoReg_o;
output reg[31:0]    data_o, addr_o;
output reg[4:0]     RegDst_o;
always @(posedge clk_i) begin
  RegWrite_o <= WB_i[1];
  MemtoReg_o <= WB_i[0];
  data_o <= data_i;
  addr_o <= addr_i;
  RegDst_o <= RegDst_i;
end
endmodule
