package datastructures.finalproject.carpool;

import java.io.IOException;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.ZeroResultsException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodedWaypointStatus;

public class APIConnection {

	private GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAcK9f_4VyyN2Klzu3D_khJlgxacckz0jw");
	private DirectionsResult directions;

	public APIConnection(String origin, String destination) throws ApiException, InterruptedException, IOException {
		DirectionsApiRequest request = DirectionsApi.getDirections(context, origin, destination);
		directions = request.await();
		if (directions.geocodedWaypoints[0].geocoderStatus.equals(GeocodedWaypointStatus.ZERO_RESULTS)) {
			throw new ZeroResultsException("No results for this route, make sure your info is correct.");
		}

	}

	public double getDistance() throws ApiException, InterruptedException, IOException {
		return directions.routes[0].legs[0].distance.inMeters; // returns meters
	}
}
