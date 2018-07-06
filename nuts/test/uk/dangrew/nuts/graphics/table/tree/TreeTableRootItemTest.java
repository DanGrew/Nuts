package uk.dangrew.nuts.graphics.table.tree;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanController;

public class TreeTableRootItemTest {

   private DayPlan concept;
   @Mock private DayPlanController parent;
   private TreeTableRootItem systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      concept = new DayPlan( "Anything" );
      systemUnderTest = new TreeTableRootItem( concept, parent );
   }//End Method

   @Test public void shouldBeExpanded() {
      assertThat( systemUnderTest.isExpanded(), is( true ) );
   }//End Method
   
   @Test public void shouldHaveController(){
      assertThat( systemUnderTest.getValue(), is( notNullValue() ) );
      assertThat( systemUnderTest.getValue(), is( instanceOf( TreeTableRootController.class ) ) );
   }//End Method

}//End Class
