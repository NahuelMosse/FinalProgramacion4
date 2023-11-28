import java.util.ArrayList;
import utilidades.Logger;

public abstract class Puesto {
    private String nombre;
    private float sueldo;
    private ArrayList<Convocatoria>convocatorias;
    private ArrayList<Empleado>empleados;

    public Puesto(String nombre, float sueldo) {
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.convocatorias = new ArrayList<Convocatoria>();
        this.empleados = new ArrayList<Empleado>();
    }
  
    public boolean hasNombre(String nombre) {
        return this.nombre.equalsIgnoreCase(nombre);
    }



    public void agregarEmpleado(Empleado empleadoNuevo) {
        empleados.add(empleadoNuevo);
        Logger.logSuccess("Empleado nuevo agregado con exito a la lista de "+nombre);
    } 


    public void mostrar() {
        System.out.println("Nombre: "+ nombre + " | sueldo: " + sueldo);
    }

    public int cantEmpleados() {
        return empleados.size();
    }

    public boolean esJerarquico() {
        return this.getClass().getName().equalsIgnoreCase("PuestoJerarquico");
    }

    public void agregarConvocatoria(Convocatoria convocatoriaNueva) {
        convocatorias.add(convocatoriaNueva);
    }
}

