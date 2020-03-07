package algoritmoGenetico;

import javax.swing.*;
import org.math.plot.*;
import vista.Vista;

public class Main {
	
    public static org.math.plot.Plot2DPanel plot;
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Vista vista = new Vista();
        

        JPanel grafica = vista.getGraficaPanel();

        // create your PlotPanel (you can use it as a JPanel)
        plot = new Plot2DPanel();

        
        plot.setSize(grafica.getWidth(),grafica.getHeight());
        
        // define the legend position
        plot.addLegend("SOUTH");
        
        grafica.add(plot);
            
        vista.setVisible(true);
        
    }
}
