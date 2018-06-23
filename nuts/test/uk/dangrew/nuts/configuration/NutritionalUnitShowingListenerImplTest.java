package uk.dangrew.nuts.configuration;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class NutritionalUnitShowingListenerImplTest {

   @Mock private Consumer< NutritionalUnit > show;
   @Mock private Consumer< NutritionalUnit > hide;
   private NutritionalUnitShowingListener systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new NutritionalUnitShowingListenerImpl( show, hide );
   }//End Method

   @Test public void shouldNotify() {
      systemUnderTest.show( NutritionalUnit.Calories );
      verify( show ).accept( NutritionalUnit.Calories );
      verify( hide, never() ).accept( NutritionalUnit.Calories );
      
      systemUnderTest.hide( NutritionalUnit.Calories );
      verify( show ).accept( NutritionalUnit.Calories );
      verify( hide ).accept( NutritionalUnit.Calories );
   }//End Method

}//End Class
