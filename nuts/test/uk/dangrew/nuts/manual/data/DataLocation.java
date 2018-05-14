/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.manual.data;

import org.json.JSONObject;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.Gender;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.persistence.fooditems.FoodItemPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.progress.ProgressSeriesPersistence;
import uk.dangrew.nuts.persistence.weighins.WeightRecordingPersistence;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

/**
 * Locator for test files.
 */
public class DataLocation {
   
   /**
    * Method to load some sample data for E2E tests.
    * @param database the {@link Database} to load in to.
    */
   public static void loadSampleFoodData( Database database ) {
      FoodItemPersistence foodItemPersistence = new FoodItemPersistence( database );
      MealPersistence< Meal > mealPersistence = new MealPersistence<>( database, database.meals() );
      MealPersistence< Template > planPersistence = new MealPersistence<>( database, database.templates() );
      
      String value = TestCommon.readFileIntoString( DataLocation.class, "food-items.json" );
      JSONObject json = new JSONObject( value );
      foodItemPersistence.readHandles().parse( json );
      
      value = TestCommon.readFileIntoString( DataLocation.class, "meals.json" );
      json = new JSONObject( value );
      mealPersistence.readHandles().parse( json );
      
      value = TestCommon.readFileIntoString( DataLocation.class, "plans.json" );
      json = new JSONObject( value );
      planPersistence.readHandles().parse( json );
      
      database.stockLists().createConcept( "Stock" );
   }//End Method
   
   /**
    * Method to load some sample data for E2E tests.
    * @param database the {@link Database} to load in to.
    */
   public static void loadSampleWeightRecordings( Database database ) {
      WeightRecordingPersistence persistence = new WeightRecordingPersistence( database );
      
      String value = TestCommon.readFileIntoString( DataLocation.class, "weightRecordings.json" );
      JSONObject json = new JSONObject( value );
      persistence.readHandles().parse( json );
   }//End Method
   
   public static void loadSampleProgressSeries( Database database ) {
      ProgressSeriesPersistence persistence = new ProgressSeriesPersistence( database );
      
      String value = TestCommon.readFileIntoString( DataLocation.class, "progressSeries.json" );
      JSONObject json = new JSONObject( value );
      persistence.readHandles().parse( json );
   }//End Method
   
   public static void configureExampleGoal( CalorieGoal calorieGoal ) {
      calorieGoal.gender().set( Gender.Male );
      calorieGoal.age().set( 28.0 );
      calorieGoal.weight().set( 197.0 );
      calorieGoal.height().set( 1.87 );
      calorieGoal.exerciseCalories().set( 500.0 );
      calorieGoal.calorieDeficit().set( 700.0 );
      calorieGoal.fatPerPound().set( 0.35 );
   }//End Method
   
}//End Class
