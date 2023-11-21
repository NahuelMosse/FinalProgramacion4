import java.util.Scanner;

public class Ejecutora {
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        Empresa empresa = new Empresa();

        int opcion = 0;
        do{
            System.out.println(
			"\n 1-"+
			"\n 2-"+
			"\n 3-"+
			"\n 4-"+
			"\n 5-"+
			"\n 6-"+
			"\n 7-");
			System.out.println("opcion: ");
			opcion = scanner.nextInt();

            switch (opcion) {
            case 0:
                break;
            case 1:
                
                break;

            case 2:
                break;
            
            case 3:
                break;

            case 4:
                break;

            case 5:
                break;
                
            case 6:
                break;

            case 7:
                break;

            default:
                System.out.println("opcion no valida");
                break;
            }

        } while(opcion!=0);
    }
}
