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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

/**
 * {@link StockUsage} provides a map of {@link FoodItem}s to the percentage of stock the use, associated with 
 * set of {@link FoodPortion}s.
 */
public class StockUsage {

   private final ObservableMap< FoodItem, Double > stockPortionUsed;
   private final ObservableMap< FoodItem, Double > totalWeightUsed;
   
   private final Map< FoodItem, Double > stockPortionUsedConstruction;
   private final Map< FoodItem, Double > totalWeightUsedConstruction;
   
   private final MapChangeListener< FoodItem, Double > subStockChangeListener;
   private final Set< Meal > subStockRegistrations;
   private final ChangeListener< Object > propertyListener;
   private final Set< ObservableValue< ? > > propertyRegistrations;
   
   private ObservableList< FoodPortion > portions;
   
   /**
    * Constructs a new {@link StockUsage}.
    */
   public StockUsage() {
      this.stockPortionUsed = FXCollections.observableHashMap();
      this.totalWeightUsed = FXCollections.observableHashMap();
      this.stockPortionUsedConstruction = new LinkedHashMap<>();
      this.totalWeightUsedConstruction = new LinkedHashMap<>();
      
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
   public ObservableMap< FoodItem, Double > stockPortionUsed() {
      return stockPortionUsed;
   }//End Method
   
   
   /**
    * Access to the total weight of stock used, no modification allowed.
    * @return the {@link ObservableMap}.
    */
   public ObservableMap< FoodItem, Double > totalWeightUsed() {
      return totalWeightUsed;
   }//End Method
   
   /**
    * Method to do a blanket recalculation.
    */
   private void recalculateStock(){
      this.stockPortionUsedConstruction.clear();
      this.totalWeightUsedConstruction.clear();
      this.subStockRegistrations.forEach( m -> m.stockUsage().stockPortionUsed().removeListener( subStockChangeListener ) );
      this.propertyRegistrations.forEach( p -> p.removeListener( propertyListener ) );
      this.portions.forEach( this::updateFoodItemPortion );
      this.updateMapFromCalculations( totalWeightUsed, totalWeightUsedConstruction );
      this.updateMapFromCalculations( stockPortionUsed, stockPortionUsedConstruction );
   }//End Method
   
   /**
    * Method to update the given data {@link Map} from the construction {@link Map}. This is resolve multiple unnecessary 
    * notifications of changes to the {@link ObservableMap}.
    * @param dataMap the {@link Map} providing the notifications.
    * @param calculationMap the {@link Map} providing the information to populate to the data map.
    */
   private void updateMapFromCalculations( Map< FoodItem, Double > dataMap, Map< FoodItem, Double > calculationMap ){
      Set< FoodItem > previousKeys = new LinkedHashSet<>( dataMap.keySet() );
      for ( FoodItem item : previousKeys ) {
         if ( !calculationMap.containsKey( item ) || calculationMap.get( item ) == 0.0 ) {
            dataMap.remove( item );
         }
      }
      
      dataMap.putAll( calculationMap );
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
      
      double totalPortionUsed = item.stockProperties().loggedWeight().get() * portion;
      double totalWeightUsedValue = totalPortionUsed / 100;
      double stockUsage = totalPortionUsed / item.stockProperties().soldInWeight().get();
      
      putValue( stockPortionUsedConstruction, item, stockUsage );
      putValue( totalWeightUsedConstruction, item, totalWeightUsedValue );
   }//End Method
   
   /**
    * Method to put the {@link FoodItem} addition into the {@link Map}.
    * @param map the {@link Map} to update.
    * @param item the {@link FoodItem} to add.
    * @param addition the amount to add for the {@link FoodItem}.
    */
   private void putValue( Map< FoodItem, Double > map, FoodItem item, Double addition ) {
      Double value = map.get( item );
      if ( value == null ) {
         value = 0.0;
      }
      map.put( item, value + addition );
   }//End Method
   
   /**
    * Method to update the stock for the given {@link Meal} and how much of it is consumed.
    * @param item the {@link Meal} being used.
    * @param portion the amount of the logged weight used.
    */
   private void updateMealPortion( Meal meal, double portion ) {
      meal.stockUsage().stockPortionUsed().addListener( subStockChangeListener );
      subStockRegistrations.add( meal );
      
      meal.stockUsage().stockPortionUsed().forEach( ( item, usage ) -> {
         double additionalUsage = usage * ( portion / 100.0 );
         double additionalWeight = ( additionalUsage / 100.0 ) * item.stockProperties().soldInWeight().get();
         putValue( stockPortionUsedConstruction, item, additionalUsage );
         putValue( totalWeightUsedConstruction, item, additionalWeight );
      } );
   }//End Method

}//End Class
