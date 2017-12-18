package uk.dangrew.nuts.cycle;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.cycle.alternating.AlternatingCycle;
import uk.dangrew.nuts.cycle.alternating.AlternatingCycleStore;

public class CycleStoreTest {

   private AlternatingCycleStore alternatingCycles;
   private CycleStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      alternatingCycles = new AlternatingCycleStore();
      systemUnderTest = new CycleStore( alternatingCycles );
   }//End Method

   @Test public void shouldProvideAlternatingCycles() {
      assertThat( systemUnderTest.alternatingCycleStore(), is( alternatingCycles ) );
   }//End Method
   
   @Test public void shouldProvideCombinedCycles(){
      AlternatingCycle first = alternatingCycles.createConcept( "First" );
      alternatingCycles.createConcept( "Second" );
      alternatingCycles.createConcept( "Third" );
      alternatingCycles.objectList().forEach( i -> assertThat( systemUnderTest.objectList().contains( i ), is( true ) ) );
      
      alternatingCycles.createConcept( "Fourth" );
      alternatingCycles.objectList().forEach( i -> assertThat( systemUnderTest.objectList().contains( i ), is( true ) ) );
      
      alternatingCycles.removeConcept( first );
      alternatingCycles.objectList().forEach( i -> assertThat( systemUnderTest.objectList().contains( i ), is( true ) ) );
   }//End Method
   
   @Test( expected = UnsupportedOperationException.class ) public void shouldNotCreateNewConceptWithName(){
      systemUnderTest.createConcept( "name" );
   }//End Method
   
   @Test( expected = UnsupportedOperationException.class ) public void shouldNotCreateNewConceptWithNameAndId(){
      systemUnderTest.createConcept( "id", "name" );
   }//End Method
   
   @Test( expected = UnsupportedOperationException.class ) public void shouldNotStore(){
      systemUnderTest.store( new AlternatingCycle( "cycle" ) );
   }//End Method
   
   @Test public void shouldGetFromId(){
      AlternatingCycle cycle = alternatingCycles.createConcept( "First" );
      assertThat( systemUnderTest.get( cycle.properties().id() ), is( cycle ) );
   }//End Method
   
   @Test( expected = UnsupportedOperationException.class ) public void shouldNotRemove(){
      systemUnderTest.removeConcept( new AlternatingCycle( "cycle" ) );
   }//End Method

}//End Class
