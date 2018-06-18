package uk.dangrew.nuts.persistence.resolution;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class ResolverTest {

   @Mock private ResolutionStrategy strategy;
   
   private Database database;
   private Resolver systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      systemUnderTest = new Resolver( database );
   }//End Method

   @Test public void shouldResolveUsingStrategy() {
      systemUnderTest.submitStrategy( strategy );
      systemUnderTest.resolve();
      verify( strategy ).resolve( database );
   }//End Method
   
   @Test public void shouldResolveMealPortions() {
      Meal subject = database.meals().createConcept( "Anything" );
      FoodItem reference = database.foodItems().createConcept( "Anything" );
      
      systemUnderTest.submitStrategy(
               new MealPortionResolution( 
                        database.meals(),
                        subject.properties().id(),
                        reference.properties().id(), 
                        45.5
               ) 
      );
      
      systemUnderTest.resolve();
      assertThat( subject.portions().get( 0 ).food().get(), is( reference ) );
      assertThat( subject.portions().get( 0 ).portion().get(), is( 45.5 ) );
      assertThat( subject.portions(), hasSize( 1 ) );
   }//End Method

}//End Class
