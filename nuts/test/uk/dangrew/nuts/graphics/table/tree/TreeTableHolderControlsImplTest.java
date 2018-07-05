package uk.dangrew.nuts.graphics.table.tree;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.TreeItem;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.meal.Meal;

public class TreeTableHolderControlsImplTest {

   private FoodPortion concept;
   private FoodHolder holder;
   private TreeItem< TreeTableController > treeItem;
   
   private TreeTableHolderControlsImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      holder = new Meal( "Meal" );
      concept = new FoodPortion( concept, 50 );
      treeItem = new TreeItem<>();
      
      systemUnderTest = new TreeTableHolderControlsImpl( concept, holder, treeItem ) {
         @Override public void requestAddTo( FoodHolder holder ) {}
         @Override public void remove( TreeItem< TreeTableController > treeItem ) {}
         @Override public void remove() {}
         @Override public void moveUp() {}
         @Override public void moveDown() {}
         @Override public void add() {}
         @Override public void copy() {}
         @Override public void copy( FoodPortion concept ) {}
         @Override public void copy( FoodPortion concept, FoodHolder holder ) {}
      };
      treeItem.setValue( systemUnderTest );
   }//End Method

   @Test public void shouldProvideObjectsToChildren() {
      assertThat( systemUnderTest.foodHolder(), is( holder ) );
      assertThat( systemUnderTest.treeItem(), is( treeItem ) );
   }//End Method
   
   @Test public void shouldAddHolderPortion() {
      assertThat( treeItem.getChildren(), is( empty() ) );
      
      FoodPortion portion = new FoodPortion( new Meal( "Another" ), 45.0 );
      holder.portions().add( portion );
      
      TreeItem< TreeTableController > child = treeItem.getChildren().get( 0 );
      assertThat( child.getValue(), is( instanceOf( TreeTableBranchController.class ) ) );
      assertThat( child.getValue().concept(), is( portion ) );
   }//End Method
   
   @Test public void shouldAddLeafPortion() {
      assertThat( treeItem.getChildren(), is( empty() ) );
      
      FoodPortion portion = new FoodPortion( new FoodItem( "Another" ), 45.0 );
      holder.portions().add( portion );
      
      TreeItem< TreeTableController > child = treeItem.getChildren().get( 0 );
      assertThat( child.getValue(), is( instanceOf( TreeTableLeafController.class ) ) );
      assertThat( child.getValue().concept(), is( portion ) );
   }//End Method
   
   @Test public void shouldRemovePortionWhenRemovedFromHolder() {
      assertThat( treeItem.getChildren(), is( empty() ) );
      
      FoodPortion portion = new FoodPortion( new Meal( "Another" ), 45.0 );
      holder.portions().add( portion );
      
      assertThat( treeItem.getChildren(), hasSize( 1 ) );
      holder.portions().remove( portion );
      assertThat( treeItem.getChildren(), hasSize( 0 ) );
   }//End Method
   
   @Test public void shouldMoveUpWhenRequested() {
      FoodPortion portion1 = new FoodPortion( new Meal( "Another" ), 45.0 );
      holder.portions().add( portion1 );
      FoodPortion portion2 = new FoodPortion( new Meal( "Another" ), 45.0 );
      holder.portions().add( portion2 );
      FoodPortion portion3 = new FoodPortion( new Meal( "Another" ), 45.0 );
      holder.portions().add( portion3 );
      
      systemUnderTest.moveUp( portion3 );
      assertThat( holder.portions().get( 0 ), is( portion1 ) );
      assertThat( holder.portions().get( 1 ), is( portion3 ) );
      assertThat( holder.portions().get( 2 ), is( portion2 ) );
      
      systemUnderTest.moveUp( portion1 );
      assertThat( holder.portions().get( 0 ), is( portion1 ) );
      assertThat( holder.portions().get( 1 ), is( portion3 ) );
      assertThat( holder.portions().get( 2 ), is( portion2 ) );
      
      systemUnderTest.moveUp( new FoodPortion() );
      assertThat( holder.portions().get( 0 ), is( portion1 ) );
      assertThat( holder.portions().get( 1 ), is( portion3 ) );
      assertThat( holder.portions().get( 2 ), is( portion2 ) );
   }//End Method
   
   @Test public void shouldMoveDownWhenRequested() {
      FoodPortion portion1 = new FoodPortion( new Meal( "Another" ), 45.0 );
      holder.portions().add( portion1 );
      FoodPortion portion2 = new FoodPortion( new Meal( "Another" ), 45.0 );
      holder.portions().add( portion2 );
      FoodPortion portion3 = new FoodPortion( new Meal( "Another" ), 45.0 );
      holder.portions().add( portion3 );
      
      systemUnderTest.moveDown( portion2 );
      assertThat( holder.portions().get( 0 ), is( portion1 ) );
      assertThat( holder.portions().get( 1 ), is( portion3 ) );
      assertThat( holder.portions().get( 2 ), is( portion2 ) );
      
      systemUnderTest.moveDown( portion2 );
      assertThat( holder.portions().get( 0 ), is( portion1 ) );
      assertThat( holder.portions().get( 1 ), is( portion3 ) );
      assertThat( holder.portions().get( 2 ), is( portion2 ) );
      
      systemUnderTest.moveDown( new FoodPortion() );
      assertThat( holder.portions().get( 0 ), is( portion1 ) );
      assertThat( holder.portions().get( 1 ), is( portion3 ) );
      assertThat( holder.portions().get( 2 ), is( portion2 ) );
   }//End Method
}//End Class
