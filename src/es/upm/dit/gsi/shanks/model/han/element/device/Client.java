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
public class Client extends Device {
	
	public static final int MAX_CONNECTIONS = 2;

	public static final String STATUS_ON = "OK";
	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OVERLOADED = "OVERLOADED";

//	public static final String PROPERTY_POWER = "Power";
//	public static final String PROPERTY_CONNECTION = "Connection";

	public Client(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, STATUS_OFF, false);
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
//		if (status.equals(Computer.STATUS_OFF)) {
//			this.changeProperty(Computer.PROPERTY_POWER, "OFF");
//			this.changeProperty(Computer.PROPERTY_CONNECTION, "OFF");
//		} else if (status.equals(Computer.STATUS_ON)) {
//			this.changeProperty(Computer.PROPERTY_POWER, "ON");
//			this.changeProperty(Computer.PROPERTY_CONNECTION, "ON");
//		} else if (status.equals(Computer.STATUS_OVERLOADED)) {
//			this.changeProperty(Computer.PROPERTY_POWER, "ON");
//			this.changeProperty(Computer.PROPERTY_CONNECTION, "OFF");
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
//		String power = (String) this.getProperty(Computer.PROPERTY_POWER);
//		String connection = (String) this.getProperty(Computer.PROPERTY_CONNECTION);
//		if (power.equals("OFF")) {
//			this.updateStatusTo(Computer.STATUS_OFF);
//		} else {
//			if (connection.equals("ON")) {
//				this.updateStatusTo(Computer.STATUS_ON);
//			} else {
//				this.updateStatusTo(Computer.STATUS_OVERLOADED);
//			}
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
//		this.addProperty(Computer.PROPERTY_POWER, "OFF");
//		this.addProperty(Computer.PROPERTY_CONNECTION, "OFF");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Client.STATUS_OVERLOADED);
		this.addPossibleStatus(Client.STATUS_ON);
		this.addPossibleStatus(Client.STATUS_OFF);
	}
	


}
