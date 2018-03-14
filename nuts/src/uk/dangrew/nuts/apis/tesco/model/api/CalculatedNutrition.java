package uk.dangrew.nuts.apis.tesco.model.api;

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
      this.energyInKj = new CalculatedNutrientValue();
      this.energyInKcal = new CalculatedNutrientValue();
      this.fat = new CalculatedNutrientValue();
      this.saturates = new CalculatedNutrientValue();
      this.carbohydrates = new CalculatedNutrientValue();
      this.sugars = new CalculatedNutrientValue();
      this.fibre = new CalculatedNutrientValue();
      this.protein = new CalculatedNutrientValue();
      this.salts = new CalculatedNutrientValue();
      this.calcium = new CalculatedNutrientValue();
      this.thiamin = new CalculatedNutrientValue();
      this.riboflavin = new CalculatedNutrientValue();
      this.niacin = new CalculatedNutrientValue();
      this.folicAcid = new CalculatedNutrientValue();
      this.iron = new CalculatedNutrientValue();
      this.omega3 = new CalculatedNutrientValue();
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

   public boolean isPopulated() {
      boolean hasPer100Header = per100HeaderProperty.get() != null;
      boolean hasPerservingHeader = perServingHeaderProperty.get() != null;
      return hasPer100Header || hasPerservingHeader;
   }//End Method
   
}//End Class
