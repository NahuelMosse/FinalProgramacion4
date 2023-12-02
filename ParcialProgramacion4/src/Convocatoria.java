import java.util.ArrayList;
import utilidades.Fecha;
import utilidades.InputHelper;
import java.util.Scanner;

import java.util.Enumeration;
import java.util.Hashtable;
import utilidades.Logger;

public abstract class Convocatoria {
    private int codigo;
    private ArrayList<Empleado> postulados;
    private Puesto puesto;
    private Fecha fecha;
    private int cantEmpleadosRequeridos;
    private Hashtable<Habilidad, Integer> requisitos;
    private ArrayList<Empleado> asignados;

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

    public void mostrarConPostulantesYAsignados() {
        this.mostrar();

        this.mostrarPostulantesYAsignados();
    }

    public void mostrarPostulantesYAsignados() {
        if (this.postulados.size() > 0) {

            System.out.println("\n** Postulantes **");
            for (Empleado empleado : postulados) {
                empleado.mostrar();
                Logger.subDivider();
            }

        } else {
            System.out.println("No hay postulantes");
        }

        if (this.asignados.size() > 0) {

            System.out.println("\n** Asignados **");
            for (Empleado empleado : asignados) {
                empleado.mostrar();
                Logger.subDivider();
            }

        } else {
            System.out.println("Aun no hay asignados");
        }
    }

    public void mostrar() {
        Logger.divider();

        System.out.println("Codigo convocatoria: " + codigo);

        System.out.print("Puesto vacante: ");
        puesto.mostrar();

        System.out.println("Fecha convocatoria: " + fecha.getDia() + " / " + fecha.getMes() + " / " + fecha.getAño());

        System.out.println("Cantidad de empleados requeridos: " + cantEmpleadosRequeridos);

        System.out.println("\nHay " + postulados.size() + " postulantes registrados");
        System.out.println("Hay " + asignados.size() + " asignados al puesto\n");

        if (requisitos.size() == 0) {
            System.out.println("No tiene requisitos");
        } else {
            Logger.subDivider();
            System.out.println("Requisitos necesarios: ");
            this.mostrarHabilidades();
            Logger.subDivider();
        }
    }

    public boolean hasCodigo(int codigo) {
        return this.codigo == codigo;
    }

    public boolean empleadoEstaPostulado(Empleado empleado) {
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

            Logger.subDivider();

            habilidad.mostrar();
            System.out.println("Años de experiencia: " + requisitos.get(habilidad));
        }
    }

    public boolean hasPostulantes() {
        return this.postulados.size() > 0;
    }

    public void darDeBajaPostulante(int legajoPostulante) {
        Empleado postulante = this.buscarPostulante(legajoPostulante);

        if (postulante == null) {
            Logger.logError("La convocatoria no tiene un postulante con legajo " + legajoPostulante);
        } else {
            postulados.remove(postulante);

            Logger.logSuccess("El postulante con legajo " + legajoPostulante
                    + " se ha dado de baja de la convocatoria con exito");
        }
    }

    private Empleado buscarPostulante(int legajoPostulante) {
        int i = 0;

        while (i < postulados.size() && !postulados.get(i).hasLegajo(legajoPostulante)) {
            i++;
        }

        if (i < postulados.size()) {
            return postulados.get(i);
        }
        return null;
    }

    public int getCantEmpleadosRequeridos() {
        return this.cantEmpleadosRequeridos;
    }

    public void asignarEmpleado(Empleado empleadoSeleccionado) {
        // como ya se que es postulante, ya se que cumple los requisitos xq paso por el
        // proceso de inscripcion
        postulados.remove(empleadoSeleccionado);
        asignados.add(empleadoSeleccionado);

        empleadoSeleccionado.nuevoCargo(puesto);

        puesto.agregarEmpleadoPorConvocatoria(empleadoSeleccionado);

        Logger.logSuccess("El empleado con legajo " + empleadoSeleccionado.getLegajo()
                + " ha sido asignado al puesto de " + puesto.getNombre());
    }

    public boolean esPostulante(Empleado empleado) {
        return postulados.contains(empleado);
    }

    public int getCantRestante() {
        return cantEmpleadosRequeridos - asignados.size();
    }

    public boolean puedeAplicar(Empleado empleadoAplicar, Scanner scanner) {
        return (!this.estaInscripto(empleadoAplicar)) && this.estaAbierta() && empleadoAplicar.puedeAplicar(requisitos, scanner);
    }

    public boolean estaInscripto(Empleado empleado) {
        return postulados.contains(empleado) || asignados.contains(empleado);
    }

    public boolean dentroDeRango(float salarioMin, float salarioMax) {
        return puesto.dentroDeRango(salarioMin, salarioMax);
    }

    public boolean hasPuesto(Puesto puesto) {
        return this.puesto == puesto;
    }

    public boolean tieneRequisito(Habilidad requisitoBuscado) {
        return requisitos.containsKey(requisitoBuscado);
    }

    public void tryEliminarRequisito(Habilidad requisitoBuscado) {
        requisitos.remove(requisitoBuscado);

    }

    public void inscribirEmpleado(Empleado empleado) {
        // ya verifique si podria inscribirse o no en empresa, solo lo agrego a
        // postulados
        postulados.add(empleado);
    }

    public void informarCantidadRestante() {
        Logger.logSuccess("Aun puede asignar a " + this.getCantRestante() + " postulantes al puesto de "
                + puesto.getNombre() + " para esta convocatoria");
    }
    
    public void editarFecha(Fecha fechaConvocatoria) {
    	this.fecha = fechaConvocatoria;
    }
    
    public void editarCantEmpleadosRequeridos(Scanner scanner) {
    	int cantEmpleadosRequeridos = InputHelper.scanInt(scanner,"Ingrese la cantidad de empleados requeridos para la convocatoria:");
    	
    	while(cantEmpleadosRequeridos < this.asignados.size()) {
    		System.out.println("Error, no se puede asignar una cantidad de empleados menor a la cantidad de asignados en la convocatoria");
    		cantEmpleadosRequeridos = InputHelper.scanInt(scanner,"Ingrese la cantidad de empleados requeridos para la convocatoria:");
    	}
    	this.cantEmpleadosRequeridos = cantEmpleadosRequeridos;
    }
    
    public void editarRequisitos(Hashtable<Habilidad, Integer> requisitos) {
    	this.requisitos = requisitos;
    }


    public void terminar() {
        if (this.cantEmpleadosRequeridos == this.asignados.size()) {
            Logger.logError("La convocatoria ya estaba cerrada");
        } else {
            this.cantEmpleadosRequeridos = this.asignados.size();

            Logger.logSuccess("Convocatoria cerrada");
        }
    }

    public boolean estaCerradaConEmpleadoAsignado(Empleado empleado) {
        return !this.estaAbierta() || this.empleadoEstaAsignado(empleado);
    }

    public boolean estaAbiertaConEmpleadoPostulado(Empleado empleado) {
        return this.estaAbierta() && this.empleadoEstaPostulado(empleado);
    }

    public boolean estaAbiertaConEmpleadoAsignado(Empleado empleado) {
        return this.estaAbierta() && this.empleadoEstaAsignado(empleado);
    }

    public boolean puedeAplicarYcumpleExpectativaSalario(
        Empleado empleadoAplicar,
        float salarioMin,
        float salarioMax,
        Scanner scanner
    ) {
        return this.puedeAplicar(empleadoAplicar, scanner) && this.dentroDeRango(salarioMin, salarioMax);
    }

}
