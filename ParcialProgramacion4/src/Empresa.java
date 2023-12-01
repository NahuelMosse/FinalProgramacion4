import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import utilidades.Fecha;
import utilidades.InputHelper;
import utilidades.Logger;

public class Empresa {
    private Scanner scanner;
    private ArrayList<Empleado> empleados;
    private ArrayList<Puesto> puestos;
    private ArrayList<Convocatoria> convocatorias;
    private ArrayList<Habilidad> habilidades;

    public Empresa(Scanner scanner) {
        this.scanner = scanner;

        this.empleados = new ArrayList<Empleado>();
        this.puestos = new ArrayList<Puesto>();
        this.convocatorias = new ArrayList<Convocatoria>();
        this.habilidades = new ArrayList<Habilidad>();
    }

    // CASO DE USO DAR DE BAJA EMPLEADO
    public void darDeBajaEmpleado() {
        Logger.header("Dar de baja empleado");

        int legajo = InputHelper.scanInt(scanner, "Numero de legajo empleado a dar de baja: ");

        Empleado empleadoEliminar = this.buscarEmpleado(legajo);

        if (empleadoEliminar == null) {
            System.out.println("ERROR: No existe empleado con el numero de legajo " + legajo);
        } else {
            Logger.divider();
            System.out.println("Información empleado a eliminar: ");
            empleadoEliminar.mostrar();
            Logger.divider();

            // determinar si el empleado esta ASIGNADO en alguna convocatoria historica
            boolean eliminarEmpleado = true;
            boolean estaAsignadoEnHistorica = this.empleadoAsignadoEnConvocatoriaHistoria(empleadoEliminar);

            if (estaAsignadoEnHistorica) {
                Logger.logWarning("El empleado con legajo " + legajo
                    + "esta registrado en convocatorias historicas, NO es recomendable eliminarlo");
            }

            int cantInscripciones = this.cantInscripcionesEmpleadoConvocatoriasAbiertas(empleadoEliminar);

            if (cantInscripciones > 0) {
                Logger.logWarning("El empleado esta inscripto en " + cantInscripciones + " convocatorias abiertas");
            }

            int cantAsignaciones = this.cantAsignacionesEmpleadoConvocatoriasAbiertas(empleadoEliminar);
            if (cantAsignaciones > 0) {
                Logger.logWarning("El empleado esta asignado en " + cantAsignaciones + " convocatorias abiertas");
            }

            // si sucede alguna de las 3 ultimas situaciones pregunto para confirmar
            if (estaAsignadoEnHistorica || cantInscripciones > 0 || cantAsignaciones > 0) {
                eliminarEmpleado = InputHelper.yesOrNoInput(scanner, "Quiere eliminarlo de todas formas?");
            }

            if (eliminarEmpleado) {
                // antes de eliminarlo de la lista de empresa, lo eliminamos de la lista de su
                // puesto actual
                Puesto puestoEmpleado = empleadoEliminar.getPuestoActual();
                puestoEmpleado.eliminarEmpleado(empleadoEliminar);

                // eliminar de convocatorias
                int cantConvocatorias = this.eliminarEmpleadoDeConvocatorias(empleadoEliminar);

                if (cantConvocatorias > 0) {
                    Logger.logSuccess(
                        "El empleado con legajo " + legajo + " ha sido eliminado de " + cantConvocatorias);
                } else {
                    Logger.logSuccess("El empleado con legajo " + legajo
                        + " NO ha sido eliminado de convocatorias porque no esta inscripto o asignado en ninguna");
                }

                // eliminar cargos
                empleadoEliminar.eliminarCargos();

                // ahora se elimina de la lista de la empresa
                empleados.remove(empleadoEliminar);

                Logger.logSuccess("Empleado eliminado con exito");
            } else {
                Logger.logError("Empleado NO ha sido eliminado");
            }
        }
    }

    private boolean empleadoAsignadoEnConvocatoriaHistoria(Empleado empleado) {
        int i = 0;

        while (
            i < convocatorias.size() &&
            !convocatorias.get(i).estaCerradaConEmpleadoAsignado(empleado)
        ) {
            i++;
        }

        return i < convocatorias.size();
    }

    private int cantInscripcionesEmpleadoConvocatoriasAbiertas(Empleado empleado) {
        int i = 0;

        for (Convocatoria convocatoria : convocatorias) {
            if (convocatoria.estaAbiertaConEmpleadoPostulado(empleado)) {
                i++;
            }
        }

        return i;
    }

    private int cantAsignacionesEmpleadoConvocatoriasAbiertas(Empleado empleado) {
        int i = 0;

        for (Convocatoria convocatoria : convocatorias) {
            if (convocatoria.estaAbiertaConEmpleadoAsignado(empleado)) {
                i++;
            }
        }

        return i;
    }

    private int eliminarEmpleadoDeConvocatorias(Empleado empleado) {
        int i = 0;

        for (Convocatoria convocatoria : convocatorias) {
            // devuelve true si lo elimino
            if (convocatoria.eliminarEmpleado(empleado)) {
                i++;
            }
        }

        return i;
    }

    public Habilidad crearUnaHabilidad() {
        Logger.header("Formulario para crear una habilidad");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        Habilidad habilidadExistente = this.buscarHabilidad(nombre);

        Habilidad habilidadNueva = null;

        if (habilidadExistente != null) {
            Logger.logError("Ya existe una habilidad con este nombre");

            boolean continuar = InputHelper.yesOrNoInput(scanner, "Desea ingresar otro nombre?");

            if (continuar) {
                habilidadNueva = this.crearUnaHabilidad();
            }
        } else {
            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();

            habilidadNueva = new Habilidad(nombre, descripcion);

            this.habilidades.add(habilidadNueva);

            Logger.logSuccess("Habilidad registrada con exito");
        }

        return habilidadNueva;
    }

    public Habilidad crearUnaHabilidad(String nombre) {
        Logger.header("Formulario para crear una habilidad");

        Habilidad habilidadExistente = this.buscarHabilidad(nombre);

        Habilidad habilidadNueva = null;

        if (habilidadExistente != null) {
            Logger.logError("Ya existe una habilidad con este nombre");

            boolean continuar = InputHelper.yesOrNoInput(scanner, "Desea ingresar otro nombre?");

            if (continuar) {
                habilidadNueva = this.crearUnaHabilidad();
            }
        } else {
            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();

            habilidadNueva = new Habilidad(nombre, descripcion);

            this.habilidades.add(habilidadNueva);

            Logger.logSuccess("Habilidad registrada con exito");
        }

        return habilidadNueva;
    }

