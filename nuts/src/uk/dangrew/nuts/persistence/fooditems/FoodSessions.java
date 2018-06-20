/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.fooditems;



import uk.dangrew.jupa.file.protocol.JarJsonPersistingProtocol;
import uk.dangrew.jupa.file.protocol.JsonPersistingProtocol;
import uk.dangrew.nuts.main.Nuts;
import uk.dangrew.nuts.persistence.dayplan.DayPlanPersistence;
import uk.dangrew.nuts.persistence.goal.calorie.CalorieGoalPersistence;
import uk.dangrew.nuts.persistence.goal.proportion.ProportionGoalPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.setup.DataSetup;
import uk.dangrew.nuts.persistence.stock.StockPersistence;
import uk.dangrew.nuts.store.Database;

/**
 * The {@link FoodSessions} provides the {@link SessionManager} for saving and loading {@link uk.dangrew.nuts.food.FoodItem}s.
 */
public class FoodSessions {

   static final String FOLDER_NAME = "uk.dangrew.nuts";
   static final String FOOD_ITEM_FILE_NAME = "food-items.json";
   static final String MEAL_FILE_NAME = "meals.json";
   static final String TEMPLATE_FILE_NAME = "plans.json";
   static final String DAY_PLANS_FILE_NAME = "dayplans.json";
   static final String SHOPPING_LISTS_FILE_NAME = "shoppingLists.json";
   static final String STOCK_LISTS_FILE_NAME = "stockLists.json";
   static final String WEIGHT_RECORDING_FILE_NAME = "weightRecordings.json";
   static final String CALORIE_GOALS_FILE_NAME = "goals.json";
   static final String PROPORTION_GOALS_FILE_NAME = "proportionGoals.json";
   static final String LABEL_FILE_NAME = "labels.json";
   static final String PROGRESS_SERIES_FILE_NAME = "progressSeries.json";
   
   static final String LEGACY_GOAL_FILE_NAME = "goal.json";
   
   private static final JarJsonPersistingProtocol foodItemFileLocation = protocolFor( FOOD_ITEM_FILE_NAME );
   private static final JarJsonPersistingProtocol mealFileLocation = protocolFor( MEAL_FILE_NAME );
   private static final JarJsonPersistingProtocol templateFileLocation = protocolFor( TEMPLATE_FILE_NAME );
   private static final JarJsonPersistingProtocol dayPlanFileLocation = protocolFor( DAY_PLANS_FILE_NAME );
   private static final JarJsonPersistingProtocol shoppingListFileLocation = protocolFor( SHOPPING_LISTS_FILE_NAME );
   private static final JarJsonPersistingProtocol stockListFileLocation = protocolFor( STOCK_LISTS_FILE_NAME );
   private static final JarJsonPersistingProtocol legacyGoalFileLocation = protocolFor( LEGACY_GOAL_FILE_NAME );
   private static final JarJsonPersistingProtocol calorieGoalsLocation = protocolFor( CALORIE_GOALS_FILE_NAME );
   private static final JarJsonPersistingProtocol proportionGoalsFileLocation = protocolFor( PROPORTION_GOALS_FILE_NAME );
   private static final JarJsonPersistingProtocol labelFileLocation = protocolFor( LABEL_FILE_NAME );
   private static final JarJsonPersistingProtocol weightRecordingLocation = protocolFor( WEIGHT_RECORDING_FILE_NAME );
   private static final JarJsonPersistingProtocol progressSeriesFileLocation = protocolFor( PROGRESS_SERIES_FILE_NAME );
   
   private final DatabaseIo databaseIo;
   private final DataSetup setup;
   
   public FoodSessions( Database database ) {
      this( 
               database,
               foodItemFileLocation,
               mealFileLocation,
               templateFileLocation,
               dayPlanFileLocation,
               shoppingListFileLocation,
               stockListFileLocation,
               legacyGoalFileLocation,
               calorieGoalsLocation,
               proportionGoalsFileLocation,
               labelFileLocation,
               weightRecordingLocation,
               progressSeriesFileLocation
      );
   }//End Constructor
   
   FoodSessions( 
            Database database,
            JsonPersistingProtocol foodItemFileLocation,
            JsonPersistingProtocol mealFileLocation,
            JsonPersistingProtocol templateFileLocation,
            JsonPersistingProtocol dayPlanFileLocation,
            JsonPersistingProtocol shoppingListFileLocation,
            JsonPersistingProtocol stockListFileLocation,
            JsonPersistingProtocol legacyGoalFileLocation,
            JsonPersistingProtocol calorieGoalsLocation,
            JsonPersistingProtocol proportionGoalsFileLocation,
            JsonPersistingProtocol labelFileLocation,
            JsonPersistingProtocol weightRecordingLocation,
            JsonPersistingProtocol progressSeriesFileLocation
      ){
   
      this.setup = new DataSetup( database );
      this.databaseIo = new DatabaseIo( database )
               
               
               .withFoodItems( foodItemFileLocation )
               .withMeals( mealFileLocation )
               .withMarshallerFor( 
                        new MealPersistence<>( database, database.shoppingLists() ), 
                        shoppingListFileLocation,
                        setup::configureDefaultShoppingList
               )
               .withMarshallerFor( 
                        new StockPersistence( database ), 
                        stockListFileLocation,
                        setup::configureDefaultStockListAndConnect
               )
               .withTemplates( templateFileLocation )
               .withMarshallerFor( new DayPlanPersistence( database ), 
                        dayPlanFileLocation,
                        setup::configureDefaultDayPlans
               )
               .withMarshallerFor( 
                        new CalorieGoalPersistence( database.calorieGoals() ), 
                        legacyGoalFileLocation, 
                        FileIoProtocol.ReadOnly 
               )
               .withCalorieGoals( calorieGoalsLocation )
               .withMarshallerFor(
                        new ProportionGoalPersistence( database.proportionGoals() ), 
                        proportionGoalsFileLocation,
                        setup::configureDefaultGoal
               )
               .withWeightRecordings( weightRecordingLocation )
               .withLabels( labelFileLocation )
               .withProgressSeries( progressSeriesFileLocation )
               ;  
   }//End Constructor
   
   private static JarJsonPersistingProtocol protocolFor( String fileName ) {
      return new JarJsonPersistingProtocol( 
               FOLDER_NAME, fileName, Nuts.class 
      );
   }//End Method
   
   public DatabaseIo databaseIo() {
      return databaseIo;
   }//End Method
   
}//End Class
