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

	}

}
