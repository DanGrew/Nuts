package uk.dangrew.nuts.nutrients;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.kode.launch.TestApplication;

public class OptionalNutritionalUnitTest {

   @Mock private ChangeListener< ? super Double > listener;
   @Mock private ObjectProperty< Double > property;
   
   private OptionalNutritionalUnit sutPopulated;
   private OptionalNutritionalUnit sutUnpopulated;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      when( property.get() ).thenReturn( 20.0 );
      sutPopulated = new OptionalNutritionalUnit( property );
      sutUnpopulated = new OptionalNutritionalUnit();
   }//End Method
   
   @Test public void shouldProvidePresence(){
      assertThat( sutPopulated.isPresent(), is( true ) );
      assertThat( sutUnpopulated.isPresent(), is( false ) );
   }//End Method

   @Test public void shouldProvideValue() {
      assertThat( sutPopulated.get(), is( 20.0 ) );
      assertThat( sutUnpopulated.get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideProperty() {
      assertThat( sutPopulated.property(), is( property ) );
      assertThat( sutUnpopulated.property(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldSet() {
      assertThat( sutPopulated.set( 45.0 ), is( true ) );
      verify( property ).set( 45.0 );
      assertThat( sutUnpopulated.set( 45.0 ), is( false ) );
      verifyNoMoreInteractions( property );
   }//End Method
   
   @Test public void shouldListen() {
      assertThat( sutPopulated.listen( listener ), is( true ) );
      verify( property ).addListener( listener );
      assertThat( sutUnpopulated.listen( listener ), is( false ) );
      verifyNoMoreInteractions( property );
   }//End Method
   
   @Test public void shouldStopListening() {
      assertThat( sutPopulated.stopListening( listener ), is( true ) );
      verify( property ).removeListener( listener );
      assertThat( sutUnpopulated.stopListening( listener ), is( false ) );
      verifyNoMoreInteractions( property );
   }//End Method

}//End Class
