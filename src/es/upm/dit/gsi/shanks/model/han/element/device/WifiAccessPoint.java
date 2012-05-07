/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.device;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author a.carrera
 * 
 */
public class WifiAccessPoint extends Modem {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_OVERLOAD = "Overload";

	public static final String PROPERTY_INTERFERENCE = "Interference"; // In %
	public static final String PROPERTY_LOAD = "Load"; // In %
	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_CONNECTION = "Connected";

	public WifiAccessPoint(String id, String initialState, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, isGateway);
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
		if (status.equals(WifiAccessPoint.STATUS_OVERLOAD)) {
			this.changeProperty(WifiAccessPoint.PROPERTY_LOAD, 100.0);
			this.changeProperty(WifiAccessPoint.PROPERTY_POWER, "ON");
			this.changeProperty(WifiAccessPoint.PROPERTY_CONNECTION,
					"Disconnected");
		} else if (status.equals(WifiAccessPoint.STATUS_OFF)) {
			this.changeProperty(WifiAccessPoint.PROPERTY_LOAD, 0);
			this.changeProperty(WifiAccessPoint.PROPERTY_CONNECTION,
					"Disconnected");
			this.changeProperty(WifiAccessPoint.PROPERTY_POWER, "OFF");
		} else if (status.equals(WifiAccessPoint.STATUS_OK)) {
			this.changeProperty(WifiAccessPoint.PROPERTY_LOAD,
					30 + Math.random() * 20 - 10);
			this.changeProperty(WifiAccessPoint.PROPERTY_INTERFERENCE,
					10 + Math.random() * 50);
			this.changeProperty(WifiAccessPoint.PROPERTY_POWER, "ON");
			this.changeProperty(WifiAccessPoint.PROPERTY_CONNECTION,
					"Connected");
		} else if (status.equals(WifiAccessPoint.STATUS_DISCONNECTED)) {
			this.changeProperty(WifiAccessPoint.PROPERTY_CONNECTION,
					"Disconnected");
			this.changeProperty(WifiAccessPoint.PROPERTY_LOAD, 0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		int load = (Integer) this.getProperty(WifiAccessPoint.PROPERTY_LOAD);
		int interference = (Integer) this
				.getProperty(WifiAccessPoint.PROPERTY_INTERFERENCE);
		String power = (String) this
				.getProperty(WifiAccessPoint.PROPERTY_POWER);
		String connection = (String) this
				.getProperty(WifiAccessPoint.PROPERTY_CONNECTION);

		if (power.equals("OFF")) {
			this.updateStatusTo(WifiAccessPoint.STATUS_OFF);
		} else {
			if (load >= 90) {
				this.updateStatusTo(WifiAccessPoint.STATUS_OVERLOAD);
			} else if (interference > 80) {
				this.updateStatusTo(WifiAccessPoint.STATUS_DISCONNECTED);
			} else if (connection.equals("Connected")) {
				this.updateStatusTo(WifiAccessPoint.STATUS_OK);
			} else {
				this.updateStatusTo(WifiAccessPoint.STATUS_DISCONNECTED);
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
		this.addProperty(WifiAccessPoint.PROPERTY_LOAD, 30);
		this.addProperty(WifiAccessPoint.PROPERTY_INTERFERENCE, 30);
		this.addProperty(WifiAccessPoint.PROPERTY_POWER, "ON");
		this.addProperty(WifiAccessPoint.PROPERTY_CONNECTION, "Connected");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(WifiAccessPoint.STATUS_OVERLOAD);
		this.addPossibleStatus(WifiAccessPoint.STATUS_DISCONNECTED);
		this.addPossibleStatus(WifiAccessPoint.STATUS_OK);
		this.addPossibleStatus(WifiAccessPoint.STATUS_OFF);
	}

}
