package uk.dangrew.nuts.graphics.table.tree;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.meal.Meal;

public class TreeTableBranchControllerTest {

   private FoodPortion portion;
   private FoodHolder anotherHolder;
   
   private FoodPortion concept;
   private FoodHolder holder;
   @Mock private TreeTableBranchItem treeItem;
   @Mock private TreeTableHolderControls parent;
   
   private TreeTableBranchController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      portion = new FoodPortion( new FoodItem( "Anything" ), 23.4 );
      holder = new Meal( "Another Holder" );
      
      holder = new Meal( "Meal" );
      concept = new FoodPortion( concept, 50 );
      treeItem = new TreeTableBranchItem( concept, holder, parent );
      
      systemUnderTest = new TreeTableBranchController(
               concept,
               holder,
               treeItem,
               parent
      );
      treeItem.setValue( systemUnderTest );
   }//End Method

   @Test public void shouldAddNewItemToHolder() {
      systemUnderTest.add();
      verify( parent ).requestAddTo( holder );
   }//End Method
   
   @Test public void shouldAddNewItemToGivenHolder() {
      systemUnderTest.requestAddTo( anotherHolder );
      verify( parent ).requestAddTo( anotherHolder );
   }//End Method
   
   @Test public void shouldCopyConceptInParent() {
      systemUnderTest.copy();
      verify( parent ).copy( concept );
   }//End Method
   
   @Test public void shouldCopyGivenPortionInHolder() {
      systemUnderTest.copy( portion );
      verify( parent ).copy( portion, holder );
   }//End Method
   
   @Test public void shouldCopyGivenInGivenHolder() {
      systemUnderTest.copy( portion, anotherHolder );
      verify( parent ).copy( portion, anotherHolder );
   }//End Method
   
   @Test public void shouldRemove() {
      systemUnderTest.remove();
      verify( parent ).remove( treeItem );
   }//End Method
   
   @Test public void shouldRemoveGiven() {
      systemUnderTest.remove( treeItem );
      verify( parent ).remove( treeItem );
   }//End Method
   
   @Test public void shouldMoveUpInParent() {
      systemUnderTest.moveUp();
      verify( parent ).moveUp( concept );
   }//End Method
   
   @Test public void shouldMoveDownInParent() {
      systemUnderTest.moveDown();
      verify( parent ).moveDown( concept );
   }//End Method

}//End Class
