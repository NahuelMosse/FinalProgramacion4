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

    public boolean empCumpleAnnosConvocatoriaJerarquica(Empleado empleadoInscribir){
        return empleadoInscribir.getAnnosEnEmpresa() >= this.annosMinimosEnEmpresa;
    }

    public boolean empAnnosSuficientesCargoActual(Empleado empleadoInscribir){
        Cargo cargoActual = empleadoInscribir.getCargoActual();
        
        if(cargoActual.tiempoEnCargo() >= PuestoJerarquico.getAnnosMinimosParaCambiar()){
            return true;
        }
        return false;
    }

    public boolean empleadoPuedeInscribirse(Empleado empleadoInscribir) {
        boolean cumpleAnnosMinimos = annosMinimosEnEmpresa <= empleadoInscribir.getAnnosEnEmpresa();
        return super.empleadoPuedeInscribirse(empleadoInscribir) && cumpleAnnosMinimos;
    }
}
