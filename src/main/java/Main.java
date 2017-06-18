import java.io.IOException;
import java.util.Scanner;

import com.google.maps.errors.ApiException;

import digraph.Digraph2;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		try {
			System.out.println(
					"Instructions: Please enter all addresses separated by semicolons.  \nMake sure to list the starting location first and the final destination last. Press enter when all locations have been entered.");

			String locs = input.nextLine();

			String[] locations = locs.split(";");

			Digraph2 graph = new Digraph2(locations);

			// find shortest route from starting point to final destination
			// going
			// through all points
			graph.PrimsAlgorithm();

			System.out.println("Shortest Route: " + graph.displayLocationsInOrder());
		} catch (ApiException | InterruptedException | IOException e) {
			System.out.println("Sorry - there was a problem with our maps connection.");
		}

	}
}
