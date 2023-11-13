import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class UnoGameController  implements ActionListener {
    UnoGame gameModel;
    GameBoardFrame gameView;
    Player currentPlayer;



    public UnoGameController(UnoGame game, GameBoardFrame view) {
        super();
        this.gameModel = game;
        this.gameView = view;


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentPlayer = gameModel.getCurrentPlayer();
        String clickedButton = e.getActionCommand();
        ArrayList<Card> handCards = currentPlayer.getHand().getCards();

        if (clickedButton == "Draw Card") {
            gameModel.handleDrawCard(currentPlayer);
        }
        if (clickedButton == "Next Player") {
            currentPlayer = gameModel.getNextCurrentPlayer();
            gameView.nextPlayerButton(false);
            gameView.drawCardButton(true);

        }
        for (Card c : handCards) {
            if (clickedButton.equals(c.stringCard())) {
                if (c.getValue() == Card.Value.WILD) {
                    gameModel.handleWildCard(chooseColor(), currentPlayer, c);
                    break;
                }
                if (c.getValue() == Card.Value.WILD_DRAW_TWO_CARDS) {
                    gameModel.handleWildDrawTwoCards(chooseColor(), currentPlayer, c, gameModel.isClockwise());
                    break;
                }
                if (c.getValue() == Card.Value.SKIP && c.getColor() == gameModel.getTopCard().getColor()) {
                    gameModel.handleSkipCard(currentPlayer, c, gameModel.isClockwise());
                    break;
                }
                if (c.getValue() == Card.Value.REVERSE && c.getColor() == gameModel.getTopCard().getColor()) {
                    gameModel.handleReverseCard(currentPlayer, c, gameModel.isClockwise());
                    break;
                }
                if (gameModel.isValidUnoPlay(c)) {
                    gameModel.handleValidPlay(currentPlayer, c);
                    break;
                }
                else {
                    gameView.updateMessages("Invalid play");
                    gameView.drawCardButton(true);
                }
            }
        }
    }

    private static Card.Color chooseColor() {
        Card.Color[] possibleColors = Card.Color.values();
        String[] colorNames = new String[possibleColors.length];

        for (int i = 0; i < possibleColors.length; i++) {
            colorNames[i] = possibleColors[i].name();
        }

        String chosenColorName = null;

        while (true) {
            chosenColorName = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose a color:",
                    "Color Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    colorNames,
                    colorNames[0]);

            // Check if a valid color was chosen
            if (chosenColorName != null) {
                try {
                    return Card.Color.valueOf(chosenColorName);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Invalid selection. Please choose a color.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You must choose a color.");
            }
        }
    }


}
