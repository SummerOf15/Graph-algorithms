import java.util.ArrayList;
import java.util.List;

public class Dijkstra {

	public static void main(String[] args) {

		/* Create the oriented graph */
		Graph g = new Graph(new String[] {"Paris", "Hambourg", "Londres", "Amsterdam", "Edimbourg", "Berlin", "Stockholm", "Rana", "Oslo"});
		
		g.addArc("Paris", "Hambourg", 7);
		g.addArc("Paris",  "Londres", 4);
		g.addArc("Paris",  "Amsterdam", 3);
		g.addArc("Hambourg",  "Stockholm", 1);
		g.addArc("Hambourg",  "Berlin", 1);
		g.addArc("Londres",  "Edimbourg", 2);
		g.addArc("Amsterdam",  "Hambourg", 2);
		g.addArc("Amsterdam",  "Oslo", 8);
        g.addArc("Amsterdam",  "Londres", 1);
		g.addArc("Stockholm",  "Oslo", 2);
		g.addArc("Stockholm",  "Rana", 5);
		g.addArc("Berlin",  "Amsterdam", 2);
		g.addArc("Berlin",  "Stockholm", 1);
		g.addArc("Berlin",  "Oslo", 3);
		g.addArc("Edimbourg",  "Oslo", 7);
		g.addArc("Edimbourg",  "Amsterdam", 3);
		g.addArc("Edimbourg",  "Rana", 6);
		g.addArc("Oslo",  "Rana", 2);
		
		/* Apply Dijkstra algorithm to get an arborescence */
		Graph tree = dijkstra(g, "Paris");
		
		System.out.println(tree);

	}
	
	/**
	 * Apply Dijkstra algorithm on a graph
	 * @param g The graph considered
	 * @param origin The starting node of the paths
	 * @return A graph which is an arborescence and represent the shortest paths from the origin to all the other nodes 
	 */
	public static Graph dijkstra(Graph g, String origin) {
		
		/* Get the index of the origin */
		int r = g.indexOf(origin);

		/* Next node considered */
		int pivot = r;
		
		/* Create a list that will contain the nodes which have been considered */
		List<Integer> V2 = new ArrayList<Integer>();
		V2.add(r);
		
		
		int[] pred = new int[g.n];
		double[] pi = new double[g.n];
		
		/* Initially, the distance between r and the other nodes is the infinity */
		for(int v = 0; v < g.n; v++)
			if(v != r)
				pi[v] = Double.POSITIVE_INFINITY;

		//TODO: mettre votre code ici

		while(V2.size()<=g.n) {
            double min_path=1000;
            int min_i=0;
            // iter each column and update the min weight and min road
            for (int i = 0; i < g.n; i++) {
                if (g.adjacency[pivot][i] > 0) {
                    double new_weight = g.adjacency[pivot][i] + pi[pivot];
                    if (pi[i] >= new_weight) {
                        pi[i] = new_weight;
                        pred[i] = pivot;

                    }
                }
            }
            // chose the new pivot
            for(int k=0;k<g.n;k++)
            {
                if(pi[k]<min_path && !V2.contains(k))
                    min_i=k;
            }
            pivot=min_i;
            V2.add(pivot);
        }
        Graph min_g=new Graph(g.nodes);
		for(int i=0;i<g.n;i++)
        {
            if(r!=i)
                min_g.addArc(r,i,pi[i]);
        }

		return min_g;
	}
}
