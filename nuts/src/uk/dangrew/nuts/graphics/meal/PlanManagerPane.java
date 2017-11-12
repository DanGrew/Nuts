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
import uk.dangrew.nuts.graphics.table.FoodTableWithControls;
import uk.dangrew.nuts.graphics.template.TemplateTable;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

/**
 * {@link PlanManagerPane} provides the pane for planning {@link Meal}s.
 */
public class PlanManagerPane extends GridPane {

   static final double PLANS_HEIGHT_PROPORTION = 25.0;
   static final double PLAN_VIEW_HEIGHT_PROPORTION = 40.0;
   static final double MEAL_VIEW_HEIGHT_PROPORTION = 35.0;

   private final FoodTableWithControls< Template > templatesTable;
   private final MealTableWithControls planView;
   private final MealTableWithControls mealView;

   /**
    * Constructs a new {@link PlanManagerPane}.
    * @param database the {@link Database}.
    */
   public PlanManagerPane( Database database ) {
      this( new JavaFxStyle(), database );
   }// End Constructor

   /**
    * Constructs a new {@link PlanManagerPane}.
    * @param styling the {@link JavaFxStyle}.
    * @param database the {@link Database}.
    */
   PlanManagerPane( JavaFxStyle styling, Database database ) {
      styling.configureConstraintsForRowPercentages( 
               this, 
               PLANS_HEIGHT_PROPORTION,
               PLAN_VIEW_HEIGHT_PROPORTION,
               MEAL_VIEW_HEIGHT_PROPORTION
      );
      styling.configureConstraintsForEvenColumns( this, 1 );

      add( templatesTable = new FoodTableWithControls<>( "Templates", new TemplateTable( database ) ), 0, 0 );
      add( planView = new MealTableWithControls( "Selected Template", database ), 0, 1 );
      add( mealView = new MealTableWithControls( "Selected Meal", database ), 0, 2 );
      
      templatesTable.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         planView.table().controller().showMeal( n.food() );
      } );
      
      planView.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         Food food = n.food().food().get();
         if ( food instanceof Meal ) {
            mealView.table().controller().showMeal( ( Meal )food );
         } else {
            mealView.table().controller().showMeal( null );
         }
      } );
   }// End Constructor

   FoodTableWithControls< Template > templatesTable() {
      return templatesTable;
   }// End Method

   MealTableWithControls mealTable() {
      return planView;
   }// End Method

}//End Class
