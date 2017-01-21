package part2.pa1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * @author : Siyadong Xiong
 * @version : 1/17/17.
 * @email : sx225@cornell.edu
 */
public class WordNet {

    private final Map<String, Set<Integer>> nouns;
    private final List<String>              synsets;
    private final Digraph                   graph;

    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new NullPointerException();

        In in = new In(synsets);

        String line;
        nouns = new HashMap<>();
        this.synsets = new ArrayList<>();
        while ((line = in.readLine()) != null) {
            String[] items = line.split(",");
            this.synsets.add(items[1]);

            int id = Integer.parseInt(items[0]);
            for (String s : items[1].split(" ")) {
                nouns.putIfAbsent(s, new HashSet<>());
                nouns.get(s).add(id);
            }
        }


        graph = new Digraph(this.synsets.size());
        in.close();
        in = new In(hypernyms);
        while ((line = in.readLine()) != null) {
            int[] nodes = Arrays.stream(line.split(","))
                                .mapToInt(Integer::valueOf)
                                .toArray();
            for (int i = 1; i < nodes.length; i++) {
                graph.addEdge(nodes[0], nodes[i]);
            }
        }

        sap = new SAP(graph);
    }

    // all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        Iterable<Integer> a        = nouns.get(nounA);
        Iterable<Integer> b        = nouns.get(nounB);
        int               ancestor = sap.ancestor(a, b);
        if (ancestor == -1) throw new IllegalArgumentException();
        return synsets.get(ancestor);
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        int dis =  sap.length(nouns.get(nounA),
                              nouns.get(nounB));
        if (dis == -1) throw new IllegalArgumentException();
        return dis;
    }
}
