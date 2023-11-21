import java.util.ArrayList;
import utilidades.Fecha;
import java.util.Enumeration;
import java.util.Hashtable;

public class Convocatoria {
    private int codigo;
    private ArrayList<Empleado>postulados;
    private Puesto puesto;
    private Fecha fecha;
    private int cantEmpleadosRequeridos;
    private Hashtable<Habilidad,Integer>requisitos;
    private ArrayList<Empleado>asignados;

    public Convocatoria(int codigo, Puesto puesto, Fecha fecha, int cantEmpleadosRequeridos){
        this.codigo = codigo;
        postulados = new ArrayList<>();
        this.puesto = puesto;
        this.fecha = fecha;
        this.cantEmpleadosRequeridos = cantEmpleadosRequeridos;
        requisitos = new Hashtable<Habilidad,Integer>();
        asignados = new ArrayList<>();
    }
}
