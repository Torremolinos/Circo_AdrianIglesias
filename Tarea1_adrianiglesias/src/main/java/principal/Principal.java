/*
* Clase Principal.java
*
* @author Adrian Iglesias Riño
* @version 1.0
*/

package principal;

import Service.MenuService;
import entidades.Perfil;
import entidades.Sesion;

public class Principal {

	public static void main(String[] args) {

		Sesion sesionActiva = new Sesion("Invitado", Perfil.INVITADO);
		MenuService vistaMenu = new MenuService(sesionActiva);
		System.out.println("Sesión activa: " + sesionActiva.getPerfil());
		vistaMenu.iniciarPrograma(sesionActiva);

		/* CONTROLAR QUE SI METO ALGO QUE NO SEA UN INT EN LA ELECCION QUE NO ROMPA */

		boolean continuar = true;
		while (continuar) {
			vistaMenu.iniciarPrograma(sesionActiva);
			if (sesionActiva.getPerfil() == Perfil.INVITADO) {
				System.out.println("🔁 Volviendo al menú principal...\n");
			} else if (sesionActiva.getPerfil() == null) {
				continuar = false;
			}
		}

		System.out.println("🛑 Programa finalizado.");
		/*
		 * String nombre = "Invitado"; Perfil perfil = Perfil.INVITADO; Sesion
		 * sesionActiva = new Sesion(nombre, perfil); MenuService vistaMenu = new
		 * MenuService(sesionActiva);
		 * System.out.println("Aqui la sesion activa del usuario : " +
		 * sesionActiva.getPerfil()); vistaMenu.iniciarPrograma(sesionActiva);
		 */
	}

}
