public class PuestoNoJerarquico extends Puesto{
    
    public PuestoNoJerarquico(String nombre, double sueldo) {
        super(nombre,sueldo);
    }

    public boolean esJerarquico() {
        return false;
    }

    public void mostrarme() {
        System.out.println("puesto NO jerarquico");
        super.mostrarme();
    }
}
