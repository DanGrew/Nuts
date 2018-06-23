package uk.dangrew.nuts.graphics.selection.view;

import org.junit.Ignore;
import org.junit.Test;

import com.sun.javafx.application.PlatformImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.event.structure.Event;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionForMealEvent;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionForTemplateEvent;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class FoodSelectionWindowTest {

   private FoodSelectionWindow window;
   
   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.startPlatform();
      
      ObservableList< Food > foods = FXCollections.observableArrayList();
      
      Database sample = new Database();
      DataLocation.loadSampleFoodData( sample );
      foods.addAll( sample.foodItems().objectList() );
      Stock stock = sample.stockLists().createConcept( "Stock" );
      stock.linkWithFoodItems( sample.foodItems() );
      for ( int i = 0; i < 10; i++ ) {
         stock.portionFor( sample.foodItems().objectList().get( i ) ).setPortion( 100 );
      }
      
      PlatformImpl.runAndWait( () -> {
         window = new FoodSelectionWindow( sample );
      } );
   
      Meal meal = new Meal( "Test Meal" );
      meal.portions().add( new FoodPortion( sample.foodItems().objectList().get( 3 ), 100.0 ) );
      meal.portions().add( new FoodPortion( sample.foodItems().objectList().get( 5 ), 125.0 ) );
      new FoodSelectionForMealEvent().fire( new Event<>( meal ) );
      
      Template template = new Template( "Test Template" );
      template.portions().add( new FoodPortion( meal, 100 ) );
      CalorieGoal calorieGoal = new CalorieGoalImpl( "Template Goal" );
      DataLocation.configureExampleGoal( calorieGoal );
      template.goalAnalytics().goal().set( calorieGoal );
      new FoodSelectionForTemplateEvent().fire( new Event<>( template ) );
      
      Thread.sleep( 9999999 );
   }//End Method

}
