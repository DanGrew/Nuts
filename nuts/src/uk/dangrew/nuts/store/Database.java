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
import uk.dangrew.nuts.goal.GoalStore;
import uk.dangrew.nuts.meal.MealStore;
import uk.dangrew.nuts.progress.WeightProgress;
import uk.dangrew.nuts.template.TemplateStore;

/**
 * The {@link Database} provides access to the data for the system such as {@link Food}s.
 */
public class Database {

   private final WeightProgress weightProgress;
   private final GoalStore goals;
   
   private final FoodItemStore foodItems;
   private final MealStore meals;
   private final TemplateStore templates;
   private final MealStore shoppingLists;
   
   /**
    * Constructs a new {@link Database}.
    */
   public Database() {
      this.weightProgress = new WeightProgress();
      this.goals = new GoalStore();
      this.foodItems = new FoodItemStore();
      this.meals = new MealStore();
      this.templates = new TemplateStore();
      this.shoppingLists = new MealStore();
   }//End Constructor
   
   /**
    * Access to the {@link WeightProgress}.
    * @return the {@link WeightProgress}.
    */
   public WeightProgress weightProgress() {
      return weightProgress;
   }//End Method
   
   /**
    * Access to the {@link GoalStore}.
    * @return the {@link GoalStore}.
    */
   public GoalStore goals() {
      return goals;
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
    * Access to the {@link uk.dangrew.nuts.template.Template}s.
    * @return the {@link TemplateStore} of {@link uk.dangrew.nuts.template.Template}s. 
    */
   public TemplateStore templates() {
      return templates;
   }//End Method
   
   /**
    * Access to the {@link uk.dangrew.nuts.meal.Meal}s stored as shopping lists.
    * @return the {@link MealStore} of {@link uk.dangrew.nuts.meal.Meal}s against 
    * their {@link uk.dangrew.nuts.food.FoodProperties#id()}.
    */
   public MealStore shoppingLists() {
      return shoppingLists;
   }//End Method
   
}//End Class
