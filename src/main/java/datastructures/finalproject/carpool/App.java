package datastructures.finalproject.carpool;

import java.io.IOException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAcK9f_4VyyN2Klzu3D_khJlgxacckz0jw");
		GeocodingResult[] results;
		try {
			results = GeocodingApi.geocode(context, "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
			System.out.println(results[0].formattedAddress);

			APIConnection ac = new APIConnection("Brooklyn", "Queens");
			ac = new APIConnection("Brooklyn", "Bronx");
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
