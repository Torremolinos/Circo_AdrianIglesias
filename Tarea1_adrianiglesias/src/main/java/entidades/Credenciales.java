package entidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Credenciales {

	private Long id;
	private String nombre;
	private String password;
	private Perfil perfil;

	public Credenciales() {
		super();
	}

	public Credenciales(Long id, String nombre, String password, Perfil perfil) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.perfil = perfil;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	/*esto era una prueba para ver si podia leer de credenciales.txt 
	 * tiene que ir en un repository*/
	
	public void leerCredenciales() {
		String ruta = "src/main/java/utils/credenciales.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;

			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split("\\|"); 
				String id = partes[0];
				String usuario = partes[1];
				String password = partes[2];
				String email = partes[3];
				String nombre = partes[4];
				String pais = partes[5];
				String rol = partes[6];

				System.out.println("Usuario: " + usuario + " (" + email + ")");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
