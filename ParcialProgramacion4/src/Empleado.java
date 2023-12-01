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
    private ArrayList<Cargo> historialDeCargos;
    private Hashtable<Habilidad, Integer> habilidades;
    private Fecha fechaDeIngreso;

    public Empleado(
        int legajo,
        String nombre,
        String apellido,
        Fecha fechaNacimiento,
        Fecha fechaDeIngreso, ArrayList<Cargo> historialDeCargos,
        Hashtable<Habilidad, Integer> habilidades
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
        System.out.println("Legajo: " + legajo);
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

    public Puesto getPuestoActual() {
        // siempre se cumple que el ultimo agregado es el actual, entonces saco el
        // ultimo
        return this.historialDeCargos.get(historialDeCargos.size() - 1).getPuesto();
    }

    public void agregarHabilidad(Scanner scanner, Habilidad habilidad) {
        if (habilidades.containsKey(habilidad)) {
            Logger.logError("El empleado " + this.nombre + " ya tiene registrada esta habilidad");
        } else {
            int annosExperiencia = InputHelper.scanInt(scanner, "Ingrese el tiempo de experiencia: ");

            habilidades.put(habilidad, annosExperiencia);

            Logger.logSuccess("Habilidad agregada al empleado con exito");
        }
    }

    public void eliminarHabilidad(Habilidad habilidad) {
        if (habilidades.remove(habilidad) == null) {
            Logger.logError("El empleado " + this.nombre + " no tiene la habilidad " + habilidad.getNombre());
        } else {
            Logger.logSuccess("Habilidad eliminada con exito");
        }
    }

    public void modificarAnnos(Scanner scanner, Habilidad habilidad) {
        if (!habilidades.containsKey(habilidad)) {
            Logger.logError("El empleado " + this.nombre + " no tiene la habilidad ");
        } else {
            int annosExperiencia = InputHelper.scanInt(scanner, "Ingrese el tiempo de experiencia: ");

            habilidades.put(habilidad, annosExperiencia);

            Logger.logSuccess("Experiencia del empleado actualizada con exito");
        }
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

    public boolean hasLegajo(int legajo) {
        return this.legajo == legajo;
    }

    public void eliminarCargos() {
        for (Cargo cargo : historialDeCargos) {
            historialDeCargos.remove(cargo);
        }
    }

    public void editarInformacion(Scanner scanner) {
        int opcion;

        do {
            Logger.header("Menu para editar informacion personal");
            System.out.println("[1] Editar nombre");
            System.out.println("[2] Editar apellido");
            System.out.println("[3] Editar fecha de nacimiento");
            System.out.println("[4] Ver informacion personal");
            System.out.println("[0] Volver al menu del Usuario");

            opcion = InputHelper.scanInt(scanner, "Opcion: ");

            switch (opcion) {
                case 0:
                    break;
                case 1:
                    this.editarNombre(scanner);
                    break;

                case 2:
                    this.editarApellido(scanner);
                    break;

                case 3:
                    this.editarFechaDeNacimiento(scanner);
                    break;

                case 4:
                    this.mostrar();
                    break;

                default:
                    break;
            }

        } while (opcion != 0);
    }

    private void editarNombre(Scanner scanner) {
        System.out.println("Nombre anterior: " + nombre);
        System.out.print("Nuevo nombre: ");
        nombre = scanner.nextLine();
        Logger.logSuccess("Nombre actualizado");
    }

    private void editarApellido(Scanner scanner) {
        System.out.println("Apellido anterior: " + apellido);
        System.out.print("Nuevo apellido: ");
        apellido = scanner.nextLine();
        Logger.logSuccess("Apellido actualizado");
    }

    private void editarFechaDeNacimiento(Scanner scanner) {
        System.out.println("Fecha de nacimiento anterior: " + fechaNacimiento.getDia() + "/" + fechaNacimiento.getMes()
                + "/" + fechaNacimiento.getAño());
        System.out.println("Nueva fecha: ");
        fechaNacimiento = Fecha.nuevaFecha();
        Logger.logSuccess("Fecha de nacimiento actualizada");
    }

    public boolean puedeAplicar(Hashtable<Habilidad, Integer> requisitos) {
        // si esta en un puesto jerarquico, determino si cumple con annos minimos en ese
        // puesto
        // comparo requisitos (lo q pide puesto) con habilidades (lo q tiene el
        // empleado)
        Cargo cargoActual = this.getCargoActual();

        return cargoActual.jerarquicoCumpleAnnosMinimos() && this.cumpleRequisitos(requisitos);
    }

    public boolean cumpleRequisitos(Hashtable<Habilidad, Integer> requisitos) {
        // recorro la hashtable de requisitos y me fijo si tiene la habilidad el
        // empleado y los años minimos
        boolean cumpleReq = true; // si no tiene algun requisito o años necesarios, no sigue recorriendo
        Habilidad requisito;

        Enumeration<Habilidad> enumReq = requisitos.keys();
        while (enumReq.hasMoreElements() && cumpleReq) {
            requisito = enumReq.nextElement();

            if (habilidades.containsKey(requisito)) {
                // años de la persona >= años necesarios convocatoria
                cumpleReq = habilidades.get(requisito) >= requisitos.get(requisito);

            } else {
                cumpleReq = false; // no tiene ese requisito, ya no puede aplicar
            }
        }

        return cumpleReq;
    }

    public int getAnnosEnEmpresa() {
        // calcular años y luego determino si tengo que restarle porque no llego mes o
        // dia para pasar de año
        Fecha hoy = Fecha.hoy();
        int annosEnEmpresa = hoy.getAño() - fechaDeIngreso.getAño();

        // Determino si tengo que restarle un año de mas
        if (hoy.getMes() < fechaDeIngreso.getMes()) {
            annosEnEmpresa--; // todavia no paso el mes de ingreso para contabilizar el año ej fecha hoy
                              // 2023/11/29 y fecha inicio 2022/12/20
        } else {
            if (hoy.getMes() == fechaDeIngreso.getMes() && hoy.getDia() < fechaDeIngreso.getDia()) {
                annosEnEmpresa--; // todaia no paso el dia del ingreso en el mismo mes para contabilizar el año ej
                                  // fecha hoy 2023/11/29 y fecha inicio 2022/11/30
            }
        }

        return annosEnEmpresa;
    }

    public boolean tieneHabilidad(Habilidad habilidadBuscada) {
        return habilidades.containsKey(habilidadBuscada);
    }

    public void tryEliminarHabilidad(Habilidad habilidadBuscada) {
        habilidades.remove(habilidadBuscada);
    }

    public int getLegajo() {
        return legajo;
    }

    public void nuevoCargo(Puesto nuevoPuesto) {
        Cargo cargoActual = this.getCargoActual();
        cargoActual.cerrarCargo();

        // crear nuevo cargo
        Cargo cargoNuevo = new Cargo(Fecha.hoy(), null, nuevoPuesto); // null xq no finalizo, cuando finaliza se
                                                                      // establece fechaFin
        historialDeCargos.add(cargoNuevo);
    }

    public Cargo getCargoActual() {
        return this.historialDeCargos.get(historialDeCargos.size() - 1); // el ultimo cargo agregado
    }

    public void mostrarCargos() {
        for (Cargo unCargo : historialDeCargos) {
            unCargo.mostrarCargo();
        }

    }

}
