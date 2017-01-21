import org.junit.Assert;
import org.junit.Test;
import part2.pa1.WordNet;

import java.util.Iterator;

/**
 * @author : Siyadong Xiong
 * @version : 1/20/17.
 * @email : sx225@cornell.edu
 */
public class WordNetTest {
    private final WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");

    @Test
    public void test() throws Exception {
        String sap;
        int dis;
        sap = wn.sap("worm", "bird");
        dis = wn.distance("worm", "bird");

        Assert.assertEquals(sap, "animal animate_being beast brute creature fauna");
        Assert.assertEquals(dis, 5);

        dis = wn.distance("white_marlin", "mileage");
        Assert.assertEquals(dis, 23);

        dis = wn.distance("Black_Plague", "black_marlin");
        Assert.assertEquals(dis, 33);

        dis = wn.distance("American_water_spaniel", "histology");
        Assert.assertEquals(dis, 27);

        dis = wn.distance("Brown_Swiss", "barrel_roll");
        Assert.assertEquals(dis, 29);

        Iterator<String> iter = wn.nouns().iterator();
        int i = 0;
        while (iter.hasNext()) {
            i++;
            iter.next();
        }
        Assert.assertEquals(i, 119188);
    }
}
