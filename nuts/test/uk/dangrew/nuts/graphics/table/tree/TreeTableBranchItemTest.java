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
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.meal.Meal;

public class TreeTableBranchItemTest {

   private FoodPortion portion;
   private FoodHolder concept;
   @Mock private TreeTableHolderControls parent;
   private TreeTableBranchItem systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      portion = new FoodPortion( concept = new Meal( "Holder" ), 125.0 );
      systemUnderTest = new TreeTableBranchItem( portion, concept, parent );
   }//End Method

   @Test public void shouldBeExpanded() {
      assertThat( systemUnderTest.isExpanded(), is( true ) );
   }//End Method
   
   @Test public void shouldHaveController(){
      assertThat( systemUnderTest.getValue(), is( notNullValue() ) );
      assertThat( systemUnderTest.getValue(), is( instanceOf( TreeTableBranchController.class ) ) );
   }//End Method

}//End Class
