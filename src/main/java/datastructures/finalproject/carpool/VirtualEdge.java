package datastructures.finalproject.carpool;

import com.google.maps.model.DirectionsResult;

public class VirtualEdge {
	private DirectionsResult directions;
	private String point1;
	private String point2;
	private double distance;

	public VirtualEdge(DirectionsResult directions) {
		this.directions = directions;
		this.distance = directions.routes[0].legs[0].distance.inMeters; // returns meters
		this.point1 = directions.routes[0].legs[0].startAddress;
		this.point2 = directions.routes[0].legs[0].endAddress;
	}

	public String getPoint1() {
		return point1;
	}

	public String getPoint2() {
		return point2;
	}

	public double getDistance() {
		return distance;
	}

	public void setPoint1(String point1) {
		this.point1 = point1;
	}

	public void setPoint2(String point2) {
		this.point2 = point2;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
