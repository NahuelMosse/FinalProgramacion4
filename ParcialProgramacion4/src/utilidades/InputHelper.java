package utilidades;

import java.util.Scanner;

public class InputHelper {

	public static int scanInt(Scanner scanner, String displayText) {
		boolean huboError = false;
		int number = 0;
		
		do {
		    huboError = false;
		    // Evita que se cuelgue el sistema cuando se ingresa un string
		    try {
				System.out.print(displayText);
				number = Integer.parseInt(scanner.nextLine());
		    } catch (Exception _error) {
				huboError = true;
				Logger.logError("Solo estan permitidos numeros enteros");
		    }
		} while (huboError);
		
		return number;
	}
	
	public static float scanFloat(Scanner scanner, String displayText) {
		boolean huboError = false;
		float number = 0;
		
		do {
		    huboError = false;
		    // Evita que se cuelgue el sistema cuando se ingresa un string
		    try {
				System.out.print(displayText);
				number = Float.parseFloat(scanner.nextLine());
		    } catch (Exception _error) {
				huboError = true;
				Logger.logError("Solo estan permitidos numeros");
		    }
		} while (huboError);
		
		return number;
	}
	
	public static boolean yesOrNoInput(Scanner scanner, String displayQuestion) {
		System.out.print(displayQuestion + " [SI/NO]: ");
        String answer = scanner.nextLine();
        
        while (!answer.equalsIgnoreCase("si") && !answer.equalsIgnoreCase("no")) {
            Logger.logError("ingrese una opcion valida (si o no)");
            System.out.print(displayQuestion + " [SI/NO]: ");
            answer = scanner.nextLine();
        }
        
        return answer.equalsIgnoreCase("si");
	}
}
