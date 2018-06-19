package uk.dangrew.nuts.persistence.template;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class TemplateParseModelTest {

   private CalorieGoal calorieGoal;
   private ProportionGoal proportionGoal;
   private Template parsedTemplate;
   private Database database;
   private TemplateParseModel< Template > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      parsedTemplate = database.templates().createConcept( "Template" );
      calorieGoal = database.calorieGoals().createConcept( "CalorieGoal" );
      proportionGoal = database.proportionGoals().createConcept( "ProportionGoal" );
      
      systemUnderTest = new TemplateParseModel<>( database, database.templates() );
   }//End Method

   @Test public void shouldIgnoreEmptyReference(){
      systemUnderTest.setId( parsedTemplate.properties().id() );
      systemUnderTest.setGoalId( "  " );
      systemUnderTest.finishMeal();
      
      assertThat( parsedTemplate.goalAnalytics().goal().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldResolveCalorieGoal(){
      systemUnderTest.setId( parsedTemplate.properties().id() );
      systemUnderTest.setGoalId( calorieGoal.properties().id() );
      systemUnderTest.finishMeal();
      
      assertThat( parsedTemplate.goalAnalytics().goal().get(), is( calorieGoal ) );
   }//End Method
   
   @Test public void shouldResolveProportionGoal(){
      systemUnderTest.setId( parsedTemplate.properties().id() );
      systemUnderTest.setGoalId( proportionGoal.properties().id() );
      systemUnderTest.finishMeal();
      
      assertThat( parsedTemplate.goalAnalytics().goal().get(), is( proportionGoal ) );
   }//End Method

}//End Class
