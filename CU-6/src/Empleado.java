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
    
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }
    
    public void setApellido(String apellido) {
    	this.apellido = apellido;
    }
    
    public void setFechaNacimiento(Fecha fechaNacimiento) {
    	this.fechaNacimiento = fechaNacimiento;
    }
    
    public void setFechaIngreso(Fecha fechaIngreso) {
    	this.fechaDeIngreso = fechaIngreso;
    }
    
    
    
}
