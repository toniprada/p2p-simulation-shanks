/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import jason.asSemantics.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import sim.util.Bag;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.agent.capability.perception.PercipientShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.perception.ShanksAgentPerceptionCapability;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.model.han.element.device.Server;
import es.upm.dit.gsi.shanks.model.han.element.link.ADSLConnection;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 * 
 */
public class UserAgent extends SimpleShanksAgent implements PercipientShanksAgent {
	
	private static final long serialVersionUID = 263836274462865563L;
	public static final double PERCEPTION_RANGUE = 50.0;

	private Logger logger = Logger.getLogger(UserAgent.class.getName());
	private String serverState = Server.STATUS_OK;
	private Computer computer;
	private Location location;


	public UserAgent(String id, Computer computer, Location location) {
		super(id);
		this.computer = computer;
		this.location = location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.agent.ShanksAgent#checkMail()
	 */
	@Override
	public void checkMail() {
		try {
			Message pendingMessage = this.getInbox().get(0);
			this.getInbox().remove(pendingMessage);
			this.serverState = (String) pendingMessage.getPropCont();
		} catch (Exception e) {
			logger.fine("There is no message in the inbox of the agent "
					+ this.getID());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.agent.SimpleShanksAgent#executeReasoningCycle(es
	 * .upm.dit.gsi.shanks.ShanksSimulation)
	 */
	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		
		Random r = new Random();
		int rand = r.nextInt(1000);
		try {
			if (rand == 0) {
				String status = computer.getCurrentStatus();
				if (status.equals(Computer.STATUS_OFF)) {
					computer.setCurrentStatus(Computer.STATUS_ON);
					connectToServer(simulation);
					connectToNeighbours(simulation);
				}  else {
					computer.setCurrentStatus(Computer.STATUS_OFF);
					closeAllConnections();
				}
			}
		} catch (UnsupportedNetworkElementStatusException e) {
			logger.severe(e.getMessage());
		} catch (TooManyConnectionException e) {
			logger.severe(e.getMessage());
		} catch (DuplicatedPortrayalIDException e) {
			logger.severe(e.getMessage());
		} catch (ScenarioNotFoundException e) {
			logger.severe(e.getMessage());
		} 
	}
	
	private void connectToServer(ShanksSimulation simulation)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		String linkId = computer.getID() + "-Server";
		Link link = getLinkIfExists(computer, linkId);
		if (link != null) {
			link.setCurrentStatus(ADSLConnection.STATUS_CONNECTED);
			HashMap properties = new HashMap<String, Double>();
			properties.add(ADSLConnection.PROPERTY_BANDWIDTH_USAGE, Server.STREAMING_BANDWIDTH);
			link.setProperties(properties)
		} else {
			try {
				Device server = (Device) simulation.getScenario()
						.getNetworkElement("Server");
				link = new ADSLConnection(linkId);
				computer.connectToDeviceWithLink(server, link);
				simulation.getScenario().addNetworkElement(link);
				Scenario2DPortrayal p = (Scenario2DPortrayal) simulation
						.getScenarioPortrayal();
				p.drawLink(link);
			} catch (DuplicatedIDException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	private void connectToNeighbours(ShanksSimulation simulation)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		Bag objects = ShanksAgentPerceptionCapability.getPercepts(simulation,
				this);
		for (Object o : objects) {
			if (o instanceof Computer) {
				Computer neighbour = (Computer) o;
				if (neighbour.getCurrentStatus().equals(Computer.STATUS_ON)) {
					String linkId = computer.getID() + "-" + neighbour.getID();
					Link link = getLinkIfExists(computer, linkId);
					if (link != null) {
						link.setCurrentStatus(ADSLConnection.STATUS_CONNECTED);
					} else {
						try {
							link = new ADSLConnection(linkId);
							computer.connectToDeviceWithLink(neighbour, link);
							simulation.getScenario().addNetworkElement(link);
							Scenario2DPortrayal p = (Scenario2DPortrayal) simulation
									.getScenarioPortrayal();
							p.drawLink(link);
						} catch (DuplicatedIDException e) {
							logger.severe(e.getMessage());
						}
					}
				}
			}
		}
	}
	
	private void closeAllConnections() throws UnsupportedNetworkElementStatusException {
		List<Link> links = computer.getLinks();
		for (Link link : links) {
			link.setCurrentStatus(ADSLConnection.STATUS_DISCONNECTED);
		}
	}
	
	private Link getLinkIfExists(Device device, String linkId) {
		List<Link> links = device.getLinks();
		for (Link link : links) {
			if (link.getID().equals(linkId)) {
				return link;
			}
		}
		return null;
	}
	
	
	@Override
	public Location getCurrentLocation() {
		return location;
	}

	@Override
	public double getPerceptionRange() {
		return PERCEPTION_RANGUE;
	}
	
	
	
//	public boolean isValidLink(Link link) {
//		List<Device> devices = link.getLinkedDevices();
//		for (Device d : devices) {
//			if (d.getCurrentStatus() == Computer.STATUS_OFF) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	
}


