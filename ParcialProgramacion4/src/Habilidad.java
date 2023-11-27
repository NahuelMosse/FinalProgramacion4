public class Habilidad {
    private String nombre;
    private String descripcion;

    public Habilidad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public boolean hasName(String nombre) {
    	// Buscamos sin espacion para encontrar mas similitudes
    	String nombreHabilidadSinEspacios = this.nombre.replace(" ", "");
    	String nombreABuscarSinEspacios = nombre.replace(" ", ""); 
    	
        return nombreHabilidadSinEspacios.equalsIgnoreCase(nombreABuscarSinEspacios);
    }
}
