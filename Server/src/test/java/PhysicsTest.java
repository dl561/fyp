import com.dl561.simulation.course.location.Location;
import com.dl561.simulation.physics.Physics;
import com.dl561.simulation.physics.Vector2D;
import com.dl561.simulation.vehicle.Car;
import com.dl561.simulation.vehicle.Vehicle;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PhysicsTest {

    @InjectMocks
    private Physics underTest;

    @Test
    public void testAcceleration() {
        Vehicle vehicle = new Car();
        vehicle.setLocation(new Location(100, 100));
        vehicle.setXVelocity(0);
        vehicle.setYVelocity(0);
        vehicle.setDirectionOfTravel(0);
        vehicle.setAcceleratorPedalDepth(100);
        vehicle.setMaxEngineForce(2);
        Vector2D result = underTest.calculateEngineForce(vehicle);
        assertEquals("Result should be magnitude 2.0", 2.0d, result.getMagnitude(), 0.0001d);
        assertEquals("Result should be direction 0.0", 0.0d, result.getDirection(), 0.0001d);
    }

    @Test
    @Ignore
    public void testBraking() {
        Vehicle vehicle = new Car();
        vehicle.setLocation(new Location(100, 100));
        vehicle.setXVelocity(2d);
        vehicle.setYVelocity(0d);
        vehicle.setDirectionOfTravel(90d);
        vehicle.setMass(1000);
        vehicle.setBrakePedalDepth(100);
        Vector2D result = underTest.calculateBrakingForce(vehicle);
        System.out.println(result.getMagnitude());
        System.out.println(Math.toDegrees(result.getDirection()));
        assertEquals("Result should have magnitude of 3.0", 3.0d, result.getMagnitude(), 0.0001d);
        assertEquals("Result should have negative input direction", 270d, Math.toDegrees(result.getDirection()), 0.0001d);
    }

    @Test
    public void testDrag() {
        Vehicle vehicle = new Car();
        vehicle.setXVelocity(0d);
        vehicle.setYVelocity(2d);
        vehicle.setDragConstant(1d);
        Vector2D result = underTest.calculateDragForce(vehicle);
        assertEquals("Result should have magnitude 4.0", 4.0d, result.getMagnitude(), 0.0001d);
        assertEquals("Result should have negative input direction", 270d, Math.toDegrees(result.getDirection()), 0.0001d);
    }

    @Test
    public void testRollingResistance() {
        Vehicle vehicle = new Car();
        vehicle.setXVelocity(2d);
        vehicle.setYVelocity(0d);
        vehicle.setRollingResistanceConstant(30d);
        Vector2D result = underTest.calculateRollingResistanceForce(vehicle);
        assertEquals("Result should have magnitude of 60.0", 60.0d, result.getMagnitude(), 0.0001d);
        assertEquals("Result should have negative input direction", 180.0d, Math.toDegrees(result.getDirection()), 0.0001d);
    }

    @Test
    public void testCalculateTotalForce() {
        Vehicle vehicle = new Car();
        vehicle.setXVelocity(2d);
        vehicle.setYVelocity(0d);
        vehicle.setRollingResistanceConstant(30d);
        vehicle.setMass(1000d);
        vehicle.setMaxEngineForce(2d);
        vehicle.setMaxBrakingForce(3d);
        vehicle.setDragConstant(1d);
        vehicle.setAcceleratorPedalDepth(100);
        vehicle.setBrakePedalDepth(100);
        Vector2D result = underTest.calculateTotalForce(vehicle);
        System.out.println("magnitude:" + result.getMagnitude());
        System.out.println("direction:" + result.getDirection());
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
        vehicle.setYVelocity(0);
        vehicle.setXVelocity(2d);
        vehicle.setDirectionOfTravel(0);
        vehicle.setLocation(new Location(100, 100));
        underTest.updateVehicle(vehicle);
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
}
