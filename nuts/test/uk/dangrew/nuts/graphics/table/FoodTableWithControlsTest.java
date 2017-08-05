package uk.dangrew.nuts.graphics.table;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javafx.scene.control.Label;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.graphics.food.GeneralFoodTable;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class FoodTableWithControlsTest {

   @Spy private JavaFxStyle styling;
   private FoodTable< ? > table;
   private FoodTableWithControls< ? > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      table = new GeneralFoodTable<>( new FoodItemStore( new Goal( "" ) ) );
      systemUnderTest = new FoodTableWithControls<>( styling, "anything", table );
   }//End Method

   @Test public void shouldProvideContent() {
      assertThat( systemUnderTest.content(), is( systemUnderTest.getContent() ) );
   }//End Method
   
   @Test public void shouldProvideTitle() {
      assertThat( systemUnderTest.getText(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldNotBeCollapsible() {
      assertThat( systemUnderTest.isCollapsible(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideTable() {
      assertThat( systemUnderTest.table(), is( notNullValue() ) );
      assertThat( systemUnderTest.content().getCenter(), is( systemUnderTest.table() ) );
   }//End Method
   
   @Test public void shouldProvideControls() {
      assertThat( systemUnderTest.controls(), is( notNullValue() ) );
      assertThat( systemUnderTest.content().getRight(), is( systemUnderTest.controls() ) );
   }//End Method
   
   @Test public void shouldProvideInstructionsWhenEmpty(){
      verify( styling ).createWrappedTextLabel( FoodTableWithControls.NO_CONTENT_INFORMATION );
      Label node = ( Label ) systemUnderTest.table().getPlaceholder();
      assertThat( node.getText(), is( FoodTableWithControls.NO_CONTENT_INFORMATION ) );
   }//End Method
   
   @Test public void shouldMaximizeSize(){
      assertThat( systemUnderTest.getMaxWidth(), is( Double.MAX_VALUE ) );
      assertThat( systemUnderTest.getMaxHeight(), is( Double.MAX_VALUE ) );
   }//End Method

}//End Class
