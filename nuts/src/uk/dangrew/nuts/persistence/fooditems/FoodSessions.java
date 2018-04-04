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
import uk.dangrew.nuts.main.Nuts;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.persistence.dayplan.DayPlanPersistence;
import uk.dangrew.nuts.persistence.goal.calorie.CalorieGoalPersistence;
import uk.dangrew.nuts.persistence.goal.proportion.ProportionGoalPersistence;
import uk.dangrew.nuts.persistence.labels.LabelPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.setup.DataSetup;
import uk.dangrew.nuts.persistence.template.TemplatePersistence;
import uk.dangrew.nuts.persistence.weighins.WeightRecordingPersistence;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;
import uk.dangrew.nuts.template.Template;

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
   
   static final String LEGACY_GOAL_FILE_NAME = "goal.json";
   
   private final Database database;
   private final DataSetup setup;
   
   private final JarJsonPersistingProtocol foodItemFileLocation;
   private final JarJsonPersistingProtocol mealFileLocation;
   private final JarJsonPersistingProtocol templateFileLocation;
   private final JarJsonPersistingProtocol weightRecordingLocation;
   private final JarJsonPersistingProtocol calorieGoalsLocation;
   
   private final ModelMarshaller foodItemMarshaller;
   private final ModelMarshaller mealMarshaller;
   private final ModelMarshaller templatesMarshaller;
   private final ModelMarshaller dayPlansMarshaller;
   private final ModelMarshaller shoppingListMarshaller;
   private final ModelMarshaller stockListMarshaller;
   private final ModelMarshaller weightRecordingMarshaller;
   private final ModelMarshaller calorieGoalMarshaller;
   private final ModelMarshaller proportionGoalMarshaller;
   private final ModelMarshaller labelMarshaller;
   
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
                        FOLDER_NAME, DAY_PLANS_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, SHOPPING_LISTS_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, STOCK_LISTS_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, WEIGHT_RECORDING_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, CALORIE_GOALS_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, PROPORTION_GOALS_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, LEGACY_GOAL_FILE_NAME, Nuts.class 
               ),
               new JarJsonPersistingProtocol( 
                        FOLDER_NAME, LABEL_FILE_NAME, Nuts.class 
               )
      );
   }//End Constructor
   
   FoodSessions( 
            Database database, 
            JarJsonPersistingProtocol foodItemFileLocation,
            JarJsonPersistingProtocol mealFileLocation,
            JarJsonPersistingProtocol planFileLocation,
            JarJsonPersistingProtocol dayPlanFileLocation,
            JarJsonPersistingProtocol shoppingListFileLocation,
            JarJsonPersistingProtocol stockListFileLocation,
            JarJsonPersistingProtocol weightRecordingFileLocation,
            JarJsonPersistingProtocol calorieGoalsFileLocation,
            JarJsonPersistingProtocol proportionGoalsFileLocation,
            JarJsonPersistingProtocol legacyGoalFileLocation,
            JarJsonPersistingProtocol labelFileLocation
   ) {
      this.database = database;
      this.setup = new DataSetup( database );
      
      this.foodItemFileLocation = foodItemFileLocation;
      this.mealFileLocation = mealFileLocation;
      this.templateFileLocation = planFileLocation;
      this.weightRecordingLocation = weightRecordingFileLocation;
      this.calorieGoalsLocation = calorieGoalsFileLocation;
      
      this.foodItemMarshaller = constructFoodItemMarshaller();
      this.mealMarshaller = constructMealMarshaller( database.meals(), mealFileLocation );
      this.templatesMarshaller = constructTemplateMarshaller( planFileLocation );
      this.dayPlansMarshaller = constructDayPlanMarshaller( dayPlanFileLocation );
      this.shoppingListMarshaller = constructMealMarshaller( database.shoppingLists(), shoppingListFileLocation );
      this.stockListMarshaller = constructMealMarshaller( database.stockLists(), stockListFileLocation );
      this.weightRecordingMarshaller = constructWeightRecordingMarshaller();
      this.calorieGoalMarshaller = constructCalorieGoalMarshaller( calorieGoalsFileLocation );
      this.proportionGoalMarshaller = constructProportionGoalMarshaller( proportionGoalsFileLocation );
      this.labelMarshaller = constructLabelMarshaller( labelFileLocation );
      
      this.legacyGoalMarshaller = constructCalorieGoalMarshaller( legacyGoalFileLocation );
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
    * @param store the {@link ConceptStore} to access data for {@link Meal} types.
    * @param fileLocation the {@link JarJsonPersistingProtocol}.
    * @return the {@link ModelMarshaller} constructed for {@link uk.dangrew.nuts.meal.Meal}s.
    */
   private < FoodTypeT extends Meal > ModelMarshaller constructMealMarshaller( ConceptStore< FoodTypeT > store, JarJsonPersistingProtocol fileLocation ){
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
    * @param fileLocation the {@link JarJsonPersistingProtocol}.
    * @return the {@link ModelMarshaller} constructed for {@link uk.dangrew.nuts.template.Template}s.
    */
   private ModelMarshaller constructTemplateMarshaller( JarJsonPersistingProtocol fileLocation ){
      TemplatePersistence< Template > persistence = new TemplatePersistence<>( database, database.templates() );
      return new ModelMarshaller( 
               persistence.structure(), 
               persistence.readHandles(), 
               persistence.writeHandles(), 
               fileLocation 
      );
   }//End Method
   
   private ModelMarshaller constructDayPlanMarshaller( JarJsonPersistingProtocol fileLocation ){
      DayPlanPersistence persistence = new DayPlanPersistence( database, database.dayPlans() );
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
   
   private ModelMarshaller constructCalorieGoalMarshaller( JarJsonPersistingProtocol protocol ){
      CalorieGoalPersistence persistence = new CalorieGoalPersistence( database.calorieGoals() );
      return new ModelMarshaller( 
               persistence.structure(), 
               persistence.readHandles(), 
               persistence.writeHandles(), 
               protocol 
      );
   }//End Method
   
   private ModelMarshaller constructProportionGoalMarshaller( JarJsonPersistingProtocol protocol ){
      ProportionGoalPersistence persistence = new ProportionGoalPersistence( database.proportionGoals() );
      return new ModelMarshaller( 
               persistence.structure(), 
               persistence.readHandles(), 
               persistence.writeHandles(), 
               protocol 
      );
   }//End Method
   
   private ModelMarshaller constructLabelMarshaller( JarJsonPersistingProtocol fileLocation ){
      LabelPersistence persistence = new LabelPersistence( database );
      return new ModelMarshaller( 
               persistence.structure(), 
               persistence.readHandles(), 
               persistence.writeHandles(), 
               fileLocation 
      );
   }//End Method

   /**
    * Method to read all {@link uk.dangrew.nuts.food.FoodItem}s and {@link uk.dangrew.nuts.meal.Meal}s
    * from known files.
    */
   public void read() {
      legacyGoalMarshaller.read();
      calorieGoalMarshaller.read();
      proportionGoalMarshaller.read();
      setup.configureDefaultGoal();
      
      foodItemMarshaller.read();
      mealMarshaller.read();
      templatesMarshaller.read();
      
      dayPlansMarshaller.read();
      setup.configureDefaultDayPlans();
      
      shoppingListMarshaller.read();
      setup.configureDefaultShoppingList();
      
      stockListMarshaller.read();
      setup.configureDefaultStockListAndConnect();
      
      labelMarshaller.read();
      
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
      dayPlansMarshaller.write();
      shoppingListMarshaller.write();
      stockListMarshaller.write();
      weightRecordingMarshaller.write();
      calorieGoalMarshaller.write();
      proportionGoalMarshaller.write();
      labelMarshaller.write();
   }// End Method
}//End Class
