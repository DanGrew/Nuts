package uk.dangrew.nuts.persistence.labels;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class LabelParseModelTest {

   private Label label;
   private Database database;
   private LabelParseModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database= new Database();
      database.labels().store( label = new Label( "Existing" ) );
      systemUnderTest = new LabelParseModel( database );
   }//End Method

   @Test public void shouldCreateLabelInStore() {
      systemUnderTest.setId( "id" );
      systemUnderTest.setName( "name" );
      systemUnderTest.finishLabel();
      
      assertThat( database.labels().get( "id" ).properties().nameProperty().get(), is( "name" ) );
   }//End Method
   
   @Test public void shouldResetConceptsOfLabelThatExists() {
      systemUnderTest.setId( database.labels().objectList().get( 0 ).properties().id() );
      systemUnderTest.setName( "name" );
      systemUnderTest.finishLabel();
      
      assertThat( database.labels().objectList(), hasSize( 1 ) );
      assertThat( database.labels().objectList().get( 0 ).properties().nameProperty().get(), is( "Existing" ) );
   }//End Method
   
   @Test public void shouldResolveFromLabelables() {
      FoodItem foodItem = new FoodItem( "1", "Item" );
      database.foodItems().store( foodItem );
      
      Meal meal = new Meal( "2", "Meal" );
      database.meals().store( meal );
      
      CalorieGoal calorieGoal = new CalorieGoalImpl( "3", "Goal" );
      database.calorieGoals().store( calorieGoal );
      
      database.labels().objectList().get( 0 ).concepts().add( new FoodItem( "anything" ) );
      
      systemUnderTest.setId( database.labels().objectList().get( 0 ).properties().id() );
      systemUnderTest.setName( "name" );
      systemUnderTest.startConcept();
      systemUnderTest.setConceptId( "1" );
      systemUnderTest.finishConcept();
      systemUnderTest.startConcept();
      systemUnderTest.setConceptId( "2" );
      systemUnderTest.finishConcept();
      systemUnderTest.startConcept();
      systemUnderTest.setConceptId( "3" );
      systemUnderTest.finishConcept();
      systemUnderTest.finishLabel();
      
      assertThat( database.labels().objectList().get( 0 ).concepts(), hasSize( 3 ) );
      assertThat( database.labels().objectList().get( 0 ).concepts(), contains( foodItem, meal, calorieGoal ) );
   }//End Method

}//End Class
