main: main.o dailyResume.o sensors.o fillMatrix.o ler.o pcg32_random_r.o limits.o addSensor.o update_sensors.o writeOnCSV.o
	gcc -Wall -g main.o dailyResume.o sensors.o fillMatrix.o ler.o pcg32_random_r.o limits.o addSensor.o update_sensors.o writeOnCSV.o -o main
main.o: main.c structSensor.h dailyResume.h  sensors.h fillMatrix.h limits.h addSensor.h sensor.h writeOnCSV.h
	gcc -Wall -g -c main.c -o main.o
addSensor.o: addSensor.c
	gcc -Wall -g -c addSensor.c -o addSensor.o
update_sensors.o: update_sensors.c
	gcc -Wall -g -c update_sensors.c -o update_sensors.o	
limits.o: limits.c
	gcc -Wall -g -c limits.c -o limits.o
pcg32_random_r.o: pcg32_random_r.s
	gcc -Wall -g -c pcg32_random_r.s -o pcg32_random_r.o
ler.o: ler.c pcg32_random_r.h
	gcc -Wall -g -c ler.c -o ler.o
fillMatrix.o: fillMatrix.c ler.h sensors.h
	gcc -Wall -g -c fillMatrix.c -o fillMatrix.o
sensors.o: sensors.s
	gcc -Wall -g -c sensors.s -o sensors.o
dailyResume.o: dailyResume.c
	gcc -Wall -g -c dailyResume.c -o dailyResume.o
writeOnCSV.o: writeOnCSV.c
	gcc -Wall -g -c writeOnCSV.c -o writeOnCSV.o
run: main
	./main
clean :
	rm ∗.o main
	
