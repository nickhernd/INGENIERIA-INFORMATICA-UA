#include<iostream>
#include <vector>
#include <stdlib.h>
#include <string>
#include <string.h>

#include <fstream>
#include <sstream>

using namespace std;

struct Proceso{
    string nombre;
    unsigned int instanteLlegada;
    unsigned int memoriaRequerida;
    unsigned int tiempoEjecucion;
    unsigned int contador;
};

struct Procesador{
    vector <Proceso> cola;
    vector <Proceso> ejecutandose;
    unsigned int memoriaTotal;
};

void leerFich(string nombreImport, vector<Proceso> &procesos){
    ifstream file_imported;
    
    file_imported.open(nombreImport.c_str(), ios::in);
    
    if(file_imported.is_open()){
        string linea="";      
        
        //Leemos linea a linea
        while(getline(file_imported, linea)){
            stringstream ss(linea);
            Proceso proceso;
            
            ss >> proceso.nombre >> proceso.instanteLlegada >> proceso.memoriaRequerida >> proceso.tiempoEjecucion;
            proceso.contador = proceso.tiempoEjecucion;

            procesos.push_back(proceso);
        }
        
        file_imported.close(); 
    }
    else{
        cout<< "El fichero de entrada no se encuentra en el directorio" << endl;
    }
}


void reordenarPorLlegada(vector<Proceso> &procesos){

    //Repetimos la comprobacion hasta que estemos seguros que esta totalmente ordenado
    for(int i = 1; i < (int)procesos.size(); i++){
        //Recorremos el vector
        for(int j = 0; j < (int)procesos.size() - 1; j++){   //Como compara parejas el último no hace falta analizarlo
            if(procesos[j].instanteLlegada > procesos[j+1].instanteLlegada){
                //Los valores mas grandes los vamos trasladando al final del vector
                swap(procesos[j], (procesos[j+1]));
            }
        }
    }
}

void ampliarProcesador(Procesador &procesador, int pos, int memoriaRestante){
    Proceso nuevo_hueco;
    nuevo_hueco.nombre = "hueco";
    nuevo_hueco.instanteLlegada = 0;
    nuevo_hueco.contador = 0;
    nuevo_hueco.tiempoEjecucion = 0;
    nuevo_hueco.memoriaRequerida = memoriaRestante;
    procesador.ejecutandose.push_back(nuevo_hueco);
	
	for(int i = procesador.ejecutandose.size() - 1 ; i > pos + 1; i--){		
   		swap(procesador.ejecutandose[i], procesador.ejecutandose[i-1]);
	}
}


void eliminarProcesos(vector<Proceso> &ejecutandose){

    //Recorremos el vector de procesos que se estan ejecutando A LA INVERSA
    for(int k = ejecutandose.size() - 1; k >= 0 ; k--){
    	
    	//Los huecos han de tener un tiempo de vida ilimitado
    	if(ejecutandose[k].nombre.compare("hueco") != 0)
        	ejecutandose[k].contador--;
		
		//Si un proceso finaliza
        if(ejecutandose[k].contador == 0){    	
			//pasa a ser un hueco
            ejecutandose[k].nombre = "hueco"; 
        }   
    }
}

bool controlTiempo(vector<Proceso> cola, vector<Proceso> ejecutandose, int memoriaTotal){
    
    bool contar = false;
    
    //Si la cola no tiene procesos retenidos...
    if(cola.empty()){

        //Cuentas si te quedan procesos vivos
        for(int i = 0; i < (int)ejecutandose.size() && contar == false; i++){
            if(ejecutandose[i].contador != 0)
                contar = true;
        }
        
        //Siempre como poco vamos a tener un proceso 'hueco' con el contador a 0
        if(ejecutandose[0].nombre.compare("hueco") == 0 && ejecutandose[0].memoriaRequerida == (unsigned int) memoriaTotal)
        	contar = false;
    }    
        
    else{
        //Cuentas si tienes cosas en cola
        contar = true;
    }

    return contar;
}

void solaparHuecos(vector<Proceso> &ejecutandose){
	    
		for(int i = (int) ejecutandose.size() -1; i >= 0; i--){
			//Comprobamos si puede solaparse con algun otro hueco colindante
       		//PD: tener en cuenta que no podemos acceder a la posicion k=size
        	if(i < (int) ejecutandose.size()  - 1 &&  ejecutandose[i].nombre.compare("hueco") == 0 && ejecutandose[i+1].nombre.compare("hueco") == 0){
        		ejecutandose[i].memoriaRequerida += ejecutandose[i+1].memoriaRequerida;
        		//Se elimina uno de los huecos redundantes
        		ejecutandose.erase(ejecutandose.begin() + i + 1);
			}
		}        
        
}


