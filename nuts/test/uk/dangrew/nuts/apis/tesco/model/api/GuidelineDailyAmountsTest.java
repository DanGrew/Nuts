package uk.dangrew.nuts.apis.tesco.model.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class GuidelineDailyAmountsTest {

   private GuidelineDailyAmounts systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new GuidelineDailyAmounts();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideObject( GuidelineDailyAmounts::energyGda )
         .shouldProvideObject( GuidelineDailyAmounts::fatGda )
         .shouldProvideObject( GuidelineDailyAmounts::saturatesGda )
         .shouldProvideObject( GuidelineDailyAmounts::sugarsGda )
         .shouldProvideObject( GuidelineDailyAmounts::saltGda );
   }//End Method

}//End Class
