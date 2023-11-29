import java.util.Scanner;

import utilidades.InputHelper;
import utilidades.Logger;

public class Ejecutora {
	private static Scanner scanner;
    private static Empresa empresa;

    public static void main(String[] args) {
		scanner = new Scanner(System.in);
		empresa = new Empresa(scanner);
        
        Logger.divider();
    	System.out.println("Bienvenid@ al sistema de administracion de cambios de puesto");
    	System.out.println("Deseamos que disfrute el recorrido por la aplicacion :)");
    	System.out.println("\nEzequiel Spagnoli - Horacio Balart - Nahuel Mosse - Nicolas Colli - Santiago Nunnez");
    	Logger.divider();

        // Define la cantidad minima de annos para cambiar de puesto siendo jerarquico
        // PuestoJerarquico.setAnnosMinimosParaCambiar();

        Ejecutora.menuPrincipal();

		scanner.close();
        
        Logger.divider();
    	System.out.println("Gracias por usar nuestro sistema :)");
    	Logger.divider();
    }
	
    public static void menuPrincipal() {
    	int opcion = 0;
		
        do {
        	Logger.header("Menu Principal");
        	
            System.out.println("\nSeleccione una de las opciones de la lista");
            System.out.println("\n[1] Admin");
            System.out.println("[2] General");
            System.out.println("[3] Usuario");
            System.out.println("[4] Ayuda"); // Comming soon
            System.out.println("[0] Salir");
            
            opcion = Ejecutora.conseguirOpcionDelMenu();

            switch (opcion) {
	        case 0:
	            // Salir
	            break;
	        case 1:
	            Ejecutora.menuAdmin();
	            break;
	        case 2:
	            Ejecutora.menuGeneral();
	            break;
	        case 3:
	            Ejecutora.menuUsuario();
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
	
    public static void menuAdmin() {
    	int opcion = 0;
		
        do {
        	Logger.header("Menu Admin");
        	
            System.out.println("\nSeleccione una de las opciones de la lista");
            System.out.println("\n[1] Agregar empleado");
            System.out.println("[2] Dar de baja empleado");
            System.out.println("[3] Agregar puesto de trabajo");
            System.out.println("[4] Borrar puesto de trabajo");
            System.out.println("[5] Crear habilidad");
            System.out.println("[6] Editar habilidad");
            System.out.println("[7] Borrar habilidad");
            System.out.println("[0] Volver al menu principal");
            
            opcion = Ejecutora.conseguirOpcionDelMenu();

            switch (opcion) {
	        case 0:
	            // Volver al menu principal
	            break;
	        case 1:
	            empresa.agregarEmpleado();
	            break;
	        case 2:
	            	
	            break;
	        case 3:
	            empresa.agregarPuesto();   
	            break;
	        case 4:
	            empresa.borrarPuesto();
	            break;
	        case 5:
	            empresa.crearUnaHabilidad();
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
	
    public static void menuGeneral() {
    	int opcion = 0;
		
        do {
        	Logger.header("Menu General");
        	
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
            
            opcion = Ejecutora.conseguirOpcionDelMenu();

            switch (opcion) {
	        case 0:
	            // Volver al menu principal
	            break;
	        case 1:
				//agregarConvocatoria se fusiona con agregarPuestoVacante xq dentro de agregarConvocatoria, se da la posibilidad de crear al usuario un puesto nuevo si no existe
				empresa.agregarConvocatoria();
	            break;
	        case 2:
	                
	            break;
	        case 3:
	            empresa.darDeBajaConvocatoria();
	            break;
	        case 4:
	                
	            break;
	        case 5:
	            empresa.mostrarConvocatoriasAbiertas();
	            break;
	        case 6:
	            empresa.mostrarConvocatoriasPuedaAplicarEmpleado();
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
	
    public static void menuUsuario() {
    	int opcion = 0;
		
        do {
        	Logger.header("Menu Usuario");
        	
            System.out.println("\nSeleccione una de las opciones de la lista");
            System.out.println("\n[1] Ver datos de empleado");
            System.out.println("[2] Ver historial de cargos");
            System.out.println("[3] Editar datos");
            System.out.println("[4] Agregar habilidad");
            System.out.println("[5] Editar años de experiencia habilidad");
            System.out.println("[6] Quitar habilidad");
            System.out.println("[0] Volver al menu principal");
            
    	    opcion = Ejecutora.conseguirOpcionDelMenu();

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
	
    public static int conseguirOpcionDelMenu() {
		System.out.print("\n");
		int opcion = InputHelper.scanInt(scanner, "Opcion: ");
			
		return opcion;
    }
}
