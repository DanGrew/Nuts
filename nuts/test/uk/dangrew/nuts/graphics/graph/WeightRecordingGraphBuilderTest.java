package uk.dangrew.nuts.graphics.graph;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class WeightRecordingGraphBuilderTest {

   private WeightRecordingGraphBuilder systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new WeightRecordingGraphBuilder();
   }//End Method

   @Test public void shouldProvideXAxisTitle() {
      assertThat( systemUnderTest.xAxisTitle(), is( nullValue() ) );
      assertThat( systemUnderTest.withXAxisTitle( "anything" ), is( systemUnderTest ) );
      assertThat( systemUnderTest.xAxisTitle(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldProvideXAxisVisibility() {
      assertThat( systemUnderTest.isXAxisVisible(), is( true ) );
      assertThat( systemUnderTest.withXAxisVisible( false ), is( systemUnderTest ) );
      assertThat( systemUnderTest.isXAxisVisible(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideXAxisTickInterval() {
      assertThat( systemUnderTest.xAxisTickInterval(), is( WeightRecordingGraphBuilder.DEFAULT_TICK_INTERVAL ) );
      assertThat( systemUnderTest.withXAxisTickInterval( 78 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.xAxisTickInterval(), is( 78.0 ) );
   }//End Method
   
   @Test public void shouldProvideYAxisTitle() {
      assertThat( systemUnderTest.yAxisTitle(), is( nullValue() ) );
      assertThat( systemUnderTest.withYAxisTitle( "anything" ), is( systemUnderTest ) );
      assertThat( systemUnderTest.yAxisTitle(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldProvideYAxisVisibility() {
      assertThat( systemUnderTest.isYAxisVisible(), is( true ) );
      assertThat( systemUnderTest.withYAxisVisible( false ), is( systemUnderTest ) );
      assertThat( systemUnderTest.isYAxisVisible(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideChartTitle() {
      assertThat( systemUnderTest.chartTitle(), is( nullValue() ) );
      assertThat( systemUnderTest.withChartTitle( "anything" ), is( systemUnderTest ) );
      assertThat( systemUnderTest.chartTitle(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldProvideChartXTranslation() {
      assertThat( systemUnderTest.chartXTranslation(), is( 0.0 ) );
      assertThat( systemUnderTest.withChartXTranslation( 78 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.chartXTranslation(), is( 78.0 ) );
   }//End Method
   
   @Test public void shouldProvideYAxisTranslation() {
      assertThat( systemUnderTest.yAxisTranslation(), is( 0.0 ) );
      assertThat( systemUnderTest.withYAxisTranslation( 78 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.yAxisTranslation(), is( 78.0 ) );
   }//End Method
   
}//End Class
