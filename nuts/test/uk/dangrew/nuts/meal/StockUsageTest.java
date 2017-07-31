package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static uk.dangrew.kode.TestCommon.precision;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

public class StockUsageTest {

   private FoodItem item1;
   private FoodItem item2;
   private FoodItem item3;
   private FoodItem item4;
   
   private ObservableList< FoodPortion > portions;
   private StockUsage systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      item1 = new FoodItem( "Item1" );
      item1.stockProperties().setWeighting( 100.0, 100.0 );
      item2 = new FoodItem( "Item2" );
      item2.stockProperties().setWeighting( 15.0, 150.0 );
      item3 = new FoodItem( "Item3" );
      item3.stockProperties().setWeighting( 200.0, 485.0 );
      item4 = new FoodItem( "Item4" );
      item4.stockProperties().setWeighting( 200.0, 485.0 );
      
      portions = FXCollections.observableArrayList();
      portions.addAll( 
               new FoodPortion( item1, 100.0 ),
               new FoodPortion( item2, 50.0 ),
               new FoodPortion( item3, 100.0 ),
               new FoodPortion( item4, 200.0 ),
               new FoodPortion()
      );
      
      systemUnderTest = new StockUsage();
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations() {
      systemUnderTest.associate( portions );
      systemUnderTest.associate( portions );
   }//End Method
   
   @Test public void shouldProvideStockUsageForBasicPortions(){
      systemUnderTest.associate( portions );

      for ( FoodPortion portion : portions ) {
         FoodItem food = ( FoodItem )portion.food().get();
         if ( food == null ) {
            continue;
         } else {
            double portionWeight = portion.portion().get() * food.stockProperties().loggedWeight().get();
            double stockPortion = portionWeight / food.stockProperties().soldInWeight().get();
            assertThat( systemUnderTest.stock().get( food ), is( closeTo( stockPortion, precision() ) ) );
         }
      }
   }//End Method
   
