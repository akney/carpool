package digraphs;

import java.util.Iterator;

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
	private int placeholder; // how many elements are currently stored in the
								// array

	public Digraph2(int size) {
		masterList = new LinkedList[size];
		for (int i = 0; i < size; i++) {
			masterList[i] = new LinkedList<VirtualEdge>();
		}
		placeholder = 0;
	}

	/**
	 * 
	 * @param name
	 *            name of the vertex
	 * @return success
	 */
	public boolean addEdge(String name) {
		if (placeholder < masterList.length) {
			if (getNode(name) == null) {

				masterList[placeholder++].add(new VirtualEdge(name), 0);
				return true;
			}
		}
		
		return false;

	}
	
	public boolean setStartingNode(String name)
	{
		for(int i = 0; i < placeholder; i++)
		{
			if (masterList[i].getFirst().getName().equalsIgnoreCase(name))
			{
				LinkedList<VirtualEdge> temp = masterList[i];
				masterList[i] = masterList[0];
				masterList[0] = temp;
				return true;
			}
		}
		return false;
	}
	
	private DigraphNode getStartingNode()
	{
		return masterList[0].getFirst().getPoint1();
	}

	/**
	 * 
	 * @param coordinate1
	 *            name of "from" vertex
	 * @param coordinate2
	 *            name of "to" vertex
	 * @param weight
	 *            of the path between the 2 vertices
	 * @return success
	 *//*
	public boolean addEdge(String coordinate1, String coordinate2, int weight) {
		VirtualEdge node1 = getNode(coordinate1);
		VirtualEdge node2 = getNode(coordinate2);
		if (node1 != null && node2 != null) {
			for (int ix = 0; ix < placeholder; ix++) {
				if (masterList[ix].getFirst().equals(node1)) {
					masterList[ix].add(node2, weight);

					return true;
				}
			}
		}

		return false;
	}*/

	/**
	 * 
	 * @param name
	 *            name of node to which we want a reference
	 * @return the node or null if unsuccessful
	 */
	private DigraphNode getNode(String name) {
		for (int ix = 0; ix < placeholder; ix++) {
			if (!masterList[ix].isEmpty()) {

				if (masterList[ix].getFirst().getName().equals(name)) {
					return masterList[ix].getFirst();
				}
			}
		}
		return null;
	}

	/**
	 * find the shortest path starting from the first node that was inserted to
	 * every other node. Step 1: update all vertices currently accessible and
	 * their distances Step 2: find the closest vertex and visit it repeat until
	 * all are visited or unvisitable (assign each node a string to represent
	 * its shortest path)
	 */
	public void PrimsAlgorithm() {

		int possibleDistance; // holds the distance from starting node to
								// current node and checks against its current
								// shortest distance to see if its any shorter
		//VirtualEdge currDigraphNode; // digraph node we are currently looking at
		DigraphNode currPoint = getStartingNode();
		
		Integer currLLNodeWeight; // linkedlist node of the
												// current digraph node
		Integer shortestDistance; // to hold the distance of the closest
									// unvisited (but visitable) node
		DigraphNode pointToCheck; // to hold the node as we iterate
										// through them to determine the closest
										// one

		// visit the starting node.
		//VirtualEdge currStartingDigraphNode = masterList[0].getFirst();
		currPoint.setShortestDistance(0);// going to itself
		currPoint.setShortestPath("");
		currPoint.setVisited(true);

		int numVisited = 1;// keeps track of how many places were visited
		int incrementor;
		while (numVisited < placeholder) {
			int i;
			// find where currStartingNode is in the array
			for (i = 0; i < placeholder; i++) {
				if (masterList[i].getFirst().getPoint1().equals(currPoint))
					break;
			}

			//Step 1:
			Iterator<VirtualEdge> iter = masterList[i].iterator();
			//LinkedListNodeIterator<VirtualEdge> nodeIter = masterList[i].nodeIterator();
			while (iter.hasNext()) {// itearte through all the places we can
									// reach from the node at which we are
									// currently starting
				currVirtualEdge = iter.next();
				currEdgeWeight = currVirtualEdge.getDistance();
				currPoint = currVirtualEdge.getPoint2();
				
				if (!currPoint.isVisited()) {
					possibleDistance = currEdgeWeight;
					
					// if the node we're looking at doesn't have a shortest
					// distance or has one that's greater than the distance we
					// calculated,
					// set the shortestpath field of that digraph node (which
					// may be referenced from multiple linkedlist nodes)
					if (currPoint.getShortestDistance() == null
							|| currPoint.getShortestDistance() > possibleDistance) {

						currPoint.setShortestDistance(possibleDistance);
						currPoint.setShortestPath(currVirtualEdge.getPoint2().getName());
					}
				}
			} // now we have labeled all the distances possible to get to
				// currently.
				
				// Step 2: now we need to actually visit the closest node.

			// assume the first distance that's filled in is the shortest
			incrementor = 0;
			shortestDistance = null;
			currPoint = null;
			do {
				if (!masterList[++incrementor].getFirst().getPoint1().isVisited()) {
					shortestDistance = masterList[incrementor].getFirst().getPoint1().getShortestDistance();
					currPoint = masterList[incrementor].getFirst();
				}

			} while (shortestDistance == null && incrementor < placeholder - 1);

			// there are no more places to go
			if (shortestDistance == null) {
				return;
			}

			// loop through the array to find the closest unvisited (but
			// visitable) node

			for (int i1 = 0; i1 < placeholder; i1++) {
				pointToCheck = masterList[i1].getFirst().getPoint1();

				// it hasn't been visited but actually has a path to be visited
				if (!pointToCheck.isVisited() && pointToCheck.getShortestDistance() != null) {
					// if there's a shorter distance than what's currently
					// stored (first available distance)
					if (shortestDistance > pointToCheck.getShortestDistance()) {
						shortestDistance = pointToCheck.getShortestDistance();
						currPoint = pointToCheck;
					}
				}
			}

			// now we've found the closest node. visit it.
			// deal with this being set at the end when no other shortest path
			// was found
			currPoint.setVisited(true);
			numVisited++;

		}

	}

	/**
	 * 
	 * @return String of each destination and its shortest path
	 */
	/*public String displayShortestPaths() {
		StringBuilder info = new StringBuilder();
		for (int i = 0; i < placeholder; i++) {
			if (!masterList[i].isEmpty())
				info.append(String.format("%-20s", masterList[i].getFirst().getName() + ":") + (masterList[i].getFirst().getShortestDistance() == null ? " No Path\n" : 
					"(Distance: "+ masterList[i].getFirst().getShortestDistance() +")"
						+ " Path: " + masterList[i].getFirst().getShortestPathFromStart() + "\n"));
		}

		return info.toString();
	}*/

}
