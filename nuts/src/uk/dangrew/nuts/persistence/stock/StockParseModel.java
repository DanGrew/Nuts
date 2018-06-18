package uk.dangrew.nuts.persistence.stock;

import uk.dangrew.nuts.persistence.meals.MealParseModel;
import uk.dangrew.nuts.persistence.resolution.StockPortionResolution;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.store.Database;

public class StockParseModel extends MealParseModel< Stock > {

   protected StockParseModel( Database database ) {
      super( database, database.stockLists() );
   }//End Constructor

   @Override protected void setId( String value ) {
      super.setId( value );
   }//End Method
   
   @Override protected void setFoodId( String value ) {
      super.setFoodId( value );
   }//End Method
   
   @Override protected void setPortionValue( Double value ) {
      super.setPortionValue( value );
   }//End Method
   
   @Override protected void finishPortion() {
      database().resolver().submitStrategy( new StockPortionResolution( meals(), id, foodId, portionValue ) );
   }//End Method
   
}//End Class
