public class PuestoJerarquico extends Puesto{
    private static int annosMinimosParaCambiar;

    public PuestoJerarquico(String nombre, double sueldo) {
        super(nombre, sueldo);
    }

    public boolean esJerarquico() {
        return true;
    }

    public void mostrarme() {
        System.out.println("puesto jerarquico");
        super.mostrarme();
    }
}
