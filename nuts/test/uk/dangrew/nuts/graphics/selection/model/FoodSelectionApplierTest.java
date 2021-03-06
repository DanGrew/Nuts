package uk.dangrew.nuts.graphics.selection.model;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.stage.Stage;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionApplier;
import uk.dangrew.nuts.meal.Meal;

public class FoodSelectionApplierTest {

   private FoodItemStore foodItems;
   private Meal focus;
   @Mock private Stage stage;
   private FoodSelectionApplier systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      foodItems = new FoodItemStore();
      focus = new Meal( "Focus" );
      systemUnderTest = new FoodSelectionApplier( stage, foodItems );
      
      systemUnderTest.focus( focus );
   }//End Method

   @Test public void shouldHideStageOnCancel() {
      systemUnderTest.cancel();
      verify( stage ).hide();
   }//End Method
   
   @Test public void shouldAddCopiesOfSelectedAndHideStageOnApply() {
      FoodPortion portion1 = new FoodPortion( new FoodItem( "Food1" ), 100 );
      FoodPortion portion2 = new FoodPortion( new FoodItem( "Food2" ), 34 );
      FoodPortion portion3 = new FoodPortion( new FoodItem( "Food3" ), 132 );
      systemUnderTest.apply( Arrays.asList( portion1, portion2, portion3 ) );
      
      assertThat( focus.portions(), hasSize( 3 ) );
      assertThat( focus.portions().get( 0 ).food().get(), is( portion1.food().get() ) );
      assertThat( focus.portions().get( 0 ).portion().get(), is( 100.0 ) );
      assertThat( focus.portions().get( 0 ), is( not( portion1 ) ) );
      assertThat( focus.portions().get( 1 ).food().get(), is( portion2.food().get() ) );
      assertThat( focus.portions().get( 1 ).portion().get(), is( 34.0 ) );
      assertThat( focus.portions().get( 1 ), is( not( portion1 ) ) );
      assertThat( focus.portions().get( 2 ).food().get(), is( portion3.food().get() ) );
      assertThat( focus.portions().get( 2 ).portion().get(), is( 132.0 ) );
      assertThat( focus.portions().get( 2 ), is( not( portion1 ) ) );
      
      verify( stage ).hide();
   }//End Method
   
   @Test public void shouldNotApplyWithNoFocus(){
      systemUnderTest.focus( null );
      
      FoodPortion portion1 = new FoodPortion( new FoodItem( "Food1" ), 100 );
      FoodPortion portion2 = new FoodPortion( new FoodItem( "Food2" ), 34 );
      FoodPortion portion3 = new FoodPortion( new FoodItem( "Food3" ), 132 );
      systemUnderTest.apply( Arrays.asList( portion1, portion2, portion3 ) );
      
      assertThat( focus.portions(), is( empty() ) );
      verify( stage ).hide();
   }//End Method
   
   @Test public void shouldAppendPortionsWithoutReplacing(){
      focus.portions().add( new FoodPortion() );
      focus.portions().add( new FoodPortion() );
      
      systemUnderTest.focus( focus );
      FoodPortion portion1 = new FoodPortion( new FoodItem( "Food1" ), 100 );
      systemUnderTest.apply( Arrays.asList( portion1 ) );
      
      assertThat( focus.portions(), hasSize( 3 ) );
   }//End Method
   
   @Test public void shouldAddFoodItemNotPresentInDatabase(){
      FoodPortion portion1 = new FoodPortion( new FoodItem( "Food1" ), 100 );
      FoodPortion portion2 = new FoodPortion( new FoodItem( "Food2" ), 34 );
      FoodPortion portion3 = new FoodPortion( new FoodItem( "Food3" ), 132 );
      systemUnderTest.apply( Arrays.asList( portion1, portion2, portion3 ) );
      
      assertThatFoodStoreHasFoodItem( portion1.food().get() );
      assertThatFoodStoreHasFoodItem( portion2.food().get() );
      assertThatFoodStoreHasFoodItem( portion3.food().get() );
      
      assertThat( focus.portions().get( 0 ).food().get(), is( foodItems.objectList().get( 0 ) ) );
      assertThat( focus.portions().get( 1 ).food().get(), is( foodItems.objectList().get( 1 ) ) );
      assertThat( focus.portions().get( 2 ).food().get(), is( foodItems.objectList().get( 2 ) ) );
   }//End Method
   
   @Test public void shouldNotDuplicateFoodWithSameExactName(){
      FoodPortion portion1 = new FoodPortion( new FoodItem( "Food1" ), 100 );
      FoodPortion portion2 = new FoodPortion( new FoodItem( "Food2" ), 34 );
      FoodPortion portion3 = new FoodPortion( new FoodItem( "Food3" ), 132 );
      
      foodItems.createConcept( "Food2" );
      systemUnderTest.apply( Arrays.asList( portion1, portion2, portion3 ) );
      
      assertThat( foodItems.objectList(), hasSize( 3 ) );
      assertThatFoodStoreHasFoodItem( portion1.food().get() );
      assertThatFoodStoreHasFoodItem( portion2.food().get() );
      assertThatFoodStoreHasFoodItem( portion3.food().get() );
      
      assertThat( focus.portions().get( 0 ).food().get(), is( foodItems.objectList().get( 1 ) ) );
      assertThat( focus.portions().get( 1 ).food().get(), is( foodItems.objectList().get( 0 ) ) );
      assertThat( focus.portions().get( 2 ).food().get(), is( foodItems.objectList().get( 2 ) ) );
   }//End Method
   
   private void assertThatFoodStoreHasFoodItem( Food item ) {
      assertThat( foodItems.objectList().stream().anyMatch( 
               f -> f.properties().nameProperty().get().equals( 
                        item.properties().nameProperty().get() 
               )
      ), is( true ) );
   }//End Method
   
   @Test public void shouldIgnoreCopyOnNoneFoodItems(){
      FoodPortion portion = new FoodPortion( new Meal( "Meal" ), 100 );
      systemUnderTest.apply( Arrays.asList( portion ) );
      assertThat( foodItems.objectList(), is( empty() ) );
      assertThat( focus.portions().get( 0 ).food().get(), is( portion.food().get() ) );
      assertThat( focus.portions().get( 0 ).portion().get(), is( portion.portion().get() ) );
   }//End Method
   
}//End Class
