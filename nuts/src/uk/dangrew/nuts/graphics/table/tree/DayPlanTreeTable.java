package uk.dangrew.nuts.graphics.table.tree;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.control.TreeTableView;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanController;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableColumnWidths;
import uk.dangrew.nuts.graphics.table.configuration.TreeTableColumnConfigurer;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;

public class DayPlanTreeTable extends TreeTableView< TreeTableController >{

   private final DayPlanController dayPlanController;
   private final NutsSettings settings;
   private final TableConfiguration configuration;
   private final TableColumnWidths widths;
   
   private DayPlan plan;
   
   public DayPlanTreeTable( Database database ) {
      this( 
               database,
               new TableConfiguration(), 
               new TableColumnWidths()
                  .withFoodNameWidth( 0.3 )
                  .withPortionWidth( 0.1 )
                  .withCombinedUnitWidth( 0.6 )
      );
   }//End Constructor
   
   DayPlanTreeTable( 
            Database database,
            TableConfiguration configuration, 
            TableColumnWidths widths
   ) {
      this.settings = database.settings();
      this.dayPlanController = database.dayPlanController();
      this.configuration = configuration;
      this.widths = widths;
      this.setEditable( true );
      
      this.configuration.initialiseStringColumn(
               new TreeTableColumnConfigurer<>( this ), 
               "Food", 
               widths.foodNameWidth(), 
               r -> r.food().get().properties().nameProperty(),
               true
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
   
   NutsSettings settings(){
      return settings;
   }//End Method
   
   DayPlanController dayPlanController(){
      return dayPlanController;
   }//End Method

}//End Class
