package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import uk.dangrew.nuts.apis.tesco.item.CalculatedNutrition;

public class NutritionAsserter {

   private String per100Header;
   private String perServingHeader;
   private String energyInKcalValuePer100;
   private String energyInKcalValuePerServing;
   private String energyInKjValuePer100;
   private String energyInKjValuePerServing;
   private String fatValuePer100;
   private String fatValuePerServing;
   private String saturatesValuePer100;
   private String saturatesValuePerServing;
   private String carbohydratesValuePer100;
   private String carbohydratesValuePerServing;
   private String sugarsValuePer100;
   private String sugarsValuePerServing;
   private String fibreValuePer100;
   private String fibreValuePerServing;
   private String proteinValuePer100;
   private String proteinValuePerServing;
   private String saltValuePer100;
   private String saltValuePerServing;
   private String calciumValuePer100;
   private String calciumValuePerServing;
   private String thiaminValuePer100;
   private String thiaminValuePerServing;
   private String riboflavinValuePer100;
   private String riboflavinValuePerServing;
   private String niacinValuePer100;
   private String niacinValuePerServing;
   private String folicAcidValuePer100;
   private String folicAcidValuePerServing;
   private String ironValuePer100;
   private String ironValuePerServing;
   private String omega3ValuePer100;
   private String omega3ValuePerServing;
      
   public NutritionAsserter per100Header( String per100Header ) {
      this.per100Header = per100Header;
      return this;
   }//End Method
   
   public NutritionAsserter perServingHeader( String perServingHeader ) {
      this.perServingHeader = perServingHeader;
      return this;
   }//End Method
   
