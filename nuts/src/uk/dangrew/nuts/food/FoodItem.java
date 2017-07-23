/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import uk.dangrew.nuts.goal.MacroGoalRatioCalculator;

/**
 * {@link FoodItem} represents a single item of food and the properties of that food. The {@link Food}
 * has a size, but is not portioned and simply measured in some amount.
 */
public class FoodItem implements Food {

   private final FoodProperties properties;
   private final FoodAnalytics foodAnalytics;
   private final GoalAnalytics goalAnalytics;
   
   /**
    * Constructs a new {@link FoodItem}.
    * @param name the name of the {@link FoodItem}.
    */
   public FoodItem( String name ) {
      this( new FoodProperties( name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodItem}.
    * @param id the id.
    * @param name the name.
    */
   public FoodItem( String id, String name ) {
      this( new FoodProperties( id, name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodItem} with the given {@link FoodProperties}.
    * @param properties the {@link FoodProperties}.
    */
   private FoodItem( FoodProperties properties ) {
      this( 
               properties,
               new FoodAnalytics(),
               new GoalAnalytics(),
               new MacroRatioCalculator(),
               new MacroGoalRatioCalculator()
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodItem}.
    * @param properties the {@link FoodProperties}.
    * @param foodAnalytics the {@link FoodAnalytics}.
    * @param goalAnalytics the {@link GoalAnalytics}.
    * @param ratioCalculator the {@link MacroRatioCalculator}.
    * @param goalRatioCalculator the {@link MacroGoalRatioCalculator}.
    */
   FoodItem( 
            FoodProperties properties, 
            FoodAnalytics foodAnalytics, 
            GoalAnalytics goalAnalytics,
            MacroRatioCalculator ratioCalculator,
            MacroGoalRatioCalculator goalRatioCalculator
   ) {
      this.properties = properties;
      this.foodAnalytics = foodAnalytics;
      this.goalAnalytics = goalAnalytics;
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
    * {@inheritDoc}
    */
   @Override public String toString() {
      return properties.id() + ": " + properties.nameProperty().get();
   }//End Method
   
}//End Class
