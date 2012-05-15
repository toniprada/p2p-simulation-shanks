/**
 * 
 */
package es.upm.dit.gsi.shanks.painter;

import java.util.Collection;

import sim.engine.SimState;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.MotherAgent;
import es.upm.dit.gsi.shanks.agent.ServerAgent;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 *
 */
public class Painter implements Steppable {

	private static final long serialVersionUID = -3993538067829444086L;
	public static final String BW_CHART_ID = "bandwith/time";

	/* (non-Javadoc)
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState sim) {
		ShanksSimulation simulation = (ShanksSimulation) sim;
		try {
			ScenarioPortrayal scenarioPortrayal = simulation.getScenarioPortrayal();
			Collection<ShanksAgent> agents = simulation.getAgents();
			for(ShanksAgent agent : agents){
				if (agent instanceof ServerAgent) {
					ServerAgent sa = (ServerAgent) agent;
					scenarioPortrayal.addDataToDataSerieInTimeChart(BW_CHART_ID, sa.getID(), simulation.schedule.getSteps(), sa.getBandwidth());
				}
			}
		} catch (DuplicatedPortrayalIDException e) {
			e.printStackTrace();
		} catch (ScenarioNotFoundException e) {
			e.printStackTrace();
		}
	}

}