void colaToProcesador(Procesador &procesador, bool &borrado){
	int hueco = -1;
	        	
	// Se mira a ver si hay un hueco bueno para el 1er proceso de la cola
    for(int i=0; i < (int) procesador.ejecutandose.size() && borrado == false; i++){

        //Si el primer elemento de la cola cabe en un hueco
        if(procesador.ejecutandose[i].nombre.compare("hueco") == 0 && procesador.cola[0].memoriaRequerida <= procesador.ejecutandose[i].memoriaRequerida){
        	
            hueco = i;
            int memoriaRestante = procesador.ejecutandose[i].memoriaRequerida - procesador.cola[0].memoriaRequerida;

            //Si cabe justo simplemente se sustituyen los valores
            if( memoriaRestante == 0){
                swap(procesador.ejecutandose[i], procesador.cola[0]);
            }
            
            //Sino hay que crear un nuevo hueco con la memoria restante
            else{
                //Pasar todos los procesos una posicion a la derecha y hacemos el hueco restante
				ampliarProcesador(procesador, i, memoriaRestante);
                //Metemos el proceso que toca
                swap(procesador.ejecutandose[i], procesador.cola[0]);
                //TODO comprobador de huecos : si hay varios huecos consecutivos debemos solaparlos 
				solaparHuecos(procesador.ejecutandose);
            }
            
            //Eliminamos el proceso de la cola
            procesador.cola.erase(procesador.cola.begin());	
            borrado = true;
        }
    }
    
    if(hueco == -1)
            	borrado = false;
}

void procesosToProcesador(vector<Proceso> &procesos, Procesador &procesador){
	
	int posHueco = -1;
	
	// Se mira a ver si hay un hueco bueno para el 1er proceso de la cola
    for(int i=0; i < (int) procesador.ejecutandose.size(); i++){
    	
        //Si el primer elemento del vector procesos cabe en un hueco
        if(procesador.ejecutandose[i].nombre.compare("hueco") == 0 && procesos[0].memoriaRequerida <= procesador.ejecutandose[i].memoriaRequerida){
        	
        	posHueco = i;
            int memoriaRestante = procesador.ejecutandose[i].memoriaRequerida - procesos[0].memoriaRequerida;
            
            //Si cabe justo simplemente se sustituyen los valores
            if( memoriaRestante == 0){
                swap(procesador.ejecutandose[i], procesos[0]);
            }
            
            //Sino hay que crear un nuevo hueco con la memoria restante
            else{
                //Pasar todos los procesos una posicion a la derecha y hacemos el hueco restante
				ampliarProcesador(procesador, i, memoriaRestante);
                //Metemos el proceso que toca
                swap(procesador.ejecutandose[i], procesos[0]);
                //TODO comprobador de huecos : si hay varios huecos consecutivos debemos solaparlos 
				solaparHuecos(procesador.ejecutandose);
            }	
        }
    }
    
    if(posHueco == -1){
    	//Sino cabe en ningun hueco a la cola
        procesador.cola.push_back(procesos[0]);        
	}
	
	procesos.erase(procesos.begin()); 
}

