#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <ctime>
using namespace std;

const int n = 10000;

double rectaC(){

	//Creación y rellenado de los arrays
	int x[n];
	int y[n];

	srand(time(NULL));
	for (int i = 0; i < n; i++) {
		x[i] = rand() % 10;
		y[i] = rand() % 10;
	}
	
	//Declaracion de variables
	float mediaY, mediaX, covarianzaXY, varianzaX;
	//Inicializacion
	mediaX = mediaY = covarianzaXY = varianzaX = 0.0;

	int inicio = clock();

	//Cálculo de medias
	for (int i = 0; i < n; i++)	{
		//Sumatorio necesario para la mediaX
		mediaX += x[i];
		//Sumatorio necesario para la mediaY
		mediaY += y[i];
		//Sumatorio necesario para la varianzaX
		varianzaX += x[i] * x[i];
		//Sumatorio necesario para la covarianzaXY
		covarianzaXY += x[i] * y[i];
	}
	//Reajustamos las medias
	mediaX /= n;
	mediaY /= n;

	//Cálculo de varianzaX = (Sumatorio(x[i]^2) / n) - mediaX^2 
	varianzaX = (varianzaX / n) - (mediaX * mediaX);
	//Cálculo de la covarianzaXY = (Sumatorio(x[i]*y[i]) / n) - (mediaX * mediaY)
	covarianzaXY = (covarianzaXY / n) - (mediaX * mediaY);

	//FORMULA DE LA RECTA DE REGRESION: y - mediaY = (covarianzaXY / varianzaX)*(x - mediaX)
	//									y = mx + n
	float m = covarianzaXY/varianzaX;
	float b = ((covarianzaXY / varianzaX) * (-mediaX)) + mediaY;

	int fin = clock();
	return (double(fin - inicio) / ((clock_t)1000));
}


double rectaEnsamblador() {
	
	//Creación y rellenado de los arrays
	int coordX[n];
	int coordY[n];

	srand(time(NULL));
	for (int i = 0; i < n; i++) {
		coordX[i] = rand() % 10;
		coordY[i] = rand() % 10;
	}
	
	int sumX, sumY, sumXCuadrado, sumXY;
	sumX = sumY = sumXCuadrado = sumXY = 0;

	float auxiliar, mediaX, mediaY, varianzaX, covarianzaXY, m, b;
	auxiliar, mediaX, mediaY, varianzaX, covarianzaXY, m, b = 0.0;

	clock_t inicio = clock();
	__asm {
		
		inicializar:
			//Arrays de coordenadas
			mov esi, 0;

		bucle:
			//contador >= n -> terminar
			cmp esi, 10000;
			jae terminar;

			//mediaX += x[i];
			mov edx, [coordX + 4*esi];
			mov eax, sumX;
			add eax, edx;
			mov sumX, eax;

			//mediaY += y[i];
			mov ebx, [coordY + 4 * esi];
			mov eax, sumY;
			add eax, ebx;
			mov sumY, eax;

			//varianzaX += x[i] * x[i]
			mov eax, edx;
			mul edx;	//Despues de multiplicar edx se resetea a 0.
			mov ecx, sumXCuadrado;
			add eax, ecx;
			mov sumXCuadrado, eax;
			
			//covarianzaXY += x[i] * y[i]
			mov edx, [coordX + 4 * esi];
			mov eax, edx;
			mul ebx;
			mov ecx, sumXY;
			add eax, ecx;
			mov sumXY, eax;

			//Incrementamos el contador
			inc esi;

			//Siguiente iteracion, salto incondicional
			jmp bucle;

		terminar:
			//Coloca un numero entero (que pasa a float) en el tope de la pila
			fild sumX;
			//Divide STO (tope pila) pasando a float entre 'n'
			fidiv n;
			//Mueve el contenido de STO a una posicion de memoria
			fstp mediaX;

			fild sumY;
			fidiv n;
			fstp mediaY;

			//VARIANZA = (sumXCuadrado / n) - (mediaX*mediaX)
			fild sumXCuadrado;
			fidiv n;
			fstp varianzaX;

			//auxiliar = (mediaX*mediaX)
			fld mediaX;
			fmul mediaX;
			fstp auxiliar;

			fld varianzaX;
			fsub auxiliar;
			fstp varianzaX;

			//COVARIANZA = (sumXY / n) - (mediaX*mediaY)
			fild sumXY;
			fidiv n;
			fstp covarianzaXY;

			//auxiliar = (mediaX*mediaY)
			fld mediaX;
			fmul mediaY;
			fstp auxiliar;

			fld covarianzaXY;
			fsub auxiliar;
			fstp covarianzaXY;

			//m = covarianzaXY/varianzaX
			fld covarianzaXY;
			fdiv varianzaX;
			fstp m;

			//b = ((covarianzaXY / varianzaX)* (-mediaX)) + mediaY
			fld b;
			fsub mediaX;
			fmul covarianzaXY;
			fdiv varianzaX;
			fadd mediaY;
			fstp b;
	}
	
	
	clock_t fin = clock();

	return (double(fin - inicio) / ((clock_t)1000));
}

