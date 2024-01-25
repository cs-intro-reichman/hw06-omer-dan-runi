// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// // Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		// // Tests the horizontal flipping of an image:
		// imageOut = flippedHorizontally(tinypic);
		// System.out.println();
		// print(imageOut);
		
		// //// Write here whatever code you need in order to test your work.
		// //// You can reuse / overide the contents of the imageOut array.
		// imageOut = flippedVertically(tinypic);
		// System.out.println();
		// print(imageOut);

		// imageOut = grayScaled(tinypic);
		// System.out.println();
		// print(imageOut);

		// imageOut = scaled(tinypic, 3, 5);
		// System.out.println();
		// print(imageOut);
		
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		int red;
		int green;
		int blue;
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numCols; col++)
			{
				red = in.readInt();
        		green = in.readInt();
        		blue = in.readInt();

				image[row][col] = new Color(red, green, blue);
			}
		}

		return image;
	}
	
    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image)
	{
		int numRows = image.length;
		int numCols = image[0].length;

		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numCols; col++)
			{
				print(image[row][col]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image)
	{
		int numRows = image.length;
		int numCols = image[0].length;
		int flipParam = numCols - 1;
	
		Color[][] horizontalImage = new Color[numRows][numCols];
	
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numCols; col++)
			{
				horizontalImage[row][col] = image[row][flipParam - col];
			}
		}
	
		return horizontalImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image)
	{
		int numRows = image.length;
		int numCols = image[0].length;
		int flipParam = numRows - 1;
	
		Color[][] verticalImage = new Color[numRows][numCols];
	
		for (int row = 0; row < numRows; row++)
	
		{
			for (int col = 0; col < numCols; col++)
	
			{
				verticalImage[row][col] = image[flipParam - row][col];
			}
		}
	
		return verticalImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel)
	{
		double red = 0.299 * pixel.getRed();
		double green = 0.587 * pixel.getGreen();
		double blue = 0.114 * pixel.getBlue();
		int lum = (int) (red + blue + green);

		Color luminated = new Color(lum, lum, lum);

		return luminated;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image)
	{
		int numRows = image.length;
		int numCols = image[0].length;
		Color[][] grayImage = new Color[numRows][numCols];//new color 2D array
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numCols; col++)
			{
				grayImage[row][col] = luminance(image[row][col]);
			}
		}
	
		return grayImage;
	}
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height)
	{
		Color[][] scaledImage = new Color[height][width];//new color 2D array
		int imageHeight = image.length;
		int imageWidth = image[0].length;
		int sourceHeight = 0;
		int sourceWidth = 0;

		for (int row = 0; row < height; row++)
		{
			for (int col = 0; col < width; col++)
			{
				sourceHeight = (int)(row * (imageHeight / (double)height));
				sourceWidth = (int)(col * (imageWidth / (double)width));
				sourceHeight = Math.min(sourceHeight, imageHeight - 1);
				sourceWidth = Math.min(sourceWidth, imageWidth - 1);
				scaledImage[row][col] = image[sourceHeight][sourceWidth];
			}
		}

		return scaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha)
	{
		int red = (int)(alpha * (c1.getRed()) + (1 - alpha) * (c2.getRed()));
		int green = (int)(alpha * (c1.getGreen()) + (1 - alpha) * (c2.getGreen()));
		int blue = (int)(alpha * (c1.getBlue()) + (1 - alpha) * (c2.getBlue()));

		Color blended = new Color(red, green, blue);
		return blended;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha)
	{
		int numRows = image1.length;
		int numCols = image1[0].length;
		Color[][] blendedImage = new Color[numRows][numCols];

		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numCols; col++)
			{
				blendedImage[row][col] = blend(image1[row][col], image2[row][col], alpha);
			}
		}

		return blendedImage;
	}


	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n)
	{
		int sourceHeight = source.length;
		int sourceWidth = source[0].length;
		double alpha = 0;

		Color[][] morphedDimension = scaled(target, sourceWidth, sourceHeight);
		Color[][] mixed;

		for (int i = 0; i <= n; i++)
		{
			alpha = (n - i) / n;
			mixed = blend(source, morphedDimension, alpha);
			display(mixed);
			StdDraw.pause(500);

		}
	}

	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}




































































	

  
	
	
	
	
	
	
	
	
	

	
