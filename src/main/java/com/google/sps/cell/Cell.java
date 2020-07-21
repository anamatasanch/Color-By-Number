package com.google.sps.cell;

/** An cell in an image. */
public class Cell {

  private final String id;
  private final String colorNeeded;

  public Cell(String id, String colorNeeded) {
    this.id = id;
    this.colorNeeded = colorNeeded;
  }

  public String getId(){
      return this.id;
  }

  public String getColorNeeded(){
      return this.colorNeeded;
  }
}
