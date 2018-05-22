package uk.dangrew.nuts.label;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;
import uk.dangrew.nuts.system.Concept;

public class LabelTest {

   private static final String ID = "some id";
   private static final String NAME = "some name";
   private Label systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new Label( ID, NAME );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.properties().id(), is( ID ) );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( NAME ) );
   }//End Method
   
   @Test public void shouldProvideConceptsAssociated(){
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideCollection( Label::concepts, mock( Concept.class ) );
   }//End Method

}//End Class
