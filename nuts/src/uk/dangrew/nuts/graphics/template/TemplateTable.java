/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.template;

import uk.dangrew.nuts.graphics.food.GeneralConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

/**
 * Provides a custom {@link ConceptTable} for {@link Template}s.
 */
public class TemplateTable extends ConceptTable< Template > {

   public TemplateTable( Database database  ) {
      this( database, new GeneralConceptTableController<>( database.templates() ) );
   }//End Constructor
   
   public TemplateTable( Database database, ConceptTableController< Template > controller ) {
      super( new TemplateTableColumns<>( database.goals() ), controller );
   }//End Constructor
   
}//End Class
