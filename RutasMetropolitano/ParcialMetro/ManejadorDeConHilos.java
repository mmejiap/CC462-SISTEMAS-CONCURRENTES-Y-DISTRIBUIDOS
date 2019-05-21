//package calificada2najarro;

package ParcialMetro;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.net.*;
import java.io.*;

import static ParcialMetro.Data_struct.*;

public class ManejadorDeConHilos implements Runnable{
    private Socket entrante;
    private int  contador;
    public ManejadorDeConHilos(Socket i,int c){
        entrante = i; contador = c; 
     
    }
    
   
    
    public void run(){
        try {
            try {
                InputStream secuenciaDeEntrada = entrante.getInputStream();
                OutputStream secuenciaDeSalida = entrante.getOutputStream();

                Scanner in = new Scanner(secuenciaDeEntrada);
                PrintWriter out = new PrintWriter(secuenciaDeSalida,true);
                float infimo=(float)8.0+((float)contador)*(float)0.5;
                float supremo= (float)8.0+((float)contador)*(float)0.5 + (float)0.5 ;

                //String[] parseTime = h.split(":");
                //return (Integer.valueOf(parseTime[0])*60*60 + Integer.valueOf(parseTime[1])*60 + Integer.valueOf(parseTime[2]));

                String hora_inicio="04:29:00";
                String hora_fin="23:59:59";

                //String[] parseTime_hI=hora_inicio.split(":");
                //Data_struct temp = new Data_struct();
                //int hora_segundo_I =  temp.timeStringToSegundos(hora_inicio);
                String infimo_str = timeAddMin(hora_inicio, contador*30);
                String supremo_str = timeAddMin(hora_inicio, contador*30+30);

                //out.println("" +  infimo + "-"+ supremo );
                out.println("" +  infimo_str + "-"+ supremo_str );
                
                 //del temp;

                boolean terminado = false;
                while(!terminado && in.hasNextLine()){
                    String linea = in.nextLine();
                    //out.println("Eco"+linea);
                    System.out.println("Eco de:"+contador+" dice:"+linea);
                    String arrayString[] = linea.split("\\s+");
                    int x = Integer.parseInt(arrayString[0]);
                    int y = Integer.parseInt(arrayString[1]);
                    
                   
                    
                    out.println(infimo + "- " + supremo);
                    
                    if(linea.trim().equals("ADIOS")){
                        terminado = true;
                    }
                }                
            } finally {
                entrante.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}
