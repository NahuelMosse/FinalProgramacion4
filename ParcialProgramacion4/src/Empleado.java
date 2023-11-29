import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import utilidades.Fecha;
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

    public boolean hasLegajo(int legajo) {
        return this.legajo == legajo;
    }

    public Puesto getPuestoActual() {
        //siempre se cumple que el ultimo agregado es el actual, entonces saco el ultimo
        return this.historialDeCargos.get(historialDeCargos.size() - 1).getPuesto();
    }
    
  
    public void agregarHabilidad(Habilidad habilidad,int annosExperiencia)
    {
    	  if(habilidades.containsKey(habilidad))
    	  { 
          	Logger.logError("Ya existe la habilidad no corresponde a este CU");
          }
		habilidades.put(habilidad,annosExperiencia);
    }


   
    public void eliminarHabilidad(Habilidad habilidad)
    {
    	
		if (habilidades.remove(habilidad) != null)
		{
		    Logger.logError("El empleado " + this.nombre + " no tiene la habilidad " + habilidad.hasNombre(nombre));
		} 
		else //para mi no hace falta este else nawe
		{
			habilidades.remove(habilidad);
		}
	}
    
    
    public void modificarAnios(Habilidad habilidad,int annosExperiencia)
    {
        if (!habilidades.containsKey(habilidad)) {
            Logger.logError("El empleado " + this.nombre + " no tiene la habilidad " + habilidad.getNombre());
        } else {
            habilidades.put(habilidad, annosExperiencia);
        }
    }

	
    
}
