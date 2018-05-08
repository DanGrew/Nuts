package uk.dangrew.nuts.graphics.progress.custom;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.graph.custom.GraphSeriesVisibility;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class ProgressSeriesGraphVisibilityControllerTest {

   private ProgressSeries series1;
   private ProgressSeries series2;
   
   @Mock private GraphSeriesVisibility visibility;
   private ProgressSeriesGraphVisibilityController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProgressSeriesGraphVisibilityController( visibility );
   }//End Method

   @Test public void shouldProvidePropertyForSeries() {
      assertThat( systemUnderTest.propertyFor( series1 ), is( notNullValue() ) );
      assertThat( systemUnderTest.propertyFor( series1 ), is( systemUnderTest.propertyFor( series1 ) ) );
   }//End Method
   
   @Test public void shouldAddToModelWhenChecked(){
      systemUnderTest.propertyFor( series1 ).set( true );
      verify( visibility ).show( series1 );
   }//End Method
   
   @Test public void shouldRemoveFromModelWhenUnchecked(){
      systemUnderTest.propertyFor( series1 ).set( true );
      verify( visibility ).show( series1 );
      systemUnderTest.propertyFor( series1 ).set( false );
      verify( visibility ).hide( series1 );
   }//End Method
   
}//End Class
