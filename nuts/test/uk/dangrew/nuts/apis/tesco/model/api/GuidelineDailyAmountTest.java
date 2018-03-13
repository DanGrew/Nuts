package uk.dangrew.nuts.apis.tesco.model.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;

public class GuidelineDailyAmountTest {

   private GuidelineDailyAmount systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new GuidelineDailyAmount();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideStringProperty( GuidelineDailyAmount::amount )
         .shouldProvideStringProperty( GuidelineDailyAmount::percent )
         .shouldProvideStringProperty( GuidelineDailyAmount::rating );
   }//End Method
   
}//End Class
