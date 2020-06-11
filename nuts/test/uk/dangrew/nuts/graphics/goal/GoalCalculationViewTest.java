package uk.dangrew.nuts.graphics.goal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class GoalCalculationViewTest {

   private CalorieGoal calorieGoal;
   private ProportionGoal proportionGoal;
   
   @Spy private CalorieGoalView calorieView;
   @Spy private ProportionGoalView proportionView;
   private GoalCalculationView systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      calorieGoal = new CalorieGoalImpl( "Calorie" );
      proportionGoal = new ProportionGoal( "Proportion" );
      systemUnderTest = new GoalCalculationView( calorieView, proportionView );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 10000000 );
   }//End Method
   
   @Test public void shouldShowEachType(){
      assertThat( systemUnderTest.getContent(), is( nullValue() ) );
      
      systemUnderTest.show( calorieGoal );
      assertThat( systemUnderTest.getContent(), is( calorieView ) );
      verify( calorieView ).detach();
      verify( proportionView ).detach();
      verify( calorieView ).show( calorieGoal );
      verify( proportionView, never() ).show( Mockito.any() );
      
      systemUnderTest.show( proportionGoal );
      assertThat( systemUnderTest.getContent(), is( proportionView ) );
      verify( calorieView, times( 2 ) ).detach();
      verify( proportionView, times( 2 ) ).detach();
      verify( calorieView, times( 1 ) ).show( Mockito.any() );
      verify( proportionView ).show( proportionGoal );
   }//End Method

}//End Class
