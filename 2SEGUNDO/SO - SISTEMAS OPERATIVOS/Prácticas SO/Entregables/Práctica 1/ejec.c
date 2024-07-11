/*
*@author: Jose Miguel Carrión y Oscar Casado
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>	//wait()
#include <string.h> 	//strcmp
#include <unistd.h>
#include <signal.h>		


//=======================================================================

void end_Sleep(){
	//Encargado de recibir la señal.
}

//=======================================================================

void comando_pstree(){
	int pid, estado;
	pid = fork(); 

	if(pid == 0){ 
		execlp("pstree", "pstree", "-p", NULL);
	}
	else{wait(&estado);}
}

//=======================================================================

void comando_ls(){
	int pid, estado;

	pid = fork();
	if(pid == 0){
		execlp("ls", "ls", "-la", NULL);
	}
	else{wait(&estado);}
}

//=======================================================================

int main(int argc, char *argv[]){
	//pid_t = int
	pid_t arb, proceso_A, proceso_B, pX, pY, pZ, pid; 

	int estado, i;  

	arb = getpid();
	printf("Soy el proceso arb: mi pid es %d\n", arb);		
	
	pid = fork(); // crea el proceso A

	if(pid != 0){ // si el proceso 'arb' se encuentra en ejecución....	
		wait(&estado); 
		printf("Soy arb(%d) y muero\n", arb); 
	}
	
	else{ // proceso proceso_A en ejecución
		proceso_A = getpid();
		printf("Soy A: mi pid es %d. Mi padre es %d\n", proceso_A, arb); 
		pid = fork(); 

		if(pid != 0){ //ejecución A
			signal(SIGUSR1, comando_pstree); // signal(señal, como_responde_a_esa_señal);		
			signal(SIGUSR2, end_Sleep);		
			wait(&estado); 	
			printf("Soy A(%d) y muero\n", proceso_A);	
		}

		else{ // proceso proceso_B en ejecución
			signal(SIGUSR1, comando_pstree);
			signal(SIGUSR2, end_Sleep);

			proceso_B = getpid();
			printf("Soy B: mi pid es %d. Mi padre es %d. Mi Abuelo es %d\n", proceso_B, proceso_A, arb);

			for(i = 1; i <= 3; i++){ // B tiene 3 hijos.			
				pid = fork();

				if(pid != 0){ 
					//el proceso fuente inicializa los pids de los hijos con el suyo propio para G.Horizotal
					if(i==1) pX = pid; 
					if(i==2) pY = pid;
				}
			
				if(pid == 0){ // Si se encuentra en ejecución cualquiera de los procesos (pX, pY, pZ)
					switch(i){
						case 1:
						// pX = getpid();
						printf("Soy X. mi pid es %d. Mi padre es %d. Mi abuelo es %d. Mi bisabuelo es %d\n", pX, proceso_B, proceso_A, arb); 
						signal(SIGUSR1, comando_ls);
						signal(SIGUSR2, end_Sleep);						
						pause(); // para que al padre le de tiempo de tener a los hijos.

						printf("Soy X(%d) y muero\n", getpid());						
						exit(0);						
						break;

						case 2:
						pY = getpid();
						printf("Soy Y. mi pid es %d. Mi padre es %d. Mi abuelo es %d. Mi bisabuelo es %d\n", pY, proceso_B, proceso_A, arb); 
						signal(SIGUSR1, comando_ls);
						signal(SIGUSR2, end_Sleep);
						pause();

						printf("Soy Y(%d) y muero\n", getpid());						
						exit(0);						
						break;	

						case 3:
						pZ = getpid();
						printf("Soy Z. mi pid es %d. Mi padre es %d. Mi abuelo es %d. Mi bisabuelo es %d\n", pZ, proceso_B, proceso_A, arb); 
						signal(SIGALRM, end_Sleep);

						alarm(atoi(argv[2])); // alarma con el tiempo pasado como 2º argumento
						pause();
						
						//··················CONTROL DE ARGUMENTOS·······················
						if(strcmp(argv[1], "A") == 0 || strcmp(argv[1], "B") == 0){  
							kill(proceso_A, SIGUSR1); // ejecuta el comando.	

							signal(SIGALRM, end_Sleep); // cuando suene el despertado despierta.				
							alarm(1); 
							pause(); 

							kill(pX, SIGUSR2);
							kill(pY, SIGUSR2);

							/* Este fragmento de código es el que mira el profesor, ejecutarlo con esto comentado.
							   Y cuando se pase descomentais estas dos lineas para que os ponga un 10.
							kill(pA, SIGUSR2);
							kill(pB, SIGUSR2);
							*/

							kill(pA, SIGUSR2);
							kill(pB, SIGUSR2);


							printf("Soy Z(%d) y muero\n", getpid());
							exit(0);						
						}

						if(strcmp(argv[1], "X") == 0 || strcmp(argv[1], "Y") == 0){
							kill(pX, SIGUSR1);

							signal(SIGALRM, end_Sleep); // cuando suene el despertado despierta.				
							alarm(1);
							pause(); 

							kill(pY, SIGUSR2);

							printf("Soy Z(%d) y muero\n", getpid());
							exit(0);						
						}
						//··············································································
						
						break;					
					}

					break; // Salida auxiliar del bucle				
				}
			} 

			// el padre cuando termine.
			for(i = 1; i <= 3; i++){ // espera muerte de todos los hijos
				wait(&estado);
			}

			printf("Soy B(%d) y muero\n", proceso_B);							
		}
	}
	return 0;
}

//gcc -g -o ejec ejec.c
//ejec con un pause() en A || B
// ejec A 2 &
// pstree (nº)
