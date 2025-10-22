/*
* Clase Sesion.java
*
* @author Adrian Iglesias Riño
* @version 1.0
*/

package entidades;

/*Es una solucion para mantener la sesion iniciada. Este objeto sesion
 * va a tener los datos de la persona que inicia el programa.
 * 
*/

public class Sesion {

	private String nombre;
	private Perfil perfil;

	public Sesion() {
		super();
	}

	public Sesion(String nombre, Perfil perfil) {
		super();
		this.nombre = nombre;
		this.perfil = perfil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public void iniciarSesion(String usuario, Perfil perfil) {
		this.nombre = usuario;
		this.perfil = perfil;
	}

	public void cerrarSesion() {
		this.nombre = null;
		this.perfil = Perfil.INVITADO;
	}

	/*Meto aqui el switch para los menus? que puedo hacer con este metodo? 
	 * autenticar mas que un metood es un filtro?*/
	
	public boolean isAutenticado() {
		return perfil != Perfil.INVITADO;
	}

	
}
