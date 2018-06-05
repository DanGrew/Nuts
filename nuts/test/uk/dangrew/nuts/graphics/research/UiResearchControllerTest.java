package uk.dangrew.nuts.graphics.research;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.research.ResearchArticle;
import uk.dangrew.nuts.research.ResearchArticleStore;

public class UiResearchControllerTest {

   private ResearchArticle first;
   private ResearchArticle second;
   
   private ResearchArticleStore store;
   @Mock private UiResearchPane pane;
   private UiResearchController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      store = new ResearchArticleStore();
      first = store.createConcept( "First" );
      second = store.createConcept( "Second" );
      
      systemUnderTest = new UiResearchController( store );
      systemUnderTest.associate( pane );
   }//End Method

   @Test public void shouldProvideAvailableArticles() {
      assertThat( systemUnderTest.articles(), contains( first, second ) );
   }//End Method
   
   @Test public void shouldDisplayResearchArticle() {
      systemUnderTest.show( second );
      verify( pane ).show( second );
      
      assertThat( systemUnderTest.selected().get(), is( second ) );
   }//End Method

}//End Class
