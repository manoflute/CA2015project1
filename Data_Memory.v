module Data_Memory(
    clk_i,
    MemWrite_i,
    MemRead_i,      //TODO: unused
    addr_i,
    write_data_i,

    data_o
);

input       clk_i, MemWrite_i, MemRead_i;
input[31:0] addr_i, write_data_i;

output[31:0] data_o;

// Memory File
reg     [31:0]      memory[0:31];
// Read Data
assign  data_o = memory[addr_i];
// Write Data   
always@(posedge clk_i) begin
    if(MemWrite_i)
        memory[addr_i] <= write_data_i;
end

endmodule
