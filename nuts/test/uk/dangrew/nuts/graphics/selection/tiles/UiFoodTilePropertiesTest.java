package uk.dangrew.nuts.graphics.selection.tiles;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.nutrients.MacroNutrient;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class UiFoodTilePropertiesTest {

   private Food food;
   private UiFoodTileProperties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      food = new FoodItem( "Food Item" );
      food.properties().nutrition().setMacroNutrients( 20, 35, 45 );
      food.nutrition().of( NutritionalUnit.Calories ).set( 56.4 );
      
      systemUnderTest = new UiFoodTileProperties( food );
   }//End Method

   @Test public void shouldSyncCarbs() {
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Carbohydrates ).getText(), is( "C: 20.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Carbohydrates ).getText(), is( "C: 20.00%" ) );
      food.properties().nutrition().of( NutritionalUnit.Carbohydrate ).set( 10.0 );
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Carbohydrates ).getText(), is( "C: 10.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Carbohydrates ).getText(), is( "C: 11.11%" ) );
   }//End Method
   
   @Test public void shouldSyncFats() {
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Fats ).getText(), is( "F: 35.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Fats ).getText(), is( "F: 35.00%" ) );
      food.properties().nutrition().of( NutritionalUnit.Fat ).set( 10.0 );
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Fats ).getText(), is( "F: 10.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Fats ).getText(), is( "F: 13.33%" ) );
   }//End Method
   
   @Test public void shouldSyncProtein() {
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Protein ).getText(), is( "P: 45.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Protein ).getText(), is( "P: 45.00%" ) );
      food.properties().protein().set( 10.0 );
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Protein ).getText(), is( "P: 10.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Protein ).getText(), is( "P: 15.38%" ) );
   }//End Method
   
   @Test public void shouldSyncCalories() {
      assertThat( systemUnderTest.calorieValueLabel().getText(), is( "56.40kcal" ) );
      food.nutrition().of( NutritionalUnit.Calories ).set( 10.0 );
      assertThat( systemUnderTest.calorieValueLabel().getText(), is( "10.00kcal" ) );
   }//End Method
   
   @Test public void shouldDetach() {
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Carbohydrates ).getText(), is( "C: 20.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Carbohydrates ).getText(), is( "C: 20.00%" ) );
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Fats ).getText(), is( "F: 35.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Fats ).getText(), is( "F: 35.00%" ) );
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Protein ).getText(), is( "P: 45.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Protein ).getText(), is( "P: 45.00%" ) );
      assertThat( systemUnderTest.calorieValueLabel().getText(), is( "56.40kcal" ) );

      systemUnderTest.detach();
      
      food.properties().nutrition().of( NutritionalUnit.Carbohydrate ).set( 10.0 );
      food.properties().nutrition().of( NutritionalUnit.Fat ).set( 10.0 );
      food.properties().protein().set( 10.0 );
      food.nutrition().of( NutritionalUnit.Calories ).set( 10.0 );
      
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Carbohydrates ).getText(), is( "C: 20.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Carbohydrates ).getText(), is( "C: 20.00%" ) );
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Fats ).getText(), is( "F: 35.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Fats ).getText(), is( "F: 35.00%" ) );
      assertThat( systemUnderTest.valueLabelFor( MacroNutrient.Protein ).getText(), is( "P: 45.00g" ) );
      assertThat( systemUnderTest.ratioLabelFor( MacroNutrient.Protein ).getText(), is( "P: 45.00%" ) );
      assertThat( systemUnderTest.calorieValueLabel().getText(), is( "56.40kcal" ) );
   }//End Method

}//End Class
