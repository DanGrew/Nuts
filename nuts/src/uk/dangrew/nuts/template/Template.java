/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.template;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.goal.GoalAnalyticsCalculator;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.meal.MealPropertiesCalculator;
import uk.dangrew.nuts.meal.MealRegistrations;
import uk.dangrew.nuts.meal.StockUsage;
import uk.dangrew.nuts.meal.TargetedFoodHolder;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.system.Properties;

/**
 * The {@link Template} represents a collection of {@link FoodPortion}s and provides notifications
 * as they change, and is associated with a {@link uk.dangrew.nuts.goal.Goal}.
 */
public class Template extends Meal implements TargetedFoodHolder {

   private final GoalAnalytics goalAnalytics;
   
   /**
    * Constructs a new {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Template( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param id the id of the {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Template( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   public Template( Properties properties ) {
      this( 
               properties, 
               new Nutrition(),
               new FoodAnalytics(), 
               new GoalAnalytics(),
               new MealRegistrations(), 
               new MealPropertiesCalculator(),
               new MacroRatioCalculator(),
               new GoalAnalyticsCalculator(),
               new StockUsage()
      );
   }//End Constructor
   
   Template( 
            Properties properties,
            Nutrition nutrition,
            FoodAnalytics foodAnalytics,
            GoalAnalytics goalAnalytics,
            MealRegistrations registrations, 
            MealPropertiesCalculator propertiesCalculator,
            MacroRatioCalculator ratioCalculator,
            GoalAnalyticsCalculator goalAnalyticsCalculator,
            StockUsage stockUsage
   ) {
      super( properties, nutrition, foodAnalytics, registrations, propertiesCalculator, ratioCalculator, stockUsage );
      this.goalAnalytics = goalAnalytics;
      goalAnalyticsCalculator.associate( nutrition, goalAnalytics );
   }//End Constructor
   
   /**
    * Access to the {@link GoalAnalytics}.
    * @return the {@link GoalAnalytics}.
    */
   @Override public GoalAnalytics goalAnalytics() {
      return goalAnalytics;
   }//End Method
   
   @Override public Template duplicate( String referenceId ) {
      Template duplicate = new Template( properties().nameProperty().get() + referenceId );
      super.duplicateProperties( duplicate, referenceId );
      duplicate.goalAnalytics().goal().set( goalAnalytics().goal().get() );
      return duplicate;
   }//End Method 
   
}//End Class
