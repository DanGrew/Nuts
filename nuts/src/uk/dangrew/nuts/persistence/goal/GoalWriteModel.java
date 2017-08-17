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
import uk.dangrew.nuts.store.Database;

/**
 * {@link GoalWriteModel} is responsible for handling the hooks for the {@link uk.dangrew.jupa.json.parse.JsonParser}
 * when writing the {@link uk.dangrew.nuts.goal.Goal}.
 */
class GoalWriteModel {
   
   private final Database database;
   
   /**
    * Constructs a new {@link GoalWriteModel}.
    * @param database the {@link Database}.
    */
   GoalWriteModel( Database database ) {
      this.database = database;
   }//End Constructor
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getAge( String key ) {
      return database.goal().age().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getWeight( String key ) {
      return database.goal().weight().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getHeight( String key ) {
      return database.goal().height().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Gender getGender( String key ) {
      return database.goal().gender().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getBmr( String key ) {
      return database.goal().bmr().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getPal( String key ) {
      return database.goal().pal().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getTee( String key ) {
      return database.goal().tee().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getExerciseCalories( String key ) {
      return database.goal().exerciseCalories().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getCalorieDeficit( String key ) {
      return database.goal().calorieDeficit().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getProteinPerPound( String key ) {
      return database.goal().proteinPerPound().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getFatPerPound( String key ) {
      return database.goal().fatPerPound().get();
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getCalorieGoal( String key ) {
      return database.goal().properties().calories().get();
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getCarbohydratesGoal( String key ) {
      return database.goal().properties().carbohydrates().get();
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getFatsGoal( String key ) {
      return database.goal().properties().fats().get();
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getProteinGoal( String key ) {
      return database.goal().properties().protein().get();
   }//End Method
   
}//End Class
