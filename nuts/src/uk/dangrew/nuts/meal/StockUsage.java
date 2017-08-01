/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.meal;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableMapImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

/**
 * {@link StockUsage} provides a map of {@link FoodItem}s to the percentage of stock the use, associated with 
 * set of {@link FoodPortion}s.
 */
public class StockUsage {

   private final ObservableMap< FoodItem, Double > stock;
   private final PrivatelyModifiableObservableMapImpl< FoodItem, Double > publicStock;
   
   private final MapChangeListener< FoodItem, Double > subStockChangeListener;
   private final Set< Meal > subStockRegistrations;
   private final ChangeListener< Object > propertyListener;
   private final Set< ObservableValue< ? > > propertyRegistrations;
   
   private ObservableList< FoodPortion > portions;
   
   /**
    * Constructs a new {@link StockUsage}.
    */
   public StockUsage() {
      this.stock = FXCollections.observableHashMap();
      this.publicStock = new PrivatelyModifiableObservableMapImpl<>( stock );
      this.subStockChangeListener = c -> recalculateStock();
      this.subStockRegistrations = new HashSet<>();
      this.propertyListener = ( s, o, n ) -> recalculateStock();
      this.propertyRegistrations = new HashSet<>();
   }//End Constructor
   
   /**
    * Associates with the given {@link FoodPortion}s.
    * @param portions the {@link ObservableList}.
    */
   public void associate( ObservableList< FoodPortion > portions ) {
      if ( this.portions != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.portions = portions;
      this.recalculateStock();
      this.portions.addListener( new FunctionListChangeListenerImpl<>( 
               p -> this.recalculateStock(), p -> this.recalculateStock()
      ) );
   }//End Method

   /**
    * Access to the stock structure, no modification allowed.
    * @return the {@link ObservableMap}.
    */
   public ObservableMap< FoodItem, Double > stock() {
      return stock;
   }//End Method
   
   /**
    * Method to do a blanket recalculation.
    */
   private void recalculateStock(){
      this.stock.clear();
      this.subStockRegistrations.forEach( m -> m.stockUsage().stock().removeListener( subStockChangeListener ) );
      this.propertyRegistrations.forEach( p -> p.removeListener( propertyListener ) );
      this.portions.forEach( this::updateFoodItemPortion );
   }//End Method
   
   /**
    * Method to update the stock for the given {@link FoodPortion}.
    * @param portion the {@link FoodPortion}.
    */
   private void updateFoodItemPortion( FoodPortion portion ) {
      portion.food().addListener( propertyListener );
      propertyRegistrations.add( portion.food() );
      portion.portion().addListener( propertyListener );
      propertyRegistrations.add( portion.portion() );
      
      Food food = portion.food().get();
      if ( food == null ){
         return;
      } else if ( food instanceof FoodItem ) {
         FoodItem item = ( FoodItem )food;
         updateFoodItemPortion( item, portion.portion().get() );
      } else if ( food instanceof Meal ) {
         Meal meal = ( Meal )food;
         updateMealPortion( meal, portion.portion().get() );
      }
   }//End Method
   
   /**
    * Method to update the stock for the given {@link FoodItem} and how much of it is consumed.
    * @param item the {@link FoodItem} being used.
    * @param portion the amount of the logged weight used.
    */
   private void updateFoodItemPortion( FoodItem item, double portion ) {
      item.stockProperties().loggedWeight().addListener( propertyListener );
      propertyRegistrations.add( item.stockProperties().loggedWeight() );
      item.stockProperties().soldInWeight().addListener( propertyListener );
      propertyRegistrations.add( item.stockProperties().soldInWeight() );
      
      double loggedPortionOfSoldIn = item.stockProperties().loggedWeight().get() / item.stockProperties().soldInWeight().get();
      double stockUsage = portion * loggedPortionOfSoldIn;
      
      Double stockValue = stock.get( item );
      if ( stockValue == null ) {
         stockValue = 0.0;
      }
      stock.put( item, stockValue + stockUsage );
   }//End Method
   
   /**
    * Method to update the stock for the given {@link Meal} and how much of it is consumed.
    * @param item the {@link Meal} being used.
    * @param portion the amount of the logged weight used.
    */
   private void updateMealPortion( Meal meal, double portion ) {
      meal.stockUsage().stock().addListener( subStockChangeListener );
      subStockRegistrations.add( meal );
      
      meal.stockUsage().stock().forEach( ( item, usage ) -> {
         Double stockValue = stock.get( item );
         if ( stockValue == null ) {
            stockValue = 0.0;
         }
         
         double currentUsage = stockValue;
         double additionalUsage = usage * ( portion / 100.0 );
         stock.put( item, currentUsage + additionalUsage );
      } );
   }//End Method

}//End Class
