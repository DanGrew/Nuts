package uk.dangrew.nuts.stock;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class StockStoreTest {

   private Stock stock;
   private StockStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      stock = new Stock( "Stock" );
      systemUnderTest = new StockStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( stock.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( stock );
      assertThat( systemUnderTest.get( stock.properties().id() ), is( stock ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      Stock newFood = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( stock );
      assertThat( systemUnderTest.get( stock.properties().id() ), is( stock ) );
      systemUnderTest.removeConcept( stock );
      assertThat( systemUnderTest.get( stock.properties().id() ), is( nullValue() ) );
   }//End Method

}//End Class
