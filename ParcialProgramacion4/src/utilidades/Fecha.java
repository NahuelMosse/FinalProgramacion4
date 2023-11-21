package utilidades;

import java.util.Calendar;
import java.util.Scanner;

public class Fecha {
    private int dia;
	private int mes;
	private int año;
	
	public static Fecha hoy(){
		Fecha f = new Fecha();
		Calendar c=Calendar.getInstance();
		f.setDia(c.get(Calendar.DAY_OF_MONTH));
		f.setMes(c.get(Calendar.MONTH)+1);
		f.setAño(c.get(Calendar.YEAR));
		return f;
	}
	
	public static boolean bisiesto(int a){
		if(a % 4 == 0){
			if( a % 100 == 0)
				if(a % 1000 == 0)
					return true;
				else
					return false;
			else
				
				return true;
		}
		
		else 
			return false;
	}
	
	public Fecha(){
		dia=mes=año=0;
	}
	
	public Fecha(int d, int m, int a){
		//si el dia o el mes son incorrectos deberia lanzar un excepcion
		int dias[]= {31,28,31,30,31,30,31,31,30,31,30,31};
		int fin = 0;
		if(m >= 1 && m <= 12){
			fin = dias[m-1];
			if(m == 2 && bisiesto(a)){
				fin = 29;
			}
			if(d >= 1 && d <= fin){
				dia = d;
				mes = m;
				año = a;
			}
		}
	}	
	public boolean equals(Fecha f){
		return año == f.getAño() && mes == f.getMes() && dia == f.getDia();
	}
	
	public static Fecha nuevaFecha()
	{
		/*Crea una instancia de Fecha con los valores especificados por el usuario, validado previamente*/
		Scanner entrada = new Scanner(System.in);
		int d,m,a;
		boolean valida = false;
		do
		{
			do
			{
				System.out.print("Ingrese D�a: ");
				d = entrada.nextInt();
			}
			while (d < 1 || d > 31);
			do
			{
				System.out.print("Ingrese Mes: ");
				m = entrada.nextInt();				
			}
			while(m < 1 || m > 12);
			do
			{
				System.out.print("Ingrese A�o: ");
				a = entrada.nextInt();
			}
			while (a < 1442 || a > 9999);
			
			if(m != 2)
			{
				if(m == 4 || m == 6 || m == 9 || m == 11)
				{
					if(d > 30)
					{
						valida = false;
					}
					else
					{
						valida = true;
					}
				}
				else
				{
					valida = true;
				}
			}
			else //FEBRERO
			{
				if(d < 29)
				{
					valida = true;
				}
				else
				{
					if(d > 29)
					{
						valida = false;
					}
					else // CASO 29 DE FEBRERO
					{
						if((a % 4) != 0) // NO ES BISIESTO
						{
							valida = false;
						}
						else
						{
							if(a % 100 != 0)
							{
								valida = true;
							}
							else
							{
								if(a % 400 == 0)
								{
									valida = true;
								}
								else
								{
									valida = false;
								}
							}
						}
						
					}					
				}
				
			}
		}
		while (!valida);
		Fecha f = new Fecha(d,m,a);
		return f;	
	}
	
	public int compareTo(Fecha f){
		//< 0 fecha receptor anterior a f
		//== 0 fecha receptor igual a f
		//>0 fecha receptor posterior a f
		int f1 = año * 10000 + mes * 100 + dia;
		int f2 = f.getAño() * 10000 + f.getMes()* 100 + f.getDia();
		return f1 - f2;
	}
	
	public boolean bisiesto(){
		return Fecha.bisiesto(año);
	}

	public Fecha sumarDias(int d){
		int dias[]= {31,28,31,30,31,30,31,31,30,31,30,31};
		Fecha f = new Fecha();
		int d2,m2,a2;
		d2=dia;
		m2=mes;
		a2=año;
		int fin, restan;
		do{
			
			fin = dias[m2-1];
			if(m2 == 2 && bisiesto())
				fin = 29;
			restan = fin - d2;
			if(restan >= d){
				d2 += d;
				d = 0;
			}
			else{
				d -= restan;
				d2 = 0;
				if(m2 == 12){
					m2 = 1;
					a2++;
				}
				else
					m2++;
			}
		}
		while(d>0);
		f.setDia(d2);
		f.setMes(m2);
		f.setAño(a2);
		return f;
	}
	
	public Fecha sumarMeses(int m){
		Fecha f = new Fecha();
		int d2,m2,a2;
		d2=dia;
		m2=mes;
		a2=año;
		int restan;
		do{
			restan = 12 - m2;
			if(restan >= m){
				m2 += m;
				m = 0;
			}
			else{
				m -= restan;
				m2 = 0;
				a2++;
			}
		}
		while(m>0);
		f.setDia(d2);
		f.setMes(m2);
		f.setAño(a2);
		return f;
	}
	public Fecha sumarAños(int a){
		Fecha f = new Fecha();
		f.setDia(dia);
		f.setMes(mes);
		f.setAño(año + a);
		return f;
	}
	
	public Fecha restarDias(int d){
		int dias[]= {31,28,31,30,31,30,31,31,30,31,30,31};
		Fecha f = new Fecha();
		int d2,m2,a2;
		d2=dia;
		m2=mes;
		a2=año;
		int fin;
		do{
			if(d2 > d){
				d2 -= d;
				d=0;
			}
			else{
				
				d-=d2;
				if(m2 == 1)
				{
					fin = 31;
					m2 = 12;
					a2--;
				}
				else{
					fin = dias[m2-2];
					if(m2-1 == 2 && bisiesto())
						fin = 29;
					m2--;
				}
				d2=fin;
			}
		}while(d>0);
		f.setDia(d2);
		f.setMes(m2);
		f.setAño(a2);
		return f;
	}
	
	public Fecha restarMeses(int m){
		Fecha f = new Fecha();
		int d2,m2,a2;
		d2=dia;
		m2=mes;
		a2=año;
		do{
			if(m2 > m){
				m2 -= m;
				m=0;
			}
			else{
				m-=m2;
				a2--;
				m2=12;
			}
		}while(m>0);
		f.setDia(d2);
		f.setMes(m2);
		f.setAño(a2);
		return f;
	}
	
	public Fecha restarAños(int a){
		Fecha f = new Fecha();
		f.setDia(dia);
		f.setMes(mes);
		f.setAño(año - a);
		return f;
	}
	
	public boolean entre(Fecha f1,Fecha f2){
		return this.compareTo(f1)>=0 && this.compareTo(f2)<=0;
	}
	
	public String toString(){
		return dia+"/"+mes+"/"+año;
	}
	public int getDia(){
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getMes() {
		return mes;
	}
	public void setAño(int año) {
		this.año = año;
	}
	public int getAño() {
		return año;
	}
}
