module ALU_Control(
  immediate_i,
  ALU_op_i,

  ALU_Ctrl_o
);
input[31:0]     immediate_i;
input[1:0]      ALU_op_i;

output[2:0]     ALU_Ctrl_o;

wire[5:0] funct_i;
assign funct_i = immediate_i[5:0];

wire[2:0] ALU_op_selections[2:0];
assign ALU_op_selections[2'b00] = 3'b000;
assign ALU_op_selections[2'b01] = 3'b010;
assign ALU_op_selections[2'b10] = funct_i[2:0];
assign ALU_Ctrl_o = ALU_op_selections[ALU_op_i];
endmodule
