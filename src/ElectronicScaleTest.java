import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class ElectronicScaleTest {
	
	@Test
	public void testGetWeightLimit() {
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		Assert.assertEquals(70,trialElectronicScale.getWeightLimit(),0.5);
	}
	
	@Test (expected = SimulationException.class)
	public void testGetZeroWeightLimit() {
		ElectronicScale trialElectronicScale = new ElectronicScale(0,60);
	}
	
	@Test (expected = SimulationException.class)
	public void testGetNegativeWeightLimit() {
		ElectronicScale trialElectronicScale = new ElectronicScale(-20,60);
	}
	
	@Test
	public void testGetSensitivity() {
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		Assert.assertEquals(60,trialElectronicScale.getSensitivity(),0.5);
	}
	
	@Test (expected = SimulationException.class)
	public void testGetZeroSensitivity() {
		ElectronicScale trialElectronicScale = new ElectronicScale(70,0);
	}
	
	@Test (expected = SimulationException.class)
	public void testGetNegativeSensitivity() {
		ElectronicScale trialElectronicScale = new ElectronicScale(70,-30);
	}
	
	@Test
	public void testGetCurrentWeight() throws OverloadException{
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.endConfigurationPhase();
		double currentWeight = 0;
		Assert.assertEquals(currentWeight, trialElectronicScale.getCurrentWeight(),1.0);
	}
	
	@Test
	public void testGetCurrentWeight_CurrentSameAsLimit() throws OverloadException{
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.endConfigurationPhase();
		double currentWeight = 70;
		trialElectronicScale.getCurrentWeight();
	}
	
	@Test
	public void testGetCurrentWeight_CurrentLargerThanLimit() throws OverloadException{
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.endConfigurationPhase();
		double currentWeight = 80;
		trialElectronicScale.getCurrentWeight();
	}

	@Test (expected = SimulationException.class)
	public void testGetCurrentWeight_PhaseError() throws OverloadException{
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.forceErrorPhase();
		trialElectronicScale.getCurrentWeight();
	}
	
	@Test (expected = SimulationException.class)
	public void testGetCurrentWeight_PhaseConfiguration() throws OverloadException{
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.getCurrentWeight();
	}
	
	@Test
	public void testAdd() {
		PriceLookupCode pluCodeForApple = new PriceLookupCode("211");
		PLUCodedItem apple = new PLUCodedItem(pluCodeForApple, 20.5);
		ElectronicScale trialElectronicScale = new ElectronicScale(7000,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(apple);
		try {
			Assert.assertEquals(20.5,trialElectronicScale.getCurrentWeight(),1.0);
		} catch (OverloadException e) {
			
		}
		
	}
	
	@Test
	public void testRemove() {
		
	}
}
