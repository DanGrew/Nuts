/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal.calorie;

import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

/**
 * {@link CalorieGoalCalculator} calculates the calorie goal from the other {@link Goal}
 * properties.
 */
class CalorieGoalCalculator {
   
   private CalorieGoal calorieGoal;

   /**
    * Associate with the given.
    * @param goal the {@link Goal}.
    */
   void associate( CalorieGoal calorieGoal ) {
      if ( this.calorieGoal != null ) {
         throw new IllegalStateException( "Aleady associated." );
      }
      this.calorieGoal = calorieGoal;
      
      ChangeListener< Object > bmrUpdater = ( s, o, n ) -> calculateBmr();
      this.calorieGoal.age().addListener( bmrUpdater );
      this.calorieGoal.weight().addListener( bmrUpdater );
      this.calorieGoal.height().addListener( bmrUpdater );
      this.calorieGoal.gender().addListener( bmrUpdater );
      
      ChangeListener< Double > teeUpdater = ( s, o, n ) -> calculateTee();
      this.calorieGoal.bmr().addListener( teeUpdater );
      this.calorieGoal.pal().addListener( teeUpdater );
      
      ChangeListener< Double > macroUpdater = ( s, o, n ) -> calculateCalorieGoal();
      this.calorieGoal.tee().addListener( macroUpdater );
      this.calorieGoal.exerciseCalories().addListener( macroUpdater );
      this.calorieGoal.calorieDeficit().addListener( macroUpdater );
   }//End Method
   
   /**
    * Method to calculate the Bmr in response to value changes.
    */
   private void calculateBmr(){
      Gender gender = calorieGoal.gender().get();
      if ( gender == null ) {
         calorieGoal.bmr().set( 0.0 );
         return;
      }
      
      switch ( gender ) {
         case Male:
            calculateMaleBmr();
            return;
         case Female:
            calculateFemaleBmr();
            return;
      }
   }//End Method
   
   /**
    * Method to calculate the bmr for a {@link Gender#Male}.
    */
   private void calculateMaleBmr(){
      double age = calorieGoal.age().get();
      double bmr = 0;
      if ( age < 18 ) {
//         15.6 x Weight(kg) + 266 x Height(m) + 299
         bmr = calculateBmr( 15.6, 266, 299 );
      } else if ( age < 30 ) {
//         14.4 x Weight(kg)  + 313 x Height(m) + 113
         bmr = calculateBmr( 14.4, 313, 113 );
      } else if ( age < 60 ) {
//         11.4 x Weight(kg)  + 541 x Height(m) – 137
         bmr = calculateBmr( 11.4, 541, -137 );
      } else {
//         11.4 x Weight(kg)  + 541 x Height(m) – 256
         bmr = calculateBmr( 11.4, 541, -256 );
      }
      calorieGoal.bmr().set( bmr );
   }//End Method
   
   /**
    * Method to calculate the bmr for a {@link Gender#Female}.
    */
   private void calculateFemaleBmr(){
      double age = calorieGoal.age().get();
      double bmr = 0;
      if ( age < 18 ) {
//      9.40 x Weight(kg)  + 249 x Height(m) + 462
         bmr = calculateBmr( 9.4, 249, 462 );
      } else if ( age < 30 ) {
//      10.4 x Weight(kg)  + 615 x Height(m) – 282
         bmr = calculateBmr( 10.4, 615, -282 );
      } else if ( age < 60 ) {
//      8.18 x Weight(kg)  + 502 x Height(m) – 11.6
         bmr = calculateBmr( 8.18, 502, -11.6 );
      } else {
//      8.52 x Weight(kg)  + 421 x Height(m) + 10.7
         bmr = calculateBmr( 8.52, 421, 10.7 );
      }
      calorieGoal.bmr().set( bmr );
   }//End Method
   
   /**
    * Method to calculate the Bmr with the given parameters for the equation.
    * @param weightMultiplier the multiplier for the weight.
    * @param heightMultiplier the multiplier for the height.
    * @param offsetweightMultiplier the offset for the equation.
    */
   private double calculateBmr( double weightMultiplier, double heightMultiplier, double offset ) {
      double weightToKgAdjusted = calorieGoal.weight().get() * 0.45359237;
      double a = weightMultiplier * weightToKgAdjusted;
      double b = heightMultiplier * calorieGoal.height().get();
      return a + b + offset;
   }//End Method
   
   /**
    * Method to calculate the Tee in response to value changes.
    */
   private void calculateTee(){
      double tee = calorieGoal.bmr().get() * calorieGoal.pal().get();
      calorieGoal.tee().set( tee );
   }//End Method
   
   /**
    * Perform the calculation, updating the {@link Goal} {@link uk.dangrew.nuts.food.FoodProperties#calories()}.
    */
   private void calculateCalorieGoal(){
      double caloriesEarned = calorieGoal.tee().get() + calorieGoal.exerciseCalories().get();
      double caloriesToSpend = caloriesEarned - calorieGoal.calorieDeficit().get();
      NutritionalUnit.Calories.of( calorieGoal ).set( caloriesToSpend );
   }//End Method

}//End Class
