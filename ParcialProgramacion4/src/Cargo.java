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
    	return fechaFin;
    }
}
