/**
 * 
 */
package es.upm.dit.gsi.shanks.agent;

import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.han.element.device.Computer;

/**
 * @author a.carrera
 * 
 */
public class TemperatureWatcherAgent extends SimpleShanksAgent {

	private Logger logger = Logger.getLogger(TemperatureWatcherAgent.class.getName());

	private Computer computer;

	/**
     * 
     */
	private static final long serialVersionUID = 263836274462865563L;

	public TemperatureWatcherAgent(String id, Computer computer) {
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
//		double temp = (Double) this.computer
//				.getProperty(Computer.PROPERTY_TEMPERATURE);
//		int freq = (Integer) this.computer.getProperty(Computer.PROPERTY_CPUFREQ);
//		String power = (String) this.computer.getProperty(Computer.PROPERTY_POWER);
//		try {
//			if (temp >= 80) {
//				if (freq >= 1500) {
//					logger.info("Temperature Agent is changing frequency.");
////					this.computer.changeProperty(Computer.PROPERTY_CPUFREQ, 1000);
////					this.computer.changeProperty(Computer.PROPERTY_TEMPERATURE, 50.0);
//				} else if (power.equals("ON")) {
//					logger.info("Temperature Agent is switching off the computer.");
//					this.computer.changeProperty(Computer.PROPERTY_POWER, "OFF");
//				}
//			}
//		} catch (UnsupportedNetworkElementStatusException e) {
//			logger.severe("Impossible to change property in device " + this.computer.getID());
//		}

	}
}
