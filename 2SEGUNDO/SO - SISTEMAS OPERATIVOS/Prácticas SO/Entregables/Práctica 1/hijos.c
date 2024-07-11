#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>	//wait()
#include <string.h> 	//strcmp
#include <unistd.h>
#include <signal.h>		
#include <sys/shm.h>

//=======================================================================

int main(int argc, char *argv[]){
	int contador, x, y, pid;

	// Punteros para el uso de 'shmat'.
	int *puntero_X, *puntero_Y, shm1, shm2;

	int hijos = 0;

	if(argc != 3){
		printf("Error en parametros.\n"); 
	}

	else{
		printf("\n");

		// String -> Int
		x = atoi(argv[1]); 
		y = atoi(argv[2]); 

		if(x <= 0 || y <= 0){
			printf("Error. x o y incorrectos\n");			
		}

		else{
			// IPC_PRIVATE => memoria solo accesible por hijos.
			// Obtención de un segmento de memoria compartida con su ID
			shm1 = shmget(IPC_PRIVATE, sizeof(int)*(x + 1), IPC_CREAT|0666);
			shm2 = shmget(IPC_PRIVATE, sizeof(int)*y, IPC_CREAT|0666);

			//Vinculación de la dirección de memoria compartida
			puntero_X = (int *)shmat(shm1, 0, 0); 
			puntero_Y = (int *)shmat(shm2, 0, 0);
			// a partir de este momento accederemos a puntero_X y puntero_Y como si fueran vectores.
		
			//PID del primer padre
			puntero_X[0] = getpid(); 

			//Bucle parametrizado (G.H.)
			for(contador = 1; contador <= x; contador++){
				pid = fork(); // tengo un hijo.
				if(pid != 0)
					break;	// el padre se sale del bucle para no hacer mas fork.
				else{					
					printf("Soy el subhijo %d mis padres son: ", getpid());

					// impresión de procesos padre					
					for(hijos = 0; hijos < contador; hijos++){
						printf("%d", puntero_X[hijos]);

						if(hijos != contador-1) // únicamente si no somos el último padre
							printf(", ");	
					}
					printf("\n");

					// Almacenaje de PID´s hijos					
					puntero_X[contador] = getpid(); 			
				}			
			}

			if(contador <= x){ 
				// rama vertical menos el ultimo.				
				wait(NULL);

				if(contador == 1){ 
					printf("Soy el superpadre %d mis hijos son ", getpid());
					
					// IMPRIMO EL VECTOR DE IDS DE LA RAMA HORIZONTAL		
					for(contador = 1; contador <= x; contador++){
						printf("%d", puntero_X[contador]);
						printf(", ");					
					}
					
					// IMPRIMO EL VECTOR DE IDS DE LA RAMA VERTICAL
					for(contador = 1; contador <= y; contador++){
						printf("%d", puntero_Y[contador-1]);
						if(contador < y)
							printf(", ");
					} 

					printf("\n"); 
				}
			}
			else{

				for(contador = 1; contador <= y; contador++){

					pid = fork();

					if(pid == 0){ // ..si el hijo no tiene mas hijos...
						puntero_Y[contador-1] = getpid();			
						sleep(20); 
						break; 
					}
				}
				
				if(contador == y + 1){ // el padre despues de crear los procesos debe esperar.
					for(contador = 1; contador <= y; contador++)
						wait(NULL);
				}
			}
		}
	}

	return 0;
}

// gcc -g -o hijos hijos.c
// ./hijos 3 5 &
// pstree -c | grep hijos


