package it.polimi.ingsw.ps23.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
	
	public class LineManipulator extends Application {

	  public static void main(String[] args) throws Exception {
		  Application.launch(args); 
		  }
	  
	  @Override 
	  public void start(final Stage stage) throws Exception {
		  Parent root = FXMLLoader.load(getClass().getResource("sceneMap.fxml"));
	      stage.setTitle("Line Manipulation Sample");
	      Scene newScene = new Scene(root);
	      stage.setScene(newScene);
		  stage.show();
		  
	  }
		
	}		  
	   
	/*make a node movable by dragging it around with the mouse.
	   private void enableDrag() {
	      final Delta dragDelta = new Delta();
	      setOnMousePressed(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	          // record a delta distance for the drag and drop operation.
	          dragDelta.x = getCenterX() - mouseEvent.getX();
	          dragDelta.y = getCenterY() - mouseEvent.getY();
	          getScene().setCursor(Cursor.MOVE);
	        }
	      });
	      setOnMouseReleased(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	          getScene().setCursor(Cursor.HAND);
	        }
	      });
	      setOnMouseDragged(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	          double newX = mouseEvent.getX() + dragDelta.x;
	          if (newX > 0 && newX < getScene().getWidth()) {
	            setCenterX(newX);
	          }  
	          double newY = mouseEvent.getY() + dragDelta.y;
	          if (newY > 0 && newY < getScene().getHeight()) {
	            setCenterY(newY);
	          }  
	        }
	      });
	      setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	          if (!mouseEvent.isPrimaryButtonDown()) {
	            getScene().setCursor(Cursor.HAND);
	          }
	        }
	      });
	      setOnMouseExited(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	          if (!mouseEvent.isPrimaryButtonDown()) {
	            getScene().setCursor(Cursor.DEFAULT);
	          }
	        }
	      });
	    }

	    // records relative x and y co-ordinates.
	    private class Delta { double x, y; }
	  }  
	}
	
	* class Anchor extends Circle { 
	    Anchor(Color color, DoubleProperty x, DoubleProperty y) {
	      super(x.get(), y.get(), 10);
	      Image logoUrl = new Image("file:"+logoURL);
	      //setFill(color.deriveColor(1, 1, 1, 0.5));
	      setFill(new ImagePattern(logoUrl, 0.7, -0.4, 2, 2, true));
	      //setStroke(color);
	      //setStrokeWidth(2);
	      //setStrokeType(StrokeType.OUTSIDE);

	      x.bind(centerXProperty());
	      y.bind(centerYProperty());
	     // enableDrag();
	    }

	  int x = 10;
	  int y = 10;
	  List<String> playersName = new ArrayList<>();
	  playersName.add("alessandro");
	  playersName.add("ciao");
	  Game game = new Game(playersName);
	  Group group = new Group();
	  List<Region> regions = game.getGameMap().getGroupRegionalCity();
	  for(Region region : regions) {
		  List<City> cities = region.getCitiesList();
		  int i = 10;
		  x = x + 100;
		  for(City city: cities) {
			  i = i + 10;
			  DoubleProperty startX = new SimpleDoubleProperty(x);
			  DoubleProperty startY = new SimpleDoubleProperty(y*i);
			  System.out.println(city);
			  if(city.getColor().equals("gold")) {
				  Gold start = new Gold(startX, startY);
				  group.getChildren().add(start);
			  }
			  else {
				  Anchor2 start = new Anchor2(startX, startY);
				  group.getChildren().add(start);
			  }
			 
			  
			  Text text = new Text();
			  text.setText(city.getName().toString());
			  text.setFont(Font.font(null, FontWeight.BOLD, 10));
		      text.setX(x);
		      text.setY(y*i);
			  group.getChildren().add(text);
		  }
	  }
	 
	  DoubleProperty startX = new SimpleDoubleProperty(100);
	  DoubleProperty startY = new SimpleDoubleProperty(100);
	  DoubleProperty endX   = new SimpleDoubleProperty(300);
	  DoubleProperty endY   = new SimpleDoubleProperty(200);
	 
	 
	  Image sfondo = new Image(new FileInputStream(new File("src/main/java/it/polimi/ingsw/ps23/server/model/initialization/images/five_pointed_star.png")));
	  Scene scene = new Scene(group,400,400, new ImagePattern(sfondo));
	 stage.setScene(scene);
	 Image sfondo = new Image(new FileInputStream(new File("src/main/java/it/polimi/ingsw/ps23/server/model/initialization/images/five_pointed_star.png")));
	
	class Gold extends Rectangle {
			public Gold(DoubleProperty x, DoubleProperty y) {
				  super(x.get(), y.get(), 50, 50);
				try {
					Image logoUrl = new Image(new FileInputStream(new File("src/main/java/it/polimi/ingsw/ps23/server/model/initialization/images/citta_gold.png")));
					setFill(new ImagePattern(logoUrl));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setOnMouseClicked(new EventHandler<MouseEvent>()
		        {
		            @Override
		            public void handle(MouseEvent t) {
		                setFill(Color.RED);
		            }
		        });
				//setStroke(Color.BLACK);
			}*/
	 /*class BoundLine extends Line {
    BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
    	DoubleBinding ciaoX = startX.add(50.00);
    	DoubleBinding ciaoY =startY.add(50.00);
      startXProperty().bind(ciaoX);
      startYProperty().bind(ciaoY);
      endXProperty().bind(endX);
      endYProperty().bind(endY);
      setStrokeWidth(2);
      setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
      setStrokeLineCap(StrokeLineCap.BUTT);
      getStrokeDashArray().setAll(10.0, 5.0);
      setMouseTransparent(true);
    }
  }*/

  // a draggable anchor displayed around a point.
 
 /* class Anchor2 extends Rectangle {
	public Anchor2(DoubleProperty x, DoubleProperty y) {
		  super(x.get(), y.get(), 50, 50);
		try {
			Image logoUrl = new Image(new FileInputStream(new File("src/main/java/it/polimi/ingsw/ps23/server/model/initialization/images/five_pointed_star.png")));
			setFill(new ImagePattern(logoUrl));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                setFill(Color.RED);
            }
        });
		//setStroke(Color.BLACK);
	}
  }	*/
