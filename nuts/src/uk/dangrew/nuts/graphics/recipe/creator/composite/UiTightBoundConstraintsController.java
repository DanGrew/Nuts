package uk.dangrew.nuts.graphics.recipe.creator.composite;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.kode.javafx.table.base.ConceptTableRowImpl;
import uk.dangrew.kode.javafx.table.controller.ConceptTableViewController;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.graphics.recipe.creator.UiRecipeConstraintController;
import uk.dangrew.nuts.recipe.constraint.tightbound.TightBoundConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.TightBoundConstraints;

public class UiTightBoundConstraintsController< SubjectT > implements ConceptTableViewController< TightBoundConstraint< SubjectT > >{

   private final ChangeListener< Double > recalculator;
   private final ListChangeListener< TightBoundConstraint< SubjectT > > constraintsListener;
   private UiRecipeConstraintController constraintController;
   private ConceptTable< TightBoundConstraint< SubjectT > > table;
   private TightBoundConstraints< SubjectT > selected;
   
   public UiTightBoundConstraintsController( UiRecipeConstraintController constraintController ) {
      this.constraintController = constraintController;
      this.recalculator = ( s, o, n ) -> constraintController.recalculate();
      this.constraintsListener = new FunctionListChangeListenerImpl<>( 
               this::internal_add, this::internal_remove 
      );
   }//End Constructor
   
   @Override public void associate( ConceptTable< TightBoundConstraint< SubjectT > > table ) {
      this.table = table;
   }//End Method
   
   public void select( TightBoundConstraints< SubjectT > contraints ) {
      if ( selected != null ) {
         this.selected.constraints().forEach( this::internal_remove );
         this.selected.constraints().removeListener( constraintsListener );
      }
      
      this.selected = contraints;
      this.table.getRows().clear();
      
      if ( selected != null ) {
         selected.constraints().forEach( this::internal_add );
         selected.constraints().addListener( constraintsListener );
         constraintController.recalculate();
      }
   }//End Method
   
   private void internal_add( TightBoundConstraint< SubjectT > constraint ) {
      table.getRows().add( new ConceptTableRowImpl<>( constraint ) );
      constraint.lowerBound().addListener( recalculator );
      constraint.upperBound().addListener( recalculator );
   }//End Method
   
   private void internal_remove( TightBoundConstraint< SubjectT > constraint ) {
      table.getRows().removeIf( r -> r.concept().equals( constraint ) );
      constraint.lowerBound().removeListener( recalculator );
      constraint.upperBound().removeListener( recalculator );
   }//End Method
   
   @Override public TightBoundConstraint< SubjectT > createConcept() {
      return null;
   }//End Method

   @Override public void removeSelectedConcept() { }

   @Override public void copySelectedConcept() { }

}//End Class
