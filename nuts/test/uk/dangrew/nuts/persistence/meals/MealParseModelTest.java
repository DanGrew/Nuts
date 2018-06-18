package uk.dangrew.nuts.persistence.meals;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class MealParseModelTest {

   private Meal meal;
   private FoodItem item;
   
   private Database database;
   private MealParseModel< Meal > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      meal = database.meals().createConcept( "Meal" );
      item = database.foodItems().createConcept( "Item" );
      
      systemUnderTest = new MealParseModel<>( database, database.meals() );
   }//End Method

   @Test public void shouldAddResolutionToDatabase() {
      systemUnderTest.setId( meal.properties().id() );
      systemUnderTest.setFoodId( item.properties().id() );
      systemUnderTest.setPortionValue( 125.0 );
      systemUnderTest.finishPortion();
      
      assertThat( meal.portions(), is( empty() ) );
      database.resolver().resolve();
      assertThat( meal.portions().get( 0 ).food().get(), is( item ) );
      assertThat( meal.portions().get( 0 ).portion().get(), is( 125.0 ) );
   }//End Method


}//End Class
