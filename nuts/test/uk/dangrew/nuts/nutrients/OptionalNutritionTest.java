package uk.dangrew.nuts.nutrients;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.beans.value.ChangeListener;
import uk.dangrew.kode.launch.TestApplication;

public class OptionalNutritionTest {

   @Mock private ChangeListener< ? super Double > listener;
   @Mock private Nutrition nutrition;
   
   private OptionalNutrition sutPopulated;
   private OptionalNutrition sutUnpopulated;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      sutPopulated = new OptionalNutrition( nutrition );
      sutUnpopulated = new OptionalNutrition();
   }//End Method

   @Test public void shouldProvideNutrition() {
      assertThat( sutPopulated.get(), is( nutrition ) );
      assertThat( sutUnpopulated.get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldListen() {
      assertThat( sutPopulated.listen( listener ), is( true ) );
      verify( nutrition ).listen( listener );
      assertThat( sutUnpopulated.listen( listener ), is( false ) );
      verifyNoMoreInteractions( nutrition );
   }//End Method
   
   @Test public void shouldStopListening() {
      assertThat( sutPopulated.stopListening( listener ), is( true ) );
      verify( nutrition ).stopListening( listener );
      assertThat( sutUnpopulated.stopListening( listener ), is( false ) );
      verifyNoMoreInteractions( nutrition );
   }//End Method

}//End Class
