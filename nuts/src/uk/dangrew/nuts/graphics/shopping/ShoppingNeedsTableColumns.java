/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.shopping;

import java.util.Arrays;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.FoodOptions;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.graphics.table.FoodTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.store.Database;

/**
 * {@link ShoppingNeedsTableColumns} provides a {@link FoodTableColumnsPopulator} for a {@link FoodTable}.
 */
public class ShoppingNeedsTableColumns implements FoodTableColumnsPopulator< FoodPortion > {

   static final String COLUMN_TITLE_FOOD = "Food";
   static final String COLUMN_TITLE_PORTION = "Portion %";
   
   private final TableConfiguration configuration;
   private final FoodOptions foodOptions;

   /**
    * Constructs a new {@link ShoppingNeedsTableColumns}.
    * @param database the {@link Database} for access to plans.
    */
   public ShoppingNeedsTableColumns( Database database ) {
      this.foodOptions = new FoodOptions( Arrays.asList( database.templates() ) );
      this.configuration = new TableConfiguration();
   }//End Constructor
      
   /**
    * {@inheritDoc}
    */
   @Override public void populateColumns( FoodTable< FoodPortion > table ) {
      configuration.initialiseFoodDropDownColumn( 
               table, 
               COLUMN_TITLE_FOOD, 
               0.6, 
               r -> r.food().food(), 
               ( r, v ) -> r.food().setFood( v ),
               foodOptions 
      );
      
      configuration.initialisePortionColumn( table, COLUMN_TITLE_PORTION, 0.4 );
   }//End Method

}//End Class
