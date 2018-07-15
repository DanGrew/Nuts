package uk.dangrew.nuts.graphics.recipe.creator;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.meal.MealTableColumns;
import uk.dangrew.nuts.graphics.meal.MealTableControllerImpl;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.store.Database;

public class UiRecipeCreatorPane extends BorderPane {
   
   private final MealTableControllerImpl resultController;
   
   public UiRecipeCreatorPane() {
      setBottom( new TableComponents< FoodPortion >()
               .withDatabase( new Database() )
               .applyColumns( MealTableColumns::new )
               .withController( resultController = new MealTableControllerImpl() )
               .buildTable()
      );
      setCenter( new UiRecipeCreatorConfigurationPane( resultController ) );
   }//End Constructor

}//End Class
