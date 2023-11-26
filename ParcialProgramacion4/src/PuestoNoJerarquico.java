public class PuestoNoJerarquico extends Puesto{
    
    public PuestoNoJerarquico(String nombre, double sueldo) {
        super(nombre,sueldo);
    }

    public void mostrar() {
        System.out.println("puesto NO jerarquico");
        super.mostrar();
    }
}
