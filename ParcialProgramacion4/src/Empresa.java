import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import utilidades.Fecha;

public class Empresa {
    Scanner scanner = new Scanner(System.in);

    private ArrayList<Empleado> empleados;
    private ArrayList<Puesto>puestos;
    private ArrayList<Convocatoria>convocatorias;
    private ArrayList<Habilidad>habilidades;

    public Empresa() {
        this.empleados = new ArrayList<Empleado>();
        this.puestos = new ArrayList<Puesto>();
        this.convocatorias = new ArrayList<Convocatoria>();
        this.habilidades = new ArrayList<Habilidad>();
    }

    //CU-01 AGREGAR HABILIDAD A LISTA DE LA EMPRESA
    public void agregarHabilidad() {
        System.out.println("Codigo habilidad: ");
        int codigo = Integer.parseInt(scanner.nextLine());

        Habilidad habilidadRepetida = this.buscarHabilidad(codigo);

        if(habilidadRepetida == null) {
            System.out.println("Nombre habilidad: ");
            String nombre = scanner.nextLine();

            System.out.println("Descripcion habilidad: ");
            String descripcion = scanner.nextLine();

            Habilidad habilidadNueva = new Habilidad(codigo, nombre, descripcion);

            this.habilidades.add(habilidadNueva);

            System.out.println("Habilidad registrada con exito !!!");

        } else {
            System.out.println("ERROR: ya existe una habilidad con este codigo");
        }
    }
    
    //AGREGADO --- BORRAR HABILIDAD
    public void borrarHabilidad() {
    	//Le doy al usuario la opcion de buscar la habilidad por codigo o nombre
    	System.out.println("Borrar la habilidad ingresando su: ");
    	System.out.println("1- Codigo");
    	System.out.println("2- Nombre");
    	int opcion = Integer.parseInt(scanner.nextLine());
    	
    	switch (opcion) {
    	case 1:
    		this.borrarHabilidadPorCodigo();
    		break;
    	case 2:
    		this.borrarHabilidadPorNombre();
    		break;
    	default:
    		System.out.println("Opcion no valida");
    		break;
    	}
    }
    
    public void borrarHabilidadPorCodigo() {
    	System.out.println("Codigo habilidad: ");
        int codigo = Integer.parseInt(scanner.nextLine());

        Habilidad habilidadBorrar = this.buscarHabilidad(codigo);

        if(habilidadBorrar != null) {
        	System.out.println("\n----------------------------------------");
        	System.out.println("Información habilidad a eliminar: ");
        	habilidadBorrar.mostrar();
        	System.out.println("----------------------------------------\n");
        	
        	this.habilidades.remove(habilidadBorrar);

            System.out.println("Habilidad borrada con exito !!!");

        } else {
            System.out.println("ERROR: no existe una habilidad con este codigo");
        }
    }
    
    public void borrarHabilidadPorNombre() {
    	System.out.println("Nombre habilidad: ");
        String nombre = scanner.nextLine();

        Habilidad habilidadBorrar = this.buscarHabilidad(nombre);

        if(habilidadBorrar != null) {
        	System.out.println("\n----------------------------------------");
        	System.out.println("Información habilidad a eliminar: ");
        	habilidadBorrar.mostrar();
        	System.out.println("----------------------------------------\n");
        	
        	this.habilidades.remove(habilidadBorrar);

            System.out.println("Habilidad borrada con exito !!!");

        } else {
            System.out.println("ERROR: no existe una habilidad con este nombre");
        }
    }
    
    
    
    
    
    

    //overriding para solo agregar a la lista una habilidad ya definida, se usa en pedirListaHabilidades
    public void agregarHabilidad(Habilidad habilidadNueva) {
        this.habilidades.add(habilidadNueva);
        System.out.println("Habilidad registrada en la lista general de la empresa");
    }
    
    
    

