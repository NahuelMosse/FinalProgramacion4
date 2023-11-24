import java.util.ArrayList;
import utilidades.Fecha;
import java.util.Enumeration;
import java.util.Hashtable;

public abstract class Convocatoria {
    private int codigo;
    private ArrayList<Empleado>postulados;
    private Puesto puesto;
    private Fecha fecha;
    private int cantEmpleadosRequeridos;
    private Hashtable<Habilidad,Integer>requisitos;
    private ArrayList<Empleado>asignados;

    public Convocatoria(int codigo, Puesto puesto, Fecha fecha, int cantEmpleadosRequeridos, Hashtable<Habilidad,Integer> requisitos) {
        this.codigo = codigo;
        this.postulados = new ArrayList<Empleado>();
        this.puesto = puesto;
        this.fecha = fecha;
        this.cantEmpleadosRequeridos = cantEmpleadosRequeridos;
        this.requisitos = requisitos;
        this.asignados = new ArrayList<Empleado>();
    }

    public boolean hasConvocatoria(int codigo) {
        return this.codigo == codigo;
    }

    public void inscribirEmpleado(Empleado empleadoInscribir) {
        //si se llama a este metodo en el caso de inscribirEmpleadoConvocatoria, se repite la verificacion
        //se podria dejar por si se lo usa para otro caso de uso de inscribir empleado a una convocatoria que ya sepa cual es el codigo
        //que pida inscribirse directamente a esa convocatoria
        if(this.empleadoPuedeInscribirse(empleadoInscribir)) {
            postulados.add(empleadoInscribir);
        } else
            System.out.println("El empleado no puede inscribirse a la convocatoria con codigo: "+codigo);
    }

    public boolean empleadoPuedeInscribirse(Empleado empleadoInscribir) {
        //FALTA HACER
        //ademas de verificar que empleado cumpla con requsitos, los años, etc. TAMBIEN FIJARSE QUE YA NO ESTE ESCRITO
        //PORQUE SINO PUEDE QUEDAR INSCRIPTO DOS VECES
        return true;
    }

    public void mostrarme() {
        System.out.println("-----------------");
        System.out.println("codigo: "+codigo);
        puesto.mostrarme();
        System.out.println("Fecha: "+fecha.getDia()+"/"+fecha.getMes()+"/"+fecha.getAño());
        System.out.println("Requsitos: ");
        //FALTA HACER, RECORRER HASHTABLE DE REQUISITOS MOSTRANDO KEY Y VALUE


        System.out.println("-----------------");
    }
}
