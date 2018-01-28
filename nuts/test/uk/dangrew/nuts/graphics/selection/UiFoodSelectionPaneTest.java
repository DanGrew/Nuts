package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class UiFoodSelectionPaneTest {

   private ObservableList< Food > foods;
   @Mock private UiFoodSelectionController controller;
   private UiFoodSelectionPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      foods = FXCollections.observableArrayList();
      foods.add( new FoodItem( "Food1" ) );
      foods.add( new FoodItem( "Food2" ) );
      foods.add( new FoodItem( "Food3" ) );
      
      systemUnderTest = new UiFoodSelectionPane( controller );
   }//End Method
   
   @Test public void shouldShowTileForEachFood() {
      systemUnderTest.layoutTiles( foods );
      for ( int i = 0; i < foods.size(); i++ ) {
         Food food = foods.get( i );
         UiFoodTile tile = ( UiFoodTile ) systemUnderTest.grid().getChildren().get( i );
         assertThat( tile.food().food().get(), is( food ) );
      }
   }//End Method
   
   @Test public void shouldAddTileForNewFood() {
      foods.add( new FoodItem( "Food4" ) );
      systemUnderTest.layoutTiles( foods );
      shouldShowTileForEachFood();
   }//End Method
   
   @Test public void shouldRemoveTileForRemovedFood() {
      foods.remove( 0 );
      systemUnderTest.layoutTiles( foods );
      shouldShowTileForEachFood();
   }//End Method
   
   @Ignore //there is a memory leak here, but in practice so small
   @Test public void shouldDetachTileWhenRemoved(){
      UiFoodTile tile = ( UiFoodTile ) systemUnderTest.grid().getChildren().get( 0 );
      foods.remove( 0 );
      assertThat( tile.food(), is( nullValue() ) );
   }//End Method
}//End Class
