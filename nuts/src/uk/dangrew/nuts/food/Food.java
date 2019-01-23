/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import uk.dangrew.kode.concept.Concept;
import uk.dangrew.nuts.nutrients.Nutrition;

/**
 * {@link Food} provides a common interface for any type of food and access to its properties.
 */
public interface Food extends Concept {

   public Nutrition nutrition();
   
   /**
    * Access to the {@link FoodAnalytics} associated.
    * @return the {@link FoodAnalytics}.
    */
   public FoodAnalytics foodAnalytics();
   
   /**
    * Method to duplicate the {@link Food}.
    * @return the duplicatred {@link Food}.
    */
   @Override public Food duplicate();
   
}//End Interface
