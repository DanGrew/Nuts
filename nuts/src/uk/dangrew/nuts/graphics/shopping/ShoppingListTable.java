/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.shopping;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import uk.dangrew.nuts.graphics.FriendlyTableView;
import uk.dangrew.nuts.shopping.ShoppingList;

/**
 * {@link ShoppingListTable} provides a {@link TableView} for {@link uk.dangrew.nuts.food.FoodItem}s
 * in a {@link ShoppingList}s {@link uk.dangrew.nuts.meal.StockUsage}.
 */
public class ShoppingListTable 
   extends TableView< ShoppingListRow > 
   implements FriendlyTableView< ShoppingListRow > 
{

   protected static final String COLUMN_TITLE_FOOD = "Food";
   protected static final String COLUMN_TITLE_PORTION = "Portion %";
   
   /**
    * Constructs a new {@link ShoppingListTable}.
    * @param shoppingList the {@link ShoppingList} to show.
    */
   public ShoppingListTable( ShoppingList shoppingList ) {
      this( shoppingList, new ShoppingListViewModel() );
   }//End Constructor
   
   /**
    * Constructs a new {@link ShoppingListTable}.
    * @param list the {@link ShoppingList} to show.
    * @param model the {@link ShoppingListViewModel}.
    */
   ShoppingListTable( ShoppingList list, ShoppingListViewModel model ) {
      this.setEditable( true );
      new ShoppingListTableColumns().populateColumns( this );
      model.associate( this, list );
   }//End Constructor

   /**
    * Friendly access to the {@link #getItems()}.
    * @return the {@link ObservableList}.
    */
   public ObservableList< ShoppingListRow > getRows(){
      return getItems();
   }//End Method
   
}//End Class
