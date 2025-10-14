package entidades;

import java.util.List;
import java.util.Set;

public class Numero {

	private Long id;
	private int order;
	private String nombre;
	private double duracion;

	private Set<Artista> artistas;

	public Numero() {
		super();
	}



	public Numero(Long id, int order, String nombre, double duracion, Set<Artista> artistas) {
		super();
		this.id = id;
		this.order = order;
		this.nombre = nombre;
		this.duracion = duracion;
		this.artistas = artistas;
	}



	public Set<Artista> getArtistas() {
		return artistas;
	}



	public void setArtistas(Set<Artista> artistas) {
		this.artistas = artistas;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}


}
