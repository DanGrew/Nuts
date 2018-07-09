package uk.dangrew.nuts.apis.tesco.model.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class ProductCharacteristicsTest {

   private ProductCharacteristics systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProductCharacteristics();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideBooleanProperty( ProductCharacteristics::isFood )
         .shouldProvideBooleanProperty( ProductCharacteristics::isDrink )
         .shouldProvideDoubleProperty( ProductCharacteristics::healthScore )
         .shouldProvideBooleanProperty( ProductCharacteristics::isHazardous )
         .shouldProvideStringProperty( ProductCharacteristics::storageType )
         .shouldProvideBooleanProperty( ProductCharacteristics::isNonLiquidAnalgesic )
         .shouldProvideBooleanProperty( ProductCharacteristics::containsLoperamide );
   }//End Method

}//End Class
