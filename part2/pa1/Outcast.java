package part2.pa1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author : Siyadong Xiong
 * @version : 1/17/17.
 * @email : sx225@cornell.edu
 */
public class Outcast {
    private final WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public static void main(String[] args) {
        String dir = "wordnet/";
        String[] params = new String[]{"synsets.txt",
                              "hypernyms.txt",
                              dir + "outcast5.txt",
                              dir + "outcast8.txt",
                              dir + "outcast11.txt"};
        WordNet wordnet = new WordNet(params[0], params[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < params.length; t++) {
            In       in    = new In(params[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(params[t] + ": " + outcast.outcast(nouns));
        }
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int    dt      = -1;
        String outcast = null;

        for (int i = 0; i < nouns.length; i++) {
            int di = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i != j) {
                    di += wordnet.distance(nouns[i], nouns[j]);
                }
            }
            if (di > dt) {
                dt = di;
                outcast = nouns[i];
            }

        }
        return outcast;
    }
}
