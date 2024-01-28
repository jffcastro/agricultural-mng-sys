#include "structSensor.h"
#include <stdio.h>
#include <stddef.h>

void writeOnCSV1 (Sensor **arraySensores, int *size){
	int x,y,z;
	FILE *file;
	file = fopen("SensorData.csv", "w");
	
	if(file == NULL) {
		printf("Não foi possível criar o ficheiro!");
	}
	
	fprintf(file,"Id,Sensor Type,Max Limit,Min Limit,Frequency, Frequency Per Second\n");
	
	for(x = 0 ; x < 6 ; x++) {
		for(y = 0 ; y < size[x] ; y++) {
			fprintf(file,"%hd,%hhd,%hd,%hd,%ld,%ld\n",arraySensores[x][y].id,arraySensores[x][y].sensorType,arraySensores[x][y].maxLimit,arraySensores[x][y].minLimit,arraySensores[x][y].frequency,arraySensores[x][y].frequencyPerSecond);
			fprintf(file,"Readings\n");
			for(z = 0 ; z < arraySensores[x][y].frequency ; z++){
				if(z == arraySensores[x][y].frequency - 1) {
					fprintf(file,"%hd",arraySensores[x][y].readings[z]);
				} else {
					fprintf(file,"%hd,",arraySensores[x][y].readings[z]);
				}
			}
			fprintf(file,"\n");
		}
	}	
}

void writeOnCSV2 (float arrayDailyResume[][3], int nSensor){
	int x;
	FILE *file;
	file = fopen("DailyResume.csv", "w");
	
	if(file == NULL){
		printf("Não foi possível criar o ficheiro!");
	
	}
	fprintf(file,"Max value,Min value,Median\n");
	
	for(x = 0; x < nSensor; x++){
		fprintf(file,"%.2f,%.2f,%.2f\n",arrayDailyResume[x][0], arrayDailyResume[x][1], arrayDailyResume[x][2]);
	}
}
