import java.util.Scanner;

import utilidades.InputHelper;
import utilidades.Logger;

public class Ejecutora {
	private static Scanner scanner;
	private static Empresa empresa;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		empresa = new Empresa(scanner);

		Logger.divider();
		System.out.println("Bienvenid@ al sistema de administracion de cambios de puesto");
		System.out.println("Deseamos que disfrute el recorrido por la aplicacion :)");
		System.out.println("\nEzequiel Spagnoli - Horacio Balart - Nahuel Mosse - Nicolas Colli - Santiago Nunnez");
		Logger.divider();

		// Define la cantidad minima de annos para cambiar de puesto siendo jerarquico,
		// es 4 años excepto que se modifique
		PuestoJerarquico.setAnnosMinimosParaCambiar(4);

		Ejecutora.menuPrincipal();

		scanner.close();

		Logger.divider();
		System.out.println("Gracias por usar nuestro sistema :)");
		Logger.divider();
	}

	public static void menuPrincipal() {
		int opcion = 0;

		do {
			Logger.header("Menu Principal");

			System.out.println("\nSeleccione una de las opciones de la lista");
			System.out.println("\n[1] Admin");
			System.out.println("[2] General");
			System.out.println("[3] Usuario");
			System.out.println("[4] Ayuda"); // Comming soon
			System.out.println("[0] Salir");

			opcion = Ejecutora.conseguirOpcionDelMenu();

			switch (opcion) {
				case 0:
					// Salir
					break;
				case 1:
					Ejecutora.menuAdmin();
					break;
				case 2:
					Ejecutora.menuGeneral();
					break;
				case 3:
					Ejecutora.menuUsuario();
					break;
				case 4:
					Ejecutora.ayuda();
					break;
				default:
					Logger.logError("La opcion " + opcion + " no esta en la lista");
					break;
			}

		} while (opcion != 0);
	}

	public static void menuAdmin() {
		int opcion = 0;

		do {
			Logger.header("Menu Admin");

			System.out.println("\nSeleccione una de las opciones de la lista");
			System.out.println("\n[1] Agregar empleado");
			System.out.println("[2] Dar de baja empleado");
			System.out.println("[3] Agregar puesto de trabajo");
			System.out.println("[4] Borrar puesto de trabajo");
			System.out.println("[5] Crear habilidad");
			System.out.println("[6] Editar habilidad");
			System.out.println("[7] Borrar habilidad");
			System.out.println("[8] Mostrar habilidades");
			System.out.println("[9] Mostrar puestos de trabajo");
			System.out.println("[10] Mostrar empleados");
			System.out.println("[0] Volver al menu principal");

			opcion = Ejecutora.conseguirOpcionDelMenu();

			switch (opcion) {
				case 0:
					// Volver al menu principal
					break;
				case 1:
					empresa.agregarEmpleado();
					break;
				case 2:
					empresa.darDeBajaEmpleado();
					break;
				case 3:
					empresa.agregarPuesto();
					break;
				case 4:
					empresa.borrarPuesto();
					break;
				case 5:
					empresa.crearUnaHabilidad();
					break;
				case 6:
					empresa.editarHabilidad();
					break;
				case 7:
					empresa.borrarHabilidad();
					break;
				case 8:
					empresa.mostrarHabilidades();
					break;
				case 9:
					empresa.mostrarPuestos();
					break;
				case 10:
					empresa.mostrarEmpleados();
					break;
				default:
					Logger.logError("La opcion " + opcion + " no esta en la lista");
					break;
			}

		} while (opcion != 0);
	}

	public static void menuGeneral() {
		int opcion = 0;

		do {
			Logger.header("Menu General");

			System.out.println("\nSeleccione una de las opciones de la lista");
			System.out.println("\n[1] Crear una nueva convocatoria");
			System.out.println("[2] Editar convocatoria");
			System.out.println("[3] Dar de baja convocatoria");
			System.out.println("[4] Definir años necesarios en puesto jerarquico para cambiar");
			System.out.println("[5] Mostrar convocatorias abiertas");
			System.out.println("[6] Mostrar convocatorias a las que pueda aplicar empleado");
			System.out.println("[7] Inscribir empleado a convocatorias");
			System.out.println("[8] Dar de baja incripto a convocatoria");
			System.out.println("[9] Elegir postulantes en convocatoria y asignarlos al puesto vacante");
			System.out.println("[10] Mostrar todas las convocatorias");
			System.out.println("[11] Terminar convocatoria");
			System.out.println("[0] Volver al menu principal");

			opcion = Ejecutora.conseguirOpcionDelMenu();

			switch (opcion) {
				case 0:
					// Volver al menu principal
					break;
				case 1:
					// agregarConvocatoria se fusiona con agregarPuestoVacante xq dentro de
					// agregarConvocatoria, se da la posibilidad de crear al usuario un puesto nuevo
					// si no existe
					empresa.agregarConvocatoria();
					break;
				case 2:
					empresa.editarConvocatoria();
					break;
				case 3:
					empresa.darDeBajaConvocatoria();
					break;
				case 4:
					PuestoJerarquico.setAnnosMinimosParaCambiar(scanner);
					break;
				case 5:
					empresa.mostrarConvocatoriasAbiertas();
					break;
				case 6:
					empresa.mostrarConvocatoriasPuedaAplicarEmpleado();
					break;
				case 7:
					empresa.inscribirEmpleadoEnConvocatorias();
					break;
				case 8:
					empresa.darDeBajaPostulanteConvocatoria();
					break;
				case 9:
					empresa.elegirPostulantesConvocatoria();
					break;
				case 10:
					empresa.mostrarConvocatorias();
					break;
				case 11:
					empresa.terminarConvocatoria();
					break;
				default:
					Logger.logError("La opcion " + opcion + " no esta en la lista");
					break;
			}

		} while (opcion != 0);
	}

	public static void menuUsuario() {
		int opcion = 0;

		Logger.header("Menu Usuario");

		int legajo = InputHelper.scanInt(scanner, "Legajo empleado: ");

		Empleado empleado = empresa.buscarEmpleado(legajo);

		boolean intentar = true;

		while (empleado == null && intentar) {
			Logger.logError("No existe empleado con legajo " + legajo);

			intentar = InputHelper.yesOrNoInput(scanner, "Quiere intentar con otro legajo?");

			if (intentar) {
				legajo = InputHelper.scanInt(scanner, "Legajo empleado: ");

				empleado = empresa.buscarEmpleado(legajo);
			}
		}

		if (empleado != null) {
			do {
				System.out.println("\nSeleccione una de las opciones de la lista");
				System.out.println("\n[1] Ver datos de empleado");
				System.out.println("[2] Ver historial de cargos");
				System.out.println("[3] Editar informacion personal del empleado");
				System.out.println("[4] Agregar habilidad");
				System.out.println("[5] Editar años de experiencia habilidad");
				System.out.println("[6] Quitar habilidad");
				System.out.println("[0] Volver al menu principal\n");

				opcion = Ejecutora.conseguirOpcionDelMenu();

				switch (opcion) {
					case 0:
						// Volver al menu principal
						break;
					case 1:
						empleado.mostrar();
						break;
					case 2:
						empleado.mostrarCargos();
						break;
					case 3:
						empleado.editarInformacion(scanner);
						break;
					case 4:
						empresa.agregarHabilidadEmpleado(empleado);
						break;
					case 5:
						empresa.editarAnnosEmpleado(empleado);
						break;
					case 6:
						empresa.quitarHabilidadEmpleado(empleado);
						break;
					default:
						Logger.logError("La opcion " + opcion + " no esta en la lista");
						break;
				}

			} while (opcion != 0);
		}

	}

	public static void ayuda() {
		Logger.header("Funcion menus");
		System.out.println("Menu admin: gestionar los registros de la empresa (empleados, habilidades, puestos)");
		System.out.println("Menu general: gestionar las convocatorias");
		System.out.println("Menu usuario: gestionar los datos de los usuarios");
	}

	public static int conseguirOpcionDelMenu() {
		System.out.print("\n");
		int opcion = InputHelper.scanInt(scanner, "Opcion: ");

		return opcion;
	}
}
