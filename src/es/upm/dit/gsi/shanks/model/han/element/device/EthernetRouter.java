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
public class EthernetRouter extends Device {

	public static final String STATUS_OK = "OK";
	public static final String STATUS_NODHCP = "NO_DHCP";
	public static final String STATUS_OFF = "OFF";

	public static final String PROPERTY_DHCP = "DHCP";
	public static final String PROPERTY_POWER = "Power";

	/**
	 * @param id
	 * @throws UnsupportedNetworkElementStatusException
	 */
	public EthernetRouter(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, EthernetRouter.STATUS_OK, true);
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
		if (status.equals(EthernetRouter.STATUS_OK)) {
			this.changeProperty(EthernetRouter.PROPERTY_POWER, "ON");
			this.changeProperty(EthernetRouter.PROPERTY_DHCP, "ON");
		} else if (status.equals(EthernetRouter.STATUS_OFF)) {
			this.changeProperty(EthernetRouter.PROPERTY_POWER, "OFF");
		} else if (status.equals(EthernetRouter.STATUS_NODHCP)) {
			this.changeProperty(EthernetRouter.PROPERTY_POWER, "ON");
			this.changeProperty(EthernetRouter.PROPERTY_DHCP, "OFF");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
		String dhcpService = (String) this
				.getProperty(EthernetRouter.PROPERTY_DHCP);
		String power = (String) this.getProperty(EthernetRouter.PROPERTY_POWER);
		if (power.equals("OFF")) {
			this.updateStatusTo(EthernetRouter.STATUS_OFF);
		} else {
			if (dhcpService.equals("OFF")) {
				this.updateStatusTo(EthernetRouter.STATUS_NODHCP);
			} else {
				this.updateStatusTo(EthernetRouter.STATUS_OK);
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
		this.addProperty(EthernetRouter.PROPERTY_DHCP, "ON");
		this.addProperty(EthernetRouter.PROPERTY_POWER, "ON");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(EthernetRouter.STATUS_OK);
		this.addPossibleStatus(EthernetRouter.STATUS_OFF);
		this.addPossibleStatus(EthernetRouter.STATUS_NODHCP);
	}

}
