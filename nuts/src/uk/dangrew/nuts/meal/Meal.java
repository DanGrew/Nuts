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
import uk.dangrew.nuts.food.MacroRatioCalculator;

/**
 * The {@link Meal} represents a collection of {@link FoodPortion}s and provides notifications
 * as they change.
 */
public class Meal implements Food {

   private final MealRegistrations registrations;
   private final MealPropertiesCalculator propertiesCalculator;
   private final FoodProperties properties;
   private final FoodAnalytics analytics;
   private final ObservableList< FoodPortion > portions;
   
   /**
    * Constructs a new {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Meal( String name ) {
      this( 
               new FoodProperties( name ), 
               new FoodAnalytics(), 
               new MealRegistrations(), 
               new MealPropertiesCalculator(),
               new MacroRatioCalculator()
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param properties the {@link FoodProperties}.
    * @param analytics the {@link FoodAnalytics}.
    * @param registrations the {@link MealRegistrations}.
    * @param propertiesCalculator the {@link MealPropertiesCalculator}.
    * @param ratioCalculator the {@link MacroRatioCalculator}.
    */
   Meal( 
            FoodProperties properties,
            FoodAnalytics analytics,
            MealRegistrations registrations, 
            MealPropertiesCalculator propertiesCalculator,
            MacroRatioCalculator ratioCalculator
   ) {
      this.registrations = registrations;
      this.portions = FXCollections.observableArrayList();
      this.registrations.associate( this );
      this.properties = properties;
      this.propertiesCalculator = propertiesCalculator;
      this.propertiesCalculator.associate( this );
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

}//End Class
