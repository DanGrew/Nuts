package uk.dangrew.nuts.graphics.progress.custom;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class ProgressEntryTextPaneTest {

   private static final String HEADER = "Header";
   private static final String NOTES = "There's lots going on here, "
            + "lots of notes to provide and detail to add. This will "
            + "most likely cross lines needing a text area.";
   @Mock private ProgressSeriesDataController controller;
   private ProgressEntryTextPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProgressEntryTextPane( controller );
   }//End Method

   @Test public void shouldUpdateWhenHeaderChanges(){
      systemUnderTest.headerField().setText( HEADER );
      verify( controller ).setHeaderForSelected( HEADER );
   }//End Method
   
   @Test public void shouldUpdateWhenNotesChanges(){
      systemUnderTest.notesArea().setText( NOTES );
      verify( controller ).setNotesForSelected( NOTES );
   }//End Method
   
   @Test public void shouldShowSelection(){
      assertThat( systemUnderTest.headerField().getText(), is( nullValue() ) );
      assertThat( systemUnderTest.notesArea().getText(), is( nullValue() ) );
      assertThat( systemUnderTest.headerField().isDisable(), is( true ) );
      assertThat( systemUnderTest.notesArea().isDisable(), is( true ) );
      
      systemUnderTest.update( HEADER, NOTES );
      assertThat( systemUnderTest.headerField().getText(), is( HEADER ) );
      assertThat( systemUnderTest.notesArea().getText(), is( NOTES ) );
      assertThat( systemUnderTest.headerField().isDisable(), is( false ) );
      assertThat( systemUnderTest.notesArea().isDisable(), is( false ) );
      
      systemUnderTest.selectionRemoved();
      assertThat( systemUnderTest.headerField().getText(), is( nullValue() ) );
      assertThat( systemUnderTest.notesArea().getText(), is( nullValue() ) );
      assertThat( systemUnderTest.headerField().isDisable(), is( true ) );
      assertThat( systemUnderTest.notesArea().isDisable(), is( true ) );
   }//End Method
   
}//End Class
