package uk.dangrew.nuts.graphics.tutorial.architecture;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.Node;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.utility.mouse.TestMouseEvent;
import uk.dangrew.nuts.graphics.tutorial.database.DatabaseComponents;
import uk.dangrew.nuts.graphics.tutorial.database.DatabaseTutorials;

public class TutorialSelectorTest {

   @Mock private MouseLocationConverter mouseLocationConverter;
   @Captor private ArgumentCaptor< TutorMessageBuilder > messageCaptor;
   
   private TutorialGlass glass;
   private DatabaseComponents components;
   private TutorialSelector systemUnderTest;

   @Before public void initialiseSystemUnderTest() throws InterruptedException {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      glass = spy( new TutorialGlass() );
      components = new DatabaseComponents();
//      TestApplication.launch( () -> components.parent() );
      systemUnderTest = new TutorialSelector( 
               mouseLocationConverter, 
               glass, 
               components 
      );
   }//End Method

   @Test public void shouldDoNothingIfMouseOutsideComponents() {
      fireMouseMovement( null );
      verify( glass, never() ).tutorUser( any() );
   }//End Method
   
   @Test public void shouldDoNothingIfMouseInsideComponentAlreadySelected() {
      shouldTutorUserWhenInNodeForFirstTime();
      
      fireMouseMovement( components.mainTableAddButton() );
      verify( glass, times( 1 ) ).tutorUser( any() );
   }//End Method
   
   @Ignore //nice to have but fiddly to test
   @Test public void shouldClearGlassIfNoGridPresentForSelection() {
      fail( "Not yet implemented" );
   }//End Method
   
   @Test public void shouldTutorUserWhenInNodeForFirstTime() {
      fireMouseMovement( components.mainTableAddButton() );
      verify( glass ).tutorUser( messageCaptor.capture() );
      
      assertThat( messageCaptor.getValue().getComponent(), is( components.mainTableAddButton() ) );
      assertThat( messageCaptor.getValue().getHighlightSubject(), is( components.mainTableAddButton() ) );
   }//End Method

   @Test public void shouldStartTutorial() {
      systemUnderTest.startTutorial( DatabaseTutorials.AddingFoodItemToTable );
      assertThat( glass.getOnMouseMoved(), is( nullValue() ) );
      verify( glass, times( 2 ) ).removeTutorHighlight();
      verify( glass, times( 1 ) ).removeTutorMessage();
   }//End Method
   
   private void fireMouseMovement( Node inside ) {
      if ( inside != null ) {
         when( mouseLocationConverter.containedInScene( any(), eq( inside ) ) ).thenReturn( true );
      }
      glass.getOnMouseMoved().handle( new TestMouseEvent() );
   }//End Method
   
   @Test public void shouldResetSelector(){
      Node parent = components.parent();
      systemUnderTest.resetSelector();
      verify( glass, times( 2 ) ).clearMessageAndHighlight();
      assertThat( components.parent(), is( not( parent ) ) );
      assertThat( glass.getOnMouseMoved(), is( notNullValue() ) );
   }//End Method
   
}//End Class
