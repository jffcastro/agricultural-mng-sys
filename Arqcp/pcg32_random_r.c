long state=0;  
long inc=0;
int pcg32_random_r()
{
    long oldstate = state;
    // Advance internal state
    state = oldstate * 6364136223846793005ULL + (inc|1);
    // Calculate output function (XSH RR), uses old state for max ILP
    int xorshifted = ((oldstate >> 18u) ^ oldstate) >> 27u;
    int rot = oldstate >> 59u;
    return (xorshifted >> rot) | (xorshifted << ((-rot) & 31));
}
