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

	public static VirtualEdge createVirtualEdge(String origin, String destination)
			throws ApiException, InterruptedException, IOException {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAcK9f_4VyyN2Klzu3D_khJlgxacckz0jw");

		DirectionsResult directions;
		DirectionsApiRequest request = DirectionsApi.getDirections(context, origin, destination);
		directions = request.await();

		VirtualEdge vedge = new VirtualEdge(directions);

		if (directions.geocodedWaypoints[0].geocoderStatus.equals(GeocodedWaypointStatus.ZERO_RESULTS)) {
			throw new ZeroResultsException("No results for this route, make sure your info is correct.");
		}

		return vedge;

	}
}
