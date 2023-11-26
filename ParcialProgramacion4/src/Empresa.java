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
        int codigo = scanner.nextInt();

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

    //overriding para solo agregar a la lista una habilidad ya definida, se usa en pedirListaHabilidades
    public void agregarHabilidad(Habilidad habilidadNueva) {
        habilidades.add(habilidadNueva);
        System.out.println("Habilidad registrada en la lista general de la empresa");
    }

    //CU-02 AGREGAR PUESTO VACANTE A LA LISTA DE LA EMPRESA
    public void agregarPuestoVacante() {

        System.out.println("Nombre: ");
        String nombre = scanner.nextLine();

        Puesto puestoRepetido = this.buscarPuestoVacante(nombre);

        if (puestoRepetido == null) {
            System.out.println("sueldo: ");
            double sueldo = Double.parseDouble(scanner.nextLine()); //para poder leer la siguiente entrada y que no salte porque lee el \n que deja el double

            System.out.println("Es un puesto jerarquico? [SI/NO]");
            String esJerarquico = scanner.nextLine(); //se usa nextLine para que el scanner quede limpio y poder leer el nombre en una proxima pasada

            while (!esJerarquico.equalsIgnoreCase("SI") && !esJerarquico.equalsIgnoreCase("NO")) {
                System.out.println("Ingrese una opcion valida, SI o NO");
                System.out.println("Es un puesto jerarquico? [SI/NO]");
                esJerarquico = scanner.nextLine();
            }

            Puesto puestoNuevo;

            if (esJerarquico.equalsIgnoreCase("SI")) {
                puestoNuevo = new PuestoJerarquico(nombre, sueldo);
            } else { //ya verifique antes, solo puede ser NO
                puestoNuevo = new PuestoNoJerarquico(nombre, sueldo);
            }

            puestos.add(puestoNuevo);

            System.out.println("Puesto vacante añadido con exito !!!");

        } else
            System.out.println("ERROR, ya se encuentra registrado un puesto con ese nombre");
        
        
    }

    //CU-03 AGREGAR EMPLEADO AL SISTEMA
    public void agregarEmpleado() {
        System.out.println("Numero de legajo: ");
        int legajo = Integer.parseInt(scanner.nextLine());

        Empleado empleadoRepetido = this.buscarEmpleado(legajo);

        if(empleadoRepetido==null) {
            System.out.println("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.println("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.println("Fecha de nacimiento: ");
            Fecha fechaNacimiento = Fecha.nuevaFecha();

            System.out.println("Fecha de ingreso a la empresa: ");
            Fecha fechaIngreso = Fecha.nuevaFecha();

            // INGRESAR TODOS LOS CARGOS QUE EMPLEADO OCUPO HASTA AHORA
            ArrayList<Cargo>historialDeCargos = this.pedirListaCargos();
            
            //crear hashtable con las habilidades y años de experiencia
            System.out.println("Ingreso de habilidades del empleado con sus años de experencia en cada una: ");
            Hashtable<Habilidad,Integer>habilidades = this.pedirListaHabilidades();

            //constructor empleado
            Empleado empleadoNuevo = new Empleado(legajo, nombre, apellido, fechaNacimiento, fechaIngreso, historialDeCargos, habilidades);

            //agrego al empleado en el puesto actual
            Puesto puestoActual = empleadoNuevo.getPuestoActual();

            puestoActual.agregarEmpleado(empleadoNuevo);

            //agregar empleado a lista de empresa
            empleados.add(empleadoNuevo);

            System.out.println("Empleado agregado a lista de la empresa!!!");

        } else 
            System.out.println("ERROR, ya existe un empleado con ese numero de legajo");
        
    }

    //para que no quede tanto codigo en metodo agregarEmpleado
    private ArrayList<Cargo> pedirListaCargos() {
        // INGRESAR TODOS LOS CARGOS QUE UN EMPLEADO OCUPO HASTA AHORA

        System.out.println("\nHistorial de cargos ocupados: ");

        //creo un arraylist local para el historial de cargos para pasarle al constructor de empleado 
        ArrayList<Cargo>historialDeCargos;
        historialDeCargos = new ArrayList<Cargo>();

        //primero ingreso los cargos antiguos
        System.out.println("Tiene puestos antiguos? (SI/NO)");
        String otro = scanner.nextLine();
        
        while (!otro.equalsIgnoreCase("SI") && !otro.equalsIgnoreCase("NO")) {
            System.out.println("Ingrese una opcion valida, SI o NO");
            System.out.println("Tiene puestos antiguos? (SI/NO)");
            otro = scanner.nextLine();
        }
        
        while (otro.equalsIgnoreCase("SI")) {
            System.out.println("Nombre puesto: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puesto = this.buscarPuestoVacante(nombrePuesto);

            if(puesto!=null) {
                System.out.println("\nFecha ingreso al puesto: ");
                Fecha fechaInicio = Fecha.nuevaFecha();

                System.out.println("\nFecha final del puesto: ");
                Fecha fechaFin = Fecha.nuevaFecha();

                Cargo nuevoCargo = new Cargo(fechaInicio, fechaFin, puesto);

                //agrego en arraylist local
                historialDeCargos.add(nuevoCargo);

                System.out.println("Puesto antiguo agregado!!");

            } else {
                System.out.println("No existe un puesto con ese nombre, pruebe de nuevo");
            }
        
            
            System.out.println("Ingresar más puestos antiguos? (SI/NO)");
            otro = scanner.nextLine();

            while (!otro.equalsIgnoreCase("SI") && !otro.equalsIgnoreCase("NO")) {
                System.out.println("Ingrese una opcion valida, SI o NO");
                System.out.println("Tiene puestos antiguos? (SI/NO)");
                otro = scanner.nextLine();
            }
        }

        //INGRESAR CARGO ACTUAL, SOLO PREGUNTA FECHA INICIO Y EL PUESTO
        System.out.println("Nombre puesto actual: ");
        String nombrePuestoActual = scanner.nextLine();

        Puesto puestoActual = this.buscarPuestoVacante(nombrePuestoActual);

        while (puestoActual==null) { //es un while porque si o si debe tener un puesto actual, sino no seria empleado
            System.out.println("No existe puesto con ese nombre, intente nuevamente");
            System.out.println("Nombre puesto actual: ");
            nombrePuestoActual = scanner.nextLine();

            puestoActual = this.buscarPuestoVacante(nombrePuestoActual);
        }

        System.out.println("\nFecha ingreso al puesto: ");
        Fecha fechaInicio = Fecha.nuevaFecha();

        Cargo nuevoCargo = new Cargo(fechaInicio, null, puestoActual); //mando null a fechaFin para colocarlo cuando abandone cargo

        //agrego en arraylist local
        historialDeCargos.add(nuevoCargo);

        System.out.println("Cargo actual agregado!!");    

        return historialDeCargos; //para mandarle al constructor de Empleado
    }

    //sirve para CU-03 agregar empleadado y CU-04 generar convocatoria
    private Hashtable<Habilidad,Integer> pedirListaHabilidades() {
        //ingresar las habilidades y los años de experiencia en cada una
        
        //crear hashtable local
        Hashtable<Habilidad,Integer> habilidades;
        habilidades = new Hashtable<Habilidad,Integer>();

        //llenar la hashtable con las habilidades
        String otra;

        do {
            System.out.println("Nombre habilidad: ");
            String nombreHabilidad = scanner.nextLine();

            //busco que la habilidad exista
            Habilidad habilidad = this.buscarHabilidad(nombreHabilidad);

            if(habilidad==null) {
                //no existe, la creamos ahora
                System.out.println("Codigo habilidad: ");
                int codigoHabilidad = Integer.parseInt(scanner.nextLine()); //porque despues va un nextLine

                //VERIFICAR QUE CODIGO NO SEA REPETIDO
                Habilidad habilidadRepetida = this.buscarHabilidad(codigoHabilidad);

                while (habilidadRepetida==null) {
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


    //CU-04 AGREGAR o GENERAR NUEVA CONVOCATORIA
    public void agregarConvocatoria() {
        System.out.println("codigo convocatoria: ");
        int codigoConvocatoria = Integer.parseInt(scanner.nextLine()); //porque despues va un nextLine

        Convocatoria convocatoriaRepetida = this.buscarConvocatoria(codigoConvocatoria);

        if(convocatoriaRepetida==null) {
            System.out.println("Nombre puesto: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puestoConvocatoria = this.buscarPuestoVacante(nombrePuesto);

            if(puestoConvocatoria!=null) {
                System.out.println("Fecha de la convocatoria: ");
                Fecha fechaConvocatoria = Fecha.nuevaFecha();

                System.out.println("Cantidad de empleados requeridos: ");
                int cantEmpleadosRequeridos = Integer.parseInt(scanner.nextLine()); //porque despues va un nextLine

                //hashtable la obtengo con la funcion pedirHabilidades, es la misma estructura
                System.out.println("Requisitos necesarios para aplicar a la convocatoria: ");
                Hashtable<Habilidad,Integer>requisitos = this.pedirListaHabilidades();

                //crear convocatoria 
                Convocatoria convocatoriaNueva;

                if (puestoConvocatoria.esJerarquico()) {
                    System.out.println("Años minimos en la empresa que se requieren para aplicar: ");
                    int annosMinimosEnEmpresa = Integer.parseInt(scanner.nextLine()); 

                    convocatoriaNueva = new ConvocatoriaJerarquico(codigoConvocatoria,puestoConvocatoria,fechaConvocatoria,cantEmpleadosRequeridos,annosMinimosEnEmpresa,requisitos);

                } else { 

                    convocatoriaNueva = new ConvocatoriaNoJerarquico(codigoConvocatoria,puestoConvocatoria,fechaConvocatoria,cantEmpleadosRequeridos,requisitos);

                }
                //agregar convocatoria a lista de convocatorias de la empresa
                convocatorias.add(convocatoriaNueva);
                System.out.println("Convocatoria registrada en el sistema!!!");

                //agregar convocatoria al Puesto que se busca
                puestoConvocatoria.agregarConvocatoria(convocatoriaNueva);

            } else
                System.out.println("ERROR, no existe un puesto con este nombre");
        } else
            System.out.println("ERROR, ya existe una convocatoria con este codigo");
    }

    //CU-05 INSCRIBIR EMPLEADO A CONVOCATORIA
    public void inscribirEmpleadoConvocatoria() {
        System.out.println("Numero legajo del empleado: ");
        int legajoEmpleado = Integer.parseInt(scanner.nextLine());

        Empleado empleado = this.buscarEmpleado(legajoEmpleado);

        if(empleado != null) {
            System.out.println("Nombre del puesto al que quiere inscribirse: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puestoAplicar = this.buscarPuestoVacante(nombrePuesto);

            if(puestoAplicar != null) {
                //ver convocatorias abiertas que puede aplicar el empleado
                puestoAplicar.mostrarConvocatoriasQueSePuedaInscribir(empleado);

                System.out.println("Codigos de convocatorias que se quiera inscribir (una por vez, 0 para fin): ");
                int codigoConvocatoria = Integer.parseInt(scanner.nextLine());

                Convocatoria convocatoria;

                while (codigoConvocatoria != 0) {
                    //buscar convocatoria con ese codigo y pedir de inscbirse a empleado
                    convocatoria = this.buscarConvocatoria(codigoConvocatoria);

                    if(convocatoria != null) {
                        convocatoria.inscribirEmpleado(empleado);
                    } else
                        System.out.println("ERROR, codigo incorrecto, no existe convocatoria con ese codigo que pueda aplicar");

                    System.out.println("Codigos de convocatorias que se quiera inscribir (una por vez, 0 para fin): ");
                    codigoConvocatoria = Integer.parseInt(scanner.nextLine());
                }
            } else
                System.out.println("ERROR, no existe un puesto con ese nombre");

        } else
            System.out.println("No existen empleado con ese legajo");
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

    private Puesto buscarPuestoVacante(String nombre) {
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
