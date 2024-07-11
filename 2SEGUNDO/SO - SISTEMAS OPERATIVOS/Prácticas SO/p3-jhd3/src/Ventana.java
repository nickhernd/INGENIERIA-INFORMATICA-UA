import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Ventana extends Frame implements ActionListener{
	private int instante = 0;
	private Canvas canvas;
	private Button next, salir;	
	private PrintWriter pw;
	private Label linstante;
	private Label lalgoritmo;
	public Ventana() {
		///////////////////////////////////
		// fichero donde escribir./////////
		try {
			pw = new PrintWriter("particiones.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		////////////////////////////////////
		this.setSize(1100,500);
		this.setLayout(new BorderLayout()); // añado a la ventana el contenedor.
		this.setTitle("mi poya gorda");
		canvas = new Canvas();	// zona donde dibujo
		Panel p = new Panel();	// panel donde coloco los botones
		next = new Button("Start");	// los dos botones
		salir = new Button("Exit");
		this.add(canvas, BorderLayout.CENTER); // 
		p.setLayout(new GridLayout(1,2));
		p.add(next);
		p.add(salir);
		Panel p2 = new Panel();
		p2.setLayout(new GridLayout(1,2));
		lalgoritmo = new Label();
		if(Principal.opcion == 1)
			lalgoritmo.setText("primer hueco");
		else
			lalgoritmo.setText("mejor hueco");
		p2.add(lalgoritmo);
		linstante = new Label("Instante: " + instante);
		p2.add(linstante);
		this.add(p2, BorderLayout.NORTH);
		this.add(p, BorderLayout.SOUTH);
		next.addActionListener(this);
		salir.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		linstante.setText("instante: " + instante);
		if(e.getSource() == salir){
			System.out.println("Se presiono salir");
			System.exit(0);
		}
		else if(e.getSource() == next){
			if(instante == 0){
				next.setLabel("Next");
				Graphics gr = canvas.getGraphics();
				//for(int i = 0; i < 200; i++){ // 2/10
				//	gr.drawRect(2*i, 6*instante, 3, 4);	
				//}
				gr.drawRect(0, 0, 1000, 10); // memoria/10 * 2
				instante = 1;
			}
			else{
				boolean insertado;
				pw.write(instante + " " + Principal.memoria.toString());
				// cada vez que le doy al next es un instante nuevo,
				// recorro todo lo que he leido del fichero y lo intento meter en la memoria.
				for(int i = 0; i < Principal.listaFichero.size(); i++){
					if(Principal.listaFichero.get(i).getInstanteLlegada() <= instante){
						// quitais el if..else y llamais mejorHueco
						if(Principal.opcion == 1){
							// insertar mejor hueco
							insertado = Principal.memoria.siguienteHueco(Principal.listaFichero.get(i));
						}
						else{
							// insertar primer hueco.
							insertado = Principal.memoria.peorHueco(Principal.listaFichero.get(i));
						}
						// si lo consigo meter, lo quito de la lista de entrada.
						if(insertado){
							// si lo consigo insertar lo quito de los elementos que he leido del fichero.
							Principal.listaFichero.remove(i);
							i--;
						}
					}
				}
				// quita de cada elemento que no sea un hueco, le quita uno al tiempo restante
				// de forma que si, algun proceso se queda en 0 de tiempo restante, lo convierte en hueco,
				// si tiene dos huecos seguidos los fusiona en 1.
				Principal.memoria.decrementar();
				//////////////////////
				System.out.println(Principal.memoria.toString());
				//////////////////////
				pw.write(Principal.memoria.toString());
				pw.write("\n");
				pw.flush();
				//////////////////////
				
				String mapa = Principal.memoria.toMapa();
				System.out.println(mapa);
				Graphics gr = canvas.getGraphics();
				
				ArrayList<ElementoMemoria> lista = Principal.memoria.getMemoria();
				
				Color [] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.orange, Color.RED,
						Color.BLACK, Color.YELLOW, Color.CYAN, Color.PINK};
				int columnaActual = 0;
				for(int i = 0; i < lista.size(); i++){
					if(lista.get(i).getPro() == 0){
						gr.setColor(Color.WHITE);
					}
					else{
						gr.setColor(colores[lista.get(i).getPro()]);
					}
					gr.fillRect(columnaActual, 10*instante, lista.get(i).getTam()/2, 10);
					columnaActual = columnaActual + lista.get(i).getTam()/2;
				}
				
				/*
				for(int i = 0; i < 200; i++){
					if(mapa.charAt(i) == '0'){
						gr.setColor(Color.BLACK);
						gr.drawRect(2*i, 6*instante, 3, 4);

//						gr.drawRect(20*i, 30*instante, 15, 20);
					}
					else{
						Color [] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.orange, Color.RED,
								Color.BLACK, Color.YELLOW, Color.CYAN, Color.PINK};
						gr.setColor(colores[mapa.charAt(i) - '0']);
						gr.fillRect(2*i, 6*instante, 3, 4);
//						gr.fillRect(20*i, 30*instante, 15, 20);
						gr.setColor(Color.BLACK);
						gr.drawRect(2*i, 6*instante, 3, 4);
//						gr.drawRect(20*i, 30*instante, 15, 20);
					}
				}*/
				instante++;
			}
		}
		else{
			System.out.println("Se presiono enter en el texto");
		}
	}
}

