/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.store;

import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.meal.MealStore;

/**
 * The {@link Database} provides access to the data for the system such as {@link Food}s.
 */
public class Database {

   private final Goal goal;
   private final FoodItemStore foodItems;
   private final MealStore meals;
   private final MealStore plans;
   
   /**
    * Constructs a new {@link Database}.
    */
   public Database() {
      this.goal = new Goal( "Goal" );
      this.foodItems = new FoodItemStore( goal );
      this.meals = new MealStore( goal );
      this.plans = new MealStore( goal );
   }//End Constructor
   
   /**
    * Access to the {@link Goal}.
    * @return the {@link Goal}.
    */
   public Goal goal() {
      return goal;
   }//End Method
   
   /**
    * Access to the {@link FoodItem}s stored.
    * @return the {@link ObjectStoreManager} of {@link uk.dangrew.nuts.food.FoodItem}s against 
    * their {@link uk.dangrew.nuts.food.FoodProperties#id()}.
    */
   public FoodItemStore foodItems() {
      return foodItems;
   }//End Method
   
   /**
    * Access to the {@link uk.dangrew.nuts.meal.Meal}s stored.
    * @return the {@link ObjectStoreManager} of {@link uk.dangrew.nuts.meal.Meal}s against 
    * their {@link uk.dangrew.nuts.food.FoodProperties#id()}.
    */
   public MealStore meals() {
      return meals;
   }//End Method
   
   /**
    * Access to the {@link uk.dangrew.nuts.meal.Meal}s stored as plans.
    * @return the {@link MealStore} of {@link uk.dangrew.nuts.meal.Meal}s against 
    * their {@link uk.dangrew.nuts.food.FoodProperties#id()}.
    */
   public MealStore plans() {
      return plans;
   }//End Method
   
}//End Class
