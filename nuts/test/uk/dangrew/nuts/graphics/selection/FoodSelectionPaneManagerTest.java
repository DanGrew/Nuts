package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

public class FoodSelectionPaneManagerTest {

   @Mock private UiFoodSelector selector;
   @Mock private TileFactory tileFactory;
   @Mock private UiFoodSelectionPane selectionPane;
   private FoodSelectionPaneManager systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new FoodSelectionPaneManager( tileFactory, selectionPane, selector );
   }//End Method

   @Test public void shouldLayoutTilesUsingFactory() {
      Food food1 = new FoodItem( "1" );
      Food food2 = new FoodItem( "2" );
      Food food3 = new FoodItem( "3" );
      List< Food > foods = Arrays.asList( food1, food2, food3 );
      
      UiFoodTile tile1 = mock( UiFoodTile.class );
      UiFoodTile tile2 = mock( UiFoodTile.class );
      UiFoodTile tile3 = mock( UiFoodTile.class );
      when( tileFactory.create( food1, selector ) ).thenReturn( tile1 );
      when( tileFactory.create( food2, selector ) ).thenReturn( tile2 );
      when( tileFactory.create( food3, selector ) ).thenReturn( tile3 );
      
      systemUnderTest.layoutTiles( foods );
      verify( selectionPane ).layoutTiles( Arrays.asList( tile1, tile2, tile3 ) );
   }//End Method
   
   @Test public void shouldSelectTile(){
      Food food1 = new FoodItem( "1" );
      Food food2 = new FoodItem( "2" );
      
      UiFoodTile tile1 = mock( UiFoodTile.class );
      UiFoodTile tile2 = mock( UiFoodTile.class );
      when( tileFactory.get( food1 ) ).thenReturn( tile1 );
      when( tileFactory.get( food2 ) ).thenReturn( tile2 );
      
      systemUnderTest.setSelected( new FoodPortion( food1, 200 ), true );
      verify( tile1 ).setSelected( true );
      
      systemUnderTest.setSelected( new FoodPortion( food2, 200 ), false );
      verify( tile2 ).setSelected( false );
   }//End Method

   @Test public void shouldProvideSelectionPane(){
      assertThat( systemUnderTest.selectionPane(), is( selectionPane ) );
   }//End Method
}//End Class
