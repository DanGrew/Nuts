/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.shopping;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.meal.MealTableController;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.store.Database;

/**
 * {@link ShoppingNeedsTable} provides a {@link FoodTable} for selecting plans for the {@link uk.dangrew.nuts.shopping.ShoppingList}.
 */
public class ShoppingNeedsTable extends FoodTable< FoodPortion > {

   /**
    * Constructs a new {@link ShoppingNeedsTable}.
    * @param database the {@link Database}.
    */
   public ShoppingNeedsTable( Database database ) {
      super( new ShoppingNeedsTableColumns( database ), new MealTableController() );
      controller().showMeal( database.shoppingList() );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public MealTableController controller() {
      return ( MealTableController )super.controller();
   }//End Method
   
}//End Class
