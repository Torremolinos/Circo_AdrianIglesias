/**
* Clase CredencialesService.java
*
* @author ADRIAN IGLESIAS RIÑO
* @version 1.0
*/

package Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.Map;
import java.util.Scanner;

import entidades.Credenciales;
import entidades.Especialidad;
import entidades.Perfil;
import utils.Config;
import utils.Utilidades;

public class CredencialesService {

	static Config config = new Config();
	public static String ruta = config.getProperty("credenciales");
	/* Para un service, o clases Dao. */

	public void leerCredenciales() {

		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;

			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split("\\|");
				String id = partes[0];
				String usuario = partes[1];
				String password = partes[2];
				String email = partes[3];
				String nombre = partes[4];
				String pais = partes[5];
				String rol = partes[6];

				System.out.println("Usuario: " + usuario + " (" + email + ")");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Credenciales buscarPorUsuarioYPassword(String usuarioBuscado,
			String passwordBuscada) {

		String adminUser = config.getProperty("useradmin");
		String adminPass = config.getProperty("passadmin");

		if (usuarioBuscado.equals(adminUser)
				&& passwordBuscada.equals(adminPass)) {
			return new Credenciales(0L, adminUser, adminPass, Perfil.ADMIN);
		}

		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;

			while ((linea = br.readLine()) != null) {
				if (linea.trim().isEmpty())
					continue;

				String[] partes = linea.split("\\|");
				if (partes.length < 7)
					continue;
				String usuario = partes[1];
				String password = partes[2];
				String rol = partes[6].trim().toUpperCase();

				if (usuario.equals(usuarioBuscado)
						&& password.equals(passwordBuscada)) {
					Long id = Long.parseLong(partes[0]);
					Perfil perfil;
					try {
						perfil = Perfil.valueOf(rol);
					} catch (IllegalArgumentException e) {
						perfil = Perfil.INVITADO;
					}
					return new Credenciales(id, usuario, password, perfil);
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer el fichero de credenciales: "
					+ e.getMessage());
		}

		return null;
	}

	private static long obtenerUltimoId(String ruta) {
		long ultimoId = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.trim().isEmpty())
					continue;
				String[] partes = linea.split("\\|");
				if (partes.length > 0) {
					try {
						ultimoId = Long.parseLong(partes[0]);
					} catch (NumberFormatException e) {
						System.out.println(
								"⚠ Se encontró un ID con formato inválido en el fichero. Se omitirá esa línea.");
						continue;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error leyendo IDs: " + e.getMessage());
		}
		return ultimoId;
	}

	public static Credenciales crearNuevaCredencial() {
		Scanner sc = new Scanner(System.in);
		String usuario, password, email, nombreCompleto, pais;
		Perfil perfil = null;

		do {
			System.out.print(
					"Introduce nombre de usuario (solo letras, sin espacios, mínimo 3): ");
			usuario = sc.nextLine().trim().toLowerCase();

			if (usuario.isEmpty()) {
				System.out.println(
						"❌ El nombre de usuario no puede estar vacío.");
				continue;
			}

			if (!usuario.matches("^[a-z]{3,}$")) {
				System.out.println(
						"❌ El usuario debe tener al menos 3 letras y no puede contener espacios ni acentos.");
				continue;
			}

			if (existeUsuarioOEmail(usuario, "")) {
				System.out.println(
						"❌ El nombre de usuario ya existe en el sistema.");
				continue;
			}

			break;
		} while (true);

		do {
			System.out.print(
					"Introduce contraseña (mínimo 4 caracteres, sin espacios): ");
			password = sc.nextLine().trim();

			if (password.isEmpty()) {
				System.out.println("❌ La contraseña no puede estar vacía.");
				continue;
			}

			if (password.contains(" ")) {
				System.out
						.println("❌ La contraseña no puede contener espacios.");
				continue;
			}

			if (password.length() < 4) {
				System.out.println(
						"❌ La contraseña debe tener al menos 4 caracteres.");
				continue;
			}

			break;
		} while (true);
		do {
			System.out.print("Introduce email válido: ");
			email = sc.nextLine().trim();

			if (email.isEmpty()) {
				System.out.println("❌ El email no puede estar vacío.");
				continue;
			}

			if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
				System.out.println(
						"❌ El formato de email no es válido (ejemplo: nombre@dominio.com).");
				continue;
			}

			if (existeUsuarioOEmail("", email)) {
				System.out.println("❌ El email ya existe en el sistema.");
				continue;
			}

			break;
		} while (true);

		do {
			System.out.print(
					"Introduce nombre completo (solo letras y espacios): ");
			nombreCompleto = sc.nextLine().trim();

			if (nombreCompleto.isEmpty()) {
				System.out.println("❌ El nombre no puede estar vacío.");
				continue;
			}

			if (!nombreCompleto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
				System.out.println(
						"❌ El nombre solo puede contener letras y espacios.");
				continue;
			}

			break;
		} while (true);

		if (existeUsuarioOEmail(usuario, email)) {
			System.out.println(
					"❌ Ya existe un usuario o email igual en el sistema.");
			return null;
		}

		PaisService paisService = new PaisService();
		Map<String, String> paises = paisService.obtenerTodosLosPaises();
		if (paises == null || paises.isEmpty()) {
			System.out.println(
					"❌ No se pudieron cargar los países desde el XML.");
			return null;
		}

		System.out.println("Países disponibles:");
		paises.forEach((codigo, nombre) -> System.out
				.println(codigo + " - " + nombre));

		String codigoPais = "";

		do {
			System.out.print("Introduce el código del país: ");
			codigoPais = sc.nextLine().trim().toUpperCase();
			if (!paises.containsKey(codigoPais)) {
				System.out.println("Código inválido, prueba otra vez.");
			}
		} while (!paises.containsKey(codigoPais));

		pais = paises.get(codigoPais);
		System.out.println("Has seleccionado: " + pais);

		int opcionPerfil = -1;
		do {
			System.out.println("Selecciona perfil:");
			System.out.println("1. COORDINACION");
			System.out.println("2. ARTISTA");
			System.out.print("Opción: ");

			String perfilElegido = sc.nextLine().trim();
			try {
				opcionPerfil = Integer.parseInt(perfilElegido);
			} catch (NumberFormatException e) {
				opcionPerfil = -1;
			}

			switch (opcionPerfil) {
			case 1:
				perfil = Perfil.COORDINACION;
				System.out.println("¿Es senior?");
				boolean esSenior = Utilidades.leerBoolean();
				Date fechaSenior = null;
				if (esSenior) {
					fechaSenior = Utilidades.leerFecha();
				}
				break;

			case 2:
				perfil = Perfil.ARTISTA;
				Especialidad especialidadSeleccionada = null;
				boolean especialidadValida = false;

				while (!especialidadValida) {
					System.out.println("Elige una especialidad:");
					System.out.println("1. ACROBACIA");
					System.out.println("2. HUMOR");
					System.out.println("3. MAGIA");
					System.out.println("4. EQUILIBRISMO");
					System.out.println("5. MALABARISMO");
					System.out.print("Opción: ");

					int opcion = -1;
					try {
					    opcion = Integer.parseInt(sc.nextLine().trim());
					} catch (NumberFormatException e) {
					    System.out.println("❌ Debes introducir un número válido (1–5).");
					    continue;
					}

					switch (opcion) {
					case 1:
						especialidadSeleccionada = Especialidad.ACROBACIA;
						especialidadValida = true;
						break;
					case 2:
						especialidadSeleccionada = Especialidad.HUMOR;
						especialidadValida = true;
						break;
					case 3:
						especialidadSeleccionada = Especialidad.MAGIA;
						especialidadValida = true;
						break;
					case 4:
						especialidadSeleccionada = Especialidad.EQUILIBRISMO;
						especialidadValida = true;
						break;
					case 5:
						especialidadSeleccionada = Especialidad.MALABARISMO;
						especialidadValida = true;
						break;
					default:
						System.out.println(
								"❌ Opción inválida. Intenta de nuevo.");
						break;
					}
				}
				break;
			default:
				System.out.println("❌ Opción no válida, prueba otra vez.");
				break;
			}

		} while (opcionPerfil < 1 || opcionPerfil > 2);

		Credenciales nueva = new Credenciales(null, usuario, password, perfil);

		registrarUsuario(nueva, email, nombreCompleto, pais);

		return nueva;
	}

