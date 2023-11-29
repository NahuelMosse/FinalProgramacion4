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
    
    public void darDeBajaConvocatoria(Convocatoria convocatoriaEliminar) {
        this.convocatorias.remove(convocatoriaEliminar);
    }


    public void mostrarConvocatoriasPuedeAplicar(Empleado empleadoAplicar) {
        //misma logica que metodos de Empresa, solo que esta en Puesto
        if (!this.hayConvocatoriasAbiertas()) {
            Logger.logSuccess("Lo sentimos, NO existen convocatoria abiertas");
        } else {
            int cantPuedeAplicar = this.cantConvocatoriasPuedeAplicar(empleadoAplicar);

            if (cantPuedeAplicar == 0) {
                Logger.logSuccess("Lo sentimos, no puede aplicar a NINGUNA convocatoria para el puesto de " + nombre);
            } else {
                Logger.header("Convocatorias disponibles para " + nombre);
                for (Convocatoria convocatoria: convocatorias) {
                    if (convocatoria.puedeAplicar(empleadoAplicar)) {
                        convocatoria.mostrar();
                    }
                }
            }
        }
    }

    private int cantConvocatoriasPuedeAplicar(Empleado empleadoAplicar) {
        int i = 0;
        for (Convocatoria convocatoria: convocatorias) {
            if (convocatoria.puedeAplicar(empleadoAplicar)) {
                i++;
            }
        }
        return i;
    }


    public boolean dentroDeRango(float salarioMin, float salarioMax) {
        boolean superaMin = sueldo >= salarioMin;
        boolean noSuperaMax = sueldo <= salarioMax;
        return superaMin && noSuperaMax;
    }


    public boolean jerarquicoCumpleAnnosMinimos(int annosEnPuesto) {
        return true; //comparo en PuestoJerarquico, aca no hay condicion
    }

    private boolean hayConvocatoriasAbiertas() {
        int i = 0;
        for (Convocatoria convocatoria : convocatorias) {
            if (convocatoria.estaAbierta()) {
                i++;
            }
        }
        return i > 0;
    }
}

