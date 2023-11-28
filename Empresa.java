import java.util.ArrayList;
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
    
    public void editarDatosEmpGenerales() {
    	System.out.println("Ingrese el legajo del empleado:");
    	int legajo = scanner.nextInt();
    	
    	Empleado empleado = buscarEmpleado(legajo);

    	if(empleado != null) {
    		int opcion;
			do {
    			System.out.println("- Editar datos de empleado -");
    	    	System.out.println("\n- 1- Editar Nombre");
    	    	System.out.println("\n- 2- Editar Apellido");
    	    	System.out.println("\n- 3- Editar Fecha de Nacimiento");
    	    	System.out.println("\n- 4- Editar Fecha de Ingreso a la Empresa");
    	    	System.out.println("\n- 5- Mostrar Datos Empleado");
    	    	System.out.println("\n- 0- Salir");
    	    	opcion = scanner.nextInt();
    	    	
    	    	switch(opcion) {
    	    		case 1:
    	    			System.out.println("\n- Ingrese el nombre:");
    	    			String nombre = scanner.next();
    	    			empleado.setNombre(nombre);
    	    		break;
    	    		
    	    		case 2:
    	    			System.out.println("\n- Ingrese el apellido:");
    	    			String apellido = scanner.next();
    	    			empleado.setNombre(apellido);
    	    		break;
    	    		
    	    		case 3:
    	    			System.out.println("\n- Ingrese la fecha de nacimiento:");
    	    			Fecha fechaNacimiento = Fecha.nuevaFecha();
    	    			empleado.setFechaNacimiento(fechaNacimiento);
    	    		break;
    	    		
    	    		case 4:
    	    			System.out.println("\n- Ingrese la fecha ingreso a la Empresa:");
    	    			Fecha fechaIngreso = Fecha.nuevaFecha();
    	    			empleado.setFechaIngreso(fechaIngreso);
    	    		break;
    	    		
    	    		case 5:
    	    			empleado.mostrar();
    	    		break;
    	    	}
    		}while(opcion != 0);
    	}
    }

	public void agregarHabilidadEmpleado()
	{
		system.out.println("Ingrese el legajo del empleado:");
		int unLegajo = sscanne.nextInt;

		Empleado unEmpleado = this.buscarEmpleado(unLegajo);

		if (unEmpleado != null)
		{
			system.out.println("Ingrese el nombre de la habilidad:")
			String nombre = s.netxt();
			Habilidad habilidadExistente = this.buscarHabilidad(nombre);
			if(habilidadExistente != null) // si la encontre
			{
				system.out.println("Ingrese el tiempo de experiencia: ")
				int unaCantidadDeTiempo = s.nextint;
				unEmpleado.agregarHabilidadEmpleado(habilidadExistente,unaCantidadDeTiempo);
			}
			else 
			{
				this.crearUnaHabilidad();
			}
		}
		else
		{
			system.out.println("Empleado inexistente.")
		}
	}
}