	private static void registrarUsuario(Credenciales nuevo, String email,
			String nombreCompleto, String pais) {
		long nuevoId = obtenerUltimoId(ruta) + 1;
		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter(ruta, true))) {
			String linea = String.format("%d|%s|%s|%s|%s|%s|%s", nuevoId,
					nuevo.getNombre(), nuevo.getPassword(), email,
					nombreCompleto, pais,
					nuevo.getPerfil().name().toLowerCase());
			bw.newLine();
			bw.write(linea);
			System.out.println("✅ Usuario registrado con éxito.");

		} catch (FileNotFoundException e) {
			System.out.println(
					"❌ No se encontró el fichero de credenciales: " + ruta);
		} catch (IOException e) {
			System.out
					.println("❌ Error al registrar usuario: " + e.getMessage());
		}
	}

	
	private static boolean existeUsuarioOEmail(String usuario, String email) {
		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.trim().isEmpty())
					continue;
				String[] partes = linea.split("\\|");
				if (partes.length >= 4) {
					String usuarioExistente = partes[1].trim().toLowerCase();
					String emailExistente = partes[3].trim().toLowerCase();

					if (usuarioExistente.equals(usuario.toLowerCase())
							|| emailExistente.equals(email.toLowerCase())) {
						return true;
					}
				}
			}
		} catch (IOException e) {
			System.out.println(
					"❌ Error al leer credenciales.txt: " + e.getMessage());
		}
		return false;
	}
}