void algPrimerHueco(int memoriaTotal, string nombreImport, string nombreExport){
	vector<Proceso> procesos;

    leerFich(nombreImport, procesos);

    reordenarPorLlegada(procesos);
    
    //Inicializamos un procesador encargado de ejecutar los procesos
    Procesador procesador;
    procesador.memoriaTotal = (unsigned int) memoriaTotal;
    
    //Inicializamos el procesador con un hueco indicado por paramatro
    Proceso inicializacion;
    inicializacion.nombre = "hueco";
    inicializacion.instanteLlegada = 0;
    inicializacion.contador = 0;
    inicializacion.tiempoEjecucion = 0;
    inicializacion.memoriaRequerida = memoriaTotal;
    
    procesador.ejecutandose.push_back(inicializacion);

    // true -> seguir con el siguinte instante de tiempo
    // false -> parar ejecucion
    bool contar = true;
    
    ofstream file_exported;

    file_exported.open(nombreExport.c_str(), ios::out);

    if(file_exported.is_open()){
    	
		//Controla los instantes de tiempo
		for(int instante = 1; contar == true || !procesos.empty(); instante++){
			
			//Instante de tiempo impreso en terminal y fichero
			cout << instante << " ";
       		file_exported << instante << " ";
		   	
		    bool borrado;
		
		    do{
		    	borrado = false;
		    	
		        //Si no cabe ni aun estando el procesador vacio a chuparla
		        if( !procesador.cola.empty() && (procesador.cola[0].memoriaRequerida > procesador.memoriaTotal)){
		            procesador.cola.erase(procesador.cola.begin());
		            borrado = true;
		        }
				  
		        //Si podria llegar a caber...
		        else if (!procesador.cola.empty()){
					colaToProcesador(procesador, borrado);
		        }
		        	
		    }while(borrado == true);
				
			
		    //Reubicamos los procesos originales
		    while(!procesos.empty() && (int)procesos[0].instanteLlegada == instante){		
		    	procesosToProcesador(procesos, procesador);
		    }		
			
			//Hay que tener en cuenta las posiciones de memoria que han ocupado los procesos anteriores
			int posInicial = 0;
			
			//Impresion de procesos ejecutandose en ese instante en el procesador tanto en terminal como en fichero
		    for(int i = 0; i < (int)procesador.ejecutandose.size(); i++){
		    	
		        cout << "[" << posInicial << " " 
		            << procesador.ejecutandose[i].nombre << " " 
		            << procesador.ejecutandose[i].memoriaRequerida << "] ";
		            
		        file_exported << "[" << posInicial << " " 
		            		  << procesador.ejecutandose[i].nombre << " " 
		                      << procesador.ejecutandose[i].memoriaRequerida << "] ";
		        
		        posInicial += procesador.ejecutandose[i].memoriaRequerida;
		    }       
			cout << endl;
			file_exported << endl;
			
						contar = controlTiempo(procesador.cola, procesador.ejecutandose, memoriaTotal);

		    eliminarProcesos(procesador.ejecutandose);
		    solaparHuecos(procesador.ejecutandose);
		    	

		}

        file_exported.close();
    }
    else
        cout << "El fichero de salida no pudo ser creado" << endl;
    
    
    cout << "Algoritmo terminado" << endl;
}

void algMejorHueco(int memoriaTotal, string nombreImport, string nombreExport){
	vector<Proceso> procesos;

    leerFich(nombreImport, procesos);

    reordenarPorLlegada(procesos);

    //Inicializamos un procesador encargado de ejecutar los procesos
    Procesador procesador;
    procesador.memoriaTotal = (unsigned int) memoriaTotal;
    
    //Inicializamos el procesador con un hueco indicado por paramatro
    Proceso inicializacion;
    inicializacion.nombre = "hueco";
    inicializacion.instanteLlegada = 0;
    inicializacion.contador = 0;
    inicializacion.tiempoEjecucion = 0;
    inicializacion.memoriaRequerida = memoriaTotal;
    
    procesador.ejecutandose.push_back(inicializacion);

    // true -> seguir con el siguinte instante de tiempo
    // false -> parar ejecucion
    bool contar = true;
    
    ofstream file_exported;

    file_exported.open(nombreExport.c_str(), ios::out);

    if(file_exported.is_open()){
    	
		//Controla los instantes de tiempo
		for(int instante = 1; contar == true || !procesos.empty(); instante++){
			
			//Instante de tiempo impreso en terminal y fichero
			cout << instante << " ";
       		file_exported << instante << " ";
		   	
		    bool borrado;
		
		    do{
		    	borrado = false;
		    	
		        //Si no cabe ni aun estando el procesador vacio a chuparla
		        if( !procesador.cola.empty() && (procesador.cola[0].memoriaRequerida > procesador.memoriaTotal)){
		            procesador.cola.erase(procesador.cola.begin());
		            borrado = true;
		        }
				  
		        //Si podria llegar a caber...
		        else if (!procesador.cola.empty()){
					colaToProcesador(procesador, borrado);
		        }
		        	
		    }while(borrado == true);
				
			
		    //Reubicamos los procesos originales
		    while(!procesos.empty() && (int)procesos[0].instanteLlegada == instante){		
		    	
		    	int posHueco = -1;
		    	int diferencia = -1;
		    	
				// Se mira a ver si hay un hueco bueno para el 1er proceso de la cola
			    for(int i=0; i < (int) procesador.ejecutandose.size(); i++){
			    	
			        //Si el primer elemento del vector procesos cabe en un hueco
			        if(procesador.ejecutandose[i].nombre.compare("hueco") == 0 && procesos[0].memoriaRequerida <= procesador.ejecutandose[i].memoriaRequerida){
			        	if(diferencia > procesador.ejecutandose[i].memoriaRequerida - procesos[0].memoriaRequerida || diferencia == -1){
				        	posHueco = i;
				        	diferencia = procesador.ejecutandose[i].memoriaRequerida - procesos[0].memoriaRequerida;	
						}   		
			        }
			    }
			    
			    //Si no cabe en ningun hueco
			    if(posHueco == -1){
			    	//Sino cabe en ningun hueco a la cola
			        procesador.cola.push_back(procesos[0]);        
				}
				else{
					//Si cabe justo simplemente se sustituyen los valores
		            if( diferencia == 0){
		                swap(procesador.ejecutandose[posHueco], procesos[0]);
		            }
		            
		            //Sino hay que crear un nuevo hueco con la memoria restante
		            else{
		                //Pasar todos los procesos una posicion a la derecha y hacemos el hueco restante
						ampliarProcesador(procesador, posHueco, diferencia);
		                //Metemos el proceso que toca
		                swap(procesador.ejecutandose[posHueco], procesos[0]);
		                //TODO comprobador de huecos : si hay varios huecos consecutivos debemos solaparlos 
						solaparHuecos(procesador.ejecutandose);
		            }	
				}
				
				procesos.erase(procesos.begin()); 			    	
		    }		
			
			//Hay que tener en cuenta las posiciones de memoria que han ocupado los procesos anteriores
			int posInicial = 0;
			
			//Impresion de procesos ejecutandose en ese instante en el procesador tanto en terminal como en fichero
		    for(int i = 0; i < (int)procesador.ejecutandose.size(); i++){
		    	
		        cout << "[" << posInicial << " " 
		            << procesador.ejecutandose[i].nombre << " " 
		            << procesador.ejecutandose[i].memoriaRequerida << "] ";
		            
		        file_exported << "[" << posInicial << " " 
		            		  << procesador.ejecutandose[i].nombre << " " 
		                      << procesador.ejecutandose[i].memoriaRequerida << "] ";
		        
		        posInicial += procesador.ejecutandose[i].memoriaRequerida;
		    }       
			cout << endl;
			file_exported << endl;
			
			contar = controlTiempo(procesador.cola, procesador.ejecutandose, memoriaTotal);
		    eliminarProcesos(procesador.ejecutandose);
		    solaparHuecos(procesador.ejecutandose);
		    	

		}

        file_exported.close();
    }
    else
        cout << "El fichero de salida no pudo ser creado" << endl;
    
    
    cout << "Algoritmo terminado" << endl;

}

