package net.bubbaland.trivia.client;

import java.awt.*;

import net.bubbaland.trivia.TriviaInterface;

/**
 * Panel for the current round data.
 */
public class RoundPanel extends TriviaPanel {

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 9190017804155701978L;
	
	// Sub-panels of the round panel
	private final HeaderPanel			roundHeaderPanel;
	private final RoundQlistPanel		roundQlistPanel;

	/**
	 * Instantiates a new round panel.
	 *
	 * @param server the server
	 * @param client the client application
	 */
	public RoundPanel( TriviaInterface server, TriviaClient client ) {

		super( new GridBagLayout() );
		
		// Create the sub-panels
		roundHeaderPanel = new HeaderPanel( server, client );
		roundQlistPanel = new RoundQlistPanel( server, client );

		// Set up layout constraints
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;	constraints.weighty = 0.0;

		// Place the sub-panels
		constraints.gridx = 0;	constraints.gridy = 0;
		this.add( roundHeaderPanel, constraints );
		
		constraints.gridx = 0;	constraints.gridy = 1;
		constraints.weighty = 1.0;
		this.add( roundQlistPanel, constraints );

	}

	/* (non-Javadoc)
	 * @see net.bubbaland.trivia.TriviaPanel#update()
	 */
	@Override
	public synchronized void update() {
		this.roundHeaderPanel.update();
		this.roundQlistPanel.update();
	}

}
