/**
 * 
 */
package com.toniprada.tsag.p2p.model.scenario.portrayal;

import com.toniprada.tsag.p2p.model.element.device.Client;
import com.toniprada.tsag.p2p.model.element.device.Server;
import com.toniprada.tsag.p2p.model.element.device.portrayal.Computer2DPortrayal;
import com.toniprada.tsag.p2p.model.element.device.portrayal.Server2DPortrayal;
import com.toniprada.tsag.p2p.model.element.link.portrayal.Link2DPortrayalChooser;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author Toni Prada <toniprada@gmail.com>
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
		this.situateDevice((Device)this.getScenario().getNetworkElement("C1"), 20, 10);
		this.situateDevice((Device)this.getScenario().getNetworkElement("C2"), 10, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("C3"), 20, 50);
		this.situateDevice((Device)this.getScenario().getNetworkElement("C4"), 40, 10);
		this.situateDevice((Device)this.getScenario().getNetworkElement("C5"), 50, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("C6"), 40, 50);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Server"), 30, 80);
//
//		this.drawLink((Link)this.getScenario().getNetworkElement("C1-C2"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C1-C3"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C1-C4"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C1-C5"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C1-C6"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C2-C3"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C2-C4"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C2-C5"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C2-C6"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C3-C4"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C3-C5"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C3-C6"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C4-C5"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C4-C6"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C5-C6"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C1-Server"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C2-Server"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C3-Server"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C4-Server"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C5-Server"));
//		this.drawLink((Link)this.getScenario().getNetworkElement("C6-Server"));

	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
	 */
	@Override
	public void setupPortrayals() {
        ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
                
        devicePortrayal.setPortrayalForClass(Client.class, new Computer2DPortrayal());
        devicePortrayal.setPortrayalForClass(Server.class, new Server2DPortrayal());
        networkPortrayal.setPortrayalForAll(new Link2DPortrayalChooser());
	}
	
	public Location getDeviceLocation(String id) {
		Device d = (Device)this.getScenario().getNetworkElement(id);
		Double2D double2d = this.getPlacedDevices().getObjectLocation(d);
		return new Location(double2d);
	}

}