double rectaSSE() {
	int *coordX = new int[n];
	int *coordY = new int[n];

	srand(time(NULL));
	for (int i = 0; i < n; i++) {
		coordX[i] = rand() % 10;
		coordY[i] = rand() % 10;
	}

	

	int sumX, sumY, sumXCuadrado, sumXY;
	sumX = sumY = sumXCuadrado = sumXY = 0;

	float auxiliar, mediaX, mediaY, varianzaX, covarianzaXY, m, b;
	auxiliar = mediaX = mediaY = varianzaX = covarianzaXY = m = b = 0.0;

	int ultimaIter = n / 4;
	clock_t inicio = clock();
	__asm {
	inicializar:
			mov ebx, 0;
			mov edx, 0;
			mov ecx, 0;
			mov eax,0 ;
			mov esi, coordX;
			mov edi, coordY;

		bucle:
			cmp ecx, ultimaIter;
			jae terminar;
			
			//Registro sumX
			movdqu xmm0, [esi]; 
			paddd xmm2, xmm0;

			//Registro sumY
			movdqu xmm1, [edi];
			paddd xmm3, xmm1;

			//Registro sumXCuadrado
			pmulld xmm0, xmm0;
			paddd xmm4, xmm0;

			movdqu xmm0, [esi]; //REINICILIZAR
			//Registro sumXY
			pmulld xmm1, xmm0;
			paddd xmm5, xmm1;

			add esi, 16;
			add edi, 16;
			
			inc ecx;
			jmp bucle;

		terminar:
			//sumatorioX
			phaddd xmm2, xmm2;
			phaddd xmm2, xmm2;
			movdqu sumX, xmm2;
			mov ebx, sumX;

			//sumatorioY
			phaddd xmm3, xmm3;
			phaddd xmm3, xmm3;
			movdqu sumY, xmm3;
			mov edx, sumY;

			//SumatorioXCuadrado
			phaddd xmm4, xmm4;
			phaddd xmm4, xmm4;
			movdqu sumXCuadrado, xmm4;
			mov eax, sumXCuadrado;

			//SumatorioXY
			phaddd xmm5, xmm5;
			phaddd xmm5, xmm5;
			movdqu sumXY, xmm5;
			mov ecx, sumXY;

			mov sumX, ebx;
			mov sumY, edx;
			mov sumXCuadrado, eax;
			mov sumXY, ecx;

			//Coloca un numero entero (que pasa a float) en el tope de la pila
			fild sumX;
			//Divide STO (tope pila) pasando a float entre 'n'
			fidiv n;
			//Mueve el contenido de STO a una posicion de memoria
			fstp mediaX;

			fild sumY;
			fidiv n;
			fstp mediaY;

			//VARIANZA = (sumXCuadrado / n) - (mediaX*mediaX)
			fild sumXCuadrado;
			fidiv n;
			fstp varianzaX;

			//auxiliar = (mediaX*mediaX)
			fld mediaX;
			fmul mediaX;
			fstp auxiliar;

			fld varianzaX;
			fsub auxiliar;
			fstp varianzaX;

			//COVARIANZA = (sumXY / n) - (mediaX*mediaY)
			fild sumXY;
			fidiv n;
			fstp covarianzaXY;

			//auxiliar = (mediaX*mediaY)
			fld mediaX;
			fmul mediaY;
			fstp auxiliar;

			fld covarianzaXY;
			fsub auxiliar;
			fstp covarianzaXY;

			//m = covarianzaXY/varianzaX
			fld covarianzaXY;
			fdiv varianzaX;
			fstp m;

			//b = ((covarianzaXY / varianzaX)* (-mediaX)) + mediaY
			fld b;
			fsub mediaX;
			fmul covarianzaXY;
			fdiv varianzaX;
			fadd mediaY;
			fstp b;
	}
	clock_t fin = clock();

	return (double(fin - inicio) / ((clock_t)1000));
}

