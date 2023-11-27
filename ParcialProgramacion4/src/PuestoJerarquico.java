import java.util.Scanner;

public class PuestoJerarquico extends Puesto{
    Scanner scanner = new Scanner(System.in);

    private static int annosMinimosParaCambiar;

    public PuestoJerarquico(String nombre, double sueldo) {
        super(nombre, sueldo);
    }
}
