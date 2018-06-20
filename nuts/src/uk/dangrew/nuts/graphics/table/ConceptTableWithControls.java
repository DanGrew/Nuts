/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.system.Concept;

/**
 * {@link ConceptTableWithControls} provides a {@link ConceptTable} with {@link ConceptControls}.
 */
public class ConceptTableWithControls< TypeT extends Concept > extends TableWithControls< ConceptTableRow< TypeT >, TypeT > {

   ConceptTableWithControls( String title, ConceptTable< TypeT > table, ConceptControls controls ) {
      this( new JavaFxStyle(), title, table, controls );
   }//End Constructor
   
   ConceptTableWithControls( 
            JavaFxStyle styling, 
            String title, 
            ConceptTable< TypeT > table,
            ConceptControls controls
   ) {
      super( styling, title, table, controls );
   }//End Constructor

   @Override public ConceptTable< TypeT > table() {
      return ( ConceptTable< TypeT > )super.table();
   }//End Method

}//End Class
