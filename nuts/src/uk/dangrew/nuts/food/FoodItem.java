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
 * {@link FoodItem} represents a single item of food and the properties of that food. The {@link Food}
 * has a size, but is not portioned and simply measured in some amount.
 */
public class FoodItem implements Food {

   private final FoodProperties properties;
   
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
    * Constructs a new {@link FoodItem}.
    * @param properties the {@link FoodProperties}.
    */
   FoodItem( FoodProperties properties ) {
      this.properties = properties;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public FoodProperties properties() {
      return properties;
   }//End Method
   
}//End Class
