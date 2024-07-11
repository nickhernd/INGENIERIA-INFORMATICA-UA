#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/wait.h>
#include <signal.h>

int KBUFFER = 1024;

int main(int argc, char const *argv[]) {
	struct sockaddr_in client, server;
	int fd1, fd2;
	int fichero;
	pid_t pid; // puede que no lo necesite
	char buffer[KBUFFER];
	int tam;

	fd1 = socket(AF_INET, SOCK_STREAM, 0); 

	server.sin_family = AF_INET;
	server.sin_port = htons(9999);
	server.sin_addr.s_addr = INADDR_ANY;

	bind(fd1, (struct sockaddr *)&server, sizeof(struct sockaddr_in));
	listen(fd1, 5);

	do{
		if(fork() == 0){
			tam = sizeof(struct sockaddr_in);
			fd2 = accept(fd1, (struct sockaddr *)&client, &tam);
			fichero = open("Google.html", O_RDONLY);
			do{
				tam = read(fichero, buffer, KBUFFER - 1);
				if(tam > 0) write(fd2, buffer, tam);
			}
			while(tam > 0);
			close(fd2);
			close(fichero);
		}
		else{
			printf("Esperando accion del cliente.\n");
			wait(NULL);
		}
	}
	while(1);


	return 0;
}