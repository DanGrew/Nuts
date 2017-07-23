/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.meal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.goal.MacroGoalRatioCalculator;

/**
 * The {@link Meal} represents a collection of {@link FoodPortion}s and provides notifications
 * as they change.
 */
public class Meal implements Food {

   private final FoodProperties properties;
   private final FoodAnalytics foodAnalytics;
   private final GoalAnalytics goalAnalytics;
   private final MealPropertiesCalculator propertiesCalculator;
   
   private final MealRegistrations registrations;
   private final ObservableList< FoodPortion > portions;
   
   /**
    * Constructs a new {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Meal( String name ) {
      this( new FoodProperties( name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param id the id of the {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Meal( String id, String name ) {
      this( new FoodProperties( id, name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param properties the {@link FoodProperties}.
    */
   private Meal( FoodProperties properties ) {
      this( 
               properties, 
               new FoodAnalytics(), 
               new GoalAnalytics(),
               new MealRegistrations(), 
               new MealPropertiesCalculator(),
               new MacroRatioCalculator(),
               new MacroGoalRatioCalculator()
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param properties the {@link FoodProperties}.
    * @param foodAnalytics the {@link FoodAnalytics}.
    * @param goalAnalytics the {@link GoalAnalytics}.
    * @param registrations the {@link MealRegistrations}.
    * @param propertiesCalculator the {@link MealPropertiesCalculator}.
    * @param ratioCalculator the {@link MacroRatioCalculator}.
    * @param goalRatioCalculator the {@link MacroGoalRatioCalculator}.
    */
   Meal( 
            FoodProperties properties,
            FoodAnalytics foodAnalytics,
            GoalAnalytics goalAnalytics,
            MealRegistrations registrations, 
            MealPropertiesCalculator propertiesCalculator,
            MacroRatioCalculator ratioCalculator,
            MacroGoalRatioCalculator goalRatioCalculator
   ) {
      this.registrations = registrations;
      this.portions = FXCollections.observableArrayList();
      this.properties = properties;
      this.foodAnalytics = foodAnalytics;
      this.goalAnalytics = goalAnalytics;
      this.propertiesCalculator = propertiesCalculator;
      this.propertiesCalculator.associate( this );
      this.registrations.associate( this );
      ratioCalculator.associate( properties, foodAnalytics );
      goalRatioCalculator.associate( properties, goalAnalytics );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodProperties properties() {
      return properties;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodAnalytics foodAnalytics() {
      return foodAnalytics;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public GoalAnalytics goalAnalytics() {
      return goalAnalytics;
   }//End Method
   
   /**
    * Access to the {@link FoodPortion}s in the {@link Meal}.
    * @return the {@link FoodPortion}s.
    */
   public ObservableList< FoodPortion > portions() {
      return portions;
   }//End Method

   /**
    * Access to the {@link MealRegistrations}.
    * @return the {@link MealRegistrations}.
    */
   public MealRegistrations registrations() {
      return registrations;
   }//End Method

}//End Class
