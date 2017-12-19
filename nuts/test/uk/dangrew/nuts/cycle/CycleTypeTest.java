package uk.dangrew.nuts.cycle;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.cycle.alternating.AlternatingCycle;
import uk.dangrew.nuts.graphics.cycle.alternating.AlternatingCyclePane;

public class CycleTypeTest {

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
   }//End Method
   
   @Test public void shouldProvideMappingToStore() {
      CycleStore store = new CycleStore();
      assertThat( CycleType.Alternating.getStoreFrom( store ), is( store.alternatingCycleStore() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldProvideVerifier(){
      CycleType.Alternating.constructConfigurationPane( mock( Cycle.class ) );
   }//End Method
   
   @Test public void shouldProvidePaneSupplier(){
      assertThat( CycleType.Alternating.constructConfigurationPane( new AlternatingCycle( "anything" ) ), is( instanceOf( AlternatingCyclePane.class ) ) );
   }//End Method

}//End Class
