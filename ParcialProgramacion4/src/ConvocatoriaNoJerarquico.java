import java.util.Hashtable;

import utilidades.Fecha;

public class ConvocatoriaNoJerarquico extends Convocatoria {
    public ConvocatoriaNoJerarquico(
        int codigo,
        Puesto puesto,
        Fecha fecha,
        int cantEmpleadosRequeridos,
        Hashtable<Habilidad,Integer> requisitos
    ) {
        super(codigo, puesto, fecha, cantEmpleadosRequeridos, requisitos);
    }

    public boolean empCumpleAnnosConvocatoriaJerarquica(Empleado empleadoInscribir){
        return true;
    }

    public boolean empAnnosSuficientesCargoActual(Empleado empleadoInscribir){
        return true;
    }
}