    //CU-02 AGREGAR PUESTO VACANTE A LA LISTA DE LA EMPRESA
    public void agregarPuesto() {

        System.out.println("Nombre del puesto: ");
        String nombre = scanner.nextLine();

        Puesto puestoRepetido = this.buscarPuesto(nombre);

        if (puestoRepetido == null) {
            System.out.println("Sueldo: ");
            double sueldo = Double.parseDouble(scanner.nextLine()); 

            System.out.println("Es un puesto jerarquico? [SI/NO]");
            String esJerarquico = scanner.nextLine(); 

            while (!esJerarquico.equalsIgnoreCase("SI") && !esJerarquico.equalsIgnoreCase("NO")) {
                System.out.println("Ingrese una opcion valida, SI o NO");
                System.out.println("Es un puesto jerarquico? [SI/NO]");
                esJerarquico = scanner.nextLine();
            }

            Puesto puestoNuevo;

            if (esJerarquico.equalsIgnoreCase("SI")) {
                puestoNuevo = new PuestoJerarquico(nombre, sueldo);
            } else { 
            	//ya verifique antes, solo puede ser NO, sino no saldria del bucle
                puestoNuevo = new PuestoNoJerarquico(nombre, sueldo);
            }

            this.puestos.add(puestoNuevo);

            System.out.println("Puesto vacante añadido con exito !!!");

        } else {
            System.out.println("ERROR: ya se encuentra registrado un puesto con ese nombre");
        }
    }
    
    //AGREGADO --- BORRAR PUESTO DE TRABAJO
    public void borrarPuesto() {
    	System.out.println("Nombre puesto a eliminar: ");
    	String nombrePuesto = scanner.nextLine();
    	
    	Puesto puestoBorrar = this.buscarPuesto(nombrePuesto);
    	
    	if (puestoBorrar != null) {
    		System.out.println("\n----------------------------------------");
        	System.out.println("Información puesto de trabajo a eliminar: ");
        	puestoBorrar.mostrar();
        	System.out.println("----------------------------------------\n");
    		
        	puestos.remove(puestoBorrar);
        	
        	System.out.println("Puesto borrado con exito !!!");
    		
    	} else {
    		System.out.println("ERROR: NO existe un puesto con ese nombre");
    	}
    }
    
    
    
    
    
    
    
    

    //CU-03 AGREGAR EMPLEADO AL SISTEMA
    public void agregarEmpleado() {
        System.out.println("Numero de legajo: ");
        int legajo = Integer.parseInt(scanner.nextLine());

        Empleado empleadoRepetido = this.buscarEmpleado(legajo);

        if(empleadoRepetido == null) {
            System.out.println("Nombre: ");
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
            System.out.println("Ingreso de habilidades del empleado con sus años de experencia en cada una: ");
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

            System.out.println("Empleado agregado a lista de la empresa!!!");

        } else {
            System.out.println("ERROR: ya existe un empleado con ese numero de legajo");
        }
    }

