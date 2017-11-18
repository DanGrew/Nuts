/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.meal;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.store.Database;

/**
 * Provides a custom {@link FoodTable} for {@link uk.dangrew.nuts.meal.Meal}s that can be selected
 * using the {@link MealTableController}.
 */
public class MealTable extends FoodTable< FoodPortion > {

   /**
    * Constructs a new {@link MealTable}.
    * @param database the {@link Database}.
    */
   public MealTable( Database database ) {
      super( new MealTableColumns( database ), new MealTableController() );
   }//End Constructor
   
   public MealTable( MealTableColumns columns ) {
      super( columns, new MealTableController() );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public MealTableController controller() {
      return ( MealTableController )super.controller();
   }//End Method
   
}//End Class
