package uk.dangrew.nuts.apis.tesco.model.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class QuantityContentsTest {

   private QuantityContents systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new QuantityContents();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideStringProperty( QuantityContents::averageMeasure )
         .shouldProvideStringProperty( QuantityContents::netContents )
         .shouldProvideDoubleProperty( QuantityContents::quantity )
         .shouldProvideStringProperty( QuantityContents::quantityUom )
         .shouldProvideDoubleProperty( QuantityContents::totalQuantity );
   }//End Method

}//End Class
