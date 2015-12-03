module ALU(
    data1_i    ,
    data2_i    ,
    ALUCtrl_i  ,
    data_o     
);
input[31:0]     data1_i;
input[31:0]     data2_i;
input[2:0]      ALUCtrl_i;
output[31:0]    data_o;
//data_o
wire[31:0] selections[7:0];
assign selections[3'b000] = data1_i + data2_i;
assign selections[3'b010] = data1_i - data2_i;
assign selections[3'b100] = data1_i & data2_i;
assign selections[3'b101] = data1_i | data2_i;
assign selections[3'b111] = data1_i * data2_i;
assign data_o = selections[ALUCtrl_i];
endmodule
