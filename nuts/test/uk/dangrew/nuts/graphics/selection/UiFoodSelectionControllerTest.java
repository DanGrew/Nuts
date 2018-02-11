package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiFoodSelectionControllerTest {

   private Database database;
   private FoodItem chicken;
   private FoodItem beans;
   private FoodItem sausages;
   
   private Stock stock;
   private Template liveSelection;
   @Mock private FoodSelectionPaneManager pane;
   
   private UiFoodSelectionController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      liveSelection = new Template( "Live Selection" );
      
      database = new Database();
      chicken = database.foodItems().createConcept( "Chicken" );
      chicken.foodAnalytics().proteinRatioProperty().set( 100.0 );
      beans = database.foodItems().createConcept( "Beans" );
      beans.foodAnalytics().proteinRatioProperty().set( 10.0 );
      sausages = database.foodItems().createConcept( "Sausages" );
      sausages.foodAnalytics().proteinRatioProperty().set( 20.0 );
      stock = database.stockLists().createConcept( "Stock" );
      stock.linkWithFoodItems( database.foodItems() );
      
      systemUnderTest = new UiFoodSelectionController( database, liveSelection );
      systemUnderTest.controlSelection( pane );
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
   
   @Test public void shouldProvideSelection(){
      FoodPortion portion = new FoodPortion( chicken, 100 );
      assertThat( systemUnderTest.isSelected( portion ), is( false ) );
      assertThat( systemUnderTest.isSelected( chicken ), is( false ) );
      assertThat( liveSelection.portions().contains( portion ), is( false ) );
      
      systemUnderTest.select( portion );
      verify( pane ).setSelected( portion, true );
      assertThat( systemUnderTest.isSelected( portion ), is( true ) );
      assertThat( systemUnderTest.isSelected( chicken ), is( true ) );
      assertThat( liveSelection.portions().contains( portion ), is( true ) );
      
      systemUnderTest.deselect( portion );
      verify( pane ).setSelected( portion, false );
      assertThat( systemUnderTest.isSelected( portion ), is( false ) );
      assertThat( systemUnderTest.isSelected( chicken ), is( false ) );
      assertThat( liveSelection.portions().contains( portion ), is( false ) );
   }//End Method
   
   @Test public void shouldAvoidUnnecessaryPaneCalls(){
      FoodPortion portion = new FoodPortion();
      systemUnderTest.select( portion );
      systemUnderTest.select( portion );
      verify( pane, times( 1 ) ).setSelected( portion, true );
      
      systemUnderTest.deselect( portion );
      systemUnderTest.deselect( portion );
      verify( pane, times( 1 ) ).setSelected( portion, false );
   }//End Method
   
   @Test public void shouldGetAndClearSelection(){
      FoodPortion portion1 = new FoodPortion( chicken, 100 );
      FoodPortion portion2 = new FoodPortion( sausages, 125 );
      FoodPortion portion3 = new FoodPortion( beans, 75 );
      
      systemUnderTest.select( portion1 );
      systemUnderTest.select( portion2 );
      systemUnderTest.select( portion3 );
      
      Set< FoodPortion > selected = systemUnderTest.getAndClearSelection();
      assertThat( selected, contains( portion1, portion2, portion3 ) );
      assertThat( systemUnderTest.isSelected( portion1 ), is( false ) );
      assertThat( systemUnderTest.isSelected( portion2 ), is( false ) );
      assertThat( systemUnderTest.isSelected( portion3 ), is( false ) );
      assertThat( systemUnderTest.getAndClearSelection(), is( empty() ) );
   }//End Method
   
   @Test public void shouldApplyFilters(){
      stock.portionFor( beans ).setPortion( 100 );
      stock.portionFor( sausages ).setPortion( 200 );
      systemUnderTest.select( new FoodPortion( chicken, 100 ) );
      systemUnderTest.select( new FoodPortion( sausages, 100 ) );
      
      systemUnderTest.applyFilters( Arrays.asList( FoodSelectionFilters.Selection, FoodSelectionFilters.Stock ) );
      verify( pane, atLeastOnce() ).layoutTiles( Arrays.asList( sausages ) );
   }//End Method
   
}//End Class
