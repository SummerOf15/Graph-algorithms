import java.util.*;

public class FordFulkerson {

	public static void main(String[] args) {

		/* The weight in graph g are the capacities of the arcs */
		Graph g = example();

		/* The weights in graph "flow" are the values of the flow */ 
		Graph flow = fordFulkerson(g, "s", "t");

		System.out.println(flow);

	}
	
	/** Function which creates an example graph on which Ford-Fulkerson will be applied */
	public static Graph example() {
		
		Graph g = new Graph(new String[] {"s", "a", "b", "c", "d", "e", "t"});

		g.addArc("s", "a", 8);
		g.addArc("s", "c", 4);
		g.addArc("s", "e", 6);
		g.addArc("a", "b", 10);
		g.addArc("a", "d", 4);
		g.addArc("b", "t", 8);
		g.addArc("c", "b", 2);
		g.addArc("c", "d", 1);
		g.addArc("d", "t", 6);
		g.addArc("e", "b", 4);
		g.addArc("e", "t", 2);
		
		return g;
	}
	public static Graph example2(){
        Graph g = new Graph(new String[] {"s", "a", "b", "c", "d", "t"});
        g.addArc("s", "a", 16);
        g.addArc("s", "b", 13);
        g.addArc("a", "b", 10);
        g.addArc("a", "c", 12);
        g.addArc("b", "a", 4);
        g.addArc("b", "d", 14);
        g.addArc("c", "b", 9);
        g.addArc("c", "t", 20);
        g.addArc("d", "c", 7);
        g.addArc("d", "t", 4);
        return g;
    }
	public static Graph fordFulkerson(Graph g, String sName, String tName) {

		/* Mark of the nodes in the graph 
		 * - mark[i] is equal to +j if the node of index i can be reached by increasing the flow on arc ji;
		 * - mark[i] is equal to -j if the node of index i can be reached by decreasing the flow on arc ij;
		 * - mark[i] is equal to Integer.MAX_VALUE if the node is not marked.
		 */
		int[] mark = new int[g.n];

		/* Get the index of nodes s and t */
		int s = g.indexOf(sName);
		int t = g.indexOf(tName);
		
		/* Create a new graph with the same nodes than g, which corresponds to the current flow */
		Graph flow = new Graph(g.nodes);

		/* Get all the arcs of the graph */
		List<Edge> arcs = g.getArcs();

		// initialize the mark array
		for(int i=0;i<g.n;i++)
		    mark[i]=Integer.MAX_VALUE;
		// a flux map to save flux in each arc
        HashMap<String,Double> flux = new HashMap<String,Double>();
        String k; // key in the HashMap
        for(Edge e: arcs)
        {
            k=g.nodes[e.id1]+"-"+g.nodes[e.id2];//"a-b"
            flux.put(k,0.0);// all flux initialized with 0
        }

        double restFlux=0;
        double sumflux=0;// total flux finally
        double maxflux=100;// max flux for each path from s to t
        List<Double> flux_list=new ArrayList<>();// save the flux of each arc for calculate the max flux
        int temp=s;// current marking node
        boolean update=false; // check if it enters a leaf node.

        // assume that there are maximum 100 sub trees
        for(int j=0;j<100;j++)
        {
            update=false; // assume that it is in a leaf node
            for(Edge e1: arcs){
                // cas1: mark +i
                if(e1.id1==temp && mark[e1.id2]==Integer.MAX_VALUE){
                    k=g.nodes[e1.id1]+"-"+g.nodes[e1.id2];
                    restFlux=e1.weight-flux.get(k);// the max flux equals to the difference between the capacity and existed flux.

                    if(restFlux>0)
                    {
                        mark[temp]=e1.id2;
                        temp=e1.id2; // move the current marking node to the next
                        flux_list.add(restFlux);
                        update=true; // it is not a leaf node in current graph
                    }
                }
                // cas2: mark -j
                else if(e1.id2==temp && mark[e1.id1]==Integer.MAX_VALUE){
                    k=g.nodes[e1.id1]+"-"+g.nodes[e1.id2];
                    restFlux=flux.get(k);// the max flux equals to the existed flux

                    if(restFlux>0) {
                        temp = e1.id1;
                        mark[e1.id2] = -e1.id1;
                        flux_list.add(restFlux);
                        update=true;
                    }
                }
                if(temp==t)
                {
                    maxflux= Collections.min(flux_list); // calculate the max flux in current path
                    sumflux+=maxflux; // sum the maxflux
                    flux_list.clear();

                    // update flux
                    for(int i=0;i<g.n;i++)
                    {
                        if(mark[i]!=Integer.MAX_VALUE)
                        {
                            // in case +
                            if (mark[i]>0)
                            {
                                k=g.nodes[i]+"-"+g.nodes[mark[i]];
                                if(flux.containsKey(k))
                                {
                                    System.out.print(k+",");
                                    flux.replace(k,maxflux+flux.get(k));
                                }
                            }
                            // in case -
                            else
                            {
                                k=g.nodes[-mark[i]]+"-"+g.nodes[i];
                                if(flux.containsKey(k))
                                {
                                    System.out.print(k+",");
                                    flux.replace(k,flux.get(k)-maxflux);
                                }

                            }
                        }
                    }
                    System.out.print("--maxflux="+maxflux);
                    // reinitialize mark list and starting points
                    for(int i=0;i<g.n;i++)
                        mark[i]=Integer.MAX_VALUE;
                    temp=s;
                    update=true;
                    System.out.print("-----flux="+flux);
                    System.out.println();
                    break;
                }

            }
            if(!update) // if it is a leaf node
            {
                for(int ind=0;ind<g.n;ind++)
                    if(Math.abs(mark[ind])==temp)
                    {
                        //pop the stack and return the previous node
//                        System.out.println("remove:"+g.nodes[temp]);
                        mark[temp]=temp;
                        temp=ind;
                        flux_list.remove(flux_list.size()-1);// remove the corresponding flux
                    }
            }
        }
        // construct the final graph
        for(String e:flux.keySet())
        {
            String ids[]=e.split("-");
            flow.addArc(g.indexOf(ids[0]),g.indexOf(ids[1]),flux.get(e));
        }
        System.out.println();
        System.out.println("-----max flux and min cut is "+sumflux);
		return flow;
	}
}
