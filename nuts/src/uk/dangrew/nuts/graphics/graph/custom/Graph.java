/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph.custom;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;

public class Graph extends BorderPane {
   
   private final LineChart< Number, Number > chart;
   private final GraphController controller;
   
   public Graph( 
            GraphBuilder graphBuilder 
   ) {
      NumberAxis xAxis = new NumberAxis();
      xAxis.setLabel( graphBuilder.xAxisTitle() );
      if ( !graphBuilder.isXAxisVisible() ) {
//         xAxis.setTickLabelsVisible( false );
         xAxis.setOpacity( 0 );
      }
      xAxis.setTickUnit( graphBuilder.xAxisTickInterval() );
      if ( graphBuilder.xAxisTickFormatter() != null ) {
         xAxis.setTickLabelFormatter( graphBuilder.xAxisTickFormatter() );
      }
      
      NumberAxis yAxis = new NumberAxis();
      yAxis.setLabel( graphBuilder.yAxisTitle() );
      yAxis.setVisible( graphBuilder.isYAxisVisible() );
      yAxis.setTranslateX( graphBuilder.yAxisTranslation() );
      
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
      
      this.controller = new GraphController( chart().getData(), xAxis, yAxis );
      this.setCenter( chart );
   }//End Constructor
   
   public GraphController controller() {
      return controller;
   }//End Method
   
   protected LineChart< Number, Number > chart(){
      return chart;
   }//End Method

}//End Class
