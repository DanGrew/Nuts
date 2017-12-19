package uk.dangrew.nuts.cycle;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class CycleTypeTest {

   @Test public void shouldProvideMappingToStore() {
      CycleStore store = new CycleStore();
      assertThat( CycleType.Alternating.getStoreFrom( store ), is( store.alternatingCycleStore() ) );
   }//End Method

}//End Class
