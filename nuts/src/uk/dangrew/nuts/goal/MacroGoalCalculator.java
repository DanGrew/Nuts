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
 * {@link MacroGoalCalculator} calculates the {@link uk.dangrew.nuts.nutrients.MacroNutrient} goals
 * from the {@link Goal} properties.
 */
class MacroGoalCalculator {

   static final double CALORIES_PER_CARBOHYDRATE_GRAM = 4;
   static final double CALORIES_PER_FAT_GRAM = 9;
   static final double CALORIES_PER_PROTEIN_GRAM = 4;
   
   private Goal goal;
   
   /**
    * Associated with the given.
    * @param goal the {@link Goal}.
    */
   void associate( Goal goal ) {
      if ( this.goal != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.goal = goal;
      
      ChangeListener< Double > listener = ( s, o, n ) -> calculate();
      goal.properties().calories().addListener( listener );
      goal.weight().addListener( listener );
      goal.proteinPerPound().addListener( listener );
      goal.fatPerPound().addListener( listener );
   }//End Method
   
   /**
    * Perform the calculation.
    */
   private void calculate(){
      double proteinGoal = goal.weight().get() * goal.proteinPerPound().get();
      goal.properties().protein().set( proteinGoal );
      
      double fatGoal = goal.weight().get() * goal.fatPerPound().get();
      goal.properties().fats().set( fatGoal );
      
      double proteinCalories = proteinGoal * CALORIES_PER_PROTEIN_GRAM;
      double fatCalories = fatGoal * CALORIES_PER_FAT_GRAM;
      
      double remainingCalories = goal.properties().calories().get() - proteinCalories - fatCalories;
      double carbohydrateGoal = remainingCalories / CALORIES_PER_CARBOHYDRATE_GRAM;
      goal.properties().carbohydrates().set( carbohydrateGoal );
   }//End Method

}//End Class
