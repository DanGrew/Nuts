/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.shopping;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.kode.observable.FunctionMapAnyKeyChangeListenerImpl;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.shopping.ShoppingList;

/**
 * {@link ShoppingListViewModel} provides a model for communicating {@link uk.dangrew.nuts.meal.StockUsage}
 * properties to the {@link ShoppingListTable}.
 */
class ShoppingListViewModel {

   private final Map< FoodItem, ObjectProperty< Double > > requiredProperties;
   private final Map< FoodItem, ObjectProperty< Double > > toBuyProperties;
   
   private ShoppingListTable table;
   private ShoppingList list;
   
   /**
    * Constructs a new {@link ShoppingListViewModel}.
    */
   ShoppingListViewModel() {
      this.requiredProperties = new HashMap<>();
      this.toBuyProperties = new HashMap<>();
   }//End Constructor
   
   /**
    * Associates with the given {@link ShoppingListTable} and {@link ShoppingList}.
    * @param table the {@link ShoppingListTable}.
    * @param shoppingList the {@link ShoppingList}.
    */
   void associate( ShoppingListTable table, ShoppingList shoppingList ) {
      if ( this.table != null || this.list != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.table = table;
      this.list = shoppingList;
      
      this.list.stockUsage().totalWeightUsed().forEach( ( f, v ) -> updateRow( f ) );
      this.list.stockUsage().totalWeightUsed().addListener( new FunctionMapAnyKeyChangeListenerImpl<>( 
               this::stockUpdated 
      ) );
      this.list.stockUsage().stockPortionUsed().addListener( new FunctionMapAnyKeyChangeListenerImpl<>( 
               this::stockUpdated 
      ) );
   }//End Method
   
   /**
    * Triggered when the {@link uk.dangrew.nuts.meal.StockUsage} properties are updated.
    * @param food the {@link FoodItem} to update.
    */
   private void stockUpdated( FoodItem food ){
      if ( list.stockUsage().totalWeightUsed().containsKey( food ) ) {
         updateRow( food );
      } else {
         removeRow( food );
      }
   }//End Method
   
   /**
    * Triggered to refresh the information in a row in the {@link ShoppingListTable}, or add a row
    * if one is not present.
    * @param item the {@link FoodItem} to update.
    */
   private void updateRow( FoodItem item ) {
      double itemWeight = list.stockUsage().totalWeightUsed().get( item );
      Double stockPortionUsed = list.stockUsage().stockPortionUsed().get( item );
      double toBuy = stockPortionUsed == null ? 0.0 : Math.ceil( ( stockPortionUsed / 100.0 ) );
      
      if ( requiredProperties.containsKey( item ) ) {
         requiredProperties.get( item ).set( itemWeight );
         toBuyProperties.get( item ).set( toBuy );
      } else {
         requiredProperties.put( item, new SimpleObjectProperty<>( itemWeight ) );
         toBuyProperties.put( item, new SimpleObjectProperty<>( toBuy ) );
         
         ShoppingListRow row = new ShoppingListRow( 
                  item, 
                  requiredProperties.get( item ), 
                  toBuyProperties.get( item ) );
         table.getRows().add( row );
      }
   }//End Method
   
   /**
    * Triggered to remove a row from the {@link ShoppingListTable} for the {@link FoodItem}.
    * @param food the {@link FoodItem} to remove from the table.
    */
   private void removeRow( FoodItem food ) {
      ShoppingListRow rowToRemove = table.getRows().stream()
               .filter( r -> r.food() == food )
               .findFirst().orElse( null );
      table.getRows().remove( rowToRemove );
   }//End Method

}//End Class
