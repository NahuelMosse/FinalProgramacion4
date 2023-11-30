import java.util.ArrayList;
import utilidades.Fecha;
import java.util.Enumeration;
import java.util.Hashtable;
import utilidades.Logger;

public abstract class Convocatoria {
    private int codigo;
    private ArrayList<Empleado>postulados;
    private Puesto puesto;
    private Fecha fecha;
    private int cantEmpleadosRequeridos;
    private Hashtable<Habilidad,Integer>requisitos;
    private ArrayList<Empleado>asignados;

    public Convocatoria(
        int codigo,
        Puesto puesto,
        Fecha fecha,
        int cantEmpleadosRequeridos,
        Hashtable<Habilidad, Integer> requisitos
    ) {
        this.codigo = codigo;
        this.postulados = new ArrayList<Empleado>();
        this.puesto = puesto;
        this.fecha = fecha;
        this.cantEmpleadosRequeridos = cantEmpleadosRequeridos;
        this.requisitos = requisitos;
        this.asignados = new ArrayList<Empleado>();
    }
    
    public void mostrarConPostulantesAsignados() {
        this.mostrar();
        //agrego informacion de cada postulante y asignado, si no hay postulantes o asingados, no los recorro y muestro msj informandolo
        if (postulados.size() > 0) {
            
            for(Empleado empleado: postulados) {
                empleado.mostrar();
            }

        } else {
            System.out.println("No hay postulantes");
        }

        if (asignados.size() > 0) {
            
            for(Empleado empleado: asignados) {
                empleado.mostrar();
            }

        } else {
            System.out.println("Aun no hay asignados");
        }
    }

    public void mostrar() {
        Logger.header("Convocatoria " + codigo);

        puesto.mostrar();

        System.out.println("Fecha convocatoria: " + fecha.getDia() + " / " + fecha.getMes() + " / " + fecha.getAño());

        System.out.println("Cantidad de empleados requeridos: " + cantEmpleadosRequeridos);

        System.out.println("Hay " + postulados.size() + " postulantes registrados");
        System.out.println("Hay " + asignados.size() + " asignados al puesto");

        System.out.println("Requisitos necesarios: ");
        this.mostrarHabilidades();
    }
  
    public boolean hasCodigo(int codigo) {
        return this.codigo == codigo;
    }
   
    public boolean empleadoEstaPostulado(Empleado empleado){
        return postulados.contains(empleado);
    }
    
    public boolean empleadoEstaAsignado(Empleado empleado) {
    	return asignados.contains(empleado);
    }
    
    public boolean eliminarEmpleado(Empleado empleadoEliminar) {
        boolean fuePostulado = postulados.remove(empleadoEliminar);
        boolean fueAsignado = asignados.remove(empleadoEliminar);
        return fuePostulado || fueAsignado;
    }

    public Puesto getPuesto() {
        return this.puesto;
    }

    public boolean estaAbierta() {
        return this.noPasoFecha() && this.quedaCupo();
    }

    public boolean noPasoFecha() {
        return Fecha.hoy().compareTo(this.fecha) <= 0;
    }

    public boolean quedaCupo() {
        return this.asignados.size() < cantEmpleadosRequeridos;
    }

    public void mostrarHabilidades() {
        Habilidad habilidad;
        Enumeration<Habilidad> enumH = requisitos.keys();
        while (enumH.hasMoreElements()) {
            habilidad = enumH.nextElement();

            habilidad.mostrar();

            System.out.println("años de experiencia: " + requisitos.get(habilidad));
        }
   }


   public boolean hasPuesto(Puesto puesto) {
        return this.puesto == puesto;
   }
  
   public boolean tieneRequisito(Habilidad requisitoBuscado) {
        return requisitos.containsKey(requisitoBuscado);
   }

   public void eliminarRequisito(Habilidad requisitoBuscado) {
        requisitos.remove(requisitoBuscado);
   }

}
