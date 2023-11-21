import java.util.ArrayList;

public class Empresa {
    private ArrayList<Empleado> empleados;
    private ArrayList<Puesto>puestos;
    private ArrayList<Convocatoria>convocatorias;
    private ArrayList<Habilidad>habilidades;

    public Empresa() {
        this.empleados = new ArrayList<Empleado>();
        this.puestos = new ArrayList<Puesto>();
        this.convocatorias = new ArrayList<Convocatoria>();
        this.habilidades = new ArrayList<Habilidad>();
    }

    
}
