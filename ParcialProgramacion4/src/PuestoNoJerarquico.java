import java.util.Scanner;

public class PuestoNoJerarquico extends Puesto {

    public PuestoNoJerarquico(String nombre, float sueldo) {
        super(nombre, sueldo);
    }

    public boolean puedeCambiarDePuesto(int annosEnPuesto, Scanner scanner) {
        return true; // comparo en PuestoJerarquico, aca no hay condicion
    }
}
