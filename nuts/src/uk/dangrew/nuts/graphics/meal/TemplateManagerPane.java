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
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.food.GeneralConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptControls;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.template.TemplateTableColumns;
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
   private final GeneralConceptTableController< Template > templateController;
   private final ConceptTableWithControls< FoodPortion > planView;
   private final TemplateTableController planController;
   private final ConceptTableWithControls< FoodPortion > mealView;
   private final MealTableControllerImpl mealController;

   public TemplateManagerPane( Database database ) {
      this( new JavaFxStyle(), database );
   }// End Constructor

   TemplateManagerPane( JavaFxStyle styling, Database database ) {
      styling.configureConstraintsForRowPercentages( 
               this, 
               PLANS_HEIGHT_PROPORTION,
               PLAN_VIEW_HEIGHT_PROPORTION,
               MEAL_VIEW_HEIGHT_PROPORTION
      );
      styling.configureConstraintsForEvenColumns( this, 1 );

      add( templatesTable = new TableComponents< Template >()
               .withDatabase( database )
               .withColumns( TemplateTableColumns::new )
               .withController( templateController = new GeneralConceptTableController<>( database.templates() ) )
               .withControls( new ConceptControls( templateController ) )
               .buildTableWithControls( "Templates" ), 
      0, 0 );
      add( planView = new TableComponents< FoodPortion >()
               .withDatabase( database )
               .withColumns( MealTableColumns::new )
               .withController( planController = new TemplateTableController() )
               .withControls( new MealControls( planController ) )
               .buildTableWithControls( "Selected Template" ), 
      0, 1 );
      add( mealView = new TableComponents< FoodPortion >()
               .withDatabase( database )
               .withColumns( MealTableColumns::new )
               .withController( mealController = new MealTableControllerImpl() )
               .withControls( new MealControls( mealController ) )
               .buildTableWithControls( "Selected Meal" ), 
      0, 2 );
      
      templatesTable.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         planController.showMeal( n.concept() );
      } );
      
      planView.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         Food food = n.concept().food().get();
         if ( food instanceof Meal ) {
            mealController.showMeal( ( Meal )food );
         } else {
            mealController.showMeal( null );
         }
      } );
   }// End Constructor

   ConceptTableWithControls< Template > templatesTable() {
      return templatesTable;
   }// End Method

}//End Class
