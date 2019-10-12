import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyWebCam{
    private static Mat cropImage;
    public static void main(String[] args) throws IOException {
        // set up openCV Library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        //set up click button
        JFrame window = new JFrame("Open CV");
        window.setSize(400, 500);

        JButton capture_btn = new JButton("Capture");
        capture_btn.setBounds(0, 500, 40, 20);

        //set up Webcam Panel
        WebcamPanel my_panel = new WebcamPanel(webcam);
        my_panel.setBounds(0,0, 400, 300);
        my_panel.setFPSDisplayed(true);
        my_panel.setImageSizeDisplayed(true);
        my_panel.setDisplayDebugInfo(true);
        my_panel.setMirrored(true);
        // set window Frame for the Camera
        window.add(my_panel);
        window.add(capture_btn);
        // window frame set up
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        capture_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage img = webcam.getImage();
                try {
                    ImageIO.write(img, "JPG", new File("D://test.jpg"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                webcam.close(); //close the webcam ;
                JOptionPane.showMessageDialog(null, "CAPTURE!");
                window.setVisible(false); // close the JFrame window;
                FaceExtracting();
            }
        });
    }
    public static void FaceExtracting(){
        CascadeClassifier faceDetector=new CascadeClassifier();
        faceDetector.load("D://Setup/Coding software/opencv/sources/data/haarcascades/haarcascade_frontalface_alt_tree.xml");
        Mat image = Imgcodecs.imread("D://test.jpg");
        System.out.println("Working!");
        // Detecting faces
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image,faceDetections);
        // Creating a rectangular box showing faces detected
        Rect rectCrop;
        for (Rect rect : faceDetections.toArray())
        {
            Imgproc.rectangle(image, new org.opencv.core.Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y  + rect.height),
                    new Scalar(0, 255, 0));
            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
            // Saving the output image
            Imgcodecs.imwrite("D://test.jpg", image);
            Mat markedImage = new Mat(image,rectCrop);
            Imgcodecs.imwrite("D://test.jpg", markedImage ); // change to the link of the image you want to
        }
    }
}

