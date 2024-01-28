#include "structSensor.h"
#include <stdio.h>
#include <stdint.h>
#include "ler.h"
#include "sensors.h"

void fillMatrix(Sensor **sensoresPerType, int *typeSizes, unsigned long * sensorF, unsigned char * types, int size, int bigger, unsigned short **matrix){
	
    int i, j, k, contador, aux;
    
    for (i = 0; i < size; i++){
		
		aux = -1;
		while(aux < 0){
			aux = ler();
		}
		matrix[i][0] = aux;
		
		
		switch (types[i]){
			case 0:
				matrix[i][0] = sens_temp(matrix[i][0], ler());
				break;
			case 1:
				matrix[i][0] = sens_velc_vento(matrix[i][0], ler());
				break;
			case 2:
				matrix[i][0] = sens_dir_vento(matrix[i][0], ler());
				break;
			case 3:
				contador = 0;
				while (types[i] != 5 && i != 0){
					i--;
					contador ++;
				}
				if (i == 0){
					matrix[contador][0] = sens_humd_atm(matrix[contador][0], matrix[contador][0], ler());
				}else {
					matrix[i + contador][0] = sens_humd_atm(matrix[i + contador][0], matrix[i][0], ler());
				}
				i += contador;
				break;
			case 4:
				contador = 0;
				while (types[i] != 5 && i != 0){
					i--;
					contador ++;
				}
				if (i == 0){
					matrix[contador][0] = sens_humd_solo(matrix[contador][0], matrix[contador][0], ler());
				}else {
					matrix[i + contador][0] = sens_humd_solo(matrix[i + contador][0], matrix[i][0], ler());
				}
				i += contador;
				break;
			case 5:
				contador = 0;
				while (types[i] != 0 && i != 0){
					i--;
					contador ++;
				}
				if (i == 0){
					matrix[contador][0] = sens_pluvio(matrix[contador][0], matrix[contador][0], ler());
				}else {
					matrix[i + contador][0] = sens_pluvio(matrix[i + contador][0], matrix[i][0], ler());
				}
				i += contador;
				break;
			default:
				printf("Invalid Type");
		}
	}
    
    
	for (i = 0; i < size; i++){
		for (j = 1; j < bigger; j ++){
			if (sensorF[i] > j){
				switch (types[i]){
					case 0:
						matrix[i][j] = sens_temp(matrix[i][j-1], ler());
						break;
					case 1:
						matrix[i][j] = sens_velc_vento(matrix[i][j-1], ler());
						break;
					case 2:
						matrix[i][j] = sens_dir_vento(matrix[i][j-1], ler());
						break;
					case 3:
						contador = 0;
						while (types[i] != 5 && i != 0){
							i--;
							contador ++;
						}
						if (i == 0){
							matrix[contador][j] = sens_humd_atm(matrix[contador][j-1], matrix[contador][j-1], ler());
						}else {
							if (sensorF[i] > j){
								matrix[i + contador][j] = sens_humd_atm(matrix[i + contador][j-1], matrix[i][j-1], ler());
							}else{
								matrix[i + contador][j] = sens_humd_atm(matrix[i + contador][j-1], matrix[i][sensorF[i] - 1], ler());
							}
						}
						i += contador;
						break;
					case 4:
					
						contador = 0;
						while (types[i] != 5 && i != 0){
							i--;
							contador ++;
						}
						if (i == 0){
							matrix[contador][j] = sens_humd_solo(matrix[contador][j-1], matrix[contador][j-1], ler());
						}else {
							if (sensorF[i] > j){
								matrix[i + contador][j] = sens_humd_solo(matrix[i + contador][j-1], matrix[i][j-1], ler());
							}else{
								matrix[i + contador][j] = sens_humd_solo(matrix[i + contador][j-1], matrix[i][sensorF[i] - 1], ler());
							}
						}
						i += contador;
						break;
						
					case 5:
					
						contador = 0;
						while (types[i] != 0 && i != 0){
							i--;
							contador ++;
						}
						if (i == 0){
							matrix[contador][j] = sens_pluvio(matrix[contador][j-1], matrix[contador][j-1], ler());
						}else {
							if (sensorF[i] > j){
								matrix[i + contador][j] = sens_pluvio(matrix[i + contador][j-1], matrix[i][j-1], ler());
							}else{
								matrix[i + contador][j] = sens_pluvio(matrix[i + contador][j-1], matrix[i][sensorF[i] - 1], ler());
							}
						}
						i += contador;
						break;
						
					default:
						printf("Invalid Type");
				}
			}
		}
    }
    
	for (i = 0; i < 6; i ++){
		for (j = 0; j < typeSizes[i]; j ++){
			contador = 0;
			for (k = 0; k < size; k ++){
				if (types[k] == i){
					sensoresPerType[i][contador].readings = matrix[k];
					contador ++;
				}
			}
		}
	}

}

void sizeMatrix(unsigned long * sensorF, int size, int *sizeF){
	int i;
    int bigger = sensorF[0];
    for (i = 1; i < size; i++){
        if (bigger < sensorF[i]){
            bigger = sensorF[i];
        }
    }
    *sizeF = bigger;
}
