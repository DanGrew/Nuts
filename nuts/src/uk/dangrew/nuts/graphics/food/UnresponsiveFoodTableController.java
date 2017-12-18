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
import uk.dangrew.nuts.graphics.table.ConceptTableController;

public class UnresponsiveFoodTableController< FoodTypeT extends Food > implements ConceptTableController< FoodTypeT > {

   @Override public void associate( ConceptTable< FoodTypeT > table ) {}//End Method
   
   @Override public FoodTypeT createConcept() {
      return null;
   }//End Method

   @Override public void removeSelectedConcept() {}//End Method
   
   @Override public void copySelectedConcept() {}//End Method
   
}//End Class
