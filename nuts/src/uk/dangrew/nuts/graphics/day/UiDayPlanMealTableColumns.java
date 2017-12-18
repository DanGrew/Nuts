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
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.meal.MealTableColumns;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.store.Database;

public class UiDayPlanMealTableColumns extends MealTableColumns {

   private final ConsumptionProperties consumptionProperties;
   
   public UiDayPlanMealTableColumns( Database database, ConsumptionProperties consumptionProperties ) {
      super( database );
      this.consumptionProperties = consumptionProperties;
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< FoodPortion > table ) {
      TableColumn< ConceptTableRow< FoodPortion >, Boolean > column = new TableColumn<>();
      column.setCellValueFactory( param -> consumptionProperties.propertyFor( param.getValue().concept() ) );
      column.setCellFactory( CheckBoxTableCell.forTableColumn( column ) );
      column.setEditable( true );
      table.getColumns().add( column );
      
      super.populateColumns( table );
   }//End Method

}//End Class
