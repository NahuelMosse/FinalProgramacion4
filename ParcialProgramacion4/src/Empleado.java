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

    public boolean hasEmpleado(int legajo) {
        return this.legajo == legajo;
    }

    public Puesto getPuestoActual() {
        /* 
        //el puesto actual es el que tiene fechaFin==null porque no ha terminado 
        //le pregunto a cada cargo si es el actual, si es me lo devuelve, sino me devuelve null
        int i = 0;
        while(!this.historialDeCargos.get(i).esActual()) //no pongo condicion de i<size porque se que alguno de los puestos es actual 
            i++;

        return this.historialDeCargos.get(i).getPuesto();
        */

        //siempre se cumple que el ultimo agregado es el actual, entonces saco el ultimo
        return this.historialDeCargos.get(historialDeCargos.size() - 1).getPuesto();
    }

    //Utilizo el cargo para conocer las fechas para saber si el empleado cumple los requisitos de cantidad de annos en el cargo
    public Cargo getCargoActual() {
        int i = 0;
        while(!this.historialDeCargos.get(i).esActual()) 
            i++;

        return this.historialDeCargos.get(i);
    }

    public int getAnnosEnEmpresa() {
        Fecha hoy = Fecha.hoy();
        int annosEnEmpresa = hoy.getAño() - fechaDeIngreso.getAño();

        if (hoy.getMes() < fechaDeIngreso.getMes()) {
            annosEnEmpresa--;
        } else
            if (hoy.getMes() == fechaDeIngreso.getMes() && hoy.getDia() < fechaDeIngreso.getMes()) {
                annosEnEmpresa--;
            }
        
        return annosEnEmpresa;
    }

    public boolean cumpleRequisitos(Hashtable<Habilidad, Integer> requisitos) {
        //recorrer hashtable requisitos y por cada una fijarse si tiene cada habilidad requerida con los años de experiencia
        boolean cumpleReq = true;
        Habilidad requisito;
        Enumeration<Habilidad> enumReq = requisitos.keys();
        while (enumReq.hasMoreElements() && cumpleReq) { //cuando detecta que una habilidad ya no se cumple, termina
            requisito = enumReq.nextElement();

            if (habilidades.containsKey(requisito)) {
                //empleado contiene habilidad / requisito, ahora verificar si tienen los años de experiencia necesarios
                cumpleReq = habilidades.get(requisito) >= requisitos.get(requisito); 
                //años de experiencia empleado >= años de experiencia requeridos 
            } else {
                cumpleReq = false; //empleado no tiene esa habilidad, ya no cumple con la condicion
            }
        }
        //si salio porque recorrio todos los requisitos y todos cumplian con los años de exp requeridos retorna true
        //si salio porque cumpleReq fue false retorna false
        return cumpleReq; 
    }

    public void mostrar() {
        System.out.println("legajo: " + legajo);
        System.out.println("Nombre completo: " + nombre + " " + apellido);
        System.out.println("Puesto actual: ");
        this.getPuestoActual().mostrar();
        System.out.println("Habilidades: ");
        
    }

    public void mostrarHabilidades() {
        Habilidad habilidad;
        Enumeration<Habilidad> enumH = habilidades.keys();
        while (enumH.hasMoreElements()) {
            habilidad = enumH.nextElement();

            habilidad.mostrar();

            System.out.println("años de experiencia: "+habilidades.get(habilidad));
        }
    }

    public void nuevoCargo(Puesto nuevoPuesto) {
        Cargo cargoNuevo = new Cargo(Fecha.hoy(),null,nuevoPuesto);
        Cargo cargoAnterior = this.getCargoActual();
        cargoAnterior.cerrarCargo();
        historialDeCargos.add(cargoNuevo);
    }
}
