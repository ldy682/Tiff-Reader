package com.example.tiff_reader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.awt.image.*;
import java.awt.Color;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Group;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        int sceneWidth, sceneHeight;
        sceneWidth = 1000;
        sceneHeight = 800;
        Button exitButton = new Button("Exit");
        exitButton.setLayoutY(30);
        exitButton.setLayoutX(30);
        Button button = new Button("Open File");
        button.setLayoutX(sceneWidth/2 - 79.55322265625/2);
        button.setLayoutY(sceneHeight - 100);
        Group root = new Group(button, exitButton);

        Group picContainer = new Group();
        root.getChildren().add(picContainer);
        ImageView imageView = new ImageView();
        root.getChildren().add(imageView);

        FileChooser fileChooser = new FileChooser();
        ExtensionFilter filter = new ExtensionFilter("TIFF", "*.tiff", "*.tif");
        fileChooser.getExtensionFilters().add(filter);
        button.setOnAction(actionEvent -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            try{
                picContainer.getChildren().clear();
                BufferedImage image = ImageIO.read(selectedFile);
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();
                imageView.setY(50);
                imageView.setX(sceneWidth/2 - imageWidth/2);

//                int[] reds = new int[imageWidth*imageHeight];
//                int[] greens = new int[imageWidth*imageHeight];
//                int[] blues =  new int[imageWidth*imageHeight];
//                int[] alphas = new int[imageWidth*imageHeight];
//                // gets the arrays pixels

                WritableImage frame = new WritableImage(imageWidth, imageHeight);
                for(int y = 0; y < imageHeight; y++){
                    for(int x = 0; x < imageWidth; x++){
                        int rgb = image.getRGB(x,y);
                        Color rgbColor = new Color(rgb);
                        javafx.scene.paint.Color color = javafx.scene.paint.Color.rgb(
                                rgbColor.getRed(),
                                rgbColor.getGreen(),
                                rgbColor.getBlue(),
                                rgbColor.getAlpha()/255.0);
                        frame.getPixelWriter().setColor(x, y, color);
//                        reds[y*imageWidth + x] = rgbColor.getRed();
//                        greens[y*imageWidth + x] = rgbColor.getGreen();
//                        blues[y*imageWidth + x] = rgbColor.getBlue();
//                        alphas[y*imageWidth + x] = rgbColor.getAlpha();
                    }
                }

                imageView.setImage(frame);
                picContainer.getChildren().add(imageView);

            }
            catch (Exception e){
                System.out.println(e);
            }
        });
        exitButton.setOnAction(actionEvent -> {stage.close();});

        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        stage.setTitle("TIFF Reader");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}