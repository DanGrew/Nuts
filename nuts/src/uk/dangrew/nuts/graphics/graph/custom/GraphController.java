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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.kode.datetime.TimestampFormat;
import uk.dangrew.kode.javafx.registrations.ChangeListenerMismatchBindingImpl;
import uk.dangrew.kode.javafx.registrations.RegistrationManager;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class GraphController {
   
   private final TimestampFormat format;
   private final GraphSeriesVisibility seriesVisibility;
   private final NumberAxis xAxis;
   private final NumberAxis yAxis;
   
   private final ObjectProperty< LocalDateTime > xAxisLowerBound;
   private final ObjectProperty< LocalDateTime > xAxisUpperBound;

   public GraphController( 
            ObservableList< Series< Number, Number > > chartData,
            NumberAxis xAxis, 
            NumberAxis yAxis
   ) {
      this( new TimestampFormat(), chartData, xAxis, yAxis );
   }//End Constructor
   
   GraphController(
            TimestampFormat formats,
            ObservableList< Series< Number, Number > > chartData,
            NumberAxis xAxis, 
            NumberAxis yAxis
   ) {
      this.format = formats;
      this.seriesVisibility = new GraphSeriesVisibility( chartData );
      
      this.xAxis = xAxis;
      this.xAxis.setAutoRanging( false );
      this.yAxis = yAxis;
      
      this.xAxisLowerBound = new SimpleObjectProperty<>();
      this.xAxisUpperBound = new SimpleObjectProperty<>();
      
      RegistrationManager registrations = new RegistrationManager();
      registrations.apply( new ChangeListenerMismatchBindingImpl<>( 
               xAxis.lowerBoundProperty(), 
               xAxisLowerBound, 
               formats::toEpochSeconds,
               formats::fromEpochSeconds
      ) );
      registrations.apply( new ChangeListenerMismatchBindingImpl<>( 
               xAxis.upperBoundProperty(), 
               xAxisUpperBound, 
               formats::toEpochSeconds,
               formats::fromEpochSeconds
      ) );
      
      this.yAxis.setAutoRanging( false );
   }//End Constructor
   
   public GraphSeriesVisibility seriesVisibility(){
      return seriesVisibility;
   }//End Method
   
   public ObjectProperty< LocalDateTime > xAxisUpperBoundProperty(){
      return xAxisUpperBound;
   }//End Method
   
   public ObjectProperty< LocalDateTime > xAxisLowerBoundProperty(){
      return xAxisLowerBound;
   }//End Method
   
   public DoubleProperty yAxisLowerBoundProperty(){
      return yAxis.lowerBoundProperty();
   }//End Method
   
   public DoubleProperty yAxisUpperBoundProperty(){
      return yAxis.upperBoundProperty();
   }//End Method

   public void focusHorizontalAxisOn( LocalDateTime focus, TimestampPeriod period ) {
      if ( focus == null || period == null ) {
         return;
      }
      xAxisLowerBoundProperty().set( period.lowerBound( focus ) );
      xAxisUpperBoundProperty().set( period.upperBound( focus ) );
   }//End Method

   public void autoScaleHorizontal() {
      LocalDateTime min = null;
      LocalDateTime max = null;
      for ( ProgressSeries series : seriesVisibility.visibleSeries() ) {
         if ( series.values().entries().isEmpty() ) {
            continue;
         }
         
         LocalDateTime first = series.values().first();
         LocalDateTime last = series.values().last();
         if ( min == null || min.isAfter( first ) ) {
            min = first;
         }
         if ( max == null || max.isBefore( last ) ) {
            max = last;
         }
      }
      
      if ( min == null || max == null ) {
         return;
      }
      
      xAxisLowerBoundProperty().set( min );
      xAxisUpperBoundProperty().set( max );
   }//End Method
   
   public void autoScaleVertical() {
      Double min = null;
      Double max = null;
      for ( ProgressSeries series : seriesVisibility.visibleSeries() ) {
         if ( series.entries().isEmpty() ) {
            continue;
         }
         
         for ( LocalDateTime key : series.entries() ) {
            Double value = series.values().entryFor( key );
            if ( value == null ) {
               continue;
            }
            if ( min == null || min > value ) {
               min = value;
            }
            if ( max == null || max < value ) {
               max = value;
            }
         }
      }
      
      if ( min == null || max == null ) {
         return;
      }
      
      yAxisLowerBoundProperty().set( min );
      yAxisUpperBoundProperty().set( max );
   }//End Method

}//End Class
