import static org.junit.Assert.*;
import org.junit.Test;

/**
 * This class contains test cases for the Card class.
 */
public class CardTest {

    /**
     * Test case for determining if a card is special (wild).
     */
    @Test
    public void isSpecialCard() {
        Card wildCard = new Card(Card.Color.RED, Card.Value.WILD);
        Card regularCard = new Card(Card.Color.GREEN, Card.Value.FIVE);
        assertTrue("Wild card should be a special card", wildCard.isSpecialCard());
        assertFalse("Regular card should not be a special card", regularCard.isSpecialCard());
    }

    /**
     * Test case for getting the color of a card.
     */
    @Test
    public void getColor() {
        Card card = new Card(Card.Color.BLUE, Card.Value.ONE);
        assertEquals("Color should match", Card.Color.BLUE, card.getColor());
    }

    /**
     * Test case for getting the value of a card.
     */
    @Test
    public void getValue() {
        Card card = new Card(Card.Color.YELLOW, Card.Value.REVERSE);
        assertEquals("Value should match", Card.Value.REVERSE, card.getValue());
    }

    /**
     * Test case for obtaining the string representation of a card.
     */
    @Test
    public void stringCard() {
        Card regularCard = new Card(Card.Color.RED, Card.Value.FIVE);
        Card wildCard = new Card(Card.Color.GREEN, Card.Value.WILD);

        assertEquals("String representation should match", "RED FIVE", regularCard.stringCard());
        assertEquals("String representation should match", "WILD", wildCard.stringCard());
    }
}
