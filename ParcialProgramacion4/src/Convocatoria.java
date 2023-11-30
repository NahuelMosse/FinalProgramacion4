import java.util.ArrayList;
import utilidades.Fecha;
import java.util.Enumeration;
import java.util.Hashtable;

public abstract class Convocatoria {
    private int codigo;
    private ArrayList<Empleado>postulados;
    private Puesto puesto;
    private Fecha fecha;
    private int cantEmpleadosRequeridos;
    private Hashtable<Habilidad,Integer>requisitos;
    private ArrayList<Empleado>asignados;

    public Convocatoria(
        int codigo,
        Puesto puesto,
        Fecha fecha,
        int cantEmpleadosRequeridos,
        Hashtable<Habilidad, Integer> requisitos
    ) {
        this.codigo = codigo;
        this.postulados = new ArrayList<Empleado>();
        this.puesto = puesto;
        this.fecha = fecha;
        this.cantEmpleadosRequeridos = cantEmpleadosRequeridos;
        this.requisitos = requisitos;
        this.asignados = new ArrayList<Empleado>();
    }

    public boolean hasCodigo(int codigo) {
        return this.codigo == codigo;
    }
    
    //1. El empleadoInscribir no puede estar en lista de postulados o asignados
    public boolean empleadoEstaPostulado(Empleado empleado){
        return postulados.contains(empleado);
    }
    
    public boolean noPasoFecha() {
    	return Fecha.hoy().compareTo(fecha) <= 0;
    }
    
    public boolean empleadoEstaAsignado(Empleado empleado) {
    	return asignados.contains(empleado);
    }
    
    public boolean eliminarEmpleado(Empleado empleadoEliminar) {
        return postulados.remove(empleadoEliminar) || asignados.remove(empleadoEliminar);
    }
}
