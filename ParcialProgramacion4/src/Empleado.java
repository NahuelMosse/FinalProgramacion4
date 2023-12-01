import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

import utilidades.Fecha;
import utilidades.InputHelper;
import utilidades.Logger;

public class Empleado {
    private int legajo;
    private String nombre;
    private String apellido;
    private Fecha fechaNacimiento;
    private ArrayList<Cargo>historialDeCargos;
    private Hashtable<Habilidad,Integer> habilidades;
    private Fecha fechaDeIngreso;

    public Empleado(
        int legajo,
        String nombre,
        String apellido,
        Fecha fechaNacimiento,
        Fecha fechaDeIngreso, ArrayList<Cargo> historialDeCargos,
        Hashtable<Habilidad,Integer> habilidades
    ) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.historialDeCargos = historialDeCargos;
        this.habilidades = habilidades;
        this.fechaDeIngreso = fechaDeIngreso;
    }


    public void mostrar() {
        System.out.println("legajo: " + legajo);
        System.out.println("Nombre completo: " + nombre + " " + apellido);
        System.out.println("Edad: " + this.calcularEdad());
        System.out.println("Puesto actual: ");
        this.getPuestoActual().mostrar();
        System.out.println("Habilidades: ");
        this.mostrarHabilidades();
    }

    public int calcularEdad() {
        Fecha hoy = Fecha.hoy();
        int edad = hoy.getAño() - fechaNacimiento.getAño();

        if (hoy.getMes() < fechaNacimiento.getMes()) {
            edad--;
        } else {
            if (hoy.getMes() == fechaNacimiento.getMes() && hoy.getDia() < fechaNacimiento.getDia()) {
                edad--; 
            }
        }

        return edad;
    }
  
    public boolean hasLegajo(int legajo) {
        return this.legajo == legajo;
    }

    public Puesto getPuestoActual() {
        //siempre se cumple que el ultimo agregado es el actual, entonces saco el ultimo
        return this.historialDeCargos.get(historialDeCargos.size() - 1).getPuesto();
    }

    public void mostrarHabilidades() {
        Habilidad habilidad;
        Enumeration<Habilidad> enumH = habilidades.keys();
        while (enumH.hasMoreElements()) {
            habilidad = enumH.nextElement();

            habilidad.mostrar();

            System.out.println("años de experiencia: " + habilidades.get(habilidad));
        }
    }

















































    
    public void editarInformacion(Scanner scanner) {
        int opcion;

        do {
            Logger.header("Menu para editar informacion personal");
            System.out.println("1- Editar nombre");
            System.out.println("2- Editar apellido");
            System.out.println("3- Editar fecha de nacimiento");
            System.out.println("4- Ver informacion personal");
            System.out.println("0- Volver al menu del Usuario");

            opcion = InputHelper.scanInt(scanner, "Opcion: ");

            switch (opcion) {
                case 0:
                    break;
                case 1:
                    System.out.println("Nombre anterior: " + nombre);
                    System.out.print("Nuevo nombre: ");
                    nombre = scanner.nextLine();
                    Logger.logSuccess("Nombre actualizado");
                    break;

                case 2:
                    System.out.println("Apellido anterior: " + apellido);
                    System.out.print("Nuevo apellido: ");
                    apellido = scanner.nextLine();
                    Logger.logSuccess("Apellido actualizado");
                    break;

                case 3:
                    System.out.println("Fecha de nacimiento anterior: " + fechaNacimiento.getDia() + "/" + fechaNacimiento.getMes() + "/" + fechaNacimiento.getAño());
                    System.out.println("Nueva fecha: ");
                    fechaNacimiento = Fecha.nuevaFecha();
                    Logger.logSuccess("Fecha de nacimiento actualizada");
                    break;

                case 4:
                    System.out.println("\nInformacion empleado");
                    this.mostrar();
                    break;

                default:
                    break;
            }

        } while (opcion != 0);
    }

}
