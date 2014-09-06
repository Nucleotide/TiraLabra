package tira.main;

import java.util.Scanner;
import tira.astar.Astar;
import tira.dijkstra.Dijkstra;

/**
 *
 * @author joonaslaakkonen
 * Pääohjelman runko, joka ohjailee ohjelma toimintaa käyttäjän syötteen perusteella. Luokka myös luo suurimman osan
 * ohjelman muista olioista.
 */
public class Run {  
    
    /**
     * 
     * @param args
     * Pääohjelma, jossa ensin luetaan karttatiedosto ja sen jälkeen käyttäjä ohjaa toimintaa.
     */
    public static void main( String[] args ){

        Scanner input = new Scanner(System.in);
        int algoritmi = 0;
        int nodelkm = 0;
        
        /**
         * Käyttäjä valitsee kumalla algoritmilla haluaa hakea reitin.
         */
        try {
            System.out.println("Valitse algoritmi reitin hakuun:\n 1 = A*\n 2 = Dijkstra.");
            algoritmi = Integer.parseInt(input.nextLine());
            System.out.println("Kuinka paljon nodeja?");
            nodelkm = Integer.parseInt(input.nextLine());
            
        } catch (NumberFormatException e) {
            System.out.println("Koitapa nyt antaa numeroita..");
        }
        
        if (algoritmi == 2 && nodelkm > 0) {
            doDijkstra(nodelkm);
        }
        if (algoritmi == 1 && nodelkm > 0) {
            doAstar(nodelkm);
        }    
        
        System.exit(0);
    }
    
    /**
     * Luodaan Astar-olio, alustetaa verkko ja etsitään reitti.
     */
    private static void doAstar(int nodeja) {
        Astar a = new Astar(nodeja);
        a.randomMap();
        long start = System.nanoTime();
        a.route();
        long end = System.nanoTime();
        long micros = (end - start) / 1000;
        System.out.println("Operaatioon kului aikaa: \n" + micros + "mikrosekuntia"); 
        a.print();
    }
     
    /**
     * Luodaan Dijsktra-olio, alustetaan verkko ja etsitään reitti.
     */
    private static void doDijkstra(int nodeja) {
        Dijkstra d = new Dijkstra(nodeja);
        d.randomMap();
        long start = System.nanoTime();
        d.route();
        long end = System.nanoTime();
        long micros = (end - start) / 1000;
        System.out.println("Operaatioon kului aikaa: \n" + micros + "mikrosekuntia"); 
        d.print();
    }    
}