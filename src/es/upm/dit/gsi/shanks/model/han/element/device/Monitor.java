/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.device;

import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author a.carrera
 * 
 */
public class Monitor extends Device {

	Logger logger = Logger.getLogger(Monitor.class.getName());

	public static final String STATUS_OFF = "Screen OFF";
	public static final String STATUS_OK = "Screen OK";
	public static final String STATUS_BLINKING = "Screen blinking";

	public static final String PROPERTY_HDMIPORT = "HDMI Port";
	public static final String PROPERTY_POWERSOURCE = "Power source";
	public static final String PROPERTY_POWER = "Power";

	/**
	 * Constructor of the Monitor
	 * 
	 * @param id
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public Monitor(String id) throws UnsupportedNetworkElementStatusException {
		super(id, Monitor.STATUS_OFF, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		String status = this.getCurrentStatus();
		if (status.equals(Monitor.STATUS_OK)) {
			this.changeProperty(Monitor.PROPERTY_HDMIPORT, "Connected");
			this.changeProperty(Monitor.PROPERTY_POWERSOURCE, "Connected");
			this.changeProperty(Monitor.PROPERTY_POWER, "ON");
		} else if (status.equals(Monitor.STATUS_OFF)) {
			this.changeProperty(Monitor.PROPERTY_POWER, "OFF");
		} else if (status.equals(Monitor.STATUS_BLINKING)) {
			this.changeProperty(Monitor.PROPERTY_POWERSOURCE, "Wrong connected");
			this.changeProperty(Monitor.PROPERTY_HDMIPORT, "Connected");
			this.changeProperty(Monitor.PROPERTY_POWER, "ON");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String powerSource = (String) this
				.getProperty(Monitor.PROPERTY_POWERSOURCE);
		String power = (String) this.getProperty(Monitor.PROPERTY_POWER);
		String hdmiPort = (String) this.getProperty(Monitor.PROPERTY_HDMIPORT);
		if (powerSource.equals("Connected") && hdmiPort.equals("Connected")) {
			if (power.equals("ON")) {
				this.updateStatusTo(Monitor.STATUS_OK);
			} else {
				this.updateStatusTo(Monitor.STATUS_OFF);
			}
		} else if (powerSource.equals("Disconnected")
				|| hdmiPort.equals("Disconnected")) {
			this.updateStatusTo(Monitor.STATUS_OFF);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(Monitor.PROPERTY_HDMIPORT, "Connected");
		this.addProperty(Monitor.PROPERTY_POWERSOURCE, "Connected");
		this.addProperty(Monitor.PROPERTY_POWER, "ON");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Monitor.STATUS_OFF);
		this.addPossibleStatus(Monitor.STATUS_OK);
		this.addPossibleStatus(Monitor.STATUS_BLINKING);
	}

	public void switchON() {
		if (this.getCurrentStatus().equals(Monitor.STATUS_OFF)) {
			try {
				this.changeProperty(PROPERTY_POWER, "ON");
			} catch (UnsupportedNetworkElementStatusException e) {
				logger.warning("Exception: " + e.getMessage());
			}
		}
	}

}
