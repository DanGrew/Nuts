package uk.dangrew.nuts.persistence.resolution;

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

public class TemplateGoalResolutionTest {

   private Template subject;
   private CalorieGoal calorieGoal;
   private ProportionGoal proportionGoal;
   
   private Database database;
   private TemplateGoalResolution systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      subject = database.templates().createConcept( "Subject" );
      calorieGoal = database.calorieGoals().createConcept( "Goal" );
      proportionGoal = database.proportionGoals().createConcept( "Goal" );
   }//End Method

   @Test public void shouldResolveCalorieGoal() {
      systemUnderTest = new TemplateGoalResolution(
               database.templates(),
               subject.properties().id(),
               calorieGoal.properties().id() 
      );
      systemUnderTest.resolve( database );
      
      assertThat( subject.goalAnalytics().goal().get(), is( calorieGoal ) );
   }//End Method
   
   @Test public void shouldResolveProportionGoal() {
      systemUnderTest = new TemplateGoalResolution( 
               database.templates(),
               subject.properties().id(),
               proportionGoal.properties().id() 
      );
      systemUnderTest.resolve( database );
      
      assertThat( subject.goalAnalytics().goal().get(), is( proportionGoal ) );
   }//End Method
   
   @Test public void shouldNotResolveGoal() {
      systemUnderTest = new TemplateGoalResolution( 
               database.templates(),
               subject.properties().id(),
               "invalid" 
      );
      systemUnderTest.resolve( database );
      
      assertThat( subject.goalAnalytics().goal().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldNotResolveEmptyGoal() {
      systemUnderTest = new TemplateGoalResolution( 
               database.templates(),
               subject.properties().id(),
               "   " 
      );
      systemUnderTest.resolve( database );
      
      assertThat( subject.goalAnalytics().goal().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldHandleInvalidSubject() {
      systemUnderTest = new TemplateGoalResolution( 
               database.templates(),
               "anything",
               calorieGoal.properties().id() 
      );
      systemUnderTest.resolve( database );
      
      assertThat( subject.goalAnalytics().goal().get(), is( nullValue() ) );
   }//End Method

}//End Class
