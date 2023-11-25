import java.util.ArrayList;

public abstract class Puesto {
    private String nombre;
    private double sueldo;
    private ArrayList<Convocatoria>convocatorias;
    private ArrayList<Empleado>empleados;

    public abstract boolean esJerarquico();

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

    public void mostrarConvocatoriasQueSePuedaInscribir(Empleado empleado) {
        for(Convocatoria convocatoria: convocatorias) {
            if(convocatoria.empleadoPuedeInscribirse(empleado)) {
                convocatoria.mostrar();
            }
        }
    }

    public void mostrarme() {
        System.out.println("nombre puesto: "+nombre);
        System.out.println("sueldo puesto: "+sueldo);
    }
}
