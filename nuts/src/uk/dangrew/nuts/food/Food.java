/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

/**
 * {@link Food} provides a common interface for any type of food and access to its properties.
 */
public interface Food {

   /**
    * Access to the {@link FoodProperties} associated.
    * @return the {@link FoodProperties}.
    */
   public FoodProperties properties();
   
   /**
    * Access to the {@link FoodAnalytics} associated.
    * @return the {@link FoodAnalytics}.
    */
   public FoodAnalytics foodAnalytics();
   
   /**
    * Access to the {@link GoalAnalytics} associated.
    * @return the {@link GoalAnalytics}.
    */
   public GoalAnalytics goalAnalytics();
}//End Interface
