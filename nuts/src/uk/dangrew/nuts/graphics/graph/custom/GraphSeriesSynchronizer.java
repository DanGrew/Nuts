package uk.dangrew.nuts.graphics.graph.custom;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Tooltip;

public class GraphSeriesSynchronizer {

   static final double MAX_TOOLTIP_WIDTH = 400;
   private static final String NO_HEADER = "No Header";
   private static final String NO_NOTES = "No Notes";
   
   private final Map< LocalDateTime, Data< Number, Number > > dataPoints;
   private final Map< LocalDateTime, Tooltip > dataPointToolTips;
   
   private final Series< Number, Number > chartData;
   private final Series< Number, Number > seriesData;
   private final Comparator< Data< Number, Number > > sorter;
   
   private boolean showing;
   
   public GraphSeriesSynchronizer( String modelName ) {
      this.showing = true;
      this.dataPoints = new HashMap<>();
      this.dataPointToolTips = new HashMap<>();
      this.chartData = new Series<>();
      this.chartData.setName( modelName );
      this.seriesData = new Series<>();
      this.seriesData.setName( modelName );
      this.sorter = ( o1, o2 ) -> Double.compare( o1.getXValue().doubleValue(), o2.getXValue().doubleValue() );
   }//End Constructor
   
   public Series< Number, Number > chartSeries() {
      return chartData;
   }//End Method

   public Series< Number, Number > dataSeries() {
      return seriesData;
   }//End Method
   
   public void update( LocalDateTime timestamp, Double value, String header, String notes ) {
      if ( value == null && header == null && notes == null ) {
         internal_remove( timestamp );
         return;
      }
      Data< Number, Number > point = dataPoints.get( timestamp );
      if ( point == null ) {
         point = internal_add( timestamp );
      }
      point.setYValue( value );
      updateToolTip( timestamp, value, header, notes );
   }//End Method
   
   private void updateToolTip( LocalDateTime timestamp, Double value, String header, String notes ) {
      Tooltip tooltip = dataPointToolTips.get( timestamp );
      if ( tooltip == null ) {
         return;
      }
      tooltip.setText( new StringBuilder()
          .append( Optional.ofNullable( header ).orElse( NO_HEADER ) )
          .append( "\n" )
          .append( Optional.ofNullable( notes ).orElse( NO_NOTES ) )
          .toString() 
      );
   }//End Method
   
   public void hide() {
      showing = false;
      //bug in javafx - turn off animation temporarily
      internal_flipAnimation();
      chartData.getData().clear();
      internal_flipAnimation();
   }//End Method
   
   public void show() {
      if ( showing ) {
         return;
      }
      showing = true;
      chartData.getData().addAll( seriesData.getData() );
   }//End Method
   
   private Data< Number, Number > internal_add( LocalDateTime timestamp ) {
      Data< Number, Number > point = new Data<>( internal_convertTimestamp( timestamp ), 0.0 );
      
      Tooltip tooltip = new Tooltip();
      tooltip.setWrapText( true );
      tooltip.setMaxWidth( MAX_TOOLTIP_WIDTH );
      dataPointToolTips.put( timestamp, tooltip );
      new DataPointTooltipInstaller( point, tooltip );
      
      dataPoints.put( timestamp, point );
      seriesData.getData().add( point );
      if ( showing ) {
         chartData.getData().add( point );
      }
      internal_sortSeries();
      return point;
   }//End Method
   
   private void internal_remove( LocalDateTime timestamp ) {
      Data< Number, Number > point = dataPoints.remove( timestamp );
      if ( point != null ) {
         chartData.getData().remove( point );
         seriesData.getData().remove( point );
      }
   }//End Method
   
   private void internal_sortSeries(){
      Collections.sort( dataSeries().getData(), sorter );
      Collections.sort( chartSeries().getData(), sorter );
   }//End Method
   
   private double internal_convertTimestamp( LocalDateTime timestamp ) {
      return timestamp.toEpochSecond( ZoneOffset.UTC );
   }//End Method
   
   private void internal_flipAnimation() {
      Optional.ofNullable( chartData.getChart() ).ifPresent( c -> c.setAnimated( !c.getAnimated() ) );
   }//End Method

   Data< Number, Number > pointFor( LocalDateTime recordTimestamp ) {
      return dataPoints.get( recordTimestamp );
   }//End Method
   
   Tooltip tooltipFor( LocalDateTime recordTimestamp ) {
      return dataPointToolTips.get( recordTimestamp );
   }//End Method

}//End Method
