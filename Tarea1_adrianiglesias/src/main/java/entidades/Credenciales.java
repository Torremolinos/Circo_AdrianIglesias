package entidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import Service.PaisService;
import utils.Config;

public class Credenciales {

	static Config config = new Config();
	public static String ruta = config.getProperty("credenciales");

	private Long id;
	private String nombre;
	private String password;
	private Perfil perfil;

	public Credenciales() {
		super();
	}

	public Credenciales(Long id, String nombre, String password,
					Perfil perfil) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.perfil = perfil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

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
					ultimoId = Long.parseLong(partes[0]);
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
							"Introduce nombre de usuario (solo letras, sin espacios): ");
			usuario = sc.nextLine().trim().toLowerCase();
		} while (!usuario.matches("^[a-z]{3,}$"));

		do {
			System.out.print("Introduce contraseña (mínimo 4 caracteres): ");
			password = sc.nextLine().trim();
		} while (password.length() < 4);

		do {
			System.out.print("Introduce email válido: ");
			email = sc.nextLine().trim();
		} while (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"));

		do {
			System.out.print("Introduce nombre completo (solo letras): ");
			nombreCompleto = sc.nextLine().trim();
		} while (!nombreCompleto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$"));

		PaisService paisService = new PaisService();
		Map<String, String> paises = paisService.obtenerTodosLosPaises();
		if (paises.isEmpty()) {
			System.out.println(
							"No se han podido cargar los países. Intenta más tarde.");
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

		int opcionPerfil;
		do {
			System.out.println("Selecciona perfil:");
			System.out.println("1. ADMIN");
			System.out.println("2. COORDINACION");
			System.out.println("3. ARTISTA");
			System.out.print("Opción: ");
			opcionPerfil = sc.nextInt();
			sc.nextLine();
			try {
				opcionPerfil = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				opcionPerfil = -1;
			}
			switch (opcionPerfil) {
			case 1:
				perfil = Perfil.ADMIN;
				break;
			case 2:
				perfil = Perfil.COORDINACION;
				break;
			case 3:
				perfil = Perfil.ARTISTA;
				break;
			default:
				System.out.println("Opción no válida, prueba otra vez.");
				break;
			}
		} while (perfil == null);

		Credenciales nueva = new Credenciales(null, usuario, password, perfil);

		registrarUsuario(nueva, email, nombreCompleto, pais);

		return nueva;
	}

	public static void registrarUsuario(Credenciales nuevo, String email,
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
		} catch (IOException e) {
			System.out.println(
							"❌ Error al registrar usuario: " + e.getMessage());
		}
	}

}