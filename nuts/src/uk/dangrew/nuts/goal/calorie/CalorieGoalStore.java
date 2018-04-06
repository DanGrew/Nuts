/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal.calorie;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.system.ConceptStore;

/**
 * {@link GoalStore} provides an {@link uk.dangrew.kode.storage.structure.ObjectStoreManager}
 * for {@link Goal}s.
 */
public class CalorieGoalStore extends MappedObservableStoreManagerImpl< String, CalorieGoal > implements ConceptStore< CalorieGoal > {

   /**
    * Constructs a new {@link GoalStore}.
    */
   public CalorieGoalStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public CalorieGoal createConcept( String name ) {
      CalorieGoal food = new CalorieGoalImpl( name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public CalorieGoal createConcept( String id, String name ) {
      CalorieGoal calorieGoal = new CalorieGoalImpl( id, name );
      store( calorieGoal );
      return calorieGoal;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void store( CalorieGoal calorieGoal ) {
      super.store( calorieGoal );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeConcept( CalorieGoal calorieGoal ) {
      remove( calorieGoal.properties().id() );
   }//End Method

}//End Class
