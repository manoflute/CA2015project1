module Control(
    Op_i       ,
    ID_EX_o,
    PC_i_mux_o,
    branch_o
);
input[5:0]      Op_i;
output reg[7:0] ID_EX_o;
output          PC_i_mux_o;
output          branch_o;

wire[7:0] lwInfo, swInfo, beqInfo, rTypeInfo;
//RegWrite, MemtoReg | MemRead, MemWrite | ALUSrc, ALUOp[1:0], RegDst
/*
assign rTypeInfo = 8'b10000101;     //rtype000000
assign lwInfo    = 8'b11101000;        //lw100011
assign swInfo    = 8'b0X01100X;        //sw101011
assign beqInfo   = 8'b0X00001X;       //beq000100
*/
assign rTypeInfo = 8'b10000101;     //rtype000000
assign lwInfo    = 8'b11101000;        //lw100011
assign swInfo    = 8'b00011000;        //sw101011
assign beqInfo   = 8'b00000010;       //beq000100
/*assign ID_EX_o = (6'b100011==Op_i)? lwInfo:
                 (6'b101011)? swInfo:
                 (6'b000100)? beqInfo:
                 (6'b000000)? rTypeInfo:
                 8'bXXXXXXXX;*/

always @(*) begin
    case (Op_i)
        6'b000000: ID_EX_o = rTypeInfo;
        6'b100011: ID_EX_o = lwInfo;  //35
        6'b101011: ID_EX_o = swInfo;  //43
        6'b000100: ID_EX_o = beqInfo; //4
        default :  ID_EX_o = 0;
    endcase
end
assign branch_o   = (6'b000100==Op_i);//if(beq) branch_o = 1
assign PC_i_mux_o = (6'b000010==Op_i);//if(jType) PC_i_mux_o = 1
endmodule
