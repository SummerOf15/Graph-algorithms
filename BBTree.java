/**
 * Class which represents the tree of and branch-and-bound algorithm
 */
public class BBTree {

	/** Best integer solution found (null if none has currently been found) */
	double[] bestSolution = null;

	/** Value of the objective of the best integer found */
	double bestObjective = 0.0;

	/** Root node of the tree */
	BBNode root;

	/** Create a tree with a root */
	public BBTree(BBNode root) {
		this.root = root;
	}

	/** Use a branch-and-bound algorithm to solve the root probleme */ 
	public void solve() {
		root.branch(this);
	}

	public static void main(String[] args) {

		BBTree tree;
		tree = BBTree.ex1();
		tree.solve();
		tree.displaySolution();
		
	}

	public static BBTree ex1() {

		double[][] mA = new double[][] {
			{-2, 2},
			{2, 3},
			{9, -2}
		};

		double[] rhs = new double[] {7, 18, 36};
		double[] obj = new double[] {3, 2};
		boolean isMinimization = false;

		BBNode root = new BBNode(mA, rhs, obj, isMinimization);
		return new BBTree(root);
	}

	/** Display the tree optimal solution */
	public void displaySolution() {
		
		if(bestSolution == null)
			System.out.println("No feasible integer solution");
		else {

			System.out.print("Optimal solution: z = " + Utility.nf.format(bestObjective) + ", ");

			String variables = "(";
			String values = "(";
			
			/* For each variable */
			for(int i = 0; i < bestSolution.length; i++)
				
				/* If the variable is not null in the solution */
				if(bestSolution[i] != 0.0) {
					
					/* Display it */
					variables += "x" + (i+1) + ", ";
					values += (int)(bestSolution[i]) + ", ";
				}

			/* Remove the last ", " at the end of variables and values */ 
			variables = variables.substring(0, Math.max(0, variables.length() - 2));
			values = values.substring(0, Math.max(0, values.length() - 2));
			
			System.out.println(variables + ") = " + values + ")");
		}
	}
}
