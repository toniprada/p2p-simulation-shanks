/**
 * 
 */
package com.toniprada.tsag.p2p.agent;

import java.util.List;
import java.util.logging.Logger;

import com.toniprada.tsag.p2p.model.element.device.Server;
import com.toniprada.tsag.p2p.model.element.link.Connection;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * @author a.carrera
 * 
 */
public class ServerAgent extends SimpleShanksAgent {
	
	private static final long serialVersionUID = -3377403682335115872L;
	
	private Logger logger = Logger.getLogger(ServerAgent.class.getName());
	private Server server;
	private double bandwidth;


	public ServerAgent(String id, Server server) {
		super(id);
		this.server = server;
	}


	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
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
//			logger.info("BW" + bw);
			this.bandwidth = bw;
		} catch (UnsupportedNetworkElementStatusException e) {
			logger.severe(e.getMessage());
		}

	}


	@Override
	public void checkMail() {
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


