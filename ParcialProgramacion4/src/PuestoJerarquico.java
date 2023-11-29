import java.util.Scanner;
import utilidades.InputHelper;

public class PuestoJerarquico extends Puesto{
    private static Scanner scanner = new Scanner(System.in);

    private static int annosMinimosParaCambiar;

    public PuestoJerarquico(String nombre, float sueldo) {
        super(nombre, sueldo);
    }


    public boolean jerarquicoCumpleAnnosMinimos(int annosEnPuesto) {
        return annosEnPuesto >= PuestoJerarquico.getAnnosMinimosParaCambiar();
    }


    //variables de clase
    public static void setAnnosMinimosParaCambiar(){
        PuestoJerarquico.annosMinimosParaCambiar = InputHelper.scanInt(scanner, "Ingrese la cantidad minima de annios requerida para cambiar a un puesto Jerarquico:");
    }

    public static int getAnnosMinimosParaCambiar() {
        if (annosMinimosParaCambiar == 0) {
            PuestoJerarquico.setAnnosMinimosParaCambiar(); //iniciacion tardia
        }
        return annosMinimosParaCambiar;
    }
}
