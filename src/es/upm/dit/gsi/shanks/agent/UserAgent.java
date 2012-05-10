/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import jason.asSemantics.Message;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.agent.capability.perception.PercipientShanksAgent;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.model.han.element.link.ADSLCable;
import es.upm.dit.gsi.shanks.model.han.element.link.ServerADSLConnection;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 * 
 */
public class UserAgent extends SimpleShanksAgent implements PercipientShanksAgent {
	
	public static final double PERCEPTION_RANGUE = 5.0;

	private Logger logger = Logger.getLogger(UserAgent.class.getName());

	private Computer computer;
	private Location location;

	/**
     * 
     */
	private static final long serialVersionUID = 263836274462865563L;

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
			//this.targetLocation = (Location) pendingMessage.getPropCont();
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
					computer.setCurrentStatus(Computer.STATUS_DOWNLOADING);
//					List<Link> links = computer.getLinks();
//					for (Link link : links) {
//						if (link instanceof ServerADSLConnection) {
//							link.setCurrentStatus(ADSLCable.STATUS_CONNECTED);
//						}
//						else if (link instanceof P2pADSLConnection) {
//							if (isValidLink(link)){
//								link.setCurrentStatus(ADSLCable.STATUS_CONNECTED);
//							}
//						}
//					}
					
					Device server = (Device) simulation.getScenario().getNetworkElement("Server");
					ADSLCable link = new ServerADSLConnection("Cable " + computer.getID() + "-Server");
					computer.connectToDeviceWithLink(server, link);
					Scenario2DPortrayal p = (Scenario2DPortrayal) simulation.getScenarioPortrayal(); 
					p.drawLink(link);
				}  else {
					computer.setCurrentStatus(Computer.STATUS_OFF);
					// Remove all connections
					List<Link> links = computer.getLinks();
					for (Link link : links) {
						simulation.getScenario().removeNetworkElement(link);
						Scenario2DPortrayal p = (Scenario2DPortrayal) simulation.getScenarioPortrayal(); 
						Network net = p.getLinks();
						Bag nodes = net.allNodes;
						for (int i = 0; i < nodes.size(); i++) {
							Edge edge = (Edge) net.getEdges(computer, nodes).objs[i];
							Link l = (Link) edge.getInfo();
							if (l.getID().equals(link.getID())) {
								net.removeEdge(edge);
							}
						}
					}
					
//					else if (link instanceof P2pADSLConnection) {
//							link.setCurrentStatus(ADSLCable.STATUS_DISCONNECTED);
//						}
//					}
				}
			}
		} catch (UnsupportedNetworkElementStatusException e) {
			// TODO
			e.printStackTrace();
		} catch (TooManyConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicatedPortrayalIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScenarioNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


