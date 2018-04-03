package uk.dangrew.nuts.graphics.goal;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.GoalTypes;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.store.Database;

public class GoalTableControllerTest {

   @Mock private UiGoalTypeSelectionDialog dialog;
   private Database database;
   
   private ConceptTable< Goal > table;
   private GoalTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      database.calorieGoals().createConcept( "CalorieGoal1" );
      database.proportionGoals().createConcept( "ProportionGoal1" );
      
      systemUnderTest = new GoalTableController( dialog, database.calorieGoals(), database.proportionGoals() );
      PlatformImpl.runAndWait( () -> table = new GoalTable( systemUnderTest ) );
   }//End Method

   @Test public void shouldProvideRows() {
      assertThat( table.getRows().get( 0 ).concept(), is( database.calorieGoals().objectList().get( 0 ) ) );
      assertThat( table.getRows().get( 1 ).concept(), is( database.proportionGoals().objectList().get( 0 ) ) );
      
      database.calorieGoals().createConcept( "CalorieGoal2" );
      database.proportionGoals().createConcept( "ProportionGoal2" );
      
      assertThat( table.getRows().get( 2 ).concept(), is( database.calorieGoals().objectList().get( 1 ) ) );
      assertThat( table.getRows().get( 3 ).concept(), is( database.proportionGoals().objectList().get( 1 ) ) );
   }//End Method
   
   @Test public void shouldCreateConcepts(){
      when( dialog.friendly_showAndWait() ).thenReturn( Optional.of( GoalTypes.Calorie ) );
      Goal goal = systemUnderTest.createConcept();
      assertThat( goal, is( instanceOf( CalorieGoal.class ) ) );
      assertThat( database.calorieGoals().hasKey( goal.properties().id() ), is( true ) );
      
      when( dialog.friendly_showAndWait() ).thenReturn( Optional.of( GoalTypes.Proportion ) );
      goal = systemUnderTest.createConcept();
      assertThat( goal, is( instanceOf( ProportionGoal.class ) ) );
      assertThat( database.proportionGoals().hasKey( goal.properties().id() ), is( true ) );
   }//End Method
   
   @Test public void shouldNotCreateConceptIfCancelled(){
      assertThat( systemUnderTest.createConcept(), is( nullValue() ) );
      
      when( dialog.friendly_showAndWait() ).thenReturn( Optional.empty() );
      assertThat( systemUnderTest.createConcept(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldRemoveConcepts(){
      assertThat( database.calorieGoals().objectList(), is( not( empty() ) ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.removeSelectedConcept();
      assertThat( database.calorieGoals().objectList(), is( empty() ) );
      
      assertThat( database.proportionGoals().objectList(), is( not( empty() ) ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.removeSelectedConcept();
      assertThat( database.proportionGoals().objectList(), is( empty() ) );
   }//End Method
   
   @Test public void shouldNotRemoveConceptIfNotSelected(){
      assertThat( database.calorieGoals().objectList(), is( not( empty() ) ) );
      assertThat( database.proportionGoals().objectList(), is( not( empty() ) ) );
      systemUnderTest.removeSelectedConcept();
      assertThat( database.calorieGoals().objectList(), is( not( empty() ) ) );
      assertThat( database.proportionGoals().objectList(), is( not( empty() ) ) );
   }//End Method
   
   @Test public void shouldNotCopyConcepts(){
      //this is tied to goals not being copy-able yet
      assertThat( database.calorieGoals().objectList(), hasSize( 1 ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.copySelectedConcept();
      assertThat( database.calorieGoals().objectList(), hasSize( 1 ) );
      
      assertThat( database.proportionGoals().objectList(), hasSize( 1 ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.copySelectedConcept();
      assertThat( database.proportionGoals().objectList(), hasSize( 1 ) );
   }//End Method

}//End Class
