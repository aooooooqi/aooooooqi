//SENG 300 Assignment 3
//Xinyang Chen (30086738)
//Qianya Yu (30088479)
//Aoqi Li (30120288)

import org.junit.Assert;
import org.junit.Test;

public class ElectronicScaleTest {
	
	@Test
	public void testGetWeightLimitAndSensitivity() {
		int weightLimitInGrams = 70;
		int sensitivity = 60;
		ElectronicScale trialElectronicScale = new ElectronicScale(weightLimitInGrams,sensitivity);
		Assert.assertEquals(weightLimitInGrams,trialElectronicScale.getWeightLimit(),0.5);
		Assert.assertEquals(sensitivity,trialElectronicScale.getSensitivity(),0.5);
	}
	
	@Test (expected = SimulationException.class)
	public void testGetZeroWeightLimit() {
		int weightLimitInGrams = 0;
		int sensitivity = 60;
		ElectronicScale trialElectronicScale = new ElectronicScale(weightLimitInGrams,sensitivity);
	}
	
	@Test (expected = SimulationException.class)
	public void testGetNegativeWeightLimit() {
		int weightLimitInGrams = -20;
		int sensitivity = 60;
		ElectronicScale trialElectronicScale = new ElectronicScale(weightLimitInGrams,sensitivity);
	}
	
	@Test (expected = SimulationException.class)
	public void testGetZeroSensitivity() {
		int weightLimitInGrams = 70;
		int sensitivity = 0;
		ElectronicScale trialElectronicScale = new ElectronicScale(weightLimitInGrams,sensitivity);
	}
	
	@Test (expected = SimulationException.class)
	public void testGetNegativeSensitivity() {
		int weightLimitInGrams = 70;
		int sensitivity = -30;
		ElectronicScale trialElectronicScale = new ElectronicScale(weightLimitInGrams,sensitivity);
	}
	
	@Test
	public void testGetCurrentWeight() throws OverloadException{
		PriceLookupCode pluCodeForItem = new PriceLookupCode("123");
		PLUCodedItem item1 = new PLUCodedItem(pluCodeForItem, 69.9);
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item1);
		Assert.assertEquals(69.9, trialElectronicScale.getCurrentWeight(),1.0);
	}
	
	@Test
	public void testCurrentWeightSameAsLimitWeight() throws OverloadException{
		PriceLookupCode pluCodeForItem = new PriceLookupCode("321");
		PLUCodedItem item1 = new PLUCodedItem(pluCodeForItem, 70);
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item1);
		Assert.assertEquals(70,trialElectronicScale.getCurrentWeight(),1.0);
	}
	
	@Test (expected = OverloadException.class)
	public void testCurrentWeightLargerThanLimitWeight() throws OverloadException{
		PriceLookupCode pluCodeForItem = new PriceLookupCode("323");
		PLUCodedItem item1 = new PLUCodedItem(pluCodeForItem, 80);
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item1);
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
		PriceLookupCode pluCodeForItem = new PriceLookupCode("211");
		PLUCodedItem item = new PLUCodedItem(pluCodeForItem, 20.5);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
		try {
			Assert.assertEquals(20.5,trialElectronicScale.getCurrentWeight(),1.0);
		} catch (OverloadException e) {}
	}
	
	@Test (expected = SimulationException.class)
	public void testAdd_PhaseError() {
		PriceLookupCode pluCodeForItem = new PriceLookupCode("213");
		PLUCodedItem item = new PLUCodedItem(pluCodeForItem, 33.2);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.forceErrorPhase();
		trialElectronicScale.add(item);
	}
	
	@Test (expected = SimulationException.class)
	public void testAdd_PhaseConfiguration() {
		PriceLookupCode pluCodeForItem = new PriceLookupCode("231");
		PLUCodedItem item = new PLUCodedItem(pluCodeForItem, 37.5);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.add(item);
	}
	
	@Test (expected = SimulationException.class)
	public void testDuplicatedAdd() {
		PriceLookupCode pluCodeForItem = new PriceLookupCode("312");
		PLUCodedItem item = new PLUCodedItem(pluCodeForItem, 100.2);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
		trialElectronicScale.add(item);
	}
	
	@Test
	public void testRemove_OneItem() {
		PriceLookupCode pluCodeForItem = new PriceLookupCode("248");
		PLUCodedItem item = new PLUCodedItem(pluCodeForItem, 23.6);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
		trialElectronicScale.remove(item);
		try {
			Assert.assertEquals(0,trialElectronicScale.getCurrentWeight(),1.0);
		} catch (OverloadException e) {}
	}
	
	@Test
	public void testRemove_TwoItems() {
		PriceLookupCode pluCodeForItem1 = new PriceLookupCode("246");
		PLUCodedItem item1 = new PLUCodedItem(pluCodeForItem1, 23.6);
		PriceLookupCode pluCodeForItem2 = new PriceLookupCode("135");
		PLUCodedItem item2 = new PLUCodedItem(pluCodeForItem2, 100.2);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item1);
		trialElectronicScale.add(item2);
		trialElectronicScale.remove(item1);
		try {
			Assert.assertEquals(100.2,trialElectronicScale.getCurrentWeight(),1.0);
		} catch (OverloadException e) {}
	}
	
	@Test
	public void testRemove_LimitWeightSmallerThanItemWeight() {
		PriceLookupCode pluCodeForItem = new PriceLookupCode("135");
		PLUCodedItem item = new PLUCodedItem(pluCodeForItem, 100.2);
		ElectronicScale trialElectronicScale = new ElectronicScale(100,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
		trialElectronicScale.remove(item);
		try {
			Assert.assertEquals(0,trialElectronicScale.getCurrentWeight(),1.0);
		} catch (OverloadException e) {}
	}
	
	@Test
	public void testRemove_SensitivitySmallerThanDifference() {
		PriceLookupCode pluCodeForItem = new PriceLookupCode("135");
		PLUCodedItem item = new PLUCodedItem(pluCodeForItem, 100.2);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,100);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
		trialElectronicScale.remove(item);
		try {
			Assert.assertEquals(0,trialElectronicScale.getCurrentWeight(),1.0);
		} catch (OverloadException e) {}
	}
	
	@Test (expected = SimulationException.class)
	public void testRemove_NoItem() {
		PriceLookupCode pluCodeForItem = new PriceLookupCode("246");
		PLUCodedItem item = new PLUCodedItem(pluCodeForItem, 123.6);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.remove(item);
	}
	
	@Test (expected = SimulationException.class)
	public void testRemove_PhaseError() {
		PriceLookupCode pluCodeForItem = new PriceLookupCode("246");
		PLUCodedItem item = new PLUCodedItem(pluCodeForItem, 35.6);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
		trialElectronicScale.forceErrorPhase();
		trialElectronicScale.remove(item);
	}
	
	@Test (expected = SimulationException.class)
	public void testRemove_PhaseConfiguration() {
		PriceLookupCode pluCodeForItem = new PriceLookupCode("246");
		PLUCodedItem item = new PLUCodedItem(pluCodeForItem, 123.4);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.remove(item);
	}	
}
