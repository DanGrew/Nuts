package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodPortion;

public class UiFoodSelectionTileTest {

   private class TestTile extends UiFoodSelectionTile {

      @Override protected void updateSelection( boolean selected ) {
         UiFoodSelectionTileTest.this.selected = selected;
      }//End Method

      @Override public FoodPortion food() {
         return null;
      }//End Method
      
   }//End Class
   
   private boolean selected;
   private TestTile systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TestTile();
   }//End Method

   @Test public void shouldHandleSelection() {
      systemUnderTest.setSelected( true );
      assertThat( selected, is( true ) );
      assertThat( systemUnderTest.isSelected(), is( true ) );
      
      systemUnderTest.setSelected( false );
      assertThat( selected, is( false ) );
      assertThat( systemUnderTest.isSelected(), is( false ) );
   }//End Method

}//End Class
