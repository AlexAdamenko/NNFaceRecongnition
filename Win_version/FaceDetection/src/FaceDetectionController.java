import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;  

class FaceDetectionController extends JPanel{  
	
    private static final long serialVersionUID = 1L;  
    private static BufferedImage image;  
	private static int absoluteFaceSize;
	private static CascadeClassifier faceCascade;
	
    public FaceDetectionController(){  
        super();   
    }  

   
	 public static BufferedImage Mat2BufferedImage(Mat m){
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( m.channels() > 1 ) {
		    type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels()*m.cols()*m.rows();
		byte [] b = new byte[bufferSize];
		m.get(0,0,b);
		image = new BufferedImage(m.cols(),m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);  
		return image;
		}
	 
	public void paintComponent(Graphics g){  
       super.paintComponent(g);   
        if (this.image==null) return;  
       g.drawImage(this.image,10,10,this.image.getWidth()/2,this.image.getHeight()/2, null);   
    }  
    
    public static Mat detectAndDisplay(Mat frame) throws IOException
	{
    	      
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();
		
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(grayFrame, grayFrame);
		
		if (absoluteFaceSize == 0)
		{
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0)
			{
				absoluteFaceSize = Math.round(height * 0.2f);
			}
		}
		
		faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(absoluteFaceSize, absoluteFaceSize), new Size());
				
		Rect[] facesArray = faces.toArray();
		
		
		for (int i = 0; i < facesArray.length; i++){
			Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
			}
		
		for (int i=0; i < facesArray.length; i++){
			Rect roi = facesArray[i];
			Mat face = grayFrame.submat(roi).clone();
			File outputfile = new File("sample/savedc" + 10 + ".jpg");
			faceRec.faceRecnet(outputfile);
		    RenderedImage image = Mat2BufferedImage(face);
		    ImageIO.write(image, "jpg", outputfile);
			
		}
		return frame;
			
	}
    
    public void init(){
    	faceCascade = new CascadeClassifier();
        faceCascade.load("resources/haarcascades/haarcascade_frontalface_alt.xml");	
    }
    
    public BufferedImage getGrayScale(BufferedImage inputImage){
        BufferedImage img = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = img.getGraphics();
        g.drawImage(inputImage, 0, 0, null);
        g.dispose();
        return img;
    }
} 