package uk.dangrew.nuts.graphics.progress.custom;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.BiConsumer;

import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.graphics.common.UiDateTimeInputDialog;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class ProgressSeriesDataController implements ConceptTableController< ProgressSeries > {

   private final UiDateTimeInputDialog timestampInput;
   private final BiConsumer< LocalDateTime, Double > updater;
   private final BiConsumer< LocalDateTime, Double >  remover;
   
   private ProgressSeriesDataTable table;
   private ProgressSeries selected;
   
   public ProgressSeriesDataController() {
      this( new UiDateTimeInputDialog() );
   }//End Constructor
   
   ProgressSeriesDataController( UiDateTimeInputDialog timestampInput ) {
      this.timestampInput = timestampInput;
      this.updater = this::internal_update;
      this.remover = ( t, v ) -> this.internal_update( t, null );
   }//End Constructor
   
   public void associate( ProgressSeriesDataTable table ) {
      this.table = table;
   }//End Method
   
   @Override public void associate( ConceptTable< ProgressSeries > table ) {
      table.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> select( n.concept() ) );
   }//End Method
   
   private void select( ProgressSeries series ) {
      if ( selected != null ) {
         selected.progressChangedListener().removeWhenProgressAdded( updater );
         selected.progressChangedListener().removeWhenProgressRemoved( remover );
         selected.progressChangedListener().removeWhenProgressUpdated( updater );
      }
      
      this.table.getRows().clear();
      this.selected = series;
      this.selected.entries().forEach( this::internal_add );
      this.selected.progressChangedListener().whenProgressAdded( updater );
      this.selected.progressChangedListener().whenProgressRemoved( remover );
      this.selected.progressChangedListener().whenProgressUpdated( updater );
   }//End Method
   
   private void internal_add( LocalDateTime timestamp ) {
      this.table.getRows().add( new ProgressSeriesDataRow( selected, timestamp ) );
      this.internal_sortRows();
   }//End Method
   
   private void internal_remove( ProgressSeriesDataRow row ) {
      this.table.getRows().remove( row );
   }//End Method
   
   private void internal_update( ProgressSeriesDataRow row, Double value ) {
      row.valueProperty().set( value );
      this.internal_sortRows();
   }//End Method
   
   private void internal_sortRows(){
      this.table.getRows().sort( ( a, b ) -> a.timestamp().compareTo( b.timestamp() ) );
   }//End Method
   
   private void internal_update( LocalDateTime timestamp, Double value ) {
      Optional< ProgressSeriesDataRow > row = table.getRows().stream()
         .filter( r -> r.timestamp().equals( timestamp ) )
         .findFirst();
      if ( !row.isPresent() ) {
         internal_add( timestamp );
      } else if ( value == null ) {
         internal_remove( row.get() );
      } else {
         internal_update( row.get(), value );
      }
   }//End Method

   @Override public ProgressSeries createConcept() {
      if ( selected == null ) {
         return null;
      }
      
      timestampInput.resetInputToNow();
      Optional< LocalDateTime > timestamp = timestampInput.friendly_showAndWait();
      if ( !timestamp.isPresent() ) {
         return null;
      }
      
      selected.record( timestamp.get(), 0.0 );
      return selected;
   }//End Method

   @Override public void removeSelectedConcept() {
      if ( selected == null ) {
         return;
      }
      
      ProgressSeriesDataRow selectedRow = table.getSelectionModel().getSelectedItem();
      if ( selectedRow == null ) {
         return;
      }
      
      selected.record( selectedRow.timestamp(), null );
   }//End Method

   @Override public void copySelectedConcept() {
      if ( selected == null ) {
         return;
      }
      
      ProgressSeriesDataRow selectedRow = table.getSelectionModel().getSelectedItem();
      if ( selectedRow == null ) {
         return;
      }
      
      LocalDateTime selectedTimestamp = selectedRow.timestamp();
      int secondsOffset = 1;
      while ( selected.entryFor( selectedTimestamp.plusSeconds( secondsOffset ) ) != null ) {
         secondsOffset++;
      }
      selected.record( 
               selectedTimestamp.plusSeconds( secondsOffset ), 
               selected.entryFor( selectedRow.timestamp() ) 
      );
   }//End Method

}//End Class
