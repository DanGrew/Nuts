package uk.dangrew.nuts.apis.tesco.model.api;

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
         .shouldProvideStringProperty( GuidelineDailyAmountReference::description )  
         .shouldProvideCollection( GuidelineDailyAmountReference::headers )
         .shouldProvideCollection( GuidelineDailyAmountReference::footers )
         .shouldProvideObject( GuidelineDailyAmountReference::gda );
   }//End Method
   
}//End Class