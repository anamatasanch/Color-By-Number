package com.google.sps.servlets;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import data.Pixel;

import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/** Servlet responsible for getting each pixel from an image. */
@WebServlet("/color-grid")
public class GetSetPixels extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage img = null;
        File imgFile = null;

        try {
            imgFile = new File("Default_Maps/Owl.jpg");
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

        // Store pixel object
        List<Pixel> pixelsList = new ArrayList<>();

        for (xAxis = 0; xAxis < width; xAxis++){
            for (yAxis = 0; yAxis < height; yAxis++){
                pixelRGB = img.getRGB(xAxis, yAxis);
                colorGrid[xAxis][yAxis] = "#" + Integer.toHexString(pixelRGB).substring(2);

                // Pixel object. Could be change to Cell object instead to get 
                    // the Cell(String id, String colorNeeded) either works
                Pixel pixel = new Pixel(colorGrid[xAxis][yAxis], xAxis, yAxis);
                pixelsList.add(pixel);
            }
        }
        
        response.setContentType("application/json;");
        Gson gson = new Gson();
        String json = gson.toJson(pixelsList);
        response.getWriter().println(json);

    }
}