package uk.dangrew.nuts.graphics.tools.dryweight;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class DryWeightToolTest {

   private DryWeightProperties properties;
   @Spy private DryWeightConverter converter;
   private DryWeightTool systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      properties = new DryWeightProperties();
      systemUnderTest = new DryWeightTool( properties, converter );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.properties(), is( properties ) );
   }//End Method
   
   @Test public void shouldAssociateWithConverter() {
      verify( converter ).associate( properties );
   }//End Method

}//End Class
