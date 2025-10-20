/*
* Clase Espectaculo.java
*
* @author Adrian Iglesias Riño
* @version 1.0
*/

package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Espectaculo implements Serializable{

	private static final long serialVersionUID = 17L;
	
	private Long id;
	private String nombre;
	private LocalDate fechaini;
	private LocalDate fechafin;
	private Coordinacion coordinacion;
	/*La diferencia seria carga pesada y carga ligera, 
	 * y que los datos vienen completos.*/
	
	/*private Long idCoord*/
	private Set<Numero> numeros = new HashSet<>();

	public Espectaculo() {
		super();
	}

	public Espectaculo(Long id, String nombre, LocalDate fechaini, LocalDate fechafin, Coordinacion coordinacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
		this.coordinacion = coordinacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaini() {
		return fechaini;
	}

	public void setFechaini(LocalDate fechaini) {
		this.fechaini = fechaini;
	}

	public LocalDate getFechafin() {
		return fechafin;
	}

	public void setFechafin(LocalDate fechafin) {
		this.fechafin = fechafin;
	}

	public Coordinacion getCoordinacion() {
		return coordinacion;
	}

	public void setCoordinacion(Coordinacion coordinacion) {
		this.coordinacion = coordinacion;
	}

}
