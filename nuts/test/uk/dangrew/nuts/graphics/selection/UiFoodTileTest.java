package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

public class UiFoodTileTest {
  
   private FoodPortion food;
   private UiFoodTileTitle title;
   private UiFoodTileMacros macros;
   private UiFoodTilePortionControl control;
   @Mock private UiFoodSelectionController controller;
   private UiFoodTile systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      food = new FoodPortion( new FoodItem( "Food" ), 100.0 );
      title = spy( new UiFoodTileTitle( food.food().get() ) );
      macros = spy( new UiFoodTileMacros( food ) );
      control = spy( new UiFoodTilePortionControl( food ) );
      systemUnderTest = new UiFoodTile( food, controller, title, macros, control );
   }//End Method

   @Test public void shouldDetachListeners() {
      assertThat( systemUnderTest.food(), is( food ) );
      systemUnderTest.detach();
      verify( title ).detach();
      verify( macros ).detach();
      verify( control ).detach();
      assertThat( systemUnderTest.food(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldAddPortion(){
      systemUnderTest.addButton().fire();
      verify( controller ).addPortion( food );
   }//End Method

}//End Class
