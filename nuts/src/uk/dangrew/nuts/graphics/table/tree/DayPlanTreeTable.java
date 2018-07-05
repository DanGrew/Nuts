package uk.dangrew.nuts.graphics.table.tree;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.control.TreeTableView;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.dayplan.DayPlan;
import uk.dangrew.nuts.dayplan.DayPlanController;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnWidths;
import uk.dangrew.nuts.graphics.table.configuration.TreeTableColumnConfigurer;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class DayPlanTreeTable extends TreeTableView< TreeTableController >{

   private final DayPlanController dayPlanController;
   private final NutsSettings settings;
   private final TableConfiguration configuration;
   private final TableColumnWidths widths;
   
   private DayPlan plan;
   
   public DayPlanTreeTable() {
      this( 
               new TableConfiguration(), 
               new TableColumnWidths()
                  .withFoodNameWidth( 0.3 )
                  .withPortionWidth( 0.1 )
                  .withCombinedUnitWidth( 0.6 )
      );
   }//End Constructor
   
   DayPlanTreeTable( TableConfiguration configuration, TableColumnWidths widths ) {
      this.settings = new NutsSettings();
      this.dayPlanController = new DayPlanController();
      this.configuration = configuration;
      this.widths = widths;
      this.setEditable( true );
      
      this.configuration.initialiseStringColumn(
               new TreeTableColumnConfigurer<>( this ), 
               "Food", 
               widths.foodNameWidth(), 
               r -> r.food().get() == null ? "Empty" : r.food().get().properties().nameProperty().get()
      );
//      configuration.configureCheckBoxController( 
//               new TreeTableColumnConfigurer<>( this ), 
//               null,
//               0.2
//      );
      this.configuration.initialisePortionColumn( 
               new TreeTableColumnConfigurer<>( this ),
               "Portion %", 
               widths.portionWidth() 
      );
      this.configuration.configureVisibleNutrientUnitColumns( 
               () -> new TreeTableColumnConfigurer<>( this ),
               widths,
               FoodPortion::nutrition, 
               NutritionalUnit::displayName, 
               false,
               settings
      );
   }//End Constructor
   
   public void setFocus( DayPlan focus ) {
      PlatformImpl.runAndWait( () -> {
         this.setRoot( new TreeTableRootItem( plan = focus, dayPlanController ) );
      } );
   }//End Method

   public DayPlan getFocus() {
      return plan;
   }//End Method

}//End Class
