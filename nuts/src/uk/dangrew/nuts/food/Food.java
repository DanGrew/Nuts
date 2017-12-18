/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import uk.dangrew.nuts.system.Concept;

/**
 * {@link Food} provides a common interface for any type of food and access to its properties.
 */
public interface Food extends Concept {

   /**
    * Access to the {@link FoodProperties} associated.
    * @return the {@link FoodProperties}.
    */
   @Override public FoodProperties properties();
   
   /**
    * Access to the {@link FoodAnalytics} associated.
    * @return the {@link FoodAnalytics}.
    */
   public FoodAnalytics foodAnalytics();
   
   /**
    * Method to duplicate the {@link Food}.
    * @param referenceId the reference to append to the name.
    * @return the duplicatred {@link Food}.
    */
   @Override public Food duplicate( String referenceId );
   
}//End Interface