   @Test public void shouldProvideStockUsageForNestedSinglePortions(){
      Meal meal1 = new Meal( "anything" );
      meal1.portions().addAll(
               new FoodPortion( item1, 100 ),
               new FoodPortion( item2, 100 ),
               new FoodPortion( item3, 100 )
      );
      
      portions.clear();
      portions.add( new FoodPortion( meal1, 100 ) );
      
      systemUnderTest.associate( portions );
      assertThat( systemUnderTest.stock().get( item1 ), is( 100.0 ) );
      assertThat( systemUnderTest.stock().get( item2 ), is( 10.0 ) );
      assertThat( systemUnderTest.stock().get( item3 ), is( closeTo( 41.237, precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideStockUsageForPortionedMeal(){
      Meal meal1 = new Meal( "anything" );
      meal1.portions().addAll(
               new FoodPortion( item1, 100 ),
               new FoodPortion( item2, 100 ),
               new FoodPortion( item3, 100 )
      );
      
      portions.clear();
      portions.add( new FoodPortion( meal1, 50 ) );
      
      systemUnderTest.associate( portions );
      assertThat( systemUnderTest.stock().get( item1 ), is( 50.0 ) );
      assertThat( systemUnderTest.stock().get( item2 ), is( 5.0 ) );
      assertThat( systemUnderTest.stock().get( item3 ), is( closeTo( 20.618, precision() ) ) );
   }//End Method
   
   @Test public void shouldUpdateStockUsageWhenNestedMealStockChanges(){
      Meal meal1 = new Meal( "anything" );
      meal1.portions().addAll(
               new FoodPortion( item1, 100 ),
               new FoodPortion( item2, 100 ),
               new FoodPortion( item3, 100 )
      );
      
      portions.clear();
      portions.add( new FoodPortion( meal1, 50 ) );
      
      systemUnderTest.associate( portions );
      assertThat( systemUnderTest.stock().get( item1 ), is( 50.0 ) );
      assertThat( systemUnderTest.stock().get( item2 ), is( 5.0 ) );
      assertThat( systemUnderTest.stock().get( item3 ), is( closeTo( 20.618, precision() ) ) );
      
      meal1.portions().get( 0 ).setPortion( 50.0 );
      assertThat( systemUnderTest.stock().get( item1 ), is( 25.0 ) );
      assertThat( systemUnderTest.stock().get( item2 ), is( 5.0 ) );
      assertThat( systemUnderTest.stock().get( item3 ), is( closeTo( 20.618, precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideStockUsageForNestedDifferentPortions(){
      Meal meal1 = new Meal( "anything" );
      meal1.portions().addAll(
               new FoodPortion( item1, 20 ),
               new FoodPortion( item2, 35 ),
               new FoodPortion( item3, 90 )
      );
      
      portions.clear();
      portions.add( new FoodPortion( meal1, 100 ) );
      
      systemUnderTest.associate( portions );
      assertThat( systemUnderTest.stock().get( item1 ), is( 20.0 ) );
      assertThat( systemUnderTest.stock().get( item2 ), is( 3.5 ) );
      assertThat( systemUnderTest.stock().get( item3 ), is( closeTo( 37.113, precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideStockUsageForMultiLayeredNestingPortions(){
      Meal meal1 = new Meal( "anything" );
      meal1.portions().add( new FoodPortion( item1, 100 ) );
      Meal meal2 = new Meal( "anything" );
      meal2.portions().add( new FoodPortion( meal1, 50 ) );
      Meal meal3 = new Meal( "anything" );
      meal3.portions().add( new FoodPortion( meal2, 25 ) );
      
      portions.clear();
      portions.add( new FoodPortion( meal3, 100 ) );
      
      systemUnderTest.associate( portions );
      assertThat( systemUnderTest.stock().get( item1 ), is( 12.5 ) );
   }//End Method

   @Test public void shouldProvideStockUsageForMultiLayeredNestingPortionsWithRepeatedItems(){
      Meal meal1 = new Meal( "anything" );
      meal1.portions().add( new FoodPortion( item1, 100 ) );
      Meal meal2 = new Meal( "anything" );
      meal2.portions().add( new FoodPortion( meal1, 50 ) );
      Meal meal3 = new Meal( "anything" );
      meal3.portions().addAll( 
               new FoodPortion( meal2, 25 ),
               new FoodPortion( item1, 100 )
      );
      
      portions.clear();
      portions.add( new FoodPortion( meal3, 100 ) );
      
      systemUnderTest.associate( portions );
      assertThat( systemUnderTest.stock().get( item1 ), is( 112.5 ) );
   }//End Method
   
   @Test public void shouldCombineStockUsageForReusedItems(){
      portions.clear();
      portions.addAll( 
               new FoodPortion( item1, 100 ),
               new FoodPortion( item1, 150 )
      );
      systemUnderTest.associate( portions );
      
      assertThat( systemUnderTest.stock().get( item1 ), is( 250.0 ) );
   }//End Method
   
   @Test public void shouldUpdateStockWhenPortionChanges(){
      systemUnderTest.associate( portions );
      
      portions.get( 0 ).setPortion( 70 );
      assertThat( systemUnderTest.stock().get( item1 ), is( 70.0 ) );
   }//End Method

   @Test public void shouldUpdateStockWhenPortionFoodChanges(){
      systemUnderTest.associate( portions );
      
      assertThat( systemUnderTest.stock().get( item1 ), is( 100.0 ) );
      assertThat( systemUnderTest.stock().get( item2 ), is( 5.0 ) );
      portions.get( 0 ).setFood( item2 );
      assertThat( systemUnderTest.stock().get( item1 ), is( nullValue() ) );
      assertThat( systemUnderTest.stock().get( item2 ), is( 15.0 ) );
   }//End Method

   @Test public void shouldUpdateStockWhenPortionAdded(){
      portions.clear();
      systemUnderTest.associate( portions );
      
      portions.add( new FoodPortion( item1, 100 ) );
      assertThat( systemUnderTest.stock().get( item1 ), is( 100.0 ) );
   }//End Method
   
   @Test public void shouldUpdateStockWhenPortionRemoved(){
      systemUnderTest.associate( portions );
      
      portions.remove( portions.get( 0 ) );
      assertThat( systemUnderTest.stock().get( item1 ), is( nullValue() ) );
   }//End Method

   @Test public void shouldNotUpdateStockWhenRemovedPortionChanged(){
      systemUnderTest.associate( portions );
      
      FoodPortion portion = portions.get( 0 );
      portions.remove( portion );
      assertThat( systemUnderTest.stock().get( item1 ), is( nullValue() ) );
      
      portion.setPortion( 34.0 );
      assertThat( systemUnderTest.stock().get( item1 ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldUpdateStockWhenLoggedWeightChanges(){
      systemUnderTest.associate( portions );
      
      item1.stockProperties().loggedWeight().set( 30.0 );
      assertThat( systemUnderTest.stock().get( item1 ), is( 30.0 ) );
   }//End Method
   
   @Test public void shouldUpdateStockWhenSoldInWeightChanges(){
      systemUnderTest.associate( portions );
      
      item1.stockProperties().soldInWeight().set( 400.0 );
      assertThat( systemUnderTest.stock().get( item1 ), is( 25.0 ) );
   }//End Method
   
}//End Class
