package uk.dangrew.nuts.research;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ResearchArticleStoreTest {

   private ResearchArticle article;
   private ResearchArticleStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      article = new ResearchArticle( "article" );
      systemUnderTest = new ResearchArticleStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( article.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( article );
      assertThat( systemUnderTest.get( article.properties().id() ), is( article ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      ResearchArticle newlabel = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newlabel.properties().id() ), is( newlabel ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( article );
      assertThat( systemUnderTest.get( article.properties().id() ), is( article ) );
      systemUnderTest.removeConcept( article );
      assertThat( systemUnderTest.get( article.properties().id() ), is( nullValue() ) );
   }//End Method
   
}//End Class
