#include <stdio.h>
#include <stdint.h>
#include "pcg32_random_r.h"
extern long state;

int ler() {
    uint32_t buffer [64]; 
    FILE *f;
    int result;
    int i;
    int random;
    f = fopen("/dev/urandom", "r"); 
    if (f == NULL) {
        printf("Error: open() failed to open /dev/random for reading\n"); 
        return 1;
    }
    result = fread(buffer , sizeof(uint32_t), 64,f);
    if (result < 1) {
        printf("error , failed to read and words\n"); 
        return 1;
    } 
    state = buffer[0];
    for(i=0;i<result;i++){
		random = pcg32_random_r();
    }
    
    return random;
}
