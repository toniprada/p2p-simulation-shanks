/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import jason.asSemantics.Message;

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
import es.upm.dit.gsi.shanks.model.adsl.element.device.ADSLModem;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.han.element.device.Client;
import es.upm.dit.gsi.shanks.model.han.element.device.EthernetRouter;
import es.upm.dit.gsi.shanks.model.han.element.link.Connection;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;

/**
 * @author a.carrera
 * 
 */
public class BoyAgent extends SimpleShanksAgent
		implements
			MobileShanksAgent,
			PercipientShanksAgent {

	private Logger logger = Logger.getLogger(CatAgent.class.getName());

	private Location currentLocation;
	private Location targetLocation;
	private Double speed;
	private Double perceptionRange;

	private MotherAgent mom;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5906067568833022855L;

	/**
	 * Create a cat agent
	 * 
	 * @param id
	 *            Name of the cat
	 * @param mom
	 *            MotherAgent
	 */
	public BoyAgent(String id, MotherAgent mom) {
		super(id);
		//For mobile shanks agent
		this.speed = 0.2;
		this.currentLocation = new Location();
		this.targetLocation = new Location();
		//For percipient shanks agent
		this.perceptionRange = 5.0;
		//For this type of agent
		this.mom = mom;
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

			try {
				this.startMovement();
				// 1st step of the reasoning cycle -> If it is near to a
				// device, it tries to repair it
				Bag objects = ShanksAgentPerceptionCapability.getPercepts(
						simulation, this);
				for (Object o : objects) {
					if (o instanceof Device) {
						double distance = ShanksAgentPerceptionCapability
								.getDistanceTo(simulation, this, o);
						if (distance < 2) {
							Device nearDevice = (Device) o;
							if (nearDevice instanceof Client
									|| nearDevice instanceof EthernetRouter) {
								List<Link> links = nearDevice.getLinks();
								for (Link link : links) {
									if (link instanceof Connection) {
										String status = link.getCurrentStatus();
										if (status
												.equals(Connection.STATUS_DISCONNECTED)) {
											this.repairEthernetCable((Connection) link);
											break;
										}
									}
								}
								if (nearDevice instanceof Client) {
									this.repairComputer(nearDevice);
									break;
								} else if (nearDevice instanceof EthernetRouter) {
									this.repairRouter(nearDevice);
									break;
								} else if (nearDevice instanceof ADSLModem) {
									this.repairADSLModem(nearDevice);
									break;
								}
							}
						}
					} else if (o instanceof CatAgent) {
						// If you are near to a cat, tell your mom that there is
						// a
						// cat
						double distance = ShanksAgentPerceptionCapability
								.getDistanceTo(simulation, this, o);
						if (distance < 5) {
							CatAgent cat = (CatAgent) o;
							this.tellMom(cat.getCurrentLocation());
							break;
						}
					}
				}

				// 2nd step of the reasoning cycle -> Find new target
				if (simulation.schedule.getSteps() % 500 == 0) { // Each 1000
																	// steps
					Location randomObjectLocation = ShanksAgentPerceptionCapability
							.getRandomObjectLocation(simulation, this);
					this.setTargetLocation(randomObjectLocation);
				}

				// 3rd step of the reasoning cycle -> Move if it is possible
				ShanksAgentMovementCapability.goTo(simulation, this,
						this.currentLocation, this.targetLocation, this.speed);
			} catch (Exception e) {
				logger.warning("Exception -> " + e.getMessage());
			}
		} catch (Exception e) {
			logger.severe("Exception: " + e.getMessage());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#getSpeed()
	 */
	@Override
	public double getSpeed() {
		return this.speed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.agent.perception.PercipientShanksAgent#
	 * getPerceptionRange()
	 */
	@Override
	public double getPerceptionRange() {
		return this.perceptionRange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#setCurrentLocation
	 * (es.upm.dit.gsi.shanks.agent.movement.Location)
	 */
	@Override
	public void setCurrentLocation(Location location) {
		this.currentLocation = location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.agent.movement.MobileShanksAgent#setTargetLocation
	 * (es.upm.dit.gsi.shanks.agent.movement.Location)
	 */
	@Override
	public void setTargetLocation(Location location) {
		this.targetLocation = location;
	}

	/**
	 * The boy repairs the link
	 * 
	 * @param link
	 */
	private void repairEthernetCable(Connection link) {
		try {
			logger.warning("The cat is biting the link " + link.getID()
					+ "!!!!");
			link.setCurrentStatus(Connection.STATUS_CONNECTED);
		} catch (Exception e) {
			logger.severe("Exception when cat is trying to bite the link: "
					+ e.getMessage());
		}
	}

	/**
	 * The boy repairs the computer
	 * 
	 * @param nearDevice
	 */
	private void repairComputer(Device nearDevice) {
//		try {
//			if (((String) nearDevice.getProperty(Computer.PROPERTY_POWER))
//					.equals("OFF")
//					|| (Double) nearDevice
//							.getProperty(Computer.PROPERTY_TEMPERATURE) > 30) {
//	
//				nearDevice.changeProperty(Computer.PROPERTY_TEMPERATURE, 15.0);
//			}
//	
//			if (((String) nearDevice.getProperty(Computer.PROPERTY_CONNECTION))
//					.equals("IP NOK")) {
//				nearDevice
//						.changeProperty(Computer.PROPERTY_CONNECTION, "IP OK");
//			}
//		} catch (UnsupportedNetworkElementStatusException e) {
//			logger.severe(e.getMessage());
//		}
	}

	/**
	 * The boy repairs the router
	 * 
	 * @param nearDevice
	 */
	private void repairRouter(Device nearDevice) {
		if (((String) nearDevice.getProperty(EthernetRouter.PROPERTY_DHCP))
				.equals("OFF")) {
			try {
				nearDevice.changeProperty(EthernetRouter.PROPERTY_DHCP, "ON");
			} catch (UnsupportedNetworkElementStatusException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	/**
	 * The boy repairs the ADSL modem
	 * 
	 * @param nearDevice
	 */
	private void repairADSLModem(Device nearDevice) {
		if (((Double) nearDevice.getProperty(ADSLModem.TEMPERATURE_PROPERTY)) > 70) {
			try {
				nearDevice.changeProperty(ADSLModem.TEMPERATURE_PROPERTY, 50);
			} catch (UnsupportedNetworkElementStatusException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	/**
	 * Send a message to your mother and say her where the cat is.
	 * 
	 * @param catLocation
	 */
	private void tellMom(Location catLocation) {
		Message msg = new Message();
		msg.setMsgId(this.getID() + "_" + System.currentTimeMillis());
		msg.setReceiver(this.mom.getID());
		msg.setPropCont(catLocation);
		this.sendMsg(msg);
	}

}
