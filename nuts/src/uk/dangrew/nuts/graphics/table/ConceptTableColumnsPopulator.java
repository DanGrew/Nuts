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
 * {@link ConceptTableColumnsPopulator} provides an interface for populating a {@link ConceptTable}.
 */
public interface ConceptTableColumnsPopulator< TypeT extends Concept > {
   
   /**
    * Instructing to populate the {@link ConceptTable} with the relevant {@link javafx.scene.control.TableColumn}s.
    * @param table the {@link ConceptTable} to populate.
    */
   public void populateColumns( ConceptTable< TypeT > table );

}//End Interface

