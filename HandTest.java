import static org.junit.Assert.*;
import org.junit.Test;

/**
 * This class contains test cases for the Hand class.
 */
public class HandTest {

    /**
     * Test case for adding a card to the hand.
     */
    @Test
    public void addCard() {
        Hand hand = new Hand();
        int initialSize = hand.getNumCards();
        Card addedCard = hand.addCard();
        int newSize = hand.getNumCards();
        assertEquals("Adding a card should increase the size by 1", initialSize + 1, newSize);
        assertTrue("Hand should contain the added card", hand.getCards().contains(addedCard));
    }

    /**
     * Test case for removing a card from the hand.
     */
    @Test
    public void removeCard() {
        Hand hand = new Hand();
        Card card = hand.getCards().get(0);
        int initialSize = hand.getNumCards();
        hand.removeCard(card);
        int newSize = hand.getNumCards();
        assertEquals("Removing a card should decrease the size by 1", initialSize - 1, newSize);
        assertFalse("Hand should not contain the removed card", hand.getCards().contains(card));
    }

    /**
     * Test case to ensure the Hand object is properly constructed.
     */
    @Test
    public void constructorTest() {
        Hand hand = new Hand();
        assertNotNull("Hand object should not be null", hand);
    }
}
