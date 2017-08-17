package uk.dangrew.nuts.graphics.graph;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.progress.WeighIn;

public class WeightRecordingGraphSeriesBuilderTest {

   private WeightRecordingGraphSeriesBuilder systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new WeightRecordingGraphSeriesBuilder();
   }//End Method

   @Test public void shouldProvideSeriesName() {
      assertThat( systemUnderTest.seriesName(), is( nullValue() ) );
      assertThat( systemUnderTest.withName( "anything" ), is( systemUnderTest ) );
      assertThat( systemUnderTest.seriesName(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldProvidePropertyRetriever() {
      Function< WeighIn, ObjectProperty< Double > > retriever = mock( Function.class );
      assertThat( systemUnderTest.propertyRetriever(), is( nullValue() ) );
      assertThat( systemUnderTest.forProperty( retriever ), is( systemUnderTest ) );
      assertThat( systemUnderTest.propertyRetriever(), is( retriever ) );
   }//End Method

}//End Class
