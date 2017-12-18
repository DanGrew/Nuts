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
 * {@link ConceptTableController} provides a controller interface for a {@link ConceptTable}.
 */
public interface ConceptTableController< TypeT extends Concept > {

   /**
    * Associated the {@link ConceptTableController} with the {@link ConceptTable}.
    * @param table the {@link ConceptTable} to control.
    */
   public void associate( ConceptTable< TypeT > table );
   
   /**
    * Instruction to create a new {@link Concept} of the associated type.
    * @return the created {@link Concept}.
    */
   public TypeT createConcept();

   /**
    * Instruction to remove the currently selected {@link Concept}.
    */
   public void removeSelectedConcept();

   /**
    * Method to copy the selected {@link Concept} in the {@link uk.dangrew.nuts.system.ConceptStore}.
    */
   public void copySelectedConcept();
   
}//End Interface

