package uk.dangrew.nuts.cycle.alternating;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class AlternatingCycleStoreTest {

   private AlternatingCycle cycle;
   private AlternatingCycleStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      cycle = new AlternatingCycle( "Cycle" );
      systemUnderTest = new AlternatingCycleStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( cycle.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( cycle );
      assertThat( systemUnderTest.get( cycle.properties().id() ), is( cycle ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      AlternatingCycle newFood = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( cycle );
      assertThat( systemUnderTest.get( cycle.properties().id() ), is( cycle ) );
      systemUnderTest.removeConcept( cycle );
      assertThat( systemUnderTest.get( cycle.properties().id() ), is( nullValue() ) );
   }//End Method

}
