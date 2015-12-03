module Hazard_Detection_Unit(
    instruction_i, //TODO: ? bits
    ID_EX_RTaddr_i,
    ID_EX_M_i,

    PC_o,
    IF_ID_o,
    Control_mux_o
);
input[31:0]     instruction_i;
input[4:0]      ID_EX_RTaddr_i;
input[1:0]      ID_EX_M_i;

output          PC_o;
output          IF_ID_o;
output          Control_mux_o;
endmodule
