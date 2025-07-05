package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Gesture enum
 */
public class GestureTest {
    
    @Test
    public void testGestureBeats() {
        // Rock beats Scissors
        assertTrue(Gesture.ROCK.beats(Gesture.SCISSORS));
        assertFalse(Gesture.SCISSORS.beats(Gesture.ROCK));
        
        // Paper beats Rock
        assertTrue(Gesture.PAPER.beats(Gesture.ROCK));
        assertFalse(Gesture.ROCK.beats(Gesture.PAPER));
        
        // Scissors beats Paper
        assertTrue(Gesture.SCISSORS.beats(Gesture.PAPER));
        assertFalse(Gesture.PAPER.beats(Gesture.SCISSORS));
        
        // Same gestures don't beat each other
        assertFalse(Gesture.ROCK.beats(Gesture.ROCK));
        assertFalse(Gesture.PAPER.beats(Gesture.PAPER));
        assertFalse(Gesture.SCISSORS.beats(Gesture.SCISSORS));
    }
    
    @Test
    public void testFromShortCode() {
        assertEquals(Gesture.ROCK, Gesture.fromShortCode("R"));
        assertEquals(Gesture.PAPER, Gesture.fromShortCode("P"));
        assertEquals(Gesture.SCISSORS, Gesture.fromShortCode("S"));
        
        // Case insensitive
        assertEquals(Gesture.ROCK, Gesture.fromShortCode("r"));
        assertEquals(Gesture.PAPER, Gesture.fromShortCode("p"));
        assertEquals(Gesture.SCISSORS, Gesture.fromShortCode("s"));
        
        // Invalid codes
        assertNull(Gesture.fromShortCode("X"));
        assertNull(Gesture.fromShortCode(""));
        assertNull(Gesture.fromShortCode(null));
    }
    
    @Test
    public void testRandom() {
        // Test that random returns a valid gesture
        for (int i = 0; i < 100; i++) {
            Gesture random = Gesture.random();
            assertNotNull(random);
            assertTrue(random == Gesture.ROCK || random == Gesture.PAPER || random == Gesture.SCISSORS);
        }
    }
    
    @Test
    public void testDisplayNames() {
        assertEquals("Rock", Gesture.ROCK.getDisplayName());
        assertEquals("Paper", Gesture.PAPER.getDisplayName());
        assertEquals("Scissors", Gesture.SCISSORS.getDisplayName());
    }
    
    @Test
    public void testShortCodes() {
        assertEquals("R", Gesture.ROCK.getShortCode());
        assertEquals("P", Gesture.PAPER.getShortCode());
        assertEquals("S", Gesture.SCISSORS.getShortCode());
    }
}
