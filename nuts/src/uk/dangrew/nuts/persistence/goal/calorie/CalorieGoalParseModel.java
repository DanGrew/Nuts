/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.goal.calorie;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalStore;
import uk.dangrew.nuts.goal.calorie.Gender;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

/**
 * {@link GoalParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing the {@link Goal}.
 */
class CalorieGoalParseModel {
   
   private final CalorieGoalStore goals;
   private final Map< NutritionalUnit, Double > nutritionalUnitGoals;
   
   private String id;
   private String name;
   private Double age;
   private Double weight;
   private Double height;
   private Gender gender;
   private Double bmr;
   private Double pal;
   private Double tee;
   private Double exerciseCalories;
   private Double calorieDeficit;
   private Double proteinPerPound;
   private Double fatPerPound;
   
   /**
    * Constructs a new {@link GoalParseModel}.
    * @param goals the {@link GoalStore}.
    */
   CalorieGoalParseModel( CalorieGoalStore goals ) {
      this.goals = goals;
      this.nutritionalUnitGoals = new EnumMap<>( NutritionalUnit.class );
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link Goal}.
    * @param key the parsed key.
    */
   void startGoal( String key ) {
      id = null;
      name = null;
      age = null;
      weight = null;
      height = null;
      gender = null;
      bmr = null;
      pal = null;
      tee = null;
      exerciseCalories = null;
      calorieDeficit = null;
      proteinPerPound = null;
      fatPerPound = null;
      this.nutritionalUnitGoals.clear();
   }//End Method
   
   /**
    * Triggered when all values of a {@link Goal} have been parsed.
    * @param key the parsed key.
    */
   void finishGoal( String key ) {
      CalorieGoal goalImpl = goals.get( id );
      if ( goalImpl == null ) {
         goalImpl = goals.createConcept( id, name );
      }
      
      setGoalProperties( goalImpl );
   }//End Method
   
   void finishSingleGoalDefinition( String key ) {
      id = "single-goal-no-id-provided-unique";
      name = "Singleton Goal"; 
      CalorieGoal singleton = goals.createConcept( id, name );
      setGoalProperties( singleton );
   }//End Method
   
   /**
    * Setter for the properties of a {@link Goal} from the parsed values.
    * @param goal the {@link Goal} to set on.
    */
   private void setGoalProperties( CalorieGoal goalImpl ) {
      goalImpl.properties().nameProperty().set( name );
      goalImpl.age().set( age );
      goalImpl.weight().set( weight );
      goalImpl.height().set( height );
      goalImpl.gender().set( gender );
      goalImpl.bmr().set( bmr );
      goalImpl.pal().set( pal );
      goalImpl.tee().set( tee );
      goalImpl.exerciseCalories().set( exerciseCalories );
      goalImpl.calorieDeficit().set( calorieDeficit );
      goalImpl.proteinPerPound().set( proteinPerPound );
      goalImpl.fatPerPound().set( fatPerPound );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         Optional.ofNullable( nutritionalUnitGoals.get( unit ) ).ifPresent( 
                  v -> unit.of( goalImpl ).set( v ) 
         );
      }
   }//End Method

   void setId( String value ) {
      this.id = value;
   }//End Method
   
   void setName( String value ) {
      this.name = value;
   }//End Method
   
   void setAge( Double value ) {
      this.age = value;
   }//End Method

   void setWeight( Double value ) {
      this.weight = value;
   }//End Method

   void setHeight( Double value ) {
      this.height = value;
   }//End Method

   void setGender( Gender value ) {
      this.gender = value;
   }//End Method

   void setBmr( Double value ) {
      this.bmr = value;
   }//End Method

   void setPal( Double value ) {
      this.pal = value;
   }//End Method

   void setTee( Double value ) {
      this.tee = value;
   }//End Method

   void setExerciseCalories( Double value ) {
      this.exerciseCalories = value;
   }//End Method

   void setCalorieDeficit( Double value ) {
      this.calorieDeficit = value;
   }//End Method

   void setProteinPerPound( Double value ) {
      this.proteinPerPound = value;
   }//End Method

   void setFatPerPound( Double value ) {
      this.fatPerPound = value;
   }//End Method
   
   @Deprecated void setCalorieGoal( Double value ) {
      this.nutritionalUnitGoals.put( NutritionalUnit.Calories, value );
   }//End Method
   
   @Deprecated void setCarbohydratesGoal( Double value ) {
      this.nutritionalUnitGoals.put( NutritionalUnit.Carbohydrate, value );
   }//End Method
   
   @Deprecated void setFatsGoal( Double value ) {
      this.nutritionalUnitGoals.put( NutritionalUnit.Fat, value );
   }//End Method
   
   @Deprecated void setProteinGoal( Double value ) {
      this.nutritionalUnitGoals.put( NutritionalUnit.Protein, value );
   }//End Method
   
   void setNutritionalUnitGoal( NutritionalUnit unit, Double value ) {
      this.nutritionalUnitGoals.put( unit, value );
   }//End Method
   
}//End Class
