/**
 * 
 */
package es.upm.dit.gsi.shanks.model.adsl.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.agent.BoyAgent;
import es.upm.dit.gsi.shanks.agent.CatAgent;
import es.upm.dit.gsi.shanks.agent.MotherAgent;
import es.upm.dit.gsi.shanks.agent.portrayal.BoyAgent2DPortrayal;
import es.upm.dit.gsi.shanks.agent.portrayal.CatAgent2DPortrayal;
import es.upm.dit.gsi.shanks.agent.portrayal.MotherAgent2DPortrayal;
import es.upm.dit.gsi.shanks.model.adsl.element.device.ADSLModem;
import es.upm.dit.gsi.shanks.model.adsl.element.device.DSLAM;
import es.upm.dit.gsi.shanks.model.adsl.element.device.portrayal.ADSLModem2DPortrayal;
import es.upm.dit.gsi.shanks.model.adsl.element.device.portrayal.DSLAM2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.han.element.device.Client;
import es.upm.dit.gsi.shanks.model.han.element.device.EthernetRouter;
import es.upm.dit.gsi.shanks.model.han.element.device.Monitor;
import es.upm.dit.gsi.shanks.model.han.element.device.portrayal.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.model.han.element.device.portrayal.EthernetRouter2DPortrayal;
import es.upm.dit.gsi.shanks.model.han.element.device.portrayal.Monitor2DPortrayal;
import es.upm.dit.gsi.shanks.model.han.element.link.portrayal.Link2DPortrayalChooser;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ShanksMath;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 *
 */
public class ADSLAccessNetworkScenario2DPortrayal extends ComplexScenario2DPortrayal {

	public ADSLAccessNetworkScenario2DPortrayal(Scenario scenario, int width, int height)
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
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
        
        ComplexScenario cs = (ComplexScenario) this.getScenario();       
        this.situateDevice((Device)cs.getNetworkElement("DSLAM zone 1"), 75, 70);
        this.situateDevice((Device)cs.getNetworkElement("DSLAM zone 2"), 75, 80);
        this.drawLink((Link)cs.getNetworkElement("global link"));
        

        this.situateDevice((Device)cs.getNetworkElement("ADSL modem 1-1"), 60, 60);
        this.situateDevice((Device)cs.getNetworkElement("ADSL modem 2-1"), 90, 60);
        this.situateDevice((Device)cs.getNetworkElement("ADSL modem 3-2"), 60, 90);
        this.situateDevice((Device)cs.getNetworkElement("ADSL modem 4-2"), 90, 90);
        this.drawLink((Link)cs.getNetworkElement("CP1-1"));
        this.drawLink((Link)cs.getNetworkElement("CP2-1"));
        this.drawLink((Link)cs.getNetworkElement("CP3-2"));
        this.drawLink((Link)cs.getNetworkElement("CP4-2"));
        

        this.drawLink((Link)cs.getNetworkElement("eth-adslModem-1"));
        this.drawLink((Link)cs.getNetworkElement("eth-adslModem-2"));
        this.drawLink((Link)cs.getNetworkElement("eth-adslModem-3"));
        this.drawLink((Link)cs.getNetworkElement("eth-adslModem-4"));
        
        
        
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
	 */
	@Override
	public void setupPortrayals() {
        ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        devicePortrayal.setPortrayalForClass(DSLAM.class, new DSLAM2DPortrayal());
        devicePortrayal.setPortrayalForClass(ADSLModem.class, new ADSLModem2DPortrayal());
        devicePortrayal.setPortrayalForClass(Client.class, new Computer2DPortrayal());
        devicePortrayal.setPortrayalForClass(Monitor.class, new Monitor2DPortrayal());
        devicePortrayal.setPortrayalForClass(EthernetRouter.class, new EthernetRouter2DPortrayal());
        networkPortrayal.setPortrayalForAll(new Link2DPortrayalChooser());

        devicePortrayal.setPortrayalForClass(CatAgent.class, new CatAgent2DPortrayal());
        devicePortrayal.setPortrayalForClass(BoyAgent.class, new BoyAgent2DPortrayal());
        devicePortrayal.setPortrayalForClass(MotherAgent.class, new MotherAgent2DPortrayal());
	}

	@Override
	public void placeScenarios() throws DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateScenario(cs.getScenario("HAN1"), new Double2D(0,0), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        this.situateScenario(cs.getScenario("HAN2"), new Double2D(150,0), ShanksMath.ANGLE_180, ShanksMath.ANGLE_0, ShanksMath.ANGLE_180);
        this.situateScenario(cs.getScenario("HAN3"), new Double2D(0,100), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        this.situateScenario(cs.getScenario("HAN4"), new Double2D(150,100), ShanksMath.ANGLE_180, ShanksMath.ANGLE_0, ShanksMath.ANGLE_180);
	}

}
