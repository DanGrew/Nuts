/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import uk.dangrew.kode.javafx.table.base.ConceptTableRow;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.common.CheckBoxController;
import uk.dangrew.nuts.graphics.meal.MealTableColumns;
import uk.dangrew.nuts.graphics.table.TableComponents;

public class UiDayPlanMealTableColumns extends MealTableColumns {

   private final CheckBoxController< FoodPortion > consumptionProperties;
   
   public UiDayPlanMealTableColumns( TableComponents< FoodPortion > components ) {
      super( components );
      this.consumptionProperties = components.checkBoxController();
   }//End Constructor
   
   @Override protected void changeColumns() {
      TableColumn< ConceptTableRow< FoodPortion >, Boolean > column = new TableColumn<>();
      column.setCellValueFactory( param -> consumptionProperties.propertyFor( param.getValue().concept() ) );
      column.setCellFactory( CheckBoxTableCell.forTableColumn( column ) );
      column.setEditable( true );
      table().getColumns().add( column );
      
      super.changeColumns();
   }//End Method

}//End Class
