package seachart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import render.Renderer;
import s57.S57dec;
import s57.S57map;

/**
 * GUI Code
 * 
 *
 */
public class SwingTemplateJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int CANVAS_WIDTH = 640;
	public static final int CANVAS_HEIGHT = 480;
	public static final String TITLE = "...Test...";
	private Rectangle rectangularBounds;
	private int zoom;
	private double factor;
	private ChartImage chartImage;
	private S57map map;

	/**
	 * Ctor
	 */
	public SwingTemplateJPanel() {
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		FileInputStream in;
		try {
			in = new FileInputStream("D:\\Shipping\\Alte ENC zum Üben\\DE421070.000");
			map = new S57map(true);
			S57dec.decodeChart(in, map);
			rectangularBounds = this.getBounds();
			zoom = 14;
			factor = 3;
			chartImage = new ChartImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		Renderer.reRender((Graphics2D) g, rectangularBounds, zoom, factor, map, chartImage);
//			g.drawRect(10, 10, 100, 100);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(TITLE);
				frame.setContentPane(new SwingTemplateJPanel());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}