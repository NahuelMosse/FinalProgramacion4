import utilidades.Fecha;

public class ConvocatoriaJerarquico extends Convocatoria{
    private int annosMinimosEnEmpresa;

    public ConvocatoriaJerarquico(int codigo, Puesto puesto, Fecha fecha, int cantEmpleadosRequeridos, int annosMinimosEnEmpresa) {
        super(codigo, puesto, fecha, cantEmpleadosRequeridos);
        this.annosMinimosEnEmpresa = annosMinimosEnEmpresa;
    }
}