   public NutritionAsserter energyInKcalValuePer100( String energyInKcalValuePer100 ) {
      this.energyInKcalValuePer100 = energyInKcalValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter energyInKcalValuePerServing( String energyInKcalValuePerServing ) {
      this.energyInKcalValuePerServing = energyInKcalValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter energyInKjValuePer100( String energyInKjValuePer100 ) {
      this.energyInKjValuePer100 = energyInKjValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter energyInKjValuePerServing( String energyInKjValuePerServing ) {
      this.energyInKjValuePerServing = energyInKjValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter fatValuePer100( String fatValuePer100 ) {
      this.fatValuePer100 = fatValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter fatValuePerServing( String fatValuePerServing ) {
      this.fatValuePerServing = fatValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter saturatesValuePer100( String saturatesValuePer100 ) {
      this.saturatesValuePer100 = saturatesValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter saturatesValuePerServing( String saturatesValuePerServing ) {
      this.saturatesValuePerServing = saturatesValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter carbohydratesValuePer100( String carbohydratesValuePer100 ) {
      this.carbohydratesValuePer100 = carbohydratesValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter carbohydratesValuePerServing( String carbohydratesValuePerServing ) {
      this.carbohydratesValuePerServing = carbohydratesValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter sugarsValuePer100( String sugarsValuePer100 ) {
      this.sugarsValuePer100 = sugarsValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter sugarsValuePerServing( String sugarsValuePerServing ) {
      this.sugarsValuePerServing = sugarsValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter fibreValuePer100( String fibreValuePer100 ) {
      this.fibreValuePer100 = fibreValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter fibreValuePerServing( String fibreValuePerServing ) {
      this.fibreValuePerServing = fibreValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter proteinValuePer100( String proteinValuePer100 ) {
      this.proteinValuePer100 = proteinValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter proteinValuePerServing( String proteinValuePerServing ) {
      this.proteinValuePerServing = proteinValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter saltValuePer100( String saltValuePer100 ) {
      this.saltValuePer100 = saltValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter saltValuePerServing( String saltValuePerServing ) {
      this.saltValuePerServing = saltValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter calciumValuePer100( String calciumValuePer100 ) {
      this.calciumValuePer100 = calciumValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter calciumValuePerServing( String calciumValuePerServing ) {
      this.calciumValuePerServing = calciumValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter thiaminValuePer100( String thiaminValuePer100 ) {
      this.thiaminValuePer100 = thiaminValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter thiaminValuePerServing( String thiaminValuePerServing ) {
      this.thiaminValuePerServing = thiaminValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter riboflavinValuePer100( String riboflavinValuePer100 ) {
      this.riboflavinValuePer100 = riboflavinValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter riboflavinValuePerServing( String riboflavinValuePerServing ) {
      this.riboflavinValuePerServing = riboflavinValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter niacinValuePer100( String niacinValuePer100 ) {
      this.niacinValuePer100 = niacinValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter niacinValuePerServing( String niacinValuePerServing ) {
      this.niacinValuePerServing = niacinValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter folicAcidValuePer100( String folicAcidValuePer100 ) {
      this.folicAcidValuePer100 = folicAcidValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter folicAcidValuePerServing( String folicAcidValuePerServing ) {
      this.folicAcidValuePerServing = folicAcidValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter ironValuePer100( String ironValuePer100 ) {
      this.ironValuePer100 = ironValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter ironValuePerServing( String ironValuePerServing ) {
      this.ironValuePerServing = ironValuePerServing;
      return this;
   }//End Method
   
   public NutritionAsserter omega3ValuePer100( String omega3ValuePer100 ) {
      this.omega3ValuePer100 = omega3ValuePer100;
      return this;
   }//End Method
   
   public NutritionAsserter omega3ValuePerServing( String omega3ValuePerServing ) {
      this.omega3ValuePerServing = omega3ValuePerServing;
      return this;
   }//End Method

   public void assertThatValuesAreCorrect( CalculatedNutrition nutrition ) {
      assertThat( nutrition.per100Header().get(), is( per100Header ) );
      assertThat( nutrition.perServingHeader().get(), is( perServingHeader ) );
      
      assertThat( nutrition.energyInKcal().valuePer100().get(), is( energyInKcalValuePer100 ) );
      assertThat( nutrition.energyInKcal().valuePerServing().get(), is( energyInKcalValuePerServing ) );
      
      assertThat( nutrition.energyInKj().valuePer100().get(), is( energyInKjValuePer100 ) );
      assertThat( nutrition.energyInKj().valuePerServing().get(), is( energyInKjValuePerServing ) );
      
      assertThat( nutrition.fat().valuePer100().get(), is( fatValuePer100 ) );
      assertThat( nutrition.fat().valuePerServing().get(), is( fatValuePerServing ) );
      
      assertThat( nutrition.saturates().valuePer100().get(), is( saturatesValuePer100 ) );
      assertThat( nutrition.saturates().valuePerServing().get(), is( saturatesValuePerServing ) );
      
      assertThat( nutrition.carbohydrates().valuePer100().get(), is( carbohydratesValuePer100 ) );
      assertThat( nutrition.carbohydrates().valuePerServing().get(), is( carbohydratesValuePerServing ) );
      
      assertThat( nutrition.sugars().valuePer100().get(), is( sugarsValuePer100 ) );
      assertThat( nutrition.sugars().valuePerServing().get(), is( sugarsValuePerServing ) );
      
      assertThat( nutrition.fibre().valuePer100().get(), is( fibreValuePer100 ) );
      assertThat( nutrition.fibre().valuePerServing().get(), is( fibreValuePerServing ) );
      
      assertThat( nutrition.protein().valuePer100().get(), is( proteinValuePer100 ) );
      assertThat( nutrition.protein().valuePerServing().get(), is( proteinValuePerServing ) );
      
      assertThat( nutrition.salt().valuePer100().get(), is( saltValuePer100 ) );
      assertThat( nutrition.salt().valuePerServing().get(), is( saltValuePerServing ) );
      
      assertThat( nutrition.calcium().valuePer100().get(), is( calciumValuePer100 ) );
      assertThat( nutrition.calcium().valuePerServing().get(), is( calciumValuePerServing ) );
      
      assertThat( nutrition.thiamin().valuePer100().get(), is( thiaminValuePer100 ) );
      assertThat( nutrition.thiamin().valuePerServing().get(), is( thiaminValuePerServing ) );
      
      assertThat( nutrition.riboflavin().valuePer100().get(), is( riboflavinValuePer100 ) );
      assertThat( nutrition.riboflavin().valuePerServing().get(), is( riboflavinValuePerServing ) );
      
      assertThat( nutrition.niacin().valuePer100().get(), is( niacinValuePer100 ) );
      assertThat( nutrition.niacin().valuePerServing().get(), is( niacinValuePerServing ) );
      
      assertThat( nutrition.folicAcid().valuePer100().get(), is( folicAcidValuePer100 ) );
      assertThat( nutrition.folicAcid().valuePerServing().get(), is( folicAcidValuePerServing ) );
      
      assertThat( nutrition.iron().valuePer100().get(), is( ironValuePer100 ) );
      assertThat( nutrition.iron().valuePerServing().get(), is( ironValuePerServing ) );
      
      assertThat( nutrition.omega3().valuePer100().get(), is( omega3ValuePer100 ) );
      assertThat( nutrition.omega3().valuePerServing().get(), is( omega3ValuePerServing ) );
   }//End Method
}//End Class
