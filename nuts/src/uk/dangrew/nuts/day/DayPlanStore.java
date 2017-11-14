/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.day;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.food.FoodStore;

public class DayPlanStore extends MappedObservableStoreManagerImpl< String, DayPlan > implements FoodStore< DayPlan > {

   public DayPlanStore() {
      super( f -> f.properties().id() );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public DayPlan createFood( String name ) {
      DayPlan food = new DayPlan( name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public DayPlan createFood( String id, String name ) {
      DayPlan food = new DayPlan( id, name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void store( DayPlan object ) {
      super.store( object );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeFood( DayPlan food ) {
      remove( food.properties().id() );
   }//End Method
}//End Class
