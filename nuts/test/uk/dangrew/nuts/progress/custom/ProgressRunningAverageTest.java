package uk.dangrew.nuts.progress.custom;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class ProgressRunningAverageTest {

   private static final LocalDateTime START = LocalDateTime.of( 2018, 4, 23, 3, 22 );
   
   @Mock private BiConsumer< LocalDate, Double > whenAdded;
   
   @Mock private RunningAverageCalculator calculator;
   @Mock private ProgressChangedListener< LocalDate, Double > averageChangedListener;
   private ProgressSeries series;
   private ProgressRunningAverage systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      when( calculator.calculate( Mockito.any(), any() ) ).thenReturn( null );
      series = new ProgressSeries( "Progress" );
      systemUnderTest = new ProgressRunningAverage( calculator, averageChangedListener, series );
   }//End Method

   @Test public void shouldDelegateCalculation() {
      when( calculator.calculate( START.toLocalDate(), series ) ).thenReturn( null, 0.5, 1002.1, null );
      series.values().record(START, 1.1 );
      assertThat( systemUnderTest.averageFor( START.toLocalDate() ), is( nullValue() ) );
      series.values().record(START, 1.2 );
      assertThat( systemUnderTest.averageFor( START.toLocalDate() ), is( 0.5 ) );
      series.values().record(START, 1.1 );
      assertThat( systemUnderTest.averageFor( START.toLocalDate() ), is( 1002.1 ) );
      series.values().record(START, 1.2 );
      assertThat( systemUnderTest.averageFor( START.toLocalDate() ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldRecalculateAverageWhenAdded(){
      series.values().record(START, 1.1 );
      shouldCalculateForAllWithinRunningAverage( 1 );
   }//End Method
   
   @Test public void shouldRecalculateAverageWhenUpdated(){
      series.values().record(START, 1.1 );
      series.values().record(START, 1.2 );
      shouldCalculateForAllWithinRunningAverage( 2 );
   }//End Method
   
   @Test public void shouldRecalculateAverageWhenRemoved(){
      series.values().record(START, 1.1 );
      series.values().record(START, null );
      shouldCalculateForAllWithinRunningAverage( 2 );
   }//End Method
   
   private void shouldCalculateForAllWithinRunningAverage( int times ){
      verify( calculator, times( times ) ).calculate( START.toLocalDate().minusDays( 3 ), series );
      verify( calculator, times( times ) ).calculate( START.toLocalDate().minusDays( 2 ), series );
      verify( calculator, times( times ) ).calculate( START.toLocalDate().minusDays( 1 ), series );
      verify( calculator, times( times ) ).calculate( START.toLocalDate(), series );
      verify( calculator, times( times ) ).calculate( START.toLocalDate().plusDays( 1 ), series );
      verify( calculator, times( times ) ).calculate( START.toLocalDate().plusDays( 2 ), series );
      verify( calculator, times( times ) ).calculate( START.toLocalDate().plusDays( 3 ), series );
   }//End Method
   
   @Test public void shouldNotifyAverageWhenAdded(){
      when( calculator.calculate( START.toLocalDate(), series ) ).thenReturn( 102.3 );
      when( calculator.calculate( START.toLocalDate().minusDays( 2 ), series ) ).thenReturn( 56.8 );
      series.values().record(START, 0.1 );
      
      verify( averageChangedListener ).progressAdded( START.toLocalDate().minusDays( 2 ), 56.8 );
      verify( averageChangedListener ).progressAdded( START.toLocalDate(), 102.3 );
      verifyNoMoreInteractions( averageChangedListener );
   }//End Method
   
   @Test public void shouldNotifyAverageWhenUpdated(){
      when( calculator.calculate( START.toLocalDate(), series ) ).thenReturn( 102.3, 7.6 );
      when( calculator.calculate( START.toLocalDate().minusDays( 2 ), series ) ).thenReturn( 56.8, 123000.0 );
      series.values().record(START, 0.1 );
      
      verify( averageChangedListener ).progressAdded( START.toLocalDate().minusDays( 2 ), 56.8 );
      verify( averageChangedListener ).progressAdded( START.toLocalDate(), 102.3 );
      verifyNoMoreInteractions( averageChangedListener );
      
      series.values().record(START, 0.2 );
      verify( averageChangedListener ).progressUpdated( START.toLocalDate().minusDays( 2 ), 123000.0 );
      verify( averageChangedListener ).progressUpdated( START.toLocalDate(), 7.6 );
      verifyNoMoreInteractions( averageChangedListener );
   }//End Method
   
   @Test public void shouldNotifyAverageWhenRemoved(){
      when( calculator.calculate( START.toLocalDate(), series ) ).thenReturn( 102.3 ).thenReturn( null );
      when( calculator.calculate( START.toLocalDate().minusDays( 2 ), series ) ).thenReturn( 56.8 ).thenReturn( null );
      series.values().record(START, 0.1 );
      
      verify( averageChangedListener ).progressAdded( START.toLocalDate().minusDays( 2 ), 56.8 );
      verify( averageChangedListener ).progressAdded( START.toLocalDate(), 102.3 );
      verifyNoMoreInteractions( averageChangedListener );
      
      series.values().record(START, 0.2 );
      verify( averageChangedListener ).progressRemoved( START.toLocalDate().minusDays( 2 ), 56.8 );
      verify( averageChangedListener ).progressRemoved( START.toLocalDate(), 102.3 );
      verifyNoMoreInteractions( averageChangedListener );
   }//End Method
   
}//End Class
