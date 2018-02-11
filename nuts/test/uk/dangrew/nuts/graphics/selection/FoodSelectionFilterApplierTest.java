package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptOptions;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.graphics.table.FilteredConceptOptions;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.store.Database;

public class FoodSelectionFilterApplierTest {

   private Database database;
   private FoodItem chicken;
   private FoodItem beans;
   private FoodItem sausages;
   private Stock stock;
   
   private FoodSelectionManager selectionManager;
   private ConceptOptions< Food > backingOptions;
   private FilteredConceptOptions< Food > options;
   private FoodSelectionFilterApplier systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      chicken = database.foodItems().createConcept( "Chicken" );
      chicken.foodAnalytics().proteinRatioProperty().set( 100.0 );
      beans = database.foodItems().createConcept( "Beans" );
      beans.foodAnalytics().proteinRatioProperty().set( 10.0 );
      sausages = database.foodItems().createConcept( "Sausages" );
      sausages.foodAnalytics().proteinRatioProperty().set( 20.0 );
      stock = new Stock( "Stock" );
      
      backingOptions = new ConceptOptionsImpl<>( database.foodItems() );
      options = new FilteredConceptOptions<>( backingOptions );
      
      selectionManager = new FoodSelectionManager();
      systemUnderTest = new FoodSelectionFilterApplier( selectionManager, options, stock );
   }//End Method

   @Test public void shouldApplySelectionFilter() {
      selectionManager.select( new FoodPortion( chicken, 100 ) );
      selectionManager.select( new FoodPortion( beans, 100 ) );
      systemUnderTest.applyFilters( FoodSelectionFilters.Selection );
      
      assertThat( options.options(), is( Arrays.asList( beans, chicken ) ) );
   }//End Method
   
   @Test public void shouldApplyStockFilter() {
      stock.linkWithFoodItems( database.foodItems() );
      stock.portionFor( chicken ).setPortion( 200 );
      
      systemUnderTest.applyFilters( FoodSelectionFilters.Stock );
      assertThat( options.options(), is( Arrays.asList( chicken ) ) );
   }//End Method

   @Test public void shouldApplyStockFilterWhenNoStockPortion() {
      systemUnderTest.applyFilters( FoodSelectionFilters.Stock );
      assertThat( options.options(), is( Arrays.asList() ) );
   }//End Method
   
   @Test public void shouldClearFiltersBetweenApplication(){
      stock.linkWithFoodItems( database.foodItems() );
      stock.portionFor( sausages ).setPortion( 200 );
      
      selectionManager.select( new FoodPortion( chicken, 100 ) );
      selectionManager.select( new FoodPortion( beans, 100 ) );
      
      systemUnderTest.applyFilters( FoodSelectionFilters.Selection );
      assertThat( options.options(), is( Arrays.asList( beans, chicken ) ) );
      
      systemUnderTest.applyFilters( FoodSelectionFilters.Stock );
      assertThat( options.options(), is( Arrays.asList( sausages ) ) );
      
   }//End Method
}//End Class