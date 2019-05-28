package ParcialMetro;

import java.util.*;

public class Grafo{

	int[] nodos;
	int[][] matrizAdy;
	String[] nombreNodo;

	int longMasPesada = Integer.MIN_VALUE;

	List<Nodo> listos = null;

	//String[] nombres = {"A","B","C"};

	Grafo(int[][] m, String[] nombres){
		this.matrizAdy=m;
		this.nombreNodo=nombres;
		nodos = new int[m.length];
		for(int i=0;i<m.length;i++)
			nodos[i]=i;
		
	}

	public ArrayList<Integer> encontrarRutaDijkstra(int inicio, int fin){
		encontrarRuta(inicio);
		Nodo tmp = new Nodo(fin);
		if(!listos.contains(tmp)){
			//System.out.println("Erro, nodo no alcanzable");
			return null;
		}
		tmp=listos.get(listos.indexOf(tmp));
		int peso = tmp.peso;
		Stack<Nodo> pila = new Stack<Nodo>();

		while(tmp!=null){
			pila.add(tmp);
			tmp=tmp.procedencia;
		}

		ArrayList<Integer> camino = new ArrayList<Integer>();
		while(!pila.isEmpty()) camino.add(pila.pop().id);

		return camino;


	}

	public void encontrarRuta(int inicio){
		Queue<Nodo> cola = new PriorityQueue<Nodo>();
		Nodo ni = new Nodo(inicio);
		listos = new LinkedList<Nodo>();
		cola.add(ni);
		while(!cola.isEmpty()){
			Nodo tmp = cola.poll();
			listos.add(tmp);
			int p = tmp.id;
			for(int j=0; j<matrizAdy.length;j++){
				if(matrizAdy[p][j]==0) continue;
				if(estaTerminado(j)) continue;

				Nodo nod = new Nodo(nodos[j],tmp.peso+matrizAdy[p][j],tmp);
				if(!cola.contains(nod)){
					cola.add(nod);
					continue;
				}
				for(Nodo x:cola){
					if(x.id==nod.id && x.peso<nod.peso){
						cola.remove(x);
						cola.add(nod);
						break;
					}
				}
			}

		}
	}

	public boolean estaTerminado(int j){
		Nodo tmp = new Nodo(nodos[j]);
		return listos.contains(tmp);
	}

	public int pesoCamino(int inicio, int fin){
		int p = 0;
		ArrayList<Integer> ruta = encontrarRutaDijkstra(inicio, fin);

		for(int i=0; i<ruta.size()-1;i++){
			p += matrizAdy[ruta.get(0)][ruta.get(1)];
		}
		
		return p;
	}

	



	public void printMatrizAdy(){
		System.out.println("... Matriz de adyacencia ...");
		for(int i=0; i<matrizAdy.length;i++){
			for(int j=0;j<matrizAdy.length;j++)
				System.out.print(""+matrizAdy[i][j]+((j==matrizAdy.length-1)?("\n"):("\t")));
		}
	}

	public void printCamino(int inicio, int fin){
		ArrayList<Integer> c = encontrarRutaDijkstra(inicio, fin);
		for(int i=0;i<c.size();i++){
			//System.out.print("->"+c.get(i));
			System.out.print("->"+nombreNodo[c.get(i)]);
		}
		System.out.println();
	}

	public void printNombresNodo(){
		System.out.println("... Paraderos ...");
                for(int i=0; i<matrizAdy.length;i++)
			System.out.print("-> "+nombreNodo[i]);
		System.out.println("");
	}

}
