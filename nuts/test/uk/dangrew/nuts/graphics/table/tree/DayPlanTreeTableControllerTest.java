package uk.dangrew.nuts.graphics.table.tree;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.TreeItem;
import uk.dangrew.kode.javafx.tree.TreeStreamer;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.database.RecipeShareControllerImpl;
import uk.dangrew.nuts.graphics.recipe.integration.RecipeGeneratorControllerImpl;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class DayPlanTreeTableControllerTest {

   private TreeItem< TreeTableController > selection;
   private TreeItem< TreeTableController > reorderedSelection;
   @Mock private TreeTableController selectionController;
   @Mock private TreeStreamer treeStreamer;
   @Mock private RecipeShareControllerImpl shareController;
   @Mock private RecipeGeneratorControllerImpl recipeGenerator;
   private FoodPortion concept;
   private Database database;
   private DayPlan dayPlan;
   
   private DayPlanTreeTable table;
   private DayPlanTreeTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      concept = new FoodPortion( new FoodItem( "Item" ), 12 );
      database = new Database();
      dayPlan = new DayPlan( "Plan" );
      table = new DayPlanTreeTable( database );
      table.setFocus( dayPlan );
      
      when( selectionController.concept() ).thenReturn( concept );
      selection = new TreeItem<>( selectionController );
      
      reorderedSelection = new TreeTableLeafItem( concept, mock( TreeTableHolderControls.class ) );
      when( treeStreamer.flatten( table.getRoot() ) ).thenReturn( Stream.of( reorderedSelection ) );
      
      systemUnderTest = new DayPlanTreeTableController( database, treeStreamer, shareController, recipeGenerator );
      systemUnderTest.associate( table );
   }//End Method

   private void select(){
      table.getSelectionModel().select( selection );
   }//End Method
   
   @Test public void shouldShowDayPlan() {
      table.setFocus( null );
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
      
      assertThat( table.getSelectionModel().getSelectedItem(), is( reorderedSelection ) );
   }//End Method
   
   @Test public void shouldMoveSelectionDown() {
      systemUnderTest.moveUp();
      verifyNoMoreInteractions( selectionController );
      
      select();
      systemUnderTest.moveUp();
      verify( selectionController ).moveUp();
      
      assertThat( table.getSelectionModel().getSelectedItem(), is( reorderedSelection ) );
   }//End Method
   
   @Test public void shouldShare(){
      table.getSelectionModel().select( table.getRoot() );
      systemUnderTest.share();
      verify( shareController ).share( dayPlan );
   }//End Method

   @Test public void shouldNotGenerateWithNoSelection(){
      systemUnderTest.generate();
      verifyZeroInteractions( recipeGenerator );
   }//End Method
   
   @Test public void shouldNotGenerateForNonFoodHolder(){
      select();
      systemUnderTest.generate();
      verifyZeroInteractions( recipeGenerator );
   }//End Method
   
   @Test public void shouldGenerate(){
      concept.setFood( new Meal( "Meal" ) );
      select();
      
      Meal holder = new Meal( "Meal" );
      holder.portions().add( new FoodPortion( new FoodItem( "anything" ), 100.0 ) );
      when( recipeGenerator.generateFor( concept.food().get() ) ).thenReturn( Optional.of( holder ) );
      
      systemUnderTest.generate();
      verify( recipeGenerator ).generateFor( concept.food().get() );
      assertThat( 
               ( ( Meal )( concept.food().get() ) ).portions().get( 0 ).food().get().properties().nameProperty().get(), 
               is( holder.portions().get( 0 ).food().get().properties().nameProperty().get() ) 
      );
   }//End Method
   
}//End Class
