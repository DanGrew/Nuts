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
import uk.dangrew.jupa.json.marshall.ModelMarshaller;
import uk.dangrew.nuts.food.FoodStore;
import uk.dangrew.nuts.main.Nuts;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.persistence.goal.GoalPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.setup.DataSetup;
import uk.dangrew.nuts.persistence.weighins.WeightRecordingPersistence;
import uk.dangrew.nuts.store.Database;

/**
 * The {@link FoodSessions} provides the {@link SessionManager} for saving and loading {@link uk.dangrew.nuts.food.FoodItem}s.
 */
public class FoodSessions {

   static final String FOLDER_NAME = "uk.dangrew.nuts";
   static final String FOOD_ITEM_FILE_NAME = "food-items.json";
   static final String MEAL_FILE_NAME = "meals.json";
   static final String TEMPLATE_FILE_NAME = "plans.json";
   static final String SHOPPING_LISTS_FILE_NAME = "shoppingLists.json";
   static final String WEIGHT_RECORDING_FILE_NAME = "weightRecordings.json";
   static final String GOALS_FILE_NAME = "goals.json";
   
   static final String LEGACY_GOAL_FILE_NAME = "goal.json";
   
   private final Database database;
   private final DataSetup setup;
   
   private final JarJsonPersistingProtocol foodItemFileLocation;
   private final JarJsonPersistingProtocol mealFileLocation;
   private final JarJsonPersistingProtocol templateFileLocation;
   private final JarJsonPersistingProtocol weightRecordingLocation;
   private final JarJsonPersistingProtocol goalsLocation;
   
   private final ModelMarshaller foodItemMarshaller;
   private final ModelMarshaller mealMarshaller;
   private final ModelMarshaller templatesMarshaller;
   private final ModelMarshaller shoppingListMarshaller;
   private final ModelMarshaller weightRecordingMarshaller;
   private final ModelMarshaller goalMarshaller;
   
   private final ModelMarshaller legacyGoalMarshaller;
   
   /**
    * Constructs a new {@link FoodSessions}.
    * @param database the {@link Database}.
    */
   public FoodSessions( 
            Database database 
   ) {
      this( 
               database, 
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, FOOD_ITEM_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, MEAL_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, TEMPLATE_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, SHOPPING_LISTS_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, WEIGHT_RECORDING_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, GOALS_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, LEGACY_GOAL_FILE_NAME, Nuts.class 
               )
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link BuildWallConfigurationSessions}.
    * @param database the {@link Database}.
    * @param foodItemFileLocation the {@link JarJsonPersistingProtocol}.
    * @param mealFileLocation the {@link JarJsonPersistingProtocol}.
    * @param planFileLocation the {@link JarJsonPersistingProtocol}.
    * @param shoppingListFileLocation the {@link JarJsonPersistingProtocol}.
    * @param weightRecordingFileLocation the {@link JarJsonPersistingProtocol}.
    * @param goalsFileLocation the {@link JarJsonPersistingProtocol}.
    * @param legacyGoalFileLocation the {@link JarJsonPersistingProtocol}.
    */
   FoodSessions( 
            Database database, 
            JarJsonPersistingProtocol foodItemFileLocation,
            JarJsonPersistingProtocol mealFileLocation,
            JarJsonPersistingProtocol planFileLocation,
            JarJsonPersistingProtocol shoppingListFileLocation,
            JarJsonPersistingProtocol weightRecordingFileLocation,
            JarJsonPersistingProtocol goalsFileLocation,
            JarJsonPersistingProtocol legacyGoalFileLocation
   ) {
      this.database = database;
      this.setup = new DataSetup( database );
      
      this.foodItemFileLocation = foodItemFileLocation;
      this.mealFileLocation = mealFileLocation;
      this.templateFileLocation = planFileLocation;
      this.weightRecordingLocation = weightRecordingFileLocation;
      this.goalsLocation = goalsFileLocation;
      
      this.foodItemMarshaller = constructFoodItemMarshaller();
      this.mealMarshaller = constructMealMarshaller( database.meals(), mealFileLocation );
      this.templatesMarshaller = constructMealMarshaller( database.templates(), planFileLocation );
      this.shoppingListMarshaller = constructMealMarshaller( database.shoppingLists(), shoppingListFileLocation );
      this.weightRecordingMarshaller = constructWeightRecordingMarshaller();
      this.goalMarshaller = constructGoalMarshaller( goalsFileLocation );
      
      this.legacyGoalMarshaller = constructGoalMarshaller( legacyGoalFileLocation );
   }//End Constructor
   
