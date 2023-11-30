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
    
    //CASO DE USO DAR DE BAJA EMPLEADO
    public void darDeBajaEmpleado() {
        Logger.header("Formulario para dar de baja empleado");

    	int legajo = InputHelper.scanInt(scanner, "Numero de legajo empleado a dar de baja: ");
    	
    	Empleado empleadoEliminar = this.buscarEmpleado(legajo);
    	
        if (empleadoEliminar == null) {
            System.out.println("ERROR: No existe empleado con el numero de legajo " + legajo);
        } else {
            Logger.divider();
        	System.out.println("Información empleado a eliminar: ");
        	empleadoEliminar.mostrar();
        	Logger.divider();
        	
            //determinar si el empleado esta ASIGNADO en alguna convocatoria historica
            boolean eliminarEmpleado = true;
            boolean estaAsignadoEnHistorica = this.empleadoAsignadoEnConvocatoriaHistoria(empleadoEliminar);

            if (estaAsignadoEnHistorica) {
                Logger.logWarning("El empleado con legajo " + legajo + "esta registrado en convocatorias historicas, NO es recomendable eliminarlo");
            }

            int cantInscripciones = this.cantInscripcionesEmpleadoConvocatoriasAbiertas(empleadoEliminar);
                
            if (cantInscripciones > 0) {
                Logger.logWarning("El empleado esta inscripto en " + cantInscripciones + " convocatorias abiertas");
            } 

            int cantAsignaciones = this.cantAsignacionesEmpleadoConvocatoriasAbiertas(empleadoEliminar);
            if (cantAsignaciones > 0) {
                Logger.logWarning("El empleado esta asignado en " + cantAsignaciones + " convocatorias abiertas");
            }

            //si sucede alguna de las 3 ultimas situaciones pregunto para confirmar
            if (estaAsignadoEnHistorica || cantInscripciones > 0 || cantAsignaciones > 0) {
                eliminarEmpleado = InputHelper.yesOrNoInput(scanner, "Quiere eliminarlo de todas formas?");
            }
            

            if (eliminarEmpleado) {
                //antes de eliminarlo de la lista de empresa, lo eliminamos de la lista de su puesto actual
                Puesto puestoEmpleado = empleadoEliminar.getPuestoActual();
                puestoEmpleado.eliminarEmpleado(empleadoEliminar);
                
                //eliminar de convocatorias
                int cantConvocatorias = this.eliminarEmpleadoDeConvocatorias(empleadoEliminar);

                if (cantConvocatorias > 0) {
                    Logger.logSuccess("El empleado con legajo " + legajo + " ha sido eliminado de " + cantConvocatorias);
                } else {
                    Logger.logSuccess("El empleado con legajo " + legajo + " NO ha sido eliminado de convocatorias porque no esta inscripto o asignado en ninguna");
                }

                //eliminar cargos
                empleadoEliminar.eliminarCargos();

                //ahora se elimina de la lista de la empresa
                empleados.remove(empleadoEliminar);

                Logger.logSuccess("Empleado eliminado con exito");
            } else {
                Logger.logError("Empleado NO ha sido eliminado");
            }
        }
    }

    private boolean empleadoAsignadoEnConvocatoriaHistoria(Empleado empleado) {
        int i = 0;
        while (i < convocatorias.size() && (convocatorias.get(i).estaAbierta() || !convocatorias.get(i).empleadoEstaAsignado(empleado))) { //While para que si ya esta signado corte
            i++;
        }

        return i < convocatorias.size();
    }

    private int cantInscripcionesEmpleadoConvocatoriasAbiertas(Empleado empleado) {
        int i = 0;
        for (Convocatoria convocatoria: convocatorias) {
            if (convocatoria.estaAbierta() && convocatoria.empleadoEstaPostulado(empleado)) {
                i++;
            }
        }

        return i;
    }

    private int cantAsignacionesEmpleadoConvocatoriasAbiertas(Empleado empleado) {
        int i = 0;
        for (Convocatoria convocatoria: convocatorias) {
            if (convocatoria.estaAbierta() && convocatoria.empleadoEstaAsignado(empleado)) {
                i++;
            }
        }

        return i;
    }

    private int eliminarEmpleadoDeConvocatorias(Empleado empleado) {
        int i = 0;
        for (Convocatoria convocatoria: convocatorias) {
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
		
		if (habilidadExistente != null) {
			Logger.logError("Ya existe una habilidad con este nombre");
			
			boolean continuar = InputHelper.yesOrNoInput(scanner, "Desea ingresar otro nombre?");
	        
	        if (continuar) {
	        	return this.crearUnaHabilidad();
	        }
		} else {
			System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();

            Habilidad habilidadNueva = new Habilidad(nombre, descripcion);

            this.habilidades.add(habilidadNueva);

            Logger.logSuccess("Habilidad registrada con exito");

            return habilidadNueva;
		}

        return null;
    }

    public Habilidad crearUnaHabilidad(String nombre) {
    	Logger.header("Formulario para crear una habilidad");

		Habilidad habilidadExistente = this.buscarHabilidad(nombre);
		
		if (habilidadExistente != null) {
			Logger.logError("Ya existe una habilidad con este nombre");
			
			boolean continuar = InputHelper.yesOrNoInput(scanner, "Desea ingresar otro nombre?");
	        
	        if (continuar) {
	        	return this.crearUnaHabilidad();
	        }
		} else {
			System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();

            Habilidad habilidadNueva = new Habilidad(nombre, descripcion);

            this.habilidades.add(habilidadNueva);

            Logger.logSuccess("Habilidad registrada con exito");

            return habilidadNueva;
		}

        return null;
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

            while (fechaIngreso.compareTo(Fecha.hoy()) > 0) {
                Logger.logError("La fecha de ingreso NO debe ser posterior al dia de hoy");
                System.out.println("Fecha de ingreso a la empresa: ");
                fechaIngreso = Fecha.nuevaFecha();
            }

            // INGRESAR TODOS LOS CARGOS QUE EMPLEADO OCUPO HASTA AHORA
            ArrayList<Cargo>historialDeCargos = this.pedirListaCargos(fechaIngreso);
            
            //crear hashtable con las habilidades y años de experiencia
            Hashtable<Habilidad, Integer>habilidades = this.pedirListaHabilidades("del empleado");

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
        float sueldo = InputHelper.scanFloat(scanner, "\nSueldo: ");
        
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
    private Hashtable<Habilidad, Integer> pedirListaHabilidades(String header) {
        //ingresar las habilidades y los años de experiencia en cada una
        Logger.header("Ingreso de habilidades y experiencia " + header);
        
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
            
            System.out.println("\nFecha a realizar convocatoria: ");
            Fecha fechaConvocatoria = Fecha.nuevaFecha();

            //verificar si la fecha es igual o despues de hoy
            while (fechaConvocatoria.compareTo(Fecha.hoy())<0) {
                Logger.logError("La fecha debe ser posterior o igual al dia de hoy");
                System.out.println("Fecha a realizar convocatoria: ");
                fechaConvocatoria = Fecha.nuevaFecha();
            }

            int cantEmpleadosRequeridos = InputHelper.scanInt(scanner, "\nCantidad de empleados requeridos: ");
            while (cantEmpleadosRequeridos <= 0) {
                Logger.logError("La cantidad de empleados requeridos debe ser mayor que 0");
                cantEmpleadosRequeridos = InputHelper.scanInt(scanner, "Cantidad de empleados requeridos: ");
            }

            Hashtable<Habilidad, Integer> requisitos = this.pedirListaHabilidades( "necesarios para aplicar a la convocatoria");
            
            Convocatoria convocatoriaNueva;

            if (puestoConvocatoria.esJerarquico()) {
                int annosMinimosEnEmpresa = InputHelper.scanInt(scanner, "\nAños minimos en la empresa que se requieren para aplicar: ");

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
                Logger.logError("NO se puede eliminar, porque "+ cantEmpleados + " empleados tienen este puesto");
            } else {
                //buscar si esta en alguna convocatoria
                boolean estaEnConvocatorias = puestoEstaEnConvocatorias(puestoBorrar);

                if (estaEnConvocatorias) {
                    Logger.logError("NO se puede eliminar, porque el puesto de " + nombrePuesto + " esta en convocatorias");
                } else {
                    puestos.remove(puestoBorrar);

                    Logger.logSuccess("Puesto de trabajo " + nombrePuesto + " ha sido ELIMINADO");
                }
            }
        }
    }

    private boolean puestoEstaEnConvocatorias(Puesto puesto) {
        int i = 0;
        while (i<convocatorias.size() && !convocatorias.get(i).hasPuesto(puesto)) {
            i++;
        }

        return i<convocatorias.size(); //si es menor, significa que salio del while porque estaba en una convocatoria 
    }
    
    //CU AGREGAR HABILIDAD EMPLEADO
    public void agregarHabilidadEmpleado()
	{
    	Logger.header("Formulario Agregar Habilidad de Empleado");
		
		int unLegajo = InputHelper.scanInt(scanner, "Ingrese el legajo del empleado: ");

		Empleado unEmpleado = this.buscarEmpleado(unLegajo);

		if (unEmpleado == null)
			Logger.logError("No existe el empleado.");
		else
		{
			System.out.print("Ingrese el nombre de la habilidad: ");
			String nombre = scanner.nextLine();
			
			Habilidad habilidadExistente = this.buscarHabilidad(nombre);

			if(habilidadExistente == null) {
				habilidadExistente = this.crearUnaHabilidad(nombre);
			}

            unEmpleado.agregarHabilidad(scanner, habilidadExistente);
		}	
	}
		

    //CU QUITAR HABILIDAD EMPLEADO 
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

            if(habilidadExistente == null) {
                Logger.logError("NO existe la habilidad " + nombre);
            } else {
                unEmpleado.eliminarHabilidad(habilidadExistente);
            }
		}
    }
    
    //CU EDITAR ANNOS EMPLEADOS
    public void editarAnnosEmpleado() {
    	Logger.header("Formulario Editar Annos Empleados");
    	
    	int unLegajo = InputHelper.scanInt(scanner, "Ingrese el legajo del empleado: ");

    	Empleado unEmpleado = this.buscarEmpleado(unLegajo);
        
    	if (unEmpleado == null) {
			Logger.logError("NO existe el empleado");
		} else {
            System.out.print("Ingrese el nombre de la habilidad: ");
			String nombre = scanner.nextLine();

			Habilidad habilidad = this.buscarHabilidad(nombre);

			if(habilidad == null) {
				Logger.logError("NO existe la habilidad " + nombre);
			} else {
				unEmpleado.modificarAnnos(scanner, habilidad);
			}
        }
	}

    //CASO DE USO DAR DE BAJA CONVOCATORIA
    public void darDeBajaConvocatoria() {
        Logger.header("Dar de baja una convocatoria");

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


    //CASO DE USO MOSTRAR CONVOCATORIAS PUEDA APLICAR EMPLEADO
    public void mostrarConvocatoriasPuedaAplicarEmpleado() {
        //le muestro al empleado todas las convocatorias que pueda aplicar de todos los puestos
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
        
        ArrayList<Convocatoria>convocatoriaPuedeAplicar = convocatoriasPuedeAplicar(empleadoAplicar);

        if (convocatoriaPuedeAplicar.size() == 0) {
            Logger.logSuccess("Lo sentimos, NO puede aplicar a NINGUNA convocatoria");
        } else {
            Logger.header("Convocatorias disponibles");
            for (Convocatoria convocatoria : convocatoriaPuedeAplicar) {
                convocatoria.mostrar(); //muestra sin los datos de los postulantes y asignados xq esta pensado para que lo vea el empleado
            }
        }
        
    }

    private ArrayList<Convocatoria> convocatoriasPuedeAplicar(Empleado empleadoAplicar) {
        ArrayList<Convocatoria> convocatoriasPuedeAplicar = new ArrayList<>();

        for (Convocatoria convocatoria: convocatorias) {
            if (convocatoria.puedeAplicar(empleadoAplicar)) {
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
            puestoAplicar.mostrarConvocatoriasPuedeAplicar(empleadoAplicar);
        }
    }

    private void mostrarConvocatoriasPuedaAplicarRangoSalario(Empleado empleadoAplicar) {
        
        float salarioMin = InputHelper.scanFloat(scanner, "Salario minimo: ");
        float salarioMax = InputHelper.scanFloat(scanner, "Salario maximo: ");

        ArrayList<Convocatoria> convocatoriasPuedeAplicar = convocatoriasPuedeAplicarRango(empleadoAplicar, salarioMin, salarioMax);

        if (convocatoriasPuedeAplicar.size() == 0) {
            Logger.logSuccess("Lo sentimos, no puede aplicar a NINGUNA convocatoria dentro del rango " + salarioMin +" - " + salarioMax);
        } else {
            Logger.header("Convocatorias disponibles con sueldo " + salarioMin + "$ - " + salarioMax + "$");
            for (Convocatoria convocatoria: convocatoriasPuedeAplicar) {
                convocatoria.mostrar();
            }
        }
        
    }

    private ArrayList<Convocatoria> convocatoriasPuedeAplicarRango(Empleado empleadoAplicar, float salarioMin, float salarioMax) {
        ArrayList<Convocatoria> convocatoriasPuedeAplicar = new ArrayList<>();

        for (Convocatoria convocatoria: convocatorias) {
            if (convocatoria.puedeAplicar(empleadoAplicar) && convocatoria.dentroDeRango(salarioMin,salarioMax)) {
                convocatoriasPuedeAplicar.add(convocatoria);
            }
        }

        return convocatoriasPuedeAplicar;
    } 


    private boolean hayConvocatoriasAbiertas() {
        int i = 0;

        while (i<convocatorias.size() && !convocatorias.get(i).estaAbierta()) {
            i++;
        }

        return i < convocatorias.size();
    }

    //CASO DE USO BORRAR HABILIDAD DEL SISTEMA
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
                Logger.logWarning("La habilidad esta siendo utilizada en el sistema (la tiene un empleado o convocatoria)");

                boolean quiereEliminar = InputHelper.yesOrNoInput(scanner, "Quiere eliminarla?");

                if (!quiereEliminar) {
                    //decide no elimarla del sistema
                    Logger.logSuccess("Habilidad " + nombreHabilidad + " NO ha sido eliminada del sistema");

                } else {
                    //decide eliminarla por compleo del sistema
                    //eliminar de empleados
                    if (empleadosTienenHabilidad) {
                        for (Empleado empleado: empleados) {
                            empleado.tryEliminarHabilidad(habilidadEliminar);
                        }
                    }

                    //eliminar de convocatorias
                    if (convocatoriasTienenRequisito) {
                        for (Convocatoria convocatoria: convocatorias) {
                            convocatoria.tryEliminarRequisito(habilidadEliminar);
                        }
                    }

                    //elimino de lista de la Empresa
                    habilidades.remove(habilidadEliminar);

                    Logger.logSuccess("Habilidad " + nombreHabilidad + " eliminada del sistema");
                }
            }
        }
    }

    public boolean empleadosTienenHabilidad(Habilidad habilidad) {
        //busco si esta en algun empleado
        int i = 0;
        while (i<empleados.size() && !empleados.get(i).tieneHabilidad(habilidad)) {
            i++;
        }

        return i<empleados.size();
    }

    public boolean convocatoriasTienenRequisito(Habilidad habilidad) {
        //busco si esta en alguna convocatoria
        int i = 0;
        while (i<convocatorias.size() && !convocatorias.get(i).tieneRequisito(habilidad)) {
            i++;
        }

        return i<convocatorias.size();
    }

    //CU VER DATOS EMPLEADO
    public void verDatosEmpleado() {
        Logger.header("Mostrar empleado");

        int unLegajo = InputHelper.scanInt(scanner, "Ingrese el legajo del empleado");
        Empleado unEmpleado = this.buscarEmpleado(unLegajo);

        if (unEmpleado == null) {
            Logger.logError("NO existe el empleado con legajo " + unEmpleado);
        } else {
            Logger.divider();
            unEmpleado.mostrar();
            Logger.divider();
        }
    }
}

