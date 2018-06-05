package uk.dangrew.nuts.graphics.research;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.text.TextFlow;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.textflow.model.TextFlowParser;
import uk.dangrew.nuts.research.ResearchArticle;
import uk.dangrew.nuts.research.ResearchArticleStore;

public class UiResearchPaneTest {

   @Mock private TextFlowParser parser;
   private ResearchArticleStore store;
   private UiResearchPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      store = new ResearchArticleStore();
      systemUnderTest = new UiResearchPane( parser, store );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      ResearchArticleStore store = new ResearchArticleStore();
      
      TestApplication.launch( () -> new UiResearchPane( store ) );
      
      Thread.sleep( 99999999 );
   }//End Method

   @Test public void shouldParseAndDisplay() {
      ResearchArticle sample = store.objectList().get( 0 );
      
      TextFlow flow = new TextFlow();
      when( parser.parse( sample.content().get() ) )
         .thenReturn( Optional.of( flow ) )
         .thenReturn( Optional.empty() );
      
      systemUnderTest.show( sample );
      assertThat( systemUnderTest.getCenter(), is( flow ) );
      
      systemUnderTest.show( sample );
      assertThat( systemUnderTest.getCenter(), is( nullValue() ) );
   }//End Method
   
}//End Class
