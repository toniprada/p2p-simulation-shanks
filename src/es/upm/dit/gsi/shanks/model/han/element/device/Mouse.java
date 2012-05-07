package es.upm.dit.gsi.shanks.model.han.element.device;

import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

public class Mouse extends Device {

	Logger logger = Logger.getLogger(Mouse.class.getName());
	
	public static final String STATUS_OFF = "Mouse OFF";
	public static final String STATUS_OK = "Mouse OK";
	public static final String STATUS_WARNING = "Mouse low battery";
	
	public static final String PROPERTY_POWERSOURCE = "Power source";
	public static final String PROPERTY_POWER = "Power";
	
	
	public Mouse(String id) throws UnsupportedNetworkElementStatusException {
		super(id, Mouse.STATUS_OFF, false);
	}
	
	
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		String status = this.getCurrentStatus();
		if (status.equals(Mouse.STATUS_OK)) {
			this.changeProperty(Mouse.PROPERTY_POWERSOURCE, "Charged");
			this.changeProperty(Mouse.PROPERTY_POWER, "ON");
		} else if (status.equals(Mouse.STATUS_OFF)) {
			this.changeProperty(Mouse.PROPERTY_POWER, "OFF");
		} else if (status.equals(Mouse.STATUS_WARNING)) {
			this.changeProperty(Mouse.PROPERTY_POWERSOURCE, "Low battery");
			this.changeProperty(Mouse.PROPERTY_POWER, "ON");
		}

	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String powerSource = (String) this
				.getProperty(Mouse.PROPERTY_POWERSOURCE);
		String power = (String) this.getProperty(Mouse.PROPERTY_POWER);
		if (powerSource.equals("Charged")) {
			if (power.equals("ON")) {
				this.updateStatusTo(Mouse.STATUS_OK);
			} else {
				this.updateStatusTo(Mouse.STATUS_OFF);
			}
		} else if (powerSource.equals("Low battery")) {
			this.updateStatusTo(Mouse.STATUS_WARNING);
		}

	}

	@Override
	public void fillIntialProperties() {
		this.addProperty(Mouse.PROPERTY_POWERSOURCE, "Charged");
		this.addProperty(Mouse.PROPERTY_POWER, "ON");

	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Mouse.STATUS_OFF);
		this.addPossibleStatus(Mouse.STATUS_OK);
		this.addPossibleStatus(Mouse.STATUS_WARNING);

	}

}
