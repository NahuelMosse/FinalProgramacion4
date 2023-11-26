import java.util.Scanner;

import utilidades.Logger;

public class Ejecutora {
	
	public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        Empresa empresa = new Empresa();
        
        Logger.divider();
    	System.out.println("Bienvenido al sistema de administracion de cambios de puesto");
    	System.out.println("Deseamos que disfrute el recorrido por la aplicacion :)");
    	System.out.println("\nEzequiel Spagnoli - Horacio Balart - Nahuel Mosse - Nicolas Colli - Santiago Nunnez");
    	Logger.divider();

        // Define la cantidad minima de annos para cambiar de puesto siendo jerarquico
        // PuestoJerarquico.setAnnosMinimosParaCambiar();

        int opcion = 0;
        do {
            Logger.divider();
            System.out.println("Seleccione una de las opciones de la lista");
            Logger.divider();
        	
            System.out.println("\n[1] Agregar habilidad a lista de habilidades de la empresa");
            System.out.println("[2] Generar puesto vacante");
            System.out.println("[3] Agregar empleado");
            System.out.println("[4] Agregar convocatoria");
            System.out.println("[5] Inscribir empleado a convocatoria");
            System.out.println("[6] Mostrar convocatorias abiertas de un puesto");
            System.out.println("[7] Mostrar convocatorias abiertas para todos los puestos");
			
            // Evita que se cuelgue el sistema cuando se ingresa un string
            boolean huboError = false;
	    do {
	        huboError = false;
		try {
		    System.out.print("\nopcion: ");
		    opcion = Integer.parseInt(scanner.nextLine());
		} catch (Exception _error) {
		    huboError = true;
		    Logger.logError("Solo estan permitidos caracteres numericos");
		}
	    } while (huboError);

            switch (opcion) {
            case 0:
                break;
            case 1:
                // empresa.agregarHabilidad();
                break;
            case 2:
                // empresa.agregarPuestoVacante();
                break;
            case 3:
                // empresa.agregarEmpleado();
                break;
            case 4:
                // empresa.agregarConvocatoria();
                break;
            case 5:
                // empresa.inscribirEmpleadoConvocatoria();
                break;
            case 6:
                break;
            case 7:
                break;
            default:
            	Logger.logError("La opcion " + opcion + " no esta en la lista");
                break;
            }
            
        } while(opcion != 0);
        
        scanner.close();
        
        Logger.divider();
    	System.out.println("Gracias por usar nuestro sistema :)");
    	Logger.divider();
    }
}
