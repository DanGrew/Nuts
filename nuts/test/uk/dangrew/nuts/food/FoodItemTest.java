package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class FoodItemTest {

   private FoodProperties properties;
   private FoodItem systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      properties = new FoodProperties( "anything" );
      systemUnderTest = new FoodItem( properties );
   }//End Method
   
   @Test public void shouldCreateWithId(){
      systemUnderTest = new FoodItem( "3487653", "skdjnvs." );
      assertThat( systemUnderTest.properties().id(), is( "3487653" ) );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( "skdjnvs." ) );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.properties(), is( properties ) );
   }//End Method

}//End Class
