package tira.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import tira.common.Edge;
import tira.common.Node;
import tira.common.Helper;
import tira.main.Mapper;
import tira.main.Target;

/**
 *
 * @author joonaslaakkonen
 * Astar olion luokka, joka vastaa reitin etsinnästä Astar algoritmilla. Käytössä heuristiikka joka vertailee
 * solmujen etäisyyttä niiden sijainnin perusteella.
 */
public class Astar {
    
    private ArrayList<Node> cells;
    private HashMap<String, ArrayList<Target>> graph;
    private String destination;
    private String source;
    private Node startCell;
    private Node goalCell;
    private Helper path;

    public Astar(String start, String end, HashMap grid) {
        this.source = start;
        this.destination = end;
        this.graph = grid;
        this.cells = new ArrayList<Node>();
        this.path = new Helper(this.cells);
    }
    
    /**
     * Alustus-metodi, joka luo Astar algoritmin tarvitsemat solmut ja polut HashMapiin tallenetun kartan perusteella.
     */  
    public void initialize() {
        for (String apu : this.graph.keySet()) {
            Node next = new Node(apu);
            this.cells.add(next);
        }
        
        for (Node helper : this.cells) {
            for (Target finder : this.graph.get(helper.toString())) {
                Node added = this.path.search(finder.getName());
                added.setCoords(finder.getX(), finder.getY());
                helper.addEdge(new Edge(added, finder.getDistance()));
            }
        }
        
        this.startCell = this.path.search(this.source);
        this.goalCell = this.path.search(this.destination);
        
        this.setHeuristics();
    }
    
    /**
     * Reitin haku algoritmilla.
     */  
    public void route() {  
        
        /**
         * Asetetaan alkusolmun etäisyydeksi nolla ja luodaan prioriteettijono, jonne lisätään
         * alkusolmu. Sen lisäksi luodaan lista, jonne käsitellyt solmut siirretään.
         */     
        this.startCell.setShortest(0);
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        queue.add(this.startCell);
        ArrayList<Node> closed = new ArrayList<Node>();
        
        /**
         * Käydään läpi prioriteettijono. 
         */     
        while (!closed.contains(this.goalCell)) {
            Node handle = queue.poll();
            closed.add(handle);
            
            for (Edge apu : handle.getEdges()) {
                Node neighbor = apu.getTarget();
                int cost = handle.getShortest() + apu.getWeight();
                
                if (!closed.contains(neighbor)) {
                    if (neighbor.getShortest() > cost) {
                        neighbor.setShortest(cost);
                        neighbor.setPrevious(handle);
                        if (queue.contains(neighbor)) {
                            queue.remove(neighbor);
                        }
                        queue.add(neighbor);
                    }
                }
            }
        }
    }
    
    /**
     * Tulostetaan lyhyin reitti alusta määränpäähän.
     */ 
    public void print() {
        if (this.goalCell.getShortest() == Integer.MAX_VALUE) {
            System.out.println("Reittiä ei ole kohteiden välillä");
        } else {
            System.out.println("Lyhyin reitti solmusta " + this.startCell.toString() + " solmuun " + this.goalCell.toString() + " on " + this.goalCell.getShortest() + "km.");         
            List<Node> route = this.path.getRoute(this.goalCell);
            System.out.println("Alla reitti:\n" + route);
        }    
    }    
    
    /**
     * Asetetaan arvio jokaiselle solmulle, kuinka pitkä matka siitä on maaliin.
     */
    private void setHeuristics() {
        for (Node setter : this.cells) {
            int xdiff = Math.abs(setter.getX() - this.goalCell.getX());
            int ydiff = Math.abs(setter.getY() - this.goalCell.getY());
            double heuristic = Math.sqrt((xdiff*xdiff + ydiff*ydiff));
            int parsed = (int)heuristic;
            setter.setHeuristic(parsed);
        }
    }
    
    /**
     * Seuraavaksi alla on metodeja, joita käytän vain testeissä päästäkseni käsiksi luokan muuttujiin.
     * Eivät siis vaikuta millään tavalla algoritmin tai ohjelman suoritukseen.
     */   
    public ArrayList<Node> getNodes() {
        return this.cells;
    }
    
    public Node getStart() {
        return this.startCell;
    }
    
    public Node getGoal() {
        return this.goalCell;
    }
    
    public Helper getHelperObject() {
        return this.path;
    }
    
    public String pathToGoalString() {
        List<Node> route = this.path.getRoute(this.goalCell);
        String path = "Lyhyin reitti solmusta " + this.startCell.toString() + " solmuun " + this.goalCell.toString() + " on " + this.goalCell.getShortest() + "km.";
        return path + route;
    }
}