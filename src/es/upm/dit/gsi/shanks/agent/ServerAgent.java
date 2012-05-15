/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.han.element.device.Server;
import es.upm.dit.gsi.shanks.model.han.element.link.Connection;

/**
 * @author a.carrera
 * 
 */
public class ServerAgent extends SimpleShanksAgent {
	
	private static final long serialVersionUID = -3377403682335115872L;
	
	private Logger logger = Logger.getLogger(ServerAgent.class.getName());
	private Server server;
	private double bandwidth;
//	private String serverStatus;


	public ServerAgent(String id, Server server) {
		super(id);
		this.server = server;
//		serverStatus = Server.STATUS_OK;
	}


	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
//		if (!server.getCurrentStatus().equals(serverStatus)) {
//			if (server.getCurrentStatus().equals(Server.STATUS_OVERLOADED)) {
//				// TODO message overload
//			} else {
//				// TODO message ok
//			}
//			serverStatus = server.getCurrentStatus();
//		}
		try {
			double bw = 0.0;
			List<Link> links = server.getLinks();
			for (Link link : links) {
				Connection l = (Connection) link;
				bw += l.getUsage();		
			}
			if ((Server.MAX_BANDWIDTH - bw) > Server.THRESHOLD) {
				server.updateStatusTo(Server.STATUS_OK);
				for (Link link : links) {
					Connection l = (Connection) link;
					if (l.getCurrentStatus().equals(Connection.STATUS_OVERLOADED)) {
						l.updateStatusTo(Connection.STATUS_CONNECTED);
					}
				}
			} else {
				server.updateStatusTo(Server.STATUS_OVERLOADED);
				for (Link link : links) {
					Connection l = (Connection) link;
					if (l.getCurrentStatus().equals(
							Connection.STATUS_CONNECTED)) {
						l.updateStatusTo(Connection.STATUS_OVERLOADED);
					}
				}
			}	
			logger.info("BW" + bw);
			this.bandwidth = bw;
		} catch (UnsupportedNetworkElementStatusException e) {
			logger.severe(e.getMessage());
		}

	}


	@Override
	public void checkMail() {
		// TODO Auto-generated method stub
		
	}
	
	
	public double getBandwidth() {
		return bandwidth;
	}
	
	public double getOverload(){
		if (bandwidth > Server.MAX_BANDWIDTH ) {
			return (bandwidth - Server.MAX_BANDWIDTH);
		} else {
			return 0;
		}
	}
	
}


