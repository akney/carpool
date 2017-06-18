import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.google.maps.errors.ApiException;

import digraph.Digraph2;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		try {
			int choice = 0;
			do {

				choice = menu(input);

				switch (choice) {
				case 1:
					System.out.println(
							"Instructions: Please enter all addresses separated by semicolons.  \nMake sure to list the starting location first and the final destination last. Press enter when all locations have been entered.");

					String locs = input.nextLine();

					String[] locations = locs.split(";");

					Digraph2 graph = new Digraph2(locations);

					// find shortest route from starting point to final
					// destination
					// going
					// through all points
					graph.PrimsAlgorithm();

					System.out.println("Shortest Route: " + graph.displayLocationsInOrder());
					break;
				default:
					System.out.println("Good Bye!");
				}

			} while (choice != 2);
		} catch (ApiException | InterruptedException | IOException e) {
			System.out.println("Sorry - there was a problem with our maps connection.");
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input.");
		}

	}

	public static int menu(Scanner input) {
		System.out.println("\n1. Enter new locations" + "\n2. Exit");

		int choice = input.nextInt();

		while (choice < 1 || choice > 2) {
			System.out.println("Invalid Input.  Please choose a menu item.");
			choice = input.nextInt();
		}

		return choice;
	}
}
