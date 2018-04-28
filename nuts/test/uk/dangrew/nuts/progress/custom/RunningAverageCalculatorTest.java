package uk.dangrew.nuts.progress.custom;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class RunningAverageCalculatorTest {

   private static final LocalDateTime START = LocalDateTime.of( 2018, 4, 23, 3, 22 );
   
   private ProgressSeries series;
   private RunningAverageCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      series = new ProgressSeries( "Series" );
      systemUnderTest = new RunningAverageCalculator();
   }//End Method

   @Test public void shouldComputeAverageOver7DaysForSingleEntries() {
      series.record( START.minusDays( 3 ), 97.0 );
      series.record( START.minusDays( 2 ), 98.0 );
      series.record( START.minusDays( 1 ), 99.0 );
      series.record( START, 100.0 );
      series.record( START.plusDays( 1 ), 101.0 );
      series.record( START.plusDays( 2 ), 102.0 );
      series.record( START.plusDays( 3 ), 110.0 );
      
      assertThat( systemUnderTest.calculate( START.toLocalDate(), series ), is( 101.0 ) );
   }//End Method
   
   @Test public void shouldComputeAverageForEachDayOver7Days() {
      series.record( START.minusDays( 3 ), 97.0 );
      series.record( START.minusDays( 2 ), 98.0 );
      series.record( START.minusDays( 1 ), 99.0 );
      series.record( START, 100.0 );
      series.record( START.plusDays( 1 ), 101.0 );
      series.record( START.plusDays( 2 ), 102.0 );
      
      series.record( START.plusDays( 3 ), 125.0 );
      series.record( START.plusDays( 3 ).plusHours( 1 ), 110.0 );
      series.record( START.plusDays( 3 ).plusHours( 2 ), 105.0 );
      series.record( START.plusDays( 3 ).plusHours( 3 ), 100.0 );
      
      assertThat( systemUnderTest.calculate( START.toLocalDate(), series ), is( 101.0 ) );
   }//End Method
   
   @Test public void shouldNotComputeAverageWhereThereAreNoEntries() {
      assertThat( systemUnderTest.calculate( START.toLocalDate(), series ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldNotComputeAverageWhereThereAreNotEnoughEntriesBelow() {
//      series.record( START.minusDays( 3 ), 97.0 );
      series.record( START.minusDays( 2 ), 98.0 );
      series.record( START.minusDays( 1 ), 99.0 );
      series.record( START, 100.0 );
      series.record( START.plusDays( 1 ), 101.0 );
      series.record( START.plusDays( 2 ), 102.0 );
      series.record( START.plusDays( 3 ), 110.0 );
      
      assertThat( systemUnderTest.calculate( START.toLocalDate(), series ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldNotComputeAverageWhereThereAreNotEnoughEntriesAbove() {
      series.record( START.minusDays( 3 ), 97.0 );
      series.record( START.minusDays( 2 ), 98.0 );
      series.record( START.minusDays( 1 ), 99.0 );
      series.record( START, 100.0 );
      series.record( START.plusDays( 1 ), 101.0 );
      series.record( START.plusDays( 2 ), 102.0 );
//      series.record( START.plusDays( 3 ), 110.0 );
      
      assertThat( systemUnderTest.calculate( START.toLocalDate(), series ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldOnlyIncludeDaysInRangeInAverage() {
      series.record( START.minusDays( 3 ), 97.0 );
      series.record( START.minusDays( 2 ), 98.0 );
      series.record( START.minusDays( 1 ), 99.0 );
      series.record( START, 100.0 );
      series.record( START.plusDays( 1 ), 101.0 );
      series.record( START.plusDays( 2 ), 102.0 );
      series.record( START.plusDays( 3 ), 110.0 );
      
      series.record( START.minusDays( 4 ), 10000.0 );
      series.record( START.plusDays( 4 ), 10000.0 );
      
      assertThat( systemUnderTest.calculate( START.toLocalDate(), series ), is( 101.0 ) );
   }//End Method

}//End Class
