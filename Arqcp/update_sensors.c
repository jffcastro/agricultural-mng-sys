#include <stdio.h>
#include <stdlib.h>
#include "structSensor.h"

//testar depois que ainda nao testei
//posso estar ai a fazer alguma coisa mal
void remove_sensors(Sensor **sensores, unsigned short id, int *size, unsigned long **numValues, unsigned char **sensorTypes, Sensor **sensoresPerType, int *typeSizes)
{
  int i;
  int wasFound = 0;
  int position = 0;
  unsigned char sensorType;
  
  
  for(i=0; i<*size; i++){
	//quando se encontra o sensor que se quer remover
	if((*sensores)[i].id == id){
		sensorType = (*sensores)[i].sensorType;
		//iguala-se essa posiçao ao sensor da posiçao seguinte
		(*sensores)[i] = (*sensores)[i+1];
		//esta variavel é usada para ver se o sensor que se quer remover ja foi encontrado
		wasFound = 1;
		position = i;
	}
	
	//se o sensor ja foi encontrado e não é a ultima posiçao do array
	if(wasFound == 1 && i != (*size - 1)){
		//iguala-se a posiçao em que se esta à posiçao seguinte
		//ou seja estou a trazer as coisas que estao a frente para trás
		//para nao ficar um buraco sem nada no meio do array
		(*sensores)[i] = (*sensores)[i+1];
	}
  }
  
  if (wasFound){
  
  
	  //este for é usado para a partir da posiçao que o sensor que vai ser removido foi encontrado,
	  //passar os valores das frequencias no array para a posiçao anterior a que eles estao
	  //ficando assim o ultimo espaço do array vazio para depois ser removido com o realloc
	  int j;
	  for(j=position; j < (*size-1); j++){
		(*numValues)[j] = (*numValues)[j+1];
	  }
	  
	  //este for é usado para a partir da posiçao que o sensor que vai ser removido foi encontrado,
	  //passar os tipos dos sensores no array para a posiçao anterior a que eles estao
	  //ficando assim o ultimo espaço do array vazio para depois ser removido com o realloc
	  int k;
	  for(k=position; k < (*size-1); k++){
		(*sensorTypes)[k] = (*sensorTypes)[k+1];
	  }
	  
	  
	  //se der porcaria é aqui !!!!!!!!!!!!!!!!!!11
	  wasFound = 0;
	  for(k=0; k<*(typeSizes + sensorType) - 1; k++){
		
		if((*(*(sensoresPerType + sensorType) + k)).id == id){
			wasFound = 1;
		} 
		
		if(wasFound == 1){
			*(*(sensoresPerType + sensorType) + k) = *(*(sensoresPerType + sensorType) + k + 1);
		}
	  }
	  
	  
	  
	  //por fim uso o realloc para realocar a memoria do array que vai ser menor uma estrutura do que era antes
	  Sensor *aux3 = (Sensor *) realloc(*sensores, (*size-1) * sizeof(Sensor));
		if (aux3 != NULL){
			*sensores = aux3;
		}
	  
	  //realocar a memoria do array para menos 1
	  unsigned long *aux = (unsigned long *) realloc(*numValues, (*size-1) * sizeof(unsigned long));
		if (aux != NULL){
			*numValues = aux;
		}
	  
	  //realocar a memoria do array para menos 1
	  unsigned char *aux2 = (unsigned char *) realloc(*sensorTypes, (*size-1) * sizeof(unsigned char));
		if (aux2 != NULL){
			*sensorTypes = aux2;
		}
		
		//se der porcaria é aqui!!!!!!!!!!!!!!!!!
		*(typeSizes + sensorType) -= 1;
		Sensor *aux4 = (Sensor *) realloc(*(sensoresPerType + sensorType), *(typeSizes + sensorType) * sizeof(Sensor));
		if (aux4 != NULL){
			*(sensoresPerType + sensorType) = aux4;
		}
	  
	  //diminui tambem o numero de sensores pois foi retirado um
	  *size -= 1;
	}else {
		printf("Sensor not found!!!\n");
	}
  
}


void chageFrequency(Sensor **sensores, unsigned short id, int *size, unsigned long **numValues, unsigned char **sensorTypes, Sensor **sensoresPerType, int *typeSizes, int newFrequency){
	int i, k;
	int wasFound = 0;
	int position = 0;
	unsigned char sensorType;
  
  for(i=0; i<*size; i++){
	//quando se encontra o sensor que se quer remover
	if((*sensores)[i].id == id){
		sensorType = (*sensores)[i].sensorType;
		//iguala-se essa posiçao ao sensor da posiçao seguinte
		(*sensores)[i] = (*sensores)[i+1];
		//esta variavel é usada para ver se o sensor que se quer remover ja foi encontrado
		wasFound = 1;
		position = i;
		sensores[i]->frequency = newFrequency;
		break;
	}
  }
  
  if (wasFound){
	  
	*numValues[position] = newFrequency;
	wasFound = 0;
	
	for(k=0; k<*(typeSizes + sensorType); k++){

		if((*(*(sensoresPerType + sensorType) + k)).id == id){
			wasFound = 1;
			(*(*(sensoresPerType + sensorType) + k)).frequency = newFrequency;
			break;
		}
	}
  }else {
		printf("Sensor not found!!!\n");
	}
}

