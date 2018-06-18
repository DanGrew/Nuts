package uk.dangrew.nuts.persistence.stock;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.store.Database;

public class StockParseModelTest {

   private Stock stock;
   private FoodItem item;
   
   private Database database;
   private StockParseModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      stock = database.stockLists().createConcept( "Stock" );
      item = database.foodItems().createConcept( "Item" );
      
      systemUnderTest = new StockParseModel( database );
   }//End Method

   @Test public void shouldAddResolutionToDatabase() {
      systemUnderTest.setId( stock.properties().id() );
      systemUnderTest.setFoodId( item.properties().id() );
      systemUnderTest.setPortionValue( 125.0 );
      systemUnderTest.finishPortion();
      
      assertThat( stock.portions(), is( empty() ) );
      database.resolver().resolve();
      assertThat( stock.portions().get( 0 ).food().get(), is( item ) );
      assertThat( stock.portions().get( 0 ).portion().get(), is( 125.0 ) );
   }//End Method

}//End Class
