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
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.table.food.FoodTableWithControls;
import uk.dangrew.nuts.table.meal.MealViewWithControls;

/**
 * {@link PlanManagerPane} provides the pane for planning {@link Meal}s.
 */
public class PlanManagerPane extends GridPane {

   static final double PLANS_HEIGHT_PROPORTION = 25.0;
   static final double PLAN_VIEW_HEIGHT_PROPORTION = 40.0;
   static final double MEAL_VIEW_HEIGHT_PROPORTION = 35.0;

   private final FoodTableWithControls< Meal > plansTable;
   private final MealViewWithControls planView;
   private final MealViewWithControls mealView;

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

      add( plansTable = new FoodTableWithControls<>( database.plans() ), 0, 0 );
      add( planView = new MealViewWithControls( database ), 0, 1 );
      add( mealView = new MealViewWithControls( database ), 0, 2 );
      
      plansTable.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         planView.mealView().table().controller().showMeal( n.food() );
         planView.mealView().mealSummary().showMeal( n.food() );
      } );
      
      planView.mealView().table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         Food food = n.food().food().get();
         if ( food instanceof Meal ) {
            mealView.mealView().table().controller().showMeal( ( Meal )food );
            mealView.mealView().mealSummary().showMeal( ( Meal )food );
         } else {
            mealView.mealView().table().controller().showMeal( null );
            mealView.mealView().mealSummary().showMeal( null );
         }
      } );
   }// End Constructor

   FoodTableWithControls< Meal > mealsTable() {
      return plansTable;
   }// End Method

   MealViewWithControls mealView() {
      return planView;
   }// End Method

}//End Class
