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
	
	private int  clientsConnected;


	public UserAgent(String id, Client computer, Location location) {
		super(id);
		this.computer = computer;
		this.location = location;
		clientsConnected = 0;
		try {
			ShanksAgentBayesianReasoningCapability.loadNetwork(this);
		} catch (Exception e) {
			e.printStackTrace();
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
				} else {
					computer.setCurrentStatus(Client.STATUS_OFF);
					closeAllConnections();
				}
			}
			String status = computer.getCurrentStatus();
			if (status.equals(Client.STATUS_ON)) {
				String s = getLinkIfExists(computer, computer.getID(), "Server").getCurrentStatus();
				if (s.equals(Connection.STATUS_OVERLOADED)) {
					computer.updateStatusTo(Client.STATUS_OVERLOADED);
				} else if (s.equals(Connection.STATUS_CONNECTED)) {
					computer.updateStatusTo(Client.STATUS_ON);
				}
			}
			// Check if any connection had been closed, 
			// in this case will be necessary to 
			// increase the stream from the server
			status = computer.getCurrentStatus();
			if (!status.equals(Client.STATUS_OFF)) {
				int numberOfLinks = -1;
				List<Link> links = computer.getLinks();
				for (Link link : links) {
					if (!link.getCurrentStatus().equals(Connection.STATUS_DISCONNECTED)) {
						numberOfLinks++;
					}
				}
				if (numberOfLinks != clientsConnected) {
					Connection linkServer = (Connection) getLinkIfExists(computer, computer.getID(), "Server");
					int difference = clientsConnected - numberOfLinks;
					linkServer.changeUsage(+(difference*Connection.UPLOAD));
					// Rebuild connected clients list
					clientsConnected = numberOfLinks;
				}
			} 
//			List<Link> links = computer.getLinks();
//			int n = links.size();
//			for (Link link : links) {
//			List<Link> links = computer.getLinks();
//			int n = links.size();
//			for (Link link : links) {
//				List<Device> devices = link.getLinkedDevices();
//				if (clientsConnected.contains(devices.get(0).getID() + "-"
//						+ devices.get(1).getID())
//						|| clientsConnected.contains(devices.get(1).getID()
//								+ "-" + devices.get(0).getID())) {
//					n--;
//				}
//			}
//			n --; // There is a connection with the server
//			if (n > 0) {
//				Connection linkServer = (Connection) getLinkIfExists(computer, computer.getID(), "Server");
//				linkServer.changeUsage(+(n*Connection.UPLOAD));
//				// Rebuild connected clients list
//				clientsConnected.clear();
//				for (Link link : links) {
//					if (!link.getID().contains("Server")) {
//						clientsConnected.add(link.getID());
//					}
//				}
//			}
			// Clear all percepts
			ShanksAgentBayesianReasoningCapability.clearEvidences(this);
			status = computer.getCurrentStatus();
			if (!status.equals(Client.STATUS_OFF)) {
				if (status.equals(Client.STATUS_OVERLOADED)) {
					ShanksAgentBayesianReasoningCapability.addEvidence(this,
							"overload", "true");
				} else {
					ShanksAgentBayesianReasoningCapability.addEvidence(this,
							"overload", "false");
				}
				//TODO desktop-mobile
				ShanksAgentBayesianReasoningCapability.addEvidence(this,
						"network", "desktop");
			}
			HashMap<String, HashMap<String, Float>> hypotheses = ShanksAgentBayesianReasoningCapability
					.getAllHypotheses(this);
			// Choose action
			if (hypotheses.get("EnableP2PAction").get("true") >= 0.7) {
				connectToNeighbours(simulation);
			}
		} catch (UnsupportedNetworkElementStatusException e) {
			e.printStackTrace();
		} catch (TooManyConnectionException e) {
			e.printStackTrace();
		} catch (DuplicatedPortrayalIDException e) {
			e.printStackTrace();
		} catch (ScenarioNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void connectToServer(ShanksSimulation simulation)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		Connection link = (Connection) getLinkIfExists(computer, computer.getID(), "Server");
		if (link != null) {
			link.setCurrentStatus(Connection.STATUS_CONNECTED);
			link.changeUsage(+Server.STREAMING_BANDWIDTH);
		} else {
			try {
				Device server = (Device) simulation.getScenario().getNetworkElement("Server");
				link = new Connection(computer.getID() + "-Server");
				computer.connectToDeviceWithLink(server, link);
				link.changeUsage(+Server.STREAMING_BANDWIDTH);
				simulation.getScenario().addNetworkElement(link);
				Scenario2DPortrayal p = (Scenario2DPortrayal) simulation.getScenarioPortrayal();
				p.drawLink(link);
			} catch (DuplicatedIDException e) {
				e.printStackTrace();
			}
		}
	}

	private void connectToNeighbours(ShanksSimulation simulation)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
//		 Connection of the client
		int connections = 0;
		List<Link> links = computer.getLinks();
		for (Link l : links) {
			if (!l.getCurrentStatus().equals(Connection.STATUS_DISCONNECTED)) {
				connections++;
			}
		}
		Bag objects = ShanksAgentPerceptionCapability.getPercepts(simulation, this);
		for (Object o : objects) {
			if (o instanceof Client) {
				if (connections < Client.MAX_CONNECTIONS) {
					Client neighbour = (Client) o;
					if (!neighbour.getCurrentStatus().equals(Client.STATUS_OFF)) {
//						// Connections of the destiny
						int neighbourConnections = 0;
						links = neighbour.getLinks();
						for (Link l : links) {
							if (!l.getCurrentStatus().equals(
									Connection.STATUS_DISCONNECTED)) {
								neighbourConnections++;
							}
						}
						if (neighbourConnections < Client.MAX_CONNECTIONS) {
							Link link = getLinkIfExists(computer,
									computer.getID(), neighbour.getID());
							if (link != null) {
								if (!link.getCurrentStatus().equals(Connection.STATUS_CONNECTED)) {
									link.setCurrentStatus(Connection.STATUS_CONNECTED);
									Connection linkServer = (Connection) getLinkIfExists(computer, computer.getID(), "Server");
									if (linkServer != null) {
										linkServer.changeUsage(-Connection.UPLOAD);
									}
									connections++;
									clientsConnected++;
								}
							} else {
								try {
									link = new Connection(computer.getID() + "-" + neighbour.getID());
									computer.connectToDeviceWithLink(neighbour, link);
									simulation.getScenario().addNetworkElement(link);
									Scenario2DPortrayal p = (Scenario2DPortrayal) simulation.getScenarioPortrayal();
									p.drawLink(link);
									Connection linkServer = (Connection) getLinkIfExists(computer, computer.getID(), "Server");
									if (linkServer != null) {
										linkServer.changeUsage(-Connection.UPLOAD);
									}
									connections++;
									clientsConnected++;
								} catch (DuplicatedIDException e) {
									e.printStackTrace();
								}
							}
						}
					}
				} else {
					break;
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
		clientsConnected = 0;
	}
	
	private Link getLinkIfExists(Device device, String id1, String id2) {
		String linkId1 = id1 + "-" + id2;
		String linkId2 = id2 + "-" + id1;
		List<Link> links = device.getLinks();
		for (Link l : links) {
			if (l.getID().equals(linkId1)) {
				return l;
			} else if (l.getID().equals(linkId2)) {
				return l;
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
	
}


