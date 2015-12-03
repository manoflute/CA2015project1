module Pipeline_ID_EX(
    clk_i,
    pipeline_info_i,
    pc_add4_i,
    RSdata_i,
    RTdata_i,
    immediate_i,
    RSaddr_i,
    RTaddr_i,
    RDaddr_i,
    
    WB_o,
    M_o,
    ALUSrc_o,
    ALU_op_o,
    RegDst_o,
    //ID_EX_o
    RSdata_o,
    RTdata_o,
    immediate_o,
    RSdata_forward_o,
    RTdata_forward_o,
    RegDst_data1_o,
    RegDst_data2_o
);
input           clk_i;
input[7:0]      pipeline_info_i;
input[31:0]     pc_add4_i, RSdata_i, RTdata_i, immediate_i;
input[4:0]      RSaddr_i, RTaddr_i, RDaddr_i;

output reg[1:0]     WB_o, M_o;
output reg          ALUSrc_o, RegDst_o;
output reg[1:0]     ALU_op_o;
output reg[31:0]    RSdata_o, RTdata_o, immediate_o;
output reg[4:0]     RSdata_forward_o, RTdata_forward_o, RegDst_data1_o, RegDst_data2_o;

wire[3:0]       EX;
always @(posedge clk_i) begin
  WB_o <= pipeline_info_i[7:6];
  M_o <= pipeline_info_i[5:4];
  ALUSrc_o <= pipeline_info_i[3];
  ALU_op_o <= pipeline_info_i[2:1];
  RegDst_o <= pipeline_info_i[0];

  RSdata_o <= RSdata_i;
  RTdata_o <= RTdata_i;
  immediate_o <= immediate_i;
  RSdata_forward_o <= RSaddr_i;
  RTdata_forward_o <= RTaddr_i;
  RegDst_data1_o <= RTaddr_i;
  RegDst_data2_o <= RDaddr_i;
end
endmodule
