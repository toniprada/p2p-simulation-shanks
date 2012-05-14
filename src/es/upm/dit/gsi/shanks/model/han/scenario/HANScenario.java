/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.scenario;

import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.han.element.device.Client;
import es.upm.dit.gsi.shanks.model.han.element.device.Server;
import es.upm.dit.gsi.shanks.model.han.element.link.Connection;
import es.upm.dit.gsi.shanks.model.han.failure.BrokenFan;
import es.upm.dit.gsi.shanks.model.han.failure.NoIPFailure;
import es.upm.dit.gsi.shanks.model.han.failure.ServerOverload;
import es.upm.dit.gsi.shanks.model.han.scenario.portrayal.HANScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 * 
 */
public class HANScenario extends Scenario {

	public static final String STATUS_NORMAL = "Sunny";
	public static final String STATUS_OVERLOAD = "Rainy";
	
	public HANScenario(String id, String initialState, Properties properties)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException {
		super(id, initialState, properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addNetworkElements()
	 */
	@Override
	public void addNetworkElements()
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedIDException {
		Client computer1 = new Client("C1");
		Client computer2 = new Client("C2");
		Client computer3 = new Client("C3");
		Client computer4 = new Client("C4");
		Client computer5 = new Client("C5");
		Client computer6 = new Client("C6");
		Server server = new Server("Server", Server.STATUS_OK);

//		Connection cableComputer12 = new Connection("C1-C2");
//		Connection cableComputer13 = new Connection("C1-C3");
//		Connection cableComputer14 = new Connection("C1-C4");
//		Connection cableComputer15 = new Connection("C1-C5");
//		Connection cableComputer16 = new Connection("C1-C6");
//		Connection cableComputer23 = new Connection("C2-C3");
//		Connection cableComputer24 = new Connection("C2-C4");
//		Connection cableComputer25 = new Connection("C2-C5");
//		Connection cableComputer26 = new Connection("C2-C6");
//		Connection cableComputer34 = new Connection("C3-C4");
//		Connection cableComputer35 = new Connection("C3-C5");
//		Connection cableComputer36 = new Connection("C3-C6");
//		Connection cableComputer45 = new Connection("C4-C5");
//		Connection cableComputer46 = new Connection("C4-C6");
//		Connection cableComputer56 = new Connection("C5-C6");
//		Connection cableServer1 = new Connection("C1-Server");
//		Connection cableServer2 = new Connection("C2-Server");
//		Connection cableServer3 = new Connection("C3-Server");
//		Connection cableServer4 = new Connection("C4-Server");
//		Connection cableServer5 = new Connection("C5-Server");
//		Connection cableServer6 = new Connection("C6-Server");
//
//		computer1.connectToDeviceWithLink(computer2, cableComputer12);
//		computer1.connectToDeviceWithLink(computer3, cableComputer13);
//		computer1.connectToDeviceWithLink(computer4, cableComputer14);
//		computer1.connectToDeviceWithLink(computer5, cableComputer15);
//		computer1.connectToDeviceWithLink(computer6, cableComputer16);
//		computer2.connectToDeviceWithLink(computer3, cableComputer23);
//		computer2.connectToDeviceWithLink(computer4, cableComputer24);
//		computer2.connectToDeviceWithLink(computer5, cableComputer25);
//		computer2.connectToDeviceWithLink(computer6, cableComputer26);
//		computer3.connectToDeviceWithLink(computer4, cableComputer34);
//		computer3.connectToDeviceWithLink(computer5, cableComputer35);
//		computer3.connectToDeviceWithLink(computer6, cableComputer36);
//		computer4.connectToDeviceWithLink(computer5, cableComputer45);
//		computer4.connectToDeviceWithLink(computer6, cableComputer46);
//		computer5.connectToDeviceWithLink(computer6, cableComputer56);
//		computer1.connectToDeviceWithLink(server, cableServer1);
//		computer2.connectToDeviceWithLink(server, cableServer2);
//		computer3.connectToDeviceWithLink(server, cableServer3);
//		computer4.connectToDeviceWithLink(server, cableServer4);
//		computer5.connectToDeviceWithLink(server, cableServer5);
//		computer6.connectToDeviceWithLink(server, cableServer6);

		this.addNetworkElement(computer1);
		this.addNetworkElement(computer2);
		this.addNetworkElement(computer3);
		this.addNetworkElement(computer4);
		this.addNetworkElement(computer5);
		this.addNetworkElement(computer6);
		this.addNetworkElement(server);
//		this.addNetworkElement(cableComputer12);
//		this.addNetworkElement(cableComputer13);
//		this.addNetworkElement(cableComputer14);
//		this.addNetworkElement(cableComputer15);
//		this.addNetworkElement(cableComputer16);
//		this.addNetworkElement(cableComputer23);
//		this.addNetworkElement(cableComputer24);
//		this.addNetworkElement(cableComputer25);
//		this.addNetworkElement(cableComputer26);
//		this.addNetworkElement(cableComputer34);
//		this.addNetworkElement(cableComputer35);
//		this.addNetworkElement(cableComputer36);
//		this.addNetworkElement(cableComputer45);
//		this.addNetworkElement(cableComputer46);
//		this.addNetworkElement(cableComputer56);
//		this.addNetworkElement(cableServer1);
//		this.addNetworkElement(cableServer2);
//		this.addNetworkElement(cableServer3);
//		this.addNetworkElement(cableServer4);
//		this.addNetworkElement(cableServer5);
//		this.addNetworkElement(cableServer6);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
	 */
	@Override
	public void addPossibleFailures() {
        
        
//        Set<NetworkElement> set = new HashSet<NetworkElement>();
//        set.add(this.getNetworkElement("PC"));
//        set.add(this.getNetworkElement("Ethernet PC"));
//        Set<NetworkElement> set2 = new HashSet<NetworkElement>();
//        set2.add(this.getNetworkElement("HDMI PC Monitor"));
//        List<Set<NetworkElement>> possibleCombinations = new ArrayList<Set<NetworkElement>>();
//        possibleCombinations.add(set);
//        possibleCombinations.add(set2);
//        this.addPossibleFailure(CutCable.class, possibleCombinations);
//        
//        NetworkElement router = this.getNetworkElement("Router");
//        this.addPossibleFailure(NoIPFailure.class, router);
        
//        NetworkElement pc = this.getNetworkElement("C1");
//        this.addPossibleFailure(BrokenFan.class, pc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario2DPortrayal()
	 */
	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		return new HANScenario2DPortrayal(this, 100, 100);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario3DPortrayal()
	 */
	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
//		return new HANScenario3DPortrayal(this, 100, 100, 100);
		return null;
	}
//
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.scenario.Scenario#getPenaltiesInStatus(java
	 * .lang.String)
	 */
	@Override
	public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
			String status) throws UnsupportedScenarioStatusException {
		if (status.equals(HANScenario.STATUS_OVERLOAD)) {
            return this.getRainyPenalties();
        }else if (status.equals(HANScenario.STATUS_NORMAL)){
        	return this.getSunnyPenalties();
        } else {
            throw new UnsupportedScenarioStatusException();
        }
	}

	private HashMap<Class<? extends Failure>, Double> getSunnyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(BrokenFan.class, 1.0);
        penalties.put(NoIPFailure.class, 1.0);
        penalties.put(ServerOverload.class, 1.0);

        
        return penalties;
	}

	private HashMap<Class<? extends Failure>, Double> getSnowyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(BrokenFan.class, 5.0);
        penalties.put(NoIPFailure.class, 1.0);
        penalties.put(ServerOverload.class, 1.0);

        return penalties;
	}

	private HashMap<Class<? extends Failure>, Double> getRainyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

        penalties.put(BrokenFan.class, 1.0);
        penalties.put(NoIPFailure.class, 1.0);
        penalties.put(ServerOverload.class, 1.0);

        return penalties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(HANScenario.STATUS_NORMAL);
		this.addPossibleStatus(HANScenario.STATUS_OVERLOAD);
	}

}
