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

    public boolean jerarquicoCumpleAnnosMinimos() {
        int annosEnCargo = this.getAnnosEnCargo();
        return puesto.jerarquicoCumpleAnnosMinimos(annosEnCargo);
    }

    public int getAnnosEnCargo(){
       //calcular años y luego determino si tengo que restarle porque no llego mes o dia para pasar de año
       Fecha hoy = Fecha.hoy();
       int annosEnCargo = hoy.getAño() - fechaInicio.getAño();

       //Determino si tengo que restarle un año de mas
       if (hoy.getMes() < fechaInicio.getMes()) {
            annosEnCargo--; //todavia no paso el mes de inicio para contabilizar el año ej fecha hoy 2023/11/29 y fecha inicio 2022/12/20
       } else {
            if (hoy.getMes() == fechaInicio.getMes() && hoy.getDia() < fechaInicio.getDia()) {
                annosEnCargo--; //todaia no paso el dia del inicio en el mismo mes para contabilizar el año ej fecha hoy 2023/11/29 y fecha inicio 2022/11/30
            }
       }

       return annosEnCargo;
    }

    
    public Fecha getFechaInicio() {
    	return this.fechaInicio;
    }
    
    public void mostrarCargo() {
        System.out.println("Puesto : ");
        this.puesto.mostrar();
    	System.out.println("Fecha de Inicio : |" + this.getFechaInicio());
    	if(fechaFin != null) {
    		System.out.println("Fecha de Fin    : |" + this.getFechaFin());
    	}
    }
}
