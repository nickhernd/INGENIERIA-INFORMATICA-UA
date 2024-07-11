#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <string.h>

int KBUFFER = 1024;

int main(int argc, char const *argv[]) {
	struct sockaddr_in client, server;
	int fd1, fd2;
	int fichero;
	int tam;
	char buffer[KBUFFER];

	fd1 = socket(AF_INET, SOCK_STREAM, 0);

	server.sin_family = AF_INET;
	server.sin_port = htons(9999);
	server.sin_addr.s_addr = inet_addr(argv[1]);

	connect(fd1, (struct sockaddr *)&server, sizeof(struct sockaddr_in));

	do{
		tam = read(fd1, buffer, KBUFFER - 1);
		if(tam > 0){
			buffer[tam] = '\0';
			printf("%s", buffer);
		}
	}
	while(tam > 0);
	close(fd1);

	return 0;
}