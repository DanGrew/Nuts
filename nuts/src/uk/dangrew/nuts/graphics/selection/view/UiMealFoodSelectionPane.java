package uk.dangrew.nuts.graphics.selection.view;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.common.UiWindowControls;
import uk.dangrew.nuts.graphics.common.UiWindowControlsActions;
import uk.dangrew.nuts.graphics.common.UiWindowControlsActionsImpl;
import uk.dangrew.nuts.graphics.food.UnresponsiveConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRowImpl;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.template.TemplateTableColumns;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiMealFoodSelectionPane extends GridPane {

   private final ConceptTable< Template > table;
   private final UiFoodSelectionController controller;
   private final Template liveSelectionProperties;
   
   public UiMealFoodSelectionPane( Database database, FoodSelectionWindowStageControls stageControls ) {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForRowPercentages( this, 10, 85, 5 );
      styling.configureConstraintsForEvenColumns( this, 1 );
      
      this.liveSelectionProperties = new Template( "Original + Selection" );
      this.controller = new UiFoodSelectionController( database, liveSelectionProperties );
      
      add( table = new TableComponents< Template >()
               .withDatabase( database )
               .applyColumns( TemplateTableColumns::new )
               .withController( new UnresponsiveConceptTableController<>() )
               .buildTable(),  
      0, 0 );
      add( new UiFoodSelectionTabs( database, controller ), 0, 1 );
      
      UiWindowControlsActions windowActions = new UiWindowControlsActionsImpl( 
               () -> stageControls.apply( controller.getAndClearSelection() ),
               stageControls::cancel
      );
      add( new UiWindowControls( windowActions ), 0, 2 );
      
      table.getRows().clear();
      table.getRows().add( new ConceptTableRowImpl<>( liveSelectionProperties ) );
   }//End Constructor

   public void selectForMeal( FoodHolder meal ) {
      liveSelectionProperties.properties().nameProperty().set( meal.properties().nameProperty().get() );
      liveSelectionProperties.portions().clear();
      liveSelectionProperties.portions().addAll( meal.portions() );
      liveSelectionProperties.goalAnalytics().goal().set( null );
   }//End Method
   
   public void selectForTemplate( Template template ) {
      selectForMeal( template );
      liveSelectionProperties.goalAnalytics().goal().set( template.goalAnalytics().goal().get() );
   }//End Method
   
   Template liveSelectionProperties(){
      return liveSelectionProperties;
   }//End Method
   
}//End Class
