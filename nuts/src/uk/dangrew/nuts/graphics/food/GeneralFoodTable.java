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
import uk.dangrew.nuts.system.ConceptStore;

/**
 * {@link GeneralFoodTable} provides a {@link FoodTable} with a {@link GeneralFoodTableController}.
 */
public class GeneralFoodTable< FoodTypeT extends Food > extends ConceptTable< FoodTypeT >{

   /**
    * Constructs a new {@link GeneralFoodTable}.
    * @param foods the {@link ConceptStore} to display.
    */
   public GeneralFoodTable( ConceptStore< FoodTypeT > foods ) {
      super( 
               new FoodTableColumns<>(),
               new GeneralConceptTableController<>( foods )
      );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public GeneralConceptTableController< FoodTypeT > controller() {
      return ( GeneralConceptTableController< FoodTypeT > ) super.controller();
   }//End Method

}//End Class
