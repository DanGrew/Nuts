package uk.dangrew.nuts.graphics.selection.tiles;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.selection.tiles.UiFoodTileConceptTitle;

public class UiFoodTileConceptTitleTest {

   private Food food;
   private UiFoodTileConceptTitle systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      food = new FoodItem( "Food Item" );
      systemUnderTest = new UiFoodTileConceptTitle( food );
   }//End Method

   @Test public void shouldUpdateTextWhenNameChanges() {
      assertThat( systemUnderTest.label().getText(), is( food.properties().nameProperty().get() ) );
      food.properties().nameProperty().set( "anything" );
      assertThat( systemUnderTest.label().getText(), is( food.properties().nameProperty().get() ) );
   }//End Method
   
   @Test public void shouldDetachFromListening() {
      assertThat( systemUnderTest.label().getText(), is( food.properties().nameProperty().get() ) );
      systemUnderTest.detach();
      food.properties().nameProperty().set( "anything" );
      assertThat( systemUnderTest.label().getText(), is( "Food Item" ) );
   }//End Method

}//End Class
