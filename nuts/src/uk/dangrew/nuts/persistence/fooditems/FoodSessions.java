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
import uk.dangrew.nuts.meal.MealStore;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.store.Database;

/**
 * The {@link FoodSessions} provides the {@link SessionManager} for saving and loading {@link uk.dangrew.nuts.food.FoodItem}s.
 */
public class FoodSessions {

   static final String FOLDER_NAME = "uk.dangrew.nuts";
   static final String FOOD_ITEM_FILE_NAME = "food-items.json";
   static final String MEAL_FILE_NAME = "meals.json";
   static final String PLAN_FILE_NAME = "plans.json";
   
   private final Database database;
   private final JarJsonPersistingProtocol foodItemFileLocation;
   private final JarJsonPersistingProtocol mealFileLocation;
   private final JarJsonPersistingProtocol planFileLocation;
   
   private final ModelMarshaller foodItemMarshaller;
   private final ModelMarshaller mealMarshaller;
   private final ModelMarshaller planMarshaller;
   
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
                        FOLDER_NAME, PLAN_FILE_NAME, Nuts.class 
               )
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link BuildWallConfigurationSessions}.
    * @param database the {@link Database}.
    * @param foodItemFileLocation the {@link JarJsonPersistingProtocol}.
    * @param mealFileLocation the {@link JarJsonPersistingProtocol}.
    */
   FoodSessions( 
            Database database, 
            JarJsonPersistingProtocol foodItemFileLocation,
            JarJsonPersistingProtocol mealFileLocation,
            JarJsonPersistingProtocol planFileLocation
   ) {
      this.database = database;
      this.foodItemFileLocation = foodItemFileLocation;
      this.mealFileLocation = mealFileLocation;
      this.planFileLocation = planFileLocation;
      
      this.foodItemMarshaller = constructFoodItemMarshaller();
      this.mealMarshaller = constructMealMarshaller( database.meals(), mealFileLocation );
      this.planMarshaller = constructMealMarshaller( database.plans(), planFileLocation );
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
    * @param store the {@link MealStore} to access data.
    * @param fileLocation the {@link JarJsonPersistingProtocol}.
    * @return the {@link ModelMarshaller} constructed for {@link uk.dangrew.nuts.meal.Meal}s.
    */
   private ModelMarshaller constructMealMarshaller( MealStore store, JarJsonPersistingProtocol fileLocation ){
      MealPersistence persistence = new MealPersistence( database, store );
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
      foodItemMarshaller.read();
      mealMarshaller.read();
      planMarshaller.read();
   }// End Method

   /**
    * Method to write all {@link uk.dangrew.nuts.food.FoodItem}s and {@link uk.dangrew.nuts.meal.Meal}s
    * to known files.
    */
   public void write() {
      foodItemMarshaller.write();
      mealMarshaller.write();
      planMarshaller.write();
   }// End Method
}//End Class
