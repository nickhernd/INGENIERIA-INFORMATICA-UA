/** 
* @author: Jaime Hernández
* @dni: 48776654W
*/

#include <stdio.h>  
#include <unistd.h> 
#include <stdlib.h> 
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>

int main(int argc, char *argv[]){
    int fd[2], file_read, file_write, byte_read;
    char character;

    char end = '\0';

    /*
        fd[0] --> lectura
        fd[1] --> escritura
    */
    
    pipe(fd); // Creación del pipe

    pid_t pid = fork(); // Creamos proceso
    
    if(pid != 0) {

        close(fd[0]); // Cerramos lectura

        file_read = open(argv[1], O_RDONLY);
        
        do {    
            byte_read = read(file_read, &character, sizeof(char));        
            if (byte_read > 0) {
                write(fd[1], &character, sizeof(char));
            }    
        } while(byte_read > 0);

        write(fd[1], &end, sizeof(char));
        
        close(fd[1]);  // Cerramos escritura
    
    } else {   

        close(fd[1]); // Cerramos escritura

        file_write = creat(argv[2], 0777);
        
        while(read(fd[0], &character, sizeof(char)) > 0){ 
            if (character == end) {
                break;
            }

            write(file_write, &character, sizeof(char));
        }    
        
        close(fd[0]); // Cerramos lectura
    }
    return 0;
}

