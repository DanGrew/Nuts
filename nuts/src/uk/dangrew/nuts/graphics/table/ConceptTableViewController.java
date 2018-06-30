/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import uk.dangrew.nuts.system.Concept;

/**
 * {@link ConceptTableViewController} provides a controller interface for a {@link ConceptTable}.
 */
public interface ConceptTableViewController< TypeT extends Concept > extends ConceptTableController< TypeT >{

   /**
    * Associated the {@link ConceptTableController} with the {@link ConceptTable}.
    * @param table the {@link ConceptTable} to control.
    */
   public void associate( ConceptTable< TypeT > table );
   
}//End Interface

