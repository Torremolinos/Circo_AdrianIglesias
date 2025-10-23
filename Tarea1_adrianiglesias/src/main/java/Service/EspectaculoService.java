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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import entidades.Coordinacion;
import entidades.Espectaculo;
import entidades.Perfil;
import utils.Config;
import utils.Utilidades;

public class EspectaculoService {

	static Config config = new Config();
	public static String RUTA = config.getProperty("espectaculos");
	public static String RUTAcredenciales = config.getProperty("credenciales");

	
	
	/**
	 * Aqui usamos ObjectOutputStream para escribir en un flujo de salida,
	 * serializando el objeto, lo usariamos para guardar esos bytes en un 
	 * archivo en este caso espectaculos.dat
	 * @param espectaculos
	 */
	public void guardarEspectaculos(List<Espectaculo> espectaculos) {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(RUTA))) {
			oos.writeObject(espectaculos);
			System.out.println("Espectáculos guardados" + " correctamente.");
			System.out.println("---------------------------");
		} catch (IOException e) {
			System.out.println(
					"Error al guardar los espectáculos: " + e.getMessage());
		}
	}

	/**
	 * Aqui usamos ObjectInputStream para leer del fichero.dat los espectaculos
	 * que hemos almacenado. En este metodo retornamos un LinkedHashSet
	 * de los espectaculos.
	 * @return
	 */
	public static LinkedHashSet<Espectaculo> listaEspectaculos() {
		 File file = new File(RUTA);

		    if (!file.exists() || file.length() == 0)
		        return new LinkedHashSet<>();

		    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
		        Object obj = ois.readObject();
		        if (obj instanceof LinkedHashSet) {
		            return (LinkedHashSet<Espectaculo>) obj;
		        } else if (obj instanceof Set) {
		            return new LinkedHashSet<>((Set<Espectaculo>) obj);
		        } else {
		            return new LinkedHashSet<>();
		        }
		    } catch (IOException | ClassNotFoundException e) {
		        System.err.println("Error al leer espectáculos: " + e.getMessage());
		        return new LinkedHashSet<>();
		    }
	}

	/**
	 * Aqui mostramos el menu de los espectaculos
	 * @param perfil
	 */
	public void mostrarInformeBasico(Perfil perfil) {
		LinkedHashSet<Espectaculo> espectaculos = listaEspectaculos();
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
				System.out.println("Periodo desde: " + e.getFechaini() + " hasta "
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
				System.out.println("Periodo desde: " + e.getFechaini() + " hasta "
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
				System.out.println("Periodo desde: " + e.getFechaini() + " hasta "
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
				System.out.println("Periodo desde: " + e.getFechaini() + " hasta "
						+ e.getFechafin());
				System.out.println();
			}
			break;
		default:
			break;
		}

	}

	/***
	 * Creamos espectaculos pidiendo el perfil del usuario como parametro.
	 * @param perfilUsuario
	 */
	public static void crearEspectaculo(Perfil perfilUsuario) {
		Coordinacion usuarioCoord = new Coordinacion();
		Scanner sc = new Scanner(System.in);

		HashSet<Espectaculo> existentes = listaEspectaculos();

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
			boolean repetido = existentes.stream()
					.anyMatch(e -> e.getNombre().equalsIgnoreCase(nombre));
			if (repetido) {
				System.out
						.println("❌ Ya existe un espectáculo con ese nombre.");
				continue;
			}
			nombreValido = nombre;
			break;
		}

		System.out.println("Introduce la fecha de inicio:");
		LocalDate inicio = Utilidades.leerFechasLocalDate();

		System.out.println("Introduce la fecha de fin:");
		LocalDate fin = Utilidades.leerFechasLocalDate();

		if (fin.isBefore(inicio)) {
			System.out.println(
					"❌ La fecha de fin debe ser posterior a la de inicio.");
			return;
		}
		if (inicio.plusYears(1).isBefore(fin)) {
			System.out.println("❌ El periodo no puede superar 1 año.");
			return;
		}

		Coordinacion coord = null;
		if (perfilUsuario == Perfil.COORDINACION) {
			coord = usuarioCoord;
		} else if (perfilUsuario == Perfil.ADMIN) {

			coord = seleccionarCoordinador();

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
			System.out.println(
					"❌ Error al guardar espectáculo: " + e.getMessage());
		}
	}

	/**
	 * Aqui guardamos el espectaculo en nuestro fichero.dat
	 * @param nuevo
	 */
	private static void guardarEspectaculo(Espectaculo nuevo) {
		HashSet<Espectaculo> existentes = listaEspectaculos();
		existentes.add(nuevo);

		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(RUTA))) {
			oos.writeObject(existentes);
		} catch (IOException e) {
			System.out.println(
					"❌ Error al guardar espectáculo: " + e.getMessage());
		}
	}


	private static long generarNuevoId() {
		
		Set<Espectaculo> existentes = listaEspectaculos();

		return existentes.isEmpty() ? 1
				: existentes.stream().mapToLong(Espectaculo::getId).max()
						.orElse(0) + 1;
	}

	public static Coordinacion seleccionarCoordinador() {
		Scanner in = new Scanner(System.in);
		List<Coordinacion> coordinadores = new ArrayList<>();

		try {
			BufferedReader lectura = new BufferedReader(new FileReader(RUTAcredenciales));
			String linea;
			while ((linea = lectura.readLine()) != null) {
				String[] parte = linea.split("\\|");
				if (parte.length == 7
						&& parte[6].equalsIgnoreCase("coordinacion")) {
					Coordinacion coordinador = new Coordinacion();
					coordinador.setId((long) Integer.parseInt(parte[0])+1);
					coordinador.setNombre(parte[4]);
					coordinador.setNacionalidad(parte[5]);
					coordinadores.add(coordinador);
				}
			}
		} catch (Exception e) {
			System.out
					.println("❌ Error al leer credenciales: " + e.getMessage());
			return null;
		}
		if (coordinadores.isEmpty()) {
			System.out.println("⚠ No hay coordinadores registrados.");
			return null;
		}
		System.out.println("=== Selecciona un coordinador ===");
		for (int i = 0; i < coordinadores.size(); i++) {
			Coordinacion listaCordinadoresRegistrados = coordinadores.get(i);
			System.out.printf("%d → %s (%s)%n", (i+1),
					listaCordinadoresRegistrados.getNombre(),
					listaCordinadoresRegistrados.getNacionalidad());
		}

		int eleccion = -1;

		while (true) {
			System.out.println("Introduce el número del coordinador");
			try {
				eleccion = Integer.parseInt(in.nextLine().trim());
				if (eleccion >= 0 && eleccion < coordinadores.size()) {
					break;
				} else {
					System.out.println("Opcion no valida, intentalo de nuevo.");
				}
			} catch (NumberFormatException e) {
				System.out.println("❌ Debes introducir un número válido.");
			}
		}
		Coordinacion elegido = coordinadores.get(eleccion);
		System.out.println("✅ Has seleccionado a: " + elegido.getNombre());
		return elegido;
	}

	public static void modificarEspectaculo(Perfil perfilUsuario) {
		Scanner sc = new Scanner(System.in);
		HashSet<Espectaculo> espectaculos = listaEspectaculos();

		if (espectaculos.isEmpty()) {
			System.out.println(
					"⚠ No hay espectáculos registrados para modificar.");
			return;
		}

		System.out.println("=== LISTA DE ESPECTÁCULOS ===");
		for (Espectaculo e : espectaculos) {
			System.out.println("ID: " + e.getId() + " → " + e.getNombre() + " ("
					+ e.getFechaini() + " → " + e.getFechafin() + ")");
		}

		System.out.print(
				"Introduce el ID del espectáculo que deseas modificar: ");
		long idBuscado = Long.parseLong(sc.nextLine().trim());

		Espectaculo seleccionado = null;
		for (Espectaculo e : espectaculos) {
			if (e.getId() == idBuscado) {
				seleccionado = e;
				break;
			}
		}

		if (seleccionado == null) {
			System.out.println("❌ No existe ningún espectáculo con ese ID.");
			return;
		}

		System.out.println("\n=== Modificando espectáculo ===");
		System.out.println("Nombre actual: " + seleccionado.getNombre());
		System.out.println("Inicio actual: " + seleccionado.getFechaini());
		System.out.println("Fin actual: " + seleccionado.getFechafin());
		System.out.println(
				"Coordinador actual: " + (seleccionado.getCoordinacion() != null
						? seleccionado.getCoordinacion().getNombre()
						: "Sin asignar"));

		System.out.print("Introduce el nuevo nombre (Enter para mantener): ");
		String nuevoNombre = sc.nextLine().trim();
		if (!nuevoNombre.isEmpty()) {
			if (nuevoNombre.length() > 25) {
				System.out
						.println("❌ El nombre no puede superar 25 caracteres.");
			} else {
				seleccionado.setNombre(nuevoNombre);
			}
		}

		System.out.println("Introduce la nueva fecha de inicio:");
		LocalDate nuevaInicio = Utilidades.leerFechasLocalDate();

		System.out.println("Introduce la nueva fecha de fin:");
		LocalDate nuevaFin = Utilidades.leerFechasLocalDate();

		if (nuevaFin.isBefore(nuevaInicio)) {
			System.out.println(
					"❌ La fecha de fin debe ser posterior a la de inicio.");
			return;
		}

		seleccionado.setFechaini(nuevaInicio);
		seleccionado.setFechafin(nuevaFin);

		if (perfilUsuario == Perfil.ADMIN) {
			System.out.print("¿Deseas cambiar el coordinador? (S/N): ");
			String resp = sc.nextLine().trim().toUpperCase();

			if (resp.equals("S")) {
				Coordinacion nuevoCoord = seleccionarCoordinador();
				if (nuevoCoord != null) {
					seleccionado.setCoordinacion(nuevoCoord);
				} else {
					System.out.println("⚠ No se cambió el coordinador.");
				}
			}
		}

		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(RUTA))) {
			oos.writeObject(espectaculos);
			System.out.println("✅ Espectáculo modificado correctamente.");
		} catch (IOException e) {
			System.out.println(
					"❌ Error al guardar los cambios: " + e.getMessage());
		}
	}
}