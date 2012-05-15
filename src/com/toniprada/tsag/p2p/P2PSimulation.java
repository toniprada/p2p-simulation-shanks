package com.toniprada.tsag.p2p;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import com.toniprada.tsag.p2p.agent.ServerAgent;
import com.toniprada.tsag.p2p.agent.UserAgent;
import com.toniprada.tsag.p2p.model.element.device.Client;
import com.toniprada.tsag.p2p.model.element.device.Server;
import com.toniprada.tsag.p2p.model.scenario.HANScenario;
import com.toniprada.tsag.p2p.model.scenario.portrayal.HANScenario2DPortrayal;
import com.toniprada.tsag.p2p.painter.Painter;

import sim.engine.Schedule;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author Toni Prada <toniprada@gmail.com>
 * 
 */
public class P2PSimulation extends ShanksSimulation {

	private static final long serialVersionUID = 8840504113460201366L;

	public P2PSimulation(long seed, Class<? extends Scenario> scenarioClass,
			String scenarioID, String initialState, Properties properties)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException, DuplicatedAgentIDException,
			DuplicatedActionIDException {
		super(seed, scenarioClass, scenarioID, initialState, properties);
	}

	public static void main(String[] args) {
		try {
			Properties scenarioProperties = new Properties();
			scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
			P2PSimulation tut = new P2PSimulation(System.currentTimeMillis(),
					HANScenario.class, "P2PNetwork", HANScenario.STATUS_NORMAL,
					scenarioProperties);
			tut.start();
			do
				if (!tut.schedule.step(tut))
					break;
			while (tut.schedule.getSteps() < 8001);
			tut.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addSteppables() {
		schedule.scheduleRepeating(Schedule.EPOCH, 3, new Painter(), 50);
	}

	@Override
	public void registerShanksAgents() throws DuplicatedAgentIDException,
			DuplicatedActionIDException {
		HANScenario2DPortrayal s = null;
		try {
			s = (HANScenario2DPortrayal) this.getScenarioPortrayal();
		} catch (DuplicatedPortrayalIDException e) {
			e.printStackTrace();
		} catch (ScenarioNotFoundException e) {
			e.printStackTrace();
		}

		UserAgent userAgent1 = new UserAgent("user1", (Client) this
				.getScenario().getNetworkElement("C1"),
				s.getDeviceLocation("C1"));
		this.registerShanksAgent(userAgent1);
		UserAgent userAgent2 = new UserAgent("user2", (Client) this
				.getScenario().getNetworkElement("C2"),
				s.getDeviceLocation("C2"));
		this.registerShanksAgent(userAgent2);
		UserAgent userAgent3 = new UserAgent("user3", (Client) this
				.getScenario().getNetworkElement("C3"),
				s.getDeviceLocation("C3"));
		this.registerShanksAgent(userAgent3);
		UserAgent userAgent4 = new UserAgent("user4", (Client) this
				.getScenario().getNetworkElement("C4"),
				s.getDeviceLocation("C4"));
		this.registerShanksAgent(userAgent4);
		UserAgent userAgent5 = new UserAgent("user5", (Client) this
				.getScenario().getNetworkElement("C5"),
				s.getDeviceLocation("C5"));
		this.registerShanksAgent(userAgent5);
		UserAgent userAgent6 = new UserAgent("user6", (Client) this
				.getScenario().getNetworkElement("C6"),
				s.getDeviceLocation("C6"));
		this.registerShanksAgent(userAgent6);

		ServerAgent serverAgent = new ServerAgent("server", (Server) this
				.getScenario().getNetworkElement("Server"));
		this.registerShanksAgent(serverAgent);

	}
}
