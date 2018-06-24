package uk.dangrew.nuts.graphics.recipe.summary;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.Label;
import uk.dangrew.kode.javafx.style.Conversions;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;

public class UiRecipeSummaryTest {

   private static final String DOUBLE_INDENTATION = UiRecipeSummary.INDENTATION + UiRecipeSummary.INDENTATION;
   
   private Meal subject;
   private UiRecipeSummary systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      Database database = new Database();
      DataLocation.loadSampleFoodData( database );
      
      subject = database.meals().get( "9dbeb24c-9a24-462e-9ea8-c6ad886e2fe3" );
      systemUnderTest = new UiRecipeSummary( subject );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 9999999 );
   }//End Method
   
   @Test public void shouldProvideExpectedRows(){
      int row = 0;
      
      assertLabelTextIs( 0, row, UiRecipeSummary.COLUMN_HEADER_FOOD );
      assertLabelTextIs( 1, row, UiRecipeSummary.COLUMN_HEADER_PORTION );
      assertLabelTextIs( 2, row, UiRecipeSummary.COLUMN_HEADER_CALORIES_KCAL );
      assertLabelTextIs( 3, row, UiRecipeSummary.COLUMN_HEADER_CARBS_G_ML );
      assertLabelTextIs( 4, row, UiRecipeSummary.COLUMN_HEADER_FATS_G_ML );
      assertLabelTextIs( 5, row, UiRecipeSummary.COLUMN_HEADER_PROTEIN_G_ML );
      
      row++;
      
      assertFoodDisplayedInRow( row++, new FoodPortion( subject, 100.0 ), "" );
      
      FoodPortion pizzaBase = subject.portions().get( 0 );
      assertFoodDisplayedInRow( row++, pizzaBase, UiRecipeSummary.INDENTATION );
      for ( FoodPortion basePortion : ( ( Meal )pizzaBase.food().get() ).portions() ) {
         assertFoodDisplayedInRow( row++, basePortion, DOUBLE_INDENTATION );   
      }
      
      FoodPortion tomatoSauce = subject.portions().get( 1 );
      assertFoodDisplayedInRow( row++, tomatoSauce, UiRecipeSummary.INDENTATION );
      for ( FoodPortion basePortion : ( ( Meal )tomatoSauce.food().get() ).portions() ) {
         assertFoodDisplayedInRow( row++, basePortion, DOUBLE_INDENTATION );   
      }
      
      FoodPortion cheeseSauce = subject.portions().get( 3 );
      assertFoodDisplayedInRow( row++, cheeseSauce, UiRecipeSummary.INDENTATION );
      for ( FoodPortion basePortion : ( ( Meal )cheeseSauce.food().get() ).portions() ) {
         assertFoodDisplayedInRow( row++, basePortion, DOUBLE_INDENTATION );   
      }
      
      Meal individualIngredients = new Meal( UiRecipeSummary.INDIVIDUAL_INGREDIENTS );
      individualIngredients.portions().add( subject.portions().get( 2 ) );
      assertFoodDisplayedInRow( row++, new FoodPortion( individualIngredients, 100.0 ), UiRecipeSummary.INDENTATION );
      for ( FoodPortion basePortion : individualIngredients.portions() ) {
         assertFoodDisplayedInRow( row++, basePortion, DOUBLE_INDENTATION  );   
      }
   }//End Method
   
   private void assertFoodDisplayedInRow( int row, FoodPortion food, String indentation ) {
      assertLabelTextIs( 0, row, indentation + food.food().get().properties().nameProperty().get() );
      assertLabelTextIs( 1, row, food.portion().get() );
      assertLabelTextIs( 2, row, food.nutrition().of( NutritionalUnit.Calories ).get() );
      assertLabelTextIs( 3, row, food.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get() );
      assertLabelTextIs( 4, row, food.properties().nutrition().of( NutritionalUnit.Fat ).get() );
      assertLabelTextIs( 5, row, food.properties().nutrition().of( NutritionalUnit.Protein ).get() );
   }//End Method
   
   private void assertLabelTextIs( int column, int row, double value ) {
      assertLabelTextIs( column, row, new Conversions().format( value ) );
   }//End Method
   
   private void assertLabelTextIs( int column, int row, String text ) {
      int index = ( row * 6 ) + column;
      Label label = ( Label ) systemUnderTest.getChildren().get( index );
      assertThat( label.getText(), is( text ) );
   }//End Method
   
}//End Class
