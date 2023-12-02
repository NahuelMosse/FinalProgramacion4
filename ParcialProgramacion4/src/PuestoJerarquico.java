import java.util.Scanner;
import utilidades.InputHelper;

public class PuestoJerarquico extends Puesto {
    private static int annosMinimosParaCambiar;

    public PuestoJerarquico(String nombre, float sueldo) {
        super(nombre, sueldo);
    }

    public boolean puedeCambiarDePuesto(int annosEnPuesto, Scanner scanner) {
        return annosEnPuesto >= PuestoJerarquico.getAnnosMinimosParaCambiar(scanner);
    }

    // variables de clase
    public static void setAnnosMinimosParaCambiar(Scanner scanner) {
        PuestoJerarquico.annosMinimosParaCambiar = InputHelper.scanInt(scanner,
                "Ingrese la cantidad minima de años requerida para cambiar a un puesto Jerarquico: ");
    }

    public static void setAnnosMinimosParaCambiar(int annosMinimos) {
        PuestoJerarquico.annosMinimosParaCambiar = annosMinimos;
    }

    public static int getAnnosMinimosParaCambiar(Scanner scanner) {
        if (annosMinimosParaCambiar == 0) {
            PuestoJerarquico.setAnnosMinimosParaCambiar(scanner); // iniciacion tardia
        }
        return annosMinimosParaCambiar;
    }
}
