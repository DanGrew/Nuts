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
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.store.Database;

/**
 * The {@link FoodSessions} provides the {@link SessionManager} for saving and loading {@link uk.dangrew.nuts.food.FoodItem}s.
 */
public class FoodSessions {

   static final String FOLDER_NAME = "uk.dangrew.nuts";
   static final String FOOD_ITEM_FILE_NAME = "food-items.json";
   static final String MEAL_FILE_NAME = "meals.json";
   
   private final Database database;
   private final JarJsonPersistingProtocol foodItemFileLocation;
   private final JarJsonPersistingProtocol mealFileLocation;
   
   private final ModelMarshaller foodItemMarshaller;
   private final ModelMarshaller mealMarshaller;
   
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
            JarJsonPersistingProtocol mealFileLocation 
   ) {
      this.database = database;
      this.foodItemFileLocation = foodItemFileLocation;
      this.mealFileLocation = mealFileLocation;
      
      this.foodItemMarshaller = constructFoodItemMarshaller();
      this.mealMarshaller = constructMealMarshaller();
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
    * @return the {@link ModelMarshaller} constructed for {@link uk.dangrew.nuts.meal.Meal}s.
    */
   private ModelMarshaller constructMealMarshaller(){
      MealPersistence persistence = new MealPersistence( database );
      return new ModelMarshaller( 
               persistence.structure(), 
               persistence.readHandles(), 
               persistence.writeHandles(), 
               mealFileLocation 
      );
   }//End Method

   /**
    * Method to read all {@link uk.dangrew.nuts.food.FoodItem}s and {@link uk.dangrew.nuts.meal.Meal}s
    * from known files.
    */
   public void read() {
      foodItemMarshaller.read();
      mealMarshaller.read();
   }// End Method

   /**
    * Method to write all {@link uk.dangrew.nuts.food.FoodItem}s and {@link uk.dangrew.nuts.meal.Meal}s
    * to known files.
    */
   public void write() {
      foodItemMarshaller.write();
      mealMarshaller.write();
   }// End Method
}//End Class
