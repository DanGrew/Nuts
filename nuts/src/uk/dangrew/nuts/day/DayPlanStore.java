/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.day;

import java.time.LocalDate;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.food.FoodStore;
import uk.dangrew.nuts.progress.SystemDateRange;

public class DayPlanStore extends MappedObservableStoreManagerImpl< LocalDate, DayPlan > implements FoodStore< DayPlan > {

   public DayPlanStore() {
      super( DayPlan::date );
      SystemDateRange dateRange = new SystemDateRange();
      for ( LocalDate date : dateRange.get() ) {
         DayPlan plan = new DayPlan( date );
         store( plan );
      }
   }//End Constructor

   @Override public DayPlan createFood( String name ) {
      throw new UnsupportedOperationException( "Not creatable yet." );
   }//End Method
   
   @Override public DayPlan createFood( String id, String name ) {
      throw new UnsupportedOperationException( "Not creatable yet." );
   }//End Method
   
   @Override public DayPlan get( String id ) {
      throw new UnsupportedOperationException( "Not indexed by id." );
   }//End Method
   
   @Override public void store( DayPlan food ) {
      super.store( food );
   }//End Method
   
   @Override public void removeFood( DayPlan food ) {
      remove( food.date() );
   }//End Method

}//End Class
