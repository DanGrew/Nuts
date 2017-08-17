/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph;

import javafx.scene.chart.NumberAxis;
/**
 * The {@link WeightRecordingGraphController} is responsible for relaying instructions to the {@link WeightRecordingGraph}
 * in order to control its behaviour and configuration. 
 */
public class WeightRecordingGraphController {
   
   private final NumberAxis xAxis;
   private final NumberAxis yAxis;

   /**
    * Constructs a new {@link WeightRecordingGraphController}.
    * @param xAxis the {@link NumberAxis}.
    * @param yAxis the {@link NumberAxis}.
    */
   public WeightRecordingGraphController( NumberAxis xAxis, NumberAxis yAxis ) {
      this.xAxis = xAxis;
      this.yAxis = yAxis;
      
      this.xAxis.setAutoRanging( false );
      this.yAxis.setAutoRanging( false );
   }//End Constructor

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
