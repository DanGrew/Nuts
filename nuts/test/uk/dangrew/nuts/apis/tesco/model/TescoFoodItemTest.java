package uk.dangrew.nuts.apis.tesco.model;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.model.TescoFoodDescription;
import uk.dangrew.nuts.apis.tesco.model.TescoFoodItem;
import uk.dangrew.nuts.food.FoodItem;

public class TescoFoodItemTest {

   private static final String ID = "ksjdbvlajsd";
   private static final String NAME = "mlsdhjblqwb";
   private TescoFoodItem systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TescoFoodItem( ID, NAME );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.properties().id(), is( ID ) );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( NAME ) );
   }//End Method
   
   @Test public void shouldProvideTescoDescription() {
      assertThat( systemUnderTest.description().get(), is( nullValue() ) );
      TescoFoodDescription description = new TescoFoodDescription( "Anything" );
      systemUnderTest.description().set( description );
      assertThat( systemUnderTest.description().get(), is( description ) );
   }//End Method
   
   @Test public void shouldProvideNutsFoodItem() {
      assertThat( systemUnderTest.foodItem().get(), is( nullValue() ) );
      FoodItem item = new FoodItem( "Anything" );
      systemUnderTest.foodItem().set( item );
      assertThat( systemUnderTest.foodItem().get(), is( item ) );
   }//End Method

}//End Class
