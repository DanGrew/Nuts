/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.food;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import uk.dangrew.nuts.configuration.NutritionalUnitShowingListenerImpl;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.nutrients.Nutrition;
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
   private ConceptTable< FoodTypeT > table;

   public FoodTableColumns( TableComponents< FoodTypeT > components ) {
      this.settings = components.settings();
      this.configuration = new TableConfiguration();
      this.settings.registrations().registerForUnitShowing( new NutritionalUnitShowingListenerImpl( 
               u -> populateColumns( table ), u -> populateColumns( table )
      ) );
   }//End Constructor
   
   protected ConceptTable< FoodTypeT > table() {
      return table;
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
      columnsForShowing( 
               table, 
               Food::nutrition,
               NutritionalUnit::name,
               0.98 - COLUMN_WIDTH_NAME 
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
   
   protected void columnsForShowing( 
            ConceptTable< FoodTypeT > table, 
            Function< FoodTypeT, Nutrition > source, 
            Function< NutritionalUnit, String > unitNaming,
            double availableWidth 
   ) {
      List< NutritionalUnit > showingUnits = Stream.of( NutritionalUnit.values() )
               .filter( u -> settings.showingPropertyFor( u ).get() )
               .collect( Collectors.toList() );
      
      double width = availableWidth / showingUnits.size();
      for ( NutritionalUnit unit : showingUnits ) {
         configuration.initialiseNutrientColumn( 
                  table, 
                  unitNaming.apply( unit ), 
                  width, 
                  f -> source.apply( f ).of( unit ), 
                  true 
         );
      }
   }//End Method
   
}//End Class
