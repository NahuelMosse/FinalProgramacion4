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

   public boolean hasPostulantes() {
        return postulados.size() > 0;
   }

   public void darDeBajaPostulante(int legajoPostulante) {
        Empleado postulante = this.buscarPostulante(legajoPostulante);

        if (postulante == null) {
            Logger.logError("La convocatoria no tiene un postulante con legajo " + legajoPostulante);
        } else {
            postulados.remove(postulante);

            Logger.logSuccess("El postulante con legajo " + legajoPostulante + " se ha dado de baja de la convocatoria con exito");
        }
   }


   private Empleado buscarPostulante(int legajoPostulante) {
    int i = 0;

    while(i<postulados.size() && !postulados.get(i).hasLegajo(legajoPostulante))
        i++;
    
    if(i<postulados.size())
        return postulados.get(i);
    else
        return null;
}

}