void impresionResultados(double c1000, double e1000, double s1000, double c3000, double e3000, double s3000) {
	cout << "> TABLA DE TIEMPOS OBTENIDA" << endl
		<< "\t| Tiempo C++\t| Tiempo Ensamblador | Tiempo SSE" << endl
		<< "--------|---------------|--------------------|------------" << endl
		<< "1000 it.| " << c1000 << "\t\t| " << e1000 << "\t\t     | " << s1000 << endl
		<< "--------|---------------|--------------------|------------" << endl
		<< "3000 it.| " << c3000 << "\t\t| " << e3000 << "\t\t     | " << s3000 << endl;
	
	cout << "##########################################################" << endl
		<< "CryEngine UA ©: " << endl
		<< "\t Joaquin Jose Cerdan Lopez (DIRECTOR)" << endl << "\t Josue Perea Martinez" << endl
		<< "\t Oscar Casado Lorenzo" << endl << "\t Jorge Vazquez Lopez" << endl
		<< "\t Diego Wenceslao Pacheco Guevara" << endl << "\t Joaquin Amat Perez" << endl
		<< "\t Jesus Plaza Ortiz" << endl;
}

int main() {

	cout << "##########################################################" << endl
		 << "BENCHMARCK REDUCIDO - Recta de regresion" << endl
		 << "##########################################################" << endl;

	double c1000 = 0;
	double e1000 = 0;
	double s1000 = 0;

	int porcentaje = 10;

	cout << "1a PRUEBA Nube aleatoria de puntos (1000 iteraciones)" << endl
		 << "EJECUCION: [";
	
	//BUCLE DE 1000
	for (int i = 1; i <= 1000; i++) {
		//Acumulamos los tiempos unitarios de cada iteración
		c1000 += rectaC();
		e1000 += rectaEnsamblador();
		s1000 += rectaSSE();

		if (porcentaje*1000/100 == i) {
			cout << "=";
			porcentaje += 10;
		}

	}
	cout << "]" << endl
		 << "----------------------------------------------------------" << endl
		 << "2a PRUEBA Nube aleatoria de puntos (3000 iteraciones)" << endl
		 << "EJECUCION: [";

	double c3000 = 0;
	double e3000 = 0;
	double s3000 = 0;

	porcentaje = 10;

	//BUCLE DE 3000
	for (int i = 1; i <= 3000; i++) {
		c3000 += rectaC();
		e3000 += rectaEnsamblador();
		s3000 += rectaSSE();

		if (porcentaje * 3000 / 100 == i) {
			cout << "=";
			porcentaje += 10;
		}
	}
	cout << "]" << endl;
	cout << "##########################################################" << endl;
	

	impresionResultados(c1000, e1000, s1000, c3000, e3000, s3000);
	//Mantener .exe abierto
	system("pause");

	return 0;
}

