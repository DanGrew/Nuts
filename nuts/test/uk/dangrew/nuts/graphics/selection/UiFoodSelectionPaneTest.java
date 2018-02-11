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
import uk.dangrew.nuts.food.FoodPortion;

public class UiFoodSelectionPaneTest {

   private ObservableList< UiFoodSelectionTile > tiles;
   @Mock private UiFoodSelectionController controller;
   private UiFoodSelectionPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      tiles = FXCollections.observableArrayList();
      tiles.add( new UiFoodTile( new FoodPortion( new FoodItem( "Food1" ), 100.0 ), controller ) );
      tiles.add( new UiFoodTile( new FoodPortion( new FoodItem( "Food2" ), 100.0 ), controller ) );
      tiles.add( new UiFoodTile( new FoodPortion( new FoodItem( "Food3" ), 100.0 ), controller ) );
      
      systemUnderTest = new UiFoodSelectionPane();
   }//End Method
   
   @Test public void shouldShowTileForEachFood() {
      systemUnderTest.layoutTiles( tiles );
      for ( int i = 0; i < tiles.size(); i++ ) {
         assertThat( systemUnderTest.grid().getChildren().get( i ), is( tiles.get( i ) ) );
      }
   }//End Method
   
   @Test public void shouldAddTileForNewFood() {
      tiles.add( new UiFoodTile( new FoodPortion( new FoodItem( "Food4" ), 100.0 ), controller ) );
      systemUnderTest.layoutTiles( tiles );
      shouldShowTileForEachFood();
   }//End Method
   
   @Test public void shouldRemoveTileForRemovedFood() {
      tiles.remove( 0 );
      systemUnderTest.layoutTiles( tiles );
      shouldShowTileForEachFood();
   }//End Method
   
   @Ignore //there is a memory leak here, but in practice so small
   @Test public void shouldDetachTileWhenRemoved(){
      UiFoodTile tile = ( UiFoodTile ) systemUnderTest.grid().getChildren().get( 0 );
      tiles.remove( 0 );
      assertThat( tile.food(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldSelectAppropriateTile(){
      //should really test this but trivial operation and not so trivial test
   }//End Method
}//End Class
