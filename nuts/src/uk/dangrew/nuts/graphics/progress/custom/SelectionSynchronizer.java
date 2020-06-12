package uk.dangrew.nuts.graphics.progress.custom;

import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.kode.javafx.table.base.ConceptTableRow;

public class SelectionSynchronizer< ConceptTypeT > {

   public SelectionSynchronizer(
            ConceptTable< ConceptTypeT > table1,
            ConceptTable< ConceptTypeT > table2 
   ) {
      if ( table1 == null || table2 == null ) {
         return;
      }
      
      table1.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> updateSelection( table1, table2 ) );
      table2.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> updateSelection( table2, table1 ) );
      
   }//End Method
   
   private void updateSelection( ConceptTable< ConceptTypeT > tableSelectedIn, ConceptTable< ConceptTypeT > tableToSelectIn ) {
      ConceptTableRow< ConceptTypeT > selection = tableSelectedIn.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         tableToSelectIn.getSelectionModel().clearSelection();
         return;
      }
      
      tableToSelectIn.getRows().stream().filter( r -> r.concept() == selection.concept() )
            .findFirst()
            .ifPresent( r -> tableToSelectIn.getSelectionModel().select( r ) );
   }//End Method
   

}//End Class
