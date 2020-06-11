package uk.dangrew.nuts.graphics.table.tree;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.store.Database;

public class DayPlanTreePaneTest {

   private Database database;
   private DayPlanTreePane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      JavaFxThreading.runAndWait( () -> systemUnderTest = new DayPlanTreePane( database ) );
   }//End Method

   @Test public void shouldContainStructure() {
      assertThat( systemUnderTest.table(), is( notNullValue() ) );
      assertThat( systemUnderTest.controls(), is( notNullValue() ) );
      assertThat( systemUnderTest.getCenter(), is( systemUnderTest.table() ) );
      assertThat( systemUnderTest.getRight(), is( systemUnderTest.controls() ) );
   }//End Method

}//End Class
