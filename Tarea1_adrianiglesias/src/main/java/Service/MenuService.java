package Service;

import java.util.Scanner;

import entidades.Credenciales;
import entidades.Perfil;
import entidades.Sesion;

public class MenuService {

	private Sesion sesion;

	public MenuService(Sesion sesion) {
		this.sesion = sesion;
	}

	private void menuInvitado() {
		
		/*ROMPE SI METES LETRAS CAMBIAAAAAR*/
		EspectaculoService espectaculo = new EspectaculoService();
		boolean comprobador = true;
		Scanner usuario = new Scanner(System.in);
		int eleccion = 0;

		do {
			System.out.println(" 🎪 Te damos la bienvenida a nuestro Circo 🎪 ");
			System.out.println("Bienvenido " + sesion.getPerfil());
			System.out.println("Tienes que elegir una de las opciones para continuar : ");
			System.out.println("1.Iniciar sesión");
			System.out.println("2.Ver espectaculos");
			System.out.println("3.Salir");

			eleccion = usuario.nextInt();
			usuario.nextLine();
			switch (eleccion) {
			case 1:
				System.out.println("Introduce tu nombre de usuario, por favor: ");
				String credencialUsuario = usuario.nextLine().trim();

				System.out.println("Introduce tu contraseña por favor: ");
				String credencalPassword = usuario.nextLine().trim();

				Credenciales credencialesUsuario = Credenciales.buscarPorUsuarioYPassword(credencialUsuario,
						credencalPassword);

				if (credencialesUsuario != null) {
					System.out.println("✅ Inicio de sesión correcto. Bienvenido " + usuario + "!");
					sesion.iniciarSesion(credencialesUsuario.getNombre(), credencialesUsuario.getPerfil());

					iniciarPrograma(sesion);
					comprobador = false;
				} else {
					System.out.println("❌ Usuario o contraseña incorrectos. Intenta de nuevo.");
				}
				break;

			case 2:
				System.out.println("Espectáculos");
				espectaculo.mostrarInformeBasico(sesion.getPerfil());
				break;

			case 3:
				System.out.println("¡Gracias por tu visita esperamos verte pronto!");
				comprobador = false;
				break;

			default:
				System.out.println("La opcion marcada es incorrecta, por favor intentalo de nuevo.");
				break;
			}
		} while (comprobador);

	}

	private boolean menuAdmin() {
		EspectaculoService espectaculo = new EspectaculoService();
		Credenciales credenciales = new Credenciales();
		boolean comprobador = true;
		Scanner usuario = new Scanner(System.in);
		int eleccion = 0;
		do {
			System.out.println("\n=== MENÚ🧑‍💻 " + sesion.getPerfil() + " 🧑‍💻===");
			System.out.println("Bienvenido " + sesion.getPerfil());
			System.out.println("Tienes que elegir una de las opciones para continuar : ");
			System.out.println("1.Ver espectáculo");
			System.out.println("2.Registrar Usuarios");
			System.out.println("3.Asignar perfil y credenciales");
			System.out.println("4.Gestionar datos de Artistas y de Coordinacion");
			System.out.println("5.Ver datos de espectáculo completo");
			System.out.println("6.Ver ficha");
			System.out.println("7.Log out");
			System.out.println("8.Salir");

			eleccion = usuario.nextInt();

			switch (eleccion) {
			case 1:
				espectaculo.mostrarInformeBasico(sesion.getPerfil());
				break;

			case 2:
				credenciales = Credenciales.crearNuevaCredencial();
				if (credenciales != null) {
					System.out.println("✅ Usuario creado con éxito: " + credenciales.getNombre());
				} else {
					System.out.println("❌ No se pudo crear el usuario.");
				}
				break;

			case 3:
				System.out.println("En construcción aún no disponible");
				break;

			case 4:
				System.out.println("En construcción aún no disponible");
				break;

			case 5:
				System.out.println("En construcción aún no disponible");
				break;

			case 6:
				System.out.println("En construcción aún no disponible");
				break;

			case 7:
				System.out.println("Saliendo al menu principal...");
				sesion.setPerfil(Perfil.INVITADO);
				comprobador = false;
				return true;

			case 8:
				System.out.println("Hasta la proxima " + sesion.getPerfil());
				sesion.setPerfil(Perfil.INVITADO);
				comprobador = false;
				return false;

			default:

				break;
			}
		} while (comprobador);
		return true;
	}

	private boolean menuCoordinador() {
		System.out.println("Menú Coordinador (pendiente)");
		sesion.setPerfil(Perfil.INVITADO);
		return true;
	}

	private boolean menuArtista() {
		System.out.println("Menú Artista (pendiente)");
		sesion.setPerfil(Perfil.INVITADO);
		return true;
	}

	public void iniciarPrograma(Sesion perfil) {
		boolean continuador = true;
		switch (perfil.getPerfil()) {
		case INVITADO:
			menuInvitado();
			break;

		case ADMIN:
			menuAdmin();
			break;

		case ARTISTA:
			menuArtista();
			break;

		case COORDINACION:
			menuCoordinador();
			break;
		default:
			System.out.println("Perfil no registrado o inexistente.");
			continuador = false;
			break;
		}

	}
}
