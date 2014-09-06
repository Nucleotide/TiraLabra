package tira.astar;

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
public class AstarTest {
    
    private LinkedList<Location> grid;
    private String start;
    private String end;
    private Astar a;
    
    public AstarTest() {
    }
    
    @Before
    public void setUp() {
        this.a = new Astar(50);
    }


    /**
     * Test of initialize method, of class Astar.
     */
    @Test
    public void testInitialize() {
        a.randomMap();
        assertTrue(a.getNodes().size()==50);
        
        assertNotNull(a.getGoal());
        assertNotNull(a.getStart());
    }

    /**
     * Test of route method, of class Astar.
     */
    @Test
    public void testRoute() {
        a.randomMap();
        a.route();
    }

    /**
     * Test of print method, of class Astar.
     */
    @Test
    public void testPrint() {
        a.randomMap();
        a.route();
        a.print();
        Helper help = a.getHelperObject();
        String vastaus = a.pathToGoalString();
        assertNotNull(vastaus);
    }
}