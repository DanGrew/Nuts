package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

public class UiFoodTilePortionControlTest {

   private FoodPortion portion;
   private UiFoodTilePortionControl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      portion = new FoodPortion( new FoodItem( "Food Item" ), 123 );
      systemUnderTest = new UiFoodTilePortionControl( portion );
   }//End Method

   @Test public void shouldUpdateTextWhenPortionChanges() {
      assertThat( systemUnderTest.portionField().getText(), is( "123" ) );
      portion.setPortion( 34.2 );
      assertThat( systemUnderTest.portionField().getText(), is( "34.2" ) );
   }//End Method
   
   @Test public void shouldDetach() {
      assertThat( systemUnderTest.portionField().getText(), is( "123" ) );
      systemUnderTest.detach();
      portion.setPortion( 34.2 );
      assertThat( systemUnderTest.portionField().getText(), is( "123" ) );
   }//End Method

}//End Class
