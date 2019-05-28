package ParcialMetro;


public class Nodo implements Comparable<Nodo>{
	int id;
	String nombre;
	int peso=Integer.MAX_VALUE;
	Nodo procedencia=null;
	
	Nodo(int x, int w, Nodo p){id=x; peso=w; procedencia=p;}
	Nodo(int x){this(x,0,null);}

	public int compareTo(Nodo tmp){return this.peso-tmp.peso;}
	public boolean equals(Object o){
		Nodo tmp = (Nodo) o;
		if(tmp.id==this.id) return true;
		return false;
	}
	public void setNombreNodo(String s){this.nombre=s;}
}
