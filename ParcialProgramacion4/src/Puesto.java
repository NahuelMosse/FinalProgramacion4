import java.util.ArrayList;
import utilidades.Logger;

public abstract class Puesto {
    private String nombre;
    private float sueldo;
    private ArrayList<Convocatoria> convocatorias;
    private ArrayList<Empleado> empleados;

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
        Logger.logSuccess("Empleado nuevo agregado con exito a la lista de " + nombre);
    }

    public void agregarEmpleadoPorConvocatoria(Empleado empleadoNuevo) {
        empleados.add(empleadoNuevo); // solo cambia con metodo agregarEmpleado que no muestro mensaje
    }

    public void mostrar() {
        String tipo;
        if (this.esJerarquico()) {
            tipo = "Jerarquico";
        } else {
            tipo = "No jerarquico";
        }

        System.out.println(nombre + " | sueldo: " + sueldo + " | tipo: " + tipo);
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

    public void eliminarEmpleado(Empleado empleadoEliminar) {
        empleados.remove(empleadoEliminar);
    }

    public void darDeBajaConvocatoria(Convocatoria convocatoriaEliminar) {
        this.convocatorias.remove(convocatoriaEliminar);
    }

    public String getNombre() {
        return nombre;
    }

    public void mostrarConvocatoriasPuedeAplicar(Empleado empleadoAplicar) {
        // misma logica que metodos de Empresa, solo que esta en Puesto

        ArrayList<Convocatoria> convocatoriaPuedeAplicar = convocatoriasPuedeAplicar(empleadoAplicar);

        if (convocatoriaPuedeAplicar.size() == 0) {
            Logger.logSuccess("Lo sentimos, no puede aplicar a NINGUNA convocatoria para el puesto de " + nombre);
        } else {
            Logger.header("Convocatorias disponibles para " + nombre);
            for (Convocatoria convocatoria : convocatoriaPuedeAplicar) {
                convocatoria.mostrar();
            }
        }

    }

    private ArrayList<Convocatoria> convocatoriasPuedeAplicar(Empleado empleadoAplicar) {
        ArrayList<Convocatoria> convocatoriasPuedeAplicar = new ArrayList<>();

        for (Convocatoria convocatoria : convocatorias) {
            if (convocatoria.puedeAplicar(empleadoAplicar)) {
                convocatoriasPuedeAplicar.add(convocatoria);
            }
        }

        return convocatoriasPuedeAplicar;
    }

    public boolean dentroDeRango(float salarioMin, float salarioMax) {
        return (sueldo >= salarioMin) && (sueldo <= salarioMax);
    }

    public boolean jerarquicoCumpleAnnosMinimos(int annosEnPuesto) {
        return true; // comparo en PuestoJerarquico, aca no hay condicion
    }

}
