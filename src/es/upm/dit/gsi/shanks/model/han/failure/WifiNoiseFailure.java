/**
 * 
 */
package es.upm.dit.gsi.shanks.model.han.failure;

import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * @author a.carrera
 *
 */
public class WifiNoiseFailure extends Failure {

	public WifiNoiseFailure(String id, double occurrenceProbability) {
		super(id, occurrenceProbability);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isResolved() {
		// TODO Auto-generated method stub
		return false;
	}

}
