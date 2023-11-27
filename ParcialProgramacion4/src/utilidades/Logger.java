package utilidades;

public class Logger {

	public static void logError(String stringError) {
		Logger.divider();
		System.out.println("ERROR: " + stringError);
		Logger.divider();
	}
	
	public static void divider() {
		System.out.println("--------------------------------------------------------------------------------------");
	}
}
