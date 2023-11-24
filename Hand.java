import java.util.ArrayList;

/**
 * The `Hand` class represents a player's hand in the Uno card game, which holds a collection of Uno cards.
 */
public class Hand {
    private int start_cards = 7;  // The initial number of cards in the hand.
    private ArrayList<Card> cards;  // The list of cards in the hand.

    private ArrayList<Card> DarkCards;  // The list of cards in the hand.
    private int num_cards;  // The number of cards currently in the hand.

    /**
     * Constructs a player's hand by randomly selecting an initial set of cards (7 cards by default).
     */
    public Hand() {
        cards = new ArrayList<Card>();
        for (int i = 0; i < start_cards; i++) {
            cards.add(Card.LightCard());
            num_cards += 1;
        }
    }

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Adds a new card to the hand.
     *
     * @return The card added to the hand.
     */
    public Card addLightCard() {
        cards.add(Card.LightCard());
        num_cards += 1;
        return cards.get(num_cards - 1);
    }

    /**
     * Removes a specified card from the hand.
     *
     * @param card The card to be removed from the hand.
     */
    public void removeCard(Card card) {
        cards.remove(card);
        num_cards -= 1;
    }

    /**
     * Prints all the cards in the hand, displaying their colors and values.
     */
    public void printAll() {
        for (Card card : cards) {
            System.out.print(card.getColor().toString() + " " + card.getValue().toString() + ", ");
        }
    }

    /**
     * Gets the list of cards in the hand.
     *
     * @return The ArrayList containing the cards in the hand.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Gets the number of cards currently in the hand.
     *
     * @return The number of cards in the hand.
     */
    public int getNumCards() {
        return num_cards;
    }

    public ArrayList<Card> ToDarkCards(){
        for (Card c: cards){
            if(c.getValue() == Card.Value.REVERSE ) {
                DarkCards.add(new Card(c.getColor(), Card.Value.REVERSE));
            } else if(c.getValue() == Card.Value.FLIP){
                DarkCards.add(new Card(c.getColor(), Card.Value.FLIP));
            } else if(c.getValue() == Card.Value.SKIP){
                DarkCards.add(new Card(c.getColor(), Card.Value.SKIP_EVERYONE));
            } else if(c.getValue() == Card.Value.WILD){
                DarkCards.add(new Card(c.getColor(), Card.Value.WILD));
            } else if(c.getValue() == Card.Value.WILD_DRAW_TWO_CARDS){
                DarkCards.add(new Card(c.getColor(), Card.Value.DRAW_FIVE));
            } else if(c.getColor() == Card.Color.BLUE){
                DarkCards.add(new Card(Card.Color.PINK, c.getValue()));
            } else if (c.getColor() == Card.Color.YELLOW) {
                DarkCards.add(new Card(Card.Color.PURPLE, c.getValue()));
            } else if (c.getColor() == Card.Color.RED) {
                DarkCards.add(new Card(Card.Color.ORANGE, c.getValue()));
            } else if (c.getColor() == Card.Color.GREEN) {
                DarkCards.add(new Card(Card.Color.TEAL, c.getValue()));
            }
        }
        return DarkCards;
    }
}



