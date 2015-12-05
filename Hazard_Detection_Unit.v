module Hazard_Detection_Unit(
    instruction_i, //TODO: ? bits
    ID_EX_RTaddr_i,
    ID_EX_M_i,

    stall_o
    //PC_o,
    //IF_ID_o,
    //Control_mux_o
);
input[31:0]     instruction_i;
input[4:0]      ID_EX_RTaddr_i;
input[1:0]      ID_EX_M_i;

//output          PC_o;
//output          IF_ID_o;
//output          Control_mux_o;
output stall_o;
/*
if (ID/EX.MemRead and
((ID/EX.RegisterRt = IF/ID.RegisterRs) or
(ID/EX.RegisterRt = IF/ID.RegisterRt)))
stall the pipeline
*/
//wire stall, ID_EX_MemRead;
//wire ID_EX_MemRead;
//assign ID_EX_MemRead = ID_EX_M_i[1];
assign stall_o = ID_EX_M_i[1] && 
  (
    (ID_EX_RTaddr_i == instruction_i[25:21]) ||
    (ID_EX_RTaddr_i == instruction_i[20:16])
  );

//assign PC_o = stall;
//assign IF_ID_o = stall;      // 1 means stalled
//assign Control_mux_o = stall;//(stall)? 1: 0;
endmodule
