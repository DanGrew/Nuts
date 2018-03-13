package uk.dangrew.nuts.apis.tesco.api.parsing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.api.parsing.CalculatedNutritionType;
import uk.dangrew.nuts.apis.tesco.model.api.CalculatedNutrition;

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
         assertThat( type.matches( null ), is( false ) );
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
   
   @Test public void shouldParseCalciumName() {
      assertThat( CalculatedNutritionType.Calcium.matches( "Calcium" ), is( true ) );
      assertThat( CalculatedNutritionType.Calcium.matches( "Calciums" ), is( true ) );
      assertThat( CalculatedNutritionType.Calcium.matches( "calcium" ), is( true ) );
      assertThat( CalculatedNutritionType.Calcium.matches( " Calcium " ), is( true ) );
      assertThat( CalculatedNutritionType.Calcium.matches( "Calcium (mg)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseThiaminName() {
      assertThat( CalculatedNutritionType.Thiamin.matches( "Thiamin" ), is( true ) );
      assertThat( CalculatedNutritionType.Thiamin.matches( "Thiamins" ), is( true ) );
      assertThat( CalculatedNutritionType.Thiamin.matches( "thiamin" ), is( true ) );
      assertThat( CalculatedNutritionType.Thiamin.matches( " Thiamin " ), is( true ) );
      assertThat( CalculatedNutritionType.Thiamin.matches( "Thiamin (mg)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseRiboflavinName() {
      assertThat( CalculatedNutritionType.Riboflavin.matches( "Riboflavin" ), is( true ) );
      assertThat( CalculatedNutritionType.Riboflavin.matches( "Riboflavins" ), is( true ) );
      assertThat( CalculatedNutritionType.Riboflavin.matches( "riboflavin" ), is( true ) );
      assertThat( CalculatedNutritionType.Riboflavin.matches( " Riboflavin " ), is( true ) );
      assertThat( CalculatedNutritionType.Riboflavin.matches( "Riboflavin (mg)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseNiacinName() {
      assertThat( CalculatedNutritionType.Niacin.matches( "Niacin" ), is( true ) );
      assertThat( CalculatedNutritionType.Niacin.matches( "Niacins" ), is( true ) );
      assertThat( CalculatedNutritionType.Niacin.matches( "niacin" ), is( true ) );
      assertThat( CalculatedNutritionType.Niacin.matches( " Niacin " ), is( true ) );
      assertThat( CalculatedNutritionType.Niacin.matches( "Niacin (mg)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseFolicAcidName() {
      assertThat( CalculatedNutritionType.FolicAcid.matches( "Folic Acid" ), is( true ) );
      assertThat( CalculatedNutritionType.FolicAcid.matches( "Folic Acids" ), is( true ) );
      assertThat( CalculatedNutritionType.FolicAcid.matches( "folic Acid" ), is( true ) );
      assertThat( CalculatedNutritionType.FolicAcid.matches( " Folic Acid " ), is( true ) );
      assertThat( CalculatedNutritionType.FolicAcid.matches( "Folic Acid (mg)" ), is( true ) );
      assertThat( CalculatedNutritionType.FolicAcid.matches( "FolicAcid" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseIronName() {
      assertThat( CalculatedNutritionType.Iron.matches( "Iron" ), is( true ) );
      assertThat( CalculatedNutritionType.Iron.matches( "Irons" ), is( true ) );
      assertThat( CalculatedNutritionType.Iron.matches( "iron" ), is( true ) );
      assertThat( CalculatedNutritionType.Iron.matches( " Iron " ), is( true ) );
      assertThat( CalculatedNutritionType.Iron.matches( "Iron (mg)" ), is( true ) );
   }//End Method
   
   @Test public void shouldParseOmega3Name() {
      assertThat( CalculatedNutritionType.Omega3.matches( "Omega" ), is( true ) );
      assertThat( CalculatedNutritionType.Omega3.matches( "Omega" ), is( true ) );
      assertThat( CalculatedNutritionType.Omega3.matches( "omega" ), is( true ) );
      assertThat( CalculatedNutritionType.Omega3.matches( " Omega 3 " ), is( true ) );
      assertThat( CalculatedNutritionType.Omega3.matches( "Omega (g)" ), is( true ) );
   }//End Method
   
   @Test public void shouldRedirectToNutrientValue(){
      assertThat( CalculatedNutritionType.EnergyInKcal.redirect( nutrition ), is( nutrition.energyInKcal() ) );
      assertThat( CalculatedNutritionType.EnergyInKj.redirect( nutrition ), is( nutrition.energyInKj() ) );
      assertThat( CalculatedNutritionType.Fats.redirect( nutrition ), is( nutrition.fat() ) );
      assertThat( CalculatedNutritionType.Saturates.redirect( nutrition ), is( nutrition.saturates() ) );
      assertThat( CalculatedNutritionType.Carbohydrate.redirect( nutrition ), is( nutrition.carbohydrates() ) );
      assertThat( CalculatedNutritionType.Sugars.redirect( nutrition ), is( nutrition.sugars() ) );
      assertThat( CalculatedNutritionType.Fibre.redirect( nutrition ), is( nutrition.fibre() ) );
      assertThat( CalculatedNutritionType.Protein.redirect( nutrition ), is( nutrition.protein() ) );
      assertThat( CalculatedNutritionType.Salt.redirect( nutrition ), is( nutrition.salt() ) );
      assertThat( CalculatedNutritionType.Calcium.redirect( nutrition ), is( nutrition.calcium() ) );
      assertThat( CalculatedNutritionType.Thiamin.redirect( nutrition ), is( nutrition.thiamin() ) );
      assertThat( CalculatedNutritionType.Riboflavin.redirect( nutrition ), is( nutrition.riboflavin() ) );
      assertThat( CalculatedNutritionType.Niacin.redirect( nutrition ), is( nutrition.niacin() ) );
      assertThat( CalculatedNutritionType.FolicAcid.redirect( nutrition ), is( nutrition.folicAcid() ) );
      assertThat( CalculatedNutritionType.Omega3.redirect( nutrition ), is( nutrition.omega3() ) );
   }//End Method

}//End Class
