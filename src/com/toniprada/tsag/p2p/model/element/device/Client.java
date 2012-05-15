/**
 * 
 */
package com.toniprada.tsag.p2p.model.element.device;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author Toni Prada <toniprada@gmail.com>
 * 
 */
public class Client extends Device {
	
	public static final int MAX_CONNECTIONS = 2;

	public static final String STATUS_ON = "OK";
	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OVERLOADED = "OVERLOADED";
	

	public Client(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, STATUS_OFF, false);
	}

	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
	}


	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {
	}

	@Override
	public void fillIntialProperties() {
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Client.STATUS_OVERLOADED);
		this.addPossibleStatus(Client.STATUS_ON);
		this.addPossibleStatus(Client.STATUS_OFF);
	}

}
