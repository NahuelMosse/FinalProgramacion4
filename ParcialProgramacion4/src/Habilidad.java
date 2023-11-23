public class Habilidad {
    private int codigo;
    private String nombre;
    private String descripcion;

    public Habilidad(int codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public boolean hasHabilidad(int codigo) {
        return this.codigo == codigo;
    }

    public boolean hasHabilidad(String nombre) {
        return this.nombre.equalsIgnoreCase(nombre);
    }
}
