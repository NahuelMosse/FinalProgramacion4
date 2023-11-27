import java.util.ArrayList;
import java.util.Scanner;

import utilidades.Logger;

public class Empresa {
	Scanner scanner = new Scanner(System.in);
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

    public void crearUnaHabilidad() {
    	Logger.header("Formulario para crear una habilidad");

    	System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

		Habilidad habilidadExistente = this.buscarHabilidad(nombre);
		
		if (habilidadExistente != null) {
			Logger.logError("Ya existe una habilidad con este nombre");
			
			System.out.print("Desea ingresar otro nombre [Si/No]: ");
	        String continuar = scanner.nextLine();
	        
	        if (continuar.equalsIgnoreCase("si")) {
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
    
    private Habilidad buscarHabilidad(String nombre) {
    	int i = 0;
    	
    	while (i < habilidades.size() && !habilidades.get(i).hasName(nombre)) {
    		i++;
    	}
    	
    	if (i < habilidades.size()) {
    		return habilidades.get(i);
    	}
    	return null;
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
    
    
    
    private Puesto buscarPuesto(String nombre) {
        int i = 0;

        while(i<puestos.size() && !puestos.get(i).hasPuesto(nombre))
            i++;
        
        if(i<puestos.size())
            return puestos.get(i);
        else
            return null;
    }
    
}
