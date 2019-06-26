import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	private static final String path = "C:/Users/Carlos Noel/eclipse-workspace/programacion3/tpe/tpe/Datasets/";

	public static void main(String[] args) {
		Grafo grafo = new Grafo();
		ArrayList<Aeropuerto> aeropuertos = new ArrayList<>();
		ArrayList<Reserva> reservas = new ArrayList<>();
		aeropuertos = cargarAeropuertos();
		aeropuertos = cargarVuelos(aeropuertos);
		grafo.setAeropuertos(aeropuertos);
		reservas = cargarReservas();
		grafo.setReservas(reservas);

		//INICIO DE LOS SERVICIOS
		int opcion = 0;
		while(opcion != 8) {
			try {
				BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
				System.out.println ("Elija una opcion (entre 1 y 8) : \n"+ "\t1)Listar todos los aeropuertos.\n" +	"\t2)Listar todas las reservas.\n"
				+ "\t3)Servicio 1: Verificar vuelo directo.\n" + 	"\t4)Servicio 2: Obtener vuelos sin aerolinea.\n" + "\t5)Servicio 3: Vuelos disponibles. \n" + 
				"\t6)Servicio 4: Algoritmo Greedy. \n" +  "\t7)Servicio 5: Backtracking. \n" +"\t8)Salir.");
				opcion = new Integer(entrada.readLine());
				switch (opcion) {
				case 1:
					grafo.listarAeropuertos();
					break;
				case 2:
					grafo.listarReservas();
					break;
				case 3:
					System.out.println("Ingrese aeropuerto origen : ");
					String aeropOrigen = entrada.readLine();
					System.out.println("Ingrese aeropuerto destino :");
					String aeropDestino = entrada.readLine();
					System.out.println("Ingrese aerolinea :");
					String aerolinea = entrada.readLine();
					System.out.println(grafo.servicio1(aeropOrigen, aeropDestino, aerolinea));
					break;
				case 4:
					System.out.println("Ingrese nombre Aeropuerto Origen :");
					aeropOrigen = entrada.readLine();
					System.out.println("Ingrese nombre Aeropuerto Destino :");
					aeropDestino = entrada.readLine();
					System.out.println(grafo.servicio2(aeropOrigen,aeropDestino));
					break;
				case 5:
					System.out.println("Ingrese nombre Pais Origen :");
					String paisOrigen = entrada.readLine();
					System.out.println("Ingrese nombre Pais Destino :");
					String paisDestino = entrada.readLine();
					System.out.println(grafo.servicio3(paisOrigen, paisDestino));
					break;
				case 6:
					System.out.println("Ingrese aeropuerto : ");
					String aeropuerto = entrada.readLine();
					System.out.println(grafo.getOrigenGreedy(aeropuerto));
					break;
				case 7:
					System.out.println("Ingrese aeropuerto : ");
					aeropuerto = entrada.readLine();
					System.out.println(grafo.backTracking(aeropuerto));
					break;
				}
			}
			catch (Exception exc ) {
				System.out.println( exc );
			}
		}

	}



	public static ArrayList<Aeropuerto> cargarAeropuertos() {
		ArrayList<Aeropuerto> aux = new ArrayList<Aeropuerto>();
		String csvFile = path+"Aeropuertos.csv";
		String line = "";
		String cvsSplitBy = ";";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				String[] items = line.split(cvsSplitBy);
				String nombre = items[0];
				String ciudad = items[1];
				String pais = items[2];

				aux.add(new Aeropuerto(nombre,ciudad, pais));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aux;
	}

	public static ArrayList<Aeropuerto> cargarVuelos(ArrayList<Aeropuerto> aeropuertos) {
		String csvFile = path+"Rutas.csv";
		String line = "";
		String cvsSplitBy = ";";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				String[] items = line.split(cvsSplitBy);
				String origen = items[0];
				String destino = items[1];
				String kilometros = items[2];
				String esCabotaje = items[3];
				String vuelo = items[4];

				Aeropuerto aeropOrigen = new Aeropuerto();
				Aeropuerto aeropDestino = new Aeropuerto();

				for (Aeropuerto aerop : aeropuertos) {
					if(aerop.getNombre().equals(origen)) {
						aeropOrigen = aerop;
					}else if(aerop.getNombre().equals(destino)) {
						aeropDestino = aerop;
					}
				}
				if(aeropOrigen != null && aeropDestino != null) {
					Ruta ruta = cargarVueloAeropuerto(aeropOrigen, aeropDestino, Double.parseDouble(kilometros), vuelo, Integer.parseInt(esCabotaje));
					Ruta vuelta = cargarVueloAeropuerto(aeropDestino, aeropOrigen, Double.parseDouble(kilometros), vuelo, Integer.parseInt(esCabotaje));
					aeropOrigen.setRuta(ruta);
					aeropDestino.setRuta(vuelta);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aeropuertos;
	}


	public static Ruta cargarVueloAeropuerto(Aeropuerto aeropOrigen, Aeropuerto aeropDestino, Double km, String vuelo, int esCabotaje){
		Ruta resultado = new Ruta(km, esCabotaje, aeropOrigen, aeropDestino);
		String arregloString = vuelo.replace("{", "");
		arregloString = arregloString.replace("}", "");
		String[] string = arregloString.split(",");
		for (String s : string) {
			String[] datos = s.split("-");
			resultado.setVuelo(datos[0], Integer.parseInt(datos[1]));
		}
		return resultado;
	}

	public static ArrayList<Reserva> cargarReservas(){
		ArrayList<Reserva> aux = new ArrayList<>();
		String csvFile = path+"Reservas.csv";
		String line = "";
		String cvsSplitBy = ";";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				String[] items = line.split(cvsSplitBy);
				String aeropOrigen = items[0];
				String aeropDestino = items[1];
				String aerolinea = items[2];
				String asientos = items[3];

				aux.add(new Reserva(aeropOrigen,aeropDestino, aerolinea, Integer.parseInt(asientos)));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aux;
	}

}

