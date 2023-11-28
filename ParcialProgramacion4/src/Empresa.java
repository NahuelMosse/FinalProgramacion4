import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import utilidades.InputHelper;
import utilidades.Logger;
import utilidades.Fecha;

public class Empresa {
    private ArrayList<Empleado> empleados;
    private ArrayList<Puesto> puestos;
    private ArrayList<Convocatoria> convocatorias;
    private ArrayList<Habilidad> habilidades;

    public Empresa() {
        this.empleados = new ArrayList<Empleado>();
        this.puestos = new ArrayList<Puesto>();
        this.convocatorias = new ArrayList<Convocatoria>();
        this.habilidades = new ArrayList<Habilidad>();
    }

    public void crearUnaHabilidad(Scanner scanner) {
    	Logger.header("Formulario para crear una habilidad");

    	System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

		Habilidad habilidadExistente = this.buscarHabilidad(nombre);
		
		if (habilidadExistente != null) {
			Logger.logError("Ya existe una habilidad con este nombre");
			
			boolean continuar = InputHelper.yesOrNoInput(scanner, "Desea ingresar otro nombre?");
	        
	        if (continuar) {
	        	this.crearUnaHabilidad(scanner);
	        }
		} else {
			System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();

            Habilidad habilidadNueva = new Habilidad(nombre, descripcion);

            this.habilidades.add(habilidadNueva);

            Logger.logSuccess("Habilidad registrada con exito");
		}
    }
    
