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

import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class GraphController {
   
   private final DateTimeFormats formats;
   private final GraphSeriesVisibility seriesVisibility;
   private final NumberAxis xAxis;
   private final NumberAxis yAxis;

   public GraphController( 
            ObservableList< Series< Number, Number > > chartData,
            NumberAxis xAxis, 
            NumberAxis yAxis
   ) {
      this( new DateTimeFormats(), chartData, xAxis, yAxis );
   }//End Constructor
   
   GraphController(
            DateTimeFormats formats,
            ObservableList< Series< Number, Number > > chartData,
            NumberAxis xAxis, 
            NumberAxis yAxis
   ) {
      this.formats = formats;
      this.seriesVisibility = new GraphSeriesVisibility( chartData );
      
      this.xAxis = xAxis;
      this.yAxis = yAxis;
      
      this.xAxis.setAutoRanging( false );
      this.yAxis.setAutoRanging( false );
   }//End Constructor
   
   public GraphSeriesVisibility seriesVisibility(){
      return seriesVisibility;
   }//End Method

   /**
    * Set the lower bound of the recording axis.
    * @param value the bound.
    */
   public void setRecordingLowerBound( double value ) {
      yAxis.setLowerBound( value );
   }//End Method

   /**
    * Set the upper bound of the recording axis.
    * @param value the bound.
    */
   public void setRecordingUpperBound( double value ) {
      yAxis.setUpperBound( value );
   }//End Method

   /**
    * Set the lower bound of the date axis.
    * @param value the bound.
    */
   public void setDateLowerBound( LocalDateTime value ) {
      xAxis.setLowerBound( formats.toEpochSeconds( value ) );
   }//End Method

   /**
    * Set the upper bound of the date axis.
    * @param value the bound.
    */
   public void setDateUpperBound( LocalDateTime value ) {
      xAxis.setUpperBound( formats.toEpochSeconds( value ) );
   }//End Method
   
   public void focusHorizontalAxisOn( LocalDateTime focus, TimestampPeriod period ) {
      if ( focus == null || period == null ) {
         return;
      }
      setDateLowerBound( period.lowerBound( focus ) );
      setDateUpperBound( period.upperBound( focus ) );
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
      
      //add some padding
      max = max.plusDays( 1 );
      
      setDateLowerBound( min );
      setDateUpperBound( max );
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
      
      setRecordingLowerBound( min );
      setRecordingUpperBound( max );
   }//End Method

}//End Class
