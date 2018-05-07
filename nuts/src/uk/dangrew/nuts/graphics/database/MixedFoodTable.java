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
import uk.dangrew.nuts.graphics.selection.model.FoodModel;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.store.Database;

public class MixedFoodTable extends ConceptTable< Food >{

   public MixedFoodTable( Database database, FoodModel model ) {
      this( 
               new FoodTableColumns<>(),
               new MixedFoodTableController( database, model )
      );
   }//End Constructor
   
   public MixedFoodTable( FoodTableColumns< Food > columns, ConceptTableController< Food > controller ) {
      super( 
               columns,
               controller
      );
   }//End Constructor
   
   @Override public MixedFoodTableController controller() {
      return ( MixedFoodTableController ) super.controller();
   }//End Method

}//End Class
