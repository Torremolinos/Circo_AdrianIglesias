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
					.getProperty("nacionalidades");

	public PaisService() {
		this.mapaPaises = leerPaises();
	}

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

	public String obtenerNombrePorCodigo(String codigo) {
		return mapaPaises.get(codigo);
	}

	public Map<String, String> obtenerTodosLosPaises() {
		return Collections.unmodifiableMap(mapaPaises);
	}
}
