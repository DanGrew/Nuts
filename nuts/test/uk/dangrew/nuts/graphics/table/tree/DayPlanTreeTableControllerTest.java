package uk.dangrew.nuts.graphics.table.tree;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.TreeItem;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.dayplan.DayPlan;

public class DayPlanTreeTableControllerTest {

   private TreeItem< TreeTableController > selection;
   @Mock private TreeTableController selectionController;
   private DayPlan dayPlan;
   
   private DayPlanTreeTable table;
   private DayPlanTreeTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      dayPlan = new DayPlan( "Plan" );
      table = new DayPlanTreeTable();
      selection = new TreeItem<>( selectionController );
      
      systemUnderTest = new DayPlanTreeTableController();
      systemUnderTest.associate( table );
   }//End Method

   private void select(){
      table.getSelectionModel().select( selection );
   }//End Method
   
   @Test public void shouldShowDayPlan() {
      assertThat( systemUnderTest.getShowing(), is( nullValue() ) );
      systemUnderTest.show( dayPlan );
      assertThat( systemUnderTest.getShowing(), is( dayPlan ) );
   }//End Method
   
   @Test public void shouldAddToDayPlan() {
      assertThat( systemUnderTest.createConcept(), is( nullValue() ) );
      verifyNoMoreInteractions( selectionController );
      
      select();
      assertThat( systemUnderTest.createConcept(), is( nullValue() ) );
      verify( selectionController ).add();
   }//End Method
   
   @Test public void shouldRemoveSelectedConcept() {
      systemUnderTest.removeSelectedConcept();
      verifyNoMoreInteractions( selectionController );
      
      select();
      systemUnderTest.removeSelectedConcept();
      verify( selectionController ).remove();
   }//End Method
   
   @Test public void shouldCopySelectedConcept() {
      systemUnderTest.copySelectedConcept();
      verifyNoMoreInteractions( selectionController );
      
      select();
      systemUnderTest.copySelectedConcept();
      verify( selectionController ).copy();
   }//End Method
   
   @Test public void shouldMoveSelectionUp() {
      systemUnderTest.moveDown();
      verifyNoMoreInteractions( selectionController );
      
      select();
      systemUnderTest.moveDown();
      verify( selectionController ).moveDown();
   }//End Method
   
   @Test public void shouldMoveSelectionDown() {
      systemUnderTest.moveUp();
      verifyNoMoreInteractions( selectionController );
      
      select();
      systemUnderTest.moveUp();
      verify( selectionController ).moveUp();
   }//End Method

}//End Class
