package digraph;

import java.io.IOException;
import java.util.LinkedList;

import com.google.maps.errors.ApiException;

import apiconnection.APIConnection;
import apiconnection.VirtualEdge;

public class Digraph2 {

	private LinkedList<VirtualEdge>[] masterList; // first node of each
													// linkedlist element in
													// each masterlist index
													// will be the
													// "from" node. Nodes that
													// can be reached from it
													// will be the next elements
													// in the linked list.
													// the weight of the
													// subsequent node from the
													// "from" node is stored in
													// a field
													// of the linked list node
													// (in addition to the field
													// of the linked list node
													// that is a reference to
													// the digraph node).

	int placeholder = 0; // number of lists instantiated in masterList
	LinkedList<DigraphNode> stops;

	public Digraph2(String[] locations) throws ApiException, InterruptedException, IOException {
		masterList = new LinkedList[locations.length];
		populateGraph(locations);
	}

	/**
	 * 
	 * @param name
	 *            name of the vertex
	 * @return success
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ApiException
	 */
	private void populateGraph(String[] locations) throws ApiException, InterruptedException, IOException {
		int ix = 0;
		// setting up from nodes (first node in each linked list)
		for (String s : locations) {
			LinkedList<VirtualEdge> tmpEdgeList = new LinkedList<VirtualEdge>();
			VirtualEdge e = APIConnection.createVirtualEdge(s, s);
			e.setPoint1(getNode(e.getFromAddress()));
			e.setPoint2(e.getPoint1());
			tmpEdgeList.add(e);

			masterList[ix++] = tmpEdgeList;
			placeholder++;
		}

		// get edges of all points going to all points
		// excluding those that go to the starting point (first member of
		// locations array)
		// and those that go to the final destination
		for (int i = 0; i < locations.length - 1; i++) {
			for (int j = 1; j < locations.length - 1; j++) {
				if (i != j) { // don't need edges going from point to self
					VirtualEdge e = APIConnection.createVirtualEdge(locations[i], locations[j]);
					e.setPoint1(getNode(e.getFromAddress()));
					e.setPoint2(getNode(e.getToAddress()));
					masterList[i].add(e);
				}
			}

		}
	}

	public boolean setStartingNode(String name) {
		for (int i = 0; i < masterList.length; i++) {
			if (masterList[i].getFirst().getPoint1().getName().equalsIgnoreCase(name)) {
				LinkedList<VirtualEdge> temp = masterList[i];
				masterList[i] = masterList[0];
				masterList[0] = temp;
				return true;
			}
		}
		return false;
	}

	private DigraphNode getStartingNode() {
		return masterList[0].getFirst().getPoint1();
	}

	/**
	 * 
	 * @param name
	 *            name of node to which we want a reference
	 * @return the node or new node if not previously in graph
	 */
	private DigraphNode getNode(String name) {
		for (int ix = 0; ix < placeholder; ix++) {
			if (!masterList[ix].isEmpty()) {
				if (masterList[ix].getFirst().getPoint1().getName().equals(name)) {
					return masterList[ix].getFirst().getPoint1();
				}
			}
		}
		return new DigraphNode(name);
	}

	/**
	 * find the shortest path starting from the first node that was inserted through all other nodes to the final destination.
	 */
	public void orderStops() {
		this.stops = new LinkedList<DigraphNode>();
		int ix2 = 0, numStops = 0;
		double shortest;
		// add origin node to the beginning of the list of stops
		DigraphNode destination = masterList[0].getFirst().getPoint1();
		stops.add(getNode(destination.getName()));
		numStops++;

		while (numStops < masterList.length - 1) {
			for (int ix = 0; ix < masterList.length - 1; ix++) {
				if (masterList[ix].getFirst().getPoint1().compareTo(stops.getLast()) == 0) {
					ix2 = 1;
					// set first unvisited value to base shortest path
					while (true) {

						if (!stops.contains(masterList[ix].get(ix2).getPoint2())) {
							destination = masterList[ix].get(ix2).getPoint2();
							shortest = masterList[ix].get(ix2).getDistance();
							break;
						}
						ix2++;
					}

					// check for shorter paths
					for (VirtualEdge e : masterList[ix]) {
						if (!stops.contains(e.getPoint2()) && e.getDistance() < shortest) {
							destination = e.getPoint2();
							shortest = e.getDistance();
						}
					}

					stops.add(getNode(destination.getName()));
					numStops++;
					break;
				}
			}
		}

		// add final destination to the end of the list
		stops.add(masterList[masterList.length - 1].getFirst().getPoint1());

	}

	/**
	 * 
	 * @return String of All destinations in order of shortest route from origin to final destination through all other points
	 * 
	 */

	public String displayLocationsInOrder() {
		StringBuilder info = new StringBuilder();
		for (DigraphNode n : stops) {

			info.append(String.format("\n%-20s", n.getName()));

		}
		return info.toString();
	}

}
