import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.JFrame;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;


public class FaceDetection {  
	
	
	public static void start(){
		
		FaceDetectionController controller = new FaceDetectionController();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);       
        String window_name = "Face detection";  
        JFrame frame = new JFrame(window_name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setSize(360,180);  
        frame.setContentPane(controller);       
        frame.setVisible(true);     
        controller.init();
        
        Mat webcam_image=new Mat();  
        VideoCapture capture =new VideoCapture(0);  
        
        //capture.set(5,10);
        
        if( capture.isOpened())  
        {  
            while( true )  
            {  
                capture.read(webcam_image);  
                if( !webcam_image.empty() )  
                {   
                    frame.setSize(webcam_image.width()/2,webcam_image.height()/2);  
                    try {
						webcam_image=FaceDetectionController.detectAndDisplay(webcam_image);
					} catch (IOException e) {
						e.printStackTrace();
					} 
                    controller.Mat2BufferedImage(webcam_image); 
                    controller.repaint();
                }  
            }  
        }  
        return;  
	}

    public static void main(String arg[]) throws IOException{  
    	
    	start();     
        
    }  
    
}