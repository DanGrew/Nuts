/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.database;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.selection.model.SimpleFoodModel;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public class UiComparableFoodTableColumns extends FoodTableColumns< Food > {

   private final CheckBoxFoodModelController controller;
   
   public UiComparableFoodTableColumns( SimpleFoodModel model ) {
      this.controller = new CheckBoxFoodModelController( model );
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< Food > table ) {
      table.setEditable( true );
      TableColumn< ConceptTableRow< Food >, Boolean > column = new TableColumn<>();
      column.setCellValueFactory( param -> controller.propertyFor( param.getValue().concept() ) );
      column.setCellFactory( CheckBoxTableCell.forTableColumn( column ) );
      column.setEditable( true );
      table.getColumns().add( column );
      
      super.populateColumns( table );
   }//End Method

}//End Class
