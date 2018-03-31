package uk.dangrew.nuts.graphics.cycle;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.cycle.Cycle;
import uk.dangrew.nuts.goal.DerivedCalorieGoal;
import uk.dangrew.nuts.goal.CalorieGoalImpl;

public class UiCycleGoalsTableControllerTest {

   @Mock private UiCycleDialogs dialogs;
   
   private Cycle cycle;
   private DerivedCalorieGoal goal1;
   private DerivedCalorieGoal goal2;
   private DerivedCalorieGoal goal3;
   
   private UiCycleGoalsTable table;
   private UiCycleGoalsTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      cycle = new Cycle( "Test Cycle" );
      cycle.goals().add( goal1 = new DerivedCalorieGoal( "Derived1" ) );
      cycle.goals().add( goal2 = new DerivedCalorieGoal( "Derived2" ) );
      cycle.goals().add( goal3 = new DerivedCalorieGoal( "Derived3" ) );
      cycle.setBaseGoal( new CalorieGoalImpl( "Base" ) );
      
      systemUnderTest = new UiCycleGoalsTableController( dialogs );
      table = new UiCycleGoalsTable( systemUnderTest );
      systemUnderTest.associate( table );
   }//End Method

   @Test public void shouldProvideEmptyTableInitially(){
      assertThat( table.getRows(), is( empty() ) );
   }//End Method
   
   @Test public void shouldFocusOnDerivedGoals() {
      systemUnderTest.focusOn( cycle );
      assertThat( table.getRows(), hasSize( 3 ) );
      
      assertThat( table.getRows().get( 0 ).concept(), is( goal1 ) );
      assertThat( table.getRows().get( 1 ).concept(), is( goal2 ) );
      assertThat( table.getRows().get( 2 ).concept(), is( goal3 ) );
   }//End Method
   
   @Test public void shouldNotCreateDerviedGoalWhenNoCycleFocussed(){
      assertThat( systemUnderTest.createConcept(), is( nullValue() ) );
      verify( dialogs ).showNoFocusDialog();
   }//End Method
   
   @Test public void shouldNotCreateDerviedGoalWithNoBaseGoal(){
      systemUnderTest.focusOn( new Cycle( "No Base Cycle" ) );
      assertThat( systemUnderTest.createConcept(), is( nullValue() ) );
      verify( dialogs ).showNoBaseGoalDialog();
   }//End Method
   
   @Test public void shouldCreateDerviedGoalWithBaseGoal(){
      systemUnderTest.focusOn( cycle );
      DerivedCalorieGoal goal = systemUnderTest.createConcept();
      assertThat( goal.baseGoal(), is( cycle.baseGoal() ) );
      assertThat( cycle.goals().contains( goal ), is( true ) );
   }//End Method
   
   @Test public void shouldRemoveSelected(){
      systemUnderTest.focusOn( cycle );
      table.getSelectionModel().select( 1 );
      
      systemUnderTest.removeSelectedConcept();
      assertThat( table.getItems(), hasSize( 2 ) );
      assertThat( cycle.goals(), hasSize( 2 ) );
      
      assertThat( table.getItems().get( 0 ).concept(), is( cycle.goals().get( 0 ) ) );
      assertThat( table.getItems().get( 1 ).concept(), is( cycle.goals().get( 1 ) ) );
   }//End Method
   
   @Test public void shouldNotRemoveWhenNoFocus(){
      systemUnderTest.removeSelectedConcept();
      assertThat( table.getItems(), is( empty() ) );
   }//End Method
   
   @Test public void shouldNotRemoveWhenNotSelected(){
      systemUnderTest.focusOn( cycle );
      systemUnderTest.removeSelectedConcept();
      assertThat( table.getItems(), hasSize( 3 ) );
   }//End Method
   
   @Test public void shouldRespondToChangesInFocussedGoal(){
      systemUnderTest.focusOn( cycle );
      DerivedCalorieGoal extra = new DerivedCalorieGoal( "Extra" );
      
      cycle.goals().add( extra );
      assertThatTableMatchesCycle();
      
      cycle.goals().remove( 0 );
      assertThatTableMatchesCycle();
   }//End Method
   
   private void assertThatTableMatchesCycle(){
      assertThat( cycle.goals().size(), is( table.getItems().size() ) );
      for ( int i = 0; i < cycle.goals().size(); i++ ) {
         assertThat( table.getItems().get( i ).concept(), is( cycle.goals().get( i ) ) );
      }
   }//End Method
   
   @Test public void shouldNotRespondToChangesInFocussedGoalWhenChanged(){
      systemUnderTest.focusOn( cycle );
      systemUnderTest.focusOn( new Cycle( "Different" ) );
      DerivedCalorieGoal extra = new DerivedCalorieGoal( "Extra" );
      
      cycle.goals().add( extra );
      assertThat( table.getItems(), is( empty() ) );
      
      cycle.goals().remove( 0 );
      assertThat( table.getItems(), is( empty() ) );
   }//End Method

   @Test public void shouldDuplicateSelection(){
      systemUnderTest.focusOn( cycle );
      table.getSelectionModel().select( 2 );
      
      systemUnderTest.copySelectedConcept();
      assertThat( cycle.goals(), hasSize( 4 ) );
      assertThatTableMatchesCycle();
   }//End Method
   
   @Test public void shouldNotDuplicateWhenNoFocus(){
      systemUnderTest.copySelectedConcept();
      assertThat( cycle.goals(), hasSize( 3 ) );
   }//End Method
   
   @Test public void shouldNotDuplicateWhenNoSelection(){
      systemUnderTest.focusOn( cycle );
      systemUnderTest.copySelectedConcept();
      assertThat( cycle.goals(), hasSize( 3 ) );
   }//End Method
}//End Class
