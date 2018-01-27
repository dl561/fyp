import com.dl561.simulation.physics.Physics;
import com.dl561.simulation.physics.Vector2D;
import com.dl561.simulation.vehicle.Car;
import com.dl561.simulation.vehicle.Vehicle;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class PhysicsTest {

    @InjectMocks
    private Physics underTest;

    @Test
    public void testAcceleration() {
        Vehicle vehicle = new Car();
        vehicle.setLocation(new Vector2D(100, 100));
        vehicle.setWRXVelocity(0);
        vehicle.setWRYVelocity(0);
        vehicle.setDirectionOfTravel(0);
        vehicle.setAcceleratorPedalDepth(100);
        vehicle.setMaxEngineForce(2);
    }

    @Test
    @Ignore
    public void testBraking() {
        Vehicle vehicle = new Car();
        vehicle.setLocation(new Vector2D(100, 100));
        vehicle.setWRXVelocity(2d);
        vehicle.setWRYVelocity(0d);
        vehicle.setDirectionOfTravel(90d);
        vehicle.setMass(1000);
        vehicle.setBrakePedalDepth(100);
    }

    @Test
    public void testDrag() {
        Vehicle vehicle = new Car();
        vehicle.setWRXVelocity(0d);
        vehicle.setWRYVelocity(2d);
        vehicle.setDragConstant(1d);
    }

    @Test
    public void testRollingResistance() {
        Vehicle vehicle = new Car();
        vehicle.setWRXVelocity(2d);
        vehicle.setWRYVelocity(0d);
        vehicle.setRollingResistanceConstant(30d);
    }

    @Test
    public void testCalculateTotalForce() {
        Vehicle vehicle = new Car();
        vehicle.setWRXVelocity(2d);
        vehicle.setWRYVelocity(0d);
        vehicle.setRollingResistanceConstant(30d);
        vehicle.setMass(1000d);
        vehicle.setMaxEngineForce(2d);
        vehicle.setMaxBrakingForce(3d);
        vehicle.setDragConstant(1d);
        vehicle.setAcceleratorPedalDepth(100);
        vehicle.setBrakePedalDepth(100);
        //TODO: need to check on this for an assertion
    }

    @Test
    public void testCalculateNewPosition() {
        Vehicle vehicle = new Car();
        vehicle.setAcceleratorPedalDepth(100);
        vehicle.setDragConstant(1d);
        vehicle.setMaxBrakingForce(3d);
        vehicle.setMaxEngineForce(2d);
        vehicle.setMass(1000d);
        vehicle.setRollingResistanceConstant(30d);
        vehicle.setWRYVelocity(0);
        vehicle.setWRXVelocity(2d);
        vehicle.setDirectionOfTravel(0);
        vehicle.setLocation(new Vector2D(100, 100));
        // underTest.updateVehicle(new Simulation(), vehicle);
        System.out.println("stuff");
        //TODO: WTF do I need to do here to make this shit correct?
    }

    @Test
    public void testNormaliseForNormal() {
        double min = 10;
        double max = 20;
        double value = 15;
        double result = Physics.normalise(min, max, value);
        assertEquals("Should not change normal value", value, result, 0d);
    }

    @Test
    public void testNormaliseForBelowMin() {
        double min = 10;
        double max = 20;
        double value = 5;
        double result = Physics.normalise(min, max, value);
        assertEquals("Should not change normal value", min, result, 0d);
    }

    @Test
    public void testNormaliseForAboveMax() {
        double min = 10;
        double max = 20;
        double value = 25;
        double result = Physics.normalise(min, max, value);
        assertEquals("Should not change normal value", max, result, 0d);
    }

    @Test
    public void testSingleCollisionWithNoCollision() {
        double v1x = 0;
        double v1y = 0;
        double v2x = 10;
        double v2y = 10;
        double v1w = 3;
        double v1l = 5;
        double v2w = 3;
        double v2l = 5;
        Vehicle vehicle1 = new Car(), vehicle2 = new Car();
        vehicle1.setLocation(new Vector2D(v1x, v1y));
        vehicle2.setLocation(new Vector2D(v2x, v2y));
        vehicle1.setWidth(v1w);
        vehicle1.setLength(v1l);
        vehicle2.setWidth(v2w);
        vehicle2.setLength(v2l);
        boolean result = underTest.checkCollisionSingle(vehicle1, vehicle2);
        assertFalse(result);
    }

    @Test
    public void testSingleCollisionWithCollision() {
        double v1x = 0;
        double v1y = 0;
        double v2x = 0;
        double v2y = 0;
        double v1w = 3;
        double v1l = 5;
        double v2w = 3;
        double v2l = 5;
        Vehicle vehicle1 = new Car(), vehicle2 = new Car();
        vehicle1.setLocation(new Vector2D(v1x, v1y));
        vehicle2.setLocation(new Vector2D(v2x, v2y));
        vehicle1.setWidth(v1w);
        vehicle1.setLength(v1l);
        vehicle2.setWidth(v2w);
        vehicle2.setLength(v2l);
        boolean result = underTest.checkCollisionSingle(vehicle1, vehicle2);
        assertTrue(result);
    }
}
