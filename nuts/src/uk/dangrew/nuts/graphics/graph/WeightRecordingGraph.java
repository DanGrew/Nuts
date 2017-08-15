/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph;

import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.progress.WeighIn;
import uk.dangrew.nuts.progress.WeightProgress;

/**
 * {@link WeightRecordingGraph} provides a {@link LineChart} with weight statistics as series.
 */
public class WeightRecordingGraph extends BorderPane {
   
   private final WeightProgress progress;
   private final LineChart< Number, Number > chart;
   private final WeightRecordingGraphController controller;
   
   /**
    * Constructs a new {@link WeightRecordingGraph}.
    * @param progress the {@link WeightProgress}.
    */
   public WeightRecordingGraph( WeightProgress progress ) {
      this.progress = progress;
      
      NumberAxis xAxis = new NumberAxis();
      xAxis.setLabel( "Epoch Day" );
      
      NumberAxis yAxis = new NumberAxis();
      
      this.controller = new WeightRecordingGraphController( xAxis, yAxis );
      
      this.chart = new LineChart<>(xAxis,yAxis);
      this.chart.setTitle( "Weight Recordings" );
      this.setCenter( chart );
      
      provideSeriesForProperty( "Weight", WeighIn::weight );
      provideSeriesForProperty( "Lean Mass", WeighIn::leanMass );
   }//End Constructor
   
   /**
    * Method to provide an individual series for the given property, providing the running average
    * along side it.
    * @param propertyName the name of the property for the series name.
    * @param propertyRetriever the {@link Function} to retrieve the property from the {@link WeighIn}.
    */
   private void provideSeriesForProperty( 
            String propertyName, 
            Function< WeighIn, ObjectProperty< Double > > propertyRetriever 
   ){
      Series< Number, Number > series = new Series<>();
      series.setName( propertyName );
      chart.getData().add(series);
      
      new WeightRecordingGraphModel(
               progress, 
               r -> r.date().toEpochDay() + 0.0,
               r -> propertyRetriever.apply( r.morningWeighIn() ), 
               series.getData() 
      );
      
      new WeightRecordingGraphModel(
               progress,
               r -> r.date().toEpochDay() + 0.5,
               r -> propertyRetriever.apply( r.eveningWeighIn() ), 
               series.getData() 
      );
      
      Series< Number, Number > average = new Series<>();
      average.setName( propertyName + " Running Average" );
      chart.getData().add( average );
      
      new WeightRecordingGraphModel(
               progress, 
               r -> r.date().toEpochDay() + 0.0,
               r -> propertyRetriever.apply( r.runningAverage() ), 
               average.getData() 
      );
   }//End Method

   /**
    * Access to the {@link WeightRecordingGraphController} for making changes to the graph.
    * @return the {@link WeightRecordingGraphController}.
    */
   public WeightRecordingGraphController controller() {
      return controller;
   }//End Method

}//End Class
