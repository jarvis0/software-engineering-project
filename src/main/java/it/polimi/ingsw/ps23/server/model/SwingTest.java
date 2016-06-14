package it.polimi.ingsw.ps23.server.model;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

import org.jgrapht.ext.JGraphXAdapter;
import it.polimi.ingsw.ps23.server.model.map.regions.City;

public class SwingTest extends JApplet{
	
	private static final Dimension DEFAULT_SIZE = new Dimension(10120, 10500);

	JFrame frame;
	private JPanel mainPanel;
	private JPanel results;
	private Game game;
	private Graph<City, DefaultEdge> citiesGraph;
	public SwingTest() {
	
			List<String> playersName = new ArrayList<>();
			playersName.add("Alessandro");
			playersName.add("Mirco");
			try {
				game = new Game(playersName);
			} catch (NoCapitalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			citiesGraph = (Graph<City, DefaultEdge>)game.getGameMap().getCitiesGraph().getGraph();
			
			
		}
	
	
	 public static void main(String [] args) {

	        
	        SwingTest applet = new SwingTest();
	        applet.init();
	        
	       
	        JFrame frame = new JFrame();
	        frame.getContentPane().add(applet);
	        frame.setTitle("JGraphT Adapter to JGraph Demo");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.pack();
	        frame.setVisible(true);
	        
	        
	    }


	public void init() {
		 JGraphXAdapter<City, DefaultEdge> jgxAdapter = new JGraphXAdapter<>(citiesGraph);
		 getContentPane().add(new mxGraphComponent(jgxAdapter));
	     resize(DEFAULT_SIZE);
	     mxGraphComponent mxc=new mxGraphComponent(jgxAdapter);
	     mxc.setAntiAlias(true);
		 mxc.setConnectable(false);
		 mxc.setExportEnabled(false);
		 mxc.setFoldingEnabled(false);
		 mxc.setPanning(true);
		 mxc.setTextAntiAlias(true);
		 add(new JScrollPane(mxc),BorderLayout.CENTER);
		 String styleName = "stile" ;
		 mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
		 mxStylesheet styleSheet = jgxAdapter.getStylesheet();
		 Hashtable<String, Object> style = new Hashtable<String, Object>();
         style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_IMAGE);
         style.put(mxConstants.STYLE_IMAGE, "src/main/java/it/polimi/ingsw/ps23/server/model/initialization/images/five_pointed_star.png");
         style.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_CENTER);
         styleSheet.putCellStyle(styleName, style);
         styleSheet.getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");
		 
		
		// style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_IMAGE);
		// style.put(mxConstants.STYLE_IMAGE, "Users/aless/Downloads/five_pointed_star.png")
		 layout.arrangeGroups(getComponentListeners(), getHeight());
		 double edgeDistance = 150;
		 layout.setForceConstant(edgeDistance);
		 layout.execute(jgxAdapter.getDefaultParent());
		
		
		
	}

	
}
/*public JGraphXGraphPanel(NAR n){
	  super(new BorderLayout());
	  NARGraph g=new NARGraph();
	  g.add(n,IncludeEverything,new NARGraph.DefaultGraphizer(true,true,true,true,true));
	  jgxAdapter=new JGraphXAdapter(g){
	  }
	;
	  mxGraphComponent mxc=new mxGraphComponent(jgxAdapter){
	  }
	;
	  
	  mxFastOrganicLayout layout=new mxFastOrganicLayout(jgxAdapter);
	  layout.setForceConstant(150);
	  layout.execute(jgxAdapter.getDefaultParent());
	  jgxAdapter.setConnectableEdges(false);
	  jgxAdapter.setCellsDisconnectable(false);
	  jgxAdapter.setEdgeLabelsMovable(false);
	}*/
	 