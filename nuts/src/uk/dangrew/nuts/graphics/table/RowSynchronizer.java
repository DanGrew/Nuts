package uk.dangrew.nuts.graphics.table;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.system.Concept;

public class RowSynchronizer< TypeT extends Concept > {

   private final ObservableList< TypeT > items;
   private final ConceptTable< TypeT > table;
   private final FunctionListChangeListenerImpl< TypeT > synchronizer;
   
   public RowSynchronizer( ConceptTable< TypeT > table, ObservableList< TypeT > items ) {
      this.table = table;
      this.items = items;
      
      this.table.getRows().clear();
      this.items.addListener( synchronizer = new FunctionListChangeListenerImpl<>( 
               this::addRow, this::removeRow 
      ) );
      this.items.forEach( this::addRow );
   }//End Constructor
   
   private void addRow( TypeT concept ) {
      table.getItems().add( new ConceptTableRow<>( concept ) );
   }//End Method
   
   private void removeRow( TypeT food ) {
      Set< ConceptTableRow< TypeT > > rowsToRemove = new HashSet<>();
      for ( ConceptTableRow< TypeT > row : table.getItems() ) {
         if ( row.concept().properties().id().equals( food.properties().id() ) ) {
            rowsToRemove.add( row );
         }
      }
      
      table.getItems().removeAll( rowsToRemove );
   }//End Method

   public void detach() {
      this.items.removeListener( synchronizer );
   }//End Method

}//End Class
