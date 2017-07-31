package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class StockPropertiesTest {

   private StockProperties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new StockProperties();
   }//End Method

   @Test public void shouldProvideSoldInWeight() {
      assertThat( systemUnderTest.soldInWeight(), is( notNullValue() ) );
      assertThat( systemUnderTest.soldInWeight().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideStorageType() {
      assertThat( systemUnderTest.storageType(), is( notNullValue() ) );
      assertThat( systemUnderTest.storageType().get(), is( StorageType.Unknown ) );
   }//End Method
   
   @Test public void shouldProvideLoggedWeight() {
      assertThat( systemUnderTest.loggedWeight(), is( notNullValue() ) );
      assertThat( systemUnderTest.loggedWeight().get(), is( 0.0 ) );
   }//End Method

}//End Class
