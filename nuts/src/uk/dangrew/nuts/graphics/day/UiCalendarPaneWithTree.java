/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.food.UnresponsiveConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRowImpl;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.tree.DayPlanTreePane;
import uk.dangrew.nuts.graphics.template.TemplateTableColumns;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiCalendarPaneWithTree extends GridPane {

   static final double DATES_HEIGHT_PROPORTION = 30.0;
   static final double TEMPLATES_HEIGHT_PROPORTION = 10.0;
   static final double PLAN_VIEW_HEIGHT_PROPORTION = 60.0;

   private final UiCalendar uiCalendar;
   private final DayPlanTreePane dayPlanTree;
   private final ConceptTable< Template > templatesTable;
//   private final ConceptTableWithControls< FoodPortion > templateView;
//   private final TemplateTableController templateController;
   
//   private final ConceptTableWithControls< FoodPortion > mealView;
//   private final MealTableControllerImpl mealTableController;
   
//   private final ConsumptionProperties consumptionProperties;

   public UiCalendarPaneWithTree( Database database ) {
      this( new JavaFxStyle(), database );
   }// End Constructor

   UiCalendarPaneWithTree( JavaFxStyle styling, Database database ) {
      this.uiCalendar = new UiCalendar( database );
//      this.consumptionProperties = new ConsumptionProperties();
      
      styling.configureConstraintsForRowPercentages( 
               this, 
               DATES_HEIGHT_PROPORTION,
               TEMPLATES_HEIGHT_PROPORTION,
               PLAN_VIEW_HEIGHT_PROPORTION
      );
      styling.configureConstraintsForEvenColumns( this, 1 );

      add( uiCalendar, 0, 0 );
      add( templatesTable = new TableComponents< Template >()
               .withDatabase( database )
               .withColumns( TemplateTableColumns::new )
               .withController( new UnresponsiveConceptTableController<>() )
               .buildTable(), 
      0, 1 );
      add( dayPlanTree = new DayPlanTreePane( database.dayPlanController() ), 0, 2 );
      
//      templateView.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
//         Food food = n.concept().food().get();
//         if ( food instanceof Meal ) {
//            mealTableController.showMeal( ( Meal )food );
//         } else {
//            mealTableController.showMeal( null );
//         }
//      } );
//      
//      templatesTable.getRows().clear();
      uiCalendar.controller().selector().selection().addListener( ( s, o, n ) -> {
         dayPlanTree.controller().show( n );
//         templateController.showMeal( n );
         templatesTable.getRows().clear();
         templatesTable.getRows().add( new ConceptTableRowImpl<>( n ) );
//         consumptionProperties.setDayPlan( n );
      } );
   }// End Constructor
   
}//End Class
