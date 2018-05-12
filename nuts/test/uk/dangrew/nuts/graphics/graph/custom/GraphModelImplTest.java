package uk.dangrew.nuts.graphics.graph.custom;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class GraphModelImplTest {

   private ProgressSeries progress;
   private GraphModelImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      progress = new ProgressSeries( "Anything" );
      for ( int i = 0; i < 7; i++ ) {
         progress.values().record( LocalDateTime.now().plusDays( i ), i* 10.0 );
      }
      
      systemUnderTest = new GraphModelImpl(
               progress 
      );
   }//End Method
   
   @Test public void shouldProvideModelName(){
      assertThat( systemUnderTest.modelName(), is( progress.properties().nameProperty().get() ) );
   }//End Method
   
   @Test public void shouldProvideSeries(){
      assertThat( systemUnderTest.series().getName(), is( systemUnderTest.modelName() ) );
      for ( LocalDateTime record : progress.entries() ) {
         assertThat( systemUnderTest.dataFor( record ), is( notNullValue() ) );
         assertThat( systemUnderTest.dataFor( record ).getXValue().longValue(), is( record.toEpochSecond( ZoneOffset.UTC ) ) );
         assertThat( systemUnderTest.dataFor( record ).getYValue(), is( progress.values().entryFor( record ) ) );
      }
   }//End Method
   
   @Test public void shouldRemoveDataWhenSetToNull() {
      LocalDateTime subject = progress.entries().iterator().next();
      progress.values().record( subject, null );
      
      assertThat( systemUnderTest.dataFor( subject ), is( nullValue() ) );
      assertThat( systemUnderTest.series().getData(), hasSize( 6 ) );
   }//End Method
   
   @Test public void shouldAddDataWhenSetToNonNullNonZero() {
      LocalDateTime subject = LocalDateTime.now().plusDays( 89 );
      progress.values().record( subject, 20.0 );
      
      assertThat( systemUnderTest.dataFor( subject ), is( notNullValue() ) );
      assertThat( systemUnderTest.series().getData(), hasSize( 8 ) );
      assertThat( systemUnderTest.series().getData().get( 7 ).getXValue().longValue(), is( subject.toEpochSecond( ZoneOffset.UTC ) ) );
      assertThat( systemUnderTest.series().getData().get( 7 ).getYValue(), is( 20.0 ) );
   }//End Method
   
   @Test public void shouldSortDataWhenAdded() {
      LocalDateTime subject = LocalDateTime.now().plusDays( 1 ).plusHours( 1 );
      progress.values().record( subject, 20.0 );
      
      assertThat( systemUnderTest.dataFor( subject ), is( notNullValue() ) );
      assertThat( systemUnderTest.series().getData(), hasSize( 8 ) );
      assertThat( systemUnderTest.series().getData().get( 2 ), is( systemUnderTest.dataFor( subject ) ) );
   }//End Method
   
   @Test public void shouldShowAndHideByEmptyingPoints(){
      assertThat( systemUnderTest.series().getData(), is( not( empty() ) ) );
      systemUnderTest.hide();
      assertThat( systemUnderTest.series().getData(), is( empty() ) );
      systemUnderTest.show();
      assertThat( systemUnderTest.series().getData(), is( not( empty() ) ) );
   }//End Method

}//End Class
