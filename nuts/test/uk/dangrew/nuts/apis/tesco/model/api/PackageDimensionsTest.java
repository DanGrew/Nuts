package uk.dangrew.nuts.apis.tesco.model.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;

public class PackageDimensionsTest {

   private PackageDimensions systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new PackageDimensions();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideDoubleProperty( PackageDimensions::number )
         .shouldProvideDoubleProperty( PackageDimensions::height )
         .shouldProvideDoubleProperty( PackageDimensions::width )
         .shouldProvideDoubleProperty( PackageDimensions::depth )
         .shouldProvideStringProperty( PackageDimensions::dimensionUom )
         .shouldProvideDoubleProperty( PackageDimensions::weight )
         .shouldProvideStringProperty( PackageDimensions::weightUom )
         .shouldProvideDoubleProperty( PackageDimensions::volume )
         .shouldProvideStringProperty( PackageDimensions::volumeUom );
   }//End Method

}//End Class
