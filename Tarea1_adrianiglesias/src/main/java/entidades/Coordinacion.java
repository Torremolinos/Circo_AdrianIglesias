/*
* Clase Coordinacion.java
*
* @author Adrian Iglesias Riño
* @version 1.0
*/

package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Coordinacion extends Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idCoord;
	private boolean senior = false;
	private LocalDate fechasenior = null;

	private List<Espectaculo> espectaculos;

	public Coordinacion() {
		super();
	}

	public Coordinacion(Long idCoord, boolean senior, LocalDate fechasenior, List<Espectaculo> espectaculos) {
		super();
		this.idCoord = idCoord;
		this.senior = senior;
		this.fechasenior = fechasenior;
		this.espectaculos = espectaculos;
	}

	public Long getIdCoord() {
		return idCoord;
	}

	public void setIdCoord(Long idCoord) {
		this.idCoord = idCoord;
	}

	public boolean isSenior() {
		return senior;
	}

	public void setSenior(boolean senior) {
		this.senior = senior;
	}

	public LocalDate getFechasenior() {
		return fechasenior;
	}

	public void setFechasenior(LocalDate fechasenior) {
		this.fechasenior = fechasenior;
	}

	public List<Espectaculo> getEspectaculos() {
		return espectaculos;
	}

	public void setEspectaculos(List<Espectaculo> espectaculos) {
		this.espectaculos = espectaculos;
	}

}
