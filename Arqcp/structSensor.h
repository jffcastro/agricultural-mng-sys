typedef struct {
	unsigned short id;
	unsigned char sensorType;
	unsigned short maxLimit;
	unsigned short minLimit;
	unsigned long frequency;
	unsigned long frequencyPerSecond;
	unsigned short *readings;
 } Sensor;
