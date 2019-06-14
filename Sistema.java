package tpe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Sistema {

	private static final String path = "C:/Users/Carlos Noel/eclipseworkspace/TPEspecial/src/tpe/Datasets/";

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		GrafoVuelos grafo = new GrafoVuelos();
		ArrayList<Aeropuerto> aeropuertos = new ArrayList<>();
		aeropuertos = cargarAeropuertos();
		aeropuertos = cargarVuelos(aeropuertos);
		grafo.setAeropuertos(aeropuertos);

		int opcion = 0;
		//INICIO DE LOS SERVICIOS
		while(opcion != 10) {
			try {
				BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
				System.out.println ("Elija una opcion (entre 1 y 5) : \r\n"+ "1) Listar todos los aeropuertos.\n" +	"2) Listar todas las reservas.\n" + "3) Servicio 1: Verificar vuelo directo.\n" + 	"4) Servicio 2: Obtener vuelos sin aerolínea.\n" + "5) Servicio 3: Vuelos disponibles. \n");
				System.out.println("Para Salir ingrese numero 10.");
				opcion = new Integer(entrada.readLine());
				switch (opcion) {
				case 1:
					grafo.getAeropuertos();
					break;
				case 2:
					grafo.getReservas();
					break;
				case 3:
					System.out.println("Ingrese nombre de aeropurto origen : ");
					String aeropOrigen = entrada.readLine();
					System.out.println("Ingrese nombre de aeropuerto destino :");
					String aeropDestino = entrada.readLine();
					System.out.println("Ingrese nombre de aerolinea :");
					String aerolinea = entrada.readLine();
					System.out.println(grafo.servicio1(aeropOrigen, aeropDestino, aerolinea));
					break;
				case 4:
					System.out.println("Ingrese nombre Aeropuerto Origen");
					aeropOrigen = entrada.readLine();
					System.out.println("Ingrese nombre Aeropuerto Destino");
					aeropDestino = entrada.readLine();
					System.out.println(grafo.servicio2(aeropOrigen,aeropDestino));
					break;
				case 5:
					System.out.println("Ingrese nombre Pais Origen");
					String paisOrigen = entrada.readLine();
					System.out.println("Ingrese nombre Pais Destino");
					String paisDestino = entrada.readLine();
					System.out.println(grafo.Servicio3(paisOrigen, paisDestino));
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
					ArrayList<Vuelo> vuelos = cargarVueloAeropuerto(aeropOrigen, aeropDestino, Double.parseDouble(kilometros), vuelo);
					aeropOrigen.setVuelos(vuelos);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aeropuertos;
	}


	public static ArrayList<Vuelo> cargarVueloAeropuerto(Aeropuerto aeropOrigen, Aeropuerto aeropDestino, Double km, String vuelo){
		ArrayList<Vuelo> resultado = new ArrayList<Vuelo>();
		String arregloString = vuelo.replace("{", "");
		arregloString = arregloString.replace("}", "");
		String[] string = arregloString.split(",");

		for (String s : string) {
			String[] datos = s.split("-");
			resultado.add(new Vuelo(datos[0], Integer.parseInt(datos[1]),km));
		}
		return resultado;
	}















	//
	//	public static ArrayList<Reserva> cargarReservas(ArrayList<Aeropuerto> aeropuertos) {
	//		ArrayList<Reserva> salida = new ArrayList<Reserva>();
	//		String csvFile = path+"Reservas.csv";
	//		String line = "";
	//		String cvsSplitBy = ";";
	//
	//		try (BufferedReader entrada = new BufferedReader(new FileReader(csvFile))) {
	//			while ((line = entrada.readLine()) != null) {
	//				String[] items = line.split(cvsSplitBy);
	//				String aeropOrigen = items[0];
	//				String aeropDestino = items[1];
	//				String aerolinea = items[2];
	//				String asientosreservados = items[3];
	//				Aeropuerto aOrigen = new Aeropuerto();
	//				Aeropuerto aDestino = new Aeropuerto();
	//				for (Aeropuerto aeropuerto : aeropuertos) {
	//					if (aeropuerto.getNombre().equals(aeropOrigen)) {
	//						aOrigen = aeropuerto;
	//					}else if (aeropuerto.getNombre().equals(aeropDestino)) {
	//						aDestino = aeropuerto;
	//					}
	//				}
	//				if(aOrigen != null && aDestino != null) {
	//					aOrigen.setReserva(new Reserva(aOrigen, aDestino, aerolinea, Integer.parseInt(asientosreservados)));
	//				}
	//			}
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//		return salida;
	//	}
	//	
	//	
	//	public static ArrayList<Vuelo> cargarReservaAeropuerto(Aeropuerto aeropOrigen, Aeropuerto aeropDestino, Double km, String vuelo){
	//		ArrayList<Vuelo> resultado = new ArrayList<Vuelo>();
	//		String arregloString = vuelo.replace("{", "");
	//		arregloString = arregloString.replace("}", "");
	//		String[] string = arregloString.split(",");
	//
	//		for (String s : string) {
	//			String[] datos = s.split("-");
	//			resultado.add(new Vuelo(datos[0], Integer.parseInt(datos[1]),km));
	//		}
	//		return resultado;
	//	}
	//	





}

