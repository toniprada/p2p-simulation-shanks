/**
 * 
 */
package com.toniprada.tsag.p2p.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import com.toniprada.tsag.p2p.model.element.device.Client;
import com.toniprada.tsag.p2p.model.element.device.Server;
import com.toniprada.tsag.p2p.model.element.link.Connection;

import sim.util.Bag;
import unbbayes.prs.bn.ProbabilisticNetwork;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.agent.capability.perception.PercipientShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.perception.ShanksAgentPerceptionCapability;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.BayesianReasonerShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.ShanksAgentBayesianReasoningCapability;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
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
	private Client computer;
	private Location location;
	private ProbabilisticNetwork bn;
	private int errors;

	private ArrayList<Link> clientsConnected;


	public UserAgent(String id, Client computer, Location location) {
		super(id);
		this.computer = computer;
		this.location = location;
		errors = 0;
		clientsConnected = new ArrayList<Link>();
		try {
			ShanksAgentBayesianReasoningCapability.loadNetwork(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		Random r = new Random();
		int rand = r.nextInt(1000);
		try {
			// Randomness to take some decisions
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
			// State of the link to server
			String status = computer.getCurrentStatus();
			if (!status.equals(Client.STATUS_OFF)) {
				String s = getLinkIfExists(computer, computer.getID(), "Server").getCurrentStatus();
				if (s.equals(Connection.STATUS_OVERLOADED)) {
					computer.updateStatusTo(Client.STATUS_OVERLOADED);
				} else if (s.equals(Connection.STATUS_CONNECTED)) {
					computer.updateStatusTo(Client.STATUS_ON);
				}
			}
			// Check if any p2p connection has been closed, 
			// in this case will be necessary to 
			// increase the stream from the server
			ArrayList<Link> linksNow = new ArrayList<Link>();
			List<Link> links = computer.getLinks();
			for (Link l : links) {
				if (!l.getCurrentStatus().equals(Connection.STATUS_DISCONNECTED)
						&& !l.getID().contains("Server")) {
					linksNow.add(l);
				}
			}
			if (!status.equals(Client.STATUS_OFF)) {
				ArrayList<Link> linksToDelete = new ArrayList<Link>();
				for (Link l : clientsConnected) {
					if (!containsLink(linksNow, l) && !l.getID().contains("Server")) {
						Connection linkServer = (Connection) getLinkIfExists(
								computer, computer.getID(), "Server");
						linkServer.changeUsage(+(Connection.UPLOAD));
						logger.info(computer.getID() + " ++upload: " + l.getID());
						linksToDelete.add(l);
						errors++;
					}
				}
				for (Link l : linksToDelete) {
					clientsConnected.remove(l);
				}
			}
//			clientsConnected = linksNow;
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
				//TODO Los clientes que sean "mobile" no se conectan en P2P
				ShanksAgentBayesianReasoningCapability.addEvidence(this,
						"network", "desktop");
			}
			HashMap<String, HashMap<String, Float>> hypotheses = ShanksAgentBayesianReasoningCapability
					.getAllHypotheses(this);
			// Choose action
			if (hypotheses.get("EnableP2PAction").get("true") >= 0.7) {
				connectP2P(simulation);
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
	
	private boolean containsLink(List<Link> linksNow, Link link) {
		for (Link l : linksNow) {
			List<Device> devices = l.getLinkedDevices();
			if (devices.size() == 2) {
				String s1 = devices.get(0).getID();
				String s2 = devices.get(1).getID();
				if (s1.contains("Server") || s2.contains("Server")
						|| link.getID().equals(s1 + "-" + s2)
						|| link.getID().equals(s2 + "-" + s1)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void connectToServer(ShanksSimulation simulation)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		Connection link = (Connection) getLinkIfExists(computer, computer.getID(), "Server");
		if (link != null) {
			// Link already created, enable
			link.setCurrentStatus(Connection.STATUS_CONNECTED);
			link.changeUsage(+Server.STREAMING_BANDWIDTH);
		} else {
			// Link doesnt exit, create
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

	private void connectP2P(ShanksSimulation simulation)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		//	Number of connections that the client has established
		int connections = 0;
		List<Link> links = computer.getLinks();
		for (Link l : links) {
			if (!l.getCurrentStatus().equals(Connection.STATUS_DISCONNECTED)) {
				connections++;
			}
		}
		// Search for another clients around
		Bag objects = ShanksAgentPerceptionCapability.getPercepts(simulation, this);
		for (Object o : objects) {
			if (o instanceof Client) {
				// If we can establish more connections
				if (connections < Client.MAX_CONNECTIONS) {
					Client neighbour = (Client) o;
					if (!neighbour.getCurrentStatus().equals(Client.STATUS_OFF)
							&& !neighbour.getID().equals(computer.getID())) {
						// Number of connections that the other client has established
						int neighbourConnections = 0;
						links = neighbour.getLinks();
						for (Link l : links) {
							if (!l.getCurrentStatus().equals(Connection.STATUS_DISCONNECTED)) {
								neighbourConnections++;
							}
						}
						// If it can establish more connections
						if (neighbourConnections < Client.MAX_CONNECTIONS) {
							Link link = getLinkIfExists(computer,
									computer.getID(), neighbour.getID());
							if (link != null) {
								// Link exits
								if (!link.getCurrentStatus().equals(Connection.STATUS_CONNECTED)) {
									link.setCurrentStatus(Connection.STATUS_CONNECTED);
									Connection linkServer = (Connection) 
											getLinkIfExists(computer, computer.getID(), "Server");
									linkServer.changeUsage(-Connection.UPLOAD);
									logger.info("1------------- " + 
										computer.getID() + neighbour.getID());
									connections++;
									clientsConnected.add(link);
								}
							} else {
								// Link doesnt exits
								try {
									link = new Connection(computer.getID() + "-" + neighbour.getID());
									computer.connectToDeviceWithLink(neighbour, link);
									simulation.getScenario().addNetworkElement(link);
									Scenario2DPortrayal p = (Scenario2DPortrayal) simulation.getScenarioPortrayal();
									p.drawLink(link);
									Connection linkServer = (Connection) getLinkIfExists(computer, 
											computer.getID(), "Server");
									linkServer.changeUsage(-Connection.UPLOAD);
									logger.info("2-------------- " + computer.getID() + 
											neighbour.getID());
									connections++;
									clientsConnected.add(linkServer);
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
//		logger.info("closeeeeeeeeee" + computer.getID());
		List<Link> links = computer.getLinks();
		for (Link link : links) {
			Connection l = (Connection) link;
			l.setCurrentStatus(Connection.STATUS_DISCONNECTED);
			l.removeUsage();
		}
		clientsConnected.clear();
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
	public void checkMail() {
//		try {
//			Message pendingMessage = this.getInbox().get(0);
//			this.getInbox().remove(pendingMessage);
//			this.serverState = (String) pendingMessage.getPropCont();
//		} catch (Exception e) {
//			logger.fine("There is no message in the inbox of the agent " + this.getID());
//		}
	}
	
	public int getErrors() {
		return this.errors;
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
		return "src/com/toniprada/tsag/p2p/agent/user.net";
	}

	@Override
	public void setBayesianNetwork(ProbabilisticNetwork bn) {
		this.bn = bn;
		
	}
	
}


