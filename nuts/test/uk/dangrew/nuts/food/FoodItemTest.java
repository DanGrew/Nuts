package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.nuts.goal.MacroGoalRatioCalculator;

public class FoodItemTest {

   private FoodProperties properties;
   private StockProperties stockProperties;
   private FoodAnalytics foodAnalytics;
   private GoalAnalytics goalAnalytics;
   @Spy private MacroRatioCalculator ratioCalculator;
   @Spy private MacroGoalRatioCalculator goalRatioCalculator;
   private FoodItem systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      properties = new FoodProperties( "anything" );
      stockProperties = new StockProperties();
      foodAnalytics = new FoodAnalytics();
      goalAnalytics = new GoalAnalytics();
      systemUnderTest = new FoodItem( 
               properties, 
               stockProperties,
               foodAnalytics, 
               goalAnalytics, 
               ratioCalculator, 
               goalRatioCalculator 
      );
   }//End Method
   
   @Test public void shouldCreateWithId(){
      systemUnderTest = new FoodItem( "3487653", "skdjnvs." );
      assertThat( systemUnderTest.properties().id(), is( "3487653" ) );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( "skdjnvs." ) );
   }//End Method

   @Test public void shouldProvideProperties(){
      assertThat( systemUnderTest.properties(), is( properties ) );
   }//End Method
   
   @Test public void shouldProvideStockProperties(){
      assertThat( systemUnderTest.stockProperties(), is( stockProperties ) );
   }//End Method
   
   @Test public void shouldProvideFoodAnalytics(){
      assertThat( systemUnderTest.foodAnalytics(), is( foodAnalytics ) );
   }//End Method
   
   @Test public void shouldAssociateRatioCalculator(){
      verify( ratioCalculator ).associate( properties, foodAnalytics );
   }//End Method
   
   @Test public void shouldProvideGoalAnalytics(){
      assertThat( systemUnderTest.goalAnalytics(), is( goalAnalytics ) );
   }//End Method
   
   @Test public void shouldAssociateGoalRatioCalculator(){
      verify( goalRatioCalculator ).associate( properties, goalAnalytics );
   }//End Method

}//End Class
