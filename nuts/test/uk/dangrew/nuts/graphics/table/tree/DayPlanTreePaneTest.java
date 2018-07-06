package uk.dangrew.nuts.graphics.table.tree;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlanController;

public class DayPlanTreePaneTest {

   @Mock private DayPlanController controller;
   private DayPlanTreePane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new DayPlanTreePane( controller );
   }//End Method

   @Test public void shouldContainStructure() {
      assertThat( systemUnderTest.table(), is( notNullValue() ) );
      assertThat( systemUnderTest.controls(), is( notNullValue() ) );
      assertThat( systemUnderTest.getCenter(), is( systemUnderTest.table() ) );
      assertThat( systemUnderTest.getRight(), is( systemUnderTest.controls() ) );
   }//End Method

}//End Class
