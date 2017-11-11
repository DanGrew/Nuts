/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import uk.dangrew.nuts.progress.WeightProgress;
import uk.dangrew.nuts.progress.WeightRecording;

/**
 * {@link WeightRecordingGraphModel} provides a model managing the data points within a series, adding points
 * with values and removing those without.
 */
public class WeightRecordingGraphModel {

   private final String modelName;
   private final WeightProgress progress;
   private final Function< WeightRecording, Double > dateRetriever;
   private final Function< WeightRecording, ObjectProperty< Double > > propertyRetriever;
   private final ObservableList< Data< Number, Number > > series;
   
   private final Map< ObjectProperty< Double >, Data< Number, Number > > dataPoints;
   private final ChangeListener< Double > propertyChangeListener;
   
   /**
    * Constructs a new {@link WeightRecordingGraphModel}.
    * @param progress the {@link WeightProgress}.
    * @param dateRetriever the {@link Function} for extracting the {@link java.time.LocalDate} representation.
    * @param propertyRetriever the {@link Function} for extracting the {@link ObjectProperty} to plot.
    * @param series the {@link ObservableList} of points to manage.
    */
   public WeightRecordingGraphModel( 
            String modelName,
            WeightProgress progress,
            Function< WeightRecording, Double > dateRetriever,
            Function< WeightRecording, ObjectProperty< Double > > propertyRetriever,
            ObservableList< Data< Number, Number > > series 
   ) {
      this.modelName = modelName;
      this.progress = progress;
      this.dateRetriever = dateRetriever;
      this.propertyRetriever = propertyRetriever;
      this.series = series;
      
      this.dataPoints = new HashMap<>();
      this.propertyChangeListener = ( s, o, n ) -> this.updateDataPoint( s );
      this.progress.records().forEach( this::constructDataPoint );
   }//End Constructor
   
   public String modelName(){
      return modelName;
   }//End Method
   
   /**
    * Method to construct a data point for the given {@link WeightRecording}, automatically added to the series if present.
    * @param recording the {@link WeightRecording} to construct for.
    */
   private void constructDataPoint( WeightRecording recording ){
      ObjectProperty< Double > property = propertyRetriever.apply( recording );
      dataPoints.put( 
            property, 
            new Data<>( dateRetriever.apply( recording ), property.get() ) 
      );
      property.addListener( propertyChangeListener );
      updateDataPoint( property );
   }//End Method

   /**
    * Method to update the data point in the series. This will remove it if no value is provided, or add it
    * if a value is set. Adding a new point will resort the points.
    * @param property the {@link ObservableValue} to update the point for.
    */
   private void updateDataPoint( ObservableValue< ? extends Double > property ) {
      Double value = property.getValue();
      Data< Number, Number > dataPoint = dataPoints.get( property );
      dataPoint.setYValue( value );
      
      if ( value == null || value == 0.0 ) {
         series.remove( dataPoint );
      } else if ( !series.contains( dataPoint ) ){
         series.add( dataPoint );
         sortSeries();
      }
   }//End Method
   
   private void sortSeries(){
      Collections.sort( series, ( o1, o2 ) -> Double.compare( o1.getXValue().doubleValue(), o2.getXValue().doubleValue() ) );
   }//End Method
   
   /**
    * Provides the {@link javafx.scene.chart.XYChart.Data} point for the {@link WeightRecording} according to the
    * property associated.
    * @param record the {@link WeightRecording}.
    * @return the {@link javafx.scene.chart.XYChart.Data} point.
    */
   Data< Number, Number > dataFor( WeightRecording record ) {
      return dataPoints.get( propertyRetriever.apply( record ) );
   }//End Method

   public void hide() {
      series.clear();
   }//End Method

   public void show() {
      series.addAll( dataPoints.values() );
      sortSeries();
   }//End Method

}//End Class
