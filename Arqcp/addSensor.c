#include "structSensor.h"
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

void addSensor (Sensor **sensores, int *size, unsigned long **numValues, unsigned char **sensorTypes, Sensor **sensoresPerType, int *typeSizes){
	
	Sensor sensor;
	unsigned short idSensor;
    unsigned char sensorType;
    unsigned short maximumLimit, minimumLimit;
    unsigned long frequency; 
	
	*size += 1;
	
	printf("Id do Sensor (Número): ");
	scanf("%hd", &idSensor);
	
	while (1){
		printf("Tipo do Sensor (Número entre 0 e 5): ");
		scanf("%hhd", &sensorType);
		if (sensorType >= 0 && sensorType <= 5){
			break;
		}else {
			printf("Tipo de sensor inválido!!\n");
		}
	}
	
	while (1){
		printf("Limite Máximo para o sensor: ");
		scanf("%hd", &maximumLimit);
		printf("Limite Mínimo para o sensor: ");
		scanf("%hd", &minimumLimit);
		if (maximumLimit >= minimumLimit){
			break;
		}else {
			printf("O limite maximo tem de ser superior ao limite minimo!!\n");
		}
	}
	printf("Frequência do sensor: ");
	scanf("%ld", &frequency);
	printf("\n\n");
	if (frequency < 1){
		frequency = 1;
	}
	
	sensor.id = idSensor;
	sensor.sensorType = sensorType;
	sensor.maxLimit = maximumLimit;
	sensor.minLimit = minimumLimit;
	sensor.frequency = frequency;
	sensor.frequencyPerSecond = frequency / 3600;
	
	unsigned long *aux = (unsigned long *) realloc(*numValues, *size * sizeof(unsigned long));
	if (aux != NULL){
		*numValues = aux;
	}
	*(*numValues + *size - 1) = frequency;
	
	unsigned char *aux2 = (unsigned char *) realloc(*sensorTypes, *size * sizeof(unsigned char));
	if (aux2 != NULL){
		*sensorTypes = aux2;
	}
	*(*sensorTypes + *size - 1) = sensorType;
	
	Sensor *aux3 = (Sensor *) realloc(*sensores, *size * sizeof(Sensor));
	if (aux3 != NULL){
		*sensores = aux3;
	}
	*(*sensores + *size - 1) = sensor;
	
	
	*(typeSizes + sensorType) += 1;
	Sensor *aux4 = (Sensor *) realloc(*(sensoresPerType + sensorType), *(typeSizes + sensorType) * sizeof(Sensor));
	if (aux4 != NULL){
		*(sensoresPerType + sensorType) = aux4;
	}
	*(*(sensoresPerType + sensorType) + *(typeSizes + sensorType) - 1) = sensor;
	
}
