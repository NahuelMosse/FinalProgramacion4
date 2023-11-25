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

    public boolean hasConvocatoria(int codigo) {
        return this.codigo == codigo;
    }

    public void inscribirEmpleado(Empleado empleadoInscribir) {
        if(this.empleadoPuedeInscribirse(empleadoInscribir)) {
            postulados.add(empleadoInscribir);
            System.out.println("inscripto a la convocatoria con codigo: "+codigo);
        } else
            System.out.println("El empleado no puede inscribirse a la convocatoria con codigo: "+codigo);
    }

    public boolean empleadoPuedeInscribirse(Empleado empleadoInscribir) {
        //FALTA HACER
        //ademas de verificar que empleado cumpla con requsitos, los años, etc. TAMBIEN FIJARSE QUE YA NO ESTE ESCRITO
        //PORQUE SINO PUEDE QUEDAR INSCRIPTO DOS VECES
    
        /* condiciones para que se pueda inscribir (en esta clase, en cada una y en convocatoriaJerarquica hay mas)
            1. empleadoInscribir no puede estar en lista de postulados o asignados
            2. empleadoInscribir debe cumplir con los requisitos de la convocatoria
                en su lista de habilidades debe tener las habilidades y igual o mas años de experiencia en cada una
            3. empleadoInscribir SI su cargo actual es de un puesto jerarquico, debe estar hace 4 años (VC) en este puesto

            paso 2 y 3 se gestiona en clase Empleado cuando llamo a empleadoInscribir.puedeInscribirse(requisitos)
        */

        return true; //momentaneo para que no de error
    }

    public void mostrar() {
        System.out.println("-----------------");
        System.out.println("codigo: "+codigo);
        puesto.mostrarme();
        System.out.println("Fecha: "+fecha.getDia()+"/"+fecha.getMes()+"/"+fecha.getAño());
        System.out.println("Requsitos: ");
        //FALTA HACER, RECORRER HASHTABLE DE REQUISITOS MOSTRANDO KEY Y VALUE


        System.out.println("-----------------");
    }
}
