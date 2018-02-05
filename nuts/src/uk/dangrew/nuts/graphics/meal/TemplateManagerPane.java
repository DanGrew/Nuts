/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.meal;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.template.TemplateTable;
import uk.dangrew.nuts.graphics.template.TemplateTableController;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

/**
 * {@link TemplateManagerPane} provides the pane for planning {@link Meal}s.
 */
public class TemplateManagerPane extends GridPane {

   static final double PLANS_HEIGHT_PROPORTION = 25.0;
   static final double PLAN_VIEW_HEIGHT_PROPORTION = 40.0;
   static final double MEAL_VIEW_HEIGHT_PROPORTION = 35.0;

   private final ConceptTableWithControls< Template > templatesTable;
   private final MealTableWithControls planView;
   private final MealTableWithControls mealView;

   public TemplateManagerPane( Database database ) {
      this( new JavaFxStyle(), database );
   }// End Constructor

   /**
    * Constructs a new {@link PlanManagerPane}.
    * @param styling the {@link JavaFxStyle}.
    * @param database the {@link Database}.
    */
   TemplateManagerPane( JavaFxStyle styling, Database database ) {
      styling.configureConstraintsForRowPercentages( 
               this, 
               PLANS_HEIGHT_PROPORTION,
               PLAN_VIEW_HEIGHT_PROPORTION,
               MEAL_VIEW_HEIGHT_PROPORTION
      );
      styling.configureConstraintsForEvenColumns( this, 1 );

      add( templatesTable = new ConceptTableWithControls<>( "Templates", new TemplateTable( database ) ), 0, 0 );
      add( planView = new MealTableWithControls( "Selected Template", new MealTable( new MealTableColumns( database ), new TemplateTableController() ) ), 0, 1 );
      add( mealView = new MealTableWithControls( "Selected Meal", database ), 0, 2 );
      
      templatesTable.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         planView.table().controller().showMeal( n.concept() );
      } );
      
      planView.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         Food food = n.concept().food().get();
         if ( food instanceof Meal ) {
            mealView.table().controller().showMeal( ( Meal )food );
         } else {
            mealView.table().controller().showMeal( null );
         }
      } );
   }// End Constructor

   ConceptTableWithControls< Template > templatesTable() {
      return templatesTable;
   }// End Method

   MealTableWithControls mealTable() {
      return planView;
   }// End Method

}//End Class
