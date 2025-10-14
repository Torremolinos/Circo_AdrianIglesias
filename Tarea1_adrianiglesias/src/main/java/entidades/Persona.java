package entidades;

public class Persona implements Comparable<Persona> {

	protected Long id;
	protected String email;
	protected String nombre;
	protected String nacionalidad;

	public Persona() {
		super();
	}

	public Persona(Long id, String email, String nombre, String nacionalidad) {
		super();
		this.id = id;
		this.email = email;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	/**
	 *Este compareTo, intento ordenar alfabeticamente
	 *los nombre y sino por los emails. 
	 */
	@Override
	public int compareTo(Persona o) {
		/*Creo que me faltan muchas cosas*/
		if (o == null) {
			return 1;
		}
		int comparador = 1;
		try {
			comparador = this.nombre.compareTo(o.nombre);

			if (comparador == 0) {
				comparador = this.email.compareTo(o.email);
				return 0;
			}
		} catch (Exception e) {
			System.out.println("Este campo esta vacio" + e);
		}

		return comparador;
	}

}
