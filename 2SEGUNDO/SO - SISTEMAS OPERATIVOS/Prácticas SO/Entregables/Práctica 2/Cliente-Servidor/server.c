#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <fcntl.h>
#include <sys/types.h> 
#include <signal.h>
#include <sys/wait.h>

#define PORT 9999


int main(int argc, char *argv[]){
	socklen_t size;
	struct sockaddr_in serverAddress, clientAddress;
   	int sock, newSock, fichOrigen;
	char buffer[260];
	pid_t pid;

	//Apertura de canal(Familia protocolos, tipo transporte, protocolo)
	sock = socket(AF_INET, SOCK_STREAM, 0);
	if (sock == -1) {
		printf("Error, cant open socket\n");
    	exit(-1);
	}else{
		//Inicializamos a cero la estructura
		bzero((char *) &serverAddress, sizeof(serverAddress));  

		serverAddress.sin_port = htons(PORT);		
		serverAddress.sin_family = AF_INET;				
		serverAddress.sin_addr.s_addr = INADDR_ANY;		

		//Si la direccion del canal es incorrecta...
		//publicacion de la direccion a la red
		if(bind(sock, (struct sockaddr *) &serverAddress, sizeof(serverAddress)) < 0){ 
			perror("Error cant link socket\n");
		}
		
		else{
			//Disposicion para aceptar conexiones
			listen(sock, 5);	// cola de peticiones hasta 5.
			do{
			
				printf("Accepting connection\n");
				pid = fork(); 		// aqui creamos un hijo.

				// CODIGO HIJO y continua atendiendo peticiones.
				if(pid == 0){ 

					// este codigo de aqui lo ejecuta el hijo. 
					size = sizeof(clientAddress);
					printf("Son is waiting for connection...\n");
					//bloqueo para peticion (establecimiento de conexion)
					newSock = accept(sock, (struct sockaddr *) &clientAddress, &size); // me quedo esperando a un cliente.
					printf("Son accepts connection\n");				
					if(newSock < 0){ 
						perror("Err when accepting connection");
					}
					else{
						// el hijo utiliza newSock para conectarse con el cliente.
						fichOrigen = open("Google.html", O_RDONLY);
						if(fichOrigen < 0){
							perror("Err when opening fich\n");			
						}					
						else{
							// TRANSMISION DEL FICHERO.
							do{
								size = read(fichOrigen, buffer, 260 - 1);			
								if(size > 0){
									write(newSock, buffer, size);
								}
							}while(size > 0);
						}
						//cerramos todo
						close(newSock);
						close(fichOrigen);
					}
				}
				
				//CODIGO PADRE
				else{
					printf("Waiting for son\n");
					wait(NULL);
				}
			}while(1);
		
		}
	}	
	return 0; 
}
