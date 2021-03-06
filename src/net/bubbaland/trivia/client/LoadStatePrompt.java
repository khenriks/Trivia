package net.bubbaland.trivia.client;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.rmi.RemoteException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import net.bubbaland.trivia.TriviaInterface;

/**
 * Creates a dialog that lists the saves and prompts for one to load.
 * 
 * @author Walter Kolczynski
 * 
 */
public class LoadStatePrompt extends JPanel {

	private static final long	serialVersionUID	= -3297076605620744620L;
	private static final float	FONT_SIZE			= 20f;

	public LoadStatePrompt(TriviaInterface server, TriviaClient client) {

		super(new GridBagLayout());

		// Try to communicate with server
		String[] saveList = null;
		int tryNumber = 0;
		boolean success = false;
		while (tryNumber < TriviaClient.MAX_RETRIES && success == false) {
			tryNumber++;
			try {
				// Set the announced values
				saveList = server.listSaves();
				success = true;
			} catch (final RemoteException e) {
				// Retry if the connection is broken
				client.log("Couldn't get list of save from server (try #" + tryNumber + ").");
			}
		}

		if (!success) {
			// Couldn't retrieve data from server, create a disconnected dialog box
			client.disconnected();
			return;
		}

		// Set up base layout constraints
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;


		// Add warning
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		JLabel label = new JLabel("Warning: This will overwrite all current data.");
		label.setFont(label.getFont().deriveFont(FONT_SIZE));
		label.setForeground(Color.RED);
		this.add(label, constraints);
		constraints.gridwidth = 1;

		// Instruction label
		constraints.gridx = 0;
		constraints.gridy = 1;
		label = new JLabel("Choose file to load:");
		label.setFont(label.getFont().deriveFont(FONT_SIZE));
		this.add(label, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		JComboBox<String> chooser = new JComboBox<String>(saveList);
		this.add(chooser, constraints);

		// Create dialog box with OK and CANCEL buttons
		final JOptionPane pane = new JOptionPane(this, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		final JDialog dialog = pane.createDialog(this.getParent(), "Load saved state");
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// Retrieve the button pressed
		final int option = ( (Integer) pane.getValue() ).intValue();

		if (option == JOptionPane.OK_OPTION) {
			String saveFile = (String) chooser.getSelectedItem();

			// Try to communicate with server
			tryNumber = 0;
			success = false;
			while (tryNumber < TriviaClient.MAX_RETRIES && success == false) {
				tryNumber++;
				try {
					// Set the announced values
					server.loadState(saveFile);
					success = true;
				} catch (final RemoteException e) {
					// Retry if the connection is broken
					client.log("Couldn't load save file on server (try #" + tryNumber + ").");
				}
			}

			if (!success) {
				// Couldn't retrieve data from server, create a disconnected dialog box
				client.disconnected();
				return;
			}

		}
	}

}
