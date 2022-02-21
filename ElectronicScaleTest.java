//SENG 300 Assignment 3
//Xinyang Chen (30086738)
//Qianya Yu (30088479)
//Aoqi Li (30120288)

import org.junit.Assert;
import org.junit.Test;

public class ElectronicScaleTest {
	@Test
	public void testGetWeightLimit() {
		int weightLimitInGrams = 70;
		int sensitivity = 60;
		ElectronicScale trialElectronicScale = new ElectronicScale(weightLimitInGrams,sensitivity);
		Assert.assertEquals(weightLimitInGrams,trialElectronicScale.getWeightLimit(),0.5);
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
	
	@Test
	public void testGetSensitivity() {
		int weightLimitInGrams = 70;
		int sensitivity = 60;
		ElectronicScale trialElectronicScale = new ElectronicScale(weightLimitInGrams,sensitivity);
		Assert.assertEquals(sensitivity,trialElectronicScale.getSensitivity(),0.5);
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
		ItemStub item = new ItemStub(69.9);
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
		Assert.assertEquals(69.9, trialElectronicScale.getCurrentWeight(),1.0);
	}
	
	@Test
	public void testCurrentWeightSameAsLimitWeight() throws OverloadException{
		ItemStub item = new ItemStub(70);
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
		Assert.assertEquals(70,trialElectronicScale.getCurrentWeight(),1.0);
	}
	
	@Test (expected = OverloadException.class)
	public void testCurrentWeightLargerThanLimitWeight() throws OverloadException{
		ItemStub item = new ItemStub(80);
		ElectronicScale trialElectronicScale = new ElectronicScale(70,60);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
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
		ItemStub item = new ItemStub(20.5);
		ElectronicScaleObserverStub l = new ElectronicScaleObserverStub();
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.attach(l);
		trialElectronicScale.add(item);
		try {
			Assert.assertEquals(20.5,trialElectronicScale.getCurrentWeight(),1.0);
		} catch (OverloadException e) {}
	}
	
	@Test (expected = SimulationException.class)
	public void testAdd_PhaseError() {
		ItemStub item = new ItemStub(33.2);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.forceErrorPhase();
		trialElectronicScale.add(item);
	}
	
	@Test (expected = SimulationException.class)
	public void testAdd_PhaseConfiguration() {
		ItemStub item = new ItemStub(37.5);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.add(item);
	}
	
	@Test (expected = SimulationException.class)
	public void testDuplicatedAdd() {
		ItemStub item = new ItemStub(100.2);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
		trialElectronicScale.add(item);
	}
	
	@Test
	public void testRemove_OneItem() {
		ItemStub item = new ItemStub(23.6);
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
		ItemStub item1 = new ItemStub(23.6);
		ItemStub item2 = new ItemStub(100.2);
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
		ItemStub item = new ItemStub(100.2);
		ElectronicScaleObserverStub l = new ElectronicScaleObserverStub();
		ElectronicScale trialElectronicScale = new ElectronicScale(100,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.attach(l);
		trialElectronicScale.add(item);
		trialElectronicScale.remove(item);
		try {
			Assert.assertEquals(0,trialElectronicScale.getCurrentWeight(),1.0);
		} catch (OverloadException e) {}
	}
	
	@Test
	public void testRemove_SensitivitySmallerThanDifference() {
		ItemStub item = new ItemStub(101.2);
		ElectronicScaleObserverStub l = new ElectronicScaleObserverStub();
		ElectronicScale trialElectronicScale = new ElectronicScale(700,100);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.attach(l);
		trialElectronicScale.add(item);
		trialElectronicScale.remove(item);
		try {
			Assert.assertEquals(0,trialElectronicScale.getCurrentWeight(),1.0);
		} catch (OverloadException e) {}
	}
	
	@Test (expected = SimulationException.class)
	public void testRemove_NoItem() {
		ItemStub item = new ItemStub(123.6);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.remove(item);
	}
	
	@Test (expected = SimulationException.class)
	public void testRemove_PhaseError() {
		ItemStub item = new ItemStub(35.6);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.endConfigurationPhase();
		trialElectronicScale.add(item);
		trialElectronicScale.forceErrorPhase();
		trialElectronicScale.remove(item);
	}
	
	@Test (expected = SimulationException.class)
	public void testRemove_PhaseConfiguration() {
		ItemStub item = new ItemStub(123.4);
		ElectronicScale trialElectronicScale = new ElectronicScale(700,600);
		trialElectronicScale.remove(item);
	}
	
	
}
