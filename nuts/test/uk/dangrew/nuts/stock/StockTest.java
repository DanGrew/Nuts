package uk.dangrew.nuts.stock;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.store.Database;

public class StockTest {

   private Database database;
   private Stock systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      DataLocation.loadSampleFoodData( database );
      systemUnderTest = new Stock( "Stock" );
      systemUnderTest.linkWithFoodItems( database.foodItems() );
   }//End Method

   @Test public void shouldProvidePortionForFood(){
      for ( FoodItem food : database.foodItems().objectList() ) {
         FoodPortion portion = systemUnderTest.portionFor( food );
         assertThat( portion.portion().get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldMonitorDatabaseChanges(){
      FoodItem newItem = database.foodItems().createConcept( "New Food" );
      assertThat( systemUnderTest.portionFor( newItem ), is( notNullValue() ) );
      
      database.foodItems().removeConcept( newItem );
      assertThat( systemUnderTest.portionFor( newItem ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldNotDuplicateFoodItemsWhenLinkedToStore(){
      FoodPortion extra = new FoodPortion( new FoodItem( "Extra" ), 100 );
      systemUnderTest.portions().add( extra );
      
      assertThat( systemUnderTest.portionFor( extra.food().get() ), is( extra ) );
      
      database.foodItems().store( ( FoodItem )extra.food().get() );
      systemUnderTest.linkWithFoodItems( database.foodItems() );
      assertThat( systemUnderTest.portionFor( extra.food().get() ), is( extra ) );
   }//End Method
   
   @Test public void shouldHoldStockInAlphabeticalOrder(){
      database = new Database();
      systemUnderTest = new Stock( "Stock" );
      systemUnderTest.linkWithFoodItems( database.foodItems() );
      
      FoodItem food1 = database.foodItems().createConcept( "b - second" );
      FoodItem food2 = database.foodItems().createConcept( "c - third" );
      FoodItem food3 = database.foodItems().createConcept( "a - first" );
      
      assertThat( systemUnderTest.portions(), hasSize( 3 ) );
      assertThat( systemUnderTest.portions().get( 0 ).food().get(), is( food3 ) );
      assertThat( systemUnderTest.portions().get( 1 ).food().get(), is( food1 ) );
      assertThat( systemUnderTest.portions().get( 2 ).food().get(), is( food2 ) );
   }//End Method
   
   @Test public void shouldProvideStockInAlphabeticalOrderInitially(){
      database = new Database();
      systemUnderTest = new Stock( "Stock" );
      
      FoodPortion food1 = new FoodPortion( new FoodItem( "b - second" ), 100 );
      FoodPortion food2 = new FoodPortion( new FoodItem( "c - third"  ), 100 );
      FoodPortion food3 = new FoodPortion( new FoodItem( "a - first"  ), 100 );
      systemUnderTest.portions().addAll( food1, food2, food3 );
      
      assertThat( systemUnderTest.portions(), hasSize( 3 ) );
      assertThat( systemUnderTest.portions().get( 0 ), is( food3 ) );
      assertThat( systemUnderTest.portions().get( 1 ), is( food1 ) );
      assertThat( systemUnderTest.portions().get( 2 ), is( food2 ) );
   }//End Method
   
}//End Class
