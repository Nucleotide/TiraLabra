package tira.dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import tira.common.Edge;
import tira.common.Node;
import tira.list.LinkedList;
import tira.utils.Helper;
import tira.utils.Location;
import tira.utils.Target;

/**
 *
 * @author joonaslaakkonen
 */
public class DijkstraTest {
    
    private LinkedList<Location> grid;
    private String start;
    private String end;
    private Dijkstra d;
    
    public DijkstraTest() {    
    }
    
    @Before
    public void setUp() {
        this.d = new Dijkstra(40);
    }

    /**
     * Test of initialize method, of class Dijkstra.
     * Testi testaa, että Nodet ja Edget on luotu, sen lisäksi tarkistetaan, että maali ja lähtö on oikein.
     */
    @Test
    public void testInitialize() {
        d.randomMap();
        assertTrue(d.getNodes().size()==40);
        
        assertNotNull(d.getGoal());
        assertNotNull(d.getStart());
    }

    /**
     * Test of route method, of class Dijkstra.
     */
    @Test
    public void testRoute() {
        d.randomMap();
        d.route();
    }

    /**
     * Test of print method, of class Dijkstra.
     */
    @Test
    public void testPrint() {     
        d.randomMap();
        d.route();
        d.print();
        Helper help = d.getHelperObject();
        String vastaus = d.pathToGoalString();
        assertNotNull(vastaus);
    }
}