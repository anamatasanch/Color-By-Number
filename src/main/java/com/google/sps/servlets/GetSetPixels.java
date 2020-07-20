package com.google.sps.servlets;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;

/** Servlet responsible for getting each pixel from an image. */
@WebServlet("/color-grid")
public class GetSetPixels extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage img = null;
        File imgFile = null;

        try {
            imgFile = new File("Default_Maps/Owl.png");
            img = ImageIO.read(imgFile);
        } catch(IOException error) {
            System.out.println(error);
        }

        // Get image width and height
        int width = img.getWidth(),
            height = img.getHeight();
            
        // Pixel coordinates
        int xAxis = 0,
            yAxis = 0, 
            pixelRGB = img.getRGB(xAxis, yAxis);

        // 2D Array contains hex of image pixels
        String[][] colorGrid = new String[width][height];

        // Store pixel hex colors
        List<String> pixelsHexList = new ArrayList<>();

        for (xAxis = 0; xAxis < width; xAxis++){
            for (yAxis = 0; yAxis < height; yAxis++){
                pixelRGB = img.getRGB(xAxis, yAxis);
                colorGrid[xAxis][yAxis] = "#" + Integer.toHexString(pixelRGB).substring(2);

                pixelsHexList.add(colorGrid[xAxis][yAxis]);

                System.out.println(colorGrid[xAxis][yAxis]);
            }
        }
        
        String[] hexArray = new String[pixelsHexList.size()];
        hexArray = pixelsHexList.toArray(hexArray);
        System.out.println("SIZE: " + pixelsHexList.size());

        response.setContentType("application/json;");
        Gson gson = new Gson();
        String json = gson.toJson(hexArray);
        response.getWriter().println(json);

    }
}