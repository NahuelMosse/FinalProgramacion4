import utilidades.Fecha;

public class Cargo {
    private Fecha fechaInicio;
    private Fecha fechaFin;
    private Puesto puesto;

    public Cargo(Fecha fechaInicio, Fecha fechaFin, Puesto puesto) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.puesto = puesto;
    }
    
    public Puesto getPuesto() {
        return this.puesto;
    }

    public Fecha getFechaFin() {
    	return this.fechaFin;
    }
    
    public Fecha getFechaInicio()
    {
    	return this.fechaInicio;
    }
    
    public void mostrarCargo()
    {
    	if(fechaFin==null)
    	{
    		System.out.println("Puesto : |" + this.getPuesto());
    		System.out.println("Fecha de Inicio : |" + this.getFechaInicio());
    	}
    	else
    	{
    		System.out.println("Puesto : |" + this.getPuesto());
    		System.out.println("Fecha de Inicio : |" + this.getFechaInicio());
    		System.out.println("Fecha de Fin    : |" + this.getFechaFin());
    	}
    }
}
