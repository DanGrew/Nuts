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
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.MacroNutrient;
import uk.dangrew.nuts.nutrients.Nutrition;

public class GoalAnalyticsCalculator {

   private final Function< Goal, GoalCalculator > calculatorRetriever;
   private final ChangeListener< Double > updater;
   
   private Nutrition nutrition;
   private GoalAnalytics analytics;
   
   private Goal goal;
   
   public GoalAnalyticsCalculator() {
      this( g -> g.type().calculator() );
   }//End Constructor
   
   GoalAnalyticsCalculator( Function< Goal, GoalCalculator > calculatorRetriever ) {
      this.calculatorRetriever = calculatorRetriever;
      this.updater = ( s, o, n ) -> updateRatios();
   }//End Constructor
   
   public void associate( Nutrition nutrition, GoalAnalytics analytics ) {
      if ( this.nutrition != null || this.analytics != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.nutrition = nutrition;
      this.nutrition.listen( updater );
      this.analytics = analytics;
      this.analytics.goal().addListener( ( s, o, n ) -> setGoal( n ) );
   }//End Method
   
   /**
    * Method to apply the {@link Goal} to the calculator.
    * @param goal the {@link Goal}, can be null.
    */
   private void setGoal( Goal goal ) {
      if ( this.goal != null ) {
         Nutrition.of( this.goal ).stopListening( updater );
      }
      
      this.goal = goal;
      if ( this.goal == null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            analytics.nutrientRatioFor( macro ).set( 0.0 );
         }
         analytics.fiberRatioProperty().set( 0.0 );
         return;
      } else {
         Nutrition.of( this.goal ).listen( updater );
         updateRatios();
      }
   }//End Method
   
   private void updateRatios() {
      if ( goal == null ) {
         return;
      }
      calculatorRetriever.apply( goal ).calculate( nutrition, analytics, goal );
   }//End Method

}//End Class
