import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the Uno card game and manages the game flow.
 * @version 1.0
 */
public class UnoGame {
    private List<Player> players; // List of players in the game
    private Card topCard;         // The current top card on the deck
    private int currentPlayerIndex; // Index of the current player
    boolean clockwise = true;     // Direction of play (clockwise or counterclockwise)

    /**
     * Constructs an UnoGame with the specified number of players.
     *
     * @param numPlayers The number of players in the game (2 to 4).
     */
    public UnoGame(int numPlayers) {
        players = new ArrayList<>();
        initializePlayers(numPlayers);
        currentPlayerIndex = 0;
        topCard = Card.generate_top_card();
    }

    /**
     * Initializes the players by collecting their names from the user.
     *
     * @param numPlayers The number of players in the game.
     */
    private void initializePlayers(int numPlayers) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String playerName = scanner.nextLine();
            Player player = new Player(playerName);
            players.add(player);
        }
    }

    /**
     * Displays the current player's hand.
     *
     * @param currentPlayer The current player.
     */
    private void displayCurrentPlayerHand(Player currentPlayer) {
        System.out.print("Player " + currentPlayer.getName() + "'s cards: \n");
        List<Card> hand = currentPlayer.getHand().getCards();
        for (int j = 0; j < hand.size(); j++) {
            Card card = hand.get(j);
            System.out.print((j + 1) + "- " + card.stringCard());
            if (j < hand.size() - 1) {
                System.out.print(",\n");
            }
        }
        System.out.println();
    }

    /**
     * Checks if a card is a valid play in the Uno game.
     *
     * @param card The card to check.
     * @return True if the card can be played; otherwise, false.
     */
    private boolean isValidUnoPlay(Card card) {
        return card.getColor() == topCard.getColor() || card.getValue() == topCard.getValue();
    }

    /**
     * Calculates the score for the winning player based on opponents' cards.
     *
     * @param winningPlayer The player who won the round.
     */
    private void calculateScoreForWinningPlayer(Player winningPlayer) {
        int finalScore = 0;

        // Iterate through the remaining players' hands
        for (Player opponent : players) {
            if (opponent != winningPlayer) {
                for (Card card : opponent.getHand().getCards()) {
                    Card.Value value = card.getValue();

                    if (value == Card.Value.REVERSE || value == Card.Value.SKIP) {
                        finalScore += 20;
                    } else if (value == Card.Value.WILD) {
                        finalScore += 40;
                    } else if (value == Card.Value.WILD_DRAW_TWO_CARDS) {
                        finalScore += 50;
                    } else {
                        finalScore += value.ordinal();
                    }
                }
            }
        }

        winningPlayer.updateScore(finalScore);
        System.out.println("Player " + winningPlayer.getName() + " scored " + finalScore + " points from opponents' cards.");
    }

    /**
     * Starts the Uno game and manages the game flow.
     */
    public void play() {
        boolean gameRunning = true;
        boolean go_next = true;
        Scanner scanner = new Scanner(System.in);

        while (gameRunning) {
            Player currentPlayer = players.get(currentPlayerIndex);
            displayGameStatus(currentPlayer);

            int cardIndex = getPlayerInput(scanner, currentPlayer);

            if (cardIndex == 0) {
                handleDrawCard(currentPlayer);
                go_next = true;
            } else {
                go_next = handlePlayCard(scanner, currentPlayer, cardIndex, clockwise);
            }

            handleUnoStatus(scanner, currentPlayer);

            if (currentPlayer.getHand().getNumCards() == 0) {
                handleWinOrPenalty(currentPlayer);
                gameRunning = currentPlayer.getHand().getNumCards() != 0;
            }

            if (go_next) {
                currentPlayerIndex = (currentPlayerIndex + (clockwise ? 1 : -1) + players.size()) % players.size();
            }
        }
    }

    /**
     * Displays the game status, including the top card and the current player's hand.
     *
     * @param currentPlayer The current player.
     */
    private void displayGameStatus(Player currentPlayer) {
        System.out.println("Top Card: " + topCard.stringCard());
        System.out.println("Player " + currentPlayer.getName() + "'s turn");
        displayCurrentPlayerHand(currentPlayer);
    }

    /**
     * Gets the player's input for selecting a card to play or drawing a card.
     *
     * @param scanner       The input scanner.
     * @param currentPlayer  The current player.
     * @return The index of the selected card or 0 to draw a card.
     */
    private int getPlayerInput(Scanner scanner, Player currentPlayer) {
        int cardIndex;
        while (true) {
            System.out.println("Enter card index to play (1 to " + currentPlayer.getHand().getNumCards() + ") or 0 to draw a card:");
            if (scanner.hasNextInt()) {
                cardIndex = scanner.nextInt();
                if (cardIndex >= 0 && cardIndex <= currentPlayer.getHand().getNumCards()) {
                    return cardIndex;
                } else {
                    System.out.println("Invalid Index for Hand, Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Handles drawing a card from the deck.
     *
     * @param currentPlayer The current player who is drawing a card.
     */
    private void handleDrawCard(Player currentPlayer) {
        Card drawnCard = currentPlayer.getHand().addCard();
        System.out.println("Player " + currentPlayer.getName() + " drew a card: " + drawnCard.stringCard());
    }

    /**
     * Handles playing a card from the player's hand.
     *
     * @param scanner       The input scanner.
     * @param currentPlayer  The current player.
     * @param cardIndex     The index of the card to play.
     * @param clockwise     The direction of play (clockwise or counterclockwise).
     * @return True if the play was successful; otherwise, false.
     */
    private boolean handlePlayCard(Scanner scanner, Player currentPlayer, int cardIndex, boolean clockwise) {
        Card selectedCard = currentPlayer.getHand().getCards().get(cardIndex - 1);
        if (selectedCard.getValue() == Card.Value.WILD) {
            return handleWildCard(scanner, currentPlayer, selectedCard);
        } else if (selectedCard.getValue() == Card.Value.SKIP) {
            return handleSkipCard(currentPlayer, selectedCard, clockwise);
        } else if (selectedCard.getValue() == Card.Value.REVERSE) {
            return handleReverseCard(currentPlayer, selectedCard, clockwise);
        } else if (selectedCard.getValue() == Card.Value.WILD_DRAW_TWO_CARDS && selectedCard.getColor() == topCard.getColor()) {
            return handleWildDrawTwoCards(scanner, currentPlayer, selectedCard, clockwise);
        } else if (isValidUnoPlay(selectedCard)) {
            return handleValidPlay(currentPlayer, selectedCard);
        } else {
            System.out.println("Invalid play. Try again.");
            return false;
        }
    }

    /**
     * Handles Uno status and reminds the player to say Uno if applicable.
     *
     * @param scanner       The input scanner.
     * @param currentPlayer  The current player.
     */
    private void handleUnoStatus(Scanner scanner, Player currentPlayer) {
        if (currentPlayer.hasUno() && !currentPlayer.hasRemindedUno()) {
            System.out.println("Player " + currentPlayer.getName() + ", you have Uno! Don't forget to say it.");
            currentPlayer.setRemindedUno(true);
        }
        if (currentPlayer.getHand().getNumCards() == 1) {
            System.out.println("Type 'UNO' to say it: ");
            String unoInput = scanner.next().trim().toUpperCase();
            if (unoInput.equals("UNO")) {
                currentPlayer.sayUno();
            }
        }
    }

    /**
     * Handles the outcome when a player wins or fails to say Uno.
     *
     * @param currentPlayer The current player.
     */
    private void handleWinOrPenalty(Player currentPlayer) {
        if (currentPlayer.getUnoCalled()) {
            System.out.println("Player " + currentPlayer.getName() + " wins!");
            calculateScoreForWinningPlayer(currentPlayer);
        } else {
            System.out.println("Player " + currentPlayer.getName() + " did not say Uno and draws 2 cards.");
            currentPlayer.getHand().addCard();
            currentPlayer.getHand().addCard();
            currentPlayer.setRemindedUno(false);
        }
    }

    /**
     * Handles playing a Wild card and allows the player to choose the color.
     *
     * @param scanner       The input scanner.
     * @param currentPlayer  The current player.
     * @param selectedCard  The Wild card to play.
     * @return True if the play was successful; otherwise, false.
     */
    private boolean handleWildCard(Scanner scanner, Player currentPlayer, Card selectedCard) {
        String chosenColor = getChosenColor(scanner);
        System.out.println("Player " + currentPlayer.getName() + " plays: " + selectedCard.stringCard() + " color chosen: " + chosenColor);
        currentPlayer.playCard(selectedCard);
        topCard = new Card(Card.Color.valueOf(chosenColor), Card.Value.WILD);
        return true;
    }

    /**
     * Handles playing a Skip card. If the card matches the color of the top card,
     * it skips the next player's turn.
     *
     * @param currentPlayer The current player.
     * @param selectedCard The Skip card to play.
     * @param clockwise     The direction of play (clockwise or counterclockwise).
     * @return True if the play was successful; otherwise, false.
     */
    private boolean handleSkipCard(Player currentPlayer, Card selectedCard, boolean clockwise) {
        if (selectedCard.getColor() == topCard.getColor() || topCard.getValue() == Card.Value.SKIP) {
            System.out.println("Player " + currentPlayer.getName() + " plays: " + selectedCard.stringCard());
            currentPlayer.playCard(selectedCard);
            topCard = selectedCard;
            currentPlayerIndex = (currentPlayerIndex + (clockwise ? 1 : -1) + players.size()) % players.size();
            return true;
        } else {
            System.out.println("Invalid play. The card must match the color of the top card.");
            return false;
        }
    }

    /**
     * Handles playing a Reverse card. If the card matches the color of the top card,
     * it reverses the direction of play.
     *
     * @param currentPlayer The current player.
     * @param selectedCard The Reverse card to play.
     * @param clockwise     The direction of play (clockwise or counterclockwise).
     * @return True if the play was successful; otherwise, false.
     */
    private boolean handleReverseCard(Player currentPlayer, Card selectedCard, boolean clockwise) {
        if (selectedCard.getColor() == topCard.getColor() || topCard.getValue() == Card.Value.REVERSE) {
            System.out.println("Player " + currentPlayer.getName() + " plays: " + selectedCard.stringCard());
            currentPlayer.playCard(selectedCard);
            topCard = selectedCard;
            this.clockwise = !clockwise;
            return true;
        } else {
            System.out.println("Invalid play. The card must match the color of the top card.");
            return false;
        }
    }

    /**
     * Handles playing a Wild Draw Two Cards. If the card matches the color of the top card,
     * it forces the next player to draw two cards and skips their turn.
     *
     * @param scanner       The input scanner.
     * @param currentPlayer  The current player.
     * @param selectedCard  The Wild Draw Two Cards to play.
     * @param clockwise     The direction of play (clockwise or counterclockwise).
     * @return True if the play was successful; otherwise, false.
     */
    private boolean handleWildDrawTwoCards(Scanner scanner, Player currentPlayer, Card selectedCard, boolean clockwise) {
        String chosenColor = getChosenColor(scanner);
        System.out.println("Player " + currentPlayer.getName() + " plays: " + selectedCard.stringCard() + " color chosen: " + chosenColor);
        currentPlayer.playCard(selectedCard);
        topCard = new Card(Card.Color.valueOf(chosenColor), Card.Value.WILD_DRAW_TWO_CARDS);

        currentPlayerIndex = (currentPlayerIndex + (clockwise ? 1 : -1) + players.size()) % players.size();
        Player nextPlayer = players.get(currentPlayerIndex);
        nextPlayer.getHand().addCard();
        nextPlayer.getHand().addCard();
        return true;
    }

    /**
     * Handles a valid card play that matches the color or value of the top card.
     *
     * @param currentPlayer  The current player.
     * @param selectedCard  The card to play.
     * @return True if the play was successful; otherwise, false.
     */
    private boolean handleValidPlay(Player currentPlayer, Card selectedCard) {
        System.out.println("Player " + currentPlayer.getName() + " plays: " + selectedCard.stringCard());
        currentPlayer.playCard(selectedCard);
        topCard = selectedCard;
        return true;
    }

    /**
     * Gets the chosen color when playing a Wild card or Wild Draw Two Cards.
     *
     * @param scanner The input scanner.
     * @return The chosen color as a string.
     */
    private String getChosenColor(Scanner scanner) {
        String chosenColor;
        while (true) {
            System.out.print("Choose a color (RED, GREEN, BLUE, YELLOW): ");
            chosenColor = scanner.next().toUpperCase();
            if (chosenColor.equals("RED") || chosenColor.equals("GREEN") || chosenColor.equals("BLUE") || chosenColor.equals("YELLOW")) {
                break;
            } else {
                System.out.println("Invalid color. Please choose a valid color.");
            }
        }
        return chosenColor;
    }

    /**
     * The main method for running the Uno game. It initializes the game, gathers player names, and starts the game loop.
     *
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numPlayers = 0;

        while (true) {
            System.out.println("How many players? ");

            if (scanner.hasNextInt()) {
                numPlayers = scanner.nextInt();

                if (numPlayers >= 2 && numPlayers <= 4) {
                    break; // Valid input, exit the loop
                } else {
                    System.out.println("Please choose a number between 2 and 4.");
                }
            } else {
                // Input is not an integer, consume the invalid input
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a valid number of players.");
            }
        }

        System.out.println("You selected " + numPlayers + " players.");

        UnoGame unoGame = new UnoGame(numPlayers);
        unoGame.play();
    }
}


