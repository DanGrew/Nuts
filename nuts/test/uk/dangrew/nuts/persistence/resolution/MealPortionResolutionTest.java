package uk.dangrew.nuts.persistence.resolution;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class MealPortionResolutionTest {

   private Database database;
   private Meal subject;
   private FoodItem item;;
   
   private MealPortionResolution systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      item = database.foodItems().createConcept( "Item" );
      subject = database.meals().createConcept( "Meal" );
      systemUnderTest = new MealPortionResolution( 
               database.meals(), 
               subject.properties().id(), 
               item.properties().id(), 
               121.0 
      );
   }//End Method

   @Test public void shouldApplyPortion() {
      systemUnderTest.resolve( database );
      assertThat( subject.portions().get( 0 ).food().get(), is( item ) );
      assertThat( subject.portions().get( 0 ).portion().get(), is( 121.0 ) );
      assertThat( subject.portions(), hasSize( 1 ) );
   }//End Method

}//End Class
