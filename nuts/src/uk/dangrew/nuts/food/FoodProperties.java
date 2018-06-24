/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import java.util.UUID;

import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.system.Properties;

/**
 * {@link FoodProperties} the properties of a single food. The {@link FoodProperties}
 * has a size, but is not portioned and simply measured in some amount.
 */
public class FoodProperties extends Properties {

   private final Nutrition nutrition;
   
   /**
    * Constructs a new {@link FoodProperties}.
    * @param name the name of the {@link FoodProperties}.
    */
   public FoodProperties( String name ) {
      this( UUID.randomUUID().toString(), name );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodProperties}.
    * @param id the unique id for the {@link FoodProperties}.
    * @param name the name of the {@link FoodProperties}.
    * @param analytics the {@link FoodAnalytics} supporting the properties.
    */
   public FoodProperties( String id, String name ) {
      super( id, name );
      this.nutrition = new Nutrition();
   }//End Constructor
   
   public Nutrition nutrition() {
      return nutrition;
   }//End Method
   
}//End Class
