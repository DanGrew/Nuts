/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.goal.calorie;

import uk.dangrew.nuts.goal.Gender;
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.CalorieGoalStore;

/**
 * {@link GoalParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing the {@link Goal}.
 */
class CalorieGoalParseModel {
   
   private final CalorieGoalStore goals;
   
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
   private Double calorieGoal;
   private Double carbohydratesGoal;
   private Double fatGoal;
   private Double proteinGoal;
   
   /**
    * Constructs a new {@link GoalParseModel}.
    * @param goals the {@link GoalStore}.
    */
   CalorieGoalParseModel( CalorieGoalStore goals ) {
      this.goals = goals;
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
      goalImpl.properties().calories().set( calorieGoal );
      goalImpl.properties().carbohydrates().set( carbohydratesGoal );
      goalImpl.properties().fats().set( fatGoal );
      goalImpl.properties().protein().set( proteinGoal );
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setId( String key, String value ) {
      this.id = value;
   }//End Method
   
   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setName( String key, String value ) {
      this.name = value;
   }//End Method
   
   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setAge( String key, Double value ) {
      this.age = value;
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setWeight( String key, Double value ) {
      this.weight = value;
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setHeight( String key, Double value ) {
      this.height = value;
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setGender( String key, Gender value ) {
      this.gender = value;
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setBmr( String key, Double value ) {
      this.bmr = value;
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setPal( String key, Double value ) {
      this.pal = value;
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setTee( String key, Double value ) {
      this.tee = value;
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setExerciseCalories( String key, Double value ) {
      this.exerciseCalories = value;
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setCalorieDeficit( String key, Double value ) {
      this.calorieDeficit = value;
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setProteinPerPound( String key, Double value ) {
      this.proteinPerPound = value;
   }//End Method

   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setFatPerPound( String key, Double value ) {
      this.fatPerPound = value;
   }//End Method
   
   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setCalorieGoal( String key, Double value ) {
      this.calorieGoal = value;
   }//End Method
   
   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setCarbohydratesGoal( String key, Double value ) {
      this.carbohydratesGoal = value;
   }//End Method
   
   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setFatsGoal( String key, Double value ) {
      this.fatGoal = value;
   }//End Method
   
   /**
    * Setter for the property.
    * @param key the parse key.
    * @param value the value.
    */
   void setProteinGoal( String key, Double value ) {
      this.proteinGoal = value;
   }//End Method
   
}//End Class
