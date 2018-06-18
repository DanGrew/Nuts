package uk.dangrew.nuts.persistence.resolution;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;
import uk.dangrew.nuts.template.Template;

public class AbstractMealPortionResolutionTest {

   private Meal subject;
   private Database database;
   
   private AbstractMealPortionResolution< Meal > systemUnderTest;
   
   private Meal providedSubject;
   private FoodPortion providedPortion;
   private Database providedDatabase;
   
   private class TestResolution extends AbstractMealPortionResolution< Meal > {

      public TestResolution( ConceptStore< ? extends Meal > store, String subjectId, String referenceId, Double portion ) {
         super( store, subjectId, referenceId, portion );
      }//End Constructor
      
      @Override protected void applyPortion( Meal subject, FoodPortion portion, Database database ) {
         providedSubject = subject;
         providedPortion = portion;
         providedDatabase = database;
         
         providedSubject.portions().add( portion );
      }
      
   }//End Class

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      
      subject = database.meals().createConcept( "Subject" );
   }//End Method

   @Test public void shouldResolveFoodItemPortion() {
      FoodItem item = database.foodItems().createConcept( "anything" );
      systemUnderTest = new TestResolution( 
               database.meals(), subject.properties().id(), item.properties().id(), 123.0
      );
      
      assertThat( subject.portions(), is( empty() ) );
      systemUnderTest.resolve( database );
      assertThat( subject.portions().get( 0 ).food().get(), is( item ) );
      assertThat( subject.portions().get( 0 ).portion().get(), is( 123.0 ) );
      assertThat( subject.portions(), hasSize( 1 ) );
      
      assertPortionApplied( item );
   }//End Method

   private void assertPortionApplied( Food item ) {
      assertThat( providedSubject, is( subject ) );
      assertThat( providedPortion.food().get(), is( item ) );
      assertThat( providedPortion.portion().get(), is( 123.0 ) );
      assertThat( providedDatabase, is( database ) );
   }//End Method
   
   @Test public void shouldResolveMealPortion() {
      Meal item = database.meals().createConcept( "anything" );
      systemUnderTest = new TestResolution( 
               database.meals(), subject.properties().id(), item.properties().id(), 123.0
      );
      
      assertThat( subject.portions(), is( empty() ) );
      systemUnderTest.resolve( database );
      assertThat( subject.portions().get( 0 ).food().get(), is( item ) );
      assertThat( subject.portions().get( 0 ).portion().get(), is( 123.0 ) );
      assertThat( subject.portions(), hasSize( 1 ) );
      
      assertPortionApplied( item );
   }//End Method
   
   @Test public void shouldResolveTemplatePortion() {
      Template item = database.templates().createConcept( "anything" );
      systemUnderTest = new TestResolution( 
               database.meals(), subject.properties().id(), item.properties().id(), 123.0
      );
      
      assertThat( subject.portions(), is( empty() ) );
      systemUnderTest.resolve( database );
      assertThat( subject.portions().get( 0 ).food().get(), is( item ) );
      assertThat( subject.portions().get( 0 ).portion().get(), is( 123.0 ) );
      assertThat( subject.portions(), hasSize( 1 ) );
      
      
      assertPortionApplied( item );
   }//End Method

   @Test public void shouldNotResolvePortion() {
      FoodItem item = new FoodItem( "anything" );
      systemUnderTest = new TestResolution( 
               database.meals(), subject.properties().id(), item.properties().id(), 123.0
      );
      
      assertThat( subject.portions(), is( empty() ) );
      systemUnderTest.resolve( database );
      assertThat( subject.portions(), is( empty() ) );
   }//End Method
   
   @Test public void shouldNotResolveForInvalidMealSubject() {
      subject = new Meal( "anything" );
      FoodItem item = database.foodItems().createConcept( "anything" );
      systemUnderTest = new TestResolution( 
               database.meals(), subject.properties().id(), item.properties().id(), 123.0
      );
      
      assertThat( subject.portions(), is( empty() ) );
      systemUnderTest.resolve( database );
      assertThat( subject.portions(), is( empty() ) );
   }//End Method
   
}//End Class