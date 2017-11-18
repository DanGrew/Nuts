/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.meal;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.FoodTableWithControls;
import uk.dangrew.nuts.store.Database;

/**
 * {@link MealTableWithControls} provides a {@link MealTable} with {@link FoodControls}.
 */
public class MealTableWithControls extends FoodTableWithControls< FoodPortion > {

   /**
    * Constructs a new {@link MealTableWithControls}.
    * @param title the title of the {@link TitledPane}.
    * @param database the {@link Database}.
    */
   public MealTableWithControls( String title, Database database ) {
      super( title, new MealTable( database ) );
   }//End Constructor
   
   public MealTableWithControls( String title, MealTable table ) {
      super( title, table );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public MealTable table() {
      return ( MealTable ) super.table();
   }//End Method

}//End Class
