package es.upm.dit.gsi.shanks.agent.action.fake;

import jason.asSyntax.Term;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.action.JasonShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.fake.FakeShanksAgent;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.model.han.element.device.EthernetRouter;
import es.upm.dit.gsi.shanks.model.han.element.device.Monitor;
import es.upm.dit.gsi.shanks.model.han.element.link.ADSLCable;

public class FakeShanksAgentAction extends JasonShanksAgentAction {

	public static final String FIX = "fix";
	private Logger logger = Logger.getLogger(FakeShanksAgentAction.class
			.getName());

	@Override
	public boolean executeAction(ShanksSimulation simulation,
			ShanksAgent agent, List<Term> arguments) {

		try {
			// To see failures in gui
			logger.info("AGENT SLEEPING 1 SECOND TO ALLOW ALL OF YOU WATCH THE FAILURE...");
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Set<Failure> failures = simulation.getScenario().getCurrentFailures();
		int number = failures.size();
		int random = simulation.random.nextInt(number);
		Failure f = (Failure) failures.toArray()[random];
		HashMap<NetworkElement, String> elements = f.getAffectedElements();
		for (NetworkElement element : elements.keySet()) {
			if (element instanceof Computer) {
				try {
					element.setCurrentStatus(Computer.STATUS_DOWNLOADING);
				} catch (UnsupportedNetworkElementStatusException e) {
					e.printStackTrace();
				}
			} else if (element instanceof ADSLCable) {
				try {
					element.setCurrentStatus(ADSLCable.STATUS_CONNECTED);
				} catch (UnsupportedNetworkElementStatusException e) {
					e.printStackTrace();
				}
			} else if (element instanceof EthernetRouter) {
				try {
					element.setCurrentStatus(EthernetRouter.STATUS_OK);
				} catch (UnsupportedNetworkElementStatusException e) {
					e.printStackTrace();
				}
			} else if (element instanceof Monitor) {
				try {
					element.setCurrentStatus(Monitor.STATUS_OK);
				} catch (UnsupportedNetworkElementStatusException e) {
					e.printStackTrace();
				}
			}
		}

		((FakeShanksAgent) agent).incrementNumberOfResolverFailures();
		// END OF THE ACTION
		simulation.getScenarioManager().checkFailures(simulation);
		logger.finer("Number of current failures: "
				+ simulation.getScenario().getCurrentFailures().size());
		return true;
	}

}
