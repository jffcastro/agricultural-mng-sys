/*
typedef struct {
	unsigned short id;													// 2
	unsigned char sensor_type;											// 1
	unsigned short max_limit; // limites do sensor						// 2
	unsigned short min_limit;											// 2
	unsigned long frequency; // frequency de leituras (em segundos)		// 8
	unsigned long readings_size; // tamanho do array de leituras		// 8
} Sensor;														// Total:  23
*/ 

/**
 * Remove sensors of a given type.
 * 
 * @param sensores		Sensors array
 * @param sensor_type	Type of arrays to remove
 * @param num_sensors	Number of sensors
*/
void remove_sensors(Sensor **sensores, unsigned short id, int *size, unsigned long **numValues, unsigned char **sensorTypes, Sensor **sensoresPerType, int *typeSizes);



void chageFrequency(Sensor **sensores, unsigned short id, int *size, unsigned long **numValues, unsigned char **sensorTypes, Sensor **sensoresPerType, int *typeSizes, int newFrequency);
