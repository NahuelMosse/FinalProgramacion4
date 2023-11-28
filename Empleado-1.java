import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import utilidades.Fecha;
import java.util.scanner;

public class Empleado {

    Scanner scanner = new Scanner(System.in);

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
    

    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }
    
    public void setApellido(String apellido) {
    	this.apellido = apellido;
    }
    
    public void setFechaNacimiento(Fecha fechaNacimiento) {
    	this.fechaNacimiento = fechaNacimiento;
    }
    
    public void setFechaIngreso(Fecha fechaIngreso) {
    	this.fechaDeIngreso = fechaIngreso;
    }
    

    //SI MAL NO RECUERDO ESTO RECIBE LA CLASE Y UN ENTERO QUE LO EMPAQUETA EN UN INTEGER/.
    public void agregarHabilidadEmpleado(Habilidad habilidadExistente,int unaCantidadDeTiempo)
    {
        if(habilidades.containsKey(habilidadExistente))  // SI CONTIENE LA POS
			aniosExp+=habilidades.get(habilidadExistente); // AL ENC LE PONGO LOS ANIOS???
		habilidades.put(habilidadExistente,aniosExp);
    }


    //METODO PARA ELIMINAR HABILIDAD EXISTENTE DE LA HASH DEL EMPLEADO
    public void eliminarHabilidad(habilidadExistente)
    {
		Enumeration<Habilidad> enumH=habilidades.keys(); // me devolvia todo esto
		while(enumH.hasMoreElements() && enumH.containsKey(habilidadExistente) ) // mientras haya elementos y sea  la clave 
		{
			habilidadExistente=enumH.nextElement(); //sigo
		}
		habilidades.remove(habilidadExistente); // sin importarme total me trae null si la encontro o no la limpio.
	}

    
    unEmpleado.modificarAnios(habilidadExistente,unaCantidadDeTiempo)
    {
        Enumeration<Habilidad> enumH=habilidades.keys(); 
        while(enumH.hasMoreElements())
        {
            if(habilidades.containsKey(habilidadExistente)
            {
                unaCantidadDeTiempo+=habilidades.get(habilidadExistente); /
		        habilidades.put(habilidadExistente,unaCantidadDeTiempo);
            }
        }
    }
    
}
