package uk.dangrew.nuts.graphics.table;

import uk.dangrew.kode.concept.Concept;

public class ReadOnlyTableColumns< TypeT extends Concept > implements ConceptTableColumnsPopulator< TypeT >{

   private ConceptTableColumnsPopulator< TypeT > original;
   
   public ReadOnlyTableColumns( ConceptTableColumnsPopulator< TypeT > original ) {
      this.original = original;
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< TypeT > table ) {
      original.populateColumns( table );
      table.getColumns().forEach( c -> c.setEditable( false ) );
   }//End Method

}//End Class
