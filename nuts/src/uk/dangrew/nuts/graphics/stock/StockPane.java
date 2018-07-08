package uk.dangrew.nuts.graphics.stock;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.meal.MealTableControllerImpl;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.store.Database;

public class StockPane extends BorderPane {

   private final MealTableControllerImpl controller;
   
   public StockPane( Database database ) {
      setCenter( new TableComponents< FoodPortion >()
               .applyColumns( StockTableColumns::new )
               .withController( controller = new MealTableControllerImpl() )
               .buildTable()
      );
      controller.showMeal( database.stockLists().objectList().get( 0 ) );
   }//End Constructor

}//End Class
