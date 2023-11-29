import java.util.Hashtable;

import utilidades.Fecha;

public class ConvocatoriaJerarquico extends Convocatoria {
    private int annosMinimosEnEmpresa;

    public ConvocatoriaJerarquico(
        int codigo,
        Puesto puesto,
        Fecha fecha,
        int cantEmpleadosRequeridos,
        int annosMinimosEnEmpresa,
        Hashtable<Habilidad,Integer> requisitos
    ) {
        super(codigo, puesto, fecha, cantEmpleadosRequeridos, requisitos);
        this.annosMinimosEnEmpresa = annosMinimosEnEmpresa;
    }

    public boolean puedeAplicar(Empleado empleadoAplicar) {
        boolean cumpleAnnosMin = empleadoAplicar.getAnnosEnEmpresa() >= annosMinimosEnEmpresa;
        return super.puedeAplicar(empleadoAplicar) && cumpleAnnosMin;
   }

   public void mostrar() {
        super.mostrar();

        System.out.println("Años minimos en la empresa para aplicar: " + annosMinimosEnEmpresa);
   }
}
