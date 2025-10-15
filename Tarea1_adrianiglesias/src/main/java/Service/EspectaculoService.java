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

import entidades.Espectaculo;

public class EspectaculoService {

	String RUTA = "ficheros/espectaculos.dat";

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

	public void mostrarInformeBasico() {
		List<Espectaculo> espectaculos = listaEspectaculos();
		if (espectaculos.isEmpty()) {
			System.out.println("No hay espectáculos para mostrar.");
			return;
		}

		System.out.println("INFORME DE ESPECTÁCULOS");
		System.out.println("---------------------------");
		for (Espectaculo e : espectaculos) {
			System.out.println("ID: " + e.getId());
			System.out.println("Nombre: " + e.getNombre());
			System.out.println("Periodo: " + e.getFechaini() + " → "
							+ e.getFechafin());
			System.out.println();
		}
	}
}