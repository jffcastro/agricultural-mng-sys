#include <stdio.h>
#include "structSensor.h"

void dailyResume(int numSensors, int sizeF, unsigned long * numValues, float resumeMatrix[numSensors][3], Sensor ** sensoresPerType, int typeSizes[]){

    float maxValue;
    float minValue;
    float median;
    float sumTotal;

    int sensor;
    int value;
    int contador;
    for (sensor = 0; sensor < 6; sensor++)
    {
		if (typeSizes[sensor] > 0){
			value = 0;
			maxValue = sensoresPerType[sensor][value].readings[0];
			minValue = maxValue;
			median = 0;
			sumTotal = maxValue;
			contador = 0;

			for (value = 0; value < typeSizes[sensor]; value++)
			{
				for (int i = 0; i < sensoresPerType[sensor][value].frequency; i ++){
					
					if (sensoresPerType[sensor][value].readings[i] > maxValue)
					{
						maxValue = sensoresPerType[sensor][value].readings[i];
					}else if (sensoresPerType[sensor][value].readings[i] < minValue)
					{
						minValue = sensoresPerType[sensor][value].readings[i];
					}

					sumTotal += sensoresPerType[sensor][value].readings[i]; 
					contador ++;
				}
			}

			median = sumTotal / contador;


			*(*(resumeMatrix + sensor) + 0) = maxValue;
			*(*(resumeMatrix + sensor) + 1) = minValue;
			*(*(resumeMatrix + sensor) + 2) = median;
		}
    }
}
