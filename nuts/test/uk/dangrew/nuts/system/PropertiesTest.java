package uk.dangrew.nuts.system;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.food.FoodProperties;

public class PropertiesTest {

   private static final String ID = "some id";
   private static final String NAME = "this is a name";
   
   private Properties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new Properties( ID, NAME );
   }//End Method
   
   @Test public void shouldCreateWithId(){
      systemUnderTest = new FoodProperties( "3487653", "skdjnvs." );
      assertThat( systemUnderTest.id(), is( "3487653" ) );
      assertThat( systemUnderTest.nameProperty().get(), is( "skdjnvs." ) );
   }//End Method
   
   @Test public void shouldProvideFoodId(){
      assertThat( systemUnderTest.id(), is( ID ) );
   }//End Method

   @Test public void shouldProvideName(){
      assertThat( systemUnderTest.nameProperty().get(), is( NAME ) );
   }//End Method
   
}//End Class
