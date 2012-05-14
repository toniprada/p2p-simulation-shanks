/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.device;

import java.util.List;

import org.jfree.util.Log;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.han.element.link.Connection;

/**
 * @author a.carrera
 * 
 */
public class Server extends Device {
	
	public static final double STREAMING_BANDWIDTH = 1.0;
	public static final double MAX_BANDWIDTH = 1.0;
	public static final double THRESHOLD = 0.3;

	public static final String STATUS_OK = "OK";
	public static final String STATUS_OVERLOADED = "Overloaded";

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
//		double bw = 0.0;
//		List<Link> links = getLinks();
//		for (Link link : links) {
//			ADSLConnection l = (ADSLConnection) link;
//			bw += l.getUsage();
//		}		
//		if ((MAX_BANDWIDTH - bw) > THRESHOLD) {
//			this.updateStatusTo(Server.STATUS_OK);
//		} else {
//			this.updateStatusTo(Server.STATUS_OVERLOADED);
//		}
//		Log.info("BW" + bw);

	}


	@Override
	public void fillIntialProperties() {
//		this.addProperty(Server.PROPERTY_USED_BANDWIDTH, 0.0);
	}

	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Server.STATUS_OVERLOADED);
		this.addPossibleStatus(Server.STATUS_OK);
	}

}
