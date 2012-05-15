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
public class Server extends Device {

	public static final double STREAMING_BANDWIDTH = 1.0;
	public static final double MAX_BANDWIDTH = 3.0;
	public static final double THRESHOLD = 0.3;

	public static final String STATUS_OK = "OK";
	public static final String STATUS_OVERLOADED = "OVERLOADED";

	public Server(String id, String initialState)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, false);
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
		this.addPossibleStatus(Server.STATUS_OVERLOADED);
		this.addPossibleStatus(Server.STATUS_OK);
	}

}