int argumentValidation(int argc, char *argv[], string &nombreImport, string &nombreExport, int &memoriaTotal, bool &primerHueco, bool &mejorHueco){
    
    int error = -1;

    //Recorremos los argumentos
    for(int i = 1; i < argc && error == -1; i++){
        switch(i){
            //Primer argumento -> nombre del fichero importado
            case 1:
                nombreImport = argv[i];             
            break;

            //Segundo argumento -> nombre del fichero exportado
            case 2:
                nombreExport = argv[i];
            break;
            
            //Tercer argumento -> tamaño de la memoria total
            case 3:
                if(atoi(argv[i]) < 0)
                    error = 1;

                memoriaTotal = atoi(argv[i]);
            break;

            //Cuarto argumento -> algoritmo seleccionado
            case 4:
                //Primer hueco algoritmo
                if(strcmp(argv[i], "-p") == 0)
                    primerHueco = true;

                //Mejor hueco algoritmo
                else if(strcmp(argv[i], "-m") == 0)
                    mejorHueco = true;

                else
                    error = 2;
            break;
        }
    }
    return error;
}

int main(int argc, char *argv[]){

    string nombreImport = "";
    string nombreExport = "";
    int memoriaTotal = 0;
    bool primerHueco = false, mejorHueco = false;

    //CONTROL DE ARGUMENTOS
    if(argc !=5){
		cout << "Has introducido mal los argumentos." << endl;
        
	}
    else{
        int error = argumentValidation(argc, argv, nombreImport, nombreExport, memoriaTotal, primerHueco, mejorHueco);
        //Si se detectan errores en los argumentos se muestran errores y se termina le programa
        if(error == -1){
            cout << "Fichero de entrada escogido: " << nombreImport << endl 
            	 << "Fichero de volcado escogido: " << nombreExport << endl
                 << "Capacidad maxima del procesador: " << memoriaTotal << endl
				 << "=========================================================" << endl;

            if(primerHueco == true){
                cout << "Has seleccionado el algoritmo del primer hueco" << endl;
                algPrimerHueco(memoriaTotal, nombreImport, nombreExport);
            }
            else{
                cout << "Has seleccionado el algoritmo del mejor hueco" << endl;
                algMejorHueco(memoriaTotal, nombreImport, nombreExport);
            }
        }

        else if(error == 1)
            cout << "La memoria introducida por argumentos es negativa." << endl;

        else if(error == 2)
            cout << "El comando del algoritmo introducido es incorrecto." << endl;

        else
            cout << "Han surgido errores imprevistos en los argumentos" << endl;
        
    }
    
    return 0;
}
