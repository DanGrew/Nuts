package uk.dangrew.nuts.system;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.cycle.Cycle;
import uk.dangrew.nuts.cycle.alternating.AlternatingCycle;

public class InstanceVerifierTest {

   private InstanceVerifier< Cycle > systemUnderTest;
   
   private static class CycleExtension extends AlternatingCycle {

      public CycleExtension( String name ) {
         super( name );
      }//End Constructor
      
   }//End Class

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new InstanceVerifier< Cycle >( AlternatingCycle.class );
   }//End Method

   @Test public void shouldAcceptExactObject() {
      systemUnderTest.verify( new AlternatingCycle( "anything" ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptAnythingElse() {
      systemUnderTest.verify( mock( Cycle.class ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptExtension() {
      systemUnderTest.verify( new CycleExtension( "anything" ) );
   }//End Method

}//End Class
