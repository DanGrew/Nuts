package uk.dangrew.nuts.graphics.progress.custom;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.BiConsumer;

import uk.dangrew.nuts.graphics.common.UiDateTimeInputDialog;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class ProgressSeriesDataController implements ConceptTableController< ProgressSeries > {

   private final UiDateTimeInputDialog timestampInput;
   private final BiConsumer< LocalDateTime, Double > valueUpdater;
   private final BiConsumer< LocalDateTime, String > textUpdater;
   
   private ProgressSeriesDataTable table;
   private ProgressEntryTextPane textPane;
   private ProgressSeries selected;
   
   public ProgressSeriesDataController() {
      this( new UiDateTimeInputDialog() );
   }//End Constructor
   
   ProgressSeriesDataController( UiDateTimeInputDialog timestampInput ) {
      this.timestampInput = timestampInput;
      this.valueUpdater = ( t, v ) -> this.internal_update( t );
      this.textUpdater = ( t, v ) -> this.internal_update( t );
   }//End Constructor
   
   public void associate( ProgressSeriesDataTable table ) {
      this.table = table;
      this.table.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> internal_updateTextPane( n ) );
   }//End Method
   
   public void associate( ProgressEntryTextPane textPane ) {
      this.textPane = textPane;
   }//End Method
   
   @Override public void associate( ConceptTable< ProgressSeries > table ) {
      table.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> select( n.concept() ) );
   }//End Method
   
   private void select( ProgressSeries series ) {
      if ( selected != null ) {
         selected.values().progressChangedListener().removeWhenProgressAdded( valueUpdater );
         selected.values().progressChangedListener().removeWhenProgressRemoved( valueUpdater );
         selected.values().progressChangedListener().removeWhenProgressUpdated( valueUpdater );
         
         selected.headers().progressChangedListener().removeWhenProgressAdded( textUpdater );
         selected.headers().progressChangedListener().removeWhenProgressRemoved( textUpdater );
         selected.headers().progressChangedListener().removeWhenProgressUpdated( textUpdater );
         
         selected.notes().progressChangedListener().removeWhenProgressAdded( textUpdater );
         selected.notes().progressChangedListener().removeWhenProgressRemoved( textUpdater );
         selected.notes().progressChangedListener().removeWhenProgressUpdated( textUpdater );
      }
      
      this.table.getRows().clear();
      this.selected = series;
      this.selected.entries().forEach( this::internal_add );
      
      this.selected.values().progressChangedListener().whenProgressAdded( valueUpdater );
      this.selected.values().progressChangedListener().whenProgressRemoved( valueUpdater );
      this.selected.values().progressChangedListener().whenProgressUpdated( valueUpdater );
      
      this.selected.headers().progressChangedListener().whenProgressAdded( textUpdater );
      this.selected.headers().progressChangedListener().whenProgressRemoved( textUpdater );
      this.selected.headers().progressChangedListener().whenProgressUpdated( textUpdater );
      
      this.selected.notes().progressChangedListener().whenProgressAdded( textUpdater );
      this.selected.notes().progressChangedListener().whenProgressRemoved( textUpdater );
      this.selected.notes().progressChangedListener().whenProgressUpdated( textUpdater );
   }//End Method
   
   private void internal_add( LocalDateTime timestamp ) {
      this.table.getRows().add( new ProgressSeriesDataRow( selected, timestamp ) );
      this.internal_sortRows();
   }//End Method
   
   private void internal_remove( ProgressSeriesDataRow row ) {
      if ( selected.entries().contains( row.timestamp() ) ) {
         return;
      }
      this.table.getRows().remove( row );
   }//End Method
   
   private void internal_updateUi( ProgressSeriesDataRow row, Double value ) {
      row.valueProperty().set( value );
      internal_updateTextPane( row );
      this.internal_sortRows();
   }//End Method
   
   private void internal_sortRows(){
      this.table.getRows().sort( ( a, b ) -> a.timestamp().compareTo( b.timestamp() ) );
   }//End Method
   
   private void internal_update( LocalDateTime timestamp ) {
      Optional< ProgressSeriesDataRow > row = table.getRows().stream()
         .filter( r -> r.timestamp().equals( timestamp ) )
         .findFirst();
      if ( !row.isPresent() ) {
         internal_add( timestamp );
      } else if ( !selected.entries().contains( timestamp ) ) {
         internal_remove( row.get() );
      } else {
         internal_updateUi( row.get(), selected.values().entryFor( timestamp ) );
      }
   }//End Method
   
   private void internal_updateTextPane( ProgressSeriesDataRow selectedRow ){
      if ( textPane == null ) {
         return;
      }
      if ( selectedRow == null ) {
         textPane.selectionRemoved();
      } else {
         textPane.update( 
                  selected.headers().entryFor( selectedRow.timestamp() ), 
                  selected.notes().entryFor( selectedRow.timestamp() ) 
         );
      }
   }//End Method

   @Override public ProgressSeries createConcept() {
      if ( selected == null ) {
         return null;
      }
      
      Optional< LocalDateTime > timestamp = timestampInput.friendly_showAndWait();
      if ( !timestamp.isPresent() ) {
         return null;
      }
      
      selected.values().record( timestamp.get(), 0.0 );
      return selected;
   }//End Method

   @Override public void removeSelectedConcept() {
      ProgressSeriesDataRow selectedRow = internal_getSelectedRow();
      if ( selectedRow == null ) {
         return;
      }
      
      selected.values().record( selectedRow.timestamp(), null );
   }//End Method

   @Override public void copySelectedConcept() {
      ProgressSeriesDataRow selectedRow = internal_getSelectedRow();
      if ( selectedRow == null ) {
         return;
      }
      
      LocalDateTime selectedTimestamp = selectedRow.timestamp();
      int secondsOffset = 1;
      while ( selected.values().entryFor( selectedTimestamp.plusSeconds( secondsOffset ) ) != null ) {
         secondsOffset++;
      }
      selected.values().record( 
               selectedTimestamp.plusSeconds( secondsOffset ), 
               selected.values().entryFor( selectedRow.timestamp() ) 
      );
   }//End Method
   
   private ProgressSeriesDataRow internal_getSelectedRow(){
      if ( selected == null ) {
         return null;
      }
      
      return table.getSelectionModel().getSelectedItem();
   }//End Method

   public void setHeaderForSelected( String header ) {
      ProgressSeriesDataRow selectedRow = internal_getSelectedRow();
      if ( selectedRow == null ) {
         return;
      }
      selected.headers().record( selectedRow.timestamp(), header );
   }//End Method
   
   public void setNotesForSelected( String notes ) {
      ProgressSeriesDataRow selectedRow = internal_getSelectedRow();
      if ( selectedRow == null ) {
         return;
      }
      selected.notes().record( selectedRow.timestamp(), notes );
   }//End Method

}//End Class
