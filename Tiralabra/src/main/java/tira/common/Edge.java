package tira.common;

/**
 *
 * @author joonaslaakkonen
 * Luokka kuvaa kartan kaarta kahden solmun välillä. Kaarella on pituus sekä kohdesolmu.
 */
public class Edge {
    public Node target;
    public int weight;
    
    public Edge(Node destination, int distance){ 
        this.target = destination;
        this.weight = distance;
    }
    
    /**
     * 
     * Gettereitä. 
     */  
    public String toString() {
        return this.target.toString() + this.weight;
    }
    
    public Node getTarget() {
        return this.target;
    }

    public int getWeight() {
        return this.weight;
    }
}