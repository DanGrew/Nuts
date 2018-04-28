/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph.weight;

import java.net.URL;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import uk.dangrew.nuts.progress.weight.WeighIn;
import uk.dangrew.nuts.progress.weight.WeightProgress;

/**
 * {@link CombinedWeightRecordingGraph} provides two {@link WeightRecordingGraph}s, one for weight
 * and one for body fat, one on top of the other to give the appearance of a combined graph.
 */
public class CombinedWeightRecordingGraph extends BorderPane {
   
   static final URL SECONDARY_CHART_STYLE_SHEET = CombinedWeightRecordingGraph.class.getResource( "secondary-chart.css" );
   static final URL PRIMARY_CHART_STYLE_SHEET = CombinedWeightRecordingGraph.class.getResource( "primary-chart.css" );
   
   /**
    * Constructs a new {@link CombinedWeightRecordingGraph}.
    * @param progress the {@link WeightProgress} to display.
    */
   public CombinedWeightRecordingGraph( WeightProgress progress ) {
      StackPane content = new StackPane();
      setCenter( content );
      
      WeightRecordingGraph weightAndLeanGraph = new WeightRecordingGraph( 
               progress, 
               new WeightRecordingGraphBuilder()
                  .withChartTitle( "Weight Recordings" )
                  .withChartXTranslation( 30 )
                  .withXAxisTitle( "Epoch Day" )
                  .withYAxisTitle( "Weight (lbs)" )
                  .withYAxisTranslation( -60 ), 
               new WeightRecordingGraphSeriesBuilder()
                  .withName( "Weight" )
                  .forProperty( WeighIn::weight ),
               new WeightRecordingGraphSeriesBuilder()
                  .withName( "Lean Mass" )
                  .forProperty( WeighIn::leanMass )
      );
      weightAndLeanGraph.getStylesheets().addAll( PRIMARY_CHART_STYLE_SHEET.toExternalForm() );
      content.getChildren().add( weightAndLeanGraph );
      
      WeightRecordingGraph fatGraph = new WeightRecordingGraph( 
               progress, 
               new WeightRecordingGraphBuilder()
                  .withChartTitle( "..." )
                  .withChartXTranslation( 30 )
                  .withXAxisTitle( "..." )
                  .withXAxisVisible( false )
                  .withXAxisTickInterval( 1 )
                  .withYAxisTitle( "Percentage" ),
               new WeightRecordingGraphSeriesBuilder()
                  .withName( "Body Fat" )
                  .forProperty( WeighIn::bodyFat )
      );
      fatGraph.getStylesheets().addAll( SECONDARY_CHART_STYLE_SHEET.toExternalForm() );
      content.getChildren().add( fatGraph );
      
      setRight( new WeightRecordingGraphSettings( weightAndLeanGraph.controller(), fatGraph.controller() ) );
   }//End Class
}//End Class
