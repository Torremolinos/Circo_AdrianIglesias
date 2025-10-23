/*
* Clase Config.java
*
* @author Adrian Iglesias Riño
* @version 1.0
*/
package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private Properties properties = new Properties();

	public Config() {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
			if (input == null) {
				System.out.println("No se pudo encontrar application.properties");
				return;
			}
			properties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Nos trae el valor de la propiedad del atributo pasado.
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
