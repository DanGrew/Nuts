/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.system.Properties;

/**
 * {@link FoodItem} represents a single item of food and the properties of that food. The {@link Food}
 * has a size, but is not portioned and simply measured in some amount.
 */
public class FoodItem implements Food {

   private final Properties properties;
   private final Nutrition nutrition;
   private final StockProperties stockProperties;
   private final FoodAnalytics foodAnalytics;
   
   /**
    * Constructs a new {@link FoodItem}.
    * @param name the name of the {@link FoodItem}.
    */
   public FoodItem( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodItem}.
    * @param id the id.
    * @param name the name.
    */
   public FoodItem( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodItem}.
    * @param properties the {@link Properties}.
    */
   protected FoodItem( Properties properties ) {
      this( 
               properties,
               new Nutrition(),
               new StockProperties(),
               new FoodAnalytics(),
               new MacroRatioCalculator()
      );
   }//End Constructor
   
   FoodItem( 
            Properties properties, 
            Nutrition nutrition,
            StockProperties stockProperties,
            FoodAnalytics foodAnalytics, 
            MacroRatioCalculator ratioCalculator
   ) {
      this.properties = properties;
      this.nutrition = nutrition;
      this.stockProperties = stockProperties;
      this.foodAnalytics = foodAnalytics;
      ratioCalculator.associate( nutrition, foodAnalytics );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public Properties properties() {
      return properties;
   }//End Method
   
   @Override public Nutrition nutrition() {
      return nutrition;
   }//End Method
   
   /**
    * Access to the {@link StockProperties}.
    * @return the {@link StockProperties}.
    */
   public StockProperties stockProperties() {
      return stockProperties;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodAnalytics foodAnalytics() {
      return foodAnalytics;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodItem duplicate( String referenceId ) {
      FoodItem duplicate = new FoodItem( properties().nameProperty().get() + referenceId );
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         unit.of( duplicate ).set( unit.of( this ).get() );
      }
      return duplicate;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String toString() {
      return properties.id() + ": " + properties.nameProperty().get();
   }//End Method
   
}//End Class
