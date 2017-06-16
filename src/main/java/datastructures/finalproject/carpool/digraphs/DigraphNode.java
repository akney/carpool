package digraphs;

public class DigraphNode implements Comparable<DigraphNode> {

	private String name;

	private String shortestPath;
	private Integer shortestDistance;	//distance of the shortest possible route to reach this vertex
	private boolean visited;

	public DigraphNode(String name) {

		this.name = name;
		this.visited = false;
		shortestDistance = null;
	}

	public String getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(String shortestPath) {
		this.shortestPath = shortestPath;
	}

	public Integer getShortestDistance() {
		return shortestDistance;
	}

	public void setShortestDistance(int shortestDistance) {
		this.shortestDistance = shortestDistance;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(DigraphNode d) {

		return d.getName().compareTo(name);

	}
}
