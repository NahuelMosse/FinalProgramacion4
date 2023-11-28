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
                    convocatoria.mostrarSinPostulantesAsignados();
                }
            }
        }
        
    }


  
  
    //CU- Generar nueva convocatoria
    public void agregarConvocatoria() {
        Logger.header("Formulario generar convocatoria");

        int codigoConvocatoria = InputHelper.scanInt(scanner, "Codigo convocatoria: ");

        Convocatoria convocatoriaRepetida = this.buscarConvocatoria(codigoConvocatoria);

        if (convocatoriaRepetida == null) {
            System.out.print("Nombre puesto: ");
            String nombrePuesto = scanner.nextLine();

            Puesto puestoConvocatoria = this.buscarPuesto(nombrePuesto);

            if (puestoConvocatoria == null) {
                //le doy la opcion de crearlo
                boolean quiereCrearlo = InputHelper.yesOrNoInput(scanner, "No existe puesto con ese nombre, quiere crearlo?");
                
                if (quiereCrearlo) {
                    puestoConvocatoria = this.agregarPuesto(nombrePuesto); 
                }
            }

            if (puestoConvocatoria != null) {
                System.out.println("Fecha a realizar convocatoria: ");
                Fecha fechaConvocatoria = Fecha.nuevaFecha();

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

            } else {
                //si no encuentra el puesto y elije no crearlo, se cancela la generacion de la convocatoria
                Logger.logError("No es posible generar la convocatoria sin un puesto, intente nuevamente");
            }
        } else {
            Logger.logError("Ya existe una convocatoria con el codigo " + codigoConvocatoria);
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

}
