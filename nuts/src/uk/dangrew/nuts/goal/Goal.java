/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.MacroRatioCalculator;

/**
 * {@link Goal} represents the properties associated with {@link uk.dangrew.nuts.nutrients.MacroNutrient} targets.
 * It also represents a {@link Food} in that it shares the same properties.
 */
public class Goal {
   
   static final double RECOMMENDED_PROTEIN_PER_POUND = 1.0;
   static final double RECOMMENDED_FAT_PER_POUND = 0.4;
   static final double RECOMMENDED_ACTIVITY_LEVEL = 1.4;
   
   private final CalorieGoalCalculator calorieCalculator;
   private final MacroGoalCalculator macroCalculator;
   
   private final FoodProperties properties;
   private final FoodAnalytics analytics;
   private final ObjectProperty< Double > age;
   private final ObjectProperty< Double > weight;
   private final ObjectProperty< Double > height;
   private final ObjectProperty< Gender > gender;
   private final ObjectProperty< Double > bmr;
   private final ObjectProperty< Double > pal;
   private final ObjectProperty< Double > tee;
   private final ObjectProperty< Double > exerciseCalories;
   private final ObjectProperty< Double > calorieDeficit;
   private final ObjectProperty< Double > proteinPerPound;
   private final ObjectProperty< Double > fatPerPound;
   
   /**
    * Constructs a new {@link Goal}.
    * @param name the name of the {@link Goal}.
    */
   public Goal( String name ) {
      this( 
               new FoodProperties( name ), 
               new FoodAnalytics(), 
               new CalorieGoalCalculator(), 
               new MacroGoalCalculator(),
               new MacroRatioCalculator()
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link Goal}.
    * @param properties the {@link FoodProperties}.
    * @param analytics the {@link FoodAnalytics}.
    * @param calorieCalculator the {@link CalorieGoalCalculator}.
    * @param macroCalculator the {@link MacroGoalCalculator}.
    * @param ratioCalculator the {@link MacroRatioCalculator}.
    */
   Goal( 
            FoodProperties properties, 
            FoodAnalytics analytics, 
            CalorieGoalCalculator calorieCalculator, 
            MacroGoalCalculator macroCalculator,
            MacroRatioCalculator ratioCalculator
   ) {
      this.properties = properties;
      this.analytics = analytics;
      this.age = new SimpleObjectProperty<>( 0.0 );
      this.weight = new SimpleObjectProperty<>( 0.0 );
      this.height = new SimpleObjectProperty<>( 0.0 );
      this.gender = new SimpleObjectProperty<>();
      this.bmr = new SimpleObjectProperty<>( 0.0 );
      this.pal = new SimpleObjectProperty<>( RECOMMENDED_ACTIVITY_LEVEL );
      this.tee = new SimpleObjectProperty<>( 0.0 );
      this.exerciseCalories = new SimpleObjectProperty<>( 0.0 );
      this.calorieDeficit = new SimpleObjectProperty<>( 0.0 );
      this.proteinPerPound = new SimpleObjectProperty<>( RECOMMENDED_PROTEIN_PER_POUND );
      this.fatPerPound = new SimpleObjectProperty<>( RECOMMENDED_FAT_PER_POUND );
      
      ratioCalculator.associate( properties, analytics );
      this.calorieCalculator = calorieCalculator;
      this.calorieCalculator.associate( this );
      this.macroCalculator = macroCalculator;
      this.macroCalculator.associate( this );
   }//End Constructor

   /**
    * Access to the {@link FoodProperties}.
    * @return the {@link FoodProperties}.
    */
   public FoodProperties properties() {
      return properties;
   }//End Method
   
   /**
    * Access to the {@link FoodAnalytics}.
    * @return the {@link FoodAnalytics}.
    */
   public FoodAnalytics foodAnalytics() {
      return analytics;
   }//End Method

   /**
    * Access to the age property, measured in years.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > age() {
      return age;
   }//End Method
   
   /**
    * Access to the weight property, measured in pounds.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > weight() {
      return weight;
   }//End Method
   
   /**
    * Access to the age height, measured in metres.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > height() {
      return height;
   }//End Method

   /**
    * Access to the basal metabolic rate property, measured in calories.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > bmr() {
      return bmr;
   }//End Method
   
   /**
    * Access to the physical activity level property, measured as a percentage of bmr.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > pal() {
      return pal;
   }//End Method
   
   /**
    * Access to the total energy expenditure calories property, measured in calories.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > tee() {
      return tee;
   }//End Method

   /**
    * Access to the exercise calories property.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > exerciseCalories() {
      return exerciseCalories;
   }//End Method
   
   /**
    * Access to the calorie deficit property.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > calorieDeficit() {
      return calorieDeficit;
   }//End Method

   /**
    * Access to the number of grams of protein per pound property. Recommended and default is 
    * {@value #RECOMMENDED_PROTEIN_PER_POUND}.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > proteinPerPound() {
      return proteinPerPound;
   }//End Method

   /**
    * Access to the number of grams of fat per pound property. Recommended and default is 
    * {@value #RECOMMENDED_FAT_PER_POUND}.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > fatPerPound() {
      return fatPerPound;
   }//End Method

   /**
    * Access to the {@link Gender} of the user.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Gender > gender() {
      return gender;
   }//End Method

}//End Class
