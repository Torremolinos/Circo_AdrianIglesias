/*
* Clase Principal.java
*
* @author Luis de Blas
* @version 1.0
*/

package utils;

import java.sql.Date;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidades {

	/**
	 * Función que pide al usuario que introduzca 's' o 'S' para Sí o 'n' o 'N'
	 * para No y devuelve true o false en cada caso. Si el usuario no introduce
	 * ni 's' ni 'S' ni 'n' ni 'N' entonces avisa al usuario y le vuelve a pedir
	 * a que lo introduzca de nuevo.
	 *
	 * @return true si el usuario introduce 's' o 'S'; false si el usuario
	 *         introduce 'n' o 'N' Ahora si, antes si se 
	 */
	public static boolean leerBoolean() {
	    Scanner in = new Scanner(System.in, "ISO-8859-1");
	    char resp;
	    do {
	        System.out.println("Pulse S para Sí o N para No:");
	        String entrada = in.nextLine().trim();
	        
	        if (entrada.isEmpty()) {
	            System.out.println("Valor vacío. Intente de nuevo.");
	            continue;
	        }

	        resp = entrada.charAt(0);

	        if (resp == 's' || resp == 'S') {
	            return true;
	        } else if (resp == 'n' || resp == 'N') {
	            return false;
	        } else {
	            System.out.println("Valor introducido incorrecto. Introduzca S o N.");
	        }
	    } while (true);
	 
	}


	/**
	 * Función que pide al usuario que introduce un valor para una fecha a
	 * partir de 3 enteros para el día, mes y año respectivamente. Si los
	 * valores introducidos no producen una fecha correcta, avisa al usuario y
	 * le pide que los introduzca de nuevo. Si no lo consigue, devolverá null
	 *
	 * @return una fecha de la clase java.sql.Date o null si hay error
	 */
	public static java.sql.Date leerFecha() {
		Date ret = null;
		int dia, mes, anio;
		boolean correcto = false;
		Scanner in;
		do {
			System.out.println("Introduzca un valor para el día (1...31)");
			in = new Scanner(System.in, "ISO-8859-1");
			dia = in.nextInt();
			System.out.println("Introduzca un valor para el mes (1...12)");
			in = new Scanner(System.in, "ISO-8859-1");
			mes = in.nextInt();
			System.out.println("Introduzca un valor para el año");
			in = new Scanner(System.in, "ISO-8859-1");
			anio = in.nextInt();

			try {
				ret = Date.valueOf(LocalDate.of(anio, mes, dia));
				correcto = true;
			} catch (Exception e) {
				System.out.println("Fecha introducida incorrecta.");
				correcto = false;
			}
		} while (!correcto);
		return ret;
	}

	/**
	 * Función que pide al usuario que introduzca un valor entero por la entrada
	 * estándar. Si el formato introducido no es correcto, avisa al usuario y le
	 * vuelve a pedir que lo introduzca de nuevo.
	 *
	 * @return el valor entero introducido por el usuario
	 */

	public static int leerInt() {
		int ret = 0;
		boolean correcto = false;
		Scanner in;
		do {
			System.out.println("Introduzca un número entero:");
			in = new Scanner(System.in, "ISO-8859-1");
			try {
				ret = in.nextInt();
				correcto = true;
			} catch (InputMismatchException ime) {
				System.out.println(
						"Formato introducido incorrecto. Intente de nuevo.");
			}
		} while (!correcto);
		return ret;
	}

	/**
	 * Función que pide al usuario que introduzca un valor String por la entrada
	 * estándar. Si el formato introducido no es correcto, avisa al usuario y le
	 * vuelve a pedir que lo introduzca de nuevo.
	 *
	 * @return el valor String introducido por el usuario
	 */

	public static String leerString(String prompt, String errorMsg) {
		String ret = "";
		boolean correcto = false;
		Scanner in;

		do {
			System.out.print(prompt);
			in = new Scanner(System.in, "ISO-8859-1");
			try {
				ret = in.nextLine();
				if (!ret.trim().isEmpty()) {
					correcto = true;
				} else {
					System.out.println(errorMsg);
				}
			} catch (Exception e) {
				System.out.println(
						" Error al leer la entrada. Intente de nuevo.");
			}
		} while (!correcto);

		return ret;
	}

	public static String removeDiacriticalMarks(String string) {
		// Form.NFC acepta ñ y distingue las tildes en español
		return Normalizer.normalize(string, Form.NFC)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	/**
	 * Lee una fecha en formato europeo (dd/MM/yyyy), valida que exista y que
	 * esté dentro del rango de hoy a un año máximo.
	 *
	 * @return LocalDate con la fecha válida
	 */
	public static LocalDate leerFechasLocalDate() {
		Scanner sc = new Scanner(System.in);
		LocalDate fecha = null;
		boolean correcta = false;

		do {
			try {
				System.out.print("Introduce el día (1-31): ");
				int dia = sc.nextInt();

				System.out.print("Introduce el mes (1-12): ");
				int mes = sc.nextInt();

				System.out.print("Introduce el año (YYYY): ");
				int anio = sc.nextInt();

				fecha = LocalDate.of(anio, mes, dia);

				LocalDate hoy = LocalDate.now();
				LocalDate max = hoy.plusYears(1);

				if (fecha.isBefore(hoy)) {
					System.out
							.println("❌ La fecha no puede ser anterior a hoy.");
				} else if (fecha.isAfter(max)) {
					System.out.println(
							"❌ La fecha no puede ser posterior a un año desde hoy.");
				} else {
					correcta = true;
				}

			} catch (InputMismatchException e) {
				System.out.println("❌ Debes introducir números válidos.");
				sc.nextLine(); 
			} catch (Exception e) {
				System.out.println(
						"❌ Fecha introducida incorrecta (día, mes o año no válidos).");
				sc.nextLine(); 
			}

		} while (!correcta);

		sc.nextLine(); 
		return fecha;
	}
}