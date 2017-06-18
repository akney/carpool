import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.google.maps.errors.ApiException;

import digraph.Digraph2;

public class InputFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	JLabel instructions1 = new JLabel("Instructions: Please enter all addresses separated by semicolons.");
	JLabel instructions2 = new JLabel(
			"Make sure to list the starting location first and the final destination last. Press calculate when all locations have been entered.");
	JTextArea input = new JTextArea(5, 20);
	JScrollPane scroll1 = new JScrollPane(input, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JButton calculate = new JButton("Calculate");
	JLabel replyLabel = new JLabel("Shortest Route: ");
	JTextArea reply = new JTextArea(5, 20);
	JScrollPane scroll2 = new JScrollPane(reply, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	public InputFrame() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 600);
		this.setTitle("QuickPool - The fastest way to do carpool!");
		this.setVisible(true);

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		add(instructions1);
		add(instructions2);

		add(scroll1);
		input.setWrapStyleWord(true);
		input.setLineWrap(true);
		add(calculate);
		add(replyLabel);
		reply.setEditable(false);
		add(scroll2);

		calculate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String locs = input.getText();
				if (!locs.trim().equals("")) {
					String[] locations = locs.split(";");

					Digraph2 graph;
					try {
						graph = new Digraph2(locations);
						// find shortest route from starting point to final
						// destination
						// going
						// through all points
						graph.orderStops();

						reply.setText(graph.displayLocationsInOrder());
					} catch (ApiException | InterruptedException | IOException e) {
						JOptionPane.showMessageDialog(null, "Sorry - there was a problem with our maps connection.");
						e.printStackTrace();
					}
				}

			}

		});

	}

}
