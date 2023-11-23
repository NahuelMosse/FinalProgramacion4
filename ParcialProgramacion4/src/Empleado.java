import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import utilidades.Fecha;

public class Empleado {
    private int legajo;
    private String nombre;
    private String apellido;
    private Fecha fechaNacimiento;
    private ArrayList<Cargo>historialDeCargos;
    private Hashtable<Habilidad,Integer> habilidades;
    private Fecha fechaDeIngreso;

    public Empleado(int legajo, String nombre, String apellido, Fecha fechaNacimiento, Fecha fechaDeIngreso, ArrayList<Cargo> historialDeCargos, Hashtable<Habilidad,Integer> habilidades) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.historialDeCargos = historialDeCargos;
        this.habilidades = habilidades;
        this.fechaDeIngreso = fechaDeIngreso;
    }

    public boolean hasEmpleado(int legajo) {
        return this.legajo == legajo;
    }

    public Puesto getPuestoActual() {
        //el puesto actual es el que tiene fechaFin==null porque no ha terminado 
        //le pregunto a cada cargo si es el actual, si es me lo devuelve, sino me devuelve null
        int i = 0;
        while(!historialDeCargos.get(i).esActual()) //no pongo condicion de i<size porque se que alguno de los puestos es actual 
            i++;

        return historialDeCargos.get(i).getPuesto();

        /*Version alternativa:
        while(historialDeCargos.get(i).getPuestoSiSosActual() == null)
            i++;
        
        return historialDeCargos.get(i).getPuestoSiSosActual()
        
        corta cuando es el puesto actual porque no es null, es el puesto en si y eso es lo que retorno
        */
    }
}
