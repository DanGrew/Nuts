/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table.tree;

import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.javafx.table.controller.ConceptTableController;

/**
 * {@link ConceptTreeTableController} provides a controller interface for a {@link DayPlanTreeTable}.
 */
public interface ConceptTreeTableController< TypeT extends Concept > extends ConceptTableController< TypeT >{

   public void associate( DayPlanTreeTable table );
   
}//End Interface

