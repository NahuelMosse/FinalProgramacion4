package utilidades;

public class Logger {

	public static void logError(String stringError) {
		Logger.divider();
		System.out.println("ERROR: " + stringError);
		Logger.divider();
	}

	public static void logSuccess(String stringSuccess) {
		System.out.println("\n" + stringSuccess + " !!!");
	}

	public static void logWarning(String stringWarning) {
		Logger.divider();
		System.out.println("ALERTA: " + stringWarning);
		Logger.divider();
	}

	public static void subDivider() {
		System.out.println("*********************************");
	}

	public static void divider() {
		System.out.println("--------------------------------------------------------------------------------------");
	}

	public static void header(String title) {
		Logger.divider();
		System.out.println(title);
		Logger.divider();
	}
}
