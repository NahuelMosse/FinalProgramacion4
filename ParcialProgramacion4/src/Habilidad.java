public class Habilidad {
    private String nombre;
    private String descripcion;

    public Habilidad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public boolean hasHabilidad(String nombre) {
        return this.nombre.equalsIgnoreCase(nombre);
    }
}
