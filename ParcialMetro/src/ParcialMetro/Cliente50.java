/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package calificada2najarro;

package ParcialMetro;


//import java.util.Scanner;
import parcial_metro.TCPClient50;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



class Cliente50{
    TCPClient50 mTcpClient;
    Scanner sc;
    
    private static final String[] paraderos_sort = new String[] {"Naranjal","Izaguirre","Pacifico","Independencia","Los jazmines","Tomas Valle","El Milagro","Honorio Delgado","Uni","Parque del Trabajo","Caqueta","2 de Mayo","Quilca","España","Estacion Central","Estadio Nacional","México","Canadá","Javier Prado","Canaval y Moreyra","Aramburu","Domingo Orué","Angamos","Ricardo Palma","Benavides","28 de Julio","Plaza de Flores","Balta","Bulevar","Union","Escuela Militar","Terán","Rosario de Villa","Matellini"};
    private static final String[] paraderos_excluidos = new String[] {"Ramon Castilla","Tacna","Jiron de la Unión","Colmena"};
            
    public static void main(String[] args)  {
        Cliente50 objcli = new Cliente50();
        objcli.iniciar();
        
    }
    void iniciar(){
       new Thread(
            new Runnable() {

                @Override
                public void run() {
                    mTcpClient = new TCPClient50("127.0.0.1",
                        new TCPClient50.OnMessageReceived(){
                            @Override
                            public void messageReceived(String message){
                                ClienteRecibe(message);
                            }
                        }
                    );
                    mTcpClient.run();                   
                }
            }
        ).start();
        //---------------------------
       
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Cliente bandera 01");
        while( !salir.equals("s")){
            salir = sc.nextLine();
            ClienteEnvia(salir);
        }
        System.out.println("Cliente bandera 02");
    
    }
    void ClienteRecibe(String llego){
        System.out.println("CLINTE50 El mensaje::" + llego);
        
        Data_struct temp = new Data_struct();
        String[] hora = llego.split("-");
        //int hI_seg = timeStringToSegundos(hora[0]);
        //int hF_seg = timeStringToSegundos(hora[1]);
        //String csvFile = "04_05_2018.csv";
        String csvFile = "src\\data\\04_05_2018.csv";
        //String line = "";
        String SplitBy = ";";
        
        //if(timeStringToSegundos(data))
        ArrayList<Data_struct> dataList=readFile(csvFile,SplitBy,hora[0],hora[1]);
        
        int tam_paraderos = paraderos_sort.length;
        //int[][] matrix = new int[tam_paraderos][tam_paraderos];
        int[][] matrix = initMatrix(dataList);
        printMatrix(matrix);
        
        //System.out.println("Primer elemento de dataList -> Hora:"+dataList.get(2).getTiempo_str() +"  pI: "+dataList.get(2).getParada_I()+"  pF: "+dataList.get(2).getParada_F());
        
        

    }
    void ClienteEnvia(String envia){
        if (mTcpClient != null) {
            mTcpClient.sendMessage(envia);
        }
    }
    
    
    public ArrayList<Data_struct> readFile(String csvFile,String cvsSplitBy, String hI, String hF){
    	//String csvFile = "04_05_2018.csv";
        String line = "";
        //String cvsSplitBy = ";";
        
        int h_seg_inicial = timeStringToSegundos(hI);
        int h_seg_final = timeStringToSegundos(hF);
        
        ArrayList<Data_struct> dataList = new ArrayList();



        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            line=br.readLine();
            
            

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                //System.out.println("Country [code= " + data[4] + " , name=" + data[5] + "]");
                //System.out.println("hora-> " + data[1] + " , parada=" + data[3] + "->"+"parada_F="+data[4]);
                
                int tt = timeStringToSegundos(data[1]);
                
                //filtramos los datos que le corresponden
                if(tt>h_seg_inicial && tt<=h_seg_final && (perteneceA(data[3],paraderos_excluidos)==false && perteneceA(data[4],paraderos_excluidos)==false))
                    dataList.add(new Data_struct(data[1],data[3],data[4]));
                
                
                
                
                //printDataList(dataList);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return dataList;
    }

    public static String timeSegundosToString(int seg){
            int h=(int)Math.floor((double)seg/3600.0);
            int m=(int)Math.floor((seg-h*3600)/60);
            int s=seg-h*3600-m*60;
            //System.out.println("    ->h:"+h+" ->m:"+m+" ->s:"+s);
            return ""+timeFormato(h)+":"+timeFormato(m)+":"+timeFormato(s);
        }
        
        public static String timeFormato(int n){
            return n<10?("0"+n):(""+n);
        }
        
        public static String timeAddMin(String t, int m){
            int tiempo = timeStringToSegundos(t);
            return timeSegundosToString(tiempo+m*60);
        }
        public static String timeAddSeg(String t, int s){
            int tiempo = timeStringToSegundos(t);
            return timeSegundosToString(tiempo+s);
        }
        
	public static int timeStringToSegundos(String h){
		String[] parseTime = h.split(":");
                //System.out.println("parseTime -> "+parseTime[0]+":"+parseTime[1]+":"+parseTime[2]);
		return (Integer.valueOf(parseTime[0])*3600 + Integer.valueOf(parseTime[1])*60 + Integer.valueOf(parseTime[2]));
	}
        
        public void printDataList(ArrayList<Data_struct> data){
            if(data.size()>0)
                System.out.println("Hora\tParadero_I\tParadero_F");
            data.stream().forEach((item) -> {
                System.out.println(""+item.getTiempo_str()+"\t"+item.getParada_I()+"\t"+item.getParada_F());
        });
        }
        
        public boolean perteneceA(String e,String[] list){
            boolean r=false;
            
            for (String list1 : list) {
                if (list1.equals(e)) {
                    return true;
                }
            }
            
            return r;
        }
        public int indexParadero(String e,String[] list){
            int index=-1;
            
            for (int i=0; i<list.length;i++) {
                if (e.equals(list[i])){
                    return i;
                }
            }
            
            return index;
        }
        
        public int[][] initMatrix(ArrayList<Data_struct> data){
            int dim = paraderos_sort.length;
            int [][] r = new int[dim][dim];
            
            for (Data_struct e : data) {
                r[indexParadero(e.parada_I,paraderos_sort)][indexParadero(e.parada_F,paraderos_sort)]++;
            }
            
            return r;
        }
        
        
        
        public void printMatrix(int[][] m ){
            int dim = paraderos_sort.length;
            /*
            System.out.print(""+fixedLengthString("-",18));
            System.out.print("\t");
            for(int i=0; i<dim;i++){
                
                System.out.print(""+fixedLengthString(paraderos_sort[i],18));
            }
            */
                    
            for(int i=0; i<dim; i++){
                System.out.print(""+fixedLengthString(paraderos_sort[i],16)+"\t");
                for(int j=0; j<dim;j++){
                    System.out.print(""+m[i][j]+(j==dim-1?("\n"):("\t")));
                }
            }
        }
        
    public static String fixedLengthString(String string, int length) {
       return String.format("%1$"+length+ "s", string);
    }

}
