import java.util.ArrayList;

public abstract class Puesto {
    private String nombre;
    private double sueldo;
    private ArrayList<Convocatoria>convocatorias;
    private ArrayList<Empleado>empleados;

    public Puesto(String nombre, double sueldo){
        this.nombre = nombre;
        this.sueldo = sueldo;
        convocatorias = new ArrayList<>();
        empleados = new ArrayList<>();
    }
    
}
