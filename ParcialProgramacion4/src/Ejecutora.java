import java.util.Scanner;

public class Ejecutora {
    public static void main(String [] args){
        Scanner s = new Scanner(System.in);
        Empresa empresa = new Empresa();

        int opc = 0;
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
			opc = s.nextInt();

            switch (opc) {
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

        } while(opc!=0);
    }
}
