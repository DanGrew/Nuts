package uk.dangrew.nuts.graphics.graph.custom;

import javafx.scene.chart.XYChart.Series;

public interface GraphModel {

   public String modelName();
   
   public Series< Number, Number > series();
   
}//End Interface

