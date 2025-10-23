/*
* Clase PaisService.java
*
* @author Adrian Iglesias Riño
* @version 1.0
*/

package Service;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import utils.Config;

public class PaisService {

	private Map<String, String> mapaPaises;
	private static final String rutaXml = new Config()
					.getProperty("paisesruta");

	/**
	 * Al instanciar nuestro service ya creamos los paises
	 * Usando el metodo correspondiente.
	 */
	public PaisService() {
		this.mapaPaises = leerPaises();
	}

	/**
	 * Metodo para leer paises del archivo.xml
	 * @return
	 */
	private Map<String, String> leerPaises() {
		Map<String, String> mapa = new HashMap<>();

		try {
			File archivo = new File(rutaXml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
							.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document documento = dBuilder.parse(archivo);
			documento.getDocumentElement().normalize();

			NodeList listaPaises = documento.getElementsByTagName("pais");

			for (int i = 0; i < listaPaises.getLength(); i++) {
				Element pais = (Element) listaPaises.item(i);

				String id = pais.getElementsByTagName("id").item(0)
								.getTextContent();
				String nombre = pais.getElementsByTagName("nombre").item(0)
								.getTextContent();

				mapa.put(id, nombre);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapa;
	}

	/**
	 * Devuelve un mapa con todos los países cargados desde el XML.
	 * El mapa contiene como clave el código del país
	 * El mapa devuelto es de solo lectura: no se puede modificar 
	 * @return un {@code Map<String, String>} inmodificable con los códigos y nombres de países
	 */
	public Map<String, String> obtenerTodosLosPaises() {
		return Collections.unmodifiableMap(mapaPaises);
	}
}
