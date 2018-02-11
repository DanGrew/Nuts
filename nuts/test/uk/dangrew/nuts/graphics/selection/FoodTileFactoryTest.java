package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class FoodTileFactoryTest {

   private Food food;
   @Mock private UiFoodSelector selector;
   private FoodTileFactory systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      food = new FoodItem( "Anything" );
      systemUnderTest = new FoodTileFactory();
   }//End Method

   @Test public void shouldCreateNewTileAndCache() {
      UiFoodTile tile = systemUnderTest.create( food, selector );
      assertThat( tile, is( notNullValue() ) );
      assertThat( systemUnderTest.create( food, selector ), is( tile ) );
      
      assertThat( tile.food().food().get(), is( food ) );
      assertThat( tile.food().portion().get(), is( 100.0 ) );
   }//End Method
   
   @Test public void shouldRetrieveCachedTile() {
      assertThat( systemUnderTest.get( food ), is( nullValue() ) );
      UiFoodTile tile = systemUnderTest.create( food, selector );
      assertThat( systemUnderTest.get( food ), is( tile ) );
   }//End Method

}//End Class
