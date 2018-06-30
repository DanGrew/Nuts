/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import uk.dangrew.nuts.graphics.FriendlyTableView;
import uk.dangrew.nuts.system.Concept;

/**
 * {@link ConceptTable} provides a {@link TableView} for {@link Concept}s.
 */
public class ConceptTable< TypeT extends Concept > 
   extends TableView< ConceptTableRow< TypeT > > 
   implements FriendlyTableView< ConceptTableRow< TypeT > > 
{

   private final ConceptTableViewController< TypeT > controller;

   public ConceptTable( 
            ConceptTableColumnsPopulator< TypeT > columnsPopulator, 
            ConceptTableViewController< TypeT > controller
   ) {
      this.controller = controller;
      this.controller.associate( this );
      this.setEditable( true );
      columnsPopulator.populateColumns( this );
   }//End Constructor
   
   /**
    * Friendly access to the {@link #getItems()}.
    * @return the {@link ObservableList}.
    */
   public ObservableList< ConceptTableRow< TypeT > > getRows(){
      return getItems();
   }//End Method
   
}//End Class
