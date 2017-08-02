/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.meal;

import java.util.Arrays;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import uk.dangrew.kode.javafx.spinner.StringExtractConverter;
import uk.dangrew.kode.javafx.table.EditCommitHandler;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.FoodOptions;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.graphics.table.FoodTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.FoodTableConfiguration;
import uk.dangrew.nuts.graphics.table.FoodTableRow;
import uk.dangrew.nuts.store.Database;

/**
 * {@link MealTableColumns} provides the {@link TableColumn} configuration for a {@link MealTable}.
 */
public class MealTableColumns implements FoodTableColumnsPopulator< FoodPortion > {

   static final String COLUMN_TITLE_FOOD = "Food";
   static final String COLUMN_TITLE_CALORIES = "Calories";
   static final String COLUMN_TITLE_CALORIE_DENSITY = "Density";
   static final String COLUMN_TITLE_PORTION = "Portion %";
   static final String COLUMN_TITLE_CARBS = "Carbs";
   static final String COLUMN_TITLE_FATS = "Fats";
   static final String COLUMN_TITLE_PROTEINS = "Protein";
   static final String COLUMN_TITLE_CALORIES_PROPORTION = "Calories %";
   static final String COLUMN_TITLE_CARBS_PROPORTION = "Carbs %";
   static final String COLUMN_TITLE_FATS_PROPORTION = "Fats %";
   static final String COLUMN_TITLE_PROTEINS_PROPORTION = "Protein %";
   
   private final FoodOptions foodOptions;
   private final FoodTableConfiguration configuration;

   /**
    * Constructs a new {@link MealTableColumns}.
    * @param database the {@link Database} for the {@link uk.dangrew.nuts.meal.Meal} {@link Food}s.
    */
   public MealTableColumns( Database database ) {
      this.configuration = new FoodTableConfiguration();
      this.foodOptions = new FoodOptions( Arrays.asList( database.foodItems(), database.meals() ) );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void populateColumns( FoodTable< FoodPortion > table ) {
      TableColumn< FoodTableRow< FoodPortion >, Food > nameColumn = new TableColumn<>( COLUMN_TITLE_FOOD );
      nameColumn.setCellFactory( ComboBoxTableCell.forTableColumn( new StringExtractConverter<>( 
               object -> object.properties().nameProperty().get(),
               foodOptions::find,
               "None"
      ), foodOptions.options() ) );
      nameColumn.setCellValueFactory( object -> object.getValue().food().food() );
      nameColumn.prefWidthProperty().bind( table.widthProperty().multiply( 0.25 ) );
      nameColumn.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> r.food().setFood( v ) ) );
      table.getColumns().add( nameColumn );
      
      TableColumn< FoodTableRow< FoodPortion >, String > portionColumn = new TableColumn<>( COLUMN_TITLE_PORTION );
      portionColumn.prefWidthProperty().bind( table.widthProperty().multiply( 0.05 ) );
      portionColumn.setCellValueFactory( object -> object.getValue().food().portion().asString() );
      table.getColumns().add( portionColumn );
      
      portionColumn.setEditable( true );
      portionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
      portionColumn.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> r.food().setPortion( Double.valueOf( v ) ) ) );
      
      configuration.initialNutrientColumn( table, COLUMN_TITLE_CALORIES, 0.08, f -> f.properties().calories(), false );
      configuration.initialNutrientColumn( table, COLUMN_TITLE_CARBS, 0.08, f -> f.properties().carbohydrates(), false );
      configuration.initialNutrientColumn( table, COLUMN_TITLE_FATS, 0.08, f -> f.properties().fats(), false );
      configuration.initialNutrientColumn( table, COLUMN_TITLE_PROTEINS, 0.08, f -> f.properties().protein(), false );
      
      configuration.initialRatioColumn( table, COLUMN_TITLE_CALORIES_PROPORTION, 0.08, f -> f.goalAnalytics().caloriesRatioProperty() );
      configuration.initialRatioColumn( table, COLUMN_TITLE_CARBS_PROPORTION, 0.08, f -> f.goalAnalytics().carbohydratesRatioProperty() );
      configuration.initialRatioColumn( table, COLUMN_TITLE_FATS_PROPORTION, 0.08, f -> f.goalAnalytics().fatsRatioProperty() );
      configuration.initialRatioColumn( table, COLUMN_TITLE_PROTEINS_PROPORTION, 0.08, f -> f.goalAnalytics().proteinRatioProperty() );
   }//End Method

}//End Class
