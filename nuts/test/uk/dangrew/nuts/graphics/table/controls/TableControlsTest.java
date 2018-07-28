package uk.dangrew.nuts.graphics.table.controls;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.Button;
import uk.dangrew.kode.launch.TestApplication;

public class TableControlsTest {

   @Mock private TableControlSet set1;
   @Mock private TableControlSet set2;
   private TableControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TableControls( set1, set2 );
   }//End Method

   @Test public void shouldAddButtons() {
      verify( set1 ).addButtons( systemUnderTest, TableControls.BUTTON_WIDTH );
      verify( set2 ).addButtons( systemUnderTest, TableControls.BUTTON_WIDTH );
   }//End Method
   
   @Test public void shouldProvideButtons(){
      Button button = new Button();
      when( set2.getButton( TableControlType.Add ) ).thenReturn( button );
      assertThat( systemUnderTest.getButton( TableControlType.Add ), is( button ) );
   }//End Method

}//End Class
