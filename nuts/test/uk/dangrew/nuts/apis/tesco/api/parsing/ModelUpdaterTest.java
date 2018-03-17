package uk.dangrew.nuts.apis.tesco.api.parsing;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.model.api.ProductDetail;

public class ModelUpdaterTest {

   private ProductDetail updateFrom;
   private ProductDetail updateTo;
   private ModelUpdater< ProductDetail > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      updateFrom = new ProductDetail();
      updateTo = new ProductDetail();
      
      systemUnderTest = new ModelUpdater<>( updateFrom );
   }//End Method

   @Test public void shouldAddFromOnePropertyToAnother() {
      assertThat( updateTo.characteristics().isFood().get(), is( nullValue() ) );
      updateFrom.characteristics().isFood().set( true );
      systemUnderTest.set( p -> p.characteristics().isFood(), updateTo );
      assertThat( updateTo.characteristics().isFood().get(), is( true ) );
   }//End Method

}//End Class
