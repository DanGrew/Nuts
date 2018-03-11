package uk.dangrew.nuts.apis.tesco.item;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CalculatedNutrition {

   private final ObjectProperty< String > per100HeaderProperty;
   private final ObjectProperty< String > perServingHeaderProperty;
   private final CalculatedNutrientValue energyInKj;
   private final CalculatedNutrientValue energyInKcal;
   private final CalculatedNutrientValue fat;
   private final CalculatedNutrientValue saturates;
   private final CalculatedNutrientValue carbohydrates;
   private final CalculatedNutrientValue sugars;
   private final CalculatedNutrientValue fibre;
   private final CalculatedNutrientValue protein;
   private final CalculatedNutrientValue salts;
   private final CalculatedNutrientValue calcium;
   private final CalculatedNutrientValue thiamin;
   private final CalculatedNutrientValue riboflavin;
   private final CalculatedNutrientValue niacin;
   private final CalculatedNutrientValue folicAcid;
   private final CalculatedNutrientValue iron;
   private final CalculatedNutrientValue omega3;
   
   public CalculatedNutrition() {
      this.per100HeaderProperty = new SimpleObjectProperty<>();
      this.perServingHeaderProperty = new SimpleObjectProperty<>();
      this.energyInKj = new CalculatedNutrientValue( "Energy (kJ)" );
      this.energyInKcal = new CalculatedNutrientValue( "Energy (kcal)" );
      this.fat = new CalculatedNutrientValue( "Fat (g)" );
      this.saturates = new CalculatedNutrientValue( "Saturates (g)" );
      this.carbohydrates = new CalculatedNutrientValue( "Carbohydrate (g)" );
      this.sugars = new CalculatedNutrientValue( "Sugars (g)" );
      this.fibre = new CalculatedNutrientValue( "Fibre (g)" );
      this.protein = new CalculatedNutrientValue( "Protein (g)" );
      this.salts = new CalculatedNutrientValue( "Salt (g)" );
      this.calcium = new CalculatedNutrientValue( "Calcium (mg)" );
      this.thiamin = new CalculatedNutrientValue( "Thiamin (mg)" );
      this.riboflavin = new CalculatedNutrientValue( "Robolavin (mg)" );
      this.niacin = new CalculatedNutrientValue( "Niacin (mg)" );
      this.folicAcid = new CalculatedNutrientValue( "Folic Acid (mg)" );
      this.iron = new CalculatedNutrientValue( "Iron (mg)" );
      this.omega3 = new CalculatedNutrientValue( "Omega3 (g)" );
   }//End Constructor

   public ObjectProperty< String > per100Header() {
      return per100HeaderProperty;
   }//End Method

   public ObjectProperty< String > perServingHeader() {
      return perServingHeaderProperty;
   }//End Method

   public CalculatedNutrientValue energyInKj() {
      return energyInKj;
   }//End Method

   public CalculatedNutrientValue energyInKcal() {
      return energyInKcal;
   }//End Method

   public CalculatedNutrientValue fat() {
      return fat;
   }//End Method

   public CalculatedNutrientValue saturates() {
      return saturates;
   }//End Method

   public CalculatedNutrientValue carbohydrates() {
      return carbohydrates;
   }//End Method

   public CalculatedNutrientValue sugars() {
      return sugars;
   }//End Method

   public CalculatedNutrientValue fibre() {
      return fibre;
   }//End Method

   public CalculatedNutrientValue protein() {
      return protein;
   }//End Method

   public CalculatedNutrientValue salt() {
      return salts;
   }//End Method
   
   public CalculatedNutrientValue calcium() {
      return calcium;
   }//End Method
   
   public CalculatedNutrientValue thiamin() {
      return thiamin;
   }//End Method
   
   public CalculatedNutrientValue riboflavin() {
      return riboflavin;
   }//End Method
   
   public CalculatedNutrientValue niacin() {
      return niacin;
   }//End Method
   
   public CalculatedNutrientValue folicAcid() {
      return folicAcid;
   }//End Method
   
   public CalculatedNutrientValue iron() {
      return iron;
   }//End Method
   
   public CalculatedNutrientValue omega3() {
      return omega3;
   }//End Method
   
}//End Class
