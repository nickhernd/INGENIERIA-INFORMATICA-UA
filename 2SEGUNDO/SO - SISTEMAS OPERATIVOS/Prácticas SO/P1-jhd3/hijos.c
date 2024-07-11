/**
	@author: Jaime Hernández 
	@dni: 48776654W
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>	
#include <string.h> 	
#include <unistd.h>
#include <signal.h>		
#include <sys/shm.h>


int main(int argc, char *argv[]) {
    int x, y, pid;
    int *pX, *pY, shareX, shareY;

    int children = 0;
    int i = 0;

    // Convertimos los argumentos de cadena a enteros.
    x = atoi(argv[1]);
    y = atoi(argv[2]);

    // Creamos dos segmentos de memoria compartida.
    shareX = shmget(IPC_PRIVATE, sizeof(int) * (x+1), IPC_CREAT | 0666);
    shareY = shmget(IPC_PRIVATE, sizeof(int) * y, IPC_CREAT | 0666);

    // Vinculamos los segmentos de memoria compartida a punteros.
    pX = (int *)shmat(shareX, 0, 0);
    pY = (int *)shmat(shareY, 0, 0);

    // Almacenamiento del PID del proceso padre en la primera posición del segmento pX.
    pX[0] = getpid();

    // Crear procesos hijos directos (x hijos).
    for (i = 0; i <= x; i++) {

        pid = fork();

        if (pid != 0) {
            
            break;

        } else {
            // Código de los hijos
            printf("Soy el subhijo %d, mis padres son: ", getpid());
            for (children = 0; children <= i; children++) {
                printf("%d", pX[children]);
                if (children != i) {
                    printf(", ");
                }
            }
            printf("\n");

            // Almacenamos el PID del hijo en el segmento pX.
            pX[i + 1] = getpid();
        }
    }

    // Código para el padre después de crear los hijos directos.
    if (i < x) {
        
        wait(NULL);

        // Si es el padre superpadre.
        if (i == 0) {
            printf("Soy el superpadre %d, mis hijos son: ", getpid());
            for (i = 1; i < x+2; i++) {
                printf("%d", pX[i]);
                if (i < x+1) {
                    printf(", ");
                }
            }


            printf("\n");
        }

    } else {
        
        // Código para los nietos.
        for (i = 0; i < y; i++) {
            pid = fork();
            if (pid == 0) {
                
                pY[i] = getpid();
                sleep(5); // Los nietos esperan 5 segundos.
                break;
            }
        }

        // Esperamos a que todos los nietos terminen.
        if (i == y) {
            for (i = 1; i < y; i++) {
                wait(NULL);
            }
        }
    }

    return 0;
}


