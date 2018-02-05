package uk.dangrew.nuts.graphics.stock;

import uk.dangrew.nuts.graphics.meal.MealTable;
import uk.dangrew.nuts.store.Database;

public class StockTable extends MealTable {

   public StockTable( Database database ) {
      super( new StockTableColumns() );
      controller().showMeal( database.stockLists().objectList().get( 0 ) );
   }//End Constructor

}//End Class
