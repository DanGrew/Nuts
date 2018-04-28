/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph.custom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.chart.NumberAxis;

public class GraphController {
   
   private final NumberAxis xAxis;
   private final NumberAxis yAxis;
   private final Map< String, GraphModel > models;

   public GraphController( 
            NumberAxis xAxis, 
            NumberAxis yAxis,
            GraphModel... models
   ) {
      this.xAxis = xAxis;
      this.yAxis = yAxis;
      
      this.xAxis.setAutoRanging( false );
      this.yAxis.setAutoRanging( false );
      
      this.models = new LinkedHashMap<>();
      for ( GraphModel model : models ) {
         this.models.put( model.modelName(), model );
      }
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

   public List< String > seriesByName() {
      return new ArrayList<>( models.keySet() );
   }//End Method

   public void enableSeries( String modelName, boolean enabled ) {
      GraphModel model = models.get( modelName );
      if ( model == null ) {
         return;
      }
      if ( enabled ) {
         model.show();
      } else {
         model.hide();
      }
   }//End Method

}//End Class
