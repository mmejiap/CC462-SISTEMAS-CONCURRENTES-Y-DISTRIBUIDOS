
package ParcialMetro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
public class ParcialMetro implements Runnable{
	private int nrocli = 38;
	private ManejadorDeConHilos[] cli = new ManejadorDeConHilos[nrocli];        

	public ParcialMetro() {                
	}

	public void run(){
		try {
			int i = 0;
			ServerSocket s = new ServerSocket(8189);
			
			Scanner ee = new Scanner(System.in);

			String bandera = ee.nextLine();

			//if(bandera.equals("inicio")){
			while (true) {                
				Socket entrante = s.accept();
				System.out.println("Engendrado " + i);
				//Runnable 
				cli[i] = new ManejadorDeConHilos(entrante,i);
				Thread t = new Thread(cli[i]);
				t.start();
				i++;                
			}
			//}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

/*
	void ServidorRecibe(String llego){
		System.out.println("Respuesta:" + llego);
	}
	void ServidorEnvia(String envia){
		if (mTcpServer != null) {
			mTcpServer.sendMessageTCPServer(envia);
		}
	
	}
*/


	//public class Servidor(){


	//}

	private void ManejadorDeConHilos(Socket entrante, int i) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
//telnet 127.0.0.1 8189
