package uk.dangrew.nuts.stock;

import java.util.HashMap;
import java.util.Map;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.meal.Meal;

public class Stock extends Meal {

   private final Map< Food, FoodPortion > portionMapping;
   
   public Stock( String name ) {
      this( new FoodProperties( name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param id the id of the {@link Meal}.
    * @param name the name of the {@link Meal}.
    */
   public Stock( String id, String name ) {
      this( new FoodProperties( id, name ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link Meal}.
    * @param properties the {@link FoodProperties}.
    */
   private Stock( FoodProperties properties ) {
      super( properties );
      this.portionMapping = new HashMap<>();
      this.portions().addListener( new FunctionListChangeListenerImpl<>( 
               this::addStockForPortion, this::removeStockForPortion
      ) );
   }//End Constructor
   
   public void linkWithFoodItems( FoodItemStore items ) {
      items.objectList().forEach( this::addStockForItem );
      items.objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::addStockForItem, this::removeStockForItem
      ) );
   }//End Constructor
   
   private void addStockForItem( FoodItem item ) {
      if ( portionMapping.containsKey( item ) ) {
         return;
      }
      FoodPortion portion = new FoodPortion( item, 0 );
      portionMapping.put( item, portion );
      super.portions().add( portion );
   }//End Method
   
   private void removeStockForItem( FoodItem item ) {
      if ( !portionMapping.containsKey( item ) ) {
         return;
      }
      FoodPortion portion = portionMapping.get( item );
      portionMapping.remove( item );
      super.portions().remove( portion );
   }//End Method
   
   private void addStockForPortion( FoodPortion added ) {
      portionMapping.put( added.food().get(), added );
   }//End Method
   
   private void removeStockForPortion( FoodPortion removed ) {
      portionMapping.remove( removed );
   }//End Method
   
   public FoodPortion portionFor( Food food ) {
      return portionMapping.get( food );
   }//End Method
   
}//End Class
