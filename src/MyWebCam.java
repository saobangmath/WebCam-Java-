import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

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
    public static void main(String[] args) throws IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        //set up click button
        JFrame window = new JFrame("Open CV");
        window.setSize(800, 800);
        JButton capture_btn = new JButton("Capture");
        capture_btn.setBounds(0, 500, 50, 50);
        capture_btn.setContentAreaFilled(false);
        capture_btn.setBackground(Color.green);
        capture_btn.setOpaque(true);
        //set up Webcam Panel
        WebcamPanel my_panel = new WebcamPanel(webcam);
        my_panel.setBounds(0,0, 800, 400);
        my_panel.setFPSDisplayed(true);
        my_panel.setImageSizeDisplayed(true);
        my_panel.setDisplayDebugInfo(true);
        my_panel.setMirrored(true);
        // set window Frame for the Camera
        window.add(my_panel);
        window.add(capture_btn);
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
                    ImageIO.write(img, "JPG", new File("test.jpg"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                webcam.close(); //close the webcam ;
                JOptionPane.showMessageDialog(null, "CAPTURE!");
                window.setVisible(false); // close the JFrame window;
            }
        });
    }
}

