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
        /* condiciones para que se pueda inscribir (en esta clase, en cada una y en convocatoriaJerarquica hay mas)
            1. El empleadoInscribir no puede estar en lista de postulados o asignados
            2. Solo si empleadoInscribir esta en puesto jerarquico, debe estar hace 4 años (VC) en este puesto. Si no es jerarquico no hay condicion
            3. Solo si empleadoInscribir se postula a puesto jerarquico, debe cumplir n cantidad de annos en la empresa. Si no es jerarquico no hay condicion
            4. en su lista de habilidades debe tener las habilidades y igual o mas años de experiencia en cada una
        */

        boolean puedeInscribirse = false;

        //Condicion 1: El empleadoInscribir no puede estar en lista de postulados o asignados
        if(!this.empEstaInscripto(empleadoInscribir)){

            // Condicion 2: Solo si empleadoInscribir esta en puesto jerarquico, debe estar hace 4 años (VC) en este puesto. Si no es jerarquico no hay condicion
            if(this.empAnnosSuficientesCargoActual(empleadoInscribir)){

                //Condicion 3: Se evaulua en "empleadoPuedeInscribirse" dentro de ConvocatoriaJerarquica
                    
                //Condicion 4: comparar hashtables de requisitos de la convocatoria y las habilidades del empleado
                //dar responsabilidad al empleado que tiene las habilidades
                if (empleadoInscribir.cumpleRequisitos(requisitos)) {
                    puedeInscribirse = true; // puede inscribirse
                }
            }
        }

        return puedeInscribirse; 
    }

    //1. El empleadoInscribir no puede estar en lista de postulados o asignados
    public boolean empEstaInscripto(Empleado empleadoInscribir){
        int i = 0;
        while (i<postulados.size() && (postulados.get(i) != empleadoInscribir)) {
            i++;
        }

        if(i<postulados.size()) 
            return true;
        else 
            return false;
    }

    //2. Solo si empleadoInscribir esta en puesto jerarquico, debe estar hace 4 años (VC) en este puesto. Si no es jerarquico no hay condicion
    public boolean empAnnosSuficientesCargoActual(Empleado empleadoInscribir){
        Cargo cargoActual = empleadoInscribir.getCargoActual();

        if(cargoActual.esJerarquico())
            return cargoActual.tiempoEnCargo() >= PuestoJerarquico.getAnnosMinimosParaCambiar();
        else   
            return true;
    }

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
