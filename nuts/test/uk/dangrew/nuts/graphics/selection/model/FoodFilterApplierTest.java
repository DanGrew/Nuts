package uk.dangrew.nuts.graphics.selection.model;

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
import uk.dangrew.nuts.graphics.table.FilteredConceptOptions;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.store.Database;

public class FoodFilterApplierTest {

   private Database database;
   private FoodItem chicken;
   private FoodItem beans;
   private FoodItem sausages;
   private Stock stock;
   
   private FilteredConceptOptions< Food > options;
   
   private FoodFilterModel model;
   private FoodFilterApplier systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      chicken = database.foodItems().createConcept( "Chicken" );
      chicken.foodAnalytics().of( NutritionalUnit.Protein ).set( 100.0 );
      beans = database.foodItems().createConcept( "Beans" );
      beans.foodAnalytics().of( NutritionalUnit.Protein ).set( 10.0 );
      sausages = database.foodItems().createConcept( "Sausages" );
      sausages.foodAnalytics().of( NutritionalUnit.Protein ).set( 20.0 );
      stock = new Stock( "Stock" );
      
      model = new FoodFilterModel( database );
      options = model.filteredConcepts();
      
      systemUnderTest = new FoodFilterApplier( model, stock );
   }//End Method

   @Test public void shouldApplyStockFilter() {
      stock.linkWithFoodItems( database.foodItems() );
      stock.portionFor( chicken ).setPortion( 200 );
      
      model.filters().add( FoodFilters.Stock );
      assertThat( options.options(), is( Arrays.asList( chicken ) ) );
   }//End Method

   @Test public void shouldApplyStockFilterWhenNoStockPortion() {
      model.filters().add( FoodFilters.Stock );
      assertThat( options.options(), is( Arrays.asList() ) );
   }//End Method
   
   @Test public void shouldClearFiltersBetweenApplication(){
      stock.linkWithFoodItems( database.foodItems() );
      stock.portionFor( sausages ).setPortion( 200 );
      
      model.filters().add( FoodFilters.Stock );
      assertThat( options.options(), is( Arrays.asList( sausages ) ) );
      
      model.filters().remove( FoodFilters.Stock );
      assertThat( options.options(), is( Arrays.asList( beans, chicken, sausages ) ) );
   }//End Method
   
   @Test public void shouldFilterBasedOnLabels(){
      database.labels().createConcept( "Meat" ).concepts().addAll( chicken, sausages );
      database.labels().createConcept( "Tinned" ).concepts().add( beans );
      
      model.filters().add( FoodFilters.Labels );
      assertThat( options.options(), is( Arrays.asList( beans, chicken, sausages ) ) );
      
      model.labels().add( database.labels().objectList().get( 0 ) );
      assertThat( options.options(), is( Arrays.asList( beans ) ) );
      
      model.labels().add( database.labels().objectList().get( 1 ) );
      assertThat( options.options(), is( Arrays.asList() ) );
      
      model.labels().remove( database.labels().objectList().get( 0 ) );
      assertThat( options.options(), is( Arrays.asList( chicken, sausages ) ) );
      
      model.filters().remove( FoodFilters.Labels );
      assertThat( options.options(), is( Arrays.asList( beans, chicken, sausages ) ) );
      
      model.labels().add( database.labels().objectList().get( 0 ) );
      assertThat( options.options(), is( Arrays.asList( beans, chicken, sausages ) ) );
   }//End Method
   
   @Test public void shouldNotFilterWhenNoLabelsSelected(){
      model.filters().add( FoodFilters.Labels );
      assertThat( options.options(), is( Arrays.asList( beans, chicken, sausages ) ) );
   }//End Method
   
   @Test public void shouldIncludeIngredientsInStringFilter(){
      Meal meal1 = database.meals().createConcept( "Meal1" );
      Meal meal2 = database.meals().createConcept( "Meal2" );
      Meal meal3 = database.meals().createConcept( "Meal3" );
      
      meal1.portions().add( new FoodPortion( chicken, 100 ) );
      meal1.portions().add( new FoodPortion( sausages, 100 ) );
      meal2.portions().add( new FoodPortion( beans, 100 ) );
      meal3.portions().add( new FoodPortion( meal1, 100 ) );
      
      assertThat( options.options(), is( Arrays.asList( beans, chicken, meal1, meal2, meal3, sausages ) ) );
      
      model.filteredConcepts().filterString().set( "Chick" );
      assertThat( options.options(), is( Arrays.asList( chicken, meal1, meal3 ) ) );
      
      model.filteredConcepts().filterString().set( "chick" );
      assertThat( options.options(), is( Arrays.asList( chicken, meal1, meal3 ) ) );
      
      model.filters().add( FoodFilters.NameOnly );
      assertThat( options.options(), is( Arrays.asList( chicken ) ) );
      
      model.filteredConcepts().filterString().set( "1" );
      assertThat( options.options(), is( Arrays.asList( meal1 ) ) );
   }//End Method
}//End Class
