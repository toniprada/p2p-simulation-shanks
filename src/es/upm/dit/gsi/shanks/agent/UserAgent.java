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
import unbbayes.prs.bn.ProbabilisticNetwork;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.agent.capability.perception.PercipientShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.perception.ShanksAgentPerceptionCapability;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.BayesianReasonerShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.ShanksAgentBayesianReasoningCapability;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.han.element.device.Client;
import es.upm.dit.gsi.shanks.model.han.element.device.Server;
import es.upm.dit.gsi.shanks.model.han.element.link.Connection;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 * 
 */
public class UserAgent extends SimpleShanksAgent implements PercipientShanksAgent, BayesianReasonerShanksAgent {
	
	private static final long serialVersionUID = 263836274462865563L;
	public static final double PERCEPTION_RANGUE = 50.0;

	private Logger logger = Logger.getLogger(UserAgent.class.getName());
	private String serverState = Server.STATUS_OK;
	private Client computer;
	private Location location;
	private ProbabilisticNetwork bn;


	public UserAgent(String id, Client computer, Location location) {
		super(id);
		this.computer = computer;
		this.location = location;
		try {
			ShanksAgentBayesianReasoningCapability.loadNetwork(this);
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
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
			logger.fine("There is no message in the inbox of the agent " + this.getID());
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
				if (status.equals(Client.STATUS_OFF)) {
					computer.setCurrentStatus(Client.STATUS_ON);
					connectToServer(simulation);
					connectToNeighbours(simulation);
				} else {
					computer.setCurrentStatus(Client.STATUS_OFF);
					closeAllConnections();
				}
			}
			String status = computer.getCurrentStatus();
			if (status.equals(Client.STATUS_ON)) {
				List<Link> links = computer.getLinks();
				for (Link link : links) {
					if (link.getID().contains("Server")) {
						String s = link.getCurrentStatus();
						if (s.equals(Connection.STATUS_OVERLOADED)) {
							computer.updateStatusTo(Client.STATUS_OVERLOADED);
						} else if (s.equals(Connection.STATUS_CONNECTED)) {
							computer.updateStatusTo(Client.STATUS_ON);
						}
					}
				}
			}
			// Clear all percepts
			ShanksAgentBayesianReasoningCapability.clearEvidences(this);
			status = computer.getCurrentStatus();
			if (!status.equals(Client.STATUS_OFF)) {
				if (status.equals(Client.STATUS_OVERLOADED)) {
					ShanksAgentBayesianReasoningCapability.addEvidence(this,
							"overload", "true");
				} else {
					ShanksAgentBayesianReasoningCapability.addEvidence(this,
							"overload", "true");
				}
				//TODO 
				ShanksAgentBayesianReasoningCapability.addEvidence(this,
						"network", "mobile");
			}
			HashMap<String, HashMap<String, Float>> hypotheses = ShanksAgentBayesianReasoningCapability
					.getAllHypotheses(this);
			// Choose action
			if (hypotheses.get("EnableP2PAction").get("true") >= 0.6) {
				connectToNeighbours(simulation);
			}
		} catch (UnsupportedNetworkElementStatusException e) {
			logger.severe(e.getMessage());
		} catch (TooManyConnectionException e) {
			logger.severe(e.getMessage());
		} catch (DuplicatedPortrayalIDException e) {
			logger.severe(e.getMessage());
		} catch (ScenarioNotFoundException e) {
			logger.severe(e.getMessage());
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}
	
	private void connectToServer(ShanksSimulation simulation)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		String linkId = computer.getID() + "-Server";
		Connection link = (Connection) getLinkIfExists(computer, linkId);
		if (link != null) {
			link.setCurrentStatus(Connection.STATUS_CONNECTED);
			link.changeUsage(+Server.STREAMING_BANDWIDTH);
//			HashMap properties = new HashMap<String, Double>();
//			properties.add(ADSLConnection.PROPERTY_BANDWIDTH_USAGE, Server.STREAMING_BANDWIDTH);
//			link.setProperties(properties)
		} else {
			try {
				Device server = (Device) simulation.getScenario().getNetworkElement("Server");
				link = new Connection(linkId);
				computer.connectToDeviceWithLink(server, link);
				link.changeUsage(+Server.STREAMING_BANDWIDTH);
				simulation.getScenario().addNetworkElement(link);
				Scenario2DPortrayal p = (Scenario2DPortrayal) simulation.getScenarioPortrayal();
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
		int connections = 0;
		for (Object o : objects) {
			if (o instanceof Client) {
				Client neighbour = (Client) o;
				if (!neighbour.getCurrentStatus().equals(Client.STATUS_OFF)) {
					String linkId = computer.getID() + "-" + neighbour.getID();
					Link link = getLinkIfExists(computer, linkId);
					if (link != null) {
						link.setCurrentStatus(Connection.STATUS_CONNECTED);
					} else {
						try {
							link = new Connection(linkId);
							List<Device> devices = link.getLinkedDevices();
							computer.connectToDeviceWithLink(neighbour, link);
							simulation.getScenario().addNetworkElement(link);
							Scenario2DPortrayal p = (Scenario2DPortrayal) simulation.getScenarioPortrayal();
							p.drawLink(link);
						} catch (DuplicatedIDException e) {
							logger.severe(e.getMessage());
						}
					}
					connections++;
					if (connections >=2) {
						break;
					}
				}
			}
		}
	}
	
	private void closeAllConnections() throws UnsupportedNetworkElementStatusException {
		List<Link> links = computer.getLinks();
		for (Link link : links) {
			Connection l = (Connection) link;
			l.setCurrentStatus(Connection.STATUS_DISCONNECTED);
			l.removeUsage();
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

	@Override
	public ProbabilisticNetwork getBayesianNetwork() {
		return bn;
	}

	@Override
	public String getBayesianNetworkFilePath() {
		return "src/es/upm/dit/gsi/shanks/agent/user.net";
	}

	@Override
	public void setBayesianNetwork(ProbabilisticNetwork bn) {
		this.bn = bn;
		
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


