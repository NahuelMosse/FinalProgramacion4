import java.util.Scanner;

public class Ejecutora {
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        Empresa empresa = new Empresa();

        int opcion = 0;
        do{
            System.out.println(
			"\n 1- agregar habilidad a lista de habilidades de la empresa"+
			"\n 2- generar puesto vacante"+ //aca tambien se incluye a los puestos que llevaron los empleados antes y en la actualidad
			"\n 3- agregar empleado"+
			"\n 4- agregar convocatoria"+
			"\n 5-"+
			"\n 6-"+
			"\n 7-");
			System.out.println("opcion: ");
            opcion = Integer.parseInt(scanner.nextLine()); //para limpiar en \n y evitar saltos de linea dentro de metodos de empresa

            switch (opcion) {
            case 0:
                break;
            case 1:
                empresa.agregarHabilidad();
                break;
            case 2:
                empresa.agregarPuestoVacante();
                break;
            case 3:
                empresa.agregarEmpleado();
                break;
            case 4:
                empresa.agregarConvocatoria();
                break;
            case 5:
                break;
                
            case 6:
                break;

            case 7:
                break;

            default:
                System.out.println("opcion no valida");
                break;
            }

        } while(opcion!=0);

        scanner.close();
    }
}
