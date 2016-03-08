import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class faceRec {

 
 public static void faceRecnet(File a){
	    // load trained neural network saved with Neuroph Studio (specify some existing neural network file here)
	    NeuralNetwork nnet = NeuralNetwork.load("nrt.nnet"); // load trained neural network saved with Neuroph Studio
	    // get the image recognition plugin from neural network
	    ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin)nnet.getPlugin(ImageRecognitionPlugin.class); // get the image recognition plugin from neural network

	    try {
	         // image recognition is done here (specify some existing image file)
	        HashMap<String, Double> output = imageRecognition.recognizeImage(a);
	        //System.out.println(output.toString());
	        for (Entry<String, Double> e : output.entrySet()) {
	        	if (e.getValue() >= 0.8 && (e.getKey().equals("savedb0") || e.getKey().equals("savedb0")
	        			|| e.getKey().equals("savedb1") || e.getKey().equals("savedb2") || e.getKey().equals("savedb3")
	        			|| e.getKey().equals("savedb4") || e.getKey().equals("savedb5") || e.getKey().equals("savedb6")
	        			|| e.getKey().equals("savedb7") || e.getKey().equals("savedb8") || e.getKey().equals("savedb9")
	        			|| e.getKey().equals("savedb10"))) {
	                System.out.println("Alex Adamenko" + " " +  e.getValue() + " " + e.getKey());
	            }
	        }
	        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("recognized.txt", true)));
	        out.println(output.toString());
	        out.close();
	    } catch(IOException ioe) {
	        ioe.printStackTrace();
	    }
 }
}