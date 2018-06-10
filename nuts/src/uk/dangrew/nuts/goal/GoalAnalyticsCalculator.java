/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal;

import java.util.function.Function;

import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.MacroNutrient;
import uk.dangrew.nuts.nutrients.Nutrition;

public class GoalAnalyticsCalculator {

   private final Function< Goal, GoalCalculator > calculatorRetriever;
   private final ChangeListener< Double > updater;
   
   private FoodProperties properties;
   private GoalAnalytics analytics;
   
   private Goal goal;
   
   public GoalAnalyticsCalculator() {
      this( g -> g.type().calculator() );
   }//End Constructor
   
   GoalAnalyticsCalculator( Function< Goal, GoalCalculator > calculatorRetriever ) {
      this.calculatorRetriever = calculatorRetriever;
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
      Nutrition.of( properties ).listen( updater );
      this.analytics = analytics;
      this.analytics.goal().addListener( ( s, o, n ) -> setGoal( n ) );
   }//End Method
   
   /**
    * Method to apply the {@link Goal} to the calculator.
    * @param goal the {@link Goal}, can be null.
    */
   private void setGoal( Goal goal ) {
      if ( this.goal != null ) {
         Nutrition.of( this.goal.properties() ).stopListening( updater );
      }
      
      this.goal = goal;
      if ( this.goal == null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            analytics.nutrientRatioFor( macro ).set( 0.0 );
         }
         analytics.fiberRatioProperty().set( 0.0 );
         return;
      } else {
         Nutrition.of( this.goal.properties() ).listen( updater );
         updateRatios();
      }
   }//End Method
   
   private void updateRatios() {
      if ( goal == null ) {
         return;
      }
      calculatorRetriever.apply( goal ).calculate( properties, analytics, goal );
   }//End Method

}//End Class
