/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.han.element.device.Server;

/**
 * @author a.carrera
 * 
 */
public class ServerAgent extends SimpleShanksAgent {
	
	private static final long serialVersionUID = -3377403682335115872L;
	
	private Logger logger = Logger.getLogger(ServerAgent.class.getName());
	private Server server;
	private String serverStatus;


	public ServerAgent(String id, Server server) {
		super(id);
		this.server = server;
		serverStatus = Server.STATUS_OK;
	}


	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		if (!server.getCurrentStatus().equals(serverStatus)) {
			if (server.getCurrentStatus().equals(Server.STATUS_OVERLOADED)) {
				// TODO message overload
			} else {
				// TODO message ok
			}
			serverStatus = server.getCurrentStatus();
		}
	}


	@Override
	public void checkMail() {
		// TODO Auto-generated method stub
		
	}
	
}


