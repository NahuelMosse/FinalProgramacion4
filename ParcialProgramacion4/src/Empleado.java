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

    public Empleado(
        int legajo,
        String nombre,
        String apellido,
        Fecha fechaNacimiento,
        Fecha fechaDeIngreso, ArrayList<Cargo> historialDeCargos,
        Hashtable<Habilidad,Integer> habilidades
    ) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.historialDeCargos = historialDeCargos;
        this.habilidades = habilidades;
        this.fechaDeIngreso = fechaDeIngreso;
    }

    public void mostrar() {
        System.out.println("legajo: " + legajo);
        System.out.println("Nombre completo: " + nombre + " " + apellido);
        System.out.println("Puesto actual: ");
        this.getPuestoActual().mostrar();
        System.out.println("Habilidades: ");
        this.mostrarHabilidades();
    }
}
