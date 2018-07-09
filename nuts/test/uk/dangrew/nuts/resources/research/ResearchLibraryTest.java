package uk.dangrew.nuts.resources.research;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.utility.io.IoCommon;
import uk.dangrew.nuts.research.ResearchArticle;
import uk.dangrew.nuts.research.ResearchArticleStore;

public class ResearchLibraryTest {

   private ResearchArticleStore store;
   
   @Mock private IoCommon ioCommon;
   private ResearchLibrary systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      store = new ResearchArticleStore();
      systemUnderTest = new ResearchLibrary( ioCommon );
   }//End Method

   @Test public void shouldContainArticles() {
      systemUnderTest = new ResearchLibrary();
      systemUnderTest.loadResearch( store );
      
      ResearchArticle article = store.objectList().stream()
         .filter( a -> a.properties().nameProperty().get().equals( ResearchLibrary.ARTICLE_CONTROLLING_INSULIN ) )
         .findFirst()
         .orElse( null );
      assertThat( article, is( notNullValue() ) );
      assertThat( article.content().get(), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldParseArticles() {
      systemUnderTest.loadResearch( store );
      verify( ioCommon ).readFileIntoString( 
               ResearchLibrary.class, 
               systemUnderTest.fileName( ResearchLibrary.ARTICLE_CONTROLLING_INSULIN ) 
      );
   }//End Method

}//End Class

