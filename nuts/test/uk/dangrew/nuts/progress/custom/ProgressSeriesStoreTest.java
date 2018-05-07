package uk.dangrew.nuts.progress.custom;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ProgressSeriesStoreTest {

   private ProgressSeries series;
   private ProgressSeriesStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      series = new ProgressSeries( "Food" );
      systemUnderTest = new ProgressSeriesStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( series.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( series );
      assertThat( systemUnderTest.get( series.properties().id() ), is( series ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      ProgressSeries series = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( series.properties().id() ), is( series ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( series );
      assertThat( systemUnderTest.get( series.properties().id() ), is( series ) );
      systemUnderTest.removeConcept( series );
      assertThat( systemUnderTest.get( series.properties().id() ), is( nullValue() ) );
   }//End Method
   
}//End Class
