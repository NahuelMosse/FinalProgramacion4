import java.util.Hashtable;
import java.util.Scanner;

import utilidades.Fecha;

public class ConvocatoriaJerarquico extends Convocatoria {
    private int annosMinimosEnEmpresa;

    public ConvocatoriaJerarquico(
        int codigo,
        Puesto puesto,
        Fecha fecha,
        int cantEmpleadosRequeridos,
        int annosMinimosEnEmpresa,
        Hashtable<Habilidad, Integer> requisitos
    ) {
        super(codigo, puesto, fecha, cantEmpleadosRequeridos, requisitos);
        this.annosMinimosEnEmpresa = annosMinimosEnEmpresa;
    }

    public boolean puedeAplicar(Empleado empleadoAplicar, Scanner scanner) {
        // si es para un puesto jerarquico, el empleado debe estar como minimo hace n
        // años en la empresa
        return super.puedeAplicar(empleadoAplicar, scanner) && (empleadoAplicar.getAnnosEnEmpresa() >= annosMinimosEnEmpresa);
    }

    public void mostrar() {
        super.mostrar();

        System.out.println("Años minimos en la empresa para aplicar: " + annosMinimosEnEmpresa);
    }
}
