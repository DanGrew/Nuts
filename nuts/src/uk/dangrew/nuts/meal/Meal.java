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
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.goal.MacroGoalRatioCalculator;

/**
 * The {@link Meal} represents a collection of {@link FoodPortion}s and provides notifications
 * as they change.
 */
public class Meal implements Food {

   private final FoodProperties properties;
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
      this( new FoodProperties( name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param id the id of the {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Meal( String id, String name ) {
      this( new FoodProperties( id, name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param properties the {@link FoodProperties}.
    */
   private Meal( FoodProperties properties ) {
      this( 
               properties, 
               new FoodAnalytics(), 
               new MealRegistrations(), 
               new MealPropertiesCalculator(),
               new MacroRatioCalculator(),
               new StockUsage()
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param properties the {@link FoodProperties}.
    * @param foodAnalytics the {@link FoodAnalytics}.
    * @param registrations the {@link MealRegistrations}.
    * @param propertiesCalculator the {@link MealPropertiesCalculator}.
    * @param ratioCalculator the {@link MacroRatioCalculator}.
    * @param stockUsage the {@link StockUsage}.
    */
   protected Meal( 
            FoodProperties properties,
            FoodAnalytics foodAnalytics,
            MealRegistrations registrations, 
            MealPropertiesCalculator propertiesCalculator,
            MacroRatioCalculator ratioCalculator,
            StockUsage stockUsage
   ) {
      this.registrations = registrations;
      this.portions = FXCollections.observableArrayList();
      this.properties = properties;
      this.foodAnalytics = foodAnalytics;
      this.propertiesCalculator = propertiesCalculator;
      this.stockUsage = stockUsage;
      
      this.propertiesCalculator.associate( this );
      this.registrations.associate( this );
      this.stockUsage.associate( portions );
      ratioCalculator.associate( properties, foodAnalytics );
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
   @Override public FoodAnalytics foodAnalytics() {
      return foodAnalytics;
   }//End Method
   
   /**
    * Access to the {@link FoodPortion}s in the {@link Meal}.
    * @return the {@link FoodPortion}s.
    */
   public ObservableList< FoodPortion > portions() {
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
   
   public void swap( FoodPortion portion1, FoodPortion portion2 ) {
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
   
   @Override public Meal duplicate( String referenceId ) {
      Meal duplicate = new Meal( properties().nameProperty().get() + referenceId );
      portions().forEach( p -> duplicate.portions().add( p.duplicate( referenceId ) ) );
      return duplicate;
   }//End Method
   
   @Override public String toString() {
      return properties.nameProperty().get();
   }//End Method

}//End Class
