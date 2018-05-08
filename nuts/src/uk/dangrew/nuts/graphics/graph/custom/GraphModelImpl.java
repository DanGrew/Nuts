/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph.custom;

import java.time.LocalDateTime;

import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class GraphModelImpl implements GraphModel {

   private final ProgressSeries progressSeries;
   private final GraphSeriesSynchronizer seriesModel;
   
   public GraphModelImpl( ProgressSeries progressSeries ) {
      this.progressSeries = progressSeries;
      this.seriesModel = new GraphSeriesSynchronizer( modelName() );
      
      this.progressSeries.entries().forEach( this::updateDataPoint );
      this.progressSeries.progressChangedListener().whenProgressAdded( this::internalRedirect_updateDataPoint );
      this.progressSeries.progressChangedListener().whenProgressRemoved( this::internalRedirect_updateDataPoint );
      this.progressSeries.progressChangedListener().whenProgressUpdated( this::internalRedirect_updateDataPoint );
   }//End Constructor
   
   @Override public String modelName(){
      return progressSeries.properties().nameProperty().get();
   }//End Method
   
   @Override public Series< Number, Number > series() {
      return seriesModel.chartSeries();
   }//End Method
   
   @Override public void show() {
      seriesModel.show();
   }//End Method
   
   @Override public void hide() {
      seriesModel.hide();
   }//End Method
   
   private void internalRedirect_updateDataPoint( LocalDateTime timestamp, Double value ) {
      updateDataPoint( timestamp );
   }//End Method
   
   private void updateDataPoint( LocalDateTime timestamp ) {
      seriesModel.update( timestamp, progressSeries.entryFor( timestamp ) );
   }//End Method
   
   Data< Number, Number > dataFor( LocalDateTime recordTimestamp ) {
      return seriesModel.pointFor( recordTimestamp );
   }//End Method

}//End Class
