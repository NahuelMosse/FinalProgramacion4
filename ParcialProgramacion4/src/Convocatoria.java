import java.util.ArrayList;
import utilidades.Fecha;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public abstract class Convocatoria {
    Scanner scanner = new Scanner(System.in);

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
                if(this.empCumpleAnnosConvocatoriaJerarquica(empleadoInscribir)){ //me parece que ya esta puesto en ConvocatoriaJerarquico, no es necesario aca
                    //Cumple con los annos requeridos para el puesto (3)
                    

                    //condicion 4: comparar hashtables de requisitos de la convocatoria y las habilidades del empleado
                    //dar responsabilidad al empleado que tiene las habilidades
                    if (empleadoInscribir.cumpleRequisitos(requisitos)) {
                        return true; // puede inscribirse
                    } else {
                        return false;
                    }

                }
                
            }
        }

        return false; // verificar si esta bien poner el return aca, si no produce errores
    }

    //1. empleadoInscribir no puede estar en lista de postulados o asignados
    public boolean empEstaInscripto(Empleado empleadoInscribir){
        /* 
        for(Empleado empAsig:asignados){
                if(empAsig == empleadoInscribir)
                    return true; //El empleado esa inscripto
        }
            return false;
        */
        //version alternativa, usando while
        int i = 0;
        while (i<postulados.size() && (postulados.get(i) != empleadoInscribir)) {
            i++;
        }

        if(i<postulados.size()) {
            return true;
        } else {
            return false;
        }
    }

    //2. Si su cargo actual es de un puesto jerarquico, debe estar hace 4 años (VC) en este puesto
    public abstract boolean empAnnosSuficientesCargoActual(Empleado empleadoInscribir);

    //3. empleadoInscribir debe cumplir con los requisitos de la convocatoria jerarquica
    public abstract boolean empCumpleAnnosConvocatoriaJerarquica(Empleado empleadoInscribir);

    
   

    public void mostrar() {
        System.out.println("-----------------");
        System.out.println("Codigo: " + codigo);
        puesto.mostrar();
        System.out.println("Fecha: " + fecha.getDia() + "/" + fecha.getMes() + "/" + fecha.getAño());
        System.out.println("Requsitos: ");

        //RECORRER HASHTABLE DE REQUISITOS MOSTRANDO KEY Y VALUE
        Habilidad habilidad;
        Enumeration<Habilidad> enumReq = requisitos.keys();
        while (enumReq.hasMoreElements()) {
            habilidad = enumReq.nextElement();
            habilidad.mostrar();
            System.out.println("Años de experiencia requeridos: " + requisitos.get(habilidad));
        }

        System.out.println("-----------------");
    }


    public void elegirEmpleadosConvocatoria() {
        if (this.estaAbierta()) {
            System.out.println("Postulantes: ");
            this.mostrarPostulados();

            System.out.println("\n----------------");
            System.out.println("Elegir postulantes: ");

            Empleado empleadoAsignar;
            String sigo="SI";

            while (quedaCupo() && sigo.equalsIgnoreCase("SI")) { //llamo para ver si puedo seguir inscribiendo postulantes a asignados
                System.out.println("numero de legajo: ");
                int legajoEmpleado = Integer.parseInt(scanner.nextLine());

                empleadoAsignar = this.buscarPostulante(legajoEmpleado);

                if (empleadoAsignar != null) {
                    //SE CONSIDERA QUE LA ASIGNACION SE REGISTRA EN LA FECHA EN LA QUE SE REALIZA
                    //FECHA DE HOY ES CUANDO ASUME EL NUEVO CARGO
                    //PORQUE SINO SE DEBEN ELIMINAR EN LOS REGISTROS EL DIA EN QUE EL EMPLEADO CAMBIA DE PUESTO

                    //registro en la convocatoria
                    asignados.add(empleadoAsignar);
                    postulados.remove(empleadoAsignar);

                    //registro en el puesto y en el empleado con su nuevo cargo
                    empleadoAsignar.getPuestoActual().eliminarEmpleado(empleadoAsignar); //elimino en el puesto viejo al empleado
                    puesto.agregarEmpleado(empleadoAsignar); //agrego al puesto de la convocatoria el nuevo empleado

                    empleadoAsignar.nuevoCargo(puesto); //el empleado cierra el cargo

                    System.out.println("Postulante asignado con exito");

                } else {
                    System.out.println("ERROR: NO se han encontrado postulantes con ese legajo");
                }

                System.out.println("Quiere asignar otro empleado? (SI/NO)");
                sigo = scanner.nextLine();
            }

            if (!quedaCupo()) {
                System.out.println("La convocatoria se cerro, ya no hay mas cupos");
            }

        } else {
            System.out.println("La convocatoria esta cerrada!");
        }
    }

    public boolean estaAbierta() {
        boolean noPasoFecha = Fecha.hoy().compareTo(fecha) <= 0;
        return noPasoFecha && this.quedaCupo(); 
    }

    public void mostrarPostulados() {
        for(Empleado empleado: postulados) {
            empleado.mostrar();
        }
    }

    public boolean quedaCupo() {
        return asignados.size() < cantEmpleadosRequeridos;
    }

    private Empleado buscarPostulante(int legajo) {
        int i = 0;

        while(i<postulados.size() && !postulados.get(i).hasEmpleado(legajo))
            i++;
        
        if(i<postulados.size())
            return postulados.get(i);
        else
            return null;
    }
}
