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


    //CASO DE USO AGREGAR EMPLEADO AL SISTEMA
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

    private ArrayList<Cargo> pedirListaCargos(Fecha fechaIngresoEmpresa) {
        // INGRESAR TODOS LOS CARGOS QUE UN EMPLEADO OCUPO HASTA AHORA

        System.out.println("\nA continuación ingresara el historial de cargos ocupados por el empleado: ");
        System.out.println("Primero se pedira los cargos ANTIGUOS que ocupo el mismo (Se ingresa SI en caso de que los tenga)");
        System.out.println("Luego cuando usted lo decida, ingresara el cargo ACTUAL (Ingresando NO a la pregunta 'tiene puestos antiguos'\n");

        //creo un arraylist local para el historial de cargos para pasarle al constructor de empleado (es lo que retorno)
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
        //primero es un 'if' y no un while porque la fecha de inicio del cargo mas antiguo se saca de la fecha de ingreso a la empresa
        //pero en los proximos cargos antiguos se saca de la fecha de fin del anterior cargo
        if (tienePuestoAntiguo.equalsIgnoreCase("SI")) {
        	//se repite el codigo porque asi puedo sacar la fecha en la que ingreso a la empresa
        	//para utilizarla como la fecha de inicio del primer cargo
        	
        	System.out.println("Nombre puesto: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puesto = this.buscarPuesto(nombrePuesto);

            //SI NO EXISTE, SE LE PREGUNTA SI LO QUIERE AGREGAR:
            if (puesto == null) {
                puesto = this.agregarPuesto(nombrePuesto); //Adentro le doy la posibilidad de crearlo o no
            }

            //tal vez el usuario puso que no quiere agregar un puesto, por eso verifico lo mismo
            if(puesto != null) {
                //fecha de ingreso al cargo es la fecha en la que ingreso a la empresa, se ingresa como parametro

                System.out.println("\nFecha final del puesto: ");
                Fecha fechaFin = Fecha.nuevaFecha();

                Cargo nuevoCargo = new Cargo(fechaIngresoEmpresa, fechaFin, puesto);

                //agrego en arraylist local
                historialDeCargos.add(nuevoCargo);

                System.out.println("Puesto antiguo agregado!!");

            } else {
                System.out.println("ERROR: No existe un puesto con ese nombre");
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

            //le doy al usuario la posibilidad de agregar el puesto si no existe
            if (puesto == null) {
                puesto = this.agregarPuesto(nombrePuesto); //Adentro le doy la posibilidad de crearlo o no
            }


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
                System.out.println("No existe un puesto con ese nombre");
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

        if (puestoActual == null) {
            //le doy al usuario la posiblidad de agregarlo
            puestoActual = this.agregarPuesto(nombrePuestoActual);
        }


        while (puestoActual == null) { //es un while porque si o si debe tener un puesto actual, sino no seria empleado
            //si el usuario presiona que no se dirige aqui
            //se deja el codigo, por si el usuario sabe que el puesto esta pero esta tipenadolo mal
            System.out.println("No existe puesto con ese nombre, intente nuevamente");
            System.out.println("Nombre puesto actual: ");
            nombrePuestoActual = scanner.nextLine();

            puestoActual = this.agregarPuesto(nombrePuestoActual); //se le pregunta si lo quiere agregar

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


    private Puesto agregarPuesto(String nombre) {
        System.out.println("No existe un puesto con ese nombre");
        System.out.println("Quiere agregarlo ahora? (SI/NO)");
        String quiereAgregarlo = scanner.nextLine();

        while (!quiereAgregarlo.equalsIgnoreCase("SI") && !quiereAgregarlo.equalsIgnoreCase("NO")) {
            System.out.println("Ingrese una opcion valida, SI o NO");
            System.out.println("Quiere agregarlo ahora? (SI/NO)");
            quiereAgregarlo = scanner.nextLine();
        }

        if (quiereAgregarlo.equalsIgnoreCase("SI")) {
            //INVOCO A METODO AGREGAR PUESTO
            //no es el mismo que el caso de uso, para que no tenga que ingresar de nuevo el nombre del puesto
            
            System.out.println("sueldo: ");
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

            System.out.println("Puesto nuevo añadido con exito !!!");

            return puestoNuevo;
        } else {
            return null; //porque no lo quizo agregar
        }
    }


    //sirve para CU agregar empleado y CU generar convocatoria
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
                System.out.println("Descripcion: ");
                String descripcion = scanner.nextLine();

                habilidad = new Habilidad(nombreHabilidad, descripcion);

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
    
    //AGREGAR HABILIDAD PARA CASO DE USO AGREGAR EMPLEADO
    //overriding para solo agregar a la lista una habilidad ya definida, se usa en pedirListaHabilidades
    //NO ES EL METODO DEL CASO DE USO AGREGAR HABILIDAD
    public void agregarHabilidad(Habilidad habilidadNueva) {
        this.habilidades.add(habilidadNueva);
        System.out.println("Habilidad registrada en la lista general de la empresa");
    }

    





    //METODOS DE BUSQUEDA
    private Empleado buscarEmpleado(int legajo) {
        int i = 0;

        while(i<empleados.size() && !empleados.get(i).hasEmpleado(legajo))
            i++;
        
        if(i<empleados.size())
            return empleados.get(i);
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

    private Habilidad buscarHabilidad(String nombre) {
        int i = 0;

        while(i<habilidades.size() && !habilidades.get(i).hasHabilidad(nombre))
            i++;
        
        if(i<habilidades.size())
            return habilidades.get(i);
        else
            return null;
    }
}
