/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.meal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.concept.Properties;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.nutrients.Nutrition;

/**
 * The {@link Meal} represents a collection of {@link FoodPortion}s and provides notifications
 * as they change.
 */
public class Meal implements Food, FoodHolder {

   private final Properties properties;
   private final Nutrition nutrition;
   private final FoodAnalytics foodAnalytics;
   private final MealPropertiesCalculator propertiesCalculator;
   
   private final MealRegistrations registrations;
   private final StockUsage stockUsage;
   private final ObservableList< FoodPortion > portions;
   
   /**
    * Constructs a new {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Meal( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param id the id of the {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Meal( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   protected Meal( Properties properties ) {
      this( 
               properties, 
               new Nutrition(),
               new FoodAnalytics(), 
               new MealRegistrations(), 
               new MealPropertiesCalculator(),
               new MacroRatioCalculator(),
               new StockUsage()
      );
   }//End Constructor
   
   protected Meal( 
            Properties properties,
            Nutrition nutrition,
            FoodAnalytics foodAnalytics,
            MealRegistrations registrations, 
            MealPropertiesCalculator propertiesCalculator,
            MacroRatioCalculator ratioCalculator,
            StockUsage stockUsage
   ) {
      this.registrations = registrations;
      this.portions = FXCollections.observableArrayList();
      this.properties = properties;
      this.nutrition = nutrition;
      this.foodAnalytics = foodAnalytics;
      this.propertiesCalculator = propertiesCalculator;
      this.stockUsage = stockUsage;
      
      this.propertiesCalculator.associate( this );
      this.registrations.associate( this );
      this.stockUsage.associate( portions );
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
    * {@inheritDoc}
    */
   @Override public FoodAnalytics foodAnalytics() {
      return foodAnalytics;
   }//End Method
   
   /**
    * Access to the {@link FoodPortion}s in the {@link Meal}.
    * @return the {@link FoodPortion}s.
    */
   @Override public ObservableList< FoodPortion > portions() {
      return portions;
   }//End Method

   /**
    * Access to the {@link MealRegistrations}.
    * @return the {@link MealRegistrations}.
    */
   public MealRegistrations registrations() {
      return registrations;
   }//End Method

   /**
    * Access to the {@link StockUsage}.
    * @return the {@link StockUsage}.
    */
   public StockUsage stockUsage() {
      return stockUsage;
   }//End Method
   
   @Override public void swap( FoodPortion portion1, FoodPortion portion2 ) {
      if ( !portions().contains( portion1 ) || !portions().contains( portion2 ) ) {
         return;
      }
      
      int firstIndex = portions().indexOf( portion1 );
      int secondIndex = portions().indexOf( portion2 );
      portions().remove( portion1 );
      portions().remove( portion2 );
      portions().add( firstIndex, portion2 );
      portions().add( secondIndex, portion1 );
   }//End Method
   
   @Override public Meal duplicate() {
      Meal duplicate = new Meal( properties().nameProperty().get() + Food.COPY_SUFFIX );
      duplicateProperties( duplicate );
      return duplicate;
   }//End Method
   
   protected void duplicateProperties( Meal duplicate ){
      portions().forEach( p -> duplicate.portions().add( p.duplicate() ) );
   }//End Method
   
   @Override public String toString() {
      return properties.nameProperty().get();
   }//End Method

}//End Class
