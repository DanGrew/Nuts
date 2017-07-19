package uk.dangrew.nuts.store;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.food.FoodItem;

public class DatabaseTest {

   private Database systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new Database();
   }//End Method

   @Test public void shouldProvideMappedFoods() {
      assertThat( systemUnderTest.foodItems(), is( instanceOf( MappedObservableStoreManagerImpl.class ) ) );
      
      FoodItem foodItem = new FoodItem( "Anything" );
      systemUnderTest.foodItems().store( foodItem );
      assertThat( systemUnderTest.foodItems().get( foodItem.properties().id() ), is( foodItem ) );
   }//End Method

}//End Class
