/**
* Clase EspectaculosService.java
*
* @author ADRIAN IGLESIAS RIÑO
* @version 1.0
*/

package Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entidades.Coordinacion;
import entidades.Espectaculo;
import entidades.Perfil;
import utils.Config;

public class EspectaculoService {

	static Config config = new Config();
	public static String RUTA = config.getProperty("espectaculos");

	public void guardarEspectaculos(List<Espectaculo> espectaculos) {
		try (ObjectOutputStream oos = new ObjectOutputStream(
						new FileOutputStream(RUTA))) {
			oos.writeObject(espectaculos);
			System.out.println("Espectáculos guardados" + " correctamente.");
			System.out.println("---------------------------");
		} catch (IOException e) {
			System.out.println("Error al guardar los espectáculos: "
							+ e.getMessage());
		}
	}

	public List<Espectaculo> listaEspectaculos() {
		File file = new File(RUTA);
		if (!file.exists()) {
			System.out.println("No hay espectáculos registrados" + "aún.");
			return new ArrayList<>();
		}
		/*
		 * Aqui devuelve un objetc asi que casteamos para que sepa que es un
		 * List<espectaculos, no se con que anotación o si esta bien para que no
		 * explote ni el sonarqube o el psalm, dar una vuelta a esto...
		 */
		/**
		 * @List<Espectaculo>
		 */
		try (ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(file))) {
			return (List<Espectaculo>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error al leer el fichero de" + "espectáculos: "
							+ e.getMessage());
			return new ArrayList<>();
		}
	}

	public void mostrarInformeBasico(Perfil perfil) {
		List<Espectaculo> espectaculos = listaEspectaculos();
		if (espectaculos.isEmpty()) {
			System.out.println("No hay espectáculos para mostrar.");
			return;
		}

		switch (perfil) {
		case INVITADO:
			System.out.println("INFORME DE ESPECTÁCULOS");
			System.out.println("---------------------------");
			for (Espectaculo e : espectaculos) {
				System.out.println("ID: " + e.getId());
				System.out.println("Nombre: " + e.getNombre());
				System.out.println("Periodo: " + e.getFechaini() + " → "
								+ e.getFechafin());
				System.out.println();
			}
			break;
		case ADMIN:
			System.out.println("INFORME DE ESPECTÁCULOS");
			System.out.println("---------------------------");
			for (Espectaculo e : espectaculos) {
				System.out.println("ID: " + e.getId());
				System.out.println("Nombre: " + e.getNombre());
				System.out.println("Periodo: " + e.getFechaini() + " → "
								+ e.getFechafin());
				System.out.println();
			}
			break;
		case ARTISTA:
			System.out.println("INFORME DE ESPECTÁCULOS");
			System.out.println("---------------------------");
			for (Espectaculo e : espectaculos) {
				System.out.println("ID: " + e.getId());
				System.out.println("Nombre: " + e.getNombre());
				System.out.println("Periodo: " + e.getFechaini() + " → "
								+ e.getFechafin());
				System.out.println();
			}
			break;
		case COORDINACION:
			System.out.println("INFORME DE ESPECTÁCULOS");
			System.out.println("---------------------------");
			for (Espectaculo e : espectaculos) {
				System.out.println("ID: " + e.getId());
				System.out.println("Nombre: " + e.getNombre());
				System.out.println("Periodo: " + e.getFechaini() + " → "
								+ e.getFechafin());
				System.out.println();
			}
			break;
		default:
			break;
		}

	}

	public static void crearEspectaculo(Perfil perfilUsuario) {
		Coordinacion usuarioCoord = new Coordinacion();
		Scanner sc = new Scanner(System.in);

		List<Espectaculo> existentes = leerEspectaculos();

		System.out.println("=== Creación de nuevo espectáculo ===");

		String nombreValido = null;
		while (true) {
			System.out.print(
							"Introduce el nombre del espectáculo (máx 25 caracteres): ");
			String nombre = sc.nextLine().trim();
			if (nombre.isEmpty() || nombre.length() > 25) {
				System.out.println(
								"❌ El nombre no puede estar vacío ni superar 25 caracteres.");
				continue;
			}
			boolean repetido = existentes.stream().anyMatch(
							e -> e.getNombre().equalsIgnoreCase(nombre));
			if (repetido) {
				System.out.println(
								"❌ Ya existe un espectáculo con ese nombre.");
				continue;
			}
			nombreValido = nombre;
			break;
		}

		LocalDate inicio, fin;
		while (true) {
			System.out.print("Fecha de inicio (YYYY-MM-DD): ");
			inicio = LocalDate.parse(sc.nextLine().trim());
			
			System.out.print("Fecha de fin (YYYY-MM-DD): ");
			fin = LocalDate.parse(sc.nextLine().trim());

			if (fin.isBefore(inicio)) {
				System.out.println(
								"❌ La fecha de fin debe ser posterior a la de inicio.");
				continue;
			}
			if (inicio.plusYears(1).isBefore(fin)) {
				System.out.println("❌ El periodo no puede superar 1 año.");
				continue;
			}
			break;
		}

		Coordinacion coord = null;
		if (perfilUsuario == Perfil.COORDINACION) {
			coord = usuarioCoord; 
		} else if (perfilUsuario == Perfil.ADMIN) {
			/*coord = seleccionarCoordinador(); hacer un metodo para buscar y 
			seleccionar coordinador.*/
		}

		Espectaculo nuevo = new Espectaculo();
		nuevo.setId(generarNuevoId());
		nuevo.setNombre(nombreValido);
		nuevo.setFechaini(inicio);
		nuevo.setFechafin(fin);
		nuevo.setCoordinacion(coord);

		existentes.add(nuevo);
		try (ObjectOutputStream oos = new ObjectOutputStream(
						new FileOutputStream(RUTA))) {
			oos.writeObject(existentes);
			System.out.println("✅ Espectáculo guardado con éxito.");
		} catch (IOException e) {
			System.out.println("❌ Error al guardar espectáculo: "
							+ e.getMessage());
		}
	}

	private static void guardarEspectaculo(Espectaculo nuevo) {
		List<Espectaculo> existentes = leerEspectaculos();
		existentes.add(nuevo);

		try (ObjectOutputStream oos = new ObjectOutputStream(
						new FileOutputStream(RUTA))) {
			oos.writeObject(existentes);
		} catch (IOException e) {
			System.out.println("❌ Error al guardar espectáculo: "
							+ e.getMessage());
		}
	}

	public static List<Espectaculo> leerEspectaculos() {
		File file = new File(RUTA);
		if (!file.exists())
			return new ArrayList<>();

		try (ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(file))) {
			return (List<Espectaculo>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			return new ArrayList<>();
		}
	}

	private static long generarNuevoId() {
		List<Espectaculo> existentes = leerEspectaculos();
		if (existentes.isEmpty())
			return 1;
		return existentes.get(existentes.size() - 1).getId() + 1;
	}

}