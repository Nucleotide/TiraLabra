package tira.astar;

import java.util.Random;
import tira.common.Edge;
import tira.common.Node;
import tira.heap.Heap;
import tira.list.LinkedList;
import tira.utils.Helper;

/**
 *
 * @author joonaslaakkonen
 * Astar olion luokka, joka vastaa reitin etsinnästä Astar algoritmilla. Käytössä heuristiikka joka vertailee
 * solmujen etäisyyttä niiden sijainnin perusteella (euklidinen etäisyys).
 */
public class Astar {
    
    private LinkedList<Node> cells;
    private Node startCell;
    private Node goalCell;
    private Helper path;
    private int nodeCount;

    public Astar(int nodeja) {
        this.cells = new LinkedList<Node>();
        this.path = new Helper(this.cells);
        this.nodeCount=nodeja;
    }
    
    /**
     * Tehdään random kartta.
     */
    public void randomMap() {
        Random rand = new Random();
        String nimi ="";
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        /**
         * Tämä for-looppi määrää monta nodea verkoon tulee ja luo nodet.
         */
        for (int i=0; i<this.nodeCount;i++) {
            nimi = "";
            for (int j = 0; j < 7; j++) {
                int arpa = rand.nextInt(35);
                nimi = nimi + letters.charAt(arpa);
                
            }
            Node uusi = new Node(nimi);
            this.cells.add(uusi);
            
            /**
             * Tässä mahdollisuus säätää x, y alueen kokoa.
             */
            int x = rand.nextInt((this.nodeCount/5));
            int y = rand.nextInt((this.nodeCount/5));
            uusi.setCoords(x, y);
        }
        
        /**
         * Tämä for-looppi arpoo jokaiselle nodelle naapureiden määrän ja naapurit.
         */
        for (Node listaaja : this.cells) {
            int naapureita = rand.nextInt((this.nodeCount/10));
            for (int i = 0; i < naapureita; i++) {
                int satunnainenNaapuri = rand.nextInt(this.nodeCount);
                /**
                 * Ei lisätä nodea itseään omaksi naapurikseen.
                 */
                if (this.cells.get(satunnainenNaapuri).equals(listaaja)) {
                    i--;
                } else {
                    /**
                     * arvotaan kaaripaino ja lisätään naapuri. Tarkistetaan, että kaari ei ole lyhyempi
                     * kuin solujen välinen euklidinen yhteys.
                     */
                    Node naapuri = (Node)this.cells.get(satunnainenNaapuri);
                    int kaari = rand.nextInt((this.nodeCount/10));
                    int xdiff = Math.abs(listaaja.getX() - naapuri.getX());
                    int ydiff = Math.abs(listaaja.getY() - naapuri.getY());
                    double euklidinen = Math.sqrt((xdiff*xdiff + ydiff*ydiff));
                    int dist = (int)euklidinen;
                    if (kaari <= dist) {
                        kaari = dist + 2;
                    }
                    listaaja.addEdge(new Edge(naapuri, kaari));
                }
            }
        }
        
        /**
         * Arvotaan maali ja lähtö.
         */
        int alku = rand.nextInt(this.nodeCount);
        int maali = rand.nextInt(this.nodeCount);
        this.startCell = (Node)this.cells.get(alku);
        this.goalCell = (Node)this.cells.get(maali);
        
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
        Heap<Node> heap = new Heap(this.cells.size());
        heap.insert(this.startCell);
        this.startCell.addedtoHeap();
        LinkedList<Node> closed = new LinkedList<Node>();
        
        /**
         * Käydään läpi keko. 
         */     
        while (!this.goalCell.closed() && !heap.empty()) {
            Node handle = heap.poll();
            handle.removedfromHeap();
            closed.add(handle);
            handle.close();
            
            for (Edge apu : handle.getEdges()) {
                Node neighbor = apu.getTarget();
                int cost = handle.getShortest() + apu.getWeight();
                
                /**
                 * Etäisyyden päivitys mikäli ollaan löydetty parempi reitti.
                 */
                if (!neighbor.closed()) {
                    if (neighbor.getShortest() > cost) {
                        neighbor.setShortest(cost);
                        neighbor.setPrevious(handle);
                        if (neighbor.inHeap()) {
                            heap.decreaseKey(neighbor);
                        } else {
                            heap.insert(neighbor);
                            neighbor.addedtoHeap();
                        }  
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
            System.out.println("Reittiä ei ole kohteiden " + this.startCell.toString() + " ja " + this.goalCell.toString() + " välillä");
        } else {
            System.out.println("Lyhyin reitti solmusta " + this.startCell.toString() + " solmuun " + this.goalCell.toString() + " on " + this.goalCell.getShortest() + "km.");         
            System.out.println(this.path.getRoute(this.goalCell));
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
    public LinkedList<Node> getNodes() {
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
        String route = this.path.getRoute(this.goalCell);
        String path = "Lyhyin reitti solmusta " + this.startCell.toString() + " solmuun " + this.goalCell.toString() + " on " + this.goalCell.getShortest() + "km.";
        return path + route;
    }
}