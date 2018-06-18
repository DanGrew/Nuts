/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.stock;

import uk.dangrew.nuts.persistence.fooditems.ConceptPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.meals.MealWriteModel;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.store.Database;

public class StockPersistence extends MealPersistence< Stock > implements ConceptPersistence {
   
   public StockPersistence( Database database ) {
      super( new StockParseModel( database ), new MealWriteModel<>( database.stockLists() ) );
   }//End Constructor
   
}//End Class