    public void agregarPuesto() {
        Logger.header("Formulario para crear un nuevo puesto de trabajo");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        Puesto puestoExistente = this.buscarPuesto(nombre);

        if (puestoExistente != null) {
            Logger.logError("Ya se encuentra registrado un puesto con ese nombre");

            boolean continuar = InputHelper.yesOrNoInput(scanner, "Desea ingresar otro nombre?");

            if (continuar) {
                this.agregarPuesto();
            }
        } else {

            float sueldo = InputHelper.scanFloat(scanner, "Sueldo: ");

            boolean esJerarquico = InputHelper.yesOrNoInput(scanner, "Es un puesto jerarquico?");

            Puesto puestoNuevo;

            if (esJerarquico) {
                puestoNuevo = new PuestoJerarquico(nombre, sueldo);
            } else {
                puestoNuevo = new PuestoNoJerarquico(nombre, sueldo);
            }

            this.puestos.add(puestoNuevo);

            Logger.logSuccess("Puesto añadido con exito");
        }
    }

    private Habilidad buscarHabilidad(String nombre) {
        int i = 0;

        while (i < habilidades.size() && !habilidades.get(i).hasNombre(nombre)) {
            i++;
        }

        if (i < habilidades.size()) {
            return habilidades.get(i);
        }

        return null;
    }

    private Puesto buscarPuesto(String nombre) {
        int i = 0;

        while (i < puestos.size() && !puestos.get(i).hasNombre(nombre)) {
            i++;
        }

        if (i < puestos.size()) {
            return puestos.get(i);
        }

        return null;
    }

    // CU- MOSTRAR CONVOCATORIAS ABIERTAS
    public void mostrarConvocatoriasAbiertas() {
        Logger.header("Convocatorias abiertas");

        if (!this.hayConvocatoriasAbiertas()) {
            Logger.logSuccess("Lo sentimos, por el momento no hay convocatorias abiertas");
        } else {
            boolean quiereVerPostulantesAsignados = InputHelper.yesOrNoInput(scanner,
                    "Quiere ver los datos de cada postulante y asignado?");

            if (quiereVerPostulantesAsignados) {
                for (Convocatoria convocatoria : convocatorias) {
                    if (convocatoria.estaAbierta()) {
                        convocatoria.mostrarConPostulantesYAsignados();
                    }
                }
            } else {
                for (Convocatoria convocatoria : convocatorias) {
                    if (convocatoria.estaAbierta()) {
                        convocatoria.mostrar();
                    }
                }
            }
        }
    }

    // determino si hay convocatorias estan abiertas, si no hay ninguno se lo
    // informo al usuario en mostrarConvocatoriasAbiertas()
    public boolean hayConvocatoriasAbiertas() {
        int i = 0;
        
        while (i < convocatorias.size() && !convocatorias.get(i).estaAbierta()) {
            i++;
        }

        return i < convocatorias.size();
    }

    // CASO DE USO AGREGAR EMPLEADO AL SISTEMA
    public void agregarEmpleado() {
        Logger.header("Formulario para ingresar empleado");

        int legajo = InputHelper.scanInt(scanner, "Numero de legajo: ");

        Empleado empleadoRepetido = this.buscarEmpleado(legajo);

        if (empleadoRepetido != null) {
            Logger.logError("Ya existe un empleado con ese numero de legajo");

            boolean continuar = InputHelper.yesOrNoInput(scanner, "Desea probar con otro numero de legajo? ");

            if (continuar) {
                this.agregarEmpleado();
            }
        } else {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.println("Fecha de nacimiento: ");
            Fecha fechaNacimiento = Fecha.nuevaFecha();

            while (fechaNacimiento.compareTo(Fecha.hoy()) > 0) {
                Logger.logError("La fecha de nacimiento debe ser anterior al dia de hoy");
                System.out.println("Fecha de nacimiento: ");
                fechaNacimiento = Fecha.nuevaFecha();
            }

            System.out.println("Fecha de ingreso a la empresa: ");
            Fecha fechaIngreso = Fecha.nuevaFecha();

            while (!fechaIngreso.entre(fechaNacimiento, Fecha.hoy())) {

                if (fechaIngreso.compareTo(Fecha.hoy()) > 0) {
                    Logger.logError("La fecha de ingreso NO debe ser posterior al dia de hoy");
                } else {
                    Logger.logError("La fecha de ingreso DEBE ser posterior a la fecha de nacimiento");
                }

                System.out.println("Fecha de ingreso a la empresa: ");
                fechaIngreso = Fecha.nuevaFecha();
            }

            // INGRESAR TODOS LOS CARGOS QUE EMPLEADO OCUPO HASTA AHORA
            ArrayList<Cargo> historialDeCargos = this.pedirListaCargos(fechaIngreso);

            // crear hashtable con las habilidades y años de experiencia
            Hashtable<Habilidad, Integer> habilidades = new Hashtable<>();

            if (InputHelper.yesOrNoInput(scanner, "\nTiene habilidades para ingresar?")) {
                habilidades = this.pedirListaHabilidades("del empleado");
            }

            // constructor empleado
            Empleado empleadoNuevo = new Empleado(
                legajo,
                nombre,
                apellido,
                fechaNacimiento,
                fechaIngreso,
                historialDeCargos,
                habilidades
            );

            // agrego al empleado en el puesto actual
            Puesto puestoActual = empleadoNuevo.getPuestoActual();

            puestoActual.agregarEmpleado(empleadoNuevo);

            // agregar empleado a lista de empresa
            empleados.add(empleadoNuevo);

            Logger.logSuccess("Empleado agregado a lista de la empresa");
        }

    }

    private ArrayList<Cargo> pedirListaCargos(Fecha fechaIngresoEmpresa) {
        // INGRESAR TODOS LOS CARGOS QUE UN EMPLEADO OCUPO HASTA AHORA
        Logger.header("Ingreso de cargos: ");

        System.out.println("Primero se pediran los cargos ANTIGUOS que ocupo (Se ingresa SI en caso de que los tenga)");
        System.out.println(
            "Cuando usted lo decida, ingresara el cargo ACTUAL (Ingresando NO a 'tiene puestos antiguos?')\n");

        // creo un arraylist local para el historial de cargos para pasarle al
        // constructor de empleado (es lo que retorno)
        ArrayList<Cargo> historialDeCargos = new ArrayList<Cargo>();

        // primero ingreso los cargos antiguos
        boolean tienePuestoAntiguo = InputHelper.yesOrNoInput(scanner, "Tiene puestos ANTIGUOS?");

        if (tienePuestoAntiguo) {
            historialDeCargos = this.pedirListaCargosAntiguos(fechaIngresoEmpresa);
        }

        // INGRESAR CARGO ACTUAL, SOLO PREGUNTA EL PUESTO
        Cargo cargoActual = this.pedirCargoActual(fechaIngresoEmpresa, historialDeCargos);

        // agrego en arraylist local
        historialDeCargos.add(cargoActual);

        Logger.logSuccess("Cargo actual agregado");

        return historialDeCargos; // para usarlo en el constructor de Empleado
    }

