package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static uk.dangrew.kode.TestCommon.precision;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.FunctionMapAnyKeyChangeListenerImpl;
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
            assertStockPresentFor( food, stockPortion );
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
      assertStockPresentFor( item1, 100 );
      assertStockPresentFor( item2, 10 );
      assertStockPresentFor( item3, 41.237 );
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
      assertStockPresentFor( item1, 50 );
      assertStockPresentFor( item2, 5 );
      assertStockPresentFor( item3, 20.6185 );
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
      assertStockPresentFor( item1, 50 );
      assertStockPresentFor( item2, 5 );
      assertStockPresentFor( item3, 20.6186 );
      
      meal1.portions().get( 0 ).setPortion( 50.0 );
      assertStockPresentFor( item1, 25 );
      assertStockPresentFor( item2, 5 );
      assertStockPresentFor( item3, 20.6186 );
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
      assertStockPresentFor( item1, 20 );
      assertStockPresentFor( item2, 3.5 );
      assertStockPresentFor( item3, 37.1134 );
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
      assertStockPresentFor( item1, 12.5 );
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
      assertStockPresentFor( item1, 112.5 );
   }//End Method
   
   @Test public void shouldCombineStockUsageForReusedItems(){
      portions.clear();
      portions.addAll( 
               new FoodPortion( item1, 100 ),
               new FoodPortion( item1, 150 )
      );
      systemUnderTest.associate( portions );
      
      assertStockPresentFor( item1, 250 );
   }//End Method
   
   @Test public void shouldUpdateStockWhenPortionChanges(){
      systemUnderTest.associate( portions );
      
      portions.get( 0 ).setPortion( 70 );
      assertStockPresentFor( item1, 70 );
   }//End Method

   @Test public void shouldUpdateStockWhenPortionFoodChanges(){
      systemUnderTest.associate( portions );
      
      assertStockPresentFor( item1, 100 );
      assertStockPresentFor( item2, 5 );
      portions.get( 0 ).setFood( item2 );
      assertStockPresentFor( item1, 0 );
      assertStockPresentFor( item2, 15 );
   }//End Method

   @Test public void shouldUpdateStockWhenPortionAdded(){
      portions.clear();
      systemUnderTest.associate( portions );
      
      portions.add( new FoodPortion( item1, 100 ) );
      assertStockPresentFor( item1, 100 );
   }//End Method
   
   @Test public void shouldUpdateStockWhenPortionRemoved(){
      systemUnderTest.associate( portions );
      
      portions.remove( portions.get( 0 ) );
      assertStockPresentFor( item1, 0 );
   }//End Method

   @Test public void shouldNotUpdateStockWhenRemovedPortionChanged(){
      systemUnderTest.associate( portions );
      
      FoodPortion portion = portions.get( 0 );
      portions.remove( portion );
      assertStockPresentFor( item1, 0 );
      
      portion.setPortion( 34.0 );
      assertStockPresentFor( item1, 0 );
   }//End Method
   
   @Test public void shouldUpdateStockWhenLoggedWeightChanges(){
      systemUnderTest.associate( portions );
      
      item1.stockProperties().loggedWeight().set( 30.0 );
      assertStockPresentFor( item1, 30 );
   }//End Method
   
   @Test public void shouldUpdateStockWhenSoldInWeightChanges(){
      systemUnderTest.associate( portions );
      
      item1.stockProperties().soldInWeight().set( 400.0 );
      assertStockPresentFor( item1, 25 );
   }//End Method
   
   @Test public void shouldNotNotifyForStockUsageClearanceOnlyNotifyIndividually(){
      systemUnderTest.associate( portions );
      
      Consumer< FoodItem > listener = mock( Consumer.class );
      FunctionMapAnyKeyChangeListenerImpl< FoodItem, Double > adapter = new FunctionMapAnyKeyChangeListenerImpl<>( listener );
      systemUnderTest.stockPortionUsed().addListener( adapter );
      
      item1.stockProperties().soldInWeight().set( 400.0 );
      verify( listener ).accept( item1 );
      verifyNoMoreInteractions( listener );
      
      item2.stockProperties().soldInWeight().set( 10.0 );
      verify( listener ).accept( item2 );
      verifyNoMoreInteractions( listener );
      
      portions.clear();
      verify( listener, times( 2 ) ).accept( item1 );
      verify( listener, times( 2 ) ).accept( item2 );
      verify( listener ).accept( item3 );
      verify( listener ).accept( item4 );
      verifyNoMoreInteractions( listener );
   }//End Method
   
   @Test public void shouldNotNotifyForTotalWeightClearanceOnlyNotifyIndividually(){
      systemUnderTest.associate( portions );
      
      Consumer< FoodItem > listener = mock( Consumer.class );
      FunctionMapAnyKeyChangeListenerImpl< FoodItem, Double > adapter = new FunctionMapAnyKeyChangeListenerImpl<>( listener );
      systemUnderTest.totalWeightUsed().addListener( adapter );
      
      item1.stockProperties().loggedWeight().set( 400.0 );
      verify( listener ).accept( item1 );
      verifyNoMoreInteractions( listener );
      
      item2.stockProperties().loggedWeight().set( 10.0 );
      verify( listener ).accept( item2 );
      verifyNoMoreInteractions( listener );
      
      portions.clear();
      verify( listener, times( 2 ) ).accept( item1 );
      verify( listener, times( 2 ) ).accept( item2 );
      verify( listener ).accept( item3 );
      verify( listener ).accept( item4 );
      verifyNoMoreInteractions( listener );
   }//End Method
   
   /**
    * Method to assert that the stock properties match for the portion used.
    * @param item the {@link FoodItem} in question.
    * @param portionUsed the portion of {@link FoodItem} used.
    */
   private void assertStockPresentFor( FoodItem item, double portionUsed ) {
      if ( portionUsed == 0.0 ) {
         assertThat( systemUnderTest.stockPortionUsed().get( item ), is( nullValue() ) );
         assertThat( systemUnderTest.totalWeightUsed().get( item ), is( nullValue() ) );
      } else {
         double totalWeight = portionUsed / 100.0 * item.stockProperties().soldInWeight().get();
         assertThat( systemUnderTest.stockPortionUsed().get( item ), is( closeTo( portionUsed, precision() ) ) );
         assertThat( systemUnderTest.totalWeightUsed().get( item ), is( closeTo( totalWeight, precision() ) ) );
      }
   }//End Method
   
}//End Class
