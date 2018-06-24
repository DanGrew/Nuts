package uk.dangrew.nuts.apis.tesco.graphics.selection;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.model.api.CalculatedNutrition;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class TescoFoodItemGeneratorTest {

   private TescoFoodDescription description;
   private TescoFoodItemGenerator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      description = new TescoFoodDescription( "Food" );
      systemUnderTest = new TescoFoodItemGenerator();
   }//End Method

   @Test public void shouldConstructPer100ItemIfDataPresent() {
      String groceryName = "Some elaborate name";
      String per100Header = "per (100g) of product";
      
      double kcal = 101.3;
      double carbs = 20.7;
      double fat = 1.3;
      double protein = 35.9;
      double fiber = 0.56;
      
      description.groceryProperties().name().set( groceryName );
      CalculatedNutrition nutrition = description.productDetail().nutrition();
      nutrition.per100Header().set( per100Header );
      nutrition.energyInKcal().valuePer100().set( Double.toString( kcal ) );
      nutrition.carbohydrates().valuePer100().set( Double.toString( carbs ) );
      nutrition.fat().valuePer100().set( Double.toString( fat ) );
      nutrition.protein().valuePer100().set( Double.toString( protein ) );
      nutrition.fibre().valuePer100().set( Double.toString( fiber ) );
      
      List< Food > items = systemUnderTest.generateFoodItemsFor( description );
      assertThat( items, hasSize( 1 ) );
      
      FoodItem first = ( FoodItem )items.get( 0 );
      assertThat( first.properties().nameProperty().get(), is( 
               groceryName + " (100g)" 
      ) );
      assertThat( first.nutrition().of( NutritionalUnit.Calories ).get(), is( kcal ) );
      assertThat( first.properties().fiber().get(), is( fiber ) );
      assertThat( first.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( carbs ) );
      assertThat( first.properties().nutrition().of( NutritionalUnit.Fat ).get(), is( fat ) );
      assertThat( first.properties().protein().get(), is( protein ) );
   }//End Method
   
   @Test public void shouldConstructPerServingItemIfDataPresent() {
      String groceryName = "Some elaborate name";
      String perServingHeader = "per (15g) serving";
      
      double kcal = 101.3;
      double carbs = 20.7;
      double fat = 1.3;
      double protein = 35.9;
      double fiber = 0.56;
      
      description.groceryProperties().name().set( groceryName );
      CalculatedNutrition nutrition = description.productDetail().nutrition();
      nutrition.perServingHeader().set( perServingHeader );
      nutrition.energyInKcal().valuePerServing().set( Double.toString( kcal ) );
      nutrition.carbohydrates().valuePerServing().set( Double.toString( carbs ) );
      nutrition.fat().valuePerServing().set( Double.toString( fat ) );
      nutrition.protein().valuePerServing().set( Double.toString( protein ) );
      nutrition.fibre().valuePerServing().set( Double.toString( fiber ) );
      
      List< Food > items = systemUnderTest.generateFoodItemsFor( description );
      assertThat( items, hasSize( 1 ) );
      
      FoodItem first = ( FoodItem )items.get( 0 );
      assertThat( first.properties().nameProperty().get(), is( 
               groceryName + " (15g)" 
      ) );
      assertThat( first.nutrition().of( NutritionalUnit.Calories ).get(), is( kcal ) );
      assertThat( first.properties().fiber().get(), is( fiber ) );
      assertThat( first.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( carbs ) );
      assertThat( first.properties().nutrition().of( NutritionalUnit.Fat ).get(), is( fat ) );
      assertThat( first.properties().protein().get(), is( protein ) );
   }//End Method
   
   @Test public void shouldNotConstructIfNameNotPresent() {
      assertThat( systemUnderTest.generateFoodItemsFor( description ), is( empty() ) );
      
      description.groceryProperties().name().set( "anything" );
      List< Food > items = systemUnderTest.generateFoodItemsFor( description );
      assertThat( items, hasSize( 0 ) );
   }//End Method
   
   @Test public void shouldConstructPer100ItemEvenIfDataInvalid() {
      String groceryName = "Some elaborate name";
      description.groceryProperties().name().set( groceryName );
      
      CalculatedNutrition nutrition = description.productDetail().nutrition();
      nutrition.per100Header().set( "unparsable" );
      nutrition.energyInKcal().valuePer100().set( "unparsable" );
      nutrition.carbohydrates().valuePer100().set( "unparsable" );
      nutrition.fat().valuePer100().set( "unparsable" );
      nutrition.protein().valuePer100().set( "unparsable" );
      nutrition.fibre().valuePer100().set( "unparsable" );
      
      List< Food > items = systemUnderTest.generateFoodItemsFor( description );
      
      FoodItem first = ( FoodItem )items.get( 0 );
      assertThat( first.properties().nameProperty().get(), is( groceryName + " (unparsable)" ) );
      assertThat( first.nutrition().of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
      assertThat( first.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 0.0 ) );
      assertThat( first.properties().nutrition().of( NutritionalUnit.Fat ).get(), is( 0.0 ) );
      assertThat( first.properties().protein().get(), is( 0.0 ) );
      assertThat( first.properties().fiber().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldConstructPerServingItemEvenIfDataInvalid() {
      String groceryName = "Some elaborate name";
      description.groceryProperties().name().set( groceryName );
      
      CalculatedNutrition nutrition = description.productDetail().nutrition();
      nutrition.perServingHeader().set( "unparsable" );
      nutrition.energyInKcal().valuePerServing().set( "unparsable" );
      nutrition.carbohydrates().valuePerServing().set( "unparsable" );
      nutrition.fat().valuePerServing().set( "unparsable" );
      nutrition.protein().valuePerServing().set( "unparsable" );
      nutrition.fibre().valuePerServing().set( "unparsable" );
      
      List< Food > items = systemUnderTest.generateFoodItemsFor( description );
      
      FoodItem first = ( FoodItem )items.get( 0 );
      assertThat( first.properties().nameProperty().get(), is( groceryName + " (unparsable)" ) );
      assertThat( first.nutrition().of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
      assertThat( first.properties().fiber().get(), is( 0.0 ) );
      assertThat( first.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 0.0 ) );
      assertThat( first.properties().nutrition().of( NutritionalUnit.Fat ).get(), is( 0.0 ) );
      assertThat( first.properties().protein().get(), is( 0.0 ) );
   }//End Method
   
}//End Class
