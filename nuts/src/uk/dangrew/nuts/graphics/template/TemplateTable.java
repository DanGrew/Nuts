/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.template;

import uk.dangrew.nuts.graphics.food.GeneralFoodTableController;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.template.Template;
import uk.dangrew.nuts.template.TemplateStore;

/**
 * Provides a custom {@link TemplateTable} for {@link Template}s.
 */
public class TemplateTable extends FoodTable< Template > {

   /**
    * Constructs a new {@link TemplateTable}.
    * @param database the {@link Database}.
    */
   public TemplateTable( TemplateStore store ) {
      super( new TemplateTableColumns<>(), new GeneralFoodTableController<>( store ) );
   }//End Constructor
   
}//End Class
