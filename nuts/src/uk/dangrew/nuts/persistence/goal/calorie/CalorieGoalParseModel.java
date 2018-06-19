/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.goal.calorie;

import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalStore;
import uk.dangrew.nuts.goal.calorie.Gender;

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
   
   void setCalorieGoal( Double value ) {
      this.calorieGoal = value;
   }//End Method
   
   void setCarbohydratesGoal( Double value ) {
      this.carbohydratesGoal = value;
   }//End Method
   
   void setFatsGoal( Double value ) {
      this.fatGoal = value;
   }//End Method
   
   void setProteinGoal( Double value ) {
      this.proteinGoal = value;
   }//End Method
   
}//End Class
