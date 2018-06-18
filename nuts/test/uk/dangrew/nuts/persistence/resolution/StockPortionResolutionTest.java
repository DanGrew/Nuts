package uk.dangrew.nuts.persistence.resolution;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.store.Database;

public class StockPortionResolutionTest {

   private Database database;
   private Stock subject;
   private FoodItem item;;
   
   private StockPortionResolution systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      item = database.foodItems().createConcept( "Item" );
      subject = database.stockLists().createConcept( "Stock" );
      systemUnderTest = new StockPortionResolution( 
               database.stockLists(), 
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
   
   @Test public void shouldNotApplyPortion() {
      subject.portions().add( new FoodPortion( item, 0.0 ) );
      
      systemUnderTest.resolve( database );
      assertThat( subject.portions().get( 0 ).food().get(), is( item ) );
      assertThat( subject.portions().get( 0 ).portion().get(), is( 121.0 ) );
      assertThat( subject.portions(), hasSize( 1 ) );
   }//End Method

}//End Class
