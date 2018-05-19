package uk.dangrew.nuts.apis.tesco.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.utility.event.TestMouseEvent;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelector;

public class TescoFoodTileFactoryTest {

   @Mock private UiFoodSelector< TescoFoodDescription > selector;
   private TescoFoodDescription food;
   private TescoFoodTileFactory systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      food = new TescoFoodDescription( "Food" );
      systemUnderTest = new TescoFoodTileFactory();
   }//End Method

   @Test public void shouldCreateFoodTile() {
      UiTescoFoodTile tile = systemUnderTest.create( food, selector );
      assertThat( tile.food(), is( food ) );
   }//End Method
   
   @Test public void shouldAssociateSelector() {
      UiTescoFoodTile tile = systemUnderTest.create( food, selector );
      tile.getOnMouseClicked().handle( new TestMouseEvent() );
      verify( selector ).select( food );
   }//End Method

}//End Class
