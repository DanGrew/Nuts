package uk.dangrew.nuts.graphics.tutorial.database;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class DatabaseManipulatorTest {

   private Database database;
   private DatabaseManipulator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new DatabaseManipulator( database = new Database() );
   }//End Method

   @Test public void shouldFindConcepts() {
      FoodItem egg = database.foodItems().createConcept( "Egg" );
      FoodItem cream = database.foodItems().createConcept( "Cream" );
      FoodItem egg2 = database.foodItems().createConcept( "Egg" );
      Meal eggMeal = database.meals().createConcept( "Egg" );
      
      assertThat( systemUnderTest.find( "Egg", Database::foodItems ), is( egg ) );
      assertThat( systemUnderTest.find( "Egg", Database::meals ), is( eggMeal ) );
   }//End Method

}//End Class
