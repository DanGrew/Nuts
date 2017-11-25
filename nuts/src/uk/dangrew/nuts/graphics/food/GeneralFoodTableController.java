/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.food;

import java.util.HashSet;
import java.util.Set;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodStore;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.graphics.table.FoodTableController;
import uk.dangrew.nuts.graphics.table.FoodTableRow;

/**
 * The {@link GeneralFoodTableController} is responsible for monitoring the {@link uk.dangrew.nuts.store.Database} and
 * updating the {@link FoodTable}, as well as performing operations on the {@link FoodTable}.
 */
public class GeneralFoodTableController< FoodTypeT extends Food > implements FoodTableController< FoodTypeT > {

   private final FoodStore< FoodTypeT > foods;
   
   private FoodTable< FoodTypeT > table;
   
   /**
    * Constructs a new {@link GeneralFoodTableController}.
    * @param foods the {@link FoodStore} data.
    */
   public GeneralFoodTableController(
            FoodStore< FoodTypeT > foods
   ) {
      this.foods = foods;
      
      this.foods.objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::addFoodRow, this::removeFoodRow
      ) );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void associate( FoodTable< FoodTypeT > table ) {
      this.table = table;
      
      this.foods.objectList().forEach( this::addFoodRow );
   }//End Method
   
   /**
    * Method to add a {@link Food} row to the {@link FoodTable}.
    * @param food the {@link Food} to add.
    */
   private void addFoodRow( FoodTypeT food ) {
      table.getItems().add( new FoodTableRow<>( food ) );
   }//End Method
   
   /**
    * Method to remove the {@link Food} row for the given {@link Food}.
    * @param food the {@link Food} to remove.
    */
   private void removeFoodRow( Food food ) {
      Set< FoodTableRow< FoodTypeT > > rowsToRemove = new HashSet<>();
      for ( FoodTableRow< FoodTypeT > row : table.getItems() ) {
         if ( row.food().properties().id().equals( food.properties().id() ) ) {
            rowsToRemove.add( row );
         }
      }
      
      table.getItems().removeAll( rowsToRemove );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public FoodTypeT createFood() {
      return foods.createFood( "Unnamed Food" );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void removeSelectedFood() {
      FoodTableRow< FoodTypeT > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      foods.removeFood( selection.food() );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void copySelectedFood() {
      FoodTableRow< FoodTypeT > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      FoodTypeT copy = ( FoodTypeT )selection.food().duplicate( "-copy" );
      foods.store( copy );
   }//End Method
   
}//End Class
