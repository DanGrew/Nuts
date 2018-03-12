package uk.dangrew.nuts.apis.tesco.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.api.Tesco;
import uk.dangrew.nuts.apis.tesco.model.TescoFoodDescription;

public class UiTescoPortionOptionsControllerTest {

   private TescoFoodDescription description;
   
   @Mock private Tesco tesco;
   @Mock private TescoSelectionPaneManager paneManager;
   @Mock private UiTescoFoodPortionOptions options;
   private UiTescoPortionOptionsController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      description = new TescoFoodDescription( "a" );
      
      systemUnderTest = new UiTescoPortionOptionsController( tesco, options );
      systemUnderTest.controlSelection( paneManager );
   }//End Method

   @Test public void shouldDisplayNoItemsInitially() {
      verify( paneManager ).layoutTiles( new ArrayList<>() );
   }//End Method
   
   @Test public void shouldSelectDescriptionTile() {
      assertThat( systemUnderTest.isSelected( description ), is( false ) );
      systemUnderTest.select( description );
      assertThat( systemUnderTest.isSelected( description ), is( true ) );
      
      InOrder verifier = inOrder( paneManager, tesco, options );
      verifier.verify( paneManager ).setSelected( description, true );
      verifier.verify( tesco ).downloadProductDetail( description );
      verifier.verify( options ).showOptions( description );
   }//End Method
   
   @Test public void shouldDeselectDescriptionTile() {
      assertThat( systemUnderTest.isSelected( description ), is( false ) );
      systemUnderTest.select( description );
      assertThat( systemUnderTest.isSelected( description ), is( true ) );
      systemUnderTest.deselect( description );
      assertThat( systemUnderTest.isSelected( description ), is( false ) );
      
      InOrder verifier = inOrder( paneManager, options );
      verifier.verify( paneManager ).setSelected( description, true );
      verifier.verify( paneManager ).setSelected( description, false );
      verifier.verify( options ).clearOptions();
   }//End Method
   
   @Test public void shouldSearchAndApplyResults() {
      when( tesco.search( "anything" ) ).thenReturn( Arrays.asList( description ) );
      
      systemUnderTest.search( "anything" );
      verify( paneManager ).layoutTiles( Arrays.asList( description ) );
   }//End Method

}//End Class
