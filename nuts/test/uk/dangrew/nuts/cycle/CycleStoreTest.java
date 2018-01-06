package uk.dangrew.nuts.cycle;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class CycleStoreTest {

   private Cycle cycle;
   private CycleStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      cycle = new Cycle( "Food" );
      systemUnderTest = new CycleStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( cycle.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( cycle );
      assertThat( systemUnderTest.get( cycle.properties().id() ), is( cycle ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      Cycle newCycle = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newCycle.properties().id() ), is( newCycle ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( cycle );
      assertThat( systemUnderTest.get( cycle.properties().id() ), is( cycle ) );
      systemUnderTest.removeConcept( cycle );
      assertThat( systemUnderTest.get( cycle.properties().id() ), is( nullValue() ) );
   }//End Method

}//End Class
