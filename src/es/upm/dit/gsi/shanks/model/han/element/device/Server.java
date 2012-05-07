/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.device;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author a.carrera
 * 
 */
public class Server extends Device {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_HACKED = "Disconnected";

	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_CONNECTION = "Connection";

	public Server(String id, String initialState)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, false);
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
		if (status.equals(Server.STATUS_OFF)) {
			this.changeProperty(Server.PROPERTY_POWER, "OFF");
			this.changeProperty(Server.PROPERTY_CONNECTION, "OFF");
		} else if (status.equals(Server.STATUS_OK)) {
			this.changeProperty(Server.PROPERTY_POWER, "ON");
			this.changeProperty(Server.PROPERTY_CONNECTION, "ON");
		} else if (status.equals(Server.STATUS_HACKED)) {
			this.changeProperty(Server.PROPERTY_POWER, "ON");
			this.changeProperty(Server.PROPERTY_CONNECTION, "OFF");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String power = (String) this.getProperty(Server.PROPERTY_POWER);
		String connection = (String) this.getProperty(Server.PROPERTY_CONNECTION);
		if (power.equals("OFF")) {
			this.updateStatusTo(Server.STATUS_OFF);
		} else {
			if (connection.equals("ON")) {
				this.updateStatusTo(Server.STATUS_OK);
			} else {
				this.updateStatusTo(Server.STATUS_HACKED);
			}
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
		this.addProperty(Server.PROPERTY_POWER, "ON");
		this.addProperty(Server.PROPERTY_CONNECTION, "ON");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Server.STATUS_HACKED);
		this.addPossibleStatus(Server.STATUS_OK);
		this.addPossibleStatus(Server.STATUS_OFF);
	}

}
