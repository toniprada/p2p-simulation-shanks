/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.agent.BoyAgent;
import es.upm.dit.gsi.shanks.agent.CatAgent;
import es.upm.dit.gsi.shanks.agent.MotherAgent;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.agent.portrayal.BoyAgent2DPortrayal;
import es.upm.dit.gsi.shanks.agent.portrayal.CatAgent2DPortrayal;
import es.upm.dit.gsi.shanks.agent.portrayal.MotherAgent2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.model.han.element.device.Server;
import es.upm.dit.gsi.shanks.model.han.element.device.portrayal.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.model.han.element.device.portrayal.Server2DPortrayal;
import es.upm.dit.gsi.shanks.model.han.element.link.portrayal.Link2DPortrayalChooser;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 *
 */
public class HANScenario2DPortrayal extends Scenario2DPortrayal {

	/**
	 * @param scenario
	 * @param width
	 * @param height
	 * @throws DuplicatedPortrayalIDException
	 */
	public HANScenario2DPortrayal(Scenario scenario, int width, int height)
			throws DuplicatedPortrayalIDException {
		super(scenario, width, height);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#addPortrayals()
	 */
	@Override
	public void addPortrayals() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#placeElements()
	 */
	@Override
	public void placeElements() {
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC1"), 20, 10);
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC2"), 10, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC3"), 20, 50);
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC4"), 40, 10);
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC5"), 50, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC6"), 40, 50);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Server"), 30, 80);
//
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 12"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 13"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 14"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 15"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 16"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 23"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 24"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 25"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 26"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 34"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 35"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 36"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 45"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 46"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable 56"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable S1"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable S2"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable S3"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable S4"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable S5"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("Cable S6"));

	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
	 */
	@Override
	public void setupPortrayals() {
        ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
                
        devicePortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
        devicePortrayal.setPortrayalForClass(Server.class, new Server2DPortrayal());
        networkPortrayal.setPortrayalForAll(new Link2DPortrayalChooser());
        

        devicePortrayal.setPortrayalForClass(CatAgent.class, new CatAgent2DPortrayal());
        devicePortrayal.setPortrayalForClass(BoyAgent.class, new BoyAgent2DPortrayal());
        devicePortrayal.setPortrayalForClass(MotherAgent.class, new MotherAgent2DPortrayal());
	}
	
	public Location getDeviceLocation(String id) {
		Device d = (Device)this.getScenario().getNetworkElement(id);
		Double2D double2d = this.getPlacedDevices().getObjectLocation(d);
		return new Location(double2d);
	}

}
