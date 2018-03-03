package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.utility.mouse.TestMouseEvent;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

public class UiFoodTileTest {
  
   private FoodPortion food;
   private UiFoodTileConceptTitle title;
   private UiFoodTileProperties macros;
   private UiFoodTilePortionControl control;
   @Mock private UiFoodSelectionController controller;
   private UiFoodTile systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      food = new FoodPortion( new FoodItem( "Food" ), 100.0 );
      title = spy( new UiFoodTileConceptTitle( food.food().get() ) );
      macros = spy( new UiFoodTileProperties( food ) );
      control = spy( new UiFoodTilePortionControl( food ) );
      systemUnderTest = new UiFoodTile( food, controller, title, macros, control );
   }//End Method

   @Test public void shouldSelectPortion(){
      systemUnderTest.getOnMouseClicked().handle( new TestMouseEvent() );
      verify( controller ).select( food );
      
      when( controller.isSelected( food ) ).thenReturn( true );
      
      systemUnderTest.getOnMouseClicked().handle( new TestMouseEvent() );
      verify( controller ).deselect( food );
   }//End Method
   
   @Test public void shouldShowSelection(){
      assertThat( systemUnderTest.getBackground().getFills().get( 0 ).getFill(), is( UiFoodTile.DESELECTED_BACKGROUND ) );
      
      systemUnderTest.setSelected( true );
      assertThat( systemUnderTest.getBackground().getFills().get( 0 ).getFill(), is( UiFoodTile.SELECTED_BACKGROUND ) );
      
      systemUnderTest.setSelected( false );
      assertThat( systemUnderTest.getBackground().getFills().get( 0 ).getFill(), is( UiFoodTile.DESELECTED_BACKGROUND ) );
   }//End Method

}//End Class
