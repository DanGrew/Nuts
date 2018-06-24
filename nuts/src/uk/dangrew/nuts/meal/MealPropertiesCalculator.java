/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.meal;

import java.util.EnumMap;
import java.util.Map;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

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
   public void associate( Meal meal ) {
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
      Map< NutritionalUnit, Double > totals = new EnumMap<>( NutritionalUnit.class );
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         totals.put( unit, 0.0 );
      }
      
      for ( FoodPortion portion : meal.portions() ) {
         if ( portion.food().get() == null ) {
            continue;
         }
         
         for ( NutritionalUnit unit : NutritionalUnit.values() ) {
            double total = totals.get( unit );
            total += unit.of( portion ).get();
            totals.put( unit, total );
         }
      }

      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         unit.of( meal ).set( totals.get( unit ) );
      }
   }//End Method

}//End Class
