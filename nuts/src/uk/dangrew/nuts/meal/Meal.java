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
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.FoodProperties;

/**
 * The {@link Meal} represents a collection of {@link FoodPortion}s and provides notifications
 * as they change.
 */
public class Meal implements Food {

   private final MealRegistrations registrations;
   private final MealPropertiesCalculator propertiesCalculator;
   private final FoodProperties properties;
   private final ObservableList< FoodPortion > portions;
   
   /**
    * Constructs a new {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Meal( String name ) {
      this( new MealRegistrations(), new FoodProperties( name ), new MealPropertiesCalculator() );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param registrations the {@link MealRegistrations}.
    * @param properties the {@link FoodProperties}.
    * @param propertiesCalculator the {@link MealPropertiesCalculator}.
    */
   Meal( 
            MealRegistrations registrations, 
            FoodProperties properties, 
            MealPropertiesCalculator propertiesCalculator 
   ) {
      this.registrations = registrations;
      this.portions = FXCollections.observableArrayList();
      this.registrations.associate( this );
      this.properties = properties;
      this.propertiesCalculator = propertiesCalculator;
      this.propertiesCalculator.associate( this );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodProperties properties() {
      return properties;
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
