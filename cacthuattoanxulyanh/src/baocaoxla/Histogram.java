package baocaoxla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

/**
 * Draw a histogram.
 * 
 * @author Hideki Shima
 *
 */
public class Histogram extends JFrame {
  
  //private static final long serialVersionUID = 1L;

  public void display(BufferedImage img) {
    Histogram frame = new Histogram(img);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(10, 10, 500, 500);
    frame.setTitle("Histogram");
    //frame.setVisible(true);
  }

  public Histogram(BufferedImage img) {
    JFreeChart chart = createChart(img);
    
    try {
      //ChartUtilities.saveChartAsPNG(new File("test.png"), chart, 300, 300);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    ChartPanel cpanel = new ChartPanel(chart);
    getContentPane().add(cpanel, BorderLayout.CENTER);
  }
 
  
  private JFreeChart createChart(BufferedImage img) {
     HistogramDataset dataset = new HistogramDataset();

        final int w = img.getWidth();
        final int h = img.getHeight();
        double[] r = new double[w * h];
        double[] g = new double[w * h];
        double[] b = new double[w * h];
        int dem = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color c = new Color(img.getRGB(j, i));
                r[dem] = c.getRed();
                g[dem] = c.getGreen();
                b[dem] = c.getBlue();
                dem++;
            }
        }
        //r = raster.getSamples(0, 0, w, h, 0, r);
        dataset.addSeries("Red", r, 256);
        //r = raster.getSamples(0, 0, w, h, 1, r);
        dataset.addSeries("Green", g, 256);
        // r = raster.getSamples(0, 0, w, h, 2, r);
        dataset.addSeries("Blue", b, 256);
        // chart
        JFreeChart chart = ChartFactory.createHistogram("Histogram", "Value",
                "Count", dataset, PlotOrientation.VERTICAL, true, true, false);
    return chart;
  }
  
}