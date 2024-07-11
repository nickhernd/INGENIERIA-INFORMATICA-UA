#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <string.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>

#define PORT 9999

int main(int argc, char *argv[]){
    char buffer[600];
    int sock, size, puerto;
    struct sockaddr_in serverAdress;
    struct hostent *server;

	//Apertura del sockets
    sock = socket(AF_INET, SOCK_STREAM, 0);
	
    if (sock < 0) 
        perror("Err: Cant create socket");
	else{
		//inicializamos la estructura a 0
	    bzero((char *) &serverAdress, sizeof(serverAdress));

		serverAdress.sin_port = htons(PORT);
		serverAdress.sin_family = AF_INET;
		serverAdress.sin_addr.s_addr = inet_addr(argv[1]);

		//Petición de conexión al sockets
		if (connect(sock ,(struct sockaddr *) &serverAdress,sizeof(serverAdress)) < 0){ 
		    perror("Err: Cant connect to socket");
		}
		else{
			do{
				size = read(sock, buffer, 600 -1);
				if(size > 0){
					buffer[size] = '\0';
				printf("%s",buffer);
				}		    
			}while(size > 0);
			close(sock);
		}
	}
    return 0;
}
