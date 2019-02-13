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
import uk.dangrew.nuts.graphics.graph.custom.Graph;
import uk.dangrew.nuts.graphics.graph.custom.GraphBuilder;
import uk.dangrew.nuts.graphics.graph.custom.GraphDateStringConverter;
import uk.dangrew.nuts.graphics.graph.custom.GraphSettings;

public class DoubleVerticalGraph extends BorderPane {
   
   static final URL SECONDARY_CHART_STYLE_SHEET = DoubleVerticalGraph.class.getResource( "secondary-chart.css" );
   static final URL PRIMARY_CHART_STYLE_SHEET = DoubleVerticalGraph.class.getResource( "primary-chart.css" );
   
   private final Graph graph1;
   private final Graph graph2;
   private final GraphSettings graphSettings1;
   private final GraphSettings graphSettings2;
   
   public DoubleVerticalGraph(
            String chartTitle,
            String xAxisTitle,
            String innerYAxisTitle,
            String outerYAxisTitle
            
   ) {
      StackPane content = new StackPane();
      this.setCenter( content );
      
      this.graph1 = new Graph( 
               new GraphBuilder()
                  .withChartTitle( chartTitle )
                  .withChartXTranslation( 30 )
                  .withXAxisTitle( xAxisTitle )
                  .withYAxisTitle( innerYAxisTitle )
                  .withYAxisTranslation( -60 )
                  .withXAxisTickFormatter( new GraphDateStringConverter() )
                  .withXAxisTickInterval( 86400 )
      );
      this.graphSettings1 = new GraphSettings( graph1.controller(), innerYAxisTitle );
      this.graph1.getStylesheets().addAll( PRIMARY_CHART_STYLE_SHEET.toExternalForm() );
      content.getChildren().add( graph1 );
      
      this.graph2 = new Graph( 
               new GraphBuilder()
                  .withChartTitle( "." )
                  .withChartXTranslation( 30 )
                  .withXAxisTitle( "." )
                  .withXAxisVisible( false )
                  .withXAxisTickInterval( 1 )
                  .withYAxisTitle( outerYAxisTitle )
      );
      this.graphSettings2 = new GraphSettings( graph2.controller(), outerYAxisTitle );
      this.graph2.getStylesheets().addAll( SECONDARY_CHART_STYLE_SHEET.toExternalForm() );
      content.getChildren().add( graph2 );
      
      BorderPane settingsPanel = new BorderPane();
      settingsPanel.setTop( graphSettings1 );
      settingsPanel.setBottom( graphSettings2 );
      this.setRight( settingsPanel );
   }//End Class
   
   public Graph graph1(){
      return graph1;
   }//End Method
   
   public Graph graph2(){
      return graph2;
   }//End Method
   
   public GraphSettings graphSettings1(){
      return graphSettings1;
   }//End Method
   
   public GraphSettings graphSettings2(){
      return graphSettings2;
   }//End Method
}//End Class
