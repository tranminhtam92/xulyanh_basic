/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baocaoxla;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

/**
 *
 * @author tao
 */
public class xulydetai {

    public BufferedImage gray(BufferedImage img) {

        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage grayimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(img.getRGB(j, i));
                int red = (int) (c.getRed() * 0.21);
                int green = (int) (c.getGreen() * 0.72);
                int blue = (int) (c.getBlue() * 0.07);
                int sum = red + green + blue;
                Color newColor = new Color(sum, sum, sum);
                grayimg.setRGB(j, i, newColor.getRGB());
            }
        }
        return grayimg;
    }

    public BufferedImage bnimage(BufferedImage img) {

        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage bnimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(img.getRGB(j, i));
                int red = (int) (c.getRed() * 0.21);
                int green = (int) (c.getGreen() * 0.72);
                int blue = (int) (c.getBlue() * 0.07);
                int sum = red + green + blue;
                Color newColor;
                if (sum > 128) {
                    newColor = new Color(255, 255, 255);
                } else {
                    newColor = new Color(0, 0, 0);
                }
                bnimg.setRGB(j, i, newColor.getRGB());
            }
        }
        return bnimg;
    }

    public BufferedImage equalization(BufferedImage img) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int[] his = new int[256];
        int[] newhis = new int[256];
        double tong = 0;
        int max = img.getWidth() * img.getHeight();
        for (int i = 0; i <= 255; i++) {
            his[i] = 0;
            newhis[i] = 0;
        }

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                his[c.getRed()]++;// tần suất các màu trong ảnh
                //System.out.print( his[c.getRed()]);
            }
        }
        for (int i = 0; i < 256; i++) {
            tong = tong + (double) his[i] / max;
            newhis[i] = (int) (255 * tong);
            // System.out.print();
        }
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                Color newc = new Color(newhis[c.getRed()], newhis[c.getRed()], newhis[c.getRed()], c.getAlpha());
                pic.setRGB(j, i, newc.getRGB());
            }
        }
        return pic;
    }

    public BufferedImage powerlaw(BufferedImage img, float gamma) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                float r = (float) c.getRed();// neus ko de float thì giá trị của màu mới = 0 hết;
                float g = (float) c.getGreen();
                float b = (float) c.getBlue();
                //int a = c.getAlpha();
                int newred = (int) (255 * Math.pow(r / 255, gamma));
                int newgreen = (int) (255 * Math.pow(g / 255, gamma));
                int newblue = (int) (255 * Math.pow(b / 255, gamma));
                Color gr = new Color((int) newred, (int) newgreen, (int) newblue);
                pic.setRGB(j, i, gr.getRGB());
            }
        }
        return pic;
    }

    public BufferedImage negative(BufferedImage img) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                int r = c.getRed();// neus ko de float thì giá trị của màu mới = 0 hết;
                int g = c.getGreen();
                int b = c.getBlue();
                //int a = c.getAlpha();
                int newred = (255 - r);
                int newgreen = (255 - g);
                int newblue = (255 - b);
                Color gr = new Color(newred, newgreen, newblue);
                pic.setRGB(j, i, gr.getRGB());
            }
        }
        return pic;
    }

    public BufferedImage bitplane(BufferedImage img, int array[]) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int[] red = new int[8];
        int[] green = new int[8];
        int[] blue = new int[8];
        int newred;
        int newgreen;
        int newblue;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                int r = c.getRed();// neus ko de float thì giá trị của màu mới = 0 hết;
                int g = c.getGreen();
                int b = c.getBlue();
                for (int k = 0; k < 8; k++) {
                    red[k] = r % 2;
                    r = r / 2;
                    green[k] = g % 2;
                    g = g / 2;
                    blue[k] = b % 2;
                    b = b / 2;
                }
                newred = red[0];
                newgreen = green[0];
                newblue = blue[0];
                for (int k = 1; k < 8; k++) {
                    newred = (int) (newred + Math.pow(2 * red[k], k * array[k]));
                    newgreen = (int) (newgreen + Math.pow(2 * green[k], k * array[k]));
                    newblue = (int) (newblue + Math.pow(2 * blue[k], k * array[k]));
                }
                Color gr = new Color(newred, newgreen, newblue);
                pic.setRGB(j, i, gr.getRGB());
            }
        }
        return pic;
    }

    public BufferedImage hideimImage(BufferedImage hide, BufferedImage img) {

        BufferedImage pic = img;
        String signature = "abc";
        int width = hide.getWidth();
        int height = hide.getHeight();
        for (int i = 0; i < signature.length(); i++) {
            int[] arraychar = new int[8];
            int so = (int) signature.charAt(i);
            int oRed = 0;
            int oGreen = 0;
            int oBlue = 0;
            int m = 0;
            while (m < 8) {
                if (m >= 4) {
                    oRed += (so % 2) * Math.pow(2, 7 - m);

                } else {
                    oGreen += (so % 2) * Math.pow(2, m);
                }

                so = so / 2;

                m++;
            }
            //for(int j=0;j<=2;j++){
            int iRGB = img.getRGB(i, 0);
            Color iColor = new Color(iRGB);

            int iRed = iColor.getRed();
            System.out.print(iRed);
            int iGreen = iColor.getGreen();
            int iBlue = iColor.getBlue();

            // Tạo giá trị mới cho 3 kênh red, green, blue của ảnh output
            // Lấy giá trị bit của mặt phẳng bit 6, 7, 8 của ảnh cần giấu, tính giá trị mức xám mới ( 6,7,8 -> 3,2,1)
            int n = 0;
            while (n < 8) {
                if (n >= 4) {
                    oRed += (iRed % 2) * Math.pow(2, n);
                    oGreen += (iGreen % 2) * Math.pow(2, n);
                }
                iRed = iRed / 2;
                iGreen = iGreen / 2;
                //iBlue = iBlue / 2;
                n++;
            }
            System.out.print(oRed);
            Color oColor = new Color(oRed, oGreen, iBlue); // Màu mới
            pic.setRGB(i, 0, oColor.getRGB()); //

        }
        for (int i = 3; i < 5; i++) {
            int oRed = 0;
            int oGreen = 0;
            int oBlue = 0;
            int m = 0;
            while (m < 8) {
                if (m >= 4) {
                    oRed += (width % 2) * Math.pow(2, 7 - m);

                } else {
                    oGreen += (width % 2) * Math.pow(2, m);
                }

                width = width / 2;

                m++;
            }
            //for(int j=0;j<=2;j++){
            int iRGB = img.getRGB(i, 0);
            Color iColor = new Color(iRGB);
            int iRed = iColor.getRed();
            int iGreen = iColor.getGreen();
            int iBlue = iColor.getBlue();

            // Tạo giá trị mới cho 3 kênh red, green, blue của ảnh output
            // Lấy giá trị bit của mặt phẳng bit 6, 7, 8 của ảnh cần giấu, tính giá trị mức xám mới ( 6,7,8 -> 3,2,1)
            int n = 0;
            while (n < 8) {
                if (n >= 4) {
                    oRed += (iRed % 2) * Math.pow(2, n);
                    oGreen += (iGreen % 2) * Math.pow(2, n);
                }
                iRed = iRed / 2;
                iGreen = iGreen / 2;
                //iBlue = iBlue / 2;
                n++;
            }

            Color oColor = new Color(oRed, oGreen, iBlue); // Màu mới
            pic.setRGB(i, 0, oColor.getRGB());
        }
        for (int i = 5; i < 7; i++) {
            int oRed = 0;
            int oGreen = 0;
            int oBlue = 0;
            int m = 0;
            while (m < 8) {
                if (m >= 4) {
                    oRed += (height % 2) * Math.pow(2, 7 - m);

                } else {
                    oGreen += (height % 2) * Math.pow(2, m);
                }

                height = height / 2;

                m++;
            }
            //for(int j=0;j<=2;j++){
            int iRGB = img.getRGB(i, 0);
            Color iColor = new Color(iRGB);
            int iRed = iColor.getRed();
            int iGreen = iColor.getGreen();
            int iBlue = iColor.getBlue();

            // Tạo giá trị mới cho 3 kênh red, green, blue của ảnh output
            // Lấy giá trị bit của mặt phẳng bit 6, 7, 8 của ảnh cần giấu, tính giá trị mức xám mới ( 6,7,8 -> 3,2,1)
            int n = 0;
            while (n < 8) {
                if (n >= 4) {
                    oRed += (iRed % 2) * Math.pow(2, n);
                    oGreen += (iGreen % 2) * Math.pow(2, n);
                }
                iRed = iRed / 2;
                iGreen = iGreen / 2;
                //iBlue = iBlue / 2;
                n++;
            }

            Color oColor = new Color(oRed, oGreen, iBlue); // Màu mới
            pic.setRGB(i, 0, oColor.getRGB());
        }
        for (int i = 0; i < hide.getWidth(); i++) {
            for (int j = 0; j < hide.getHeight(); j++) {
                // Duyệt tất cả điểm ảnh, lấy giá trị 3 kênh red, green, blue tại từng điểm ảnh của ảnh ImgHide
                int iRGB = hide.getRGB(i, j);
                Color iColor = new Color(iRGB);

                int iRed = iColor.getRed();

                int iGreen = iColor.getGreen();
                int iBlue = iColor.getBlue();

                // Tạo giá trị mới cho 3 kênh red, green, blue của ảnh output
                int oRed = 0;
                int oGreen = 0;
                int oBlue = 0;

                // Lấy giá trị bit của mặt phẳng bit 6, 7, 8 của ảnh cần giấu, tính giá trị mức xám mới ( 6,7,8 -> 3,2,1)
                int m = 0;
                while (m < 8) {
                    if (m >= 5) {
                        oRed += (iRed % 2) * Math.pow(2, 7 - m);
                        oGreen += (iGreen % 2) * Math.pow(2, 7 - m);
                        oBlue += (iBlue % 2) * Math.pow(2, 7 - m);
                    }
                    iRed = iRed / 2;
                    iGreen = iGreen / 2;
                    iBlue = iBlue / 2;
                    m++;
                }

                // Lấy giá trị 3 kênh red, green, blue tại từng điểm ảnh của ảnh ImgCase
                iRGB = img.getRGB(i, j + 1);
                iColor = new Color(iRGB);

                iRed = iColor.getRed();
                iGreen = iColor.getGreen();
                iBlue = iColor.getBlue();

                // Lấy giá trị bit của mặt phẳng bit 4, 5, 6, 7, 8 của ảnh cần giấu, tính giá trị mức xám mới 
                m = 0;
                while (m < 8) {
                    if (m >= 3) {
                        oRed += (iRed % 2) * Math.pow(2, m);
                        oGreen += (iGreen % 2) * Math.pow(2, m);
                        oBlue += (iBlue % 2) * Math.pow(2, m);
                    }
                    iRed = iRed / 2;
                    iGreen = iGreen / 2;
                    iBlue = iBlue / 2;
                    m++;
                }

                Color oColor = new Color(oRed, oGreen, oBlue); // Màu mới
                pic.setRGB(i, j + 1, oColor.getRGB()); // Gán giá trị rgb mới cho điểm ảnh đó
            }
        }
        return pic;
    }

    public BufferedImage extract(BufferedImage img) {

        char[] array = new char[3];
        int width = 0;
        int height = 0;
        for (int i = 0; i <= 2; i++) {
            Color c = new Color(img.getRGB(i, 0));
            int red = c.getRed();
            int green = c.getGreen();
            int blue = c.getBlue();
            int m = 0;
            int bitdau = 0;
            int bitcuoi = 0;
            int oblue = 0;
            while (m < 8) {
                if (m < 4) {
                    bitdau = (int) (bitdau + (green % 2) * Math.pow(2, m));
                    bitcuoi = (int) (bitcuoi + (red % 2) * Math.pow(2, 7 - m));

                }
                green = green / 2;
                red = red / 2;
                m++;

            }
            array[i] = (char) (bitdau + bitcuoi);

        }
        String signature1 = new String(array);
        if (signature1.equals("abc")) {
            int k = 0;
            for (int i = 3; i <= 4; i++) {
                Color c = new Color(img.getRGB(i, 0));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int m = 0;
                int bitdau = 0;
                int bitcuoi = 0;
                int oblue = 0;
                while (m < 8) {
                    if (m < 4) {
                        bitdau = (int) (bitdau + (green % 2) * Math.pow(2, m + k * 8));
                        bitcuoi = (int) (bitcuoi + (red % 2) * Math.pow(2, (7 - m) + k * 8));

                    }
                    green = green / 2;
                    red = red / 2;
                    m++;

                }
                width = width + bitdau + bitcuoi;
                k++;
            }

            k = 0;
            for (int i = 5; i <= 6; i++) {
                Color c = new Color(img.getRGB(i, 0));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int m = 0;
                int bitdau = 0;
                int bitcuoi = 0;
                int oblue = 0;
                while (m < 8) {
                    if (m < 4) {
                        bitdau = (int) (bitdau + (green % 2) * Math.pow(2, m + k * 8));
                        bitcuoi = (int) (bitcuoi + (red % 2) * Math.pow(2, (7 - m) + k * 8));

                    }
                    green = green / 2;
                    red = red / 2;
                    m++;

                }
                height = height + bitdau + bitcuoi;
                k++;
            }
            BufferedImage pic = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    // Duyệt tất cả điểm ảnh, lấy giá trị 3 kênh red, green, blue tại từng điểm ảnh của ảnh ImgHide
                    int iRGB = img.getRGB(i, j + 1);
                    Color iColor = new Color(iRGB);
                    int red = iColor.getRed();
                    int green = iColor.getGreen();
                    int blue = iColor.getBlue();

                    // Tạo giá trị mới cho 3 kênh red, green, blue của ảnh output
                    int oRed = 0;
                    int oGreen = 0;
                    int oBlue = 0;

                    // Lấy giá trị bit của các mặt phẳng bit 1, 2, 3, tính giá trị mức xám mới ( 6, 7, 8)
                    int m = 0;
                    while (m < 3) {

                        oRed += (red % 2) * Math.pow(2, 7 - m);
                        oGreen += (green % 2) * Math.pow(2, 7 - m);
                        oBlue += (blue % 2) * Math.pow(2, 7 - m);

                        red = red / 2;
                        green = green / 2;
                        blue = blue / 2;
                        m++;
                    }

                    Color oColor = new Color(oRed, oGreen, oBlue); // Màu mới
                    pic.setRGB(i, j, oColor.getRGB()); // Gán giá trị rgb mới cho điểm ảnh đó
                }
            }
            return pic;
        } else {
            JOptionPane.showConfirmDialog(null, "anh chua duoc giau");
        }
        return null;
    }

    public BufferedImage max_filter(BufferedImage img, int n) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int k = n / 2;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {

                int maxr = -1;
                int maxg = -1;
                int maxb = -1;
                int z = i;
                int w = j;
                for (int a = z - k; a <= z + k; a++) {
                    for (int b = w - k; b <= w + k; b++) {
                        if (a >= 0 && a < img.getHeight() && b >= 0 && b < img.getWidth()) {
                            Color c = new Color(img.getRGB(b, a));
                            int red = c.getRed();
                            int green = c.getGreen();
                            int blue = c.getBlue();
                            if (red > maxr) {
                                maxr = red;
                            }
                            if (green > maxg) {
                                maxg = green;
                            }
                            if (blue > maxb) {
                                maxb = blue;
                            }
                        }
                    }
                }
                Color max = new Color(maxr, maxg, maxb);
                pic.setRGB(j, i, max.getRGB());
            }
        }
        return pic;
    }

    public BufferedImage min_filter(BufferedImage img, int n) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int k = n / 2;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {

                int minr = 255;
                int ming = 255;
                int minb = 255;
                Color max = new Color(img.getRGB(j, i));
                if (i >= k && i < img.getHeight() - k && j >= k && j < img.getWidth() - k) {
                    for (int a = i - k; a <= i + k; a++) {
                        for (int b = j - k; b <= j + k; b++) {

                            Color c = new Color(img.getRGB(b, a));
                            int red = c.getRed();
                            int green = c.getGreen();
                            int blue = c.getBlue();
                            if (red < minr) {
                                minr = red;
                            }
                            if (green < ming) {
                                ming = green;
                            }
                            if (blue < minb) {
                                minb = blue;
                            }

                        }
                    }
                    max = new Color(minr, ming, minb);
                    pic.setRGB(j, i, max.getRGB());
                }
                pic.setRGB(j, i, max.getRGB());
            }
        }
        return pic;
    }

    public BufferedImage median_filter(BufferedImage img, int n) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        //pic=img;
        int[] arrayred = new int[n * n];
        int[] arraygreen = new int[n * n];
        int[] arrayblue = new int[n * n];
        //for(int i=0;i<n*n;i++){
        //arrayred[i]=0;
        //arraygreen[i]=0;
        // arrayblue[i]=0;
        //}
        int k = n / 2;
        for (int i = k; i < img.getHeight() - k; i++) {
            for (int j = k; j < img.getWidth() - k; j++) {
                int dem = 0;
                for (int a = i - k; a <= i + k; a++) {
                    for (int b = j - k; b <= j + k; b++) {
                        Color c = new Color(img.getRGB(b, a));
                        arrayred[dem] = c.getRed();
                        arraygreen[dem] = c.getGreen();
                        arrayblue[dem] = c.getBlue();
                        dem++;
                    }
                }
                for (int a = 0; a < n * n - 1; a++) {
                    for (int b = a + 1; b < n * n; b++) {
                        if (arrayblue[a] > arrayblue[b]) {
                            int tam = arrayblue[a];
                            arrayblue[a] = arrayblue[b];
                            arrayblue[b] = tam;
                        }
                        if (arrayred[a] > arrayred[b]) {
                            int tam = arrayred[a];
                            arrayred[a] = arrayred[b];
                            arrayred[b] = tam;
                        }
                        if (arraygreen[a] > arraygreen[b]) {
                            int tam = arraygreen[a];
                            arraygreen[a] = arraygreen[b];
                            arraygreen[b] = tam;
                        }
                    }
                }
                Color newc = new Color(arrayred[n * n / 2], arraygreen[n * n / 2], arrayblue[n * n / 2]);
                pic.setRGB(j, i, newc.getRGB());
            }
        }
        return pic;
    }

    public BufferedImage avg(BufferedImage img, int n) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int k = n / 2;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int red = 0;
                int green = 0;
                int blue = 0;
                Color newc = new Color(img.getRGB(j, i));
                if (i >= k && i < img.getHeight() - k && j >= k && j < img.getWidth() - k) {
                    for (int a = i - k; a <= i + k; a++) {
                        for (int b = j - k; b <= j + k; b++) {

                            Color c = new Color(img.getRGB(b, a));
                            red = red + c.getRed();
                            green = green + c.getGreen();
                            blue = blue + c.getBlue();

                        }
                    }
                    newc = new Color((int) red / (n * n), (int) green / (n * n), (int) blue / (n * n));
                }
                pic.setRGB(j, i, newc.getRGB());
            }
        }

        return pic;
    }

    public BufferedImage gaussian(BufferedImage img, float n) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        float[][] kenel =new float[3][3];
        int center=(kenel.length-1)/2;
        
        float tong = 0;
        for (int i = -center; i < kenel.length-center; i++) {
            for (int j = -center; j <kenel.length-center; j++) {
                
                kenel[i+center][j+center]= (float) ((1 / (2 * Math.PI * n * n)) * Math.exp((-(i * i) - (j * j)) / (2 * n * n)));
                tong = (tong + kenel[i+center][j+center]);
            }
        }
        
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int red = 0;
                int green = 0;
                int blue = 0;
                Color newc = new Color(img.getRGB(j, i));
                if (i >= 1 && i < img.getHeight() - 1 && j >= 1 && j < img.getWidth() - 1) {
                    int dema = 0;
                    for (int a = i - 1; a <= i + 1; a++) {
                        int demb = 0;
                        for (int b = j - 1; b <= j + 1; b++) {
                            Color c = new Color(img.getRGB(b, a));
                            red = (int) (red + c.getRed() * kenel[dema][demb]);
                            green = (int) (green + c.getGreen() * kenel[dema][demb]);
                            blue = (int) (blue + c.getBlue() * kenel[dema][demb]);
                            demb++;
                        }
                        dema++;
                    }
                    red = (int) (red/tong) ;
                    green = (int) (green/tong) ;
                    blue = (int) (blue /tong);
                    if (red < 0) {
                    red = 0;
                }
                if (red > 255) {
                    red = 255;
                }
                if (green < 0) {
                    green = 0;
                }
                if (green > 255) {
                    green = 255;
                }
                if (blue < 0) {
                    blue = 0;
                }
                if (blue > 255) {
                    blue = 255;
                }

                    //int gaussered = (int) (red * Math.exp((-(i * i) - (j * j)) / (2 * n * n)));
                    //int gaussegreen = (int) (green * ((1 / (2 * Math.PI * n * n)) * Math.exp((-(i * i) - (j * j)) / (2 * n * n))));
                    //int gausseblue = (int) (blue * ((1 / (2 * Math.PI * n * n)) * Math.exp((-(i * i) - (j * j)) / (2 * n * n))));
                   
                    newc = new Color(red,green, blue);
                    //return img;
                }

                pic.setRGB(j, i, newc.getRGB());
            }
        }
        return pic;
    }

    public BufferedImage patterning(BufferedImage img, int n) {
        BufferedImage cpimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage cpimg1 = new BufferedImage(img.getWidth() * 4, img.getHeight() * 4, BufferedImage.TYPE_INT_RGB);
        int[][] matrix = {{12, 5, 6, 13}, {4, 0, 1, 7}, {11, 3, 2, 8}, {15, 10, 9, 14}};
        for (int i = 0; i < img.getHeight() - n; i = i + n) {
            for (int j = 0; j < img.getWidth() - n; j = j + n) {
                float avg = 0;
                for (int a = 0; a < n; a++) {
                    for (int b = 0; b < n; b++) {
                        Color c = new Color(img.getRGB(j + b, i + a));
                        avg = avg + (float) c.getRed() / (n * n);
                    }
                }
                for (int a = 0; a < n; a++) {
                    for (int b = 0; b < n; b++) {
                        Color c = new Color((int) avg, (int) avg, (int) avg);
                        cpimg.setRGB(j + b, i + a, c.getRGB());
                    }
                }

            }
        }
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(cpimg.getRGB(j, i));
                int t = (int) (c.getRed() * (float) 17 / 255);

                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        Color half;
                        if (t <= matrix[k][l]) {
                            half = new Color(0, 0, 0);
                        } else {
                            half = new Color(255, 255, 255);
                        }
                        cpimg1.setRGB(j * 4 + l, i * 4 + k, half.getRGB());
                    }
                }

            }
        }
        return cpimg1;
    }

    public BufferedImage dither(BufferedImage img, int n) {
        //BufferedImage cpimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage cpimg1 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int[][] matrix = {{12, 5, 6, 13}, {4, 0, 1, 7}, {11, 3, 2, 8}, {15, 10, 9, 14}};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = (int) (255 * (float) (matrix[i][j] + 0.5) / 16);
            }
        }

        int countx = 0;
        for (int i = 0; i < img.getHeight() - n; i = i + n) {
            int county = 0;
            for (int j = 0; j < img.getWidth() - n; j = j + n) {
                int avg = 0;
                for (int a = 0; a < n; a++) {
                    for (int b = 0; b < n; b++) {
                        Color c = new Color(img.getRGB(j + b, i + a));
                        avg = (int) (avg + (float) c.getRed() / (n * n));
                    }
                }
                for (int a = 0; a < n; a++) {
                    for (int b = 0; b < n; b++) {
                        Color c = null;
                        if (avg > matrix[countx % 4][county % 4]) {
                            c = new Color(255, 255, 255);
                        } else {
                            c = new Color(0, 0, 0);
                        }
                        cpimg1.setRGB(j + b, i + a, c.getRGB());
                    }
                }
                county++;

            }
            countx++;
        }
        return cpimg1;
    }

    public BufferedImage error_diffusion(BufferedImage img, int n) {
        BufferedImage cpimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage cpimg1 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int error;
        for (int i = 0; i < img.getHeight() - n; i = i + n) {
            for (int j = 0; j < img.getWidth() - n; j = j + n) {
                float avg = 0;
                for (int a = 0; a < n; a++) {
                    for (int b = 0; b < n; b++) {
                        Color c = new Color(img.getRGB(j + b, i + a));
                        avg = avg + (float) c.getRed() / (n * n);
                    }
                }
                for (int a = 0; a < n; a++) {
                    for (int b = 0; b < n; b++) {
                        Color c = new Color((int) avg, (int) avg, (int) avg);
                        cpimg.setRGB(j + b, i + a, c.getRGB());
                    }
                }

            }
        }
        int x = 0;
        for (int i = 0; i < img.getHeight() - n; i = i + n) {
            int y = 0;
            for (int j = 0; j < img.getWidth() - n; j = j + n) {
                Color c = new Color(cpimg.getRGB(j, i));
                Color newc;
                if (c.getRed() < 128) {
                    error = c.getRed();
                    newc = new Color(0, 0, 0);
                } else {
                    error = c.getRed() - 255;
                    newc = new Color(255, 255, 255);
                }
                for (int a = 0; a < n; a++) {
                    for (int b = 0; b < n; b++) {

                        cpimg1.setRGB(j + b, i + a, newc.getRGB());
                    }
                }

                if (j < img.getWidth() - n - 1) {
                    Color c1 = new Color(cpimg.getRGB(j + n, i));
                    int value1 = c1.getRed() + (int) (0.4375 * error);
                    if (value1 > 255) {
                        value1 = 255;
                    }
                    if (value1 < 0) {
                        value1 = 0;
                    }
                    Color newc1 = new Color(value1, value1, value1);
                    cpimg.setRGB(j + n, i, newc1.getRGB());
                }
                if (j > 0 && i < img.getHeight() - n - 1) {
                    Color c2 = new Color(cpimg.getRGB(j - n, i + n));
                    int value2 = c2.getRed() + (int) (0.1875 * error);
                    if (value2 > 255) {
                        value2 = 255;
                    }
                    if (value2 < 0) {
                        value2 = 0;
                    }
                    Color newc2 = new Color(value2, value2, value2);
                    cpimg.setRGB(j - n, i + n, newc2.getRGB());
                }
                if (i < img.getHeight() - n - 1) {
                    Color c3 = new Color(cpimg.getRGB(j, i + n));
                    int value3 = c3.getRed() + (int) (0.3125 * error);
                    if (value3 > 255) {
                        value3 = 255;
                    }
                    if (value3 < 0) {
                        value3 = 0;
                    }
                    Color newc3 = new Color(value3, value3, value3);
                    //Color c40=new Color(c4.getRed()+(int)(0.3125*error),c4.getRed()+(int)(0.3125*error),c4.getRed()+(int)(0.3125*error));
                    cpimg.setRGB(j, i + n, newc3.getRGB());
                }
                if (j < img.getWidth() - n - 1 && i < img.getHeight() - n - 1) {
                    Color c4 = new Color(cpimg.getRGB(j + n, i + n));
                    int value4 = c4.getRed() + (int) (0.0625 * error);
                    if (value4 > 255) {
                        value4 = 255;
                    }
                    if (value4 < 0) {
                        value4 = 0;
                    }
                    Color newc4 = new Color(value4, value4, value4);
                    cpimg.setRGB(j + 1, i + 1, newc4.getRGB());
                }

            }
        }
        return cpimg1;

    }

    public BufferedImage Laplacian(BufferedImage img) {
        BufferedImage cpimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int[][] matrix = {{0, 1, 0}, {1, -4, 1}, {0, 1, 0}};
        for (int i = 1; i < img.getHeight() - 1; i++) {
            for (int j = 1; j < img.getWidth() - 1; j++) {
                int red = 0;
                int green = 0;
                int blue = 0;
                int demx = 0;
                for (int a = i - 1; a <= i + 1; a++) {
                    int demy = 0;
                    for (int b = j - 1; b <= j + 1; b++) {
                        Color c = new Color(img.getRGB(b, a));
                        red = red + c.getRed() * matrix[demx][demy];
                        green = green + c.getGreen() * matrix[demx][demy];
                        blue = blue + c.getBlue() * matrix[demx][demy];
                        demy++;
                    }
                    demx++;

                }
                if (red < 0) {
                    red = 0;
                }
                if (red > 255) {
                    red = 255;
                }
                if (green < 0) {
                    green = 0;
                }
                if (green > 255) {
                    green = 255;
                }
                if (blue < 0) {
                    blue = 0;
                }
                if (blue > 255) {
                    blue = 255;
                }
                // System.out.println(red);
                Color c1 = new Color(red, green, blue);
                cpimg.setRGB(j, i, c1.getRGB());
            }
        }
        return cpimg;
    }

    public BufferedImage Sharpened(BufferedImage img) {
        BufferedImage cpimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int[][] matrix = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
        for (int i = 1; i < img.getHeight() - 1; i++) {
            for (int j = 1; j < img.getWidth() - 1; j++) {
                int red = 0;
                int green = 0;
                int blue = 0;
                int demx = 0;
                for (int a = i - 1; a <= i + 1; a++) {
                    int demy = 0;
                    for (int b = j - 1; b <= j + 1; b++) {
                        Color c = new Color(img.getRGB(b, a));
                        red = red + c.getRed() * matrix[demx][demy];
                        green = green + c.getGreen() * matrix[demx][demy];
                        blue = blue + c.getBlue() * matrix[demx][demy];
                        demy++;
                    }
                    demx++;

                }
                if (red < 0) {
                    red = 0;
                }
                if (red > 255) {
                    red = 255;
                }
                if (green < 0) {
                    green = 0;
                }
                if (green > 255) {
                    green = 255;
                }
                if (blue < 0) {
                    blue = 0;
                }
                if (blue > 255) {
                    blue = 255;
                }
                // System.out.println(red);
                Color c1 = new Color(red, green, blue);
                cpimg.setRGB(j, i, c1.getRGB());
            }
        }
        return cpimg;
    }

    public BufferedImage erosion(BufferedImage img) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        //BufferedImage bn= new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        //bn=bnimage(img);
        int[][] matrix = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        for (int i = 1; i < img.getHeight() - 1; i++) {
            for (int j = 1; j < img.getWidth() - 1; j++) {
                Color newc = new Color(img.getRGB(j, i));
                boolean fit = true;
                int demx = 0;
                if (i > 0 && i < img.getHeight() - 1 && j > 0 && j < img.getWidth() - 1) {
                    for (int a = i - 1; a <= i + 1; a++) {
                        int demy = 0;
                        for (int b = j - 1; b <= j + 1; b++) {
                            Color c = new Color(img.getRGB(b, a));
                            if(matrix[demx][demy]==1&&c.getRed()==255){
                                fit=false;
                            }
                        }
                        demx++;

                    }
                    if(fit==true)
                        newc=new Color(0,0,0);
                    else
                        newc=new Color(255,255,255);
                }
                pic.setRGB(j, i,newc.getRGB());
            }
        }
        return pic;
    }
     public BufferedImage dilation(BufferedImage img) {
        BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        //BufferedImage bn= new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        //bn=bnimage(img);
        int[][] matrix = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        for (int i = 1; i < img.getHeight() - 1; i++) {
            for (int j = 1; j < img.getWidth() - 1; j++) {
                Color newc = new Color(img.getRGB(j, i));
                boolean hit = false;
                int demx = 0;
                if (i > 0 && i < img.getHeight() - 1 && j > 0 && j < img.getWidth() - 1) {
                    for (int a = i - 1; a <= i + 1; a++) {
                        int demy = 0;
                        for (int b = j - 1; b <= j + 1; b++) {
                            Color c = new Color(img.getRGB(b, a));
                            if(matrix[demx][demy]==1&&c.getRed()==0){
                                hit=true;
                            }
                        }
                        demx++;

                    }
                    if(hit==true)
                        newc=new Color(0,0,0);
                    else
                        newc=new Color(255,255,255);
                }
                pic.setRGB(j, i,newc.getRGB());
            }
        }
        return pic;
    }
     public BufferedImage boundary(BufferedImage img){
          BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
          BufferedImage erosion= new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
          erosion=erosion(img);
          for(int i=0;i<img.getHeight();i++){
              for(int j=0;j<img.getWidth();j++){
                  Color c;
                  if(img.getRGB(j, i)-erosion.getRGB(j, i)==0){
                      c=new Color(0,0,0);
                  }
                  else{
                      c=new Color(255,255,255);
                  }
                  pic.setRGB(j, i, c.getRGB());
              }
          }
          return pic;
     }
}
