package uk.dangrew.nuts.template;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.MacroGoalRatioCalculator;
import uk.dangrew.nuts.meal.MealChangeListener;
import uk.dangrew.nuts.meal.MealPropertiesCalculator;
import uk.dangrew.nuts.meal.MealRegistrations;
import uk.dangrew.nuts.meal.StockUsage;

public class TemplateTest {

   @Mock private MealChangeListener listener;
   
   private FoodItem food1;
   private FoodItem food2;
   private FoodPortion portion1;
   private FoodPortion portion2;
   
   private FoodProperties properties;
   private FoodAnalytics foodAnalytics;
   private GoalAnalytics goalAnalytics;
   @Spy private MacroRatioCalculator ratioCalculator;
   @Mock private MealRegistrations registrations;
   @Mock private MealPropertiesCalculator propertiesCalculator;
   @Spy private MacroGoalRatioCalculator goalRatioCalculator;
   @Spy private StockUsage stockUsage;
   private Template systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      food1 = new FoodItem( "Food1" );
      food2 = new FoodItem( "Food2" );
      portion1 = new FoodPortion();
      portion2 = new FoodPortion();
      
      properties = new FoodProperties( "anything" );
      foodAnalytics = new FoodAnalytics();
      goalAnalytics = new GoalAnalytics();
      systemUnderTest = new Template(
               properties, 
               foodAnalytics, 
               goalAnalytics,
               registrations, 
               propertiesCalculator, 
               ratioCalculator,
               goalRatioCalculator,
               stockUsage
      );
   }//End Method
   
   @Test public void shouldUseName(){
      systemUnderTest = new Template( "Name" );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( "Name" ) );
   }//End Method

   @Test public void shouldProvideFoodPortions() {
      assertThat( systemUnderTest.portions(), is( empty() ) );
      systemUnderTest.portions().add( portion1 );
      assertThat( systemUnderTest.portions(), contains( portion1 ) );
   }//End Method
   
   @Test public void shouldProvideRegistrations(){
      assertThat( systemUnderTest.registrations(), is( registrations ) );
   }//End Method
   
   @Test public void shouldAssoiateWithRegistrations(){
      verify( registrations ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldAssoiateWithCalculator(){
      verify( propertiesCalculator ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldProvideProperties(){
      assertThat( systemUnderTest.properties(), is( properties ) );
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
   
   @Test public void shouldProvideStockUsage(){
      assertThat( systemUnderTest.stockUsage(), is( stockUsage ) );
   }//End Method
   
   @Test public void shouldAsscoiateStockUsage(){
      verify( stockUsage ).associate( systemUnderTest.portions() );
   }//End Method
   
   @Test public void shouldDuplicate(){
      systemUnderTest.portions().add( portion1 );
      systemUnderTest.portions().add( portion2 );
      systemUnderTest.goalAnalytics().goal().set( new Goal( "Goal" ) );
      Template duplicate = systemUnderTest.duplicate( "anything" );
      assertThat( duplicate.portions().get( 0 ).portion().get(), is( portion1.portion().get() ) );
      assertThat( duplicate.portions().get( 1 ).portion().get(), is( portion2.portion().get() ) );
      assertTrue( duplicate.portions().get( 0 ).food().get() == portion1.food().get() );
      assertTrue( duplicate.portions().get( 1 ).food().get() == portion2.food().get() );
      assertTrue( duplicate.goalAnalytics().goal().get() == systemUnderTest.goalAnalytics().goal().get()  );
   }//End Method

}//End Class
