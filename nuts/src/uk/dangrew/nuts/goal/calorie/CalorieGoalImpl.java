/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal.calorie;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.kode.concept.Properties;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.goal.GoalTypes;
import uk.dangrew.nuts.nutrients.Nutrition;

/**
 * {@link Goal} represents the properties associated with {@link uk.dangrew.nuts.nutrients.MacroNutrient} targets.
 * It also represents a {@link Food} in that it shares the same properties.
 */
public class CalorieGoalImpl implements CalorieGoal {
   
   static final double RECOMMENDED_PROTEIN_PER_POUND = 1.0;
   static final double RECOMMENDED_FAT_PER_POUND = 0.4;
   static final double RECOMMENDED_ACTIVITY_LEVEL = 1.4;
   
   private final CalorieGoalCalculator calorieCalculator;
   private final MacroCalorieGoalCalculator macroCalculator;
   
   private final Properties properties;
   private final Nutrition nutrition;
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
    * Constructs a new {@link GoalImpl}.
    * @param name the name of the {@link Goal}.
    */
   public CalorieGoalImpl( String name ) {
      this( 
               new Properties( name ), 
               new Nutrition(),
               new FoodAnalytics(), 
               new CalorieGoalCalculator(), 
               new MacroCalorieGoalCalculator(),
               new MacroRatioCalculator()
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link GoalImpl}.
    * @param name the name of the {@link Goal}.
    */
   public CalorieGoalImpl( String id, String name ) {
      this( 
               new Properties( id, name ), 
               new Nutrition(),
               new FoodAnalytics(), 
               new CalorieGoalCalculator(), 
               new MacroCalorieGoalCalculator(),
               new MacroRatioCalculator()
      );
   }//End Constructor
   
   CalorieGoalImpl( 
            Properties properties, 
            Nutrition nutrition,
            FoodAnalytics analytics, 
            CalorieGoalCalculator calorieCalculator, 
            MacroCalorieGoalCalculator macroCalculator,
            MacroRatioCalculator ratioCalculator
   ) {
      this.properties = properties;
      this.nutrition = nutrition;
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
      
      ratioCalculator.associate( nutrition, analytics );
      this.calorieCalculator = calorieCalculator;
      this.calorieCalculator.associate( this );
      this.macroCalculator = macroCalculator;
      this.macroCalculator.associate( this );
   }//End Constructor

   @Override public Properties properties() {
      return properties;
   }//End Method
   
   @Override public Nutrition nutrition() {
      return nutrition;
   }//End Method
   
   @Override public FoodAnalytics foodAnalytics() {
      return analytics;
   }//End Method
   
   @Override public GoalTypes type() {
      return GoalTypes.Calorie;
   }//End Method

   @Override public ObjectProperty< Double > age() {
      return age;
   }//End Method
   
   @Override public ObjectProperty< Double > weight() {
      return weight;
   }//End Method
   
   @Override public ObjectProperty< Double > height() {
      return height;
   }//End Method

   /**
    * Access to the basal metabolic rate property, measured in calories.
    * @return the {@link ObjectProperty}.
    */
   @Override public ObjectProperty< Double > bmr() {
      return bmr;
   }//End Method
   
   /**
    * Access to the physical activity level property, measured as a percentage of bmr.
    * @return the {@link ObjectProperty}.
    */
   @Override public ObjectProperty< Double > pal() {
      return pal;
   }//End Method
   
   @Override public ObjectProperty< Double > tee() {
      return tee;
   }//End Method

   @Override public ObjectProperty< Double > exerciseCalories() {
      return exerciseCalories;
   }//End Method
   
   @Override public ObjectProperty< Double > calorieDeficit() {
      return calorieDeficit;
   }//End Method

   @Override public ObjectProperty< Double > proteinPerPound() {
      return proteinPerPound;
   }//End Method

   @Override public ObjectProperty< Double > fatPerPound() {
      return fatPerPound;
   }//End Method

   @Override public ObjectProperty< Gender > gender() {
      return gender;
   }//End Method
   
   @Override public Food duplicate() {
      return this;
   }//End Method

   @Override public String toString() {
      return properties.nameProperty().get();
   }//End Method
}//End Class
