/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal;

import javafx.beans.value.ChangeListener;

/**
 * {@link CalorieGoalCalculator} calculates the calorie goal from the other {@link Goal}
 * properties.
 */
class CalorieGoalCalculator {
   
   private Goal goal;

   /**
    * Associate with the given.
    * @param goal the {@link Goal}.
    */
   void associate( Goal goal ) {
      if ( this.goal != null ) {
         throw new IllegalStateException( "Aleady associated." );
      }
      this.goal = goal;
      
      ChangeListener< Double > listener = ( s, o, n ) -> calculate();
      this.goal.maintenanceCalories().addListener( listener );
      this.goal.exerciseCalories().addListener( listener );
      this.goal.calorieDeficit().addListener( listener );
   }//End Method
   
   /**
    * Perform the calculation, updating the {@link Goal} {@link uk.dangrew.nuts.food.FoodProperties#calories()}.
    */
   private void calculate(){
      double caloriesEarned = goal.maintenanceCalories().get() + goal.exerciseCalories().get();
      double caloriesToSpend = caloriesEarned - goal.calorieDeficit().get();
      goal.properties().calories().set( caloriesToSpend );
   }//End Method

}//End Class
