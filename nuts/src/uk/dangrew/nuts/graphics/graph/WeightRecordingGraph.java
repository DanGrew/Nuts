/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.progress.WeightProgress;

/**
 * {@link WeightRecordingGraph} provides a {@link LineChart} with weight statistics as series.
 */
public class WeightRecordingGraph extends BorderPane {
   
   private final LineChart< Number, Number > chart;
   private final WeightRecordingGraphController controller;
   
   /**
    * Constructs a new {@link WeightRecordingGraph}.
    * @param progress the {@link WeightProgress}.
    * @param graphBuilder the {@link WeightRecordingGraphBuilder}.
    * @param seriesBuilders the {@link WeightRecordingGraphSeriesBuilder}s for the individual
    * {@link javafx.scene.chart.XYChart.Series} in the graph.
    */
   public WeightRecordingGraph( 
            WeightProgress progress, 
            WeightRecordingGraphBuilder graphBuilder, 
            WeightRecordingGraphSeriesBuilder... seriesBuilders
   ) {
      NumberAxis xAxis = new NumberAxis();
      xAxis.setLabel( graphBuilder.xAxisTitle() );
      if ( !graphBuilder.isXAxisVisible() ) {
         xAxis.setTickLabelsVisible( false );
         xAxis.setOpacity( 0 );
      }
      
      NumberAxis yAxis = new NumberAxis();
      yAxis.setLabel( graphBuilder.yAxisTitle() );
      yAxis.setVisible( graphBuilder.isYAxisVisible() );
      yAxis.setTickUnit( graphBuilder.xAxisTickInterval() );
      yAxis.setTranslateX( graphBuilder.yAxisTranslation() );
      
      this.controller = new WeightRecordingGraphController( xAxis, yAxis );
      
      this.chart = new LineChart<>( xAxis, yAxis );
      this.chart.setTitle( graphBuilder.chartTitle() );
      
      double chartXTranslation = graphBuilder.chartXTranslation();
      this.chart.setTranslateX( chartXTranslation );
      
      DoubleBinding widthAdjuster = this.widthProperty().subtract( chartXTranslation * 2 );
      this.chart.prefWidthProperty().bind( widthAdjuster );
      this.chart.minWidthProperty().bind( widthAdjuster );
      this.chart.maxWidthProperty().bind( widthAdjuster );
      
      DoubleBinding heightAdjuster = this.heightProperty().subtract( 40 );
      this.chart.prefHeightProperty().bind( heightAdjuster );
      this.chart.minHeightProperty().bind( heightAdjuster );
      this.chart.maxHeightProperty().bind( heightAdjuster );
      
      this.setCenter( chart );
      
      for ( WeightRecordingGraphSeriesBuilder builder : seriesBuilders ) {
         new WeightRecordingGraphSeries( progress, chart, builder );
      }
   }//End Constructor
   
   /**
    * Access to the {@link WeightRecordingGraphController} for making changes to the graph.
    * @return the {@link WeightRecordingGraphController}.
    */
   public WeightRecordingGraphController controller() {
      return controller;
   }//End Method

}//End Class
