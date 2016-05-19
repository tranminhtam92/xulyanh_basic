/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baocaoxla;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

/**
 *
 * @author tao
 */
public class xuly_compare {

    public ChartPanel displayhistogram(BufferedImage image) {
        
        HistogramDataset dataset = new HistogramDataset();

        final int w = image.getWidth();
        final int h = image.getHeight();
        double[] r = new double[w * h];
        double[] g = new double[w * h];
        double[] b = new double[w * h];
        int dem = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color c = new Color(image.getRGB(j, i));
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
        ChartPanel panel = new ChartPanel(chart);
        return panel;

    }

    public int intersection(BufferedImage image, BufferedImage imgcompare) {
        int[] his = new int[256];
        int[] his1 = new int[256];

        for (int i = 0; i < 256; i++) {
            his[i] = 0;
            his1[i] = 0;

        }
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                his[(c.getRed() + c.getGreen() + c.getBlue()) / 3]++;

            }
        }
        int width1 = imgcompare.getWidth();
        int height1 = imgcompare.getHeight();
        for (int i = 0; i < height1; i++) {
            for (int j = 0; j < width1; j++) {
                Color c = new Color(imgcompare.getRGB(j, i));
                his1[(c.getRed() + c.getGreen() + c.getBlue()) / 3]++;
            }
        }
        double summin = 0;
        double sumhis = 0;

        for (int i = 0; i < 256; i++) {
            summin = summin + Math.min(his[i], his1[i]);
            sumhis = sumhis + his[i];
        }
        int percentred = (int) ((summin / sumhis) * 100);
        return percentred;
    }

    public int Correlation(BufferedImage image, BufferedImage imgcompare) {
        //BufferedImage img=new BufferedImage(image.getWidth(),get, imageType)
        int[] his = new int[256];
        int[] his1 = new int[256];

        for (int i = 0; i < 256; i++) {
            his[i] = 0;
            his1[i] = 0;

        }
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                his[(c.getRed() + c.getGreen() + c.getBlue()) / 3]++;

            }
        }
        int width1 = imgcompare.getWidth();
        int height1 = imgcompare.getHeight();
        for (int i = 0; i < height1; i++) {
            for (int j = 0; j < width1; j++) {
                Color c = new Color(imgcompare.getRGB(j, i));
                his1[(c.getRed() + c.getGreen() + c.getBlue()) / 3]++;
            }
        }
        double sumhis = 0;
        double sumhis1 = 0;

        for (int i = 0; i < 256; i++) {
            sumhis = sumhis + his[i];
            sumhis1 = sumhis1 + his1[i];
        }
        double tu = 0;
        double mau1 = 0;
        double mau2 = 0;
        for (int i = 0; i < 256; i++) {
            tu = tu + (his[i] - sumhis / 256) * (his1[i] - sumhis1 / 256);
            mau1 = mau1 + Math.pow((his[i] - sumhis / 256), 2);
            mau2 = mau2 + Math.pow((his1[i] - sumhis1 / 256), 2);
        }
        double mau = Math.sqrt(mau1 * mau2);
        int compare = (int) (((tu / mau) + 1) * 50);

        return compare;
    }

    public int BHATTACHARYYA(BufferedImage image, BufferedImage imgcompare) {
        int[] his = new int[256];
        int[] his1 = new int[256];

        for (int i = 0; i < 256; i++) {
            his[i] = 0;
            his1[i] = 0;

        }
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                his[(c.getRed() + c.getGreen() + c.getBlue()) / 3]++;

            }
        }
        int width1 = imgcompare.getWidth();
        int height1 = imgcompare.getHeight();
        for (int i = 0; i < height1; i++) {
            for (int j = 0; j < width1; j++) {
                Color c = new Color(imgcompare.getRGB(j, i));
                his1[(c.getRed() + c.getGreen() + c.getBlue()) / 3]++;
            }
        }
        double sumhis=0;
        double sumhis1=0;
        for(int i=0;i<256;i++){
            sumhis=sumhis+his[i];
            sumhis1=sumhis1+his1[i];
        }
        double sum=0;
        for(int i=0;i<256;i++){
            sum=sum+Math.sqrt(his[i]*his1[i]);
        }
        int percent=(int)((1-Math.sqrt(1-sum/Math.sqrt(sumhis*sumhis1)))*100);
        System.out.print(Math.sqrt(1-sum));
        return percent;
    }
    public int chi_square(BufferedImage image, BufferedImage imgcompare){
         int[] his = new int[256];
         int[]hisnomal= new int[256];
        int[] his1 = new int[256];

        for (int i = 0; i < 256; i++) {
            his[i] = 0;
            hisnomal[i]=0;
            his1[i] = 0;

        }
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                his[(c.getRed() + c.getGreen() + c.getBlue()) / 3]++;
               // hisnomal[255-(c.getRed() + c.getGreen() + c.getBlue()) / 3]++;

            }
        }
        int width1 = imgcompare.getWidth();
        int height1 = imgcompare.getHeight();
        for (int i = 0; i < height1; i++) {
            for (int j = 0; j < width1; j++) {
                Color c = new Color(imgcompare.getRGB(j, i));
                his1[(c.getRed() + c.getGreen() + c.getBlue()) / 3]++;
            }
        }
        double sum=0;
        double sum1=0;
        for(int i=0;i<256;i++){
            sum1=sum1+Math.pow(hisnomal[i]-his[i],2)/(hisnomal[i]+his[i]);
        }
         for(int i=0;i<256;i++){
            sum=sum+Math.pow(his1[i]-his[i],2)/(his1[i]+his[i]);
        }
         int percent=(int)((1-sum/sum1)*100);
         return percent;
    }
}
