/**
 * 
 */
package es.upm.dit.gsi.shanks.model.adsl.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.model.adsl.element.device.ADSLModem;
import es.upm.dit.gsi.shanks.model.adsl.element.device.DSLAM;
import es.upm.dit.gsi.shanks.model.adsl.element.link.CopperPair;
import es.upm.dit.gsi.shanks.model.adsl.failure.LostSynchronismFailure;
import es.upm.dit.gsi.shanks.model.adsl.scenario.portrayal.ADSLAccessNetworkScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.han.element.link.Connection;
import es.upm.dit.gsi.shanks.model.han.scenario.HANScenario;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.AlreadyConnectedScenarioException;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.NonGatewayDeviceException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 *
 */
public class ADSLAccesNetworkScenario extends ComplexScenario {


    public static final String STORM = "STORM";
    public static final String EARTHQUAKE = "EARTHQUAKE";
    public static final String SUNNY = "SUNNY";
	
	public ADSLAccesNetworkScenario(String type, String initialState,
			Properties properties)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, NonGatewayDeviceException,
			AlreadyConnectedScenarioException, SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		super(type, initialState, properties);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addNetworkElements()
	 */
	@Override
	public void addNetworkElements()
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedIDException {
		
		DSLAM dslam1 = new DSLAM("DSLAM zone 1");
		DSLAM dslam2 = new DSLAM("DSLAM zone 2");
		
		CopperPair global = new CopperPair("global link");
		
		dslam1.connectToDeviceWithLink(dslam2, global);
		
		ADSLModem modem1 = new ADSLModem("ADSL modem 1-1");
		ADSLModem modem2 = new ADSLModem("ADSL modem 2-1");
		ADSLModem modem3 = new ADSLModem("ADSL modem 3-2");
		ADSLModem modem4 = new ADSLModem("ADSL modem 4-2");
		
		CopperPair cp11 = new CopperPair("CP1-1");
		CopperPair cp21 = new CopperPair("CP2-1");
		CopperPair cp32 = new CopperPair("CP3-2");
		CopperPair cp42 = new CopperPair("CP4-2");
		
		modem1.connectToDeviceWithLink(dslam1, cp11);
		modem2.connectToDeviceWithLink(dslam1, cp21);
		modem3.connectToDeviceWithLink(dslam2, cp32);
		modem4.connectToDeviceWithLink(dslam2, cp42);
		
//		EthernetCable eth1 = new EthernetCable("eth-adslModem-1", 0.5);
//		EthernetCable eth2 = new EthernetCable("eth-adslModem-2", 0.5);
//		EthernetCable eth3 = new EthernetCable("eth-adslModem-3", 0.5);
////		EthernetCable eth4 = new EthernetCable("eth-adslModem-4", 0.5);
//		
//		modem1.connectToLink(eth1);
//		modem2.connectToLink(eth2);
//		modem3.connectToLink(eth3);
//		modem4.connectToLink(eth4);

		this.addNetworkElement(dslam1);
		this.addNetworkElement(dslam2);
		this.addNetworkElement(global);
		this.addNetworkElement(modem1);
		this.addNetworkElement(modem2);
		this.addNetworkElement(modem3);
		this.addNetworkElement(modem4);
		this.addNetworkElement(cp11);
		this.addNetworkElement(cp21);
		this.addNetworkElement(cp32);
		this.addNetworkElement(cp42);
//        this.addNetworkElement(eth1);
//        this.addNetworkElement(eth2);
//        this.addNetworkElement(eth3);
//        this.addNetworkElement(eth4);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
	 */
	@Override
	public void addPossibleFailures() {
        this.addPossibleFailure(LostSynchronismFailure.class, this.getNetworkElement("ADSL modem 1-1"));
        this.addPossibleFailure(LostSynchronismFailure.class, this.getNetworkElement("ADSL modem 2-1"));
        this.addPossibleFailure(LostSynchronismFailure.class, this.getNetworkElement("ADSL modem 3-2"));
        this.addPossibleFailure(LostSynchronismFailure.class, this.getNetworkElement("ADSL modem 4-2"));
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario2DPortrayal()
	 */
	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new ADSLAccessNetworkScenario2DPortrayal(this, 200, 200);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario3DPortrayal()
	 */
	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#getPenaltiesInStatus(java.lang.String)
	 */
	@Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws UnsupportedScenarioStatusException {

        if (status.equals(ADSLAccesNetworkScenario.STORM)) {
            return this.getStormPenalties();
        } else if (status.equals(ADSLAccesNetworkScenario.EARTHQUAKE)) {
            return this.getEarthquakePenalties();
        } else if (status.equals(ADSLAccesNetworkScenario.SUNNY)) {
            return this.getSunnyPenalties();
        } else {
            throw new UnsupportedScenarioStatusException();
        }

    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getSunnyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(LostSynchronismFailure.class, 1.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getEarthquakePenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(LostSynchronismFailure.class, 10.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getStormPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(LostSynchronismFailure.class, 3.0);

        return penalties;
    }

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
        this.addPossibleStatus(ADSLAccesNetworkScenario.STORM);
        this.addPossibleStatus(ADSLAccesNetworkScenario.EARTHQUAKE);
        this.addPossibleStatus(ADSLAccesNetworkScenario.SUNNY);
	}

	@Override
	public void addScenarios() throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, NonGatewayDeviceException,
			AlreadyConnectedScenarioException, SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {

        Properties p = this.getProperties();
        this.addScenario(HANScenario.class, "HAN1", HANScenario.STATUS_NORMAL, p,
                "Router", "eth-adslModem-1");
        this.addScenario(HANScenario.class, "HAN2", HANScenario.STATUS_NORMAL, p,
                "Router", "eth-adslModem-2");
        this.addScenario(HANScenario.class, "HAN3", HANScenario.STATUS_NORMAL, p,
                "Router", "eth-adslModem-3");
        this.addScenario(HANScenario.class, "HAN4", HANScenario.STATUS_NORMAL, p,
                "Router", "eth-adslModem-4");
	}

}
