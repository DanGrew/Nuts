/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.goal;

import uk.dangrew.nuts.goal.Gender;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.store.Database;

/**
 * {@link GoalParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing the {@link Goal}.
 */
class GoalParseModel {
   
   private final Database database;
   
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
    * @param database the {@link Database}.
    */
   GoalParseModel( Database database ) {
      this.database = database;
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link Goal}.
    * @param key the parsed key.
    */
   void startGoal( String key ) {
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
      Goal goal = database.goal();
      
      goal.age().set( age );
      goal.weight().set( weight );
      goal.height().set( height );
      goal.gender().set( gender );
      goal.bmr().set( bmr );
      goal.pal().set( pal );
      goal.tee().set( tee );
      goal.exerciseCalories().set( exerciseCalories );
      goal.calorieDeficit().set( calorieDeficit );
      goal.proteinPerPound().set( proteinPerPound );
      goal.fatPerPound().set( fatPerPound );
      goal.properties().calories().set( calorieGoal );
      goal.properties().carbohydrates().set( carbohydratesGoal );
      goal.properties().fats().set( fatGoal );
      goal.properties().protein().set( proteinGoal );
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
