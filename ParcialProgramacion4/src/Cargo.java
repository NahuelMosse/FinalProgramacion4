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
    
    public boolean esActual() {
        return fechaFin == null; //si es null, es el cargo actual
    }

    public Puesto getPuesto() {
        return puesto;
    }
}
