#include <stdio.h>
#include "sensores.h"
#include "ler.h"

int main(){
	int leitura = ler();
	printf("%hhd, %hhd\n",sens_temp(16,leitura),leitura);
	
	leitura = ler();
	printf("%hhd, %hhd\n",sens_velc_vento(16,leitura),leitura);
	
	leitura = ler();
	printf("%hhd, %hhd\n",sens_humd_atm(20,16,leitura),leitura);
	
	leitura = ler();
	printf("%hhd, %hhd\n",sens_humd_solo(20,16,leitura),leitura);
	
	leitura = ler();
	printf("%hhd, %hhd\n",sens_pluvio(20,16,leitura),leitura);
	
}
