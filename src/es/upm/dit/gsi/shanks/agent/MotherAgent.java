/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import jason.asSemantics.Message;

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
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;

/**
 * @author a.carrera
 * 
 */
public class MotherAgent extends SimpleShanksAgent
		implements
			MobileShanksAgent,
			PercipientShanksAgent {

	private Logger logger = Logger.getLogger(CatAgent.class.getName());

	private Location currentLocation;
	private Location targetLocation;
	private Double speed;
	private Double perceptionRange;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5906067568833022855L;

	/**
	 * Create a mom
	 * 
	 * @param id
	 *            Name of the cat
	 * @param speed
	 *            Movement speed
	 */
	public MotherAgent(String id, double speed) {
		super(id);
		this.speed = speed;
		this.currentLocation = new Location();
		this.targetLocation = new Location();
		this.perceptionRange = 5.0;
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
			this.targetLocation = (Location) pendingMessage.getPropCont();
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
		try {
			if (simulation.getScenarioPortrayal() instanceof Scenario2DPortrayal) {
				if (currentLocation.getLocation2D() == null) {
					Location location = new Location(new Double2D(80, 80));
					ShanksAgentMovementCapability.updateLocation(simulation,
							this, location);
				}
			} else if (simulation.getScenarioPortrayal() instanceof Scenario3DPortrayal) {
				if (currentLocation.getLocation3D() == null) {
					Location location = new Location(new Double3D(80, 80, 80));
					ShanksAgentMovementCapability.updateLocation(simulation,
							this, location);
				}
			}
		} catch (Exception e) {
			logger.severe("Exception: " + e.getMessage());
		}
		
		try {
			this.startMovement();
			// 1st step of the reasoning cycle -> It bites if it is near to a
			// device
			Bag objects = ShanksAgentPerceptionCapability.getPercepts(
					simulation, this);
			// IF RCV msg -> find the cat
			for (Object o : objects) {
				if (o instanceof CatAgent) {
					// If you are near to a cat, tell your mom that there is a
					// cat
					CatAgent cat = (CatAgent) o;
					this.setTargetLocation(cat.getCurrentLocation());
					if (this.currentLocation.isNearTo(cat.getCurrentLocation(), 1)) {
						this.frightenOffTheCat((CatAgent) o);	
					}
					break;
				}
			}

			// 2nd step of the reasoning cycle -> Find new target
			if (this.targetLocation == null) {
				Location randomLocation = ShanksAgentPerceptionCapability.getRandomObjectLocation(simulation, this);
				this.setTargetLocation(randomLocation);
			}

			// 3rd step of the reasoning cycle -> Move if it is possible
			ShanksAgentMovementCapability.goTo(simulation, this, this.currentLocation,
					this.targetLocation, this.speed);
		} catch (Exception e) {
			logger.warning("Exception -> " + e.getMessage());
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
		// Nothing to do, this agent always can move
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.agent.movement.Mobile2DShanksAgent#startMovement()
	 */
	@Override
	public void startMovement() {
		// Nothing to do, this agent always can move
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#isAllowedToMove()
	 */
	@Override
	public boolean isAllowedToMove() {
		return true;
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#getSpeed()
	 */
	@Override
	public double getSpeed() {
		return this.speed;
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

	/**
	 * @param the
	 *            cat agent
	 */
	private void frightenOffTheCat(CatAgent cat) {
		cat.setFrightened(true);
		this.targetLocation = null;
	}

}
