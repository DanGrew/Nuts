package uk.dangrew.nuts.progress.custom;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class SeriesValuesTest {

   private SeriesValues systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new SeriesValues();
   }//End Method
   
   @Test public void shouldProvideProgressForRangeInHours(){
      LocalDateTime t1 = LocalDateTime.of( 2018, 4, 20, 4, 45 );
      LocalDateTime t2 = t1.plusHours( 1 );
      LocalDateTime t3 = t1.plusHours( 2 );
      LocalDateTime t4 = t1.plusHours( 3 );
      LocalDateTime t5 = t1.plusHours( 4 );
      double v1 = 1223.2;
      double v2 = 95.2;
      double v3 = 0.1;
      double v4 = 578.4;
      double v5 = -34;
      
      systemUnderTest.record( t1, v1 );
      systemUnderTest.record( t2, v2 );
      systemUnderTest.record( t3, v3 );
      systemUnderTest.record( t4, v4 );
      systemUnderTest.record( t5, v5 );
      
      Map< LocalDateTime, Double > subMap = systemUnderTest.range( t1, t5 );
      assertThat( subMap, hasEntry( t1, v1 ) );
      assertThat( subMap, hasEntry( t2, v2 ) );
      assertThat( subMap, hasEntry( t3, v3 ) );
      assertThat( subMap, hasEntry( t4, v4 ) );
      assertThat( subMap, hasEntry( t5, v5 ) );
      
      subMap = systemUnderTest.range( t2, t4 );
      assertThat( subMap, not( hasEntry( t1, v1 ) ) );
      assertThat( subMap, hasEntry( t2, v2 ) );
      assertThat( subMap, hasEntry( t3, v3 ) );
      assertThat( subMap, hasEntry( t4, v4 ) );
      assertThat( subMap, not( hasEntry( t5, v5 ) ) );
      
      subMap = systemUnderTest.range( t2.toLocalDate() );
      assertThat( subMap, hasEntry( t1, v1 ) );
      assertThat( subMap, hasEntry( t2, v2 ) );
      assertThat( subMap, hasEntry( t3, v3 ) );
      assertThat( subMap, hasEntry( t4, v4 ) );
      assertThat( subMap, hasEntry( t5, v5 ) );
   }//End Method

}//End Class
