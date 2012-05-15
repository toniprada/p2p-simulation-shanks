package com.toniprada.tsag.p2p;

/**
 * 
 */

import java.util.HashMap;
import java.util.Properties;

import javax.swing.JFrame;

import com.toniprada.tsag.p2p.model.scenario.HANScenario;
import com.toniprada.tsag.p2p.painter.Painter;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author Toni Prada <toniprada@gmail.com>
 * 
 */
public class TutorialSimulation2DGUI extends ShanksSimulation2DGUI {


	public TutorialSimulation2DGUI(ShanksSimulation sim) {
		super(sim);
	}

	public static String getName() {
		return "P2P Streaming network - 2D";
	}


	@Override
	public void addDisplays(Scenario2DPortrayal paramScenario2DPortrayal) {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) {
		try {
			Properties scenarioProperties = new Properties();
			scenarioProperties.put(Scenario.SIMULATION_GUI,
					Scenario.SIMULATION_2D);
			P2PSimulation tut = new P2PSimulation(System.currentTimeMillis(),
					HANScenario.class, "P2PNetwork", HANScenario.STATUS_NORMAL,
					scenarioProperties);
			TutorialSimulation2DGUI gui = new TutorialSimulation2DGUI(tut);
			gui.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addCharts(Scenario2DPortrayal arg0)
			throws DuplicatedChartIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		this.addTimeChart(Painter.BW_CHART_ID, "Time / Steps", "Bandwidth");
		this.addTimeChart(Painter.OVERLOAD_CHART_ID, "Time / Steps", "Overload");
		this.addTimeChart(Painter.ERROR_CHART_ID, "Time / Steps", "Errors");

	}

	@Override
	public void locateFrames(Scenario2DPortrayal scenarioPortrayal) {
		HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
		JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
		mainFrame.setLocation(100, 100);
		JFrame chartFrame = frames.get(Painter.BW_CHART_ID);
		chartFrame.setLocation(200, 300);
		JFrame chartFrame2 = frames.get(Painter.OVERLOAD_CHART_ID);
		chartFrame2.setLocation(400, 300);
		JFrame chartFrame3 = frames.get(Painter.ERROR_CHART_ID);
		chartFrame3.setLocation(600, 300);
	}

}
