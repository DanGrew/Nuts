/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.food;

import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.system.Concept;

public class UnresponsiveConceptTableController< TypeT extends Concept > implements ConceptTableController< TypeT > {

   @Override public void associate( ConceptTable< TypeT > table ) {}//End Method
   
   @Override public TypeT createConcept() {
      return null;
   }//End Method

   @Override public void removeSelectedConcept() {}//End Method
   
   @Override public void copySelectedConcept() {}//End Method
   
}//End Class
