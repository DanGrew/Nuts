/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal.proportion;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;

public class ProportionGoalStore extends MappedObservableStoreManagerImpl< String, ProportionGoal > implements ConceptStore< ProportionGoal > {

   public ProportionGoalStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   @Override public ProportionGoal createConcept( String name ) {
      ProportionGoal food = new ProportionGoal( name );
      store( food );
      return food;
   }//End Method
   
   @Override public ProportionGoal createConcept( String id, String name ) {
      ProportionGoal ProportionGoal = new ProportionGoal( id, name );
      store( ProportionGoal );
      return ProportionGoal;
   }//End Method
   
   @Override public void store( ProportionGoal ProportionGoal ) {
      super.store( ProportionGoal );
   }//End Method
   
   @Override public void removeConcept( ProportionGoal ProportionGoal ) {
      remove( ProportionGoal.properties().id() );
   }//End Method

}//End Class
