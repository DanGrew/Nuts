package uk.dangrew.nuts.apis.tesco.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.FoodSelectionManager;
import uk.dangrew.nuts.graphics.selection.UiFoodSelectionPane;
import uk.dangrew.nuts.graphics.selection.UiFoodSelectionTile;
import uk.dangrew.nuts.graphics.selection.UiFoodSelector;

public class UiTescoFoodDescriptionPaneTest {

   @Captor private ArgumentCaptor< List< UiFoodSelectionTile > > tilesCaptor;
   @Captor private ArgumentCaptor< FoodPortion > portionCaptor;
   private TescoFoodDescription description;
   private List< Food > foods;
   
   @Mock private TescoFoodItemGenerator itemGenerator;
   @Mock private UiFoodSelector< FoodPortion > selectionController;
   @Mock private FoodSelectionManager selectionManager;
   @Mock private UiFoodSelectionPane selectionPane;
   private UiTescoFoodDescriptionPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      description = new TescoFoodDescription( "Tesco Food" );
      foods = Arrays.asList( new FoodItem( "A" ), new FoodItem( "B" ), new FoodItem( "C" ) );
      when( itemGenerator.generateFoodItemsFor( description ) ).thenReturn( foods );
      
      systemUnderTest = new UiTescoFoodDescriptionPane( 
               selectionController, 
               selectionManager,
               selectionPane,
               itemGenerator
      );
   }//End Method

   @Test public void shouldResetTiles() {
      systemUnderTest.clearOptions();
      verify( selectionPane ).layoutTiles( Collections.emptyList() );
   }//End Method
   
   @Test public void shouldShowOptions() {
      systemUnderTest.showOptions( description );
      verify( selectionPane ).layoutTiles( tilesCaptor.capture() );
      
      for ( int i = 0; i < 3; i++ ) {
         assertThat( tilesCaptor.getValue().get( i ).food().food().get(), is( foods.get( i ) ) );
      }
   }//End Method
   
   @Test public void shouldProvideSelection() {
      when( selectionManager.isSelected( Mockito.any() ) ).thenReturn( true, false, true );
      assertThat( systemUnderTest.isSelected( mock( FoodPortion.class ) ), is( true ) );
      assertThat( systemUnderTest.isSelected( mock( FoodPortion.class ) ), is( false ) );
      assertThat( systemUnderTest.isSelected( mock( FoodPortion.class ) ), is( true ) );
   }//End Method
   
   @Test public void shouldDeselect() {
      systemUnderTest.showOptions( description );
      verify( selectionPane ).layoutTiles( tilesCaptor.capture() );
      assertThat( tilesCaptor.getValue().get( 0 ).isSelected(), is( false ) );
      
      systemUnderTest.select( tilesCaptor.getValue().get( 0 ).food() );
      assertThat( tilesCaptor.getValue().get( 0 ).isSelected(), is( true ) );
      
      systemUnderTest.deselect( tilesCaptor.getValue().get( 0 ).food() );
      
      verify( selectionManager ).deselect( tilesCaptor.getValue().get( 0 ).food() );
      assertThat( tilesCaptor.getValue().get( 0 ).isSelected(), is( false ) );
      verify( selectionController ).deselect( tilesCaptor.getValue().get( 0 ).food() );
   }//End Method
   
   @Test public void shouldSelect() {
      systemUnderTest.showOptions( description );
      verify( selectionPane ).layoutTiles( tilesCaptor.capture() );
      assertThat( tilesCaptor.getValue().get( 0 ).isSelected(), is( false ) );
      
      systemUnderTest.select( tilesCaptor.getValue().get( 0 ).food() );
      
      verify( selectionManager ).select( tilesCaptor.getValue().get( 0 ).food() );
      assertThat( tilesCaptor.getValue().get( 0 ).isSelected(), is( true ) );
      verify( selectionController ).select( tilesCaptor.getValue().get( 0 ).food() );
   }//End Method

}//End Class
