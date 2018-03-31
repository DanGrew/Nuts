package uk.dangrew.nuts.graphics.cycle;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.cycle.Cycle;
import uk.dangrew.nuts.cycle.CycleStore;
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.GoalStore;

public class UiCycleTableControllerTest {

   private UiCycleTable table;
   @Mock private UiCycleCreationDialog creationDialog;
   
   private CycleStore cycles;
   
   private CalorieGoal baseGoal;
   private GoalStore goals;
   private UiCycleTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      cycles = new CycleStore();
      goals = new GoalStore();
      baseGoal = goals.createConcept( "Goal1" );
      
      systemUnderTest = new UiCycleTableController( creationDialog, cycles );
      PlatformImpl.runAndWait( () -> table = new UiCycleTable( systemUnderTest ) );
      systemUnderTest.associate( table );
   }//End Method

   @Test public void shouldUpdateRowsWhenAddedToCycles() {
      Cycle cycle = cycles.createConcept( "Cycle1" );
      assertThat( table.getRows(), hasSize( 1 ) );
      assertThat( table.getRows().get( 0 ).concept(), is( cycle ) );
   }//End Method
   
   @Test public void shouldUpdateRowsWhenRemovedFromCycles() {
      Cycle cycle1 = cycles.createConcept( "Cycle1" );
      Cycle cycle2 = cycles.createConcept( "Cycle2" );
      Cycle cycle3 = cycles.createConcept( "Cycle3" );
      assertThat( table.getRows(), hasSize( 3 ) );
      
      cycles.remove( cycle2.properties().id() );
      assertThat( table.getRows(), hasSize( 2 ) );
      assertThat( table.getRows().get( 0 ).concept(), is( cycle1 ) );
      assertThat( table.getRows().get( 1 ).concept(), is( cycle3 ) );
   }//End Method

   @Test public void shouldUseDialogToCreateNewCycle() {
      when( creationDialog.friendly_showAndWait() ).thenReturn( Optional.of( new CycleCreationResult( baseGoal ) ) );
      Cycle cycle = systemUnderTest.createConcept();
      assertThat( cycle.baseGoal(), is( baseGoal ) );
   }//End Method
   
   @Test public void shouldNotCreateIfNoDialogResult() {
      when( creationDialog.friendly_showAndWait() ).thenReturn( Optional.empty() );
      Cycle cycle = systemUnderTest.createConcept();
      assertThat( cycle, is( nullValue() ) );
   }//End Method
   
   @Test public void shouldRemoveSelectedCycle() {
      cycles.createConcept( "Cycle" );
      assertThat( table.getRows(), hasSize( 1 ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.removeSelectedConcept();
      assertThat( table.getRows(), hasSize( 0 ) );
   }//End Method
   
   @Test public void shouldNotRemoveIfNotSelected() {
      cycles.createConcept( "Cycle" );
      assertThat( table.getRows(), hasSize( 1 ) );
      systemUnderTest.removeSelectedConcept();
      assertThat( table.getRows(), hasSize( 1 ) );
   }//End Method
   
   @Test public void shouldNotCopySelectedCycle() {
      cycles.createConcept( "Cycle" );
      assertThat( table.getRows(), hasSize( 1 ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.copySelectedConcept();
      assertThat( table.getRows(), hasSize( 1 ) );
   }//End Method
   
   @Test public void shouldNotCopyWhenNotSelected() {
      cycles.createConcept( "Cycle" );
      assertThat( table.getRows(), hasSize( 1 ) );
      systemUnderTest.copySelectedConcept();
      assertThat( table.getRows(), hasSize( 1 ) );
   }//End Method
}//End Class
