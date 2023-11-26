import java.util.Scanner;

public class PuestoJerarquico extends Puesto{
    static Scanner scanner = new Scanner(System.in);

    private static int annosMinimosParaCambiar;

    public PuestoJerarquico(String nombre, double sueldo) {
        super(nombre, sueldo);
    }

    public void mostrar() {
        System.out.println("Puesto jerarquico");
        super.mostrar();
    }

    public static void setAnnosMinimosParaCambiar(){
        System.out.println("Ingrese la cantidad minima de annios requerida para cambiar a un puesto Jerarquico:");
        PuestoJerarquico.annosMinimosParaCambiar = Integer.parseInt(scanner.nextLine());
    }

    public static int getAnnosMinimosParaCambiar() {
        return annosMinimosParaCambiar;
    }
}
