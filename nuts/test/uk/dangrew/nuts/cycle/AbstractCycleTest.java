package uk.dangrew.nuts.cycle;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.system.Properties;

public class AbstractCycleTest {

   private Goal baseGoal;
   private Properties properties;
   private AbstractCycle systemUnderTest;
   
   private static class TestAbstractCycle extends AbstractCycle {

      protected TestAbstractCycle( Properties properties ) {
         super( properties );
      }//End Constructor
      
      @Override public CycleType type() {
         return null;
      }//End Method

      @Override public AbstractCycle duplicate( String referenceId ) {
         AbstractCycle duplicate = new TestAbstractCycle( new Properties( properties().nameProperty().get() + referenceId ) );
         super.duplicate( duplicate );
         return duplicate;
      }//End Method
      
   }//End Class

   @Before public void initialiseSystemUnderTest() {
      baseGoal = new Goal( "goal" );
      properties = new Properties( "id", "name" );
      systemUnderTest = new TestAbstractCycle( properties );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.properties(), is( properties ) );
   }//End Method
   
   @Test public void shouldProvideBaseGoal() {
      assertThat( systemUnderTest.baseGoal(), is( nullValue() ) );
      systemUnderTest.setBaseGoal( baseGoal );
      assertThat( systemUnderTest.baseGoal(), is( baseGoal ) );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleBaseGoalSet() {
      systemUnderTest.setBaseGoal( baseGoal );
      systemUnderTest.setBaseGoal( baseGoal );
   }//End Method
   
   @Test public void shouldDuplicateWithBaseGoal(){
      systemUnderTest.setBaseGoal( baseGoal );
      assertDuplicatedCorrectly();
   }//End Method
   
   @Test public void shouldDuplicateWithoutBaseGoal(){
      assertDuplicatedCorrectly();
   }//End Method
   
   private void assertDuplicatedCorrectly(){
      AbstractCycle duplicate = systemUnderTest.duplicate( "-copy" );
      assertThat( duplicate.properties().nameProperty().get(), is( "name-copy" ) );
      assertThat( duplicate.properties().id(), is( not( "id" ) ) );
      assertThat( duplicate.baseGoal(), is( systemUnderTest.baseGoal() ) );
   }//End Method
   
   @Test public void shouldProvidePropertyForOrderingOfGoals(){
      assertThat( systemUnderTest.approach().get(), is( AbstractCycle.DEFAULT_APPROACH ) );
      systemUnderTest.approach().set( CycleApproach.HighThenLow );
      assertThat( systemUnderTest.approach().get(), is( CycleApproach.HighThenLow ) );
   }//End Method

}//End Class
