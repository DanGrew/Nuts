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
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.food.UnresponsiveConceptTableController;
import uk.dangrew.nuts.graphics.meal.MealControls;
import uk.dangrew.nuts.graphics.meal.MealTableColumns;
import uk.dangrew.nuts.graphics.meal.MealTableControllerImpl;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.template.TemplateTableColumns;
import uk.dangrew.nuts.graphics.template.TemplateTableController;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiCalendarPane extends GridPane {

   static final double DATES_HEIGHT_PROPORTION = 30.0;
   static final double TEMPLATES_HEIGHT_PROPORTION = 10.0;
   static final double PLAN_VIEW_HEIGHT_PROPORTION = 30.0;
   static final double MEAL_VIEW_HEIGHT_PROPORTION = 30.0;

   private final UiCalendar uiCalendar;
   private final ConceptTable< Template > templatesTable;
   private final ConceptTableWithControls< FoodPortion > templateView;
   private final TemplateTableController templateController;
   
   private final ConceptTableWithControls< FoodPortion > mealView;
   private final MealTableControllerImpl mealTableController;
   
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
      add( templatesTable = new TableComponents< Template >()
               .withDatabase( database )
               .withColumns( TemplateTableColumns::new )
               .withController( new UnresponsiveConceptTableController<>() )
               .buildTable(), 
      0, 1 );
      add( templateView = new TableComponents< FoodPortion >()
               .withCheckBoxController( consumptionProperties )
               .withDatabase( database )
               .withColumns( UiDayPlanMealTableColumns::new )
               .withController( templateController = new TemplateTableController() )
               .withControls( new MealControls( templateController ) )
               .buildTableWithControls( "Selected Day" )
      , 0, 2 );
      add( mealView = new TableComponents< FoodPortion >()
               .withDatabase( database )
               .withColumns( MealTableColumns::new )
               .withController( mealTableController = new MealTableControllerImpl() )
               .withControls( new MealControls( mealTableController ) )
               .buildTableWithControls( "Selected Meal" ), 
      0, 3 );
      
      templateView.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         Food food = n.concept().food().get();
         if ( food instanceof Meal ) {
            mealTableController.showMeal( ( Meal )food );
         } else {
            mealTableController.showMeal( null );
         }
      } );
      
      templatesTable.getRows().clear();
      uiCalendar.controller().selector().selection().addListener( ( s, o, n ) -> {
         templateController.showMeal( n );
         templatesTable.getRows().clear();
         templatesTable.getRows().add( new ConceptTableRow<>( n ) );
         consumptionProperties.setDayPlan( n );
      } );
   }// End Constructor
   
}//End Class
