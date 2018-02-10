/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.food;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;

/**
 * {@link GeneralFoodTable} provides a {@link FoodTable} with a {@link GeneralFoodTableController}.
 */
public class GeneralFoodTable< FoodTypeT extends Food > extends ConceptTable< FoodTypeT >{

   public GeneralFoodTable( Database database, ConceptStore< FoodTypeT > foods ) {
      super( 
               new FoodTableColumns<>(),
               new GeneralFoodTableController<>( database, foods )
      );
   }//End Constructor
   
   @Override public GeneralFoodTableController< FoodTypeT > controller() {
      return ( GeneralFoodTableController< FoodTypeT > ) super.controller();
   }//End Method

}//End Class
