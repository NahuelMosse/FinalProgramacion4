import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

import utilidades.Logger;

public class Ejecutora {
	
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        Empresa empresa = new Empresa();
        
        Logger.divider();
    	System.out.println("Bienvenid@ al sistema de administracion de cambios de puesto");
    	System.out.println("Deseamos que disfrute el recorrido por la aplicacion :)");
    	System.out.println("\nEzequiel Spagnoli - Horacio Balart - Nahuel Mosse - Nicolas Colli - Santiago Nunnez");
    	Logger.divider();

        // Define la cantidad minima de annos para cambiar de puesto siendo jerarquico
        // PuestoJerarquico.setAnnosMinimosParaCambiar();

        Ejecutora.menuPrincipal(empresa, scanner);
        
        scanner.close();
        
        Logger.divider();
    	System.out.println("Gracias por usar nuestro sistema :)");
    	Logger.divider();
    }
	
	public static void menuPrincipal(Empresa empresa, Scanner scanner) {
		int opcion = 0;
		
        do{
        	Logger.divider();
        	System.out.println("Menu Principal");
        	Logger.divider();
        	
        	System.out.println("\nSeleccione una de las opciones de la lista");
            System.out.println("\n[1] Admin");
            System.out.println("[2] General");
            System.out.println("[3] Usuario");
            System.out.println("[4] Ayuda"); // Comming soon
            System.out.println("[0] Salir");
            
            opcion = Ejecutora.conseguirOpcionDelMenu(scanner);

            switch (opcion) {
	            case 0:
	            	// Salir
	                break;
	            case 1:
	            	Ejecutora.menuAdmin(empresa, scanner);
	                break;
	            case 2:
	            	Ejecutora.menuGeneral(empresa, scanner);
	                break;
	            case 3:
	            	Ejecutora.menuUsuario(empresa, scanner);
	                break;
	            case 4:
	                // ayuda
	            	// Se puede hacer todo un menu que te explique a usar el sistema
	                break;
	            default:
	            	Logger.logError("La opcion " + opcion + " no esta en la lista");
	                break;
            }
            
        } while(opcion != 0);
	}
	
	public static void menuAdmin(Empresa empresa, Scanner scanner) {
		int opcion = 0;
		
        do{
        	Logger.divider();
        	System.out.println("Menu Admin");
        	Logger.divider();
        	
        	System.out.println("\nSeleccione una de las opciones de la lista");
            System.out.println("\n[1] Agregar empleado");
            System.out.println("[2] Dar de baja empleado");
            System.out.println("[3] Agregar puesto de trabajo");
            System.out.println("[4] Borrar puesto de trabajo");
            System.out.println("[5] Agregar habilidad");
            System.out.println("[6] Editar habilidad");
            System.out.println("[7] Borrar habilidad");
            System.out.println("[0] Volver al menu principal");
            
            opcion = Ejecutora.conseguirOpcionDelMenu(scanner);

            switch (opcion) {
	            case 0:
	            	// Volver al menu principal
	                break;
	            case 1:
	            	// empresa.agregarEmpleado();
	                break;
	            case 2:
	            	
	                break;
	            case 3:
	                
	                break;
	            case 4:
	            	
	                break;
	            case 5:
	            	// empresa.agregarHabilidad();
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
	}
	
	public static void menuGeneral(Empresa empresa, Scanner scanner) {
		int opcion = 0;
		
        do{
        	Logger.divider();
        	System.out.println("Menu General");
        	Logger.divider();
        	
        	System.out.println("\nSeleccione una de las opciones de la lista");
            System.out.println("\n[1] Crear una nueva convocatoria");
            System.out.println("[2] Editar convocatoria");
            System.out.println("[3] Dar de baja convocatoria");
            System.out.println("[4] Terminar proceso de convocatoria");
            System.out.println("[5] Mostrar convocatorias abiertas");
            System.out.println("[6] Mostrar convocatorias a las que pueda aplicar empleado");
            System.out.println("[7] Inscribir empleado a convocatorias");
            System.out.println("[8] Dar de baja incripto a convocatoria");
            System.out.println("[9] Definir años necesarios en puesto jerarquico para cambiar");
            System.out.println("[0] Volver al menu principal");
            
            opcion = Ejecutora.conseguirOpcionDelMenu(scanner);

            switch (opcion) {
	            case 0:
	            	// Volver al menu principal
	                break;
	            case 1:
	            	// empresa.agregarConvocatoria();
	            	// empresa.agregarPuestoVacante(); // esto deberia ser crear convocatoria
	            	// Revisar como fusionar estos dos en uno
	                break;
	            case 2:
	                
	                break;
	            case 3:
	            	
	                break;
	            case 4:
	                
	                break;
	            case 5:
	                
	                break;
	            case 6:
	            	
	                break;
	            case 7:
	            	// empresa.inscribirEmpleadoConvocatoria();
	                break;
	            case 8:
	            	
	                break;
	            case 9:
	            	
	                break;
	            default:
	            	Logger.logError("La opcion " + opcion + " no esta en la lista");
	                break;
            }
            
        } while(opcion != 0);
	}
	
	public static void menuUsuario(Empresa empresa, Scanner scanner) {
		int opcion = 0;
		
        do{
        	Logger.divider();
        	System.out.println("Menu Usuario");
        	Logger.divider();
        	
        	System.out.println("\nSeleccione una de las opciones de la lista");
            System.out.println("\n[1] Ver datos de empleado");
            System.out.println("[2] Ver historial de cargos");
            System.out.println("[3] Editar datos");
            System.out.println("[4] Agregar habilidad");
            System.out.println("[5] Editar años de experiencia habilidad");
            System.out.println("[6] Quitar habilidad");
            System.out.println("[0] Volver al menu principal");
            
            opcion = Ejecutora.conseguirOpcionDelMenu(scanner);

            switch (opcion) {
	            case 0:
	            	// Volver al menu principal
	                break;
	            case 1:
	            	
	                break;
	            case 2:
	                
	                break;
	            case 3:
	                
	                break;
	            case 4:
	                
	                break;
	            case 5:
	                
	                break;
	            case 6:
	            	
	                break;
	            default:
	            	Logger.logError("La opcion " + opcion + " no esta en la lista");
	                break;
            }
            
        } while(opcion != 0);
	}
	
	public static int conseguirOpcionDelMenu(Scanner scanner) {
		int opcion = 0;
	
        boolean huboError = false;
		do {
			huboError = false;
			// Evita que se cuelgue el sistema cuando se ingresa un string
			try {
				System.out.print("\nopcion: ");
				opcion = Integer.parseInt(scanner.nextLine());
			} catch (Exception _error) {
				huboError = true;
				Logger.logError("Solo estan permitidos caracteres numericos");
			}
		} while (huboError);
		
		return opcion;
	}
}
