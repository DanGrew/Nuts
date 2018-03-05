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

   public CalculatedNutrientValue salts() {
      return salts;
   }//End Method
   
}//End Class
