package uk.dangrew.nuts.dayplan;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.system.Properties;
import uk.dangrew.nuts.template.Template;

public class DayPlanTest {

   private Template structure;
   private Properties properties;
   private DayPlan systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      properties = new Properties( "Anything" );
      structure = new Template( properties );
      systemUnderTest = new DayPlan( structure );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.properties(), is( properties ) );
   }//End Method
   
   @Test public void shouldProvideFoodHolderStructure(){
      assertThat( systemUnderTest.nutrition(), is( structure.nutrition() ) );
      assertThat( systemUnderTest.foodAnalytics(), is( structure.foodAnalytics() ) );
      assertThat( systemUnderTest.goalAnalytics(), is( structure.goalAnalytics() ) );
      assertThat( systemUnderTest.portions(), is( structure.portions() ) );
   }//End Method
   
   @Test public void shouldRemoveFromPlan(){
      Meal meal1 = new Meal( "Meal1" );
      Meal meal2 = new Meal( "Meal2" );
      Meal meal3 = new Meal( "Meal3" );
      Meal meal4 = new Meal( "Meal4" );
      
      FoodItem item1 = new FoodItem( "Item1" );
      FoodItem item2 = new FoodItem( "Item2" );
      
      meal3.portions().add( new FoodPortion( meal4, 100.0 ) );
      meal2.portions().add( new FoodPortion( meal3, 100.0 ) );
      meal1.portions().add( new FoodPortion( meal2, 100.0 ) );
      
      meal3.portions().add( new FoodPortion( item1, 100.0 ) );
      meal1.portions().add( new FoodPortion( item2, 100.0 ) );
      
      systemUnderTest.portions().add( new FoodPortion( meal1, 0 ) );
      
      systemUnderTest.remove( meal3.portions().get( 0 ) );
      assertThat( meal3.portions(), hasSize( 1 ) );
      assertThat( meal3.portions().get( 0 ).food().get(), is( item1 ) );
      
      systemUnderTest.remove( meal3.portions().get( 0 ) );
      assertThat( meal3.portions(), hasSize( 0 ) );
      
      systemUnderTest.remove( systemUnderTest.portions().get( 0 ) );
      assertThat( systemUnderTest.portions(), hasSize( 0 ) );
   }//End Method

}//End Class
