/**
 * 
 */


import java.util.HashMap;
import java.util.Properties;

import javax.swing.JFrame;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.han.scenario.HANScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.painter.Painter;

/**
 * @author a.carrera
 *
 */
public class TutorialSimulation2DGUI extends ShanksSimulation2DGUI {

    /**
     * @param sim
     */
    public TutorialSimulation2DGUI(ShanksSimulation sim) {
        super(sim);
    }

    /**
     * @return
     */
    public static String getName() {
        return "Tutorial Shanks - Home Area Nertwork - 2D";
    }
	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.ShanksSimulation2DGUI#addDisplays(es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal)
	 */
	@Override
	public void addDisplays(Scenario2DPortrayal paramScenario2DPortrayal) {
		// TODO Auto-generated method stub

	}
	
	public static void main (String[] args) {
		try {
			Properties scenarioProperties = new Properties();
	        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_2D);
//	        scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.SIMULATION_3D);
//	         scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
			TutorialSimulation tut = new TutorialSimulation(System.currentTimeMillis(), HANScenario.class, "ADSL scenario", HANScenario.STATUS_NORMAL, scenarioProperties);
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
		JFrame chartFrame = frames.get(Painter.BW_CHART_ID);
		
		mainFrame.setLocation(100, 100);
		chartFrame.setLocation(500, 300);		
		JFrame chartFrame2 = frames.get(Painter.OVERLOAD_CHART_ID);
		chartFrame2.setLocation(600, 300);		
		JFrame chartFrame3 = frames.get(Painter.ERROR_CHART_ID);
		chartFrame3.setLocation(700, 300);		

	}

}
