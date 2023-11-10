import static org.junit.Assert.*;
import org.junit.Test;

/**
 * This class contains test cases for the Player class.
 */
public class PlayerTest {

    /**
     * Test case for getting the player's name.
     */
    @Test
    public void getName() {
        Player player = new Player("TestPlayer");
        String name = player.getName();
        assertEquals("Player's name should match the provided name", "TestPlayer", name);
    }

    /**
     * Test case for getting the player's score.
     */
    @Test
    public void getScore() {
        Player player = new Player("TestPlayer");
        int score = player.getScore();
        assertEquals("Player's initial score should be 0", 0, score);
    }

    /**
     * Test case for getting the player's hand.
     */
    @Test
    public void getHand() {
        Player player = new Player("TestPlayer");
        Hand hand = player.getHand();
        assertNotNull("Player's hand should not be null", hand);
    }

    /**
     * Test case for setting the player's score.
     */
    @Test
    public void setScore() {
        Player player = new Player("TestPlayer");
        player.setScore(10);
        int score = player.getScore();
        assertEquals("Player's score should be set to 10", 10, score);
    }

    /**
     * Test case for getting Uno called status.
     */
    @Test
    public void getUnoCalled() {
        Player player = new Player("TestPlayer");
        boolean unoCalled = player.getUnoCalled();
        assertFalse("Uno should initially not be called", unoCalled);
    }

    /**
     * Test case for setting Uno called status.
     */
    @Test
    public void setUnoCalled() {
        Player player = new Player("TestPlayer");
        player.setUnoCalled(true);
        boolean unoCalled = player.getUnoCalled();
        assertTrue("Uno should be set to true", unoCalled);
    }

    /**
     * Test case for drawing a card.
     */
    @Test
    public void drawCard() {
        Player player = new Player("TestPlayer");
        int initialSize = player.getHand().getNumCards();
        player.drawCard();
        int newSize = player.getHand().getNumCards();
        assertEquals("Drawing a card should increase the size by 1", initialSize + 1, newSize);
    }

    /**
     * Test case for playing a card.
     */
    //@Test
    /*public void playCard() {
        Player player = new Player("TestPlayer");
        Hand hand = player.getHand();
        Card card = hand.getCards().get(0);
        assertTrue("Playing a valid card should return true", player.playCard(card));
        Card invalidCard = new Card(Card.Color.RED, Card.Value.ONE);
        assertFalse("Playing an invalid card should return false", player.playCard(invalidCard));
    }

    /**
     * Test case for checking Uno status.
     */
    @Test
    public void hasUno() {
        Player player = new Player("TestPlayer");
        assertFalse("Player should not initially have Uno", player.hasUno());
    }

    /**
     * Test case for calling Uno.
     */
    @Test
    public void callUno() {
        Player player = new Player("TestPlayer");
        player.callUno();
        assertTrue("Uno should be called", player.getUnoCalled());
    }

    /**
     * Test case for updating the player's score.
     */
    /*
    @Test
    public void updateScore() {
        Player player = new Player("TestPlayer");
        player.updateScore(true);
        assertEquals("Player's score should be updated to 1", 1, player.getScore());
    }
    */
}
