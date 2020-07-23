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

    // String array to hold all hex colors of a image
    String[] hexArray = new String[256];

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage img = null;
        File imgFile = null;

        String imgFileRequest = "";
        if(request.getParameter("mapChioce1") != null){
            imgFileRequest = "Default_Maps/Owl.png";
            System.out.println("Owl is Picked");
        }
        else if(request.getParameter("mapChioce2") != null){
            imgFileRequest = "Default_Maps/Orange_Cat.png";
            System.out.println("Cat is Picked");
        }
        else if(request.getParameter("mapChioce3") != null){
            imgFileRequest = "Default_Maps/Pig.png";
            System.out.println("Pig is Picked");
        }
        else{
            System.out.println("NOTHING IS PICKED");
        }

        try {
            imgFile = new File(imgFileRequest);
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

        for (yAxis = 0; yAxis < height; yAxis++){
            for (xAxis = 0; xAxis < width; xAxis++){
                pixelRGB = img.getRGB(xAxis, yAxis);
                colorGrid[xAxis][yAxis] = "#" + Integer.toHexString(pixelRGB).substring(2);

                pixelsHexList.add(colorGrid[xAxis][yAxis]);
            }
        }

        hexArray = pixelsHexList.toArray(hexArray);

        // Redirect back to the HTML page.
        response.sendRedirect(request.getContextPath() + "/generateGrid.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;");
        Gson gson = new Gson();
        String json = gson.toJson(hexArray);
        response.getWriter().println(json);

        hexArray = new String[256];
    }
}
