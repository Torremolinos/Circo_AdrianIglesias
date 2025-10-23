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
	
	/**
	 * Este metodo gestiona el menu invitado, nos muestras su menu con 
	 * las funciones accesibles.
	 * @return
	 */

	private boolean menuInvitado() {
		SesionActiva();
		EspectaculoService espectaculo = new EspectaculoService();
		boolean comprobador = true;
		Scanner usuario = new Scanner(System.in);
		String entrada;
		int eleccion = -1;

		do {
			System.out.println("Bienvenido " + sesion.getPerfil());
			System.out
					.println("🎪 Te damos la bienvenida a nuestro Circo 🎪 ");
			System.out.println(
					"Tienes que elegir una de las opciones para continuar : ");
			System.out.println("1.Iniciar sesión");
			System.out.println("2.Ver espectaculos");
			System.out.println("3.Salir");

			entrada = usuario.nextLine().trim();
			try {
				eleccion = Integer.parseInt(entrada);
			} catch (NumberFormatException e) {
				System.out.println("⚠️ Debes introducir un número");
				System.out.println();

				continue;
			}
			switch (eleccion) {
			case 1:
				System.out
						.println("Introduce tu nombre de usuario, por favor: ");
				String credencialUsuario = usuario.nextLine().trim();

				System.out.println("Introduce tu contraseña por favor: ");
				String credencalPassword = usuario.nextLine().trim();

				Credenciales credencialesUsuario = CredencialesService
						.buscarPorUsuarioYPassword(credencialUsuario,
								credencalPassword);

				if (credencialesUsuario != null) {
					System.out.println("✅ Inicio de sesión correcto.");
					sesion.iniciarSesion(credencialesUsuario.getNombre(),
							credencialesUsuario.getPerfil());

					iniciarPrograma(sesion);
					comprobador = false;
				} else {
					System.out.println(
							"❌ Usuario o contraseña incorrectos. Intentalo de nuevo.");
				}
				break;

			case 2:
				espectaculo.mostrarInformeBasico(sesion.getPerfil());
				break;

			case 3:
				System.out.println(
						"¡Gracias por tu visita esperamos verte pronto!");
				comprobador = false;
				return false;

			default:
				System.out.println(
						"La opcion marcada es incorrecta, por favor intentalo de nuevo.");
				break;
			}
		} while (comprobador);
		return false;
	}

	/**
	 * Este metodo gestiona el menuAdmin, dandonos acceso a las diferentes acciones o funcionalidades
	 * que puede acceder el admin.
	 * Tambien hay partes del menu en construccion a futuras mejoras.
	 * @return
	 */
	private boolean menuAdmin() {
		SesionActiva();
		EspectaculoService espectaculo = new EspectaculoService();
		Credenciales credenciales = new Credenciales();
		boolean comprobador = true;
		Scanner usuario = new Scanner(System.in);
		System.out.println("Bienvenido " + sesion.getPerfil());
		String entrada;
		int eleccion = -1;
		do {
			System.out.println("\n===(͠≖ ͜ʖ͠≖) MENÚ " + sesion.getPerfil()
					+ " (͠≖ ͜ʖ͠≖)===");
			System.out.println("🎪Bienvenido " + sesion.getNombre());
			System.out.println(
					"Tienes que elegir una de las opciones para continuar : ");
			System.out.println("1.Ver espectáculo");
			System.out.println("2.Registrar Usuarios");
			System.out.println("3.Crear espectáculos");
			System.out.println("4.Modificar espectáculos");
			System.out.println("5.Asignar perfil y credenciales");
			System.out
					.println("6.Gestionar datos de Artistas y de Coordinacion");
			System.out.println("7.Ver datos de espectáculo completo");
			System.out.println("8.Ver ficha");
			System.out.println("9.Log out");
			System.out.println("10.Salir");

			entrada = usuario.nextLine().trim();
			try {
				eleccion = Integer.parseInt(entrada);
			} catch (NumberFormatException e) {
				System.out.println(" ༼ ಠ 益 ಠ༽ Debes introducir un número");
				System.out.println();

				continue;
			}

			switch (eleccion) {
			case 1:
				espectaculo.mostrarInformeBasico(sesion.getPerfil());
				break;

			case 2:
				credenciales = CredencialesService.crearNuevaCredencial();
				break;

			case 3:
				System.out.println("Crear espectáculos");
				EspectaculoService.crearEspectaculo(sesion.getPerfil());
				break;

			case 4:
				System.out.println("Modificar espectáculos");
				EspectaculoService.modificarEspectaculo(sesion.getPerfil());
				break;
				
			case 5:
				System.out.println("En construcción aún no disponible");
				break;

			case 6:
				System.out.println("En construcción aún no disponible");
				break;

			case 7:
				System.out.println("En construcción aún no disponible");
				break;

			case 8:
				System.out.println("En construcción aún no disponible");
				break;

			case 9:
				System.out.println("Saliendo al menu principal...");
				sesion.cerrarSesion();
				this.iniciarPrograma(sesion);
				comprobador = false;
				return true;

			case 10:
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

	/**
	 * Este metodo nos permite acceder a las funcionalidades del menu y poder elegirlas
	 * correspondientemente.
	 * @return
	 */
	
	private boolean menuCoordinador() {
		SesionActiva();
	    EspectaculoService espectaculo = new EspectaculoService();
	    boolean comprobador = true;
	    Scanner sc = new Scanner(System.in);
	    String entrada;
	    int eleccion = -1;

	    do {
	        System.out.println("\n=== 🎪 MENÚ "+sesion.getPerfil()+" 🎪 ===");
	        System.out.println("Bienvenido/a, " + sesion.getNombre());
	        System.out.println("Elige una opción:");
	        System.out.println("1. Ver espectáculos");
	        System.out.println("2. Crear espectáculo");
	        System.out.println("3. Modificar espectáculo");
	        System.out.println("4. Cerrar sesión");
	        System.out.println("5. Salir del programa");

	        entrada = sc.nextLine().trim();
	        try {
	            eleccion = Integer.parseInt(entrada);
	        } catch (NumberFormatException e) {
	            System.out.println("⚠️ Debes introducir un número.");
	            continue;
	        }

	        switch (eleccion) {
	            case 1:
	                espectaculo.mostrarInformeBasico(sesion.getPerfil());
	                break;
	            case 2:
	                EspectaculoService.crearEspectaculo(sesion.getPerfil());
	                break;
	            case 3:
	                EspectaculoService.modificarEspectaculo(sesion.getPerfil());
	                break;
	            case 4:
	                System.out.println("Cerrando sesión...");
	                sesion.cerrarSesion();
	                this.iniciarPrograma(sesion);
	                comprobador = false;
	                break;
	            case 5:
	                System.out.println("👋 Saliendo del programa...");
	                comprobador = false;
	                return false;
	            default:
	                System.out.println("❌ Opción no válida. Intenta de nuevo.");
	        }
	    } while (comprobador);
	    return true;
	}
	
	/**
	 * Este metodo nos permite acceder al menu artista para gestionar sus 
	 * diferentes funciones.
	 * @return
	 */
	private boolean menuArtista() {
		SesionActiva();
	    boolean comprobador = true;
	    Scanner sc = new Scanner(System.in);
	    String entrada;
	    int eleccion = -1;

	    do {
	        System.out.println("\n=== 🎨 MENÚ " +sesion.getPerfil() +" 🎨 ===");
	        System.out.println("Bienvenido/a, " + sesion.getNombre());
	        System.out.println("Selecciona una opción:");
	        System.out.println("1. Ver espectáculos disponibles");
	        System.out.println("2. Ver mi ficha personal");
	        System.out.println("3. Cerrar sesión");
	        System.out.println("4. Salir del programa");

	        entrada = sc.nextLine().trim();
	        try {
	            eleccion = Integer.parseInt(entrada);
	        } catch (NumberFormatException e) {
	            System.out.println("⚠️ Debes introducir un número válido.");
	            continue;
	        }

	        switch (eleccion) {
	            case 1:
	                EspectaculoService espectaculo = new EspectaculoService();
	                espectaculo.mostrarInformeBasico(sesion.getPerfil());
	                break;
	            case 2:
	                System.out.println("=== Ver mi ficha personal ===");
	                System.out.println("(En construcción)");
	                break;
	            case 3:
	                System.out.println("Cerrando sesión...");
	                sesion.cerrarSesion();
	                this.iniciarPrograma(sesion);
	                comprobador = false;
	                break;
	            case 4:
	                System.out.println("👋 Saliendo del programa...");
	                comprobador = false;
	                return false;
	            default:
	                System.out.println("❌ Opción no válida. Intenta de nuevo.");
	        }
	    } while (comprobador);
	    return true;
	}

	/**
	 * Metodo que iniciar el programa con el menu correspondiente al perfil
	 * que se pase como atributo.
	 * @param perfil
	 * @return
	 */
	public boolean iniciarPrograma(Sesion perfil) {
		switch (perfil.getPerfil()) {
		case INVITADO:
			return menuInvitado();
		case ADMIN:
			return menuAdmin();
		case ARTISTA:
			return menuArtista();
		case COORDINACION:
			return menuCoordinador();
		default:
			System.out.println("Perfil no existente.");
			return true;
		}
	}
	/**
	 * Nos muestra el perfil de la sesion que tenemos actualmente activa
	 * @return
	 */
	public boolean SesionActiva() {
		System.out.println("Sesión activa: " + sesion.getPerfil());
	    if (this.sesion == null || this.sesion.getPerfil() == null) {
	        System.out.println("⚠️ No hay sesión activa. Por favor, inicia sesión primero.");
	        return false; 
	    }
	    return true; 
	}
}
