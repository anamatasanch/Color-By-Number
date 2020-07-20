package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.DataBufferInt;
import com.google.gson.Gson;

@MultipartConfig
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
  String[] hexColors = new String[256];

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
    InputStream fileContent = filePart.getInputStream(); //Create input stream from "file"
    BufferedImage img = ImageIO.read(fileContent); //Use the input stream to create a bufferedImage

    Image newImage = img.getScaledInstance(16, 16, Image.SCALE_DEFAULT);//With buffered image, convert to image to resize
    //Reconvert to buffered image
    BufferedImage pixImg = new BufferedImage(newImage.getWidth(null),newImage.getHeight(null),BufferedImage.TYPE_INT_RGB);
    Graphics bg = pixImg.getGraphics();
    //Draw the original image on the new resized BufferedImage
    bg.drawImage(newImage, 0, 0, null);
    bg.dispose();

    //Get the colors from the pixelated image
    int[] colors = ( (DataBufferInt) pixImg.getRaster().getDataBuffer() ).getData();

    //Go through every color
    for ( int i = 0 ; i < colors.length ; i++ ) {
        Color c = new Color(colors[i]);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        //Using rgb, convert to hex
        String hex = String.format("#%02x%02x%02x", r, g, b);
        hexColors[i] = hex;
    }

    //For now, save image to see the changes/pixalation
    if (img!=null){
      ImageIO.write(pixImg, "jpg", new File("output.jpg") );
      System.out.println("image created");
    }else{
      System.out.println("Image was somehow null");
    }

    IOUtils.closeQuietly(fileContent);
    response.sendRedirect(request.getContextPath() + "/game.html");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(hexColors));
    hexColors = new String[256];
  }

  private String convertToJson(String[] hexColors) {
    Gson gson = new Gson();
    String json = gson.toJson(hexColors);
    return json;
  }
}
