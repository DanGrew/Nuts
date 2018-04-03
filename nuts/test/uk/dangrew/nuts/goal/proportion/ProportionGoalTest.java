package uk.dangrew.nuts.goal.proportion;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;
import uk.dangrew.nuts.goal.GoalTypes;

public class ProportionGoalTest {

   private static final String ID = "slifdunvl";
   private static final String NAME = "sbfvuybaolwehbr";
   private ProportionGoal systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProportionGoal( ID, NAME );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.properties().id(), is( ID ) );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( NAME ) );
   }//End Method
   
   @Test public void shouldProvideType(){
      assertThat( systemUnderTest.type(), is( GoalTypes.Proportion ) );
   }//End Method
   
   @Test public void shouldProvideStructure(){
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideObject( ProportionGoal::foodAnalytics )
         .shouldProvideObject( ProportionGoal::configuration );
   }//End Method

}//End Class
