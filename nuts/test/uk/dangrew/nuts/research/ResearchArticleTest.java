package uk.dangrew.nuts.research;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class ResearchArticleTest {

   private static final String ID = "some id";
   private static final String NAME = "some name";
   private ResearchArticle systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ResearchArticle( ID, NAME );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.properties().id(), is( ID ) );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( NAME ) );
   }//End Method
   
   @Test public void shouldProvideConceptsAssociated(){
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideProperty( ResearchArticle::content, "anything" );
   }//End Method

}//End Class
