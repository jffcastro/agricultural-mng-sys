#include <stdio.h>
#include <stdlib.h>

int **new_matrix(int lines, int columns){
	
	int **ptr = NULL, i;
	ptr = (int **) malloc(lines * sizeof(int*));
	for (i = 0; i < lines; i ++){
		*(ptr + i) = (int *) malloc(columns * sizeof(int));
	}
	return ptr;
	
}
