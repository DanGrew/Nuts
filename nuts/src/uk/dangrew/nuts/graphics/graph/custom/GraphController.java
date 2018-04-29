/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph.custom;

import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;

public class GraphController {
   
   private final GraphSeriesVisibility seriesVisibility;
   private final NumberAxis xAxis;
   private final NumberAxis yAxis;

   public GraphController( 
            ObservableList< Series< Number, Number > > chartData,
            NumberAxis xAxis, 
            NumberAxis yAxis
   ) {
      this.seriesVisibility = new GraphSeriesVisibility( chartData );
      
      this.xAxis = xAxis;
      this.yAxis = yAxis;
      
      this.xAxis.setAutoRanging( false );
      this.yAxis.setAutoRanging( false );
   }//End Constructor
   
   public GraphSeriesVisibility seriesVisibility(){
      return seriesVisibility;
   }//End Method

   /**
    * Set the lower bound of the recording axis.
    * @param value the bound.
    */
   public void setRecordingLowerBound( double value ) {
      yAxis.setLowerBound( value );
   }//End Method

   /**
    * Set the upper bound of the recording axis.
    * @param value the bound.
    */
   public void setRecordingUpperBound( double value ) {
      yAxis.setUpperBound( value );
   }//End Method

   /**
    * Set the lower bound of the date axis.
    * @param value the bound.
    */
   public void setDateLowerBound( double value ) {
      xAxis.setLowerBound( value );
   }//End Method

   /**
    * Set the upper bound of the date axis.
    * @param value the bound.
    */
   public void setDateUpperBound( double value ) {
      xAxis.setUpperBound( value );
   }//End Method

}//End Class
