/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.manual.data;

import java.util.Random;

import uk.dangrew.jupa.file.protocol.WorkspaceJsonPersistingProtocol;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.Gender;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;
import uk.dangrew.nuts.store.Database;

/**
 * Locator for test files.
 */
public class DataLocation {
   
   private static final Random random = new Random();
   
   /**
    * Method to load some sample data for E2E tests.
    * @param database the {@link Database} to load in to.
    */
   public static void loadSampleFoodData( Database database ) {
      new DatabaseIo( database )
               .withFoodItems( new WorkspaceJsonPersistingProtocol( "food-items.json", DataLocation.class ) )
               .withMeals( new WorkspaceJsonPersistingProtocol( "meals.json", DataLocation.class ) )
               .withTemplates( new WorkspaceJsonPersistingProtocol( "plans.json", DataLocation.class ) )
               .read();
      database.stockLists().createConcept( "Stock" );
      database.resolver().resolve();
   }//End Method
   
   /**
    * Method to load some sample data for E2E tests.
    * @param database the {@link Database} to load in to.
    */
   public static void loadSampleWeightRecordings( Database database ) {
      new DatabaseIo( database )
               .withWeightRecordings( new WorkspaceJsonPersistingProtocol( "weightRecordings.json", DataLocation.class ) )
               .read();
      database.resolver().resolve();
   }//End Method
   
   public static void loadSampleProgressSeries( Database database ) {
      new DatabaseIo( database )
               .withProgressSeries( new WorkspaceJsonPersistingProtocol( "progressSeries.json", DataLocation.class ) )
               .read();
      database.resolver().resolve();
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
   
   public static FoodItem randomizedFood(){
      FoodItem item = new FoodItem( "Anything" );
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         item.nutrition().of( unit ).set( random.nextDouble() * 100 );
      }
      return item;
   }//End Method
   
}//End Class
