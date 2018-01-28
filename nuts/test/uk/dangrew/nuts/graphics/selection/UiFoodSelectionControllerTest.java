package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class UiFoodSelectionControllerTest {

   private Database database;
   private FoodItem chicken;
   private FoodItem beans;
   private FoodItem sausages;
   
   private Meal meal;
   @Mock private UiFoodSelectionPane pane;
   
   private UiFoodSelectionController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      meal = new Meal( "Meal" );
      
      database = new Database();
      chicken = database.foodItems().createConcept( "Chicken" );
      chicken.foodAnalytics().proteinRatioProperty().set( 100.0 );
      beans = database.foodItems().createConcept( "Beans" );
      beans.foodAnalytics().proteinRatioProperty().set( 10.0 );
      sausages = database.foodItems().createConcept( "Sausages" );
      sausages.foodAnalytics().proteinRatioProperty().set( 20.0 );
      
      systemUnderTest = new UiFoodSelectionController( meal, database );
      systemUnderTest.controlSelection( pane );
   }//End Method

   @Test public void shouldAddPortionToMealByDuplicating() {
      FoodPortion portion = new FoodPortion( new FoodItem( "anything" ), 134.4 );
      systemUnderTest.addPortion( portion );
      assertThat( meal.portions(), hasSize( 1 ) );
      assertThat( meal.portions().get( 0 ), is( not( portion ) ) );
      assertThat( meal.portions().get( 0 ).food().get(), is( portion.food().get() ) );
      assertThat( meal.portions().get( 0 ).portion().get(), is( portion.portion().get() ) );
   }//End Method
   
   @Test public void shouldInvertSortingOnOptions(){
      verify( pane ).layoutTiles( Arrays.asList( beans, chicken, sausages ) );
      
      systemUnderTest.invertSort( true );
      verify( pane, times( 2 ) ).layoutTiles( Arrays.asList( sausages, chicken, beans ) );
      
      systemUnderTest.invertSort( false );
      verify( pane, times( 3 ) ).layoutTiles( Arrays.asList( beans, chicken, sausages ) );
   }//End Method
   
   @Test public void shouldFilterOptions(){
      verify( pane ).layoutTiles( Arrays.asList( beans, chicken, sausages ) );
      
      systemUnderTest.filterOptions( "chi" );
      verify( pane, times( 2 ) ).layoutTiles( Arrays.asList( chicken ) );
      
      systemUnderTest.filterOptions( "a" );
      verify( pane, times( 3 ) ).layoutTiles( Arrays.asList( beans, sausages ) );
   }//End Method
   
   @Test public void shouldChangeSelectionType(){
      verify( pane ).layoutTiles( Arrays.asList( beans, chicken, sausages ) );
      
      systemUnderTest.useSelectionType( FoodSelectionTypes.Protein );
      verify( pane, times( 2 ) ).layoutTiles( Arrays.asList( chicken, sausages, beans ) );
      
      systemUnderTest.useSelectionType( FoodSelectionTypes.All );
      verify( pane, times( 3 ) ).layoutTiles( Arrays.asList( beans, chicken, sausages ) );
   }//End Method
   
   @Test public void shouldDetectChangesInOptionsAndFeedThrough(){
      verify( pane ).layoutTiles( Arrays.asList( beans, chicken, sausages ) );
      
      FoodItem xyz = database.foodItems().createConcept( "xyz" );
      verify( pane, times( 2 ) ).layoutTiles( Arrays.asList( beans, chicken, sausages, xyz ) );
      
      database.foodItems().removeConcept( chicken );
      verify( pane, times( 3 ) ).layoutTiles( Arrays.asList( beans, sausages, xyz ) );
   }//End Method
   
}//End Class
