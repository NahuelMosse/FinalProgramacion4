import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Hashtable;

import utilidades.Fecha;
import utilidades.InputHelper;
import utilidades.Logger;
import utilidades.Fecha;

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

    public void crearUnaHabilidad() {
    	Logger.header("Formulario para crear una habilidad");

    	System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

		Habilidad habilidadExistente = this.buscarHabilidad(nombre);
		
		if (habilidadExistente != null) {
			Logger.logError("Ya existe una habilidad con este nombre");
			
			boolean continuar = InputHelper.yesOrNoInput(scanner, "Desea ingresar otro nombre?");
	        
	        if (continuar) {
	        	this.crearUnaHabilidad();
	        }
		} else {
			System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();

            Habilidad habilidadNueva = new Habilidad(nombre, descripcion);

            this.habilidades.add(habilidadNueva);

            Logger.logSuccess("Habilidad registrada con exito");
		}
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

        while(i < puestos.size() && !puestos.get(i).hasNombre(nombre))
            i++;

        if (i < puestos.size()) {
        	return puestos.get(i);
        }
        return null;
    }


    //CU- MOSTRAR CONVOCATORIAS ABIERTAS
    public void mostrarConvocatoriasAbiertas() {
        Logger.header("Convocatorias abiertas");

        boolean quiereVerPostulantesAsignados = InputHelper.yesOrNoInput(scanner, "Quiere ver los datos de cada postulante y asignado?");

        if (quiereVerPostulantesAsignados) {
            for(Convocatoria convocatoria: convocatorias) {
                if (convocatoria.estaAbierta()) {
                    convocatoria.mostrarConPostulantesAsignados();
                }
            }
        } else {
            for(Convocatoria convocatoria: convocatorias) {
                if (convocatoria.estaAbierta()) {
                    convocatoria.mostrar();
                }
            }
        } 
    }


  
  


    //CASO DE USO AGREGAR EMPLEADO AL SISTEMA
    public void agregarEmpleado() {
        Logger.header("Formulario para ingresar empleado");

        int legajo = InputHelper.scanInt(scanner, "Numero de legajo: ");

        Empleado empleadoRepetido = this.buscarEmpleado(legajo);

        if (empleadoRepetido != null) {
            Logger.logError("ya existe un empleado con ese numero de legajo");
        } else {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
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
        }
    
    }

    private ArrayList<Cargo> pedirListaCargos(Fecha fechaIngresoEmpresa) {
        // INGRESAR TODOS LOS CARGOS QUE UN EMPLEADO OCUPO HASTA AHORA
        Logger.header("Ingreso de cargos: ");

        System.out.println("Primero se pediran los cargos ANTIGUOS que ocupo (Se ingresa SI en caso de que los tenga)");
        System.out.println("Cuando usted lo decida, ingresara el cargo ACTUAL (Ingresando NO a 'tiene puestos antiguos?')\n");

        //creo un arraylist local para el historial de cargos para pasarle al constructor de empleado (es lo que retorno)
        ArrayList<Cargo> historialDeCargos = new ArrayList<Cargo>();

        //primero ingreso los cargos antiguos
        boolean tienePuestoAntiguo = InputHelper.yesOrNoInput(scanner, "Tiene puestos ANTIGUOS?");
        
        if (tienePuestoAntiguo) {
        	historialDeCargos = this.pedirListaCargosAntiguos(fechaIngresoEmpresa);
        }
        
        //INGRESAR CARGO ACTUAL, SOLO PREGUNTA EL PUESTO
        Cargo cargoActual = this.pedirCargoActual(fechaIngresoEmpresa, historialDeCargos);
        
        //agrego en arraylist local
        historialDeCargos.add(cargoActual);

        Logger.logSuccess("Cargo actual agregado");    

        return historialDeCargos; //para usarlo en el constructor de Empleado
    }

    //INGRESO CARGOS ANTIGUOS
    private ArrayList<Cargo> pedirListaCargosAntiguos(Fecha fechaIngresoEmpresa) {
        ArrayList<Cargo> historialDeCargos = new ArrayList<Cargo>();
        
        Logger.header("Ingreso cargos antiguos");

        System.out.println("\nRECORDAR: Los cargos antiguos se deben ingresar en orden empezando desde el mas antiguo\n");

        boolean tieneCargoAntiguo = true;

        do {
            System.out.print("Nombre puesto: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puesto = this.buscarPuesto(nombrePuesto);

            //SI NO EXISTE, SE LE PREGUNTA SI LO QUIERE AGREGAR:
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

            //determinar la fecha de inicio en el cargo
            //si es el primer cargo, es la misma que la fecha de ingreso a la empresa
            //si no es la fecha de fin del ultimo cargo ingresado
            Fecha fechaInicio;

            if (historialDeCargos.size()>0) { //ya se ingresaron cargos antiguos
                fechaInicio = historialDeCargos.get(historialDeCargos.size()-1).getFechaFin();   
            } else { //es el primer cargo que se ingresa
                fechaInicio = fechaIngresoEmpresa;
            }

            System.out.println("\nFecha en la que finalizo el cargo: ");
            Fecha fechaFin = Fecha.nuevaFecha();

            //comprobaciones fecha fin sea correcta
            while (fechaFin.compareTo(fechaInicio)<=0 || fechaFin.compareTo(Fecha.hoy())>0) {
                if (fechaFin.compareTo(fechaInicio)<=0) {
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

            //agrego en arraylist local
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

        while (puestoActual == null) { //es un while porque si o si debe tener un puesto actual, sino no puedo crear empleado
            //doy la posibilidad de crearlo
            boolean agregarPuesto = InputHelper.yesOrNoInput(scanner, "El puesto no existe, quiere AGREGARLO?");
                
            if (agregarPuesto) {
                puestoActual = this.agregarPuesto(nombrePuestoActual);
            } else {
                //por si el usuario quiere comprobar que lo esta tipeando bien, le pregunto de nuevo por el puesto
                System.out.print("Nombre puesto actual: ");
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

        return nuevoCargo;
    }


    //NO ES EL METODO DEL CASO DE USO AGREGAR PUESTO, ESTE YA RECIBE EL NOMBRE, se usa en pedirListaCargos cuando quiere crearalo si no existe
    private Puesto agregarPuesto(String nombre) {
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
        //ingresar las habilidades y los años de experiencia en cada una
        Logger.header("Ingreso de habilidades y experiencia");
        
        //crear hashtable local
        Hashtable<Habilidad, Integer> habilidades = new Hashtable<Habilidad, Integer>();

        //llenar la hashtable con las habilidades
        boolean otra = false;

        do {
            System.out.print("Nombre habilidad: ");
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
            if(habilidades.containsKey(habilidad)) {
                Logger.logError("La habilidad ya esta agregada en la lista");
            } else {
                //ya se que no esta, ahora pido los años de experiencia en ella
                int annosExperiencia = InputHelper.scanInt(scanner, "Años de experiencia en "+nombreHabilidad+": ");

                //agrego a la hashtable
                habilidades.put(habilidad, annosExperiencia);

                Logger.logSuccess("Habilidad agregada");
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

  
    //CU- Generar nueva convocatoria
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
                //le doy la opcion de crearlo
                boolean quiereCrearlo = InputHelper.yesOrNoInput(scanner, "No existe puesto con ese nombre, quiere crearlo?");
                
                if (quiereCrearlo) {
                    puestoConvocatoria = this.agregarPuesto(nombrePuesto); 
                } else {
                    //le doy la posiblidad de ingresar el nombre de nuevo, por si cree que tipeo mal
                    System.out.print("Nombre puesto: ");
                    nombrePuesto = scanner.nextLine();

                    puestoConvocatoria = this.buscarPuesto(nombrePuesto);
                }
            }
            
            System.out.println("Fecha a realizar convocatoria: ");
            Fecha fechaConvocatoria = Fecha.nuevaFecha();

            //verificar si la fecha es igual o despues de hoy
            while (fechaConvocatoria.compareTo(Fecha.hoy())<0) {
                Logger.logError("La fecha debe ser posterior o igual al dia de hoy");
                System.out.println("Fecha a realizar convocatoria: ");
                fechaConvocatoria = Fecha.nuevaFecha();
            }

            int cantEmpleadosRequeridos = InputHelper.scanInt(scanner, "Cantidad de empleados requeridos: ");

            System.out.println("Requisitos necesarios para aplicar a la convocatoria: ");
            Hashtable<Habilidad, Integer> requisitos = this.pedirListaHabilidades();
            
            Convocatoria convocatoriaNueva;

            if (puestoConvocatoria.esJerarquico()) {
                int annosMinimosEnEmpresa = InputHelper.scanInt(scanner, "Años minimos en la empresa que se requieren para aplicar: ");

                convocatoriaNueva = new ConvocatoriaJerarquico(
                    codigoConvocatoria,
                    puestoConvocatoria,
                    fechaConvocatoria,
                    cantEmpleadosRequeridos,
                    annosMinimosEnEmpresa,
                    requisitos
                );
            } else { 
                convocatoriaNueva = new ConvocatoriaNoJerarquico(
                    codigoConvocatoria,
                    puestoConvocatoria,
                    fechaConvocatoria,
                    cantEmpleadosRequeridos,
                    requisitos
                );
            }

            //agregar a la lista de convocatorias
            convocatorias.add(convocatoriaNueva);

            //agregar a la lista de convocatorias DEL PUESTO
            puestoConvocatoria.agregarConvocatoria(convocatoriaNueva);

            Logger.logSuccess("Convocatoria registrada en el sistema");
        }
    }


    private Convocatoria buscarConvocatoria(int codigo) {
        int i = 0;

        while(i<convocatorias.size() && !convocatorias.get(i).hasCodigo(codigo))
            i++;
        
        if(i<convocatorias.size())
            return convocatorias.get(i);
        else
            return null;
    }

  
  //CASO DE USO BORRAR PUESTO DE TRABAJO
    public void borrarPuesto() {
        System.out.print("Nombre puesto de trabajo: ");
        String nombrePuesto = scanner.nextLine();

        Puesto puestoBorrar = this.buscarPuesto(nombrePuesto);

        if (puestoBorrar != null) {
            Logger.header("Informacion puesto a eliminar: ");
            puestoBorrar.mostrar();

            int cantEmpleados = puestoBorrar.cantEmpleados();

            if (cantEmpleados == 0) {
                puestos.remove(puestoBorrar);

                Logger.logSuccess("Puesto de trabajo ELIMINADO");

            } else {
                Logger.logError("NO se puede eliminar, porque "+ cantEmpleados + " empleados tienen este puesto");
            }

        } else {
            Logger.logError("NO existe puesto de trabajo con este nombre");
        }
    }


    //CASO DE USO DAR DE BAJA CONVOCATORIA
    public void darDeBajaConvocatoria() {
        Logger.header("Dar de baja convocatoria");

        int codigoConvocatoria = InputHelper.scanInt(scanner, "Codigo convocatoria a dar de baja: ");

        Convocatoria convocatoriaEliminar = this.buscarConvocatoria(codigoConvocatoria);

        if (convocatoriaEliminar == null) {
            Logger.logError("No existe convocatoria con codigo: " + codigoConvocatoria);
        } else {
            boolean eliminar = true;

            if (!convocatoriaEliminar.estaAbierta()) { //si esta cerrada, se lo informo y le pregunto si quiere continuar
                System.out.println("Alerta: La convocatoria es historica (ya esta cerrada)");
                eliminar = InputHelper.yesOrNoInput(scanner, "Quiere eliminarla?");
            }

            if (eliminar) {
                //eliminar convocatoria de la empresa
                this.convocatorias.remove(convocatoriaEliminar);

                //eliminar convocatoria del puesto
                Puesto puestoConvocatoria = convocatoriaEliminar.getPuesto();

                puestoConvocatoria.darDeBajaConvocatoria(convocatoriaEliminar);

                Logger.logSuccess("La convocatoria ha sido eliminada");
            } else {
                Logger.logSuccess("La convocatoria historica NO ha sido eliminada"); 
                //la llamo 'historica' porque solo puedo cambiar el estado de 'eliminar' en caso de que sea historica
            }
        }
    }


    //CASO DE USO ELEGIR POSTULANTES DE CONVOCATORIA PARA EL PUESTO VACANTE
    public void elegirPostulantesConvocatoria() {
        Logger.header("Formulario para elegir postulantes de convocatoria");

        int codigoConvocatoria = InputHelper.scanInt(scanner, "Codigo convocatoria: ");

        Convocatoria convocatoria = this.buscarConvocatoria(codigoConvocatoria);

        if (convocatoria == null) {
            Logger.logError("NO existe una convocatoria con codigo " + codigoConvocatoria + " en el sistema");
        } else {
            if (!convocatoria.estaAbierta()) {
                Logger.logError("La convocatoria esta cerrada, NO se puede seleccionar postulantes");
            } else {
                if (!convocatoria.hasPostulantes()) {
                    Logger.logError("No puede continuar la seleccion porque no hay postulantes para la convocatoria con codigo " + codigoConvocatoria);
                } else {
                    //se pueden seleccionar postulantes para el puesto vacante

                    convocatoria.mostrarConPostulantesAsignados();

                    int legajoEmpleado;
                    Empleado empleadoSeleccionado;
                    boolean agregarOtro = true;

                    System.out.println("\n Elegir postulantes: ");

                    do {
                        legajoEmpleado = InputHelper.scanInt(scanner, "Legajo postulante seleccionado: ");

                        empleadoSeleccionado = this.buscarEmpleado(legajoEmpleado);

                        if (empleadoSeleccionado == null || !convocatoria.esPostulante(empleadoSeleccionado)) {
                            Logger.logError("No existe un postulante con el legajo " + legajoEmpleado);
                        } else {
                            convocatoria.asignarEmpleado(empleadoSeleccionado);
                        }

                        //me adelanto a comprobar para saber si pregunto o no, para no preguntar si se que ya no puede inscribir a nadie mas    
                        if (convocatoria.quedaCupo() && convocatoria.hasPostulantes()) { 
                            String pregunta;

                            if (empleadoSeleccionado == null || !convocatoria.esPostulante(empleadoSeleccionado)) {
                                pregunta = "Quiere intentar con otro legajo?"; //xq se q el anterior ingreso no funciono
                            } else {
                                pregunta = "Quiere agregar otro postulante?"; //xq se que el anterior funciono y lo agrego
                            }

                            agregarOtro = InputHelper.yesOrNoInput(scanner, pregunta); //la pregunta depende del caso anterior

                        } else {
                            //Determino porque no puedo seguir, para informarle al usuario
                            if (!convocatoria.quedaCupo()) {

                                Logger.logSuccess("Ya no puede seleccionar a mas postulantes porque se quedo sin cupo");
                            
                            } else { 

                                Logger.logSuccess("Ya no puede selecccionar a mas postulantes porque no hay mas");
                            
                            }
                            
                        }

                    } while (convocatoria.quedaCupo() && convocatoria.hasPostulantes() && agregarOtro);


                    if (convocatoria.quedaCupo()) { //si salio x esto es poque todavia la convocatoria esta abierta
                        Logger.logSuccess("Aun puede asignar a " + convocatoria.getCantRestante() + " postulantes al puesto de " + convocatoria.getPuesto().getNombre() + " para esta convocatoria");
                    } else {
                        Logger.logSuccess("Convocatoria cerrada porque se quedo sin cupo");
                    }
                }
            }
        }
    }


}
