/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.meal;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * The {@link MealPropertiesCalculator} is responsible for monitoring changes made to the {@link Meal}
 * and updating the overall {@link uk.dangrew.nuts.food.FoodProperties}.
 */
public class MealPropertiesCalculator implements MealChangeListener {
   
   private Meal meal;
   
   /**
    * Associate the given {@link Meal} with this calculator.
    * @param meal the {@link Meal} to associate with, only one allowed.
    */
   void associate( Meal meal ) {
      if ( this.meal != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.meal = meal;
      this.meal.registrations().listen( this );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void mealChanged() {
      double totalCarbs = 0;
      double totalFats = 0;
      double totalProtein = 0;
      
      for ( FoodPortion portion : meal.portions() ) {
         if ( portion.food().get() == null ) {
            continue;
         }
         totalCarbs += portion.nutritionFor( MacroNutrient.Carbohydrates ).get();
         totalFats += portion.nutritionFor( MacroNutrient.Fats ).get();
         totalProtein += portion.nutritionFor( MacroNutrient.Protein ).get();
      }

      meal.properties().setMacros( totalCarbs, totalFats, totalProtein );
   }//End Method

}//End Class
