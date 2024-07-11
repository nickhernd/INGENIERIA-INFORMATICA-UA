/**
* @author: Jaime Hernández
* @dni: 48776654W
*/

#include <stdio.h>
#include <stdlib.h>	
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>

/* 
	Ejecución del comando una vez recivida la señal
*/
void comand_pstree() {
	int pid, status;

	pid = fork(); // creación de proceso

	if(pid == 0) { 
		execlp("/bin/pstree", "pstree", "-p", NULL);
	} else {
		wait(&status);
	}
}

void comand_ls(){
	int pid, status;

	pid = fork();

	if(pid == 0){
		execlp("ls", "ls", "-la", NULL);
	} else{
		wait(&status);
	}
}

void waitproc() {
	int status;

	wait(&status);
}

int main(int argc, char *argv[]) {
	pid_t pid, ejec, PID_A, PID_B, PID_X, PID_Y, PID_Z; 
	int status;


	ejec = getpid(); // Id ejec
	printf("Soy el proceso ejec: mi pid es %d\n", ejec);

	pid = fork(); // Creación de hijo

	if (pid != 0) {

		wait(&status);    // espera de ejeccuón hasta que el hijo muera
		printf("Soy ejec (%d) y muero\n", ejec);

	} else {
		PID_A = getpid();
		printf("Soy A: mi pid es %d. Mi padre es %d\n", PID_A, ejec); 

		pid = fork(); //creación proceso B

		if(pid != 0) {
			signal(SIGUSR1, comand_pstree);	 // caso de que reciva una señal (SIGUSR1) se ejecuta comand_pstree
			signal(SIGUSR2, waitproc);	// caso de que reciva una señal (SIGUSR2) se ejecuta waitproc

			wait(&status); 	
			printf("Soy A (%d) y muero\n", PID_A);	
		} else {
			signal(SIGUSR1, comand_pstree);
			signal(SIGUSR2, waitproc);

			PID_B = getpid();
			printf("Soy B: mi pid es %d. Mi padre es %d. Mi Abuelo es %d\n", PID_B, PID_A, ejec);

			//Creación del árbol
			for(int i = 0; i < 3; i++) {
				pid = fork(); // hijos x, y ,z por cada i
				
				if(pid == 0) { // Caso en el que se este ejecutando el hijo hace el siguiente árbol (horizontal)
					switch(i) { // No creamos mas hijos a partir de x, y,z terminarmos sus procesos

						case 0:
							PID_X = getpid();
							printf ("Soy X. mi pid es %d. Mi padre es %d. Mi abuelo es %d. Mi bisabuelo es %d\n", PID_X, PID_B, PID_A, ejec); 

							signal(SIGUSR1, comand_ls);
							signal(SIGUSR2, waitproc);						

							pause();

							printf("Soy X (%d) y muero\n", getpid());						
							exit(0);						
							break;

						case 1:
							PID_Y = getpid();
							printf("Soy Y. mi pid es %d. Mi padre es %d. Mi abuelo es %d. Mi bisabuelo es %d\n", PID_Y, PID_B, PID_A, ejec); 

							signal(SIGUSR1, comand_ls);
							signal(SIGUSR2, waitproc);

							pause();

							printf("Soy Y (%d) y muero\n", getpid());						
							exit(0);						
							break;	

						case 2:
							PID_Z = getpid();

							printf("Soy Z. mi pid es %d. Mi padre es %d. Mi abuelo es %d. Mi bisabuelo es %d\n", PID_Z, PID_B, PID_A, ejec); 

							signal(SIGALRM, waitproc);

							alarm(atoi(argv[2])); 

							pause();

							if(strcmp(argv[1], "A") == 0 || strcmp(argv[1], "B") == 0){ 
								if(strcmp(argv[1], "A") == 0) {
									printf("Soy el proceso %s con pid %d, he recibido la señal\n", argv[1], PID_A);
								} else {
									printf("Soy el proceso %s con pid %d, he recibido la señal\n", argv[1], PID_B);
								}
								
								kill(PID_A, SIGUSR1);	// Mandamos la señal SIGUSR1 a PID del proceso  A

								signal(SIGALRM, waitproc);				
								
								alarm(atoi(argv[2])); 
								pause(); 

								kill(PID_X, SIGUSR2);
								kill(PID_Y, SIGUSR2);
								kill(PID_A, SIGUSR2);
								kill(PID_B, SIGUSR2);


								printf("Soy Z (%d) y muero\n", getpid());
								exit(0);						
							}

							if(strcmp(argv[1], "X") == 0 || strcmp(argv[1], "Y") == 0){
								if(strcmp(argv[1], "X") == 0) {
									printf("Soy el proceso %s con pid %d, he recibido la señal\n", argv[1], PID_X);
								} else {
									printf("Soy el proceso %s con pid %d, he recibido la señal\n", argv[1], PID_Y);
								}

								kill(PID_X, SIGUSR1);
								signal(SIGALRM, waitproc);				

								alarm(atoi(argv[2]));
								pause(); 

								kill(PID_Y, SIGUSR2);
								printf("Soy Z (%d) y muero\n", getpid());
								exit(0);						
							}

							break;

						}
					}

				}

				int i = 0;

				while(i < 3) {
					wait(&status);
					i++;
				}

				printf("Soy B (%d) y muero\n", PID_B);

			}

		}
		return 0;
}



