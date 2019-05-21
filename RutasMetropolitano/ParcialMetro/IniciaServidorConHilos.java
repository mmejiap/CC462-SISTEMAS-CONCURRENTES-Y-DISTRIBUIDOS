/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package calificada2najarro;

package ParcialMetro;

public class IniciaServidorConHilos {
    public static void main(String[] args) {
        ParcialMetro server = new ParcialMetro();
        new Thread(server).start();
    }
}
