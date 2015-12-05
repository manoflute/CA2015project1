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
initial begin 
    pc_add4_o = 0;
    instruction_o = 0;
end
always @(posedge clk_i) begin
  if (!hazard_IF_ID_i) begin//if no stall
    pc_add4_o <= pc_add4_i;
    
    if (flush_i)
        instruction_o <= 32'd0;
    else
        instruction_o <= instruction_i;
  end
end
endmodule
