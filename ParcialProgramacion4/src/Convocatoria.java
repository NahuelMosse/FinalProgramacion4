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

    public boolean hasCodigo(int codigo) {
        return this.codigo == codigo;
    }

    public void inscribirEmpleado(Empleado empleadoInscribir) {
        boolean empleadoPuedeInscribirse = this.empleadoPuedeInscribirse(empleadoInscribir);
        if(empleadoPuedeInscribirse) {
            this.postulados.add(empleadoInscribir);
            System.out.println("Inscripto a la convocatoria con codigo: " + codigo);
        } else
            System.out.println("El empleado no puede inscribirse a la convocatoria con codigo: " + codigo);
    }

    public boolean empleadoPuedeInscribirse(Empleado empleadoInscribir) {
        //FALTA HACER
        //ademas de verificar que empleado cumpla con requsitos, los años, etc. TAMBIEN FIJARSE QUE YA NO ESTE ESCRITO
        //PORQUE SINO PUEDE QUEDAR INSCRIPTO DOS VECES
    
        /* condiciones para que se pueda inscribir (en esta clase, en cada una y en convocatoriaJerarquica hay mas)
            1. empleadoInscribir no puede estar en lista de postulados o asignados
            2. empleadoInscribir SI su cargo actual es de un puesto jerarquico, debe estar hace 4 años (VC) en este puesto
            3. empleadoInscribir debe cumplir con los requisitos de la convocatoria jerarquica
            4. en su lista de habilidades debe tener las habilidades y igual o mas años de experiencia en cada una
            

            paso 2 y 3 se gestiona en clase Empleado cuando llamo a empleadoInscribir.puedeInscribirse(requisitos)
        */
        if(!this.empEstaInscripto(empleadoInscribir)){
            //El empleado no esta inscripto (1)

            if(this.empAnnosSuficientesCargoActual(empleadoInscribir)){
                //Si esta en cargo jerarquico cumple con los años (2)
                if(this.empCumpleAnnosConvocatoriaJerarquica(empleadoInscribir)){
                    //Cumple con los annos requeridos para el puesto (3)
                    
                    //FALTA LA CONDICION 4!!!!!!!!!!!!!!!!!!!!!!!!!!

                    return true; //Puede inscribirse
                }
                
            }
        }

        return false;
    }

    //1. empleadoInscribir no puede estar en lista de postulados o asignados
    public boolean empEstaInscripto(Empleado empleadoInscribir){
        for(Empleado empAsig:asignados){
                if(empAsig == empleadoInscribir)
                    return true; //El empleado esa inscripto
            }
            return false;
    }

    //2. Si su cargo actual es de un puesto jerarquico, debe estar hace 4 años (VC) en este puesto
    public abstract boolean empAnnosSuficientesCargoActual(Empleado empleadoInscribir);

    //3. empleadoInscribir debe cumplir con los requisitos de la convocatoria jerarquica
    public abstract boolean empCumpleAnnosConvocatoriaJerarquica(Empleado empleadoInscribir);

    
   

    public void mostrar() {
        System.out.println("-----------------");
        System.out.println("Codigo: " + codigo);
        puesto.mostrarme();
        System.out.println("Fecha: " + fecha.getDia() + "/" + fecha.getMes() + "/" + fecha.getAño());
        System.out.println("Requsitos: ");
        //FALTA HACER, RECORRER HASHTABLE DE REQUISITOS MOSTRANDO KEY Y VALUE


        System.out.println("-----------------");
    }
}
