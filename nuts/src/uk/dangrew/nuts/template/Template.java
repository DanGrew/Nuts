/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.template;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.goal.GoalAnalyticsCalculator;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.meal.MealPropertiesCalculator;
import uk.dangrew.nuts.meal.MealRegistrations;
import uk.dangrew.nuts.meal.StockUsage;

/**
 * The {@link Template} represents a collection of {@link FoodPortion}s and provides notifications
 * as they change, and is associated with a {@link uk.dangrew.nuts.goal.Goal}.
 */
public class Template extends Meal implements Food {

   private final GoalAnalytics goalAnalytics;
   
   /**
    * Constructs a new {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Template( String name ) {
      this( new FoodProperties( name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param id the id of the {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Template( String id, String name ) {
      this( new FoodProperties( id, name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param properties the {@link FoodProperties}.
    */
   protected Template( FoodProperties properties ) {
      this( 
               properties, 
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
            FoodProperties properties,
            FoodAnalytics foodAnalytics,
            GoalAnalytics goalAnalytics,
            MealRegistrations registrations, 
            MealPropertiesCalculator propertiesCalculator,
            MacroRatioCalculator ratioCalculator,
            GoalAnalyticsCalculator goalAnalyticsCalculator,
            StockUsage stockUsage
   ) {
      super( properties, foodAnalytics, registrations, propertiesCalculator, ratioCalculator, stockUsage );
      this.goalAnalytics = goalAnalytics;
      goalAnalyticsCalculator.associate( properties, goalAnalytics );
   }//End Constructor
   
   /**
    * Access to the {@link GoalAnalytics}.
    * @return the {@link GoalAnalytics}.
    */
   public GoalAnalytics goalAnalytics() {
      return goalAnalytics;
   }//End Method
   
   @Override public Template duplicate( String referenceId ) {
      Template duplicate = new Template( properties().nameProperty().get() + referenceId );
      super.duplicateProperties( duplicate, referenceId );
      duplicate.goalAnalytics().goal().set( goalAnalytics().goal().get() );
      return duplicate;
   }//End Method 
   
}//End Class
