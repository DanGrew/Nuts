package uk.dangrew.nuts.graphics.label;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.store.Database;

public class UiLabelCreationControlsTest {

   private UiLabelController controller;
   private UiLabelCreationControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      controller = spy( new UiLabelController( new Database() ) );
      systemUnderTest = new UiLabelCreationControls( controller );
   }//End Method

   @Test public void shouldUpdateNameFieldWithSelection() {
      assertThat( systemUnderTest.nameField().getText(), is( "" ) );
      controller.selectLabel( new Label( "anything" ) );
      assertThat( systemUnderTest.nameField().getText(), is( "anything" ) );
      controller.selectLabel( null );
      assertThat( systemUnderTest.nameField().getText(), is( "" ) );
   }//End Method
   
   @Test public void shouldChangeName() {
      systemUnderTest.nameField().setText( "anything" );
      systemUnderTest.changeNameButton().fire();
      verify( controller ).changeName( "anything" );
   }//End Method
   
   @Test public void shouldCreateLabel() {
      systemUnderTest.nameField().setText( "anything" );
      systemUnderTest.createLabelButton().fire();
      verify( controller ).createLabel( "anything" );
   }//End Method
   
   @Test public void shouldDeleteLabel() {
      systemUnderTest.deleteLabelButton().fire();
      verify( controller ).deleteLabel();
   }//End Method

}//End Class
