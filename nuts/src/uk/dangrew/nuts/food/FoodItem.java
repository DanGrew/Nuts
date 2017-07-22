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
   private final FoodAnalytics analytics;
   
   /**
    * Constructs a new {@link FoodItem}.
    * @param name the name of the {@link FoodItem}.
    */
   public FoodItem( String name ) {
      this( new FoodProperties( name ), new FoodAnalytics(), new MacroRatioCalculator() );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodItem}.
    * @param id the id.
    * @param name the name.
    */
   public FoodItem( String id, String name ) {
      this( new FoodProperties( id, name ), new FoodAnalytics(), new MacroRatioCalculator() );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodItem}.
    * @param properties the {@link FoodProperties}.
    * @param analytics the {@link FoodAnalytics}.
    * @param ratioCalculator the {@link MacroRatioCalculator}.
    */
   FoodItem( FoodProperties properties, FoodAnalytics analytics, MacroRatioCalculator ratioCalculator ) {
      this.properties = properties;
      this.analytics = analytics;
      ratioCalculator.associate( properties, analytics );
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
   @Override public FoodAnalytics analytics() {
      return analytics;
   }//End Method
   
}//End Class
