
#include <stdio.h> 
#include <sys/stat.h> 
#include <unistd.h> 
#include <stdlib.h> 
#include <string.h>
#include <fcntl.h>

//=======================================================================

int main(int argc, char *argv[]){
	int tuberia[2], fichero_IN, fichero_OUT, numLeidos;

	pipe(tuberia); 
	
	//=== PADRE ===//
	if(fork() != 0){
		// Caracteres indiciduales leidos del fichero.	
		char caracter;		
		close(tuberia[0]);

		//Apertura de ficheros en modo lectura
		fichero_IN = open(argv[1], O_RDONLY);	

		do{	// ...mientras que no llega al fin de fichero...
			numLeidos = read(fichero_IN, &caracter, sizeof(char)); 		

			// Escribo en el tuberia	
			write(tuberia[1], &caracter, sizeof(char));	

		}while(numLeidos != 0);

		char fin = '\0';

		write(tuberia[1], &fin, sizeof(char));
		close(fichero_IN);
	}

	//=== HIJO ===//
	else{	
		sleep(5);
		char caracter;

		// Finalizamos la escritura de la la segunda tubería
		close(tuberia[1]);

		fichero_OUT = creat(argv[2], 0666);					
		read(tuberia[0], &caracter, sizeof(char));	
		
		while(caracter != '\0'){ //...mientras que no llegue al final del fichero leido...
		
			write(fichero_OUT, &caracter, sizeof(char));
			read(tuberia[0], &caracter, sizeof(char));
		}		
		close(fichero_OUT);		
	}
	return 0;
}

//gcc copia.c
//a.out

