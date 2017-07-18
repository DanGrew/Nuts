package uk.dangrew.nuts.store;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.food.Food;

public class DatabaseTest {

   private Database systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new Database();
   }//End Method

   @Test public void shouldProvideMappedFoods() {
      assertThat( systemUnderTest.foods(), is( instanceOf( MappedObservableStoreManagerImpl.class ) ) );
      
      Food food = new Food( "Anything" );
      systemUnderTest.foods().store( food );
      assertThat( systemUnderTest.foods().get( food.id() ), is( food ) );
   }//End Method

}//End Class
