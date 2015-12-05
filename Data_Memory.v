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
reg     [7:0]      memory[0:31];
// Read Data
assign  data_o = (MemRead_i) ? {memory[addr_i], memory[addr_i+1], memory[addr_i+2], memory[addr_i+3]} : 0;
// Write Data   
always@(posedge clk_i) begin
    if(MemWrite_i)
        {memory[addr_i], memory[addr_i+1], memory[addr_i+2], memory[addr_i+3]} <= write_data_i;
end

endmodule
