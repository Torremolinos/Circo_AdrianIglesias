package principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Service.EspectaculoService;
import entidades.Credenciales;
import entidades.Espectaculo;
import entidades.Persona;
import entidades.Sesion;

public class Principal {

	public static void main(String[] args) {

		/*
		 * Vamos a tener que instanciar" todo antes y que vaya revisando que
		 * cosas necesito y si esta todo validado como deberia estar.
		 */

		/*
		 * Instanciamos sesion
		 * 
		 * validador boolean, validador sesion?
		 * 
		 * un loop hasta que el usuario quiera irse.
		 * 
		 * 
		 * menu
		 * 
		 * opciones 1.Login 2.Exit
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		/*
		 * Credenciales credenciales = new Credenciales();
		 * 
		 * credenciales.leerCredenciales();
		 */

		/*
		 * Persona persona = new Persona(); Persona persona2 = new Persona();
		 * 
		 * if (persona.compareTo(persona2)==0) {
		 * System.out.println(persona.compareTo(persona2)); }
		 */

		/*
		 * Sesion sesion = new Sesion();
		 * 
		 * System.out.println(sesion.getPerfilActual());
		 */
		
		/*mirar esto porque da un null y quiero arreglarlo para cazarlo bien.*/
		
		/*
		Credenciales usuario =null;
		
		try {
			 usuario = Credenciales.buscarPorUsuarioYPassword("caamila",
					"cam1las");
			
		} catch (NullPointerException e) {
			System.out.print("El usuario no existe");
		}
	
		System.out.println(usuario.getPerfil() + usuario.getNombre()
				+ usuario.getPassword());*/
		
		 EspectaculoService service = new EspectaculoService();

	        // 1️⃣ Crear unos espectáculos de prueba
	        List<Espectaculo> lista = new ArrayList<>();
	        lista.add(new Espectaculo(1L, "Circo de la Luna", LocalDate.of(2024, 3, 1), 		LocalDate.of(2024, 9, 1), null));
	        lista.add(new Espectaculo(2L, "Magia y Sonrisas", LocalDate.of(2025, 1, 10), 		LocalDate.of(2025, 6, 30), null));

	        // 2️⃣ Guardarlos
	        service.guardarEspectaculos(lista);

	        // 3️⃣ Leerlos e imprimir el informe
	        service.mostrarInformeBasico();
	    }
		
		

	
}
