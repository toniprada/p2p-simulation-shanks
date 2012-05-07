/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import java.util.List;
import java.util.logging.Logger;

import sim.util.Bag;
import sim.util.Double2D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.capability.movement.Location;
import es.upm.dit.gsi.shanks.agent.capability.movement.MobileShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.movement.ShanksAgentMovementCapability;
import es.upm.dit.gsi.shanks.agent.capability.perception.PercipientShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.perception.ShanksAgentPerceptionCapability;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.han.element.link.ADSLCable;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;

/**
 * @author a.carrera
 * 
 */
public class CatAgent extends SimpleShanksAgent implements MobileShanksAgent, PercipientShanksAgent {

	private Logger logger = Logger.getLogger(CatAgent.class.getName());

	private Location currentLocation;
	private Location targetLocation;
	private Double speed;
	private boolean allowToMove;
	private double threshold;
	private double perceptionRange;

	private boolean isFrightened;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5906067568833022855L;

	/**
	 * Create a cat agent
	 * 
	 * @param id
	 *            Name of the cat
	 * @param threshold
	 *            how bored is the cat? 0<x<1
	 */
	public CatAgent(String id, double threshold, double speed) {
		super(id);
		this.speed = speed;
		this.threshold = threshold;
		this.perceptionRange = 10.0;
		this.allowToMove = true;
		this.currentLocation = new Location();
		this.targetLocation = new Location();
		this.setFrightened(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.agent.ShanksAgent#checkMail()
	 */
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
					Location location = new Location(new Double2D(50, 50));
					ShanksAgentMovementCapability.updateLocation(simulation,
							this, location);
				}
			} else if (simulation.getScenarioPortrayal() instanceof Scenario3DPortrayal) {
				if (currentLocation.getLocation3D() == null) {
					Location location = new Location(new Double3D(50, 50, 50));
					ShanksAgentMovementCapability.updateLocation(simulation,
							this, location);
				}
			}
			
		} catch (Exception e) {
			logger.severe("Exception: " + e.getMessage());
		}
		try {
			this.startMovement();
			// Step 0 Run, run, run... :)
			if (this.isFrightened) {
				Location loc = this.getCurrentLocation();
				Location target = new Location();
				if (loc.is2DLocation()) {
					Double2D frightened = new Double2D(Math.random() * 100,
							Math.random() * 100);	
					target.setLocation2D(frightened);
				} else if (loc.is3DLocation()) {
					Double3D frightened = new Double3D(Math.random() * 100,
							Math.random() * 100, Math.random() * 100);
					target.setLocation3D(frightened);
				}
				this.setTargetLocation(target);
				ShanksAgentMovementCapability.goTo(simulation, this, this.currentLocation,
						this.targetLocation, this.getSpeed() * 20);
				if (Math.random() < 0.5) {
					this.setFrightened(false);
				}
			} else {
				// 1st step of the reasoning cycle -> It bites if it is near to
				// a
				// device

				Bag objects = ShanksAgentPerceptionCapability.getPercepts(simulation, this);
				for (Object o : objects) {
					if (o instanceof Device) { // If it is a device, the cat
												// tries
												// to bite
						Location objectLocation = ShanksAgentPerceptionCapability.getObjectLocation(simulation, this, o);
						if (this.currentLocation.isNearTo(objectLocation, 1)) {
							if (Math.random() < this.threshold) {
								Device nearDevice = (Device) o;
								logger.warning(nearDevice.getID());
//								List<Link> links = nearDevice.getLinks();
//								for (Link link : links) {
//									if (link instanceof ADSLCable) {
//										this.bitesLink((ADSLCable) link);
//										break;
//									}
//								}
							}
							break;
						}
					} else if (o instanceof BoyAgent
							|| o instanceof MotherAgent) {
						// If it is a human, the cat stops the movement and
						// hides its guilt
						Location agentLocation = ShanksAgentPerceptionCapability.getObjectLocation(simulation, this, o);
						if (this.currentLocation.isNearTo(agentLocation, 3)) {
							this.stopMovement();
							break;
						}
					}
				}

				// 2nd step of the reasoning cycle -> Find new target
				if (simulation.schedule.getSteps() % 1000 == 0) { // Each 1000
																	// steps
					Location randomObjLocation = ShanksAgentPerceptionCapability.getRandomObjectLocation(simulation, this);
					this.setTargetLocation(randomObjLocation);
				}

				// 3rd step of the reasoning cycle -> Move if it is possible
				if (this.allowToMove) {
					ShanksAgentMovementCapability.goTo(simulation, this,
							this.currentLocation, this.targetLocation, this.speed);
				}
			}
		} catch (Exception e) {
			logger.warning("Exception in the reasoning cycle -> " + e.getMessage());
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.agent.movement.Mobile2DShanksAgent#setSpeed(java
	 * .lang.Double)
	 */
	@Override
	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.agent.movement.Mobile2DShanksAgent#stopMovement()
	 */
	@Override
	public void stopMovement() {
		this.allowToMove = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.agent.movement.Mobile2DShanksAgent#startMovement()
	 */
	@Override
	public void startMovement() {
		this.allowToMove = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#getLocation()
	 */
	@Override
	public Location getCurrentLocation() {
		return this.currentLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#getLocation()
	 */
	@Override
	public Location getTargetLocation() {
		return this.targetLocation;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#isAllowedToMove()
	 */
	@Override
	public boolean isAllowedToMove() {
		return this.allowToMove;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#getSpeed()
	 */
	@Override
	public double getSpeed() {
		return this.speed;
	}

	/**
	 * @return the isFrightened
	 */
	public boolean isFrightened() {
		return isFrightened;
	}

	/**
	 * @param isFrightened
	 *            the isFrightened to set
	 */
	public void setFrightened(boolean isFrightened) {
		this.isFrightened = isFrightened;
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.agent.perception.PercipientShanksAgent#getPerceptionRange()
	 */
	@Override
	public double getPerceptionRange() {
		return this.perceptionRange;
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#setCurrentLocation(es.upm.dit.gsi.shanks.agent.movement.Location)
	 */
	@Override
	public void setCurrentLocation(Location location) {
		this.currentLocation = location;
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#setTargetLocation(es.upm.dit.gsi.shanks.agent.movement.Location)
	 */
	@Override
	public void setTargetLocation(Location location) {
		this.targetLocation = location;
	}

}
