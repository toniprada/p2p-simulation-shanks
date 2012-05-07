/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import java.util.logging.Logger;

import sim.util.Bag;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.agent.capability.movement.MobileShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.movement.ShanksAgentMovementCapability;
import es.upm.dit.gsi.shanks.agent.capability.perception.PercipientShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.perception.ShanksAgentPerceptionCapability;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.han.element.link.ADSLCable;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;

/**
 * @author Antonio Prada
 * 
 */
public class UserAgent2 extends SimpleShanksAgent implements PercipientShanksAgent, MobileShanksAgent {

	private Logger logger = Logger.getLogger(UserAgent2.class.getName());

	private Double2D coordinates;
	private Location currentLocation;


	/**
	 * 
	 */
	private static final long serialVersionUID = -5906067568833022855L;

	public UserAgent2(String id, Double2D coordinates) {
		super(id);
		this.currentLocation = new Location();
		this.coordinates = coordinates;
	}


	@Override
	public void checkMail() {
		// Ignore all messages
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
		try {
			if (simulation.getScenarioPortrayal() instanceof Scenario2DPortrayal) {
				if (currentLocation.getLocation2D() == null) {
					Location location = new Location(coordinates);
					ShanksAgentMovementCapability.updateLocation(simulation,
							this, location);
				}
			} 
		} catch (Exception e) {
			logger.severe("Exception: " + e.getMessage());
		}
		try {
			Bag objects = ShanksAgentPerceptionCapability.getPercepts(simulation, this);
			for (Object o : objects) {
				if (o instanceof Device) { 
					Location objectLocation = ShanksAgentPerceptionCapability.getObjectLocation(simulation, this, o);
					if (this.currentLocation.isNearTo(objectLocation, 1)) {
						Device nearDevice = (Device) o;
						logger.warning(nearDevice.getID());
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.warning("Exception in the reasoning cycle -> " + e.getMessage());
			e.printStackTrace();
		}

	}


	/**
	 * The cat bites a link
	 * 
	 * @param link
	 */
	private void bitesLink(ADSLCable link) {
		try {
			logger.warning("The cat is biting the link " + link.getID()
					+ "!!!!");
			if (link.getCurrentStatus().equals(ADSLCable.STATUS_CONNECTED)) {
				link.setCurrentStatus(ADSLCable.STATUS_OVERLOADED);
			} else if (link.getCurrentStatus().equals(
					ADSLCable.STATUS_OVERLOADED)) {
				// Good solution -> Generate an event/failure
				// Now we use a workaround to change states
				link.setCurrentStatus(ADSLCable.STATUS_DISCONNECTED);
			}
		} catch (Exception e) {
			logger.severe("Exception when cat is trying to bite the link: "
					+ e.getMessage());
		}
	}


	@Override
	public Location getCurrentLocation() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public double getPerceptionRange() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Location getTargetLocation() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isAllowedToMove() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setCurrentLocation(Location arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setSpeed(Double arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setTargetLocation(Location arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void startMovement() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void stopMovement() {
		// TODO Auto-generated method stub
		
	}

}
