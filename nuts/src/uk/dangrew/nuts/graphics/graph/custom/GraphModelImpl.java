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
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class GraphModelImpl implements GraphModel {

   private final ProgressSeries progressSeries;
   private final Series< Number, Number > series;
   
   private final Map< LocalDateTime, Data< Number, Number > > dataPoints;
   
   public GraphModelImpl( ProgressSeries progressSeries ) {
      this.progressSeries = progressSeries;
      this.series = new Series<>();
      this.series.setName( modelName() );
      
      this.dataPoints = new HashMap<>();
      this.progressSeries.entries().forEach( this::updateDataPoint );
      this.progressSeries.progressChangedListener().whenProgressAdded( this::internalRedirect_updateDataPoint );
      this.progressSeries.progressChangedListener().whenProgressRemoved( this::internalRedirect_updateDataPoint );
      this.progressSeries.progressChangedListener().whenProgressUpdated( this::internalRedirect_updateDataPoint );
   }//End Constructor
   
   @Override public String modelName(){
      return progressSeries.properties().nameProperty().get();
   }//End Method
   
   @Override public Series< Number, Number > series() {
      return series;
   }//End Method
   
   private ObservableList< Data< Number, Number > > seriesData(){
      return series().getData();
   }//End Method
   
   private void internalRedirect_updateDataPoint( LocalDateTime timestamp, Double value ) {
      updateDataPoint( timestamp );
   }//End Method
   
   private void updateDataPoint( LocalDateTime timestamp ) {
      if ( !dataPoints.containsKey( timestamp ) ) {
         dataPoints.put( 
               timestamp, 
               new Data<>( timestamp.toEpochSecond( ZoneOffset.UTC ), progressSeries.entryFor( timestamp ) ) 
         );
      }
      
      Data< Number, Number > dataPoint = dataPoints.get( timestamp );
      dataPoint.setYValue( progressSeries.entryFor( timestamp ) );
      
      if ( dataPoint.getYValue() == null ) {
         dataPoints.remove( timestamp );
         seriesData().remove( dataPoint );
      } else if ( !seriesData().contains( dataPoint ) ){
         seriesData().add( dataPoint );
         sortSeries();
      }
   }//End Method
   
   private void sortSeries(){
      Collections.sort( seriesData(), ( o1, o2 ) -> Double.compare( o1.getXValue().doubleValue(), o2.getXValue().doubleValue() ) );
   }//End Method
   
   Data< Number, Number > dataFor( LocalDateTime recordTimestamp ) {
      return dataPoints.get( recordTimestamp );
   }//End Method

}//End Class