    public void agregarPuesto(Scanner scanner) {
    	Logger.header("Formulario para crear un nuevo puesto de trabajo");
    	
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        Puesto puestoExistente = this.buscarPuesto(nombre);

        if (puestoExistente != null) {
        	Logger.logError("Ya se encuentra registrado un puesto con ese nombre");
        	
        	boolean continuar = InputHelper.yesOrNoInput(scanner, "Desea ingresar otro nombre?");
	        
	        if (continuar) {
	        	this.agregarPuesto(scanner);
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

        while(i < puestos.size() && !puestos.get(i).hasNombre(nombre))
            i++;

        if (i < puestos.size()) {
        	return puestos.get(i);
        }
        return null;
    }
  

    //CASO DE USO AGREGAR EMPLEADO AL SISTEMA
    public void agregarEmpleado(Scanner scanner) {
        Logger.header("Formulario para ingresar empleado: ");

        int legajo = InputHelper.scanInt(scanner, "Numero de legajo: ");

        Empleado empleadoRepetido = this.buscarEmpleado(legajo);

        if(empleadoRepetido == null) {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.println("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.println("Fecha de nacimiento: ");
            Fecha fechaNacimiento = Fecha.nuevaFecha();

            System.out.println("Fecha de ingreso a la empresa: ");
            Fecha fechaIngreso = Fecha.nuevaFecha();

            // INGRESAR TODOS LOS CARGOS QUE EMPLEADO OCUPO HASTA AHORA
            ArrayList<Cargo>historialDeCargos = this.pedirListaCargos(fechaIngreso);
            
            //crear hashtable con las habilidades y años de experiencia
            Hashtable<Habilidad, Integer>habilidades = this.pedirListaHabilidades();

            //constructor empleado
            Empleado empleadoNuevo = new Empleado(
                legajo,
                nombre,
                apellido,
                fechaNacimiento,
                fechaIngreso,
                historialDeCargos,
                habilidades
            );

            //agrego al empleado en el puesto actual
            Puesto puestoActual = empleadoNuevo.getPuestoActual();

            puestoActual.agregarEmpleado(empleadoNuevo);

            //agregar empleado a lista de empresa
            empleados.add(empleadoNuevo);

            Logger.logSuccess("Empleado agregado a lista de la empresa");

        } else {
            Logger.logError("ya existe un empleado con ese numero de legajo");
        }
    }

    private ArrayList<Cargo> pedirListaCargos(Fecha fechaIngresoEmpresa) {
        Scanner scanner = new Scanner(System.in);
        // INGRESAR TODOS LOS CARGOS QUE UN EMPLEADO OCUPO HASTA AHORA
        Logger.header("Ingreso de cargos: ");

        System.out.println("Primero se pediran los cargos ANTIGUOS que ocupo (Se ingresa SI en caso de que los tenga)");
        System.out.println("Cuando usted lo decida, ingresara el cargo ACTUAL (Ingresando NO a 'tiene puestos antiguos?')\n");

        //creo un arraylist local para el historial de cargos para pasarle al constructor de empleado (es lo que retorno)
        ArrayList<Cargo> historialDeCargos;
        historialDeCargos = new ArrayList<Cargo>();

        //primero ingreso los cargos antiguos
        boolean tienePuestoAntiguo = InputHelper.yesOrNoInput(scanner, "Tiene puestos ANTIGUOS?");
        
        
        //carga del cargo mas antiguo en la que la fecha de ingreso al cargo es la misma que la de ingreso a la empresa
        //primero es un 'if' y no un while porque la fecha de inicio del cargo mas antiguo se saca de la fecha de ingreso a la empresa
        //pero en los proximos cargos antiguos se saca de la fecha de fin del anterior cargo
        if (tienePuestoAntiguo) {
        	historialDeCargos = this.pedirListaCargosAntiguos(fechaIngresoEmpresa);
        }
        
        //si se ingreso un puesto valido o se creo, no hay problema se registra el puesto antiguo
        //sino, se dirige directo a la creacion de un puesto actual que si o si se debe registrar
        //porque un empleado no puede registrarse si no tiene al menos un cargo, con un puesto registrado en el sistema
        
        //INGRESAR CARGO ACTUAL, SOLO PREGUNTA FECHA INICIO Y EL PUESTO
        System.out.println("\nINGRESO PUESTO ACTUAL");
        System.out.println("Nombre puesto actual: ");
        String nombrePuestoActual = scanner.nextLine();

        Puesto puestoActual = this.buscarPuesto(nombrePuestoActual);

        if (puestoActual == null) {
            //le doy al usuario la posiblidad de agregarlo
            boolean agregarPuesto = InputHelper.yesOrNoInput(scanner, "El puesto no existe, quiere agregarlo?");
                
            if (agregarPuesto) {
                puestoActual = this.agregarPuesto(nombrePuestoActual);
            }
        }


        while (puestoActual == null) { //es un while porque si o si debe tener un puesto actual, sino no seria empleado
            //doy la posibilidad de crearlo
            boolean agregarPuesto = InputHelper.yesOrNoInput(scanner, "El puesto no existe, quiere AGREGARLO?");
                
            if (agregarPuesto) {
                puestoActual = this.agregarPuesto(nombrePuestoActual);
            } else {
                //por si el usuario quiere comprobar que lo esta tipeando bien
                System.out.println("Nombre puesto actual: ");
                nombrePuestoActual = scanner.nextLine();

                puestoActual = this.buscarPuesto(nombrePuestoActual);
            }
        }

        //la fecha de inicio del puesto actual es:
        // si tuvo cargos antiguos, es igual a la fecha de fin del ultimo cargo
        // si no tuvo cargos antiguos, es igual a la fecha en la que entro a la empresa

        Fecha fechaInicio;

        if (historialDeCargos.size()>0) { //tiene cargos antiguos
            fechaInicio = historialDeCargos.get(historialDeCargos.size()-1).getFechaFin();   
        } else { //es la fecha en la que inicio en la empresa
            fechaInicio = fechaIngresoEmpresa;
        }

        Cargo nuevoCargo = new Cargo(fechaInicio, null, puestoActual); //mando null a fechaFin para colocarlo cuando abandone cargo

        //agrego en arraylist local
        historialDeCargos.add(nuevoCargo);

        Logger.logSuccess("Cargo actual agregado");    

        return historialDeCargos; //para usarlo en el constructor de Empleado
    }

    //INGRESO CARGOS ANTIGUOS
    private ArrayList<Cargo> pedirListaCargosAntiguos(Fecha fechaIngresoEmpresa) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Cargo> historialDeCargos;
        historialDeCargos = new ArrayList<Cargo>();
        
        System.out.println("\nRECORDAR: Los puestos antiguos se deben ingresar en orden empezando desde el mas antiguo\n");

        System.out.println("Nombre puesto: ");
        String nombrePuesto = scanner.nextLine();

        Puesto puesto = this.buscarPuesto(nombrePuesto);

        //SI NO EXISTE, SE LE PREGUNTA SI LO QUIERE AGREGAR:
        if (puesto == null) {
            boolean agregarPuesto = InputHelper.yesOrNoInput(scanner, "El puesto no existe, quiere agregarlo?");
            
            if (agregarPuesto) {
                puesto = this.agregarPuesto(nombrePuesto);
            }
            
        }

        //Se deja este if, por si no quiere agregar un nuevo puesto si no existe, y quiere ir directamente a ingresar un puesto actual
        if(puesto != null) {
            //fecha de ingreso al cargo es la fecha en la que ingreso a la empresa, se ingresa como parametro

            System.out.println("\nFecha final del puesto: ");
            Fecha fechaFin = Fecha.nuevaFecha();

            Cargo nuevoCargo = new Cargo(fechaIngresoEmpresa, fechaFin, puesto);

            //agrego en arraylist local
            historialDeCargos.add(nuevoCargo);

            Logger.logSuccess("Cargo antiguo agregado");

            
            //INGRESO DEL RESTO DE PUESTOS ANTIGUOS, utilizo while para ingresar n cargos
            boolean tienePuestoAntiguo = InputHelper.yesOrNoInput(scanner, "Tiene MAS puestos ANTIGUOS?");
            
            while (tienePuestoAntiguo) {
                System.out.println("Nombre puesto: ");
                nombrePuesto = scanner.nextLine();

                puesto = this.buscarPuesto(nombrePuesto);

                //le doy al usuario la posibilidad de agregar el puesto si no existe
                if (puesto == null) {
                    boolean agregarPuesto = InputHelper.yesOrNoInput(scanner, "El puesto no existe, quiere agregarlo?");
                    
                    if (agregarPuesto) {
                        puesto = this.agregarPuesto(nombrePuesto);
                    }
                
                }   

                //se deja este if por si ingresa que no quiere agregar un nuevo puesto que no existe en la empresa
                if(puesto != null) {
                    //fecha de ingreso es la fecha de fin del ultimo puesto
                    //como el ingreso es en orden: 
                    Fecha fechaInicio = historialDeCargos.get(historialDeCargos.size()-1).getFechaFin();

                    System.out.println("\nFecha final del puesto: ");
                    fechaFin = Fecha.nuevaFecha();

                    nuevoCargo = new Cargo(fechaInicio, fechaFin, puesto);

                    //agrego en arraylist local
                    historialDeCargos.add(nuevoCargo);

                    Logger.logSuccess("Puesto antiguo agregado");

                } else {
                    Logger.logError("No fue posible registrar el cargo");
                }
            
                
                tienePuestoAntiguo = InputHelper.yesOrNoInput(scanner, "Tiene MAS puestos ANTIGUOS?");
            }


        } else {
            Logger.logError("No fue posible registrar el cargo");
            //CORTO INGRESO DE PUESTOS ANTIGUOS PORQUE YA NO PUEDO TOMAR LA FECHA PORQUE NO PUEDO REGISTRAR CARGOS QUE NO TIENEN PUESTO
        }


        return historialDeCargos;
    }


    //NO ES EL METODO DEL CASO DE USO AGREGAR PUESTO, ESTE YA RECIBE EL NOMBRE, se usa en pedirListaCargos
    private Puesto agregarPuesto(String nombre) {
        Scanner scanner = new Scanner(System.in);
        
        float sueldo = InputHelper.scanFloat(scanner, "Sueldo: ");
        
        boolean esJerarquico = InputHelper.yesOrNoInput(scanner, "Es un puesto jerarquico?");

        Puesto puestoNuevo;

        if (esJerarquico) {
            puestoNuevo = new PuestoJerarquico(nombre, sueldo);
        } else { 
            puestoNuevo = new PuestoNoJerarquico(nombre, sueldo);
        }

        this.puestos.add(puestoNuevo); //agrego a la lista de la empresa

        Logger.logSuccess("Puesto nuevo añadido con exito");

        return puestoNuevo;
    }


    //sirve para CU agregar empleado y CU generar convocatoria
    private Hashtable<Habilidad, Integer> pedirListaHabilidades() {
        Scanner scanner = new Scanner(System.in);
        //ingresar las habilidades y los años de experiencia en cada una
        Logger.header("Ingreso de habilidades y experiencia: ");
        
        //crear hashtable local
        Hashtable<Habilidad, Integer> habilidades;
        habilidades = new Hashtable<Habilidad, Integer>();

        //llenar la hashtable con las habilidades
        boolean otra;

        do {
            System.out.println("Nombre habilidad: ");
            String nombreHabilidad = scanner.nextLine();

            //busco que la habilidad exista
            Habilidad habilidad = this.buscarHabilidad(nombreHabilidad);

            if(habilidad == null) {
                System.out.println("Descripcion: ");
                String descripcion = scanner.nextLine();

                habilidad = new Habilidad(nombreHabilidad, descripcion);

                this.habilidades.add(habilidad); //agrego en la lista de la empresa
            }

            //ya tengo la habilidad, verifico si ya esta agregada en la hashtable
            if(!habilidades.containsKey(habilidad)) {
                //ya se que no esta, ahora pido los años de experiencia en ella
                int annosExperiencia = InputHelper.scanInt(scanner, "Años de experiencia en "+nombreHabilidad+": ");

                //agrego a la hashtable
                habilidades.put(habilidad, annosExperiencia);

                Logger.logSuccess("Habilidad agregada");

            } else {
                Logger.logError("La habilidad ya esta agregada en la lista");
            }
                
            otra = InputHelper.yesOrNoInput(scanner, "Agregar otra habilidad:");

        } while (otra);

        return habilidades;
    }



    //METODOS DE BUSQUEDA
    private Empleado buscarEmpleado(int legajo) {
        int i = 0;

        while(i<empleados.size() && !empleados.get(i).hasLegajo(legajo))
            i++;
        
        if(i<empleados.size())
            return empleados.get(i);
        return null;
    }
}
