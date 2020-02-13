import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Kruskal {
	
	public static void main(String[] args) {

        /* Create a graph which contains nodes a, b, c, d, e, f, g */
        Graph g = new Graph(new String[]{"a", "b", "c", "d", "e", "f", "g"});

        /* Add the edges */
        g.addEdge("a", "b", 1.0);
        g.addEdge("a", "c", 3.0);
        g.addEdge("b", "c", 2.0);
        g.addEdge("b", "d", 5.0);
        g.addEdge("b", "e", 7.0);
        g.addEdge("b", "f", 9.0);
        g.addEdge("c", "d", 4.0);
        g.addEdge("d", "e", 6.0);
        g.addEdge("d", "g", 12.0);
        g.addEdge("e", "f", 8.0);
        g.addEdge("e", "g", 11.0);
        g.addEdge("f", "g", 10.0);

        /* Get a minimum spanning tree of the graph */
        Graph tree = kruskalCC(g);

        /* If there is such a tree (i.e., if the graph is connex */
        if (tree != null)

            /* Display it */
            System.out.println(tree);

        else
            System.out.println("No spanning tree");
        // total cost
        double result=0.0;

        for (int i = 0; i < tree.n; i++)
            for (int j = 0; j < tree.n; j++)
                if (tree.adjacency[i][j] != 0)

                    /* If there is an edge */
                    if (tree.adjacency[i][j] == tree.adjacency[j][i]) {
                        /* if we meet this edge for the first time */
                        if (i < j)
                            result +=  tree.adjacency[i][j];
                    }
        System.out.println("total cost is "+result+"\n");
	}

	/**
	 * Apply Kruskal algorithm to find a minimal spanning tree of a graph
	 * @return A tree which corresponds a minimal spanning tree of the graph; null if there is none
	 */
	public static Graph kruskal(Graph g, boolean computeMin) {
		
		/* Create a new graph with the same nodes than g */
		Graph tree = new Graph(g.nodes);

		/* Get all the edges from g */
		List<Edge> edges = g.getEdges();
		
		/* Sort the edges by increasing weight */
        Collections.sort(edges);
        if (!computeMin)
            Collections.reverse(edges);

		//TODO: mettre votre code ici
        for(Edge e:edges)
        {
            if(!tree.createACycle(e))
            {
                tree.addEdge(e);
            }
        }

		return tree;
	}
    public static Graph kruskalCC(Graph g)
    {
        /* Create a new graph with the same nodes than g */
        Graph tree = new Graph(g.nodes);

        /* Get all the edges from g */
        List<Edge> edges = g.getEdges();

        /* Sort the edges by increasing weight */
        Collections.sort(edges);
        boolean[] component=new boolean[tree.n];
        assert (!component[0]);
        for (Edge edge: edges)
        {
            if(!component[edge.id1] || !component[edge.id2])
            {
                component[edge.id1]=true;
                component[edge.id2]=true;
                tree.addEdge(edge);
            }
        }
        return tree;
    }
}
