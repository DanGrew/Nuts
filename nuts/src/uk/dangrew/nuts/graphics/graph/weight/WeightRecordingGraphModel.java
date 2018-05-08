/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph.weight;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.nuts.graphics.graph.custom.GraphModel;
import uk.dangrew.nuts.progress.weight.WeightProgress;
import uk.dangrew.nuts.progress.weight.WeightRecording;

/**
 * {@link WeightRecordingGraphModel} provides a model managing the data points within a series, adding points
 * with values and removing those without.
 */
public class WeightRecordingGraphModel implements GraphModel {

   private final String modelName;
   private final WeightProgress progress;
   private final Function< WeightRecording, Double > dateRetriever;
   private final Function< WeightRecording, ObjectProperty< Double > > propertyRetriever;
   private final Series< Number, Number > series;
   
   private final Map< ObjectProperty< Double >, Data< Number, Number > > dataPoints;
   private final ChangeListener< Double > propertyChangeListener;
   
   /**
    * Constructs a new {@link WeightRecordingGraphModel}.
    * @param progress the {@link WeightProgress}.
    * @param dateRetriever the {@link Function} for extracting the {@link java.time.LocalDate} representation.
    * @param propertyRetriever the {@link Function} for extracting the {@link ObjectProperty} to plot.
    * @param series the {@link javafx.scene.chart.XYChart.Series} to manage.
    */
   public WeightRecordingGraphModel( 
            String modelName,
            WeightProgress progress,
            Function< WeightRecording, Double > dateRetriever,
            Function< WeightRecording, ObjectProperty< Double > > propertyRetriever,
            Series< Number, Number > series 
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
   
   @Override public String modelName(){
      return modelName;
   }//End Method
   
   @Override public Series< Number, Number > series() {
      return series;
   }//End Method
   
   private ObservableList< Data< Number, Number > > seriesData(){
      return series().getData();
   }//End Method
   
   @Override public void show() {
      throw new UnsupportedOperationException( "Not integrated with Graph yet." );
   }//End Method
   
   @Override public void hide() {
      throw new UnsupportedOperationException( "Not integrated with Graph yet." );
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
         seriesData().remove( dataPoint );
      } else if ( !seriesData().contains( dataPoint ) ){
         seriesData().add( dataPoint );
         sortSeries();
      }
   }//End Method
   
   private void sortSeries(){
      Collections.sort( seriesData(), ( o1, o2 ) -> Double.compare( o1.getXValue().doubleValue(), o2.getXValue().doubleValue() ) );
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

}//End Class
