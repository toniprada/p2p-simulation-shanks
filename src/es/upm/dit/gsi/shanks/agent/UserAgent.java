/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.model.han.element.link.ADSLCable;
import es.upm.dit.gsi.shanks.model.han.element.link.P2pADSLConnection;
import es.upm.dit.gsi.shanks.model.han.element.link.ServerADSLConnection;

/**
 * @author a.carrera
 * 
 */
public class UserAgent extends SimpleShanksAgent {

	private Logger logger = Logger.getLogger(UserAgent.class.getName());

	private Computer computer;

	/**
     * 
     */
	private static final long serialVersionUID = 263836274462865563L;

	public UserAgent(String id, Computer computer) {
		super(id);
		this.computer = computer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.agent.ShanksAgent#checkMail()
	 */
	@Override
	public void checkMail() {
		// Ignore all mails
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
					computer.setCurrentStatus(Computer.STATUS_WAITING);
					List<Link> links = computer.getLinks();
					for (Link link : links) {
						if (link instanceof ServerADSLConnection) {
							link.setCurrentStatus(ADSLCable.STATUS_CONNECTED);
						}
//						else if (link instanceof P2pADSLConnection) {
//							if (isValidLink(link)){
//								link.setCurrentStatus(ADSLCable.STATUS_CONNECTED);
//							}
//						}
					}
				} else if (status.equals(Computer.STATUS_WAITING)) {
					computer.setCurrentStatus(Computer.STATUS_DOWNLOADING);
				} else {
					computer.setCurrentStatus(Computer.STATUS_OFF);
					List<Link> links = computer.getLinks();
					for (Link link : links) {
						if (link instanceof ServerADSLConnection) {
							link.setCurrentStatus(ADSLCable.STATUS_DISCONNECTED);
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
		}
	}
	
	public boolean isValidLink(Link link) {
		List<Device> devices = link.getLinkedDevices();
		for (Device d : devices) {
			if (d.getCurrentStatus() == Computer.STATUS_OFF) {
				return false;
			}
		}
		return true;
	}
	
	
}


