/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.food;

import uk.dangrew.nuts.configuration.NutritionalUnitShowingListenerImpl;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnWidths;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.system.Properties;

/**
 * {@link FoodTableColumns} provides the {@link javafx.scene.control.TableColumn} configuration 
 * for a {@link FoodTable}.
 */
public class FoodTableColumns< FoodTypeT extends Food > implements ConceptTableColumnsPopulator< FoodTypeT > {

   protected static final String COLUMN_TITLE_FOOD = "Food";
   static final double COLUMN_WIDTH_NAME = 0.3;
   
   private final NutsSettings settings;
   private final TableConfiguration configuration;
   private final TableColumnWidths widths;
   private ConceptTable< FoodTypeT > table;

   public FoodTableColumns( TableComponents< FoodTypeT > components ) {
      this( 
               new TableColumnWidths()
                  .withFoodNameWidth( 0.3 )
                  .withCombinedUnitWidth( 0.7 ),
               components
      );
   }//End Constructor
   
   protected FoodTableColumns( TableColumnWidths widths, TableComponents< FoodTypeT > components ) {
      this.settings = components.settings();
      this.configuration = new TableConfiguration();
      this.widths = widths;
      this.settings.registrations().registerForUnitShowing( new NutritionalUnitShowingListenerImpl( 
               u -> populateColumns( table ), u -> populateColumns( table )
      ) );
   }//End Constructor
   
   protected ConceptTable< FoodTypeT > table() {
      return table;
   }//End Method
   
   protected NutsSettings settings(){
      return settings;
   }//End Method
   
   protected TableColumnWidths tableWidths(){
      return widths;
   }//End Method
   
   @Override public final void populateColumns( ConceptTable< FoodTypeT > table ) {
      allocateTable( table );
      changeColumns();
   }//End Method
   
   private void allocateTable( ConceptTable< FoodTypeT > table ) {
      this.table = table;
      this.table.getColumns().clear();
   }//End Method
   
   protected void changeColumns() {
      standardNameColumn( table, COLUMN_TITLE_FOOD, COLUMN_WIDTH_NAME );
      configuration.configureVisibleNutrientUnitColumns( 
               () -> new TableViewColumnConfigurer< FoodTypeT, String >( table ),
               widths,
               Food::nutrition,
               NutritionalUnit::name,
               true,
               settings
      );
   }//End Method
   
   protected void standardNameColumn(
            ConceptTable< FoodTypeT > table,
            String title, 
            double width
   ){
      configuration.initialiseFoodPropertyStringColumn( 
               table, 
               title, 
               width, 
               Properties::nameProperty, 
               true 
      );  
   }//End Method
   
}//End Class
