package ParcialMetro;

public class Data_struct{

	String tarjeta;
	String tiempo_str;
	int tiempo_seg;
	String parada_I;
	String parada_F;


	public Data_struct(){
		this.tarjeta = "00";
		this.tiempo_str="00";
		this.tiempo_seg=0;
		this.parada_I="00";
		this.parada_F="00";
	}

	public Data_struct(String t_str,String pI, String pF){
		this.tiempo_str=t_str;
		this.parada_I=pI;
		this.parada_F=pF;
		this.tiempo_seg=timeStringToSegundos(t_str);
	}


	public void setTarjeta(String tar){this.tarjeta = tar;};
	public void setTiempo_str(String t_str){this.tiempo_str = t_str;};
	public void setTiempo_seg(int t_seg){this.tiempo_seg = t_seg;};
	public void setParada_T(String pI){this.parada_I = pI;};
	public void setParada_F(String pF){this.parada_F = pF;};

	public String getTarjeta(){return this.tarjeta;};
	public String getTiempo_str(){return this.tiempo_str;};
	public int getTiempo_seg(){return this.tiempo_seg;};
	public String getParada_I(){return this.parada_I;};
	public String getParada_F(){return this.parada_F;};


        
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

}