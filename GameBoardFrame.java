import javax.swing.*;
import java.awt.*;


public class GameBoardFrame extends JFrame{
    private JButton drawButton;
    private JButton nextPlayerButton;
    private JPanel playerHandPanel;
    private JLabel topCardLabel;
    private JLabel currentPlayerLabel;
    private UnoGame gameModel;

    public GameBoardFrame(UnoGame game) {
        gameModel = game;
        setTitle("Uno Game");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main panel with BorderLayout to organize sub-panels
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Player hand panel
        playerHandPanel = new JPanel(new FlowLayout());
        mainPanel.add(playerHandPanel, BorderLayout.CENTER);

        // Top card and draw pile
        JPanel topCardPanel = new JPanel();
        topCardLabel = new JLabel();
        topCardPanel.add(topCardLabel);
        mainPanel.add(topCardPanel, BorderLayout.NORTH);

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout());
        drawButton = new JButton("Draw Card");
        drawButton.addActionListener(new UnoGameController(gameModel));
        actionPanel.add(drawButton);
        updateTopCardDisplay();

        // Initialize the current player label
        currentPlayerLabel = new JLabel();
        mainPanel.add(currentPlayerLabel, BorderLayout.NORTH);
        updateCurrentPlayerDisplay();


        nextPlayerButton = new JButton("Next Player");
        nextPlayerButton.addActionListener(new UnoGameController(gameModel));
        actionPanel.add(nextPlayerButton);

        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);
        // update the panel with player cards
        updatePlayerHandPanel();
    }



    // Update the view components as necessary
    public void updateView() {
        // Update player's hand, top card, etc.
    }

    public void updatePlayerHandPanel() {
        playerHandPanel.removeAll(); // Clear the existing cards (buttons)

        // Get the current player's hand
        Hand currentHand = gameModel.getCurrentPlayer().getHand();

        for (Card card : currentHand.getCards()) {
            // For each card, create a button and set the text to the card's string representation
            JButton cardButton = new JButton(card.stringCard());
            cardButton.addActionListener(new UnoGameController(gameModel));

            // Optional: Customize the button with colors, images, etc.
            //customizeCardButton(cardButton, card);

            // Add the button to the player hand panel
            playerHandPanel.add(cardButton);
        }

        // Refresh the panel to show the updated hand
        playerHandPanel.revalidate();
        playerHandPanel.repaint();
    }

    private void updateTopCardDisplay() {
        Card topCard = gameModel.getTopCard();
        String cardText = topCard.stringCard();
        topCardLabel.setText(cardText);

        // If you have card images, you would set the icon of the label instead
        // ImageIcon icon = new ImageIcon(getClass().getResource("/path/to/card/images/" + cardText + ".png"));
        // topCardLabel.setIcon(icon);
    }

    private void updateCurrentPlayerDisplay(){

        String playerName = gameModel.getCurrentPlayer().getName();
        currentPlayerLabel.setText("Current Player: " + playerName);
    }

    // Main method to start the game GUI
    public static void main(String[] args) {
        // Show input dialog to get the number of players
        String numPlayersStr = JOptionPane.showInputDialog(null,
                "How many players? (2-4)",
                "Number of Players",
                JOptionPane.QUESTION_MESSAGE);
        int numPlayers = 0;

        // Validate and parse the input
        try {
            numPlayers = Integer.parseInt(numPlayersStr);
            if (numPlayers < 2 || numPlayers > 4) {
                JOptionPane.showMessageDialog(null,
                        "Please choose a number between 2 and 4.",
                        "Invalid Number",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0); // Exit or repeat the process
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid input. Please enter a number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0); // Exit or repeat the process
        }

        // Start the game with the specified number of players
        UnoGame unoGame = new UnoGame(numPlayers);
        // Ideally, pass unoGame to the view
        GameBoardFrame view = new GameBoardFrame(unoGame);
        view.setVisible(true);


        // Start the game logic if needed
         unoGame.play();
    }

}
