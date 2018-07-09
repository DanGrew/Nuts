package uk.dangrew.nuts.graphics.template;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.event.structure.Event;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionEvent;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionRequest;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.template.Template;

public class TemplateTableControllerTest {

   private Template template;
   private Meal meal;
   
   @Captor private ArgumentCaptor< Event< FoodSelectionRequest > > eventCaptor;
   @Mock private FoodSelectionEvent selectionEvents;
   private TemplateTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      meal = new Meal( "Meal" );
      template = new Template( "Template" );
      systemUnderTest = new TemplateTableController( selectionEvents );
   }//End Method

   @Test public void shouldFireSelectionEventWhenShowingTemplate() {
      systemUnderTest.showMeal( template );
      systemUnderTest.createConcept();
      verify( selectionEvents ).fire( eventCaptor.capture() );
      
      assertThat( eventCaptor.getValue().getValue().meal(), is( template ) );
   }//End Method
   
   @Test public void shouldNotFireSelectionEventWhenShowingMeal() {
      systemUnderTest.showMeal( meal );
      systemUnderTest.createConcept();
      verify( selectionEvents, never() ).fire( Mockito.any() );
   }//End Method
   
   @Test public void shouldNotFireSelectionEventWhenNotShowingAnything() {
      systemUnderTest.createConcept();
      verify( selectionEvents, never() ).fire( Mockito.any() );
   }//End Method

}//End Class
