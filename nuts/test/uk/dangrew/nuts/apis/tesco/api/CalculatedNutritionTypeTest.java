package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.item.CalculatedNutrition;

public class CalculatedNutritionTypeTest {

   private CalculatedNutrition nutrition;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      nutrition = new CalculatedNutrition();
   }//End Method

   @Test public void shouldNotParseValues(){
      for ( CalculatedNutritionType type : CalculatedNutritionType.values() ) {
         assertThat( type.matches( "Energy" ), is( false ) );
         assertThat( type.matches( "anything" ), is( false ) );
      }
   }//End Method
   
   @Test public void shouldParseEnergyInKcalName() {
      assertThat( CalculatedNutritionType.EnergyInKcal.matches( "Energy (kcal)" ), is( true ) );
      assertThat( CalculatedNutritionType.EnergyInKcal.matches( "Energy kcal" ), is( true ) );
      assertThat( CalculatedNutritionType.EnergyInKcal.matches( "Energy Kcal" ), is( true ) );
      assertThat( CalculatedNutritionType.EnergyInKcal.matches( "Energy in kcal" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseEnergyInKjName() {
      assertThat( CalculatedNutritionType.EnergyInKj.matches( "Energy (kj)" ), is( true ) );
      assertThat( CalculatedNutritionType.EnergyInKj.matches( "Energy kj" ), is( true ) );
      assertThat( CalculatedNutritionType.EnergyInKj.matches( "Energy kJ" ), is( true ) );
      assertThat( CalculatedNutritionType.EnergyInKj.matches( "Energy Kj" ), is( true ) );
      assertThat( CalculatedNutritionType.EnergyInKj.matches( "Energy in kj" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseFatName() {
      assertThat( CalculatedNutritionType.Fats.matches( "Fat" ), is( true ) );
      assertThat( CalculatedNutritionType.Fats.matches( "Fats" ), is( true ) );
      assertThat( CalculatedNutritionType.Fats.matches( "fat" ), is( true ) );
      assertThat( CalculatedNutritionType.Fats.matches( " Fat " ), is( true ) );
      assertThat( CalculatedNutritionType.Fats.matches( "fat (g)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseSaturatesName() {
      assertThat( CalculatedNutritionType.Saturates.matches( "Saturates" ), is( true ) );
      assertThat( CalculatedNutritionType.Saturates.matches( "Saturate" ), is( true ) );
      assertThat( CalculatedNutritionType.Saturates.matches( "saturate" ), is( true ) );
      assertThat( CalculatedNutritionType.Saturates.matches( " of which saturates " ), is( true ) );
      assertThat( CalculatedNutritionType.Saturates.matches( "Saturates (g)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseCarbohydratesName() {
      assertThat( CalculatedNutritionType.Carbohydrate.matches( "Carbohydrates" ), is( true ) );
      assertThat( CalculatedNutritionType.Carbohydrate.matches( "Carbohydrate" ), is( true ) );
      assertThat( CalculatedNutritionType.Carbohydrate.matches( "carbohydrates" ), is( true ) );
      assertThat( CalculatedNutritionType.Carbohydrate.matches( " Carbohydrates " ), is( true ) );
      assertThat( CalculatedNutritionType.Carbohydrate.matches( "Carbohydrates (g)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseSugarsName() {
      assertThat( CalculatedNutritionType.Sugars.matches( "Sugars" ), is( true ) );
      assertThat( CalculatedNutritionType.Sugars.matches( "Sugar" ), is( true ) );
      assertThat( CalculatedNutritionType.Sugars.matches( "sugar" ), is( true ) );
      assertThat( CalculatedNutritionType.Sugars.matches( " Sugars " ), is( true ) );
      assertThat( CalculatedNutritionType.Sugars.matches( "Sugars (g)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseFibreName() {
      assertThat( CalculatedNutritionType.Fibre.matches( "Fibre" ), is( true ) );
      assertThat( CalculatedNutritionType.Fibre.matches( "Fibres" ), is( true ) );
      assertThat( CalculatedNutritionType.Fibre.matches( "fibre" ), is( true ) );
      assertThat( CalculatedNutritionType.Fibre.matches( " Fibre " ), is( true ) );
      assertThat( CalculatedNutritionType.Fibre.matches( "Fibre (g)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseProteinName() {
      assertThat( CalculatedNutritionType.Protein.matches( "Protein" ), is( true ) );
      assertThat( CalculatedNutritionType.Protein.matches( "Proteins" ), is( true ) );
      assertThat( CalculatedNutritionType.Protein.matches( "protein" ), is( true ) );
      assertThat( CalculatedNutritionType.Protein.matches( " Protein " ), is( true ) );
      assertThat( CalculatedNutritionType.Protein.matches( "Protein (g)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseSaltName() {
      assertThat( CalculatedNutritionType.Salt.matches( "Salt" ), is( true ) );
      assertThat( CalculatedNutritionType.Salt.matches( "Salts" ), is( true ) );
      assertThat( CalculatedNutritionType.Salt.matches( "salt" ), is( true ) );
      assertThat( CalculatedNutritionType.Salt.matches( " Salt " ), is( true ) );
      assertThat( CalculatedNutritionType.Salt.matches( "Salt (g)" ), is( true ) );
   }//End Method
   
   @Test public void shouldRedirectToNutrientValue(){
      assertThat( CalculatedNutritionType.EnergyInKcal.redirect( nutrition ), is( nutrition.energyInKcal() ) );
      assertThat( CalculatedNutritionType.EnergyInKj.redirect( nutrition ), is( nutrition.energyInKcal() ) );
      assertThat( CalculatedNutritionType.EnergyInKcal.redirect( nutrition ), is( nutrition.energyInKcal() ) );
      assertThat( CalculatedNutritionType.EnergyInKcal.redirect( nutrition ), is( nutrition.energyInKcal() ) );
      assertThat( CalculatedNutritionType.EnergyInKcal.redirect( nutrition ), is( nutrition.energyInKcal() ) );
      assertThat( CalculatedNutritionType.EnergyInKcal.redirect( nutrition ), is( nutrition.energyInKcal() ) );
      assertThat( CalculatedNutritionType.EnergyInKcal.redirect( nutrition ), is( nutrition.energyInKcal() ) );
      assertThat( CalculatedNutritionType.EnergyInKcal.redirect( nutrition ), is( nutrition.energyInKcal() ) );
      assertThat( CalculatedNutritionType.EnergyInKcal.redirect( nutrition ), is( nutrition.energyInKcal() ) );
   }//End Method

}//End Class
