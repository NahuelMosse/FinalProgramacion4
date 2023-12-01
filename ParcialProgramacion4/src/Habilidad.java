import java.util.Scanner;

import utilidades.Logger;

public class Habilidad {
    private String nombre;
    private String descripcion;

    public Habilidad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public boolean hasNombre(String nombre) {
    	// Buscamos sin espacion para encontrar mas similitudes
    	String nombreHabilidadSinEspacios = this.nombre.replace(" ", "");
    	String nombreABuscarSinEspacios = nombre.replace(" ", ""); 
    	
        return nombreHabilidadSinEspacios.equalsIgnoreCase(nombreABuscarSinEspacios);
    }
	
    public void mostrar() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Descripcion: ");
        System.out.println(descripcion);
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public void editarNombre(Scanner scanner) {
        System.out.println("Nombre antiguo: " + nombre);
        System.out.print("Nuevo nombre: ");
        nombre = scanner.nextLine();
        Logger.logSuccess("Nombre editado correctamente");
    }

    public void editarDescripcion(Scanner scanner) {
        System.out.println("Descripcion antigua: ");
        System.out.println(descripcion);
        System.out.print("Nueva descripcion: ");
        descripcion = scanner.nextLine();
        Logger.logSuccess("Descripcion editada correctamente");
    }
}
