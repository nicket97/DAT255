package platooning;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.paint.Color;

public class Contraster {
	public static BufferedImage fixImg(File f){
		BufferedImage img = new BufferedImage(400, 225, BufferedImage.TYPE_INT_ARGB);
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0 ; i < 400; i++){
			for(int j = 0; j < 225; j++){
				Color rgb = getColorFromInt(img.getRGB(i, j));
				if(rgb.getGreen() > 200 && rgb.getBlue() > 200 && rgb.getRed()< 80){
					rgb.rgb(0, 255, 255);
					img.setRGB(i, j, getIntFromColor(rgb));
				}
			}
		}
		
		
		return img;
		
	}
	public static Color getColorFromInt(int color) {
		int argb = color;
		int r = (argb >> 16) & 0xFF;
		int g = (argb >> 8) & 0xFF;
		int b = (argb >> 0) & 0xFF;
		return Color.rgb(r, g, b);

	}

	/**
	 * Turns rgb-values into a number
	 * @param rgb values to be transformed
	 * @return int representing the colors
	 */
	public static int getIntFromColor(Color rgb) {

		int R = (int) Math.round(255 * rgb.getRed());
		int G = (int) Math.round(255 * rgb.getGreen());
		int B = (int) Math.round(255 * rgb.getBlue());

		R = (R << 16) & 0x00FF0000;
		G = (G << 8) & 0x0000FF00;
		B = B & 0x000000FF;

		return 0xFF000000 | R | G | B;
	}
}
