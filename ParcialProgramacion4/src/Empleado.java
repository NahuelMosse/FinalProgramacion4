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
    
  //SI MAL NO RECUERDO ESTO RECIBE LA CLASE Y UN ENTERO QUE LO EMPAQUETA EN UN INTEGER/.
    public void agregarHabilidad(Habilidad habilidad,int annosExperiencia)
    {
        if(habilidades.containsKey(habilidad))  // SI CONTIENE LA POS
        	Logger.logError("Ya existe la habilidad no corresponde a este CU");
		habilidades.put(habilidad,annosExperiencia);
    }


    //METODO PARA ELIMINAR HABILIDAD EXISTENTE DE LA HASH DEL EMPLEADO
    public void eliminarHabilidad(Habilidad habilidad)
    {
		Enumeration<Habilidad> enumH=habilidades.keys(); // me devolvia todo esto
		while(enumH.hasMoreElements() && ((Hashtable<Habilidad, Integer>) enumH).containsKey(habilidad) ) // mientras haya elemento 
		{
			habilidad=enumH.nextElement(); //sigo
		}
		
		// sin importarme total me trae null si la encontro o no la limpio.
		if (habilidad == null)
		{
		    Logger.logError("El empleado " + this.nombre + " no tiene la habilidad " + habilidad.hasNombre(nombre));
		} 
		else
		{
			habilidades.remove(habilidad);
		}
	}
    
    
    public void modificarAnios(Habilidad habilidadExistente,int unaCantidadDeTiempo)
    {
        Enumeration<Habilidad> enumH=habilidades.keys(); 
        while(enumH.hasMoreElements())
        {
            if(habilidades.containsKey(habilidadExistente))
            {
                unaCantidadDeTiempo+=habilidades.get(habilidadExistente); 
		        habilidades.put(habilidadExistente,unaCantidadDeTiempo);
            }
        }
    }

	
    
}
