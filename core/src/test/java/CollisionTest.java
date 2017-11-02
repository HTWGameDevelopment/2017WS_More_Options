import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.moreoptions.prototype.gameEngine.util.CollisionUtil;
import org.junit.Test;

/**
 * Created by denwe on 30.10.2017.
 */
public class CollisionTest {

    @Test
    public void testOverlaps() {




    }

    @Test
    public void testXOverlap() {

        Rectangle r = new Rectangle(5,0,200,200);
        Circle c = new Circle(0,0,5);



        //Overlap is 5

        System.out.println(CollisionUtil.getXOverlap(c,r) + "|" +Intersector.overlaps(c,r));

    }


}