    //para que no quede tanto codigo en metodo agregarEmpleado
    private ArrayList<Cargo> pedirListaCargos(Fecha fechaIngresoEmpresa) {
        // INGRESAR TODOS LOS CARGOS QUE UN EMPLEADO OCUPO HASTA AHORA

        System.out.println("\nA continuación ingresara el historial de cargos ocupados por el empleado: ");
        System.out.println("Primero se pedira los cargos ANTIGUOS que ocupo el mismo (Se ingresa SI en caso de que los tenga)");
        System.out.println("Luego cuando usted lo decida, ingresara el cargo ACTUAL (Ingresando NO a la pregunta 'tiene puestos antiguos'");

        //creo un arraylist local para el historial de cargos para pasarle al constructor de empleado 
        ArrayList<Cargo> historialDeCargos;
        historialDeCargos = new ArrayList<Cargo>();

        //primero ingreso los cargos antiguos
        System.out.println("\nTiene puestos ANTIGUOS? (SI/NO)");
        String tienePuestoAntiguo = scanner.nextLine();
        
        while (!tienePuestoAntiguo.equalsIgnoreCase("SI") && !tienePuestoAntiguo.equalsIgnoreCase("NO")) {
            System.out.println("Ingrese una opcion valida, SI o NO");
            System.out.println("Tiene puestos antiguos? (SI/NO)");
            tienePuestoAntiguo = scanner.nextLine();
        }
        
        System.out.println("RECORDAR: Los puestos antiguos se deben ingresar en orden empezando desde el mas antiguo");
        
        //carga del cargo mas antiguo en la que la fecha de ingreso al cargo es la misma que la de ingreso a la empresa
        if (tienePuestoAntiguo.equalsIgnoreCase("SI")) {
        	//se repite el codigo porque asi puedo sacar la fecha en la que ingreso a la empresa
        	//para utilizarla como la fecha de inicio del primer cargo
        	
        	System.out.println("Nombre puesto: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puesto = this.buscarPuesto(nombrePuesto);

            if(puesto != null) {
                //fecha de ingreso al cargo es la fecha en la que ingreso a la empresa

                System.out.println("\nFecha final del puesto: ");
                Fecha fechaFin = Fecha.nuevaFecha();

                Cargo nuevoCargo = new Cargo(fechaIngresoEmpresa, fechaFin, puesto);

                //agrego en arraylist local
                historialDeCargos.add(nuevoCargo);

                System.out.println("Puesto antiguo agregado!!");

            } else {
                System.out.println("No existe un puesto con ese nombre, pruebe de nuevo");
            }
        
        }
        
        
        System.out.println("Ingresar más puestos ANTIGUOS? (SI/NO)");
        String otro = scanner.nextLine();

        while (!otro.equalsIgnoreCase("SI") && !otro.equalsIgnoreCase("NO")) {
            System.out.println("Ingrese una opcion valida, SI o NO");
            System.out.println("Tiene puestos antiguos? (SI/NO)");
            otro = scanner.nextLine();
        }
        
        while (otro.equalsIgnoreCase("SI")) {
            System.out.println("Nombre puesto: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puesto = this.buscarPuesto(nombrePuesto);

            if(puesto != null) {
                //fecha de ingreso es la fecha de fin del ultimo puesto
            	//como el ingreso es en orden: 
            	Fecha fechaInicio = historialDeCargos.get(historialDeCargos.size()-1).getFechaFin();

                System.out.println("\nFecha final del puesto: ");
                Fecha fechaFin = Fecha.nuevaFecha();

                Cargo nuevoCargo = new Cargo(fechaInicio, fechaFin, puesto);

                //agrego en arraylist local
                historialDeCargos.add(nuevoCargo);

                System.out.println("Puesto antiguo agregado!!");

            } else {
                System.out.println("No existe un puesto con ese nombre, pruebe de nuevo");
            }
        
            
            System.out.println("Ingresar más puestos ANTIGUOS? (SI/NO)");
            otro = scanner.nextLine();

            while (!otro.equalsIgnoreCase("SI") && !otro.equalsIgnoreCase("NO")) {
                System.out.println("Ingrese una opcion valida, SI o NO");
                System.out.println("Tiene puestos antiguos? (SI/NO)");
                otro = scanner.nextLine();
            }
        }

        //INGRESAR CARGO ACTUAL, SOLO PREGUNTA FECHA INICIO Y EL PUESTO
        System.out.println("INGRESO PUESTO ACTUAL");
        System.out.println("Nombre puesto actual: ");
        String nombrePuestoActual = scanner.nextLine();

        Puesto puestoActual = this.buscarPuesto(nombrePuestoActual);

        while (puestoActual == null) { //es un while porque si o si debe tener un puesto actual, sino no seria empleado
            System.out.println("No existe puesto con ese nombre, intente nuevamente");
            System.out.println("Nombre puesto actual: ");
            nombrePuestoActual = scanner.nextLine();

            puestoActual = this.buscarPuesto(nombrePuestoActual);
        }

        System.out.println("\nFecha ingreso al puesto: ");
        Fecha fechaInicio = Fecha.nuevaFecha();

        Cargo nuevoCargo = new Cargo(fechaInicio, null, puestoActual); //mando null a fechaFin para colocarlo cuando abandone cargo

        //agrego en arraylist local
        historialDeCargos.add(nuevoCargo);

        System.out.println("Cargo actual agregado!!");    

        return historialDeCargos; //para usarlo en el constructor de Empleado
    }

    //sirve para CU-03 agregar empleadado y CU-04 generar convocatoria
    private Hashtable<Habilidad, Integer> pedirListaHabilidades() {
        //ingresar las habilidades y los años de experiencia en cada una
        
        //crear hashtable local
        Hashtable<Habilidad, Integer> habilidades;
        habilidades = new Hashtable<Habilidad, Integer>();

        //llenar la hashtable con las habilidades
        String otra;

        do {
            System.out.println("Nombre habilidad: ");
            String nombreHabilidad = scanner.nextLine();

            //busco que la habilidad exista
            Habilidad habilidad = this.buscarHabilidad(nombreHabilidad);

            if(habilidad == null) {
                //no existe, la creamos ahora
                System.out.println("Codigo habilidad: ");
                int codigoHabilidad = Integer.parseInt(scanner.nextLine()); //porque despues va un nextLine

                //VERIFICAR QUE CODIGO NO SEA REPETIDO
                Habilidad habilidadRepetida = this.buscarHabilidad(codigoHabilidad);

                while (habilidadRepetida != null) {
                    System.out.println("Codigo repetido, elija otro: ");
                    codigoHabilidad = Integer.parseInt(scanner.nextLine());
                    habilidadRepetida = this.buscarHabilidad(codigoHabilidad);
                }

                System.out.println("Descripcion: ");
                String descripcion = scanner.nextLine();

                habilidad = new Habilidad(codigoHabilidad, nombreHabilidad, descripcion);

                this.agregarHabilidad(habilidad);
            }

            //ya tengo la habilidad, verifico si ya esta agregada en la hashtable
            if(!habilidades.containsKey(habilidad)) {
                //ya se que no esta, ahora pido los años de experiencia en ella
                System.out.println("Años de experiencia en "+nombreHabilidad+": ");
                int annosExperiencia = Integer.parseInt(scanner.nextLine()); //porque despues va un nextLine

                //agrego a la hashtable
                habilidades.put(habilidad, annosExperiencia);

                System.out.println("Habilidad agregada!!!");

            } else
                System.out.println("La habilidad ya esta agregada en la lista");

            System.out.println("Agregar otra habilidad: (SI/NO)");
            otra = scanner.nextLine(); //nextLinea porque despues va otro nextLine para evitar que se coma el salto de pagina
            
            while (!otra.equalsIgnoreCase("SI") && !otra.equalsIgnoreCase("NO")) {
                System.out.println("Ingrese una opcion valida, SI o NO");
                System.out.println("Agregar otra habilidad: (SI/NO)");
                otra = scanner.nextLine();
            }
        } while (otra.equalsIgnoreCase("SI"));

        return habilidades;
    }
    
    
    
    
    
    //AGREGADO --- DAR DE BAJA EMPLEADO
    public void darDeBajaEmpleado() {
    	System.out.println("\nNumero de legajo empleado a eliminar: ");
    	int legajo = Integer.parseInt(scanner.nextLine());
    	
    	Empleado empleadoEliminar = this.buscarEmpleado(legajo);
    	
    	if (empleadoEliminar != null) {
    		System.out.println("\n----------------------------------------");
        	System.out.println("Información empleado a eliminar: ");
        	empleadoEliminar.mostrar();
        	System.out.println("----------------------------------------\n");
        	
        	//Se podria elimar de las convocatorias, pero queda como registro historico de quienes se postularon
        	//independientemente de si ya no son parte de la empresa o no
        	
        	//si se debe sacar de las convocatorias todavia abiertas para que no se lo seleccione ya que no existe
        	for(Convocatoria convocatoria: convocatorias) {
        		boolean estaInscripto = convocatoria.empEstaInscripto(empleadoEliminar) || convocatoria.empEstaAsignado(empleadoEliminar);
        		if (estaInscripto && convocatoria.noPasoFecha()) {
        			//si esta inscripto, tambien puede haber sido seleccionado para el puesto
        			//si la convocatoria todavia no paso su fecha, significa que NO es historica, por lo que debe 
        			//eliminar al empleado en la lista asignados ademas de inscriptos, porque parece que ya se eligio a alguien pero esa persona
        			//ya no forma parte de la empresa
        			
        			//se elimina de la convocatoria al empleado en la lista de inscriptos y asignados (SOLO si NO es una convocatoria historica)
        			//se considera una convocatoria histórica aquella que NO paso la fecha establecida en la misma
        			convocatoria.eliminarEmpleado(empleadoEliminar); 
        		}
        	}
        	
        	
        	//antes de eliminarlo de la lista de empresa, lo eliminamos de la lista de su puesto actual
        	Puesto puestoEmpleado = empleadoEliminar.getPuestoActual();
        	puestoEmpleado.eliminarEmpleado(empleadoEliminar);
        	
        	//ahora se elimina de la lista de la empresa
        	empleados.remove(empleadoEliminar);
        	
        	//si bien se elimino el empleado de la lista de la empresa, todavia se puede encontrar en convocatorias historicas en la lista de postulados y asignados
        	
        	System.out.println("Empleado eliminado con exito !!!");
    		
    	} else {
    		System.out.println("ERROR: No existe empleado con el numero de legajo " + legajo);
    	}
    }
    
    


    //CU-04 AGREGAR o GENERAR NUEVA CONVOCATORIA
    public void agregarConvocatoria() {
        System.out.println("Codigo convocatoria: ");
        int codigoConvocatoria = Integer.parseInt(scanner.nextLine()); 

        Convocatoria convocatoriaRepetida = this.buscarConvocatoria(codigoConvocatoria);

        if(convocatoriaRepetida == null) {
            System.out.println("Nombre puesto: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puestoConvocatoria = this.buscarPuesto(nombrePuesto);

            if(puestoConvocatoria != null) {
                System.out.println("Fecha de la convocatoria: ");
                Fecha fechaConvocatoria = Fecha.nuevaFecha();

                System.out.println("Cantidad de empleados requeridos: ");
                int cantEmpleadosRequeridos = Integer.parseInt(scanner.nextLine()); 

                //hashtable la obtengo con la funcion pedirHabilidades, es la misma estructura
                System.out.println("Requisitos necesarios para aplicar a la convocatoria: ");
                Hashtable<Habilidad, Integer>requisitos = this.pedirListaHabilidades();

                //crear convocatoria 
                Convocatoria convocatoriaNueva;

                if (puestoConvocatoria.esJerarquico()) {
                    System.out.println("Años minimos en la empresa que se requieren para aplicar: ");
                    int annosMinimosEnEmpresa = Integer.parseInt(scanner.nextLine()); 

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
                //agregar convocatoria a lista de convocatorias de la empresa
                convocatorias.add(convocatoriaNueva);
                System.out.println("Convocatoria registrada en el sistema!!!");

                //agregar convocatoria al Puesto que se busca
                puestoConvocatoria.agregarConvocatoria(convocatoriaNueva);

            } else {
                System.out.println("ERROR: no existe un puesto con este nombre");
            }
        } else {
            System.out.println("ERROR: ya existe una convocatoria con este codigo");
        }
    }

    //CU-05 INSCRIBIR EMPLEADO A CONVOCATORIA
    public void inscribirEmpleadoConvocatoria() {
        System.out.println("Numero legajo del empleado: ");
        int legajoEmpleado = Integer.parseInt(scanner.nextLine());

        Empleado empleado = this.buscarEmpleado(legajoEmpleado);

        if(empleado != null) {
            System.out.println("Nombre del puesto al que quiere inscribirse: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puestoAplicar = this.buscarPuesto(nombrePuesto);

            if(puestoAplicar != null) {
                //ver convocatorias abiertas que puede aplicar el empleado
                int cantPuedeAplicar = puestoAplicar.mostrarConvocatoriasQueSePuedaInscribir(empleado); 
                
                //con 'cantPuedeAplicar' obtengo a cuantas convocatorias puede aplicar para saber si tengo que mostrar 
                //la opcion de elegir y para saber cuantas veces tengo que pedir por codigos para aplicar

                
                if (cantPuedeAplicar > 0) {
                	System.out.println("se puede inscribir a " + cantPuedeAplicar + " convocatorias");
                	
                	
                    System.out.println("Codigos de convocatorias que se quiera inscribir (una por vez, 0 para fin): ");
                    int codigoConvocatoria = Integer.parseInt(scanner.nextLine());

                    Convocatoria convocatoria;

                    int i = 0; //para saber cuantas veces tengo que pedir el codigo para inscribir (el mensaje del while)
                    
                    /* 
                    Funcion del contador 'i': sirve para que no tengamos que preguntar al usuario por el codigo por si quiere aplicar 
                    a una convocatoria si ya no tiene mas convocatorias para aplicar
                    Si por ejemplo el usuario puede inscribirse a 2 convocatorias, luego de que se inscriba a las 2, no se le 
                    vuelve a preguntar por otro codigo o 0 para fin, directamente se termina el while 
                    */

                    while (codigoConvocatoria != 0 && i < cantPuedeAplicar) {
                        //buscar convocatoria con ese codigo y pedir de inscbirse a empleado
                        convocatoria = this.buscarConvocatoria(codigoConvocatoria);

                        if(convocatoria != null) {
                            convocatoria.inscribirEmpleado(empleado);
                            i++; //para saber si tengo que preguntar de nuevo por el codigo o no
                        } else {
                            System.out.println("ERROR: codigo incorrecto, no existe convocatoria con ese codigo que pueda aplicar");
                        }
                        
                        /*Si ya no tiene mas convocatorias para anotarse, recien se va a detectar cuando compruebe la condicion, por lo que
                         si no se pone este if, va a preguntar de manera innecesaria si quiere anotarse a otra convocatoria, porque saldria por la 2da condicion */
                        
                        if (i < cantPuedeAplicar) { //para que no vuelva a preguntar si ya no se puede inscribir a otra, sale por la 2da condicion
                            System.out.println("Codigos de convocatorias que se quiera inscribir (una por vez, 0 para fin): ");
                            codigoConvocatoria = Integer.parseInt(scanner.nextLine());
                        } else {
                        	System.out.println(" Ya no puede aplicar a ninguna convocatoria más!");
                        }
                    }
                } else {
                    System.out.println("Lo sentimos! No puede aplicar a ninguna convocatoria para este puesto");
                }

            } else
                System.out.println("ERROR: no existe un puesto con ese nombre");

        } else
            System.out.println("No existen empleado con ese legajo");
    }

    //CU-06 ELEGIR EMPLEADOS EN CONVOCATORIA
    public void elegirEmpleadosConvocatoria() {
        //se podria agregar una opcion para que el usuario vea las convocatorias de un puesto determinado si quiere

        System.out.println("Codigo convocatoria");
        int codigoConvocatoria = Integer.parseInt(scanner.nextLine());

        Convocatoria convocatoria = this.buscarConvocatoria(codigoConvocatoria);

        if (convocatoria != null) {
            convocatoria.elegirEmpleadosConvocatoria();

        } else {
            System.out.println("ERROR: NO existe una convocatoria con ese codigo");
        }
    }

    //AGREGADO --- CU-0X MOSTRAR TODAS LAS CONVOCATORIAS ABIERTAS
    public void mostrarConvocatoriasAbiertas() {
    	for(Convocatoria convocatoria: convocatorias) {
    		if(convocatoria.estaAbierta()) {
    			convocatoria.mostrar();
    		}
    	}
    }


    //AGREGADO --- CU-0X MOSTRAR CONVOCATORIAS ABIERTAS A LAS QUE PUEDE APLICAR EMPLEADO
    public void mostrarConvocatoriasPuedaInscribirEmpleado() {
    	//como ya tengo programada para que muestre todas las convocatorias a las que puede
    	//inscibirse de un puesto, recorro todos los puesto y muestro ordenadas por puesto
    	
    	System.out.println("Numero legajo del empleado: ");
    	int legajo = Integer.parseInt(scanner.nextLine());
    	
    	Empleado empleado = this.buscarEmpleado(legajo);
    	
    	if(empleado != null) {
    		System.out.println("Convocatorias a las que se pueda inscribir, ordenadas por puestos");
    		
    		for(Puesto puesto: puestos) {
    			puesto.mostrarConvocatoriasQueSePuedaInscribir(empleado);
    		}
    		
    	} else {
    		System.out.println("ERROR: No existe empleado con ese numero de legajo");
    	}
    }

    
    //AGREGADO --- CU-0X DAR DE BAJA CONVOCATORIA (ABIERTA)
    public void darDeBajaConvocatoria() {
    	System.out.println("Codigo convocatoria a dar de baja: ");
    	int codigoConvocatoria = Integer.parseInt(scanner.nextLine());
    	
    	Convocatoria convocatoria = this.buscarConvocatoria(codigoConvocatoria);
    	
    	if(convocatoria != null && convocatoria.estaAbierta()) {
    		System.out.println("\n----------------------------------------");
        	System.out.println("Información convocatoria a eliminar: ");
        	convocatoria.mostrar();
        	System.out.println("----------------------------------------\n");
    		
        	//primero elimino del puesto para el cual era la convocatoria
        	Puesto puestoConvocatoria = convocatoria.getPuesto();
        	
        	puestoConvocatoria.darDeBajaConvocatoria(convocatoria);
        	
        	//segundo elimino en la lista de la empresa
        	convocatorias.remove(convocatoria);
        	
    		
    	} else {
    		if(convocatoria != null) {
    			System.out.println("ERROR: No existe convocatoria con ese codigo");
    		} else {
    			//las convocatorias cerradas se dejan para un registro historico, no pueden ser eliminadas
    			System.out.println("ERROR: Convocatoria esta cerrada, NO se puede eliminar");
    		}
    	}
    }
    
    
    //AGREGADO --- DAR DE BAJA INSCRIPTO A CONVOCATORIA
    public void darDeBajaInscriptoConvocatoria() {
    	System.out.println("Codigo convocatoria: ");
    	int codigoConvocatoria = Integer.parseInt(scanner.nextLine());
    	
    	Convocatoria convocatoria = this.buscarConvocatoria(codigoConvocatoria);
    	
    	if(convocatoria != null) {
    		
    		if(convocatoria.estaAbierta()) {
    			System.out.println("Numero de legajo del empleado a dar de baja: ");
    			int legajo = Integer.parseInt(scanner.nextLine());
    			
    			Empleado empleado = this.buscarEmpleado(legajo);
    			
    			if(empleado != null) {
    				
    				if(convocatoria.empEstaInscripto(empleado)) {
    					convocatoria.darDeBajaPostulante(empleado);
    					
    					System.out.println("Empleado dado de baja en convocatoria!!!");
    					
    				} else {
    					System.out.println("ERROR: El empleado NO esta inscripto en la convocatoria");
    				}
    				
    			} else {
    				System.out.println("ERROR: No existe ningun empleado con ese legajo en el sistema");
    			}
    			 
    		} else {
    			System.out.println("ERROR: La convocatoria esta CERRADA, no se pueden realizar modificaciones");
    		}
    		
    	} else {
    		System.out.println("ERROR: No existe convocatoria con el codigo " + codigoConvocatoria);
    	}
    }
    
    

    private Habilidad buscarHabilidad(int codigo) {
        int i = 0;

        while(i<habilidades.size() && !habilidades.get(i).hasHabilidad(codigo))
            i++;
        
        if(i<habilidades.size())
            return habilidades.get(i);
        else
            return null;
    }

    private Habilidad buscarHabilidad(String nombre) {
        int i = 0;

        while(i<habilidades.size() && !habilidades.get(i).hasHabilidad(nombre))
            i++;
        
        if(i<habilidades.size())
            return habilidades.get(i);
        else
            return null;
    }

    private Puesto buscarPuesto(String nombre) {
        int i = 0;

        while(i<puestos.size() && !puestos.get(i).hasPuesto(nombre))
            i++;
        
        if(i<puestos.size())
            return puestos.get(i);
        else
            return null;
    }

    private Empleado buscarEmpleado(int legajo) {
        int i = 0;

        while(i<empleados.size() && !empleados.get(i).hasEmpleado(legajo))
            i++;
        
        if(i<empleados.size())
            return empleados.get(i);
        else
            return null;
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
}
