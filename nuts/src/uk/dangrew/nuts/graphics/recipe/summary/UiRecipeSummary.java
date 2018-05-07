package uk.dangrew.nuts.graphics.recipe.summary;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.Conversions;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.LabelBuilder;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;

public class UiRecipeSummary extends GridPane {
   
   static final String INDIVIDUAL_INGREDIENTS = "Individual Ingredients";
   static final String COLUMN_HEADER_PROTEIN_G_ML = "Protein (g/ml)";
   static final String COLUMN_HEADER_FATS_G_ML = "Fats (g/ml)";
   static final String COLUMN_HEADER_CARBS_G_ML = "Carbs (g/ml)";
   static final String COLUMN_HEADER_CALORIES_KCAL = "Calories (kcal)";
   static final String COLUMN_HEADER_PORTION = "Portion (%)";
   static final String COLUMN_HEADER_FOOD = "Food";
   static final String INDENTATION = "---- "; 
   
   private static final Color HEADER_BACKGROUND = Color.BLACK;
   private static final Color TOTAL_STATS_BACKGROUND = Color.CORNFLOWERBLUE;
   private static final Color MEAL_BACKGROUND = Color.GRAY;
   private static final Color ITEM_BACKGROUND = Color.WHITE;
   
   private static final Color LIGHT_TEXT_COLOUR = Color.WHITE;
   private static final Color DARK_TEXT_COLOUR = Color.BLACK;
   
   private final Conversions conversions;
   private final JavaFxStyle styling;
   
   private int rowCount;
   private int currentIndentation;
   private Meal individualIngredients;
   
   public UiRecipeSummary( Meal meal ) {
      this.conversions = new Conversions();
      this.styling = new JavaFxStyle();
      this.styling.configureConstraintsForColumnPercentages( this, 40, 12, 12, 12, 12, 12 );
      
      this.individualIngredients = new Meal( INDIVIDUAL_INGREDIENTS );
      
      this.addColumnHeaders();
      this.addTopLevelMeal( meal );
      
      this.styling.configureConstraintsForEvenRows( this, rowCount );
   }//End Class
   
   private void addTopLevelMeal( Meal meal ) {
      addFoodRow( new FoodPortion( meal, 100.0 ), TOTAL_STATS_BACKGROUND, LIGHT_TEXT_COLOUR );
      currentIndentation++;
      
      meal.portions().stream()
         .filter( p -> p.food().get() instanceof FoodItem )
         .forEach( p -> individualIngredients.portions().add( p ) );
      
      meal.portions().stream()
         .filter( p -> p.food().get() instanceof Meal )
         .forEach( this::addMealContents );
      
      addMealContents( new FoodPortion( individualIngredients, 100.0 ) );
   }//End Method
   
   private void addMealContents( FoodPortion mealPortion ){
      addFoodRow( mealPortion, MEAL_BACKGROUND, LIGHT_TEXT_COLOUR );
      currentIndentation++;
      
      Meal meal = ( Meal ) mealPortion.food().get();
      for ( FoodPortion portion : meal.portions() ) {
         if ( portion.food().get() instanceof FoodItem ) {
            addFoodItemUsage( portion );
         } else if ( portion.food().get() instanceof Meal ) {
            addMealContents( portion );
         }
      }
      currentIndentation--;
   }//End Method
   
   private void addColumnHeaders() {
      Label foodHeader = basicBoldLabel( COLUMN_HEADER_FOOD, HEADER_BACKGROUND, LIGHT_TEXT_COLOUR );
      Label portionHeader = basicBoldLabel( COLUMN_HEADER_PORTION, HEADER_BACKGROUND, LIGHT_TEXT_COLOUR );
      Label caloriesHeader = basicBoldLabel( COLUMN_HEADER_CALORIES_KCAL, HEADER_BACKGROUND, LIGHT_TEXT_COLOUR );
      Label carbsHeader = basicBoldLabel( COLUMN_HEADER_CARBS_G_ML, HEADER_BACKGROUND, LIGHT_TEXT_COLOUR );
      Label fatsHeader = basicBoldLabel( COLUMN_HEADER_FATS_G_ML, HEADER_BACKGROUND, LIGHT_TEXT_COLOUR );
      Label proteinHeader = basicBoldLabel( COLUMN_HEADER_PROTEIN_G_ML, HEADER_BACKGROUND, LIGHT_TEXT_COLOUR );
      addRow( foodHeader, portionHeader, caloriesHeader, carbsHeader, fatsHeader, proteinHeader );
   }//End Method
   
   private void addFoodRow( FoodPortion meal, Color backgroundColour, Color textColour ) {
      Label itemLabel = basicLabel( withIndentation( meal.food().get().properties().nameProperty().get() ), backgroundColour, textColour );
      Label portionLabel = basicLabel( textWrap( meal.portion().get() ), backgroundColour, textColour );
      Label caloriesLabel = basicLabel( textWrap( meal.properties().calories().get() ), backgroundColour, textColour );
      Label carbsLabel = basicLabel( textWrap( meal.properties().carbohydrates().get() ), backgroundColour, textColour );
      Label fatsLabel = basicLabel( textWrap( meal.properties().fats().get() ), backgroundColour, textColour );
      Label proteinLabel = basicLabel( textWrap( meal.properties().protein().get() ), backgroundColour, textColour );
      addRow( itemLabel, portionLabel, caloriesLabel, carbsLabel, fatsLabel, proteinLabel );
   }//End Method
   
   private void addFoodItemUsage( FoodPortion item ) {
      addFoodRow( item, ITEM_BACKGROUND, DARK_TEXT_COLOUR );
   }//End Method
   
   private String textWrap( double value ) {
      return conversions.format( value );
   }//End Method
   
   private String withIndentation( String value ) {
      StringBuilder builder = new StringBuilder();
      for ( int i = 0; i < currentIndentation; i++ ) {
         builder.append( INDENTATION );
      }
      builder.append( value );
      return builder.toString();
   }//End Method

   private Label basicLabel( String text, Color backgroundColour, Color textColour ) {
      return new LabelBuilder()
               .withText( text )
               .withTextColour( textColour )
               .withBackgroundColour( backgroundColour )
               .withMaxWidth()
               .withMaxHeight()
               .build();
   }//End Method
   
   private Label basicBoldLabel( String text, Color bakcgroundColour, Color textColour ) {
      return new LabelBuilder()
               .withText( text )
               .withTextColour( textColour )
               .withBackgroundColour( bakcgroundColour )
               .withMaxWidth()
               .withMaxHeight()
               .asBold()
               .build();
   }//End Method
   
   private void addRow( Label food, Label portion, Label calories, Label carbs, Label fats, Label protein ) {
      add( food, 0, rowCount );
      add( portion, 1, rowCount );
      add( calories, 2, rowCount );
      add( carbs, 3, rowCount );
      add( fats, 4, rowCount );
      add( protein, 5, rowCount );
      rowCount++;
   }//End Method
   
}//End Class
