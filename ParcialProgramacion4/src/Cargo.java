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
        return this.fechaFin == null; //si es null, es el cargo actual
    }

    public Puesto getPuesto() {
        return this.puesto;
    }

    public boolean esJerarquico(){
       return this.puesto.esJerarquico();
    }

    public int tiempoEnCargo() {
        int cantDias=0;
        if(this.fechaFin == null){
            //En el cargo actual la fecha fin es null
            cantDias=Fecha.hoy().compareTo(this.fechaInicio);
        }
        else{
            cantDias=this.fechaFin.compareTo(this.fechaInicio);
        }
        return cantDias;
    }

    public void cerrarCargo() {
        fechaFin = Fecha.hoy();
    }
}
