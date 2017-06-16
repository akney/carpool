import java.util.ArrayList;
import java.util.LinkedList;

public class PrimsDigraph {

	private ArrayList<DigraphNode> vertices;
	private DigraphNode finalDestination;

	public PrimsDigraph(ArrayList<DigraphNode> vertices, String finalAddress) {
		// list of vertices are passed in from main
		// the starting point is first, and the final Destination is not
		// included
		this.vertices = vertices;
		// generates shortest paths from each node to every other node
		generatePaths();

		finalDestination = new DigraphNode(finalAddress);
	}

	/**
	 * Instantiates a new LinkedList orderedList to store the order in which the
	 * locations should be visited to achieve the shortest route. (note -
	 * results will not be exact, since the final destination is not taken into
	 * account during this calculation. However delays, if any, will be
	 * minimal.)
	 * 
	 * The starting point is set to first. until all locations have been visited
	 * the algorithm checks the last node in the LL and looks for the shortest
	 * path to an unvisited node, and sets that as the next location in
	 * orderedList.
	 * 
	 * At the end the final destination is added to the end of the list.
	 * 
	 * 
	 * @return
	 */
	private LinkedList<DigraphNode> primsAlgorithm() {
		LinkedList<DigraphNode> orderedList = new LinkedList<DigraphNode>();
		DigraphNode currNode, toNode, tmpNode;
		double distance;

		// get vertex of next destination with address
		tmpNode = retrieveVertex(vertices.get(0).getOutGoingEdges().get(0).getDestination());

		// set starting point as first location
		orderedList.add(vertices.get(0));

		for (int ix = 0; ix < vertices.size(); ix++) {
			// perform algo
			currNode = orderedList.getLast();
			distance = 0;

			for (AtarasEdge e : currNode.getOutGoingEdges()) {

				tmpNode = retrieveVertex(e.getDestination());
				if (!tmpNode.isVisited()) {
					if (distance == 0) {
						toNode = tmpNode;
					} else if (e.getDistance() < distance) {
						toNode = tmpNode;
						distance = e.getDistance();
					}
				}
			}

			orderedList.add(toNode);
			toNode.markAsVisited();

		}

		// tack final destination on to the end
		orderedList.add(finalDestination);

		return orderedList;
	}

	private DigraphNode retrieveVertex(String address) throws VertexNotFoundException2 {
		for (DigraphNode n : vertices) {
			if (n.address.compareTo(address) == 0) {
				return n;
			}
		}
		throw new VertexNotFoundException2();
	}

	/**
	 * generates the shortest path from each node to every other node, excluding
	 * the final destination. stores each edge in the origin node's field - a LL
	 * of edges
	 */
	private void generatePaths() {
		for (DigraphNode fromNode : vertices) {
			for (DigraphNode toNode : vertices) {
				fromNode.addOutGoingEdge(APIConnection.getEdge(fromNode.getAddress(), toNode.getAddress()));
			}
		}
	}

	// additional classes
	class DigraphNode {

		private String address;
		private LinkedList<AtarasEdge> outGoingEdges;
		private boolean visited = false;

		public DigraphNode(String address) {
			this.address = address;
			outGoingEdges = new LinkedList<AtarasEdge>();
		}

		private void addOutGoingEdge(AtarasEdge edge) {
			if (!outGoingEdges.contains(edge)) {
				outGoingEdges.add(edge);
			}
		}

		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (obj == this) {
				return true;
			}

			if (obj instanceof DigraphNode) {
				DigraphNode other = (DigraphNode) obj;

				return this.address.equals(other.address);
			} else {
				return false;
			}
		}

		public String getAddress() {
			return address;
		}

		private LinkedList<AtarasEdge> getOutGoingEdges() {
			return outGoingEdges;
		}

		private void markAsVisited() {
			visited = true;
		}

		private boolean isVisited() {
			return visited;
		}
	}

	class AtarasEdge {

		private String getDestination() {
		}

		private double getDistance() {
		}
	}

}
