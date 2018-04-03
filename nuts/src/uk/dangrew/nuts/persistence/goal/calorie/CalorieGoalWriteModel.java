/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.goal.calorie;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.goal.Gender;
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.GoalStore;

/**
 * {@link GoalWriteModel} is responsible for handling the hooks for the {@link uk.dangrew.jupa.json.parse.JsonParser}
 * when writing the {@link uk.dangrew.nuts.goal.Goal}.
 */
class CalorieGoalWriteModel {
   
   private final GoalStore goals;
   private final List< CalorieGoal > buffer;
   private CalorieGoal current; 
   
   /**
    * Constructs a new {@link GoalWriteModel}.
    * @param goals the {@link GoalStore}.
    */
   CalorieGoalWriteModel( GoalStore goals ) {
      this.goals = goals;
      this.buffer = new ArrayList<>();
   }//End Constructor
   
   /**
    * Getter for the number of {@link Goal}s to write.
    * @param key the key.
    * @return the number to write.
    */
   Integer getNumberOfGoals( String key ){
      return goals.objectList().size();
   }//End Method
   
   /**
    * Triggered when starting to write all {@link Goal}s.
    * @param key the key.
    */
   void startWritingGoals( String key ) {
      buffer.clear();
      buffer.addAll( goals.objectList() );
   }//End Method
   
   /**
    * Triggered when starting to write an individual {@link Goal}.
    * @param key the key.
    */
   void startWritingGoal( String key ) {
      if ( buffer.isEmpty() ) {
         return;
      }
      this.current = buffer.remove( 0 );
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   String getId( String key ) {
      return current.properties().id();
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   String getName( String key ) {
      return current.properties().nameProperty().get();
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getAge( String key ) {
      return current.age().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getWeight( String key ) {
      return current.weight().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getHeight( String key ) {
      return current.height().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Gender getGender( String key ) {
      return current.gender().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getBmr( String key ) {
      return current.bmr().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getPal( String key ) {
      return current.pal().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getTee( String key ) {
      return current.tee().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getExerciseCalories( String key ) {
      return current.exerciseCalories().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getCalorieDeficit( String key ) {
      return current.calorieDeficit().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getProteinPerPound( String key ) {
      return current.proteinPerPound().get();
   }//End Method

   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getFatPerPound( String key ) {
      return current.fatPerPound().get();
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getCalorieGoal( String key ) {
      return current.properties().calories().get();
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getCarbohydratesGoal( String key ) {
      return current.properties().carbohydrates().get();
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getFatsGoal( String key ) {
      return current.properties().fats().get();
   }//End Method
   
   /**
    * Getter for the property from the {@link uk.dangrew.nuts.goal.Goal}.
    * @param key the parse key.
    * @return the value.
    */
   Double getProteinGoal( String key ) {
      return current.properties().protein().get();
   }//End Method
   
}//End Class
