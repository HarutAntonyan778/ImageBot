import javafx.scene.input.KeyCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by harut on 9/24/17.
 */
public class Main {
    public static BufferedImage bfImg = null;
    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    public static final Point startPoint = new Point(224,181);
    public static Point point = startPoint;
    public static final int ONE_PIXEL = 1;

    public static Map<Color,Point> colors = new HashMap<Color, Point>();


    public static void main(String[] args) throws IOException, InterruptedException, AWTException {
        File file = new File("/home/harut/Pictures/dog.jpeg");
        colors.put(Color.WHITE,new Point(15,546));
        colors.put(Color.BLACK,new Point(28,546));
        colors.put(new Color(160,160,160),new Point(46,546));
        colors.put(new Color(128,128,128),new Point(15,563));
        colors.put(new Color(64,64,64),new Point(28,563));
        colors.put(new Color(48,48,48),new Point(46,563));
        bfImg = ImageIO.read(file);
        WIDTH = bfImg.getWidth();
        HEIGHT = bfImg.getHeight();
        openPinta();
        Thread.sleep(3000);
        writeImage();

    }



    public static Color[][] getColoursOfImage() {
        Color[][] colours = new Color[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT ; i++) {
            for (int j = 0; j < WIDTH ; j++) {
                colours[i][j] = new Color(bfImg.getRGB(j,i));

            }

        }
        return colours;
    }


    public static void writeImage() throws AWTException {
        Robot robot = new Robot();
        robot.mouseMove(point.x,point.y);
        Color [][] myColors = getColoursOfImage();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j <WIDTH ; j++) {
                Point pnt = getBestColor(myColors[i][j]);
                robot.mouseMove(pnt.x,pnt.y);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                robot.mouseMove(point.x,point.y);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseMove(point.x + ONE_PIXEL,point.y);
                point = new Point(point.x + ONE_PIXEL,point.y);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);

            }
            point = new Point(startPoint.x,point.y + ONE_PIXEL);

        }
    }


    public static int distantion(Color clr1, Color clr2) {

        int dist = (int)(Math.sqrt(Math.pow(clr1.getRed() - clr2.getRed(),2) +Math.pow(clr1.getBlue() - clr2.getBlue(),2) +Math.pow(clr1.getGreen() - clr2.getGreen(),2)) );

        return dist;
    }


    public static Point getBestColor(Color color) {
        Point bestColor = colors.get(Color.WHITE);
        int minDist = distantion(Color.WHITE,color);

        for(Map.Entry item : colors.entrySet()) {
            int dist = distantion((Color)item.getKey(),color);
            if (dist < minDist) {
                bestColor = colors.get(item.getKey());
                minDist = dist;
            }
        }
        return bestColor;
    }

    private static void openPinta() throws InterruptedException {
        openTerminal();
        Thread.sleep(1000);
        try {
            Robot rbt = new Robot();
            rbt.keyPress(KeyEvent.VK_P);
            rbt.keyPress(KeyEvent.VK_I);
            rbt.keyPress(KeyEvent.VK_N);
            rbt.keyPress(KeyEvent.VK_T);
            rbt.keyPress(KeyEvent.VK_A);
            rbt.keyPress(KeyEvent.VK_ENTER);
            rbt.keyRelease(KeyEvent.VK_ENTER);
            rbt.keyRelease(KeyEvent.VK_P);
            rbt.keyRelease(KeyEvent.VK_I);
            rbt.keyRelease(KeyEvent.VK_N);
            rbt.keyRelease(KeyEvent.VK_T);
            rbt.keyRelease(KeyEvent.VK_A);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }


    private static void openTerminal() throws InterruptedException {
        Thread.sleep(1000);
        try {
            Robot rbt = new Robot();
            rbt.keyPress(KeyEvent.VK_CONTROL);
            rbt.keyPress(KeyEvent.VK_ALT);
            rbt.keyPress(KeyEvent.VK_T);
            rbt.keyRelease(KeyEvent.VK_CONTROL);
            rbt.keyRelease(KeyEvent.VK_ALT);
            rbt.keyRelease(KeyEvent.VK_T);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
