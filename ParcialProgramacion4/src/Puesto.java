import java.util.ArrayList;

public abstract class Puesto {
    private String nombre;
    private double sueldo;
    private ArrayList<Convocatoria>convocatorias;
    private ArrayList<Empleado>empleados;

    public Puesto(String nombre, double sueldo) {
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.convocatorias = new ArrayList<Convocatoria>();
        this.empleados = new ArrayList<Empleado>();
    }
    
    public boolean hasPuesto(String nombre) {
        return this.nombre.equalsIgnoreCase(nombre);
    }

    public void agregarEmpleado(Empleado empleadoNuevo) {
        empleados.add(empleadoNuevo);
        System.out.println("empleado nuevo agregado con exito a la lista de "+nombre+" !!!");
    }

    public void agregarConvocatoria(Convocatoria convocatoriaNueva) {
        convocatorias.add(convocatoriaNueva);
        System.out.println("convocatoria registrada en el puesto "+nombre);
    }

    public int mostrarConvocatoriasQueSePuedaInscribir(Empleado empleado) {
        int i = 0;
        for(Convocatoria convocatoria: convocatorias) {
            if(convocatoria.empleadoPuedeInscribirse(empleado)) {
                i++;
                convocatoria.mostrar();
            }
        }
        return i; //retorna la cantidad de convocatorias a la que puede aplicar el empleado para ese puesto
    }

    public void mostrar() {
        System.out.println("nombre puesto: "+nombre);
        System.out.println("sueldo puesto: "+sueldo);
    }

    public boolean esJerarquico() {
        return this.getClass().getName().equals("PuestoJerarquico");
    }

    public void eliminarEmpleado(Empleado empleadoEliminar) {
        empleados.remove(empleadoEliminar);
    }
    
    public void darDeBajaConvocatoria(Convocatoria convocatoriaEliminar) {
    	convocatorias.remove(convocatoriaEliminar);
    }
    
    
}
