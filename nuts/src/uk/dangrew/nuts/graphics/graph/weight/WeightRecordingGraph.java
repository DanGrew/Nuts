/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph.weight;

import uk.dangrew.nuts.graphics.graph.custom.Graph;
import uk.dangrew.nuts.graphics.graph.custom.GraphBuilder;
import uk.dangrew.nuts.progress.weight.WeightProgress;

/**
 * {@link WeightRecordingGraph} provides a {@link LineChart} with weight statistics as series.
 */
public class WeightRecordingGraph extends Graph {
   
   /**
    * Constructs a new {@link WeightRecordingGraph}.
    * @param progress the {@link WeightProgress}.
    * @param graphBuilder the {@link GraphBuilder}.
    * @param seriesBuilders the {@link WeightRecordingGraphSeriesBuilder}s for the individual
    * {@link javafx.scene.chart.XYChart.Series} in the graph.
    */
   public WeightRecordingGraph( 
            WeightProgress progress, 
            GraphBuilder graphBuilder, 
            WeightRecordingGraphSeriesBuilder... seriesBuilders
   ) {
      super( graphBuilder );
      
      for ( WeightRecordingGraphSeriesBuilder builder : seriesBuilders ) {
         new WeightRecordingGraphSeries( progress, chart(), builder );
      }
   }//End Constructor
   
}//End Class
