/**
 * 
 */
package com.toniprada.tsag.p2p.painter;

import java.util.Collection;

import com.toniprada.tsag.p2p.agent.ServerAgent;
import com.toniprada.tsag.p2p.agent.UserAgent;

import sim.engine.SimState;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author Toni Prada <toniprada@gmail.com>
 * 
 */
public class Painter implements Steppable {

	private static final long serialVersionUID = -3993538067829444086L;
	public static final String BW_CHART_ID = "bandwidth/time";
	public static final String OVERLOAD_CHART_ID = "overload/time";
	public static final String ERROR_CHART_ID = "errors/time";

	@Override
	public void step(SimState sim) {
		ShanksSimulation simulation = (ShanksSimulation) sim;
		try {
			ScenarioPortrayal scenarioPortrayal = simulation
					.getScenarioPortrayal();
			Collection<ShanksAgent> agents = simulation.getAgents();
			for (ShanksAgent agent : agents) {
				if (agent instanceof ServerAgent) {
					ServerAgent sa = (ServerAgent) agent;
					scenarioPortrayal.addDataToDataSerieInTimeChart(
							BW_CHART_ID, sa.getID(),
							simulation.schedule.getSteps(), sa.getBandwidth());
					scenarioPortrayal.addDataToDataSerieInTimeChart(
							OVERLOAD_CHART_ID, sa.getID(),
							simulation.schedule.getSteps(), sa.getOverload());
				}
			}
			for (ShanksAgent agent : agents) {
				if (agent instanceof UserAgent) {
					UserAgent ua = (UserAgent) agent;
					scenarioPortrayal.addDataToDataSerieInTimeChart(
							ERROR_CHART_ID, ua.getID(),
							simulation.schedule.getSteps(), ua.getErrors());
				}
			}
		} catch (DuplicatedPortrayalIDException e) {
			e.printStackTrace();
		} catch (ScenarioNotFoundException e) {
			e.printStackTrace();
		}
	}

}
