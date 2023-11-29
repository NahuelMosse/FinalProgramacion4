import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import utilidades.Fecha;

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
        System.out.println("Puesto actual: ");
        this.getPuestoActual().mostrar();
        System.out.println("Habilidades: ");
        this.mostrarHabilidades();
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


    public boolean puedeAplicar(Hashtable<Habilidad,Integer> requisitos) {
        //si esta en un puesto jerarquico, determino si cumple con annos minimos en ese puesto
        Cargo cargoActual = this.getCargoActual();
        boolean jerarquicoCumpleAnnosMinimos = cargoActual.jerarquicoCumpleAnnosMinimos();

        //comparo requisitos con habilidades
        boolean cumpleRequisitos = this.cumpleRequisitos(requisitos);

        return jerarquicoCumpleAnnosMinimos && cumpleRequisitos;
    }

    public Cargo getCargoActual() {
        return this.historialDeCargos.get(historialDeCargos.size() - 1);
    }

    public boolean cumpleRequisitos(Hashtable<Habilidad,Integer> requisitos) {
        //recorro la hashtable de requisitos y me fijo si tiene la habilidad el empleado y los años minimos
        boolean cumpleReq = true; //si no tiene algun requisito o años necesarios, no sigue recorriendo
        Habilidad requisito;

        Enumeration<Habilidad> enumReq = requisitos.keys();
        while (enumReq.hasMoreElements() && cumpleReq) {
            requisito = enumReq.nextElement();

            if (habilidades.containsKey(requisito)) {
                            //   años de la persona    >= años necesarios convocatoria
                cumpleReq = habilidades.get(requisito) >= requisitos.get(requisito); 

            } else {
                cumpleReq = false; //no tiene ese requisito, ya no puede aplicar
            }
        }

        return cumpleReq;
    }


    public int getAnnosEnEmpresa() {
        //calcular años y luego determino si tengo que restarle porque no llego mes o dia para pasar de año
        Fecha hoy = Fecha.hoy();
        int annosEnEmpresa = hoy.getAño() - fechaDeIngreso.getAño();
 
        //Determino si tengo que restarle un año de mas
        if (hoy.getMes() < fechaDeIngreso.getMes()) {
             annosEnEmpresa--; //todavia no paso el mes de ingreso para contabilizar el año ej fecha hoy 2023/11/29 y fecha inicio 2022/12/20
        } else {
             if (hoy.getMes() == fechaDeIngreso.getMes() && hoy.getDia() < fechaDeIngreso.getDia()) {
                 annosEnEmpresa--; //todaia no paso el dia del ingreso en el mismo mes para contabilizar el año ej fecha hoy 2023/11/29 y fecha inicio 2022/11/30
             }
        }
 
        return annosEnEmpresa;
    }

    
}
