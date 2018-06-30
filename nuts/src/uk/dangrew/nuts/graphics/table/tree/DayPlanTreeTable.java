package uk.dangrew.nuts.graphics.table.tree;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.control.TreeTableView;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TreeTableColumnConfigurer;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class DayPlanTreeTable extends TreeTableView< TreeTableBranchController >{

   private final NutsSettings settings;
   private final TableConfiguration configuration;
   
   private TreeTableBranchItem root;
   
   public DayPlanTreeTable() {
      this.settings = new NutsSettings();
      this.configuration = new TableConfiguration();
      this.setEditable( true );
      this.setShowRoot( false );
      
      configuration.initialiseStringColumn(
               new TreeTableColumnConfigurer<>( this ), 
               "Food", 
               0.3, 
               r -> r.food().get() == null ? "Empty" : r.food().get().properties().nameProperty().get()
      );
      configuration.configureCheckBoxController( 
               new TreeTableColumnConfigurer<>( this ), 
               null,
               0.2
      );
      configuration.initialisePortionColumn( 
               new TreeTableColumnConfigurer<>( this ),
               "Portion %", 
               0.1 
      );
      configuration.configureVisibleNutrientUnitColumns( 
               () -> new TreeTableColumnConfigurer<>( this ), 
               FoodPortion::nutrition, 
               NutritionalUnit::displayName, 
               0.2,
               false,
               settings
      );
   }//End Constructor
   
   public void setFocus( Meal focus ) {
      PlatformImpl.runAndWait( () -> {
         this.setRoot( root = new TreeTableBranchItem( new FoodPortion( focus, 100 ) ) );
      } );
   }//End Method

   public Meal getFocus() {
      return ( Meal )root.getValue().concept().food().get();
   }//End Method

}//End Class
