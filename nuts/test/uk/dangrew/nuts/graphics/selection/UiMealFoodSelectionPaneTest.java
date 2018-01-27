package uk.dangrew.nuts.graphics.selection;

import org.junit.Ignore;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class UiMealFoodSelectionPaneTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      ObservableList< Food > foods = FXCollections.observableArrayList();
      
      Database sample = new Database();
      DataLocation.loadSampleFoodData( sample );
      foods.addAll( sample.foodItems().objectList() );
      
      TestApplication.launch( () -> new UiMealFoodSelectionPane( new Meal( "Anything" ), sample ) );
      
      Thread.sleep( 9999999 );
   }//End Method
}
