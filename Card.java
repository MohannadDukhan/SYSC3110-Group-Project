import java.util.Random;

/**
 * The `Card` class represents Uno cards with a specific color and value.
 */
public class Card {
    /**
     * Enum for the possible values of Uno cards, including numbers, special cards, and wild cards.
     */
    public enum Value { ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, SKIP, WILD, WILD_DRAW_TWO_CARDS }

    /**
     * Enum for the possible colors of Uno cards.
     */
    public enum Color { RED, GREEN, BLUE, YELLOW }

    private final Value value;  // The value of the card.
    private final Color color;  // The color of the card.

    /**
     * Constructs a random Uno card with a random color and value.
     */
    public Card() {
        Random random = new Random();
        this.color = Color.values()[random.nextInt(Color.values().length)];
        this.value = Value.values()[random.nextInt(Value.values().length)];
    }

    /**
     * Constructs a Uno card with a specified color and value.
     *
     * @param color The color of the card.
     * @param value The value of the card.
     */
    public Card(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    /**
     * Generates a random Uno card to be used as the top card, excluding special cards.
     *
     * @return A random Uno card.
     */
    public static Card generate_top_card() {
        Card card;
        Random random = new Random();
        do {
            card = new Card();
        } while (card.isSpecialCard());
        return card;
    }

    /**
     * Checks if the card is a special card (wild, wild draw two, reverse, or skip).
     *
     * @return `true` if the card is a special card, otherwise `false`.
     */
    public boolean isSpecialCard() {
        return value == Value.WILD ||
                value == Value.WILD_DRAW_TWO_CARDS ||
                value == Value.REVERSE ||
                value == Value.SKIP;
    }

    /**
     * Gets the color of the Uno card.
     *
     * @return The color of the card.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the value of the Uno card.
     *
     * @return The value of the card.
     */
    public Value getValue() {
        return value;
    }

    /**
     * Returns a string representation of the Uno card.
     * If the card's value is a wild card, it returns only the value; otherwise, it returns both the color and value.
     *
     * @return A string representation of the Uno card.
     */
    public String stringCard() {
        if (value == Value.WILD || value == Value.WILD_DRAW_TWO_CARDS ) {
            return value.toString();
        } else {
            return color.toString() + " " + value.toString();
        }
    }
}
