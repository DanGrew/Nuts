/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.nuts.progress.WeightProgress;

/**
 * {@link WeightRecordingGraphSeries} is responsible for constructing a {@link javafx.scene.chart.XYChart.Series} for a
 * {@link uk.dangrew.nuts.progress.WeighIn} property, morning and evening and running average.
 */
public class WeightRecordingGraphSeries {
   
   /**
    * Constructs a new {@link WeightRecordingGraphSeries}.
    * @param progress the {@link WeightProgress}.
    * @param chart the {@link LineChart} to add the {@link javafx.scene.chart.XYChart.Series} to.
    * @param builder the {@link WeightRecordingGraphSeriesBuilder}.
    */
   WeightRecordingGraphSeries( 
            WeightProgress progress,
            LineChart< Number, Number > chart,
            WeightRecordingGraphSeriesBuilder builder
   ) {
      Series< Number, Number > series = new Series<>();
      series.setName( builder.seriesName() );
      chart.getData().add( series );
      
      new WeightRecordingGraphModel(
               "Morning " + builder.seriesName(),
               progress, 
               r -> r.date().toEpochDay() + 0.0,
               r -> builder.propertyRetriever().apply( r.morningWeighIn() ), 
               series.getData() 
      );
      
      new WeightRecordingGraphModel(
               "Evening " + builder.seriesName(),
               progress,
               r -> r.date().toEpochDay() + 0.5,
               r -> builder.propertyRetriever().apply( r.eveningWeighIn() ), 
               series.getData() 
      );
      
      Series< Number, Number > average = new Series<>();
      average.setName( builder.seriesName() + " Running Average" );
      chart.getData().add( average );
      
      new WeightRecordingGraphModel(
               average.getName(),
               progress, 
               r -> r.date().toEpochDay() + 0.0,
               r -> builder.propertyRetriever().apply( r.runningAverage() ), 
               average.getData() 
      );
   }//End Method

}//End Class
