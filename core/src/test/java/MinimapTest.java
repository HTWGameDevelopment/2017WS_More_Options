import com.moreoptions.prototype.level.Minimap;
import org.junit.Test;

/**
 * Tests for the minimap
 * Creates an entity of the map multiple times with a different number of rooms
 */
public class MinimapTest {

    @Test
    public void testTwentyRooms() {
        for(int i = 0; i < 100000; i++) {
            Minimap m = new Minimap(10,10,20);
        }
    }

    @Test
    public void testSevenRooms() {
        for (int i = 0; i < 100000; i++) {
            Minimap m = new Minimap(10, 10, 7);
        }
    }

    @Test
    public void testFortyRooms() {
        for (int i = 0; i < 100000; i++) {
            Minimap m = new Minimap(10, 10, 40);
        }
    }

    @Test
    public void testSixtyRooms() {
        for (int i = 0; i < 100000; i++) {
            Minimap m = new Minimap(10, 10, 60);
        }
    }
}