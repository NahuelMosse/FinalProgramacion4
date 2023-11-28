import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import utilidades.Fecha;

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
    public void agregarHabilidadEmpleado(Habilidad habilidadExistente,int unaCantidadDeTiempo)
    {
        if(habilidades.containsKey(habilidadExistente))  // SI CONTIENE LA POS
			unaCantidadDeTiempo+=habilidades.get(habilidadExistente); // AL ENC LE PONGO LOS ANIOS???
		habilidades.put(habilidadExistente,unaCantidadDeTiempo);
    }


    //METODO PARA ELIMINAR HABILIDAD EXISTENTE DE LA HASH DEL EMPLEADO
    public void eliminarHabilidad(Habilidad habilidadExistente)
    {
		Enumeration<Habilidad> enumH=habilidades.keys(); // me devolvia todo esto
		while(enumH.hasMoreElements() && ((Hashtable<Habilidad, Integer>) enumH).containsKey(habilidadExistente) ) // mientras haya elemento 
		{
			habilidadExistente=enumH.nextElement(); //sigo
		}
		habilidades.remove(habilidadExistente); // sin importarme total me trae null si la encontro o no la limpio.
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
