import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Assert;
import org.junit.Test;
import part2.pa1.SAP;

import java.util.Arrays;

/**
 * @author : Siyadong Xiong
 * @version : 1/20/17.
 * @email : sx225@cornell.edu
 */
public class SAPTest {
    private static final SAP sap = new SAP(new Digraph(new In("wordnet/digraph-wordnet.txt")));
    @Test
    public void length() throws Exception {
        Assert.assertEquals(sap.length(81552, 22741), 14);
        Assert.assertEquals(sap.length(Arrays.asList(35932), Arrays.asList(37299, 59062)), 12);
        Assert.assertEquals(sap.length(Arrays.asList(35431, 76511), Arrays.asList(39066)), 15);
        Assert.assertEquals(10, sap.length(Arrays.asList(69789, 71206), Arrays.asList(15721, 51687)));

    }

}
