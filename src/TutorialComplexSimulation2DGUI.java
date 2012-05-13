import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.adsl.scenario.ADSLAccesNetworkScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * 
 */

/**
 * @author a.carrera
 *
 */
public class TutorialComplexSimulation2DGUI extends ShanksSimulation2DGUI {

    /**
     * @param sim
     */
    public TutorialComplexSimulation2DGUI(ShanksSimulation sim) {
        super(sim);
    }

    /**
     * @return
     */
    public static String getName() {
        return "Tutorial Shanks - ADSL Access Network - 2D";
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
			TutorialComplexSimulation tut = new TutorialComplexSimulation(System.currentTimeMillis(), ADSLAccesNetworkScenario.class, "ADSL scenario", ADSLAccesNetworkScenario.SUNNY, scenarioProperties);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void locateFrames(Scenario2DPortrayal arg0) {
		// TODO Auto-generated method stub
		
	}
}
