#include <stdio.h>
#include "structSensor.h"

void limits(unsigned short maxiumLimit, unsigned short miniumLimit, int wrongReadLimit, int sensorType, int sensor, Sensor ** sensoresPerType)
{
  int i;
  int count = 0;
  int j;
  for(i = 0; i<sensoresPerType[sensorType][sensor].frequency; i++){
    if (sensoresPerType[sensorType][sensor].readings[i] > maxiumLimit || sensoresPerType[sensorType][sensor].readings[i] < miniumLimit){
        count++;
        //printf("Error!");
        if(count == wrongReadLimit){
            for(j=i; j > (i-count); j--){
                sensoresPerType[sensorType][sensor].readings[j] = 0;
            }
            count = 0;
        }
    }else{
        count = 0;
    }
  }
}
