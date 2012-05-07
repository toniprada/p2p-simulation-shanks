/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.failure;

import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * @author a.carrera
 * 
 */
public class ServerOverload extends Failure {

	public ServerOverload() {
		super(ServerOverload.class.getName(), 0.01);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
//		this.addPossibleAffectedElements(ADSLCable.class,
//				ADSLCable.STATUS_DISCONNECTED);
////		this.addPossibleAffectedElements(HDMICable.class, HDMICable.STATUS_CUT);
//		this.addPossibleAffectedElements(Computer.class,
//				Computer.STATUS_WAITING);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#isResolved()
	 */
	@Override
	public boolean isResolved() {
		Set<NetworkElement> elements = this.getAffectedElements().keySet();
		boolean resolved = false;
//		for (NetworkElement element : elements) {
//			if (element instanceof ADSLCable
//					|| element instanceof HDMICable) {
//				double lossRatio = (Double) element
//						.getProperty(ADSLCable.PROPERTY_PACKETLOSSRATIO);
//				if (lossRatio < 0.01) {
//					resolved = true;
//				} else {
//					resolved = false;
//					break;
//				}
//			} else if (element instanceof Computer) {
//				String connection = (String) element.getProperty(Computer.PROPERTY_CONNECTION);
//				if (connection.equals("IP OK")) {
//					resolved = true;
//				} else {
//					resolved = false;
//					break;
//				}
//
//			}
//		}
		return resolved;
	}

}