    // INGRESO CARGOS ANTIGUOS
    private ArrayList<Cargo> pedirListaCargosAntiguos(Fecha fechaIngresoEmpresa) {
        Logger.header("Ingreso cargos antiguos");

        ArrayList<Cargo> historialDeCargos = new ArrayList<Cargo>();

        System.out.println("\nRECORDAR: Los cargos antiguos se deben ingresar en orden empezando desde el mas antiguo\n");

        boolean tieneCargoAntiguo = true;

        do {
            System.out.print("Nombre puesto: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puesto = this.buscarPuesto(nombrePuesto);

            // SI NO EXISTE, SE LE PREGUNTA SI LO QUIERE AGREGAR:
            while (puesto == null) {
                boolean agregarPuesto = InputHelper.yesOrNoInput(scanner, "El puesto no existe, quiere agregarlo?");

                if (agregarPuesto) {
                    puesto = this.agregarPuesto(nombrePuesto);
                } else {
                    System.out.print("Nombre puesto: ");
                    nombrePuesto = scanner.nextLine();

                    puesto = this.buscarPuesto(nombrePuesto);
                }
            }

            // determinar la fecha de inicio en el cargo
            // si es el primer cargo, es la misma que la fecha de ingreso a la empresa
            // si no es la fecha de fin del ultimo cargo ingresado
            Fecha fechaInicio;

            if (historialDeCargos.size() > 0) { // ya se ingresaron cargos antiguos
                fechaInicio = historialDeCargos.get(historialDeCargos.size() - 1).getFechaFin();
            } else { // es el primer cargo que se ingresa
                fechaInicio = fechaIngresoEmpresa;
            }

            System.out.println("\nFecha en la que finalizo el cargo: ");
            Fecha fechaFin = Fecha.nuevaFecha();

            // comprobaciones fecha fin sea correcta
            
            while (!fechaFin.entre(fechaInicio, Fecha.hoy())) {

                if (fechaFin.compareTo(fechaInicio) <= 0) {
                    Logger.logError("La fecha de fin debe ser posterior a la fecha de inicio: ");
                    System.out.println("\nFecha en la que finalizo el cargo: ");
                    fechaFin = Fecha.nuevaFecha();
                } else {
                    Logger.logError("La fecha de fin debe ser anterior al dia de hoy: ");
                    System.out.println("\nFecha en la que finalizo el cargo: ");
                    fechaFin = Fecha.nuevaFecha();
                }

            }

            Cargo nuevoCargo = new Cargo(fechaInicio, fechaFin, puesto);

            // agrego en arraylist local
            historialDeCargos.add(nuevoCargo);

            Logger.logSuccess("Cargo antiguo agregado");

            tieneCargoAntiguo = InputHelper.yesOrNoInput(scanner, "Tiene MAS cargos ANTIGUOS?");

        } while (tieneCargoAntiguo);

        return historialDeCargos;
    }

    private Cargo pedirCargoActual(Fecha fechaIngresoEmpresa, ArrayList<Cargo> historialDeCargos) {
        Logger.header("Ingreso cargo actual");

        System.out.print("Nombre puesto actual: ");
        String nombrePuestoActual = scanner.nextLine();

        Puesto puestoActual = this.buscarPuesto(nombrePuestoActual);

        while (puestoActual == null) { 
            boolean agregarPuesto = InputHelper.yesOrNoInput(scanner, "El puesto no existe, quiere AGREGARLO?");

            if (agregarPuesto) {
                puestoActual = this.agregarPuesto(nombrePuestoActual);
            } else {
                // por si el usuario quiere comprobar que lo esta tipeando bien, le pregunto de
                // nuevo por el puesto
                System.out.print("Nombre puesto actual: ");
                nombrePuestoActual = scanner.nextLine();

                puestoActual = this.buscarPuesto(nombrePuestoActual);
            }
        }

        // la fecha de inicio del puesto actual es:
        // si tuvo cargos antiguos, es igual a la fecha de fin del ultimo cargo
        // si no tuvo cargos antiguos, es igual a la fecha en la que entro a la empresa
        Fecha fechaInicio;

        if (historialDeCargos.size() > 0) { // tiene cargos antiguos
            fechaInicio = historialDeCargos.get(historialDeCargos.size() - 1).getFechaFin();
        } else { // es la fecha en la que inicio en la empresa
            fechaInicio = fechaIngresoEmpresa;
        }

        Cargo nuevoCargo = new Cargo(fechaInicio, null, puestoActual); 
        // mando null a fechaFin para colocarlo cuando abandone cargo

        return nuevoCargo;
    }

    // NO ES EL METODO DEL CASO DE USO AGREGAR PUESTO, ESTE YA RECIBE EL NOMBRE, se
    // usa en pedirListaCargos cuando quiere crearalo si no existe
    private Puesto agregarPuesto(String nombre) {
        float sueldo = InputHelper.scanFloat(scanner, "\nSueldo: ");

        boolean esJerarquico = InputHelper.yesOrNoInput(scanner, "Es un puesto jerarquico?");

        Puesto puestoNuevo;

        if (esJerarquico) {
            puestoNuevo = new PuestoJerarquico(nombre, sueldo);
        } else {
            puestoNuevo = new PuestoNoJerarquico(nombre, sueldo);
        }

        this.puestos.add(puestoNuevo); // agrego a la lista de la empresa

        Logger.logSuccess("Puesto nuevo añadido con exito");

        return puestoNuevo;
    }

    // sirve para CU agregar empleado y CU generar convocatoria
    private Hashtable<Habilidad, Integer> pedirListaHabilidades(String header) {
        // ingresar las habilidades y los años de experiencia en cada una
        Logger.header("Ingreso de habilidades y experiencia " + header);

        // crear hashtable local
        Hashtable<Habilidad, Integer> habilidades = new Hashtable<Habilidad, Integer>();

        // llenar la hashtable con las habilidades
        boolean otra = false;

        do {
            System.out.print("Nombre habilidad: ");
            String nombreHabilidad = scanner.nextLine();

            // busco que la habilidad exista
            Habilidad habilidad = this.buscarHabilidad(nombreHabilidad);

            if (habilidad == null) {
                System.out.print("Descripcion: ");
                String descripcion = scanner.nextLine();

                habilidad = new Habilidad(nombreHabilidad, descripcion);

                this.habilidades.add(habilidad); // agrego en la lista de la empresa
            }

            // ya tengo la habilidad, verifico si ya esta agregada en la hashtable
            if (habilidades.containsKey(habilidad)) {
                Logger.logError("La habilidad ya esta agregada en la lista");
            } else {
                // ya se que no esta, ahora pido los años de experiencia en ella
                int annosExperiencia = InputHelper.scanInt(scanner, "Años de experiencia en " + nombreHabilidad + ": ");

                // agrego a la hashtable
                habilidades.put(habilidad, annosExperiencia);

                Logger.logSuccess("Habilidad agregada");
            }

            otra = InputHelper.yesOrNoInput(scanner, "Agregar otra habilidad:");

        } while (otra);

        return habilidades;
    }

    // METODOS DE BUSQUEDA
    private Empleado buscarEmpleado(int legajo) {
        int i = 0;

        while (i < empleados.size() && !empleados.get(i).hasLegajo(legajo))
            i++;

        if (i < empleados.size())
            return empleados.get(i);
        return null;
    }

    // CU- Generar nueva convocatoria
    public void agregarConvocatoria() {
        Logger.header("Formulario generar convocatoria");

        int codigoConvocatoria = InputHelper.scanInt(scanner, "Codigo convocatoria: ");

        Convocatoria convocatoriaRepetida = this.buscarConvocatoria(codigoConvocatoria);

        if (convocatoriaRepetida != null) {
            Logger.logError("Ya existe una convocatoria con el codigo " + codigoConvocatoria);
        } else {
            System.out.print("Nombre puesto: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puestoConvocatoria = this.buscarPuesto(nombrePuesto);

            while (puestoConvocatoria == null) {
                // le doy la opcion de crearlo
                boolean quiereCrearlo = InputHelper.yesOrNoInput(scanner,
                        "No existe puesto con ese nombre, quiere crearlo?");

                if (quiereCrearlo) {
                    puestoConvocatoria = this.agregarPuesto(nombrePuesto);
                } else {
                    // le doy la posiblidad de ingresar el nombre de nuevo, por si cree que tipeo
                    // mal
                    System.out.print("Nombre puesto: ");
                    nombrePuesto = scanner.nextLine();

                    puestoConvocatoria = this.buscarPuesto(nombrePuesto);
                }
            }

            System.out.println("\nFecha a realizar convocatoria: ");
            Fecha fechaConvocatoria = Fecha.nuevaFecha();

            // verificar si la fecha es igual o despues de hoy
            while (fechaConvocatoria.compareTo(Fecha.hoy()) < 0) {
                Logger.logError("La fecha debe ser posterior o igual al dia de hoy");
                System.out.println("Fecha a realizar convocatoria: ");
                fechaConvocatoria = Fecha.nuevaFecha();
            }

            int cantEmpleadosRequeridos = InputHelper.scanInt(scanner, "\nCantidad de empleados requeridos: ");
            while (cantEmpleadosRequeridos <= 0) {
                Logger.logError("La cantidad de empleados requeridos debe ser mayor que 0");
                cantEmpleadosRequeridos = InputHelper.scanInt(scanner, "Cantidad de empleados requeridos: ");
            }

            Hashtable<Habilidad, Integer> requisitos = this.pedirListaHabilidades("necesarios para aplicar a la convocatoria");

            Convocatoria convocatoriaNueva;

            if (puestoConvocatoria.esJerarquico()) {
                int annosMinimosEnEmpresa = InputHelper.scanInt(
                    scanner,
                    "\nAños minimos en la empresa que se requieren para aplicar: "
                );

                while (annosMinimosEnEmpresa < 0) {
                    Logger.logError("La cantidad de Años minimos debe ser positiva");
                    annosMinimosEnEmpresa = InputHelper.scanInt(
                        scanner,
                        "\nAños minimos en la empresa que se requieren para aplicar: "
                    );
                }

                convocatoriaNueva = new ConvocatoriaJerarquico(
                        codigoConvocatoria,
                        puestoConvocatoria,
                        fechaConvocatoria,
                        cantEmpleadosRequeridos,
                        annosMinimosEnEmpresa,
                        requisitos);
            } else {
                convocatoriaNueva = new ConvocatoriaNoJerarquico(
                        codigoConvocatoria,
                        puestoConvocatoria,
                        fechaConvocatoria,
                        cantEmpleadosRequeridos,
                        requisitos);
            }

            // agregar a la lista de convocatorias
            convocatorias.add(convocatoriaNueva);

            // agregar a la lista de convocatorias DEL PUESTO
            puestoConvocatoria.agregarConvocatoria(convocatoriaNueva);

            Logger.logSuccess("Convocatoria registrada en el sistema");
        }
    }

    private Convocatoria buscarConvocatoria(int codigo) {
        int i = 0;

        while (i < convocatorias.size() && !convocatorias.get(i).hasCodigo(codigo)) {
            i++;
        }

        if (i < convocatorias.size())
            return convocatorias.get(i);
        return null;
    }
    
    // CU Editar datos de convocatoria
    public void editarConvocatoria() {
    	Logger.header("Editar informacion de Convocatoria");

        int codigo = InputHelper.scanInt(scanner, "Ingrese el codigo de la convocatoria: ");

        Convocatoria convocatoria = this.buscarConvocatoria(codigo);

        if (convocatoria == null) {
            Logger.logError("No existe la convocatoria con codigo " + codigo);
        } else {
        	int opcion;

            do {
                Logger.header("Menu para editar informacion de Convocatoria");
                System.out.println("[1] Sacar un empleado de los postulados");
                System.out.println("[2] Editar la fecha");
                System.out.println("[3] Editar la cantidad de empleados requeridos");
                System.out.println("[4] Volver a ingresar las habilidades");
                System.out.println("[5] Ver informacion de la convocatoria");
                System.out.println("[0] Volver al menu del General");

                opcion = InputHelper.scanInt(scanner, "Opcion: ");

                switch (opcion) {
                    case 0:
                        break;
                    case 1:
                    	this.darDeBajaPostulante(convocatoria);
                        break;

                    case 2:
                    	this.editarFechaConvocatoria(convocatoria);
                        break;

                    case 3:
                    	convocatoria.editarCantEmpleadosRequeridos(scanner);
                        break;

                    case 4:
                    	Hashtable<Habilidad, Integer> requisitos = this.pedirListaHabilidades("necesarios para aplicar a la convocatoria");
                    	convocatoria.editarRequisitos(requisitos);
                        break;
                        
                    case 5:
                    	convocatoria.mostrarConPostulantesYAsignados();
                        break;

                    default:
                        break;
                }

            } while (opcion != 0);
        }
    }
    
    public void editarFechaConvocatoria(Convocatoria convocatoria){
    	System.out.println("\nFecha a realizar convocatoria: ");
        Fecha fechaConvocatoria = Fecha.nuevaFecha();

        // verificar si la fecha es hoy o posterior 
        while (fechaConvocatoria.compareTo(Fecha.hoy()) < 0) {
            Logger.logError("La fecha debe ser posterior o igual al dia de hoy");
            System.out.println("Fecha a realizar convocatoria: ");
            fechaConvocatoria = Fecha.nuevaFecha();
        }
        
        convocatoria.editarFecha(fechaConvocatoria);
    }
    
    
    // CASO DE USO BORRAR PUESTO DE TRABAJO
    public void borrarPuesto() {
        Logger.header("Borrar puesto de trabajo");

        System.out.print("Nombre puesto de trabajo: ");
        String nombrePuesto = scanner.nextLine();

        Puesto puestoBorrar = this.buscarPuesto(nombrePuesto);

        if (puestoBorrar == null) {
            Logger.logError("NO existe  el puesto de trabajo " + nombrePuesto);
        } else {
            Logger.header("Informacion puesto a eliminar: ");
            puestoBorrar.mostrar();

            int cantEmpleados = puestoBorrar.cantEmpleados();

            if (cantEmpleados != 0) {
                Logger.logError("NO se puede eliminar, porque " + cantEmpleados + " empleados tienen este puesto");
            } else {
                // buscar si esta en alguna convocatoria
                boolean estaEnConvocatorias = puestoEstaEnConvocatorias(puestoBorrar);

                if (estaEnConvocatorias) {
                    Logger.logError(
                            "NO se puede eliminar, porque el puesto de " + nombrePuesto + " esta en convocatorias");
                } else {
                    puestos.remove(puestoBorrar);

                    Logger.logSuccess("Puesto de trabajo " + nombrePuesto + " ha sido ELIMINADO");
                }
            }
        }
    }

    private boolean puestoEstaEnConvocatorias(Puesto puesto) {
        int i = 0;

        while (i < convocatorias.size() && !convocatorias.get(i).hasPuesto(puesto)) {
            i++;
        }

        return i < convocatorias.size(); // si es menor, significa que salio del while porque estaba en una convocatoria
    }

    // CU AGREGAR HABILIDAD EMPLEADO
    public void agregarHabilidadEmpleado() {
        Logger.header("Formulario Agregar Habilidad de Empleado");

        int unLegajo = InputHelper.scanInt(scanner, "Ingrese el legajo del empleado: ");

        Empleado unEmpleado = this.buscarEmpleado(unLegajo);

        if (unEmpleado == null)
            Logger.logError("No existe el empleado.");
        else {
            System.out.print("Ingrese el nombre de la habilidad: ");
            String nombre = scanner.nextLine();

            Habilidad habilidadExistente = this.buscarHabilidad(nombre);

            if (habilidadExistente == null) {
                habilidadExistente = this.crearUnaHabilidad(nombre);
            }

            unEmpleado.agregarHabilidad(scanner, habilidadExistente);
        }
    }

    // CU QUITAR HABILIDAD EMPLEADO
    public void quitarHabilidadEmpleado() {
        Logger.header("Formulario Quitar Habilidad de Empleado");

        int unLegajo = InputHelper.scanInt(scanner, "Ingrese el legajo del empleado: ");

        Empleado unEmpleado = this.buscarEmpleado(unLegajo);

        if (unEmpleado == null) {
            Logger.logError("No existe el empleado");
        } else {
            System.out.print("Ingrese el nombre de la habilidad: ");
            String nombre = scanner.nextLine();

            Habilidad habilidadExistente = this.buscarHabilidad(nombre);

            if (habilidadExistente == null) {
                Logger.logError("NO existe la habilidad " + nombre);
            } else {
                unEmpleado.eliminarHabilidad(habilidadExistente);
            }
        }
    }

    // CU EDITAR ANNOS EMPLEADOS
    public void editarAnnosEmpleado() {
        Logger.header("Formulario para editar annos de experiencia de una habilidad de un empleado");

        int unLegajo = InputHelper.scanInt(scanner, "Ingrese el legajo del empleado: ");

        Empleado unEmpleado = this.buscarEmpleado(unLegajo);

        if (unEmpleado == null) {
            Logger.logError("NO existe el empleado");
        } else {
            System.out.print("Ingrese el nombre de la habilidad: ");
            String nombre = scanner.nextLine();

            Habilidad habilidad = this.buscarHabilidad(nombre);

            if (habilidad == null) {
                Logger.logError("NO existe la habilidad " + nombre);
            } else {
                unEmpleado.modificarAnnos(scanner, habilidad);
            }
        }
    }

    // CU VER DATOS EMPELADO
    public void verHistorialDeCargos() {
        Logger.header("Historial de cargos");

        int unLegajo = InputHelper.scanInt(scanner, "Ingrese el legajo del empleado: ");
        Empleado unEmpleado = this.buscarEmpleado(unLegajo);

        if (unEmpleado == null) {
            Logger.logError("NO existe el empleado con legajo " + unLegajo);
        } else {
            unEmpleado.mostrarCargos();
        }

    }

    // CASO DE USO DAR DE BAJA CONVOCATORIA
    public void darDeBajaConvocatoria() {
        Logger.header("Dar de baja una convocatoria");

        int codigoConvocatoria = InputHelper.scanInt(scanner, "Codigo convocatoria a dar de baja: ");

        Convocatoria convocatoriaEliminar = this.buscarConvocatoria(codigoConvocatoria);

        if (convocatoriaEliminar == null) {
            Logger.logError("No existe convocatoria con codigo: " + codigoConvocatoria);
        } else {
            boolean eliminar = true;

            if (!convocatoriaEliminar.estaAbierta()) { 
                // si esta cerrada, se lo informo y le pregunto si quiere continuar
                Logger.logWarning("La convocatoria esta cerrada");
                eliminar = InputHelper.yesOrNoInput(scanner, "Quiere eliminarla?");
            }

            if (eliminar) {
                // eliminar convocatoria de la empresa
                this.convocatorias.remove(convocatoriaEliminar);

                // eliminar convocatoria del puesto
                Puesto puestoConvocatoria = convocatoriaEliminar.getPuesto();

                puestoConvocatoria.darDeBajaConvocatoria(convocatoriaEliminar);

                Logger.logSuccess("La convocatoria ha sido eliminada");
            } else {
                Logger.logSuccess("La convocatoria historica NO ha sido eliminada");
                // la llamo 'historica' porque solo puedo cambiar el estado de 'eliminar' en
                // caso de que sea historica
            }
        }
    }

    // CASO DE USO DAR DE BAJA INSCRIPTO A CONVOCATORIA
    public void darDeBajaPostulanteConvocatoria() {
        Logger.header("Dar de baja inscripto a convocatoria: ");

        boolean verConvocatoriasAbiertas = InputHelper.yesOrNoInput(
            scanner,
            "Quiere ver las convocatorias que se encuentran abiertas?"
        );

        if (verConvocatoriasAbiertas) {
            this.mostrarConvocatoriasAbiertas();
        }

        Logger.divider();
        int codigoConvocatoria = InputHelper.scanInt(scanner, "Codigo convocatoria: ");

        Convocatoria convocatoria = this.buscarConvocatoria(codigoConvocatoria);

        if (convocatoria == null) {
            Logger.logError("No existe la convocatoria con codigo " + codigoConvocatoria);
        } else {
            if (!convocatoria.estaAbierta()) {
                Logger.logError("La convocatoria esta cerrada");
            } else {
                this.darDeBajaPostulante(convocatoria);
            }
        }
    }
    
    //Se usa en "darDeBajaPostulanteConvocatoria" y en "editarConvocatoria"
    public void darDeBajaPostulante(Convocatoria convocatoria) {
    	if (!convocatoria.hasPostulantes()) {
            Logger.logError("La convocatoria NO tiene postulantes");
        } else {
            int legajoPostulante = InputHelper.scanInt(scanner, "Legajo postulante: ");

            convocatoria.darDeBajaPostulante(legajoPostulante);
        }
    }

    // CASO DE USO MOSTRAR HABILIDADES
    public void mostrarHabilidades() {
        Logger.header("Habilidades registradas en el sistema");

        if (habilidades.size() == 0) {
            Logger.logError("No se encuentran habilidades registradas");
        } else {
            for (Habilidad habilidad : habilidades) {
                habilidad.mostrar();
            }
        }
    }

    public void mostrarPuestos() {
        Logger.header("Puestos de trabajo registrados en el sistema");

        if (puestos.size() == 0) {
            Logger.logError("No se encuentran puestos de trabajo registrados");
        } else {
            for (Puesto puesto : puestos) {
                puesto.mostrar();
            }
        }
    }

    public void editarInformacionEmpleado() {
        Logger.header("Editar informacion personal del empleado");

        int legajoEmpleado = InputHelper.scanInt(scanner, "Numero de legajo: ");

        Empleado empleado = this.buscarEmpleado(legajoEmpleado);

        if (empleado == null) {
            Logger.logError("No existe un empleado con el legajo " + legajoEmpleado);
        } else {
            empleado.editarInformacion(scanner);
        }
    }

    // CASO DE USO EDITAR HABILIDAD MENU GENERAL (para todo el sistema)
    public void editarHabilidad() {
        Logger.header("Formulario para editar habilidad");

        System.out.print("Nombre habilidad: ");
        String nombreHabilidad = scanner.nextLine();

        Habilidad habilidad = this.buscarHabilidad(nombreHabilidad);

        if (habilidad == null) {
            Logger.logError("No existe una habilidad llamada " + nombreHabilidad);
        } else {
            int opcion;

            System.out.println("\nMenu para editar habilidad");
            do {

                System.out.println("[1] Editar nombre");
                System.out.println("[2] Editar descripcion");
                System.out.println("[3] Ver habilidad");
                System.out.println("[0] Volver al menu Admin");

                opcion = InputHelper.scanInt(scanner, "Opcion: ");

                switch (opcion) {
                    case 0:
                        // Volver al menu
                        break;
                    case 1:
                        habilidad.editarNombre(scanner);
                        break;
                    case 2:
                        habilidad.editarDescripcion(scanner);
                        break;
                    case 3:
                        habilidad.mostrar();
                        break;
                    default:
                        Logger.logError("Opcion no valida");
                        break;
                }

            } while (opcion != 0);
        }
    }

    // CASO DE USO ELEGIR POSTULANTES DE CONVOCATORIA PARA EL PUESTO VACANTE
    public void elegirPostulantesConvocatoria() {
        Logger.header("Elegir postulantes de convocatoria");

        int codigoConvocatoria = InputHelper.scanInt(scanner, "Codigo convocatoria: ");

        Convocatoria convocatoria = this.buscarConvocatoria(codigoConvocatoria);

        if (convocatoria == null) {
            Logger.logError("NO existe una convocatoria con codigo " + codigoConvocatoria + " en el sistema");
        } else {
            if (!convocatoria.estaAbierta()) {
                Logger.logError("La convocatoria esta cerrada, NO se puede seleccionar postulantes");
            } else {
                if (!convocatoria.hasPostulantes()) {
                    Logger.logError(
                        "No puede continuar la seleccion porque no hay postulantes para la convocatoria con codigo "
                        + codigoConvocatoria
                    );
                } else {
                    // se pueden seleccionar postulantes para el puesto vacante

                    convocatoria.mostrarConPostulantesYAsignados();

                    int legajoEmpleado;
                    Empleado empleadoSeleccionado;
                    boolean agregarOtro = true;

                    System.out.println("\n Elegir postulantes: ");

                    do {
                        legajoEmpleado = InputHelper.scanInt(scanner, "Legajo postulante seleccionado: ");

                        empleadoSeleccionado = this.buscarEmpleado(legajoEmpleado);

                        if (empleadoSeleccionado == null || !convocatoria.esPostulante(empleadoSeleccionado)) {
                            Logger.logError("No existe un postulante con el legajo " + legajoEmpleado);

                            agregarOtro = InputHelper.yesOrNoInput(scanner, "Quiere intentar con otro legajo? ");
                        } else {
                            convocatoria.asignarEmpleado(empleadoSeleccionado);

                            if (!convocatoria.quedaCupo()) {
                                agregarOtro = false;

                                Logger.logSuccess("Ya no puede seleccionar a mas postulantes porque se quedo sin cupo");
                                Logger.logSuccess("Convocatoria cerrada");

                            } else if (!convocatoria.hasPostulantes()) {
                                agregarOtro = false;

                                Logger.logSuccess("Ya no puede selecccionar a mas postulantes porque no hay mas");
                                convocatoria.informarCantidadRestante();

                            } else {
                                agregarOtro = InputHelper.yesOrNoInput(scanner, "Quiere agregar otro legajo? ");
                            }
                        }

                    } while (agregarOtro);
                }
            }
        }
    }

    // CASO DE USO MOSTRAR CONVOCATORIAS PUEDA APLICAR EMPLEADO
    public void mostrarConvocatoriasPuedaAplicarEmpleado() {
        // le muestro al empleado todas las convocatorias que pueda aplicar de todos los puestos
        Logger.header("Convocatorias disponibles para el empleado");

        int legajoEmpleado = InputHelper.scanInt(scanner, "Numero de legajo empleado: ");

        Empleado empleado = this.buscarEmpleado(legajoEmpleado);

        if (empleado == null) {
            Logger.logError("No existe un empleado con ese legajo");
        } else {
            this.mostrarConvocatoriasPuedaAplicarEmpleado(empleado);
        }
    }

    public void mostrarConvocatoriasPuedaAplicarEmpleado(Empleado empleadoAplicar) {
        if (!this.hayConvocatoriasAbiertas()) {

            Logger.logSuccess("Lo sentimos, NO existen convocatoria abiertas");

        } else {
            int opcion = 0;

            do {
                Logger.header("Menu Consultar Convocatorias");
                System.out.println("[1] Ver todas las convocatorias a las que puede aplicar");
                System.out.println("[2] Ver convocatorias de un puesto especifico");
                System.out.println("[3] Ver convocatorias para un rango de salario");
                System.out.println("[0] Volver");

                opcion = InputHelper.scanInt(scanner, "Opcion: ");

                switch (opcion) {
                    case 0:
                        // Volver al menu
                        break;
                    case 1:
                        this.mostrarTodasConvocatoriasPuedaAplicar(empleadoAplicar);
                        break;
                    case 2:
                        this.mostrarConvocatoriasPuestoPuedeAplicar(empleadoAplicar);
                        break;
                    case 3:
                        this.mostrarConvocatoriasPuedaAplicarRangoSalario(empleadoAplicar);
                        break;
                    default:
                        Logger.logError("Opcion no disponible");
                        break;
                }

            } while (opcion != 0);
        }

    }

    private void mostrarTodasConvocatoriasPuedaAplicar(Empleado empleadoAplicar) {

        ArrayList<Convocatoria> convocatoriaPuedeAplicar = convocatoriasPuedeAplicar(empleadoAplicar);

        if (convocatoriaPuedeAplicar.size() == 0) {
            Logger.logSuccess("Lo sentimos, NO puede aplicar a NINGUNA convocatoria");
        } else {
            Logger.header("Convocatorias disponibles");
            for (Convocatoria convocatoria : convocatoriaPuedeAplicar) {
                convocatoria.mostrar(); 
                // muestra sin los datos de los postulantes y asignados xq esta pensado para que lo vea el empleado
            }
        }

    }

    private ArrayList<Convocatoria> convocatoriasPuedeAplicar(Empleado empleadoAplicar) {
        ArrayList<Convocatoria> convocatoriasPuedeAplicar = new ArrayList<>();

        for (Convocatoria convocatoria : convocatorias) {
            if (convocatoria.puedeAplicar(empleadoAplicar, scanner)) {
                convocatoriasPuedeAplicar.add(convocatoria);
            }
        }

        return convocatoriasPuedeAplicar;
    }

    private void mostrarConvocatoriasPuestoPuedeAplicar(Empleado empleadoAplicar) {
        System.out.print("Nombre del puesto a aplicar: ");
        String nombrePuesto = scanner.nextLine();

        Puesto puestoAplicar = this.buscarPuesto(nombrePuesto);

        if (puestoAplicar == null) {
            Logger.logError("No existe un puesto llamado '" + nombrePuesto + "'");
        } else {
            puestoAplicar.mostrarConvocatoriasPuedeAplicar(empleadoAplicar, scanner);
        }
    }

    private void mostrarConvocatoriasPuedaAplicarRangoSalario(Empleado empleadoAplicar) {

        float salarioMin = InputHelper.scanFloat(scanner, "Salario minimo: ");
        float salarioMax = InputHelper.scanFloat(scanner, "Salario maximo: ");

        ArrayList<Convocatoria> convocatoriasPuedeAplicar = convocatoriasPuedeAplicarRango(
            empleadoAplicar,
            salarioMin,
            salarioMax
        );

        if (convocatoriasPuedeAplicar.size() == 0) {
            Logger.logSuccess(
                "Lo sentimos, no puede aplicar a NINGUNA convocatoria dentro del rango "
                + salarioMin + " - " + salarioMax
            );
        } else {
            Logger.header("Convocatorias disponibles con sueldo $" + salarioMin + " - $" + salarioMax);
            for (Convocatoria convocatoria : convocatoriasPuedeAplicar) {
                convocatoria.mostrar();
            }
        }

    }

    private ArrayList<Convocatoria> convocatoriasPuedeAplicarRango(
        Empleado empleadoAplicar,
        float salarioMin,
        float salarioMax
    ) {
        ArrayList<Convocatoria> convocatoriasPuedeAplicar = new ArrayList<>();

        for (Convocatoria convocatoria : convocatorias) {
            if (convocatoria.puedeAplicarYcumpleExpectativaSalario(empleadoAplicar, salarioMin, salarioMax,scanner)) {
                convocatoriasPuedeAplicar.add(convocatoria);
            }
        }

        return convocatoriasPuedeAplicar;
    }

    // CASO DE USO INSCRIBIR EMPLEADO A CONVOCATORIAS
    public void inscribirEmpleadoEnConvocatorias() {
        Logger.header("Formulario para inscribir empleado en convocatorias");

        int legajoEmpleado = InputHelper.scanInt(scanner, "Numero de legajo: ");

        Empleado empleado = this.buscarEmpleado(legajoEmpleado);

        if (empleado == null) {
            Logger.logError("No existe un empleado con el legajo " + legajoEmpleado + " en el sistema");
        } else {
            // determinar si puede inscribirse a alguna convocatoria, si no puede a ninguna,
            // se lo informo
            int cantPuedeAplicar = this.convocatoriasPuedeAplicar(empleado).size();

            if (cantPuedeAplicar == 0) {
                System.out.println("Lo sentimos, no puede inscribirse en ninguna convocatoria");
            } else {
                boolean quiereVerConvocatorias = InputHelper.yesOrNoInput(scanner,
                        "Quiere ver las convocatorias a las que puede aplicar?");
                if (quiereVerConvocatorias) {
                    this.mostrarConvocatoriasPuedaAplicarEmpleado(empleado);
                }
                int codigoConvocatoria;
                Convocatoria convocatoria;
                boolean otra;
                do {
                    codigoConvocatoria = InputHelper.scanInt(scanner, "Codigo convocatoria: ");

                    convocatoria = this.buscarConvocatoria(codigoConvocatoria);

                    if (convocatoria == null) {

                        Logger.logError("No existe una convocatoria con codigo " + codigoConvocatoria);

                    } else {

                        if (!convocatoria.puedeAplicar(empleado, scanner)) {

                            Logger.logError(
                                "El empleado con legajo " + legajoEmpleado
                                + " NO puede aplicar a la convocatoria con codigo " + codigoConvocatoria
                            );

                        } else {

                            convocatoria.inscribirEmpleado(empleado);

                            Logger.logSuccess("El empleado con legajo " + legajoEmpleado
                                    + " ha sido añadido exitosamente a la convocatoria con codigo "
                                    + codigoConvocatoria);

                            cantPuedeAplicar--;
                            // se usa para no darle la posibilidad de inscribirse a mas convocatorias si no puede
                        }
                    }

                    if (cantPuedeAplicar == 0) { // si se inscribio en la ultima posible, ya no pregunta
                        otra = false;
                        Logger.logSuccess("No tiene mas convocatorias disponibles para inscribirse");
                    } else {
                        otra = InputHelper.yesOrNoInput(scanner, "Quiere inscribirse a otra convocatoria?");
                    }

                } while (otra);

            }

        }
    }

    // CASO DE USO BORRAR HABILIDAD DEL SISTEMA
    public void borrarHabilidad() {
        Logger.header("Borrar habilidad");

        System.out.print("Nombre habilidad a eliminar: ");
        String nombreHabilidad = scanner.nextLine();

        Habilidad habilidadEliminar = this.buscarHabilidad(nombreHabilidad);

        if (habilidadEliminar == null) {

            Logger.logError("La habilidad '" + nombreHabilidad + "' no esta registrada en el sistema");

        } else {
            boolean empleadosTienenHabilidad = this.empleadosTienenHabilidad(habilidadEliminar);
            boolean convocatoriasTienenRequisito = this.convocatoriasTienenRequisito(habilidadEliminar);

            if (!empleadosTienenHabilidad && !convocatoriasTienenRequisito) {
                habilidades.remove(habilidadEliminar);

                Logger.logSuccess("Habilidad " + nombreHabilidad + " eliminada del sistema");

            } else {
                Logger.logWarning(
                        "La habilidad esta siendo utilizada en el sistema (la tiene un empleado o convocatoria)");

                boolean quiereEliminar = InputHelper.yesOrNoInput(scanner, "Quiere eliminarla?");

                if (!quiereEliminar) {
                    // decide no elimarla del sistema
                    Logger.logSuccess("Habilidad " + nombreHabilidad + " NO ha sido eliminada del sistema");

                } else {
                    // decide eliminarla por compleo del sistema
                    // eliminar de empleados
                    if (empleadosTienenHabilidad) {
                        for (Empleado empleado : empleados) {
                            empleado.tryEliminarHabilidad(habilidadEliminar);
                        }
                    }

                    // eliminar de convocatorias
                    if (convocatoriasTienenRequisito) {
                        for (Convocatoria convocatoria : convocatorias) {
                            convocatoria.tryEliminarRequisito(habilidadEliminar);
                        }
                    }

                    // elimino de lista de la Empresa
                    habilidades.remove(habilidadEliminar);

                    Logger.logSuccess("Habilidad " + nombreHabilidad + " eliminada del sistema");
                }
            }
        }
    }

    public boolean empleadosTienenHabilidad(Habilidad habilidad) {
        // busco si esta en algun empleado
        int i = 0;

        while (i < empleados.size() && !empleados.get(i).tieneHabilidad(habilidad)) {
            i++;
        }

        return i < empleados.size();
    }

    public boolean convocatoriasTienenRequisito(Habilidad habilidad) {
        // busco si esta en alguna convocatoria
        int i = 0;

        while (i < convocatorias.size() && !convocatorias.get(i).tieneRequisito(habilidad)) {
            i++;
        }

        return i < convocatorias.size();
    }

    // CU VER DATOS EMPLEADO
    public void verDatosEmpleado() {
        Logger.header("Mostrar empleado");

        int unLegajo = InputHelper.scanInt(scanner, "Ingrese el legajo del empleado: ");
        Empleado unEmpleado = this.buscarEmpleado(unLegajo);

        if (unEmpleado == null) {
            Logger.logError("NO existe el empleado con legajo " + unEmpleado);
        } else {
            Logger.divider();
            unEmpleado.mostrar();
            Logger.divider();
        }
    }


    //CU Mostrar todas las convocatorias
    public void mostrarConvocatorias() {
        if (convocatorias.size() == 0) {

            Logger.logSuccess("Lo sentimos, NO existen convocatoria en el sistema");

        } else {
            int opcion = 0;

            do {
                Logger.header("Menu Consultar Convocatorias");
                System.out.println("[1] Ver todas las convocatorias");
                System.out.println("[2] Ver convocatorias de un puesto especifico");
                System.out.println("[3] Ver convocatorias para un rango de salario");
                System.out.println("[0] Volver");

                opcion = InputHelper.scanInt(scanner, "Opcion: ");

                switch (opcion) {
                    case 0:
                        // Volver al menu
                        break;
                    case 1:
                        this.mostrarTodasConvocatorias();
                        break;
                    case 2:
                        this.mostrarConvocatoriasPuesto();
                        break;
                    case 3:
                        this.mostrarConvocatoriasRangoSalario();
                        break;
                    default:
                        Logger.logError("Opcion no disponible");
                        break;
                }

            } while (opcion != 0);
        }

    }

    public void mostrarTodasConvocatorias() {
        for (Convocatoria convocatoria: convocatorias) {
            convocatoria.mostrar();
        }
    }

    public void mostrarConvocatoriasPuesto() {
        System.out.println("Nombre puesto: ");
        String nombrePuesto = scanner.nextLine();

        Puesto puesto = this.buscarPuesto(nombrePuesto);

        if (puesto == null) {
            Logger.logError("No existe puesto con el nombre " + nombrePuesto);
        } else {
            puesto.mostrarConvocatorias();
        }
    }


    public void mostrarConvocatoriasRangoSalario() {
        float salarioMin = InputHelper.scanFloat(scanner, "Salario minimo: ");
        float salarioMax = InputHelper.scanFloat(scanner, "Salario maximo: ");

        int i = 0;

        Logger.header("Convocatorias disponibles con sueldo " + salarioMin + "$ - " + salarioMax + "$");
        for (Convocatoria convocatoria : convocatorias) {
            if (convocatoria.dentroDeRango(salarioMin, salarioMax)) {
                convocatoria.mostrar();
                i++;
            }
        }
        
        if (i == 0) {
            Logger.logError("No existen convocatorias dentro del rango " + salarioMin + "$ - " + salarioMax + "$");
        }
    }
    
    //CU Mostrar empleados
    public void mostrarEmpleados() {
        Logger.header("Empleados registrados en el sistema");

        if (empleados.size() == 0) {
            Logger.logError("No hay empleados registrados en el sistema");
        } else {
            Logger.divider();
            for (Empleado empleado: empleados) {
                empleado.mostrar();
                Logger.divider();
            }
        }
    }


    //CU Terminar convocatoria
    public void terminarConvocatoria() {
        Logger.header("Terminar convocatoria");

        int codigoConvocatoria = InputHelper.scanInt(scanner, "Codigo convocatoria: ");

        Convocatoria convocatoria = this.buscarConvocatoria(codigoConvocatoria);

        if (convocatoria != null) {
            convocatoria.terminar(); //iguala cant inscriptos con los asignados, para que se cierre
        } else {

            Logger.logError("No existe convocatoria con codigo " + codigoConvocatoria);

            boolean continuar = InputHelper.yesOrNoInput(scanner, "Quiere probar con otro codigo?");
         
            if (continuar) {
                this.terminarConvocatoria();
            }
        }
    }
}
