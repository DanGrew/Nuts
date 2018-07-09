package uk.dangrew.nuts.graphics.table.tree;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanController;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionEvent;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.template.Template;

public class TreeTableRootControllerTest {

   private FoodHolder holder;
   private FoodPortion portion1;
   private FoodPortion portion2;
   
   private DayPlan plan;
   private TreeTableRootItem treeItem;
   @Mock private DayPlanController dayPlanController;
   private TreeTableRootController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      tearDown();
      
      holder = new Meal( "Holder" );
      portion1 = new FoodPortion( new FoodItem( "Item" ), 23.0 );
      portion2 = new FoodPortion( new Meal( "Meal" ), 24.0 );
      new FoodSelectionEvent().register( e -> {
         assertThat( e.getValue().meal(), is( instanceOf( Template.class ) ) );
         e.getValue().meal().portions().add( portion1 );
         e.getValue().meal().portions().add( portion2 );
      } );
      
      plan = new DayPlan( "Anything" );
      treeItem = new TreeTableRootItem( plan, dayPlanController );
      systemUnderTest = new TreeTableRootController( plan, treeItem, dayPlanController );
      treeItem.setValue( systemUnderTest );
   }//End Method
   
   @After public void tearDown(){
      new FoodSelectionEvent().clearAllSubscriptions();
   }//End Method

   @Test public void shouldAddSelectionToDayPlan() {
      systemUnderTest.add();
      verify( dayPlanController ).add( portion1, plan );
      verify( dayPlanController ).add( portion2, plan );
      verifyNoMoreInteractions( dayPlanController );
   }//End Method
   
   @Test public void shouldAddSelectionToHolder() {
      systemUnderTest.requestAddTo( holder );
      verify( dayPlanController ).add( portion1, holder );
      verify( dayPlanController ).add( portion2, holder );
      verifyNoMoreInteractions( dayPlanController );
   }//End Method
   
   @Test public void shouldCopyTheGivenInTheDayPlan() {
      systemUnderTest.copy( portion1 );
      verify( dayPlanController ).add( portion1, plan );
   }//End Method
   
   @Test public void shouldCopyTheGivenInTheGiven() {
      systemUnderTest.copy( portion1, holder );
      verify( dayPlanController ).add( portion1, holder );
   }//End Method
   
   @Test public void shouldRemoveTheGivenTreeItem() {
      TreeTableLeafItem treeItem = new TreeTableLeafItem( portion1, systemUnderTest );
      systemUnderTest.remove( treeItem );
      verify( dayPlanController ).remove( portion1, plan );
   }//End Method

}//End Class
