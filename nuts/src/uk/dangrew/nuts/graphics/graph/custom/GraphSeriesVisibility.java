package uk.dangrew.nuts.graphics.graph.custom;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class GraphSeriesVisibility {

   private final ObservableList< ProgressSeries > internal_availableSeries;
   private final ObservableList< ProgressSeries > internal_visibleSeries;
   private final ObservableList< ProgressSeries > availableSeries;
   private final ObservableList< ProgressSeries > visibleSeries;
   
   private final Map< ProgressSeries, GraphModel > models;
   private final ObservableList< Series< Number, Number > > chartData;
   
   public GraphSeriesVisibility( ObservableList< Series< Number, Number > > chartData ) {
      this.chartData = chartData;
      this.models = new HashMap<>();
      this.internal_availableSeries = FXCollections.observableArrayList();
      this.internal_visibleSeries = FXCollections.observableArrayList();
      this.availableSeries = new PrivatelyModifiableObservableListImpl<>( internal_availableSeries );
      this.visibleSeries = new PrivatelyModifiableObservableListImpl<>( internal_visibleSeries );
   }//End Constructor

   public ObservableList< ProgressSeries > availableSeries(){
      return availableSeries;
   }//End Method
   
   public ObservableList< ProgressSeries > visibleSeries(){
      return visibleSeries;
   }//End Method
   
   public void add( ProgressSeries progress ) {
      if ( models.containsKey( progress ) ) {
         return;
      }
      
      internal_add( progress );
      show( progress );
   }//End Method
   
   private void internal_add( ProgressSeries progress ){
      GraphModel model = new GraphModelImpl( progress );
      models.put( progress, model );
      
      chartData.add( model.series() );
      internal_availableSeries.add( progress );
   }//End Method

   public void hide( ProgressSeries progress ) {
      models.get( progress ).hide();
      internal_visibleSeries.remove( progress );
   }//End Method

   public void show( ProgressSeries progress ) {
      if ( !models.containsKey( progress ) ) {
         add( progress );
      }
      
      if ( visibleSeries().contains( progress ) ) {
         return;
      }
      models.get( progress ).show();
      internal_visibleSeries.add( progress );
   }//End Method
   
   Series< Number, Number > dataFor( ProgressSeries progress ) {
      if ( !models.containsKey( progress ) ) {
         return null;
      }
      
      return models.get( progress ).series();
   }//End Method
}//End Class
