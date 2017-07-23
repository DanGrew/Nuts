package uk.dangrew.nuts.table.food;

import org.junit.Ignore;
import org.junit.Test;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.nutrients.MacroNutrient;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.table.manager.FoodManagerPane;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class FoodTableTest {

   private Database database;
   private FoodTable< FoodItem > table;
   
   @Ignore
   @Test public void manual() throws InterruptedException {
      database = new Database();
      
      Food food1 = addNewFood( "Egg", 0, 8, 9 );
      Food food2 = addNewFood( "Oats So Simple", 26.3, 2.2, 3.2 );
      Food food3 = addNewFood( "Apple", 31.4, 0.2, 1.0 );
      Food food4 = addNewFood( "Banana Carb Killa", 14.8, 8.3, 22.7 );
      Food food5 = addNewFood( "Belvita Duos", 34, 7.6, 3.6 );
      Food food6 = addNewFood( "Cookies & Cream Carb Killa", 13.6, 8.2, 23.3 );
      Food food7 = addNewFood( "Graze Protein Bite", 14, 8, 4.6 );
      Food food8 = addNewFood( "Wholemeal Bread (4 Slices)", 60.4, 2.8, 16 );
      Food food9 = addNewFood( "Tuna & Sweetcorn (62.5g)", 4.1, 8.2, 6.2 );
      Food food10 = addNewFood( "Grapes", 21.6, 0.1, 0.6 );
      Food food11 = addNewFood( "Brazil Nuts (15g)", 1.6, 10.2, 2.1 );
      Food food12 = addNewFood( "Walnut Halves (20g)", 0.7, 13.7, 3 );
      Food food13 = addNewFood( "Salmon (130g)", 0.6, 21.1, 28.9 );
      Food food14 = addNewFood( "Brown Basmati (125g)", 26, 1.4, 4.5 );
      Food food15 = addNewFood( "90c Squares", 11.9, 2.8, 1.0 );
      Food food16 = addNewFood( "Yoghurt (Onken)", 3.4, 3.7, 3.7 );
      Food food17 = addNewFood( "Graze Banana Flapjack", 26, 14, 8 );
      Food food18 = addNewFood( "Summer Berry Flapjack", 31, 11, 3 );
      Food food19 = addNewFood( "Lean Mince (166g)", 0, 7.1, 33.9 );
      Food food20 = addNewFood( "Spaghetti (75g dry)", 52.5, 1.0, 8.9 );
      Food food21 = addNewFood( "Dolmios Sauce (166g)", 10.4, 0.1, 2.5 );
      Food food22 = addNewFood( "Banana", 23, 0.3, 1 );
      
      addNewMeal( "Breakfast", new FoodPortion( food1, 100 ), new FoodPortion( food2, 100 ), new FoodPortion( food3, 50 ) );
      addNewMeal( "Lunch", new FoodPortion( food8, 100 ), new FoodPortion( food9, 100 ), new FoodPortion( food10, 100 ) );
      
      TestApplication.launch( () -> new BorderPane( new FoodManagerPane( database ) ) );
      
      Thread.sleep( 1000000 );
   }//End Method
   
   private Food addNewFood( String name, double c, double f, double p ) {
      FoodItem foodItem = database.foodItems().createFood( name );
      foodItem.properties().nutritionFor( MacroNutrient.Carbohydrates ).set( c );
      foodItem.properties().nutritionFor( MacroNutrient.Fats ).set( f );
      foodItem.properties().nutritionFor( MacroNutrient.Protein ).set( p );
      return foodItem;
   }//End Method
   
   private void addNewMeal( String name, FoodPortion... portions ) {
      Meal meal = database.meals().createFood( name );
      meal.portions().addAll( portions );
   }//End Method
   
}//End Class
