/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.meal;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import uk.dangrew.kode.javafx.spinner.StringExtractConverter;
import uk.dangrew.kode.javafx.table.EditCommitHandler;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.table.food.FoodTableConfiguration;
import uk.dangrew.nuts.table.food.FoodTableRow;

/**
 * {@link MealTable} provides a {@link TableView} for {@link FoodPortion}s.
 */
public class MealTable extends TableView< FoodTableRow< FoodPortion > > {

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
   private final MealTableController controller;

   /**
    * Constructs a new {@link MealTable}.
    * @param database the {@link Database} for the {@link uk.dangrew.nuts.meal.Meal} {@link Food}s.
    */
   public MealTable( Database database ) {
      this( new MealTableController(), database );
   }//End Constructor
      
   /**
    * Constructs a new {@link MealTable}.
    * @param controller the {@link MealTableController}.
    * @param database the {@link Database} for the {@link uk.dangrew.nuts.meal.Meal} {@link Food}s.
    */
   MealTable( MealTableController controller, Database database ) {
      this.controller = controller;
      this.controller.associate( this );
      this.configuration = new FoodTableConfiguration();
      this.foodOptions = new FoodOptions( database );
      initialiseColumns();
   }//End Constructor
   
   /**
    * Friendly access to the {@link #getItems()}.
    * @return the {@link ObservableList}.
    */
   public ObservableList< FoodTableRow< FoodPortion > > getRows(){
      return getItems();
   }//End Method
   
   /**
    * Method to initialise the {@link javafx.scene.control.TableColumn}s in the {@link TableView}. 
    */
   private void initialiseColumns(){
      setEditable( true );
      
      TableColumn< FoodTableRow< FoodPortion >, Food > nameColumn = new TableColumn<>( COLUMN_TITLE_FOOD );
      nameColumn.setCellFactory( ComboBoxTableCell.forTableColumn( new StringExtractConverter<>( 
               object -> object.properties().nameProperty().get(),
               foodOptions::find,
               "None"
      ), foodOptions.options() ) );
      nameColumn.setCellValueFactory( object -> object.getValue().food().food() );
      nameColumn.prefWidthProperty().bind( widthProperty().multiply( 0.25 ) );
      nameColumn.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> r.food().setFood( v ) ) );
      getColumns().add( nameColumn );
      
      TableColumn< FoodTableRow< FoodPortion >, String > portionColumn = new TableColumn<>( COLUMN_TITLE_PORTION );
      portionColumn.prefWidthProperty().bind( widthProperty().multiply( 0.05 ) );
      portionColumn.setCellValueFactory( object -> object.getValue().food().portion().asString() );
      getColumns().add( portionColumn );
      
      portionColumn.setEditable( true );
      portionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
      portionColumn.setOnEditCommit( new EditCommitHandler<>( ( r, v ) -> r.food().setPortion( Double.valueOf( v ) ) ) );
      
      configuration.initialNutrientColumn( this, COLUMN_TITLE_CALORIES, 0.08, f -> f.properties().calories(), false );
      configuration.initialNutrientColumn( this, COLUMN_TITLE_CARBS, 0.08, f -> f.properties().carbohydrates(), false );
      configuration.initialNutrientColumn( this, COLUMN_TITLE_FATS, 0.08, f -> f.properties().fats(), false );
      configuration.initialNutrientColumn( this, COLUMN_TITLE_PROTEINS, 0.08, f -> f.properties().protein(), false );
      
      configuration.initialRatioColumn( this, COLUMN_TITLE_CALORIES_PROPORTION, 0.08, f -> f.goalAnalytics().caloriesRatioProperty() );
      configuration.initialRatioColumn( this, COLUMN_TITLE_CARBS_PROPORTION, 0.08, f -> f.goalAnalytics().carbohydratesRatioProperty() );
      configuration.initialRatioColumn( this, COLUMN_TITLE_FATS_PROPORTION, 0.08, f -> f.goalAnalytics().fatsRatioProperty() );
      configuration.initialRatioColumn( this, COLUMN_TITLE_PROTEINS_PROPORTION, 0.08, f -> f.goalAnalytics().proteinRatioProperty() );
   }//End Method

   /**
    * Access to the {@link MealTableController}.
    * @return the {@link MealTableController}.
    */
   public MealTableController controller() {
      return controller;
   }//End Method

}//End Class
