#include "structSensor.h"
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include "dailyResume.h"
#include "sensors.h"
#include "fillMatrix.h"
#include "limits.h"
#include "new_matrix.h"
#include "addSensor.h"
#include "writeOnCSV.h"
#include "sensor.h"

int main(int argc, char **argv)
{
	printf("\nTipos De Sensor\n\n0 -> Sensor da Temperatura\n\n1 -> Sensor da Velocidade do Vento\n\n2 -> Sensor da Direção do Vento\n\n3 -> Sensor da Humidade Atmosférica\n\n4 -> Sensor da Humidade do Solo\n\n5 -> Sensor da Pluviosidade\n\n\n\n");
    
	int i, j, k;
	int sizeF = 0;
    int numSensors = 0; // Number of sensors
    int totalInicialSensores = 0;
    int totalTypes = 6;

	Sensor *sensores = (Sensor *) malloc(numSensors * sizeof(Sensor));
	int typeSizes[] = {0, 0, 0, 0, 0, 0}; 
	Sensor **sensoresPerType = (Sensor **) malloc(totalTypes * sizeof(Sensor));
	for(int i = 0 ; i < totalTypes ; i++) {
		*(sensoresPerType + i) = (Sensor *) malloc(typeSizes[i] * sizeof(Sensor));			
	}
    unsigned long *numValues = (unsigned long *) malloc(numSensors * sizeof(unsigned long));
    unsigned char *sensorTypes = (unsigned char *) malloc(numSensors * sizeof(unsigned char));
    int wrongReadLimit;
	
	printf("Número de Sensores que pretende inserir: ");
    scanf("%d", &totalInicialSensores);
    printf("\n");
    
    for (i = 0; i < totalInicialSensores; i++){
		addSensor(&sensores, &numSensors, &numValues, &sensorTypes, sensoresPerType, typeSizes);
	}

	//US111
    unsigned short idOfSensorToRemove;
    unsigned short idOfSensorToChange;
    char choice;
    long newF;
    
    while (1){
		
		printf("Pretende remover ou adicionar algum sensor do programa? Insira 3 para alterar a frequencia de um sensor,  2 para adicionar um novo sensor, 1 para remover um sensor e 0 para nenhuma das opcoes: ");
		scanf("%hhd", &choice);
		printf("\n");
    
		if(choice == 1){
			printf("Id do sensor que pretende remover: ");
			scanf("%hd", &idOfSensorToRemove);
			remove_sensors(&sensores, idOfSensorToRemove, &numSensors, &numValues, &sensorTypes, sensoresPerType, typeSizes);

			printf("Novo vetor de sensores:\n");
			for(i=0; i<numSensors; i++){
				printf("Sensor: %d\n", sensores[i].id);
			}
		}else if (choice == 2){
			printf("Novo Sensor:\n");
			addSensor(&sensores, &numSensors, &numValues, &sensorTypes, sensoresPerType, typeSizes);
			
			printf("Novo vetor de sensores:\n");
			for(i=0; i<numSensors; i++){
				printf("Sensor: %d\n", sensores[i].id);
			}
			
		}else if (choice == 3){
			printf("Id do sensor que pretende alterar: ");
			scanf("%hd", &idOfSensorToChange);
			printf("Nova Frequência:");
			scanf("%ld", &newF);
			printf("\n");
			chageFrequency(&sensores, idOfSensorToChange, &numSensors, &numValues, &sensorTypes, sensoresPerType, typeSizes, newF);
			
			
		}else if (choice == 0){
			break;
		}else {
			printf("Escolha Errada!!!\n");
		}
		printf("\n");
	}
    


	printf("\nLimite Máximo de leituras erradas: ");
    scanf("%d", &wrongReadLimit);
    printf("\n");
    if (wrongReadLimit < 1){
		wrongReadLimit = 1;
	}

    
    sizeMatrix(numValues, numSensors, &sizeF);

	unsigned short **valuesMatrix = (unsigned short **) malloc(numSensors * sizeof(unsigned short*));
	for(int x = 0 ; x < numSensors ; x++) {
		*(valuesMatrix+x) = (unsigned short *) malloc(sizeF * sizeof(unsigned short));				
	}
	
    printf("\nValores Originais\n\n");
    fillMatrix(sensoresPerType, typeSizes, numValues, sensorTypes, numSensors, sizeF, valuesMatrix);
    for (k = 0; k < totalTypes; k ++){
		if (typeSizes[k] > 0){
			printf("Tipo:%hhd\n", sensoresPerType[k][0].sensorType);
		}
		for (i = 0; i < typeSizes[k]; i ++){
			printf("Sensor:%hd\n", sensoresPerType[k][i].id);
			for (j = 0; j < sensoresPerType[k][i].frequency; j++){
				printf("%hd ", sensoresPerType[k][i].readings[j]);
			}
			printf("\n");
		}
		printf("\n");
	}

    // START US104 -> Limits
    
    // END US104 -> Limits

	
    printf("\n\n");
    printf("Valores Corrigidos\n\n");
    
    for (k = 0; k < totalTypes; k ++){
		for (i = 0; i < typeSizes[k]; i ++){
			limits(sensoresPerType[k][i].maxLimit, sensoresPerType[k][i].minLimit, wrongReadLimit, k, i, sensoresPerType);
		}
	}
    
	
	
    for (k = 0; k < totalTypes; k ++){
		if (typeSizes[k] > 0){
			printf("Tipo:%hhd\n", sensoresPerType[k][0].sensorType);
		}
		for (i = 0; i < typeSizes[k]; i ++){
			printf("Sensor:%hd\n", sensoresPerType[k][i].id);
			for (j = 0; j < sensoresPerType[k][i].frequency; j++){
				printf("%hd ", sensoresPerType[k][i].readings[j]);
			}
			printf("\n");
		}
		printf("\n");
	}


    // START US103 -> Daily Resume
    float resumeMatrix[numSensors][3];  // Empty Matrix to fill in dailyResume()

    dailyResume(numSensors, sizeF, numValues, resumeMatrix, sensoresPerType, typeSizes);
    // END US103 -> Daily Resume


    printf("\n\n\n");
    printf("Resumo Diário\n\n");
    printf("%10sMáximo  Mínimo  Média\n\n", "");
    for (i = 0; i < 6; i ++){
		printf("Tipo %d: ", i);
		if (typeSizes[i] > 0){
			for (j = 0; j < 3; j++){
				printf("%7.2f ", resumeMatrix[i][j]);
			}
			printf("\n\n");
		}else {
			printf("Sem Leituras\n\n");
		}
	}
	
	writeOnCSV1(sensoresPerType, typeSizes);
	writeOnCSV2(resumeMatrix, numSensors);

    return 0;
}
