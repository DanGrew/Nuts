/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.meal;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.store.Database;

/**
 * Provides a custom {@link ConceptTable} for {@link uk.dangrew.nuts.meal.Meal}s that can be selected
 * using the {@link MealTableController}.
 */
public class MealTable extends ConceptTable< FoodPortion > {

   /**
    * Constructs a new {@link MealTable}.
    * @param database the {@link Database}.
    */
   public MealTable( Database database ) {
      super( new MealTableColumns( database ), new MealTableControllerImpl( database ) );
   }//End Constructor
   
   public MealTable( Database database, MealTableColumns columns ) {
      super( columns, new MealTableControllerImpl( database ) );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public MealTableController controller() {
      return ( MealTableController )super.controller();
   }//End Method
   
}//End Class
