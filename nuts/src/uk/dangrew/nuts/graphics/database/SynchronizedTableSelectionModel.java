package uk.dangrew.nuts.graphics.database;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.system.Concept;

public class SynchronizedTableSelectionModel< TypeT extends Concept > {

   private final ConceptTable< TypeT > table1;
   private final ConceptTable< TypeT > table2;
   private final ObjectProperty< TypeT > selected;
   
   public SynchronizedTableSelectionModel( 
            ConceptTable< TypeT > table1, 
            ConceptTable< TypeT > table2 
   ) {
      this.table1 = table1;
      this.table1.getSelectionModel().selectedItemProperty().addListener( 
               ( s, o, n ) -> table1SelectionMade( n ) 
      );
      this.table2 = table2;
      this.table2.getSelectionModel().selectedItemProperty().addListener( 
               ( s, o, n ) -> table2SelectionMade( n ) 
      );
      this.selected = new SimpleObjectProperty<>();
   }//End Constructor
   
   private void table1SelectionMade( ConceptTableRow< TypeT > selectedInTable ) {
      table2.getSelectionModel().clearSelection();
      table1.requestFocus();
      updateSelection( selectedInTable );
   }//End Method
   
   private void table2SelectionMade( ConceptTableRow< TypeT > selectedInTable ) {
      table1.getSelectionModel().clearSelection();
      table2.requestFocus();
      updateSelection( selectedInTable );
   }//End Method
   
   private void updateSelection( ConceptTableRow< TypeT > selectedInTable ) {
      if ( selectedInTable == null ) {
         selected.set( null );
      } else {
         selected.set( selectedInTable.concept() );
      }
   }//End Method

   public ObjectProperty< TypeT > selected() {
      return selected;
   }//End Method

}//End Class
