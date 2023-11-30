import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

import utilidades.Fecha;
import utilidades.InputHelper;
import utilidades.Logger;

public class Empleado {
    private int legajo;
    private String nombre;
    private String apellido;
    private Fecha fechaNacimiento;
    private ArrayList<Cargo>historialDeCargos;
    private Hashtable<Habilidad,Integer> habilidades;
    private Fecha fechaDeIngreso;

    public Empleado(
        int legajo,
        String nombre,
        String apellido,
        Fecha fechaNacimiento,
        Fecha fechaDeIngreso, ArrayList<Cargo> historialDeCargos,
        Hashtable<Habilidad,Integer> habilidades
    ) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.historialDeCargos = historialDeCargos;
        this.habilidades = habilidades;
        this.fechaDeIngreso = fechaDeIngreso;
    }
    
    public void mostrar() {
        System.out.println("legajo: " + legajo);
        System.out.println("Nombre completo: " + nombre + " " + apellido);
        System.out.println("Puesto actual: ");
        this.getPuestoActual().mostrar();
        System.out.println("Habilidades: ");
        this.mostrarHabilidades();
    }
  
    public Puesto getPuestoActual() {
        //siempre se cumple que el ultimo agregado es el actual, entonces saco el ultimo
        return this.historialDeCargos.get(historialDeCargos.size() - 1).getPuesto();
    }
  
    public void agregarHabilidad(Scanner scanner, Habilidad habilidad) {
        if(habilidades.containsKey(habilidad)) { 
            Logger.logError("El empleado "  + this.nombre + " ya tiene registrada esta habilidad");
        } else {
            int annosExperiencia = InputHelper.scanInt(scanner, "Ingrese el tiempo de experiencia: ");

            habilidades.put(habilidad, annosExperiencia);

            Logger.logSuccess("Habilidad agregada al empleado con exito");
        }
    }
   
    public void eliminarHabilidad(Habilidad habilidad) {
		if (habilidades.remove(habilidad) == null) {
		    Logger.logError("El empleado " + this.nombre + " no tiene la habilidad " + habilidad.getNombre());
		} else  {
            Logger.logSuccess("Habilidad eliminada con exito");
        }
	  }
    
    public void modificarAnnos(Scanner scanner, Habilidad habilidad) {
        if (!habilidades.containsKey(habilidad)) {
            Logger.logError("El empleado " + this.nombre + " no tiene la habilidad ");
        } else {
            int annosExperiencia = InputHelper.scanInt(scanner, "Ingrese el tiempo de experiencia: ");

            habilidades.put(habilidad, annosExperiencia);

            Logger.logSuccess("Experiencia del empleado actualizada con exito");
        }
    }

    public void mostrarHabilidades() {
        Habilidad habilidad;
        Enumeration<Habilidad> enumH = habilidades.keys();
        while (enumH.hasMoreElements()) {
            habilidad = enumH.nextElement();

            habilidad.mostrar();

            System.out.println("años de experiencia: "+habilidades.get(habilidad));
        }
    }
    
    public boolean hasLegajo(int legajo) {
        return this.legajo == legajo;
    }


    public void eliminarCargos() {
        for (Cargo cargo: historialDeCargos) {
            historialDeCargos.remove(cargo);
        }
    }


    public boolean tieneHabilidad(Habilidad habilidadBuscada) {
        return habilidades.containsKey(habilidadBuscada);
    }

    public void tryEliminarHabilidad(Habilidad habilidadBuscada) {
        habilidades.remove(habilidadBuscada);
    }

}
