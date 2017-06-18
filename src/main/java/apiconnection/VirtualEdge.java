package apiconnection;

import com.google.maps.model.DirectionsResult;

import digraph.DigraphNode;

public class VirtualEdge {
	private DirectionsResult directions;
	// private String point1;
	// private String point2;
	private DigraphNode point1;
	private DigraphNode point2;
	private double distance;

	public VirtualEdge(DirectionsResult directions) {
		this.directions = directions;
		this.distance = directions.routes[0].legs[0].distance.inMeters; // returns
																		// meters
		this.point1 = new DigraphNode(directions.routes[0].legs[0].startAddress);
		this.point2 = new DigraphNode(directions.routes[0].legs[0].endAddress);
	}

	public DigraphNode getPoint1() {
		return point1;
	}

	public DigraphNode getPoint2() {
		return point2;
	}

	public double getDistance() {
		return distance;
	}

	public void setPoint1(DigraphNode point1) {
		this.point1 = point1;
	}

	public void setPoint2(DigraphNode point2) {
		this.point2 = point2;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
