package uk.dangrew.nuts.graphics.table.tree;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.view.FoodSelectionWindow;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;

public class DayPlanTreeTableTest {

   private MealTreePane pane;
   private DayPlanTreeTable systemUnderTest; 
   
   private FoodItem item1;
   private FoodItem item2;
   private FoodItem item3;
   private FoodItem item4;
   
   private Meal subMeal1;
   private Meal subMeal2;
   
   private DayPlan focus;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      
      Database database = new Database();
      database.stockLists().createConcept( "" );
      PlatformImpl.runAndWait( () -> new FoodSelectionWindow( database ) );
      
      item1 = database.foodItems().createConcept( "Item1" );
      item1.nutrition().of( NutritionalUnit.Calories ).set( 101.0 );
      item1.nutrition().setMacroNutrients( 23, 4, 78 );
      item2 = database.foodItems().createConcept( "Item2" );
      item2.nutrition().of( NutritionalUnit.Calories ).set( 78.0 );
      item2.nutrition().setMacroNutrients( 0.1, 7.0, 5.6 );
      item3 = database.foodItems().createConcept( "Item3" );
      item3.nutrition().of( NutritionalUnit.Calories ).set( 140.0 );
      item3.nutrition().setMacroNutrients( 0, 15, 1 );
      item4 = database.foodItems().createConcept( "Item4" );
      item4.nutrition().of( NutritionalUnit.Calories ).set( 198.0 );
      item4.nutrition().setMacroNutrients( 4, 12, 3 );
      
      subMeal1 = database.meals().createConcept( "SubMeal1" );
      subMeal1.portions().addAll( 
               new FoodPortion( item1, 100 ),
               new FoodPortion( item2, 125 )
      );
      
      subMeal2 = database.meals().createConcept( "SubMeal2" );
      subMeal2.portions().addAll( 
               new FoodPortion( item2, 240 ),
               new FoodPortion( item3, 250 )
      );
      
      focus = database.dayPlans().createConcept( "Focus" );
      focus.portions().addAll( 
               new FoodPortion( subMeal1, 100 ),
               new FoodPortion( subMeal2, 100 )
      );
   }//End Method
   
   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> pane = new MealTreePane() );
      
      pane.controller().showMeal( focus );
      
      Thread.sleep( 9999999 );
   }//End Method

}//End Class
