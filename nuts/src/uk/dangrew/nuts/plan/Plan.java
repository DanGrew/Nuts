/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.plan;

import uk.dangrew.nuts.meal.Meal;

/**
 * {@link Plan} is a more specific {@link Meal}.
 */
public class Plan extends Meal {

   /**
    * Constructs a new {@link Plan}.
    * @param name the name of the {@link Plan}.
    */
   public Plan( String name ) {
      super( name );
   }//End Method

}//End Class
