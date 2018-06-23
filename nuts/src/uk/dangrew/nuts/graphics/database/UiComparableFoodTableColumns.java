/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.database;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.TableConfiguration;

public class UiComparableFoodTableColumns extends FoodTableColumns< Food > {

   private final TableConfiguration configuration;
   private final CheckBoxFoodModelController controller;
   
   public UiComparableFoodTableColumns( TableComponents< Food > components ) {
      super( components );
      this.configuration = new TableConfiguration();
      this.controller = new CheckBoxFoodModelController( components.foodModel() );
   }//End Constructor
   
   @Override public void changeColumns() {
      table().setEditable( true );
      configuration.configureCheckBoxController( table(), controller, 0.05 );
      super.changeColumns();
   }//End Method

}//End Class
