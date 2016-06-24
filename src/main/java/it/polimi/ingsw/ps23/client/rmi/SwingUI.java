package it.polimi.ingsw.ps23.client.rmi;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps23.server.model.initialization.RawObject;

public class SwingUI {

	private static final String CONFIGURATION_PATH = "src/main/java/it/polimi/ingsw/ps23/client/commons/configuration/";
	private static final String CITIES_POSITION_CSV = "citiesPosition.csv";
	private static final String CITIES_CONNECTION_CSV = "citiesConnection.csv";

	private JFrame frame;
	private JPanel mapPanel;

	private BufferedImage readImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	SwingUI() {
		frame = new JFrame();
		frame.setTitle("Council of Four");
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		mapPanel = new JPanel();
		mapPanel.setLayout(null);
		List<String[]> rawCitiesPosition = new RawObject(CONFIGURATION_PATH + CITIES_POSITION_CSV).getRawObject();
		for(String[] rawCityPosition : rawCitiesPosition) {
			BufferedImage cityImage = readImage(CONFIGURATION_PATH + rawCityPosition[5]);
			int x = Integer.parseInt(rawCityPosition[3]);
			int y = Integer.parseInt(rawCityPosition[4]);
			int width = Integer.parseInt(rawCityPosition[1]);
			int height = Integer.parseInt(rawCityPosition[2]);
			Image resizedCityImage = cityImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			JLabel cityLabel = new JLabel(new ImageIcon(resizedCityImage));
			cityLabel.setBounds(x, y, width, height);
			mapPanel.add(cityLabel);
		}
		List<String[]> rawCitiesConnection = new RawObject(CONFIGURATION_PATH + CITIES_CONNECTION_CSV).getRawObject();
		for(String[] rawCityConnection : rawCitiesConnection) {
			BufferedImage connectionImage = readImage(CONFIGURATION_PATH + rawCityConnection[0]);
			int x = Integer.parseInt(rawCityConnection[3]);
			int y = Integer.parseInt(rawCityConnection[4]);
			int width = Integer.parseInt(rawCityConnection[1]);
			int height = Integer.parseInt(rawCityConnection[2]);
			Image resizedConnectionImage = connectionImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			JLabel connectionLabel = new JLabel(new ImageIcon(resizedConnectionImage));
			connectionLabel.setBounds(x, y, width, height);
			mapPanel.add(connectionLabel);
		}
		BufferedImage mapImage = readImage(CONFIGURATION_PATH + "images/mapBackground.png");
		Image resizedMapImage = mapImage.getScaledInstance(800, 464, Image.SCALE_SMOOTH);
		JLabel mapLabel = new JLabel(new ImageIcon(resizedMapImage));
		mapLabel.setBounds(0, 0, 800, 464);
		mapPanel.add(mapLabel);
		frame.add(mapPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new SwingUI();
	}
	
}
