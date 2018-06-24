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
 * {@link MacroGoalCalculator} calculates the {@link uk.dangrew.nuts.nutrients.MacroNutrient} goals
 * from the {@link Goal} properties.
 */
class MacroCalorieGoalCalculator {

   static final double CALORIES_PER_CARBOHYDRATE_GRAM = 4;
   static final double CALORIES_PER_FAT_GRAM = 9;
   static final double CALORIES_PER_PROTEIN_GRAM = 4;
   
   private CalorieGoal calorieGoal;
   
   /**
    * Associated with the given.
    * @param goal the {@link Goal}.
    */
   void associate( CalorieGoal calorieGoal ) {
      if ( this.calorieGoal != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.calorieGoal = calorieGoal;
      
      ChangeListener< Double > listener = ( s, o, n ) -> calculate();
      NutritionalUnit.Calories.of( calorieGoal ).property().addListener( listener );
      calorieGoal.weight().addListener( listener );
      calorieGoal.proteinPerPound().addListener( listener );
      calorieGoal.fatPerPound().addListener( listener );
   }//End Method
   
   /**
    * Perform the calculation.
    */
   private void calculate(){
      double proteinGoal = calorieGoal.weight().get() * calorieGoal.proteinPerPound().get();
      NutritionalUnit.Protein.of( calorieGoal ).set( proteinGoal );
      
      double fatGoal = calorieGoal.weight().get() * calorieGoal.fatPerPound().get();
      NutritionalUnit.Fat.of( calorieGoal ).set( fatGoal );
      
      double proteinCalories = proteinGoal * CALORIES_PER_PROTEIN_GRAM;
      double fatCalories = fatGoal * CALORIES_PER_FAT_GRAM;
      
      double remainingCalories = NutritionalUnit.Calories.of( calorieGoal ).get() - proteinCalories - fatCalories;
      double carbohydrateGoal = remainingCalories / CALORIES_PER_CARBOHYDRATE_GRAM;
      NutritionalUnit.Carbohydrate.of( calorieGoal ).set( carbohydrateGoal );
   }//End Method

}//End Class
