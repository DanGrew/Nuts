package uk.dangrew.nuts.apis.tesco.model.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class ProductDetailTest {

   private ProductDetail systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProductDetail();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideStringProperty( ProductDetail::gtin )
         .shouldProvideStringProperty( ProductDetail::tpnb )
         .shouldProvideCollection( ProductDetail::tpncs )
         .shouldProvideStringProperty( ProductDetail::description )
         .shouldProvideStringProperty( ProductDetail::brand )
         .shouldProvideObject( ProductDetail::quantityContents )
         .shouldProvideObject( ProductDetail::characteristics )
         .shouldProvideCollection( ProductDetail::gdas, new GuidelineDailyAmountReference() )
         .shouldProvideObject( ProductDetail::nutrition )
         .shouldProvideCollection( ProductDetail::storageInstructions )
         .shouldProvideStringProperty( ProductDetail::marketingTextProperty )
         .shouldProvideObject( ProductDetail::packageDimensions );
   }//End Method

}//End Class