   /**
    * Method to construct the {@link ModelMarshaller}.
    * @return the {@link ModelMarshaller} constructed for {@link uk.dangrew.nuts.food.FoodItem}s.
    */
   private ModelMarshaller constructFoodItemMarshaller(){
      FoodItemPersistence persistence = new FoodItemPersistence( database );
      return new ModelMarshaller( 
               persistence.structure(), 
               persistence.readHandles(), 
               persistence.writeHandles(), 
               foodItemFileLocation 
      );
   }//End Method
   
   /**
    * Method to construct the {@link ModelMarshaller}.
    * @param store the {@link FoodStore} to access data for {@link Meal} types.
    * @param fileLocation the {@link JarJsonPersistingProtocol}.
    * @return the {@link ModelMarshaller} constructed for {@link uk.dangrew.nuts.meal.Meal}s.
    */
   private < FoodTypeT extends Meal > ModelMarshaller constructMealMarshaller( FoodStore< FoodTypeT > store, JarJsonPersistingProtocol fileLocation ){
      MealPersistence< FoodTypeT > persistence = new MealPersistence<>( database, store );
      return new ModelMarshaller( 
               persistence.structure(), 
               persistence.readHandles(), 
               persistence.writeHandles(), 
               fileLocation 
      );
   }//End Method
   
   /**
    * Method to construct the {@link ModelMarshaller}.
    * @return the {@link ModelMarshaller} constructed for {@link uk.dangrew.nuts.progress.WeightRecording}s.
    */
   private ModelMarshaller constructWeightRecordingMarshaller(){
      WeightRecordingPersistence persistence = new WeightRecordingPersistence( database );
      return new ModelMarshaller( 
               persistence.structure(), 
               persistence.readHandles(), 
               persistence.writeHandles(), 
               weightRecordingLocation 
      );
   }//End Method
   
   /**
    * Method to construct the {@link ModelMarshaller}.
    * @param protocol the {@link JarJsonPersistingProtocol}.
    * @return the {@link ModelMarshaller} constructed for the {@link uk.dangrew.nuts.goal.Goal}.
    */
   private ModelMarshaller constructGoalMarshaller( JarJsonPersistingProtocol protocol ){
      GoalPersistence persistence = new GoalPersistence( database.goals() );
      return new ModelMarshaller( 
               persistence.structure(), 
               persistence.readHandles(), 
               persistence.writeHandles(), 
               protocol 
      );
   }//End Method

   /**
    * Method to read all {@link uk.dangrew.nuts.food.FoodItem}s and {@link uk.dangrew.nuts.meal.Meal}s
    * from known files.
    */
   public void read() {
      legacyGoalMarshaller.read();
      goalMarshaller.read();
      setup.configureDefaultGoal();
      
      foodItemMarshaller.read();
      mealMarshaller.read();
      templatesMarshaller.read();
      
      shoppingListMarshaller.read();
      setup.configureDefaultShoppingList();
      
      weightRecordingMarshaller.read();
   }// End Method

   /**
    * Method to write all {@link uk.dangrew.nuts.food.FoodItem}s and {@link uk.dangrew.nuts.meal.Meal}s
    * to known files.
    */
   public void write() {
      foodItemMarshaller.write();
      mealMarshaller.write();
      templatesMarshaller.write();
      shoppingListMarshaller.write();
      weightRecordingMarshaller.write();
      goalMarshaller.write();
   }// End Method
}//End Class
