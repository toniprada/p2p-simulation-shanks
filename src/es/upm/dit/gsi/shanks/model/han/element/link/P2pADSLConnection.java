/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.element.link;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * @author a.carrera
 * 
 */
public class P2pADSLConnection extends ADSLCable {

	public P2pADSLConnection(String id)
			throws UnsupportedNetworkElementStatusException {
		super(id, ADSLCable.STATUS_DISCONNECTED);
	}


}
