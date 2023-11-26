import java.util.Hashtable;

import utilidades.Fecha;

public class ConvocatoriaJerarquico extends Convocatoria {
    private int annosMinimosEnEmpresa;

    public ConvocatoriaJerarquico(
        int codigo,
        Puesto puesto,
        Fecha fecha,
        int cantEmpleadosRequeridos,
        int annosMinimosEnEmpresa,
        Hashtable<Habilidad,Integer> requisitos
    ) {
        super(codigo, puesto, fecha, cantEmpleadosRequeridos, requisitos);
        this.annosMinimosEnEmpresa = annosMinimosEnEmpresa;
    }

    //3. Solo si empleadoInscribir se postula a puesto jerarquico, debe cumplir n cantidad de annos en la empresa. Si no es jerarquico no hay condicion
    public boolean empleadoPuedeInscribirse(Empleado empleadoInscribir) {
        boolean cumpleAnnosMinimos = annosMinimosEnEmpresa <= empleadoInscribir.getAnnosEnEmpresa();
        return super.empleadoPuedeInscribirse(empleadoInscribir) && cumpleAnnosMinimos;
    }
}
