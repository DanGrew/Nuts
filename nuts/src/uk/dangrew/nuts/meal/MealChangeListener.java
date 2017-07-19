/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.meal;

/**
 * The {@link MealChangeListener} provides the interface for notifying when a {@link Meal} changes.
 */
@FunctionalInterface
public interface MealChangeListener {
   
   /**
    * Method triggered when the {@link Meal} has changed.
    */
   public void mealChanged();

}//End Interface

