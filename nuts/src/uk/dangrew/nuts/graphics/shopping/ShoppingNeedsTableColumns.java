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

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptOptions;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.TableConfiguration;

/**
 * {@link ShoppingNeedsTableColumns} provides a {@link FoodTableColumnsPopulator} for a {@link FoodTable}.
 */
public class ShoppingNeedsTableColumns implements ConceptTableColumnsPopulator< FoodPortion > {

   static final String COLUMN_TITLE_FOOD = "Food";
   static final String COLUMN_TITLE_PORTION = "Portion %";
   
   private final TableConfiguration configuration;
   private final ConceptOptions< Food > conceptOptions;

   public ShoppingNeedsTableColumns( TableComponents< FoodPortion > components ) {
      this.conceptOptions = new ConceptOptionsImpl<>( Arrays.asList( components.database().templates() ) );
      this.configuration = new TableConfiguration();
   }//End Constructor
      
   @Override public void populateColumns( ConceptTable< FoodPortion > table ) {
      configuration.initialiseFoodDropDownColumn( 
               table, 
               COLUMN_TITLE_FOOD, 
               0.6, 
               FoodPortion::food, 
               ( r, v ) -> r.setFood( v ),
               conceptOptions 
      );
      
      configuration.initialisePortionColumn( table, COLUMN_TITLE_PORTION, 0.4 );
   }//End Method

}//End Class
