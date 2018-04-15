package uk.dangrew.nuts.graphics.database;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.meal.MealTable;
import uk.dangrew.nuts.graphics.meal.MealTableWithControls;
import uk.dangrew.nuts.graphics.selection.model.FoodFilterModel;
import uk.dangrew.nuts.graphics.selection.model.SimpleFoodModel;
import uk.dangrew.nuts.graphics.selection.view.UiFoodFilter;
import uk.dangrew.nuts.graphics.selection.view.UiFoodFilterImpl;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionControls;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class UiDatabaseManagerPane extends GridPane {

   private final ConceptTableWithControls< Food > foodTable;
   private final ConceptTableWithControls< Food > comparisonTable;
   private final MealTableWithControls mealTable;
   private final SimpleFoodModel comparisonModel;
   
   public UiDatabaseManagerPane( Database database ) {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForRowPercentages( this, 5, 35, 60 );
      styling.configureConstraintsForColumnPercentages( this, 60, 40 );
      
      FoodFilterModel filterModel = new FoodFilterModel( database );
      UiFoodFilter filter = new UiFoodFilterImpl( database, filterModel );
      
      comparisonModel = new SimpleFoodModel();
      
      add( new UiFoodSelectionControls( database.labels().objectList(), filter ), 0, 0 );
      add( foodTable = new ConceptTableWithControls<>( 
               "Foods", 
               new MixedFoodTable( new UiComparableFoodTableColumns( comparisonModel ), 
                        new MixedFoodTableController( database, filterModel ) 
               ) 
      ), 0, 1 );
      add( comparisonTable = new ConceptTableWithControls<>( "Comparison", new MixedFoodTable( database, comparisonModel ) ), 1, 1 );
      add( mealTable = new MealTableWithControls( "Contents of Selection", database ), 1, 2 );
      
      SynchronizedTableSelectionModel< Food > selectionSynchronizer = new SynchronizedTableSelectionModel<>( foodTable.table(), comparisonTable.table() );
      selectionSynchronizer.selected().addListener( this::handleSelection );
      
      GridPane.setRowSpan( foodTable, 2 );
   }//End Constructor
   
   private void handleSelection( ObservableValue< ? extends Food > source, Food old, Food updated ) {
      if ( updated instanceof Meal ) {
         mealTable.table().controller().showMeal( ( Meal )updated );
      } else {
         mealTable.table().controller().showMeal( null );
      }
   }//End Method
   
   ConceptTable< Food > foodTable(){
      return foodTable.table();
   }//End Method
   
   ConceptTable< Food > comparisonTable(){
      return comparisonTable.table();
   }//End Method
   
   MealTable mealTable(){
      return mealTable.table();
   }//End Method
}//End Class
