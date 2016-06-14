package it.polimi.ingsw.ps23.server.model;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxEdgeLabelLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxGraphLayout;
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
		 jgxAdapter.getModel().beginUpdate();
		 String styleName = "stile" ;
		/* Hashtable<String, Object> style = new Hashtable<String, Object>();
		    style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_IMAGE);
		    style.put(mxConstants.STYLE_IMAGE, "src/main/java/it/polimi/ingsw/ps23/server/model/initialization/images/five_pointed_star.png");
		    style.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_BOTTOM);
		    style.put(mxConstants.STYLE_NOLABEL, "1");*/
		    mxStylesheet styleSheet = jgxAdapter.getStylesheet();
		
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
		 
		 //mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
		 mxEdgeLabelLayout layout = new mxEdgeLabelLayout(jgxAdapter);
		 jgxAdapter.setStylesheet(styleSheet);
		 jgxAdapter.setConnectableEdges(false);
		 jgxAdapter.setCellsDisconnectable(false);
		
		 
		 styleSheet.getDefaultVertexStyle().put(mxConstants.STYLE_FILLCOLOR, "#5DA2E8");
		 styleSheet.getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");
		 
		
		 styleSheet.getDefaultVertexStyle().put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_IMAGE);
		 styleSheet.getDefaultVertexStyle().put(mxConstants.SHAPE_IMAGE, "src/main/java/it/polimi/ingsw/ps23/server/model/initialization/images/five_pointed_star.png");
		 Object[] roots = jgxAdapter.getChildCells(jgxAdapter.getDefaultParent(), true, false);		
		 for (int i = 0; i < roots.length; i++) {
		     Object[] root = {roots[i]};
		     if(i < 5) {
		    	 int x = 0;
			     int y = i;
			     if( i%2 != 0) {
			    	 x = 300;
			    	 y = i - 1;
			     }
			     jgxAdapter.moveCells(root, 50 + x , y * 50 + 10);
		     }
		     int k = 0;
		     if(i >= 5 && i < 10) {
		    	 int x = i + 1;
		    	 int y = 0;
		    	 if( x%3 != 0) {
		    		 x--;
			    	 y = 100;
			    	 k = 100*((x-5)%3);
			     }
		    	 jgxAdapter.moveCells(root, (x - 5) * 400 + 200 , 50 + y + k);
		     }
		     
		     if(i >= 10) {
		    	 int x = i;
		    	 int y = 0;
		    	 x--;
		    	 if( x%3 != 0) {
		    		 x++;
			    	 y = 100;
			    	 k = 100*((x-5)%3);
			    	 x--;
			     }
		    	 x++;
		    	 jgxAdapter.moveCells(root, (x - 10 ) * 400 + 1000 , 50 + y + k);
		    	 

		    	
		     }
		     jgxAdapter.getView().clear(root, true, true);
		     jgxAdapter.getView().validate();
		  }
		  jgxAdapter.refresh();
		  jgxAdapter.getModel().endUpdate();
		  Object parent = jgxAdapter.getDefaultParent();
		  Object v2 = jgxAdapter.insertVertex(parent, null, "destination", 200, 20,80, 30, styleName);
		  layout.execute(jgxAdapter.getDefaultParent());		
	}

}
	
		// styleSheet.getDefaultVertexStyle().put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_IMAGE);
		// styleSheet.getDefaultVertexStyle().put(mxConstants.SHAPE_IMAGE, "src/main/java/it/polimi/ingsw/ps23/server/model/initialization/images/five_pointed_star.png");
        // style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_IMAGE);
        // style.put(mxConstants.STYLE_IMAGE, "src/main/java/it/polimi/ingsw/ps23/server/model/initialization/images/five_pointed_star.png");
        // style.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_CENTER);
		// style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_IMAGE);
		// style.put(mxConstants.STYLE_IMAGE, "Users/aless/Downloads/five_pointed_star.png");
		// layout.arrangeGroups(getComponentListeners(), getHeight());
		// double edgeDistance = 150;
		// layout.setForceConstant(edgeDistance);
		
		
		
	
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
	 