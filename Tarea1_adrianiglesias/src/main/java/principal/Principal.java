package principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Service.EspectaculoService;
import Service.MenuService;
import entidades.Credenciales;
import entidades.Espectaculo;
import entidades.Perfil;
import entidades.Persona;
import entidades.Sesion;
import utils.Config;

public class Principal {

	public static void main(String[] args) {

		String nombre = "Invitado";
		Perfil perfil = Perfil.INVITADO;
		Sesion sesionActiva = new Sesion(nombre, perfil);
		MenuService vistaMenu = new MenuService(sesionActiva);
		System.out.println("Aqui la sesion activa del usuario : "
				+ sesionActiva.getPerfil());
		vistaMenu.iniciarPrograma(sesionActiva);
	}

}
