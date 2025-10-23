/*
* Clase Artista.java
*
* @author Adrian Iglesias Riño
* @version 1.0
*/

package entidades;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Artista extends Persona {
	/*debo meter static final long serialVersionUID = 1L;???*/
	private Long idArt;
	private String apodo;
	private Set<Especialidad> especialidades = new HashSet<Especialidad>();
	private List<Numero> numeros;
	/*hay que tener en cuenta que hay que transformar un conjunto en una 	lista no habra elementos 	repetidos
	 *mejor trabajar con conjuntos*/
	public Artista() {
		super();
	}

	public Artista(Long idArt, String apodo, Set<Especialidad> especialidades, List<Numero> numeros) {
		super();
		this.idArt = idArt;
		this.apodo = apodo;
		this.especialidades = especialidades;
		this.numeros = numeros;
	}
	
	public Set<Especialidad> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}

	public Long getIdArt() {
		return idArt;
	}

	public void setIdArt(Long idArt) {
		this.idArt = idArt;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}


	public List<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}

}