package uk.dangrew.nuts.graphics.database;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.meal.MealControls;
import uk.dangrew.nuts.graphics.meal.MealTableColumns;
import uk.dangrew.nuts.graphics.meal.MealTableController;
import uk.dangrew.nuts.graphics.meal.MealTableControllerImpl;
import uk.dangrew.nuts.graphics.meal.RecipeControls;
import uk.dangrew.nuts.graphics.selection.model.FoodFilterModel;
import uk.dangrew.nuts.graphics.selection.model.FoodFilters;
import uk.dangrew.nuts.graphics.selection.model.SimpleFoodModel;
import uk.dangrew.nuts.graphics.selection.view.FoodSelectionControlsConfiguration;
import uk.dangrew.nuts.graphics.selection.view.UiFoodFilter;
import uk.dangrew.nuts.graphics.selection.view.UiFoodFilterImpl;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionControls;
import uk.dangrew.nuts.graphics.table.ConceptControls;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseComponents;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class UiDatabaseManagerPane extends GridPane {

   private final UiFoodSelectionControls foodSelectionControls;
   private final MealControls mealTableControls;
   
   private final ConceptTableWithControls< Food > foodTable;
   private final ConceptTableWithControls< Food > comparisonTable;
   private final ConceptTableWithControls< FoodPortion > mealTable;
   
   private final MixedFoodTableController mixedTableController;
   private final MealTableController mealTableController;
   private final RecipeController recipeController;
   
   private final SimpleFoodModel comparisonModel;
   
   public UiDatabaseManagerPane( NutsSettings settings, Database database ) {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForColumnPercentages( this, 50, 50 );
      
      FoodFilterModel filterModel = new FoodFilterModel( database );
      UiFoodFilter filter = new UiFoodFilterImpl( database, filterModel );
      
      comparisonModel = new SimpleFoodModel();
      recipeController = new RecipeController( database, filterModel );
      
      add( foodSelectionControls = new UiFoodSelectionControls( 
               database.labels().objectList(), 
               filter,
               new FoodSelectionControlsConfiguration()
                  .withoutReverseSorting()
                  .withoutFilter( FoodFilters.Default )
                  .withoutFilter( FoodFilters.Selection )
      ), 0, 0 );
      add( foodTable = new ConceptTableWithControls<>( 
               "Foods", 
               new TableComponents< Food >()
                        .withSettings( settings )
                        .withFoodModel( comparisonModel )
                        .withColumns( UiComparableFoodTableColumns::new ) 
                        .withController( recipeController )
                        .buildTable(),
               new RecipeControls( recipeController )
      ), 0, 1 );
      add( comparisonTable = new TableComponents< Food >()
                  .withSettings( settings )
                  .withColumns( FoodTableColumns< Food >::new )
                  .withController( mixedTableController = new MixedFoodTableController( database, comparisonModel ) )
                  .withControls( new ConceptControls( mixedTableController ) )
                  .buildTableWithControls( "Comparison" ), 
      1, 1 );
      add( mealTable = new TableComponents< FoodPortion >()
               .withSettings( settings )
               .withDatabase( database )
               .withColumns( MealTableColumns::new )
               .withController( mealTableController = new MealTableControllerImpl() )
               .withControls( mealTableControls = new MealControls( mealTableController ) )
               .buildTableWithControls( "Contents of Selection" ), 
      1, 2 );
      
      SynchronizedTableSelectionModel< Food > selectionSynchronizer = new SynchronizedTableSelectionModel<>( foodTable.table(), comparisonTable.table() );
      selectionSynchronizer.selected().addListener( this::handleSelection );
      
      GridPane.setRowSpan( foodTable, 2 );
      GridPane.setColumnSpan( foodSelectionControls, 2 );
   }//End Constructor
   
   private void handleSelection( ObservableValue< ? extends Food > source, Food old, Food updated ) {
      if ( updated instanceof Meal ) {
         mealTableController.showMeal( ( Meal )updated );
      } else {
         mealTableController.showMeal( null );
      }
   }//End Method
   
   ConceptTable< Food > foodTable(){
      return foodTable.table();
   }//End Method
   
   ConceptTable< Food > comparisonTable(){
      return comparisonTable.table();
   }//End Method
   
   ConceptTable< FoodPortion > mealTable(){
      return mealTable.table();
   }//End Method
   
   MealTableController mealTableController(){
      return mealTableController;
   }//End Method
   
   public void populateComponents( DatabaseComponents components ){
      components
               .withMainTable( foodTable.table() )
               .withMainTableAddButton( foodTable.controls().addButton() )
               .withMainTableFoodTypeDialog( recipeController.foodTypeSelectionDialog() )
      
               .withMealTable( mealTable.table() )
               .withMealTableAddButton( mealTable.controls().addButton() )
               .withMealTableRemoveButton( mealTable.controls().removeButton() )
               .withMealTableCopyButton( mealTable.controls().copyButton() )
               .withMealTableUpButton( mealTableControls.upButton() )
               .withMealTableDownButton( mealTableControls.downButton() )
               ;
   }//End Method
}//End Class
