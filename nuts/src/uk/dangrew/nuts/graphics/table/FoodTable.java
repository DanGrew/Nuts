/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.FriendlyTableView;

/**
 * {@link FoodTable} provides a {@link TableView} for {@link FoodPortion}s.
 */
public class FoodTable< FoodTypeT extends Food > 
   extends TableView< FoodTableRow< FoodTypeT > > 
   implements FriendlyTableView< FoodTableRow< FoodTypeT > > 
{

   protected static final String COLUMN_TITLE_FOOD = "Food";
   protected static final String COLUMN_TITLE_PORTION = "Portion %";
   
   private final FoodTableController< FoodTypeT > controller;

   /**
    * Constructs a new {@link FoodTable}.
    * @param columnsPopulator the {@link FoodTableColumnsPopulator}.
    * @param controller the {@link FoodTableController}.
    */
   public FoodTable( 
            FoodTableColumnsPopulator< FoodTypeT > columnsPopulator, 
            FoodTableController< FoodTypeT > controller
   ) {
      this.controller = controller;
      this.controller.associate( this );
      this.setEditable( true );
      columnsPopulator.populateColumns( this );
   }//End Constructor
   
   /**
    * Friendly access to the {@link #getItems()}.
    * @return the {@link ObservableList}.
    */
   public ObservableList< FoodTableRow< FoodTypeT > > getRows(){
      return getItems();
   }//End Method
   
   /**
    * Access to the {@link FoodTableController}.
    * @return the {@link FoodTableController}.
    */
   public FoodTableController< FoodTypeT > controller() {
      return controller;
   }//End Method

}//End Class
