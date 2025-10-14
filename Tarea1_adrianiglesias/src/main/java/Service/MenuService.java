package Service;

import java.util.Scanner;

import entidades.Perfil;
import entidades.Sesion;

public class MenuService {

	private Sesion sesion;

	public MenuService(Sesion sesion) {
		this.sesion = sesion;
	}

	private void menuInvitado(Perfil perfil) {

		boolean comprobador = false;
		Scanner usuario = new Scanner(System.in);
		int eleccion = 0;
		if (perfil != Perfil.INVITADO || perfil == null) {
			System.out.println("Este menu es para invitados");
		}

		/*
		 * estoy pensando en meter los mensajes en ArrayList... pero no estoy seguro
		 * cuanto de bueno es y que escalable puede ser...
		 */
		do {
			System.out.println("Te damos la bienvenida a nuestro Circo");
			System.out.println("Tienes que elegir una de las opciones para continuar : ");
			System.out.println("1.Registrarse");
			System.out.println("2.Ver espectaculos");
			System.out.println("3.Salir");

			eleccion = usuario.nextInt();

			switch (eleccion) {
			case 1:
				/*
				 * le registro con un metodo en... login? necesito una clase login? en sesion???
				 * como abstraigo esto?
				 */
				break;

			case 2:

				break;

			case 3:

				break;
			default:

				break;
			}
		} while (comprobador);

	}

	private void menuAdmin() {
		/*P*/
		/*Aqui pasamos perfil? y lo comparo con admin de mis propiedades?*/

	}

	private void menuUsuario() {
		
		/* menu usuario? Coordinador? Artista? quizas un switch como hace Luis
		 * Para cada menu?
		 * entonces deberia hacer 2?
		 *  */ }
}
