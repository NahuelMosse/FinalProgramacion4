import java.util.Scanner;

public class Ejecutora {
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        Empresa empresa = new Empresa();

        PuestoJerarquico.setAnnosMinimosParaCambiar(); //Permite definir la cantidad de annos minima en un puesto jerarquico para poder acceder otro un puesto jerarquico

        int opcion = 0;
        do{
            System.out.println(
			"\n 1- Agregar habilidad a lista de habilidades de la empresa" +
			"\n 2- Generar puesto vacante" + //aca tambien se incluye a los puestos que llevaron los empleados antes y en la actualidad
			"\n 3- Agregar empleado" +
			"\n 4- Agregar convocatoria" +
			"\n 5- Inscribir empleado a convocatoria" +
			"\n 6- Mostrar convocatorias abiertas de un puesto" +
			"\n 7- Mostrar convocatorias abiertas para todos los puestos");
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
                empresa.inscribirEmpleadoConvocatoria();
                break;
                
            case 6:
                break;

            case 7:
                break;

            default:
                System.out.println("Opcion no valida");
                break;
            }

        } while(opcion!=0);

        scanner.close();
    }
}
