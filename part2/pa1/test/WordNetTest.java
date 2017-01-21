import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import part2.pa1.WordNet;

import java.util.Iterator;

/**
 * @author : Siyadong Xiong
 * @version : 1/20/17.
 * @email : sx225@cornell.edu
 */
public class WordNetTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void test() throws Exception {
        final WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        String sap;
        int    dis;
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
        int              i    = 0;
        while (iter.hasNext()) {
            i++;
            iter.next();
        }
        Assert.assertEquals(i, 119188);
    }

    @Test
    public void testThrowException() throws Exception {
        int i = 0;
        try {
            new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidTwoRoots.txt");
        } catch (IllegalArgumentException ex) {
            i++;
        }
        try {
            new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidCycle.txt");
        } catch (IllegalArgumentException ex) {
            i++;
        }
        try {
            new WordNet("wordnet/synsets6.txt", "wordnet/hypernyms6InvalidTwoRoots.txt");
        } catch (IllegalArgumentException ex) {
            i++;
        }
        try {
            new WordNet("wordnet/synsets6.txt", "wordnet/hypernyms6InvalidCycle.txt");
        } catch (IllegalArgumentException ex) {
            i++;
        }
        try {
            new WordNet("wordnet/synsets6.txt", "wordnet/hypernyms6InvalidCycle.txt");
        } catch (IllegalArgumentException ex) {
            i++;
        }
        Assert.assertEquals(5, i);
    }
}
