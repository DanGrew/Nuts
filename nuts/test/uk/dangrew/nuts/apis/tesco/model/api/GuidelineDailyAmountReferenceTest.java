package uk.dangrew.nuts.apis.tesco.model.api;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;

public class GuidelineDailyAmountReferenceTest {

   private GuidelineDailyAmountReference systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new GuidelineDailyAmountReference();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideObservableList( GuidelineDailyAmountReference::headers )
         .shouldProvideObservableList( GuidelineDailyAmountReference::footers )
         .shouldProvideObject( GuidelineDailyAmountReference::gda );
   }//End Method
   
   @Test public void shouldSetDescription(){
      assertThat( systemUnderTest.description(), is( nullValue() ) );
      systemUnderTest.setDescription( "anything" );
      assertThat( systemUnderTest.description(), is( "anything" ) );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowDescriptionToChange(){
      systemUnderTest.setDescription( "anything" );
      systemUnderTest.setDescription( "else" );
   }//End Method
}//End Class