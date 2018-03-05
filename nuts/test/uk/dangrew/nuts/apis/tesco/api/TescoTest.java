package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoTest {

   @Mock private TescoApiController controller;
   private Tesco systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new Tesco( controller );
   }//End Method

   @Ignore
   @Test public void manual(){
      //over 2000 results
      new Tesco().search( "chi" ).forEach( d -> System.out.println( d.groceryProperties().name().get() ) );
   }//End Method
   
   @Test public void shouldProvideFoodDescriptionsForSearch() {
      List< TescoFoodDescription > first = Arrays.asList( new TescoFoodDescription( "a" ) );
      List< TescoFoodDescription > second = Arrays.asList( new TescoFoodDescription( "b" ) );
      List< TescoFoodDescription > third = Arrays.asList( new TescoFoodDescription( "c" ) );
      List< TescoFoodDescription > fourth = Arrays.asList( new TescoFoodDescription( "d" ) );
      
      when( controller.search( Mockito.any() ) ).thenReturn( 
               first, second, third, fourth
      );
      
      assertThat( systemUnderTest.search( "coconut" ), is( first ) );
      assertThat( systemUnderTest.search( "anything" ), is( second ) );
      assertThat( systemUnderTest.search( "coconut" ), is( third ) );
      assertThat( systemUnderTest.search( "coconut" ), is( fourth ) );
   }//End Method
   
   @Ignore
   @Test public void shouldProvideFoodItemsDescription() {
      fail( "Not yet implemented" );
   }//End Method

}//End Class
