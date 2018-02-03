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
import uk.dangrew.nuts.graphics.meal.MealTableControllerImpl;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

/**
 * {@link ShoppingNeedsTable} provides a {@link ConceptStore} for selecting plans for the {@link uk.dangrew.nuts.shopping.ShoppingList}.
 */
public class ShoppingNeedsTable extends ConceptTable< FoodPortion > {

   /**
    * Constructs a new {@link ShoppingNeedsTable}.
    * @param database the {@link Database}.
    * @param shoppingList the shopping list to show.
    */
   public ShoppingNeedsTable( Database database, Meal shoppingList ) {
      super( new ShoppingNeedsTableColumns( database ), new MealTableControllerImpl() );
      controller().showMeal( shoppingList );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public MealTableController controller() {
      return ( MealTableController )super.controller();
   }//End Method
   
}//End Class
