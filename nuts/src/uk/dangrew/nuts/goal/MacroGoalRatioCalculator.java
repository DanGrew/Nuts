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
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * {@link MacroGoalRatioCalculator} calculates the ratio of {@link MacroNutrient}s against the 
 * {@link MacroNutrient}s provided in a {@link Goal}.
 */
public class MacroGoalRatioCalculator {

   private final ChangeListener< Double > updater;
   
   private FoodProperties properties;
   private GoalAnalytics analytics;
   
   private Goal goal;
   
   /**
    * Constructs a new {@link MacroGoalRatioCalculator}.
    */
   public MacroGoalRatioCalculator() {
      this.updater = ( s, o, n ) -> updateRatios();
   }//End Constructor
   
   /**
    * Associate with the given.
    * @param properties the {@link FoodProperties}.
    * @param analytics the {@link FoodAnalytics}.
    * @param goal the {@link Goal}.
    */
   public void associate( FoodProperties properties, GoalAnalytics analytics ) {
      if ( this.properties != null || this.analytics != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.properties = properties;
      this.analytics = analytics;
      this.analytics.goal().addListener( ( s, o, n ) -> setGoal( n ) );
   }//End Method
   
   /**
    * Method to apply the {@link Goal} to the calculator.
    * @param goal the {@link Goal}, can be null.
    */
   private void setGoal( Goal goal ) {
      if ( this.goal != null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            this.goal.properties().nutritionFor( macro ).gramsProperty().removeListener( updater );
         }
      }
      
      this.goal = goal;
      if ( this.goal == null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            analytics.nutrientRatioFor( macro ).set( 0.0 );
         }
         return;
      } else {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            properties.nutritionFor( macro ).gramsProperty().addListener( updater );
            this.goal.properties().nutritionFor( macro ).gramsProperty().addListener( updater );
         }
         updateRatios();
      }
   }//End Method
   
   /**
    * Method to update the ratios of {@link Goal}s {@link MacroNutrient}s.
    */
   private void updateRatios(){
      if ( this.goal == null ) {
         return;
      }
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         double macroGoal = goal.properties().nutritionFor( macro ).inGrams();
         if ( macroGoal == 0 ) {
            analytics.nutrientRatioFor( macro ).set( 0.0 );   
            continue;
         }
         double macroUsage = properties.nutritionFor( macro ).inGrams();
         double proportion = macroUsage * 100 / macroGoal;
         analytics.nutrientRatioFor( macro ).set( proportion );
      }
   }//End Method

}//End Class
