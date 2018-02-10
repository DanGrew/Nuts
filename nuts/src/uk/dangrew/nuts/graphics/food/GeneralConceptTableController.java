/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.food;

import java.util.HashSet;
import java.util.Set;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.ConceptStore;

/**
 * The {@link GeneralConceptTableController} is responsible for monitoring the {@link uk.dangrew.nuts.store.Database} and
 * updating the {@link ConceptTable}, as well as performing operations on the {@link ConceptTable}.
 */
public class GeneralConceptTableController< TypeT extends Concept > implements ConceptTableController< TypeT > {

   private final ConceptStore< TypeT > concepts;
   
   private ConceptTable< TypeT > table;
   
   public GeneralConceptTableController(
            ConceptStore< TypeT > foods
   ) {
      this.concepts = foods;
      
      this.concepts.objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::addRow, this::removeRow
      ) );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void associate( ConceptTable< TypeT > table ) {
      this.table = table;
      
      this.concepts.objectList().forEach( this::addRow );
   }//End Method
   
   protected ConceptTable< TypeT > table() {
      return table;
   }//End Method
   
   private void addRow( TypeT concept ) {
      table.getItems().add( new ConceptTableRow<>( concept ) );
   }//End Method
   
   private void removeRow( TypeT food ) {
      Set< ConceptTableRow< TypeT > > rowsToRemove = new HashSet<>();
      for ( ConceptTableRow< TypeT > row : table.getItems() ) {
         if ( row.concept().properties().id().equals( food.properties().id() ) ) {
            rowsToRemove.add( row );
         }
      }
      
      table.getItems().removeAll( rowsToRemove );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public TypeT createConcept() {
      return concepts.createConcept( "Unnamed" );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void removeSelectedConcept() {
      ConceptTableRow< TypeT > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      concepts.removeConcept( selection.concept() );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void copySelectedConcept() {
      ConceptTableRow< TypeT > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      TypeT copy = ( TypeT )selection.concept().duplicate( "-copy" );
      concepts.store( copy );
   }//End Method
   
}//End Class
