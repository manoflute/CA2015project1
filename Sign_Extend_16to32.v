
module Sign_Extend_16to32(
    data_i     ,
    data_o     
);
input[15:0] data_i;
output[31:0] data_o;

assign data_o = (data_i[15])? {-16'b1, data_i}: {16'b0, data_i};
endmodule
