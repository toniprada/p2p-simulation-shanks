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
	
	public static final double STREAMING_BANDWIDTH = 1.0;
	public static final double MAX_BANDWIDTH = 2.0;
	public static final double THRESHOLD = 0.3;

	public static final String STATUS_OK = "OK";
	public static final String STATUS_OVERLOADED = "Overloaded";

	public static final String PROPERTY_USED_BANDWIDTH = "used_bandwidth";

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
		double usedBandwidth = (Double) this.getProperty(Server.PROPERTY_USED_BANDWIDTH);
		if ((MAX_BANDWIDTH - usedBandwidth) > THRESHOLD) {
			this.updateStatusTo(Server.STATUS_OK);
		} else {
			this.updateStatusTo(Server.STATUS_OVERLOADED);
		}
	}


	@Override
	public void fillIntialProperties() {
		this.addProperty(Server.PROPERTY_USED_BANDWIDTH, 0.0);

	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Server.STATUS_OVERLOADED);
		this.addPossibleStatus(Server.STATUS_OK);
	}

}
