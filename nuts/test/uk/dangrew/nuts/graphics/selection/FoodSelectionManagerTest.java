package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

public class FoodSelectionManagerTest {

   private FoodItem chicken;
   private FoodItem beans;
   private FoodItem sausages;
   private FoodSelectionManager systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      chicken = new FoodItem( "Chicken" );
      beans = new FoodItem( "Beans" );
      sausages = new FoodItem( "Sausages" );
      systemUnderTest = new FoodSelectionManager();
   }//End Method

   @Test public void shouldProvideSelection(){
      FoodPortion portion = new FoodPortion( chicken, 100 );
      assertThat( systemUnderTest.isSelected( portion ), is( false ) );
      assertThat( systemUnderTest.isSelected( chicken ), is( false ) );
      
      systemUnderTest.select( portion );
      assertThat( systemUnderTest.isSelected( portion ), is( true ) );
      assertThat( systemUnderTest.isSelected( chicken ), is( true ) );

      systemUnderTest.deselect( portion );
      assertThat( systemUnderTest.isSelected( portion ), is( false ) );
      assertThat( systemUnderTest.isSelected( chicken ), is( false ) );
   }//End Method
   
   @Test public void shouldGetAndClearSelection(){
      FoodPortion portion1 = new FoodPortion( chicken, 100 );
      FoodPortion portion2 = new FoodPortion( sausages, 125 );
      FoodPortion portion3 = new FoodPortion( beans, 75 );
      
      systemUnderTest.select( portion1 );
      systemUnderTest.select( portion2 );
      systemUnderTest.select( portion3 );
      
      Set< FoodPortion > selected = systemUnderTest.getAndClearSelection();
      assertThat( selected, contains( portion1, portion2, portion3 ) );
      assertThat( systemUnderTest.isSelected( portion1 ), is( false ) );
      assertThat( systemUnderTest.isSelected( portion2 ), is( false ) );
      assertThat( systemUnderTest.isSelected( portion3 ), is( false ) );
      assertThat( systemUnderTest.getAndClearSelection(), is( empty() ) );
   }//End Method
  
}//End Class
