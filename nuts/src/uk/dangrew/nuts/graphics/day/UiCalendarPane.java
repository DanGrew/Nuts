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
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.food.UnresponsiveConceptTableController;
import uk.dangrew.nuts.graphics.meal.MealTable;
import uk.dangrew.nuts.graphics.meal.MealTableWithControls;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.template.TemplateTable;
import uk.dangrew.nuts.graphics.template.TemplateTableController;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class UiCalendarPane extends GridPane {

   static final double DATES_HEIGHT_PROPORTION = 30.0;
   static final double TEMPLATES_HEIGHT_PROPORTION = 10.0;
   static final double PLAN_VIEW_HEIGHT_PROPORTION = 30.0;
   static final double MEAL_VIEW_HEIGHT_PROPORTION = 30.0;

   private final UiCalendar uiCalendar;
   private final TemplateTable templatesTable;
   private final MealTableWithControls templateView;
   private final MealTableWithControls mealView;
   private final ConsumptionProperties consumptionProperties;

   public UiCalendarPane( Database database ) {
      this( new JavaFxStyle(), database );
   }// End Constructor

   UiCalendarPane( JavaFxStyle styling, Database database ) {
      this.uiCalendar = new UiCalendar( database );
      this.consumptionProperties = new ConsumptionProperties();
      
      styling.configureConstraintsForRowPercentages( 
               this, 
               DATES_HEIGHT_PROPORTION,
               TEMPLATES_HEIGHT_PROPORTION,
               PLAN_VIEW_HEIGHT_PROPORTION,
               MEAL_VIEW_HEIGHT_PROPORTION
      );
      styling.configureConstraintsForEvenColumns( this, 1 );

      add( uiCalendar, 0, 0 );
      add( templatesTable = new TemplateTable( database, new UnresponsiveConceptTableController<>() ), 0, 1 );
      MealTable tableWithConsumption = new MealTable( 
               new UiDayPlanMealTableColumns( database, consumptionProperties ), 
               new TemplateTableController() 
      );
      add( templateView = new MealTableWithControls( "Selected Day", tableWithConsumption ), 0, 2 );
      add( mealView = new MealTableWithControls( "Selected Meal", database ), 0, 3 );
      
      templateView.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         Food food = n.concept().food().get();
         if ( food instanceof Meal ) {
            mealView.table().controller().showMeal( ( Meal )food );
         } else {
            mealView.table().controller().showMeal( null );
         }
      } );
      
      templatesTable.getRows().clear();
      uiCalendar.controller().selector().selection().addListener( ( s, o, n ) -> {
         templateView.table().controller().showMeal( n );
         templatesTable.getRows().clear();
         templatesTable.getRows().add( new ConceptTableRow<>( n ) );
         consumptionProperties.setDayPlan( n );
      } );
   }// End Constructor
   
}//End Class
