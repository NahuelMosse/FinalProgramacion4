public class PuestoNoJerarquico extends Puesto{
    
    public PuestoNoJerarquico(String nombre, double sueldo) {
        super(nombre,sueldo);
    }

    public void mostrarme() {
        System.out.println("puesto NO jerarquico");
        super.mostrarme();
    }
}
