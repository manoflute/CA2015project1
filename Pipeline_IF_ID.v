module Pipeline_IF_ID(
    clk_i,
    hazard_IF_ID_i,
    pc_add4_i,
    instruction_i,
    flush_i,
    pc_add4_o,
    instruction_o
);
input           clk_i;
input           hazard_IF_ID_i;
input[31:0]     pc_add4_i;
input[31:0]     instruction_i;
input           flush_i;

output reg[31:0]    pc_add4_o;
output reg[31:0]    instruction_o;

always @(posedge clk_i) begin
  pc_add4_o <= pc_add4_i;
  instruction_o <= instruction_i;
end
endmodule
