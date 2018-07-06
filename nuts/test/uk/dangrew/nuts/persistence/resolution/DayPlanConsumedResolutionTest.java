package uk.dangrew.nuts.persistence.resolution;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;

public class DayPlanConsumedResolutionTest {

   private Database database;
   private DayPlan subject;
   private FoodItem item;;
   
   private DayPlanConsumedResolution systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();

   }//End Method

   @Test public void shouldResolveFoodFromDayPlanController(){
      item = database.dayPlanController().foodItems().createConcept( "Item" );
      item.nutrition().setMacroNutrients( 100, 34, 45 );
      subject = database.dayPlans().createConcept( "Plan" );
      
      systemUnderTest = new DayPlanConsumedResolution( 
               database.dayPlans(), 
               subject.properties().id(), 
               item.properties().id(), 
               121.0,
               true
      );
      
      systemUnderTest.resolve( database );
      
      assertThat( subject.portions(), hasSize( 1 ) );
      FoodPortion portion = subject.portions().get( 0 );
      assertThat( portion.food().get(), is( item ) );
      assertThat( portion.portion().get(), is( 121.0 ) );
      assertThat( subject.consumed(), contains( portion ) );
   }//End Method
   
   @Test public void shouldResolveFoodFromDatabaseAndCopyIntoDayController(){
      item = database.foodItems().createConcept( "Item" );
      item.nutrition().setMacroNutrients( 100, 34, 45 );
      subject = database.dayPlans().createConcept( "Plan" );
      
      systemUnderTest = new DayPlanConsumedResolution( 
               database.dayPlans(), 
               subject.properties().id(), 
               item.properties().id(), 
               121.0,
               true
      );
      
      systemUnderTest.resolve( database );
      
      assertThat( subject.portions(), hasSize( 1 ) );
      FoodPortion portion = subject.portions().get( 0 );
      assertThat( portion.food().get(), is( not( item ) ) );
      assertThat( portion.portion().get(), is( 121.0 ) );
      
      FoodItem copy = ( FoodItem ) portion.food().get();
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( copy.nutrition().of( unit ).get(), is( item.nutrition().of( unit ).get() ) );
      }
      assertThat( subject.consumed(), contains( portion ) );
   }//End Method
   
}//End Class
