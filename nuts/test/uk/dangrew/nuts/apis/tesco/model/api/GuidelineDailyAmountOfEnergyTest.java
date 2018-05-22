package uk.dangrew.nuts.apis.tesco.model.api;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class GuidelineDailyAmountOfEnergyTest {

   private GuidelineDailyAmountOfEnergy systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new GuidelineDailyAmountOfEnergy();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideStringProperty( GuidelineDailyAmountOfEnergy::energyInKcal )
         .shouldProvideStringProperty( GuidelineDailyAmountOfEnergy::energyInKj );
   }//End Method
   
}//End Class
