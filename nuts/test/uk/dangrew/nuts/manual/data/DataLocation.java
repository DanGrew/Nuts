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
import uk.dangrew.nuts.persistence.fooditems.FoodItemPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.weighins.WeightRecordingPersistence;
import uk.dangrew.nuts.store.Database;

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
      MealPersistence mealPersistence = new MealPersistence( database, database.meals() );
      MealPersistence planPersistence = new MealPersistence( database, database.plans() );
      
      String value = TestCommon.readFileIntoString( DataLocation.class, "food-items.json" );
      JSONObject json = new JSONObject( value );
      foodItemPersistence.readHandles().parse( json );
      
      value = TestCommon.readFileIntoString( DataLocation.class, "meals.json" );
      json = new JSONObject( value );
      mealPersistence.readHandles().parse( json );
      
      value = TestCommon.readFileIntoString( DataLocation.class, "plans.json" );
      json = new JSONObject( value );
      planPersistence.readHandles().parse( json );
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
   
}//End Class
