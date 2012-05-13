/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.failure;

import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.han.element.device.Client;

/**
 * @author a.carrera
 *
 */
public class BrokenFan extends Failure {

	public BrokenFan() {
		super(BrokenFan.class.getName(),0.001);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
//		this.addPossibleAffectedElements(Computer.class, Computer.STATUS_HIGHTEMP);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#isResolved()
	 */
	@Override
	public boolean isResolved() {
		Set<NetworkElement> affectedElements = this.getAffectedElements().keySet();
		boolean solved = false;
		for (NetworkElement element : affectedElements) {
			if (element instanceof Client) {
//				double temp = (Double) element.getProperty(Computer.PROPERTY_TEMPERATURE);
//				String power = (String) element.getProperty(Computer.PROPERTY_POWER);
//				if (temp<90 || power.equals("OFF")) {
//					solved = true;
//				} else {
//					solved = false;
//					break;
//				}
			}
		}
		return solved;
	}

}
