package uk.dangrew.nuts.graphics.table.tree;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

public class TreeTableLeafControllerTest {

   private FoodPortion concept;
   private TreeTableLeafItem treeItem;
   @Mock private TreeTableHolderControls parent;
   private TreeTableLeafController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      concept = new FoodPortion( new FoodItem( "Item" ), 125.0 );
      treeItem = new TreeTableLeafItem( concept, parent );
      systemUnderTest = new TreeTableLeafController( concept, treeItem, parent );
      treeItem.setValue( systemUnderTest );
   }//End Method

   @Test public void shouldDelegateToParent() {
      systemUnderTest.add();
      verify( parent ).add();
      
      systemUnderTest.copy();
      verify( parent ).copy( concept );
      
      systemUnderTest.remove();
      verify( parent ).remove( treeItem );
      
      systemUnderTest.moveUp();
      verify( parent ).moveUp( concept );
      
      systemUnderTest.moveDown();
      verify( parent ).moveDown( concept );
   }//End Method

}//End Class
