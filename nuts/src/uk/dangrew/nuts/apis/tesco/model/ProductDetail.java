package uk.dangrew.nuts.apis.tesco.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ProductDetail {
   
   private final ObjectProperty< String > gtinProperty;
   private final ObjectProperty< String > tpnbProperty;
   private final ObservableList< String > tpncs;
   private final ObjectProperty< String > descriptionProperty;
   private final ObjectProperty< String > brandProperty;
   private final QuantityContents quantityContents;
   private final ProductCharacteristics characteristics;
   private final ObservableMap< String, GuidelineDailyAmountReference > gdas;
   private final CalculatedNutrition nutrition;
   private final ObservableList< String > storageInstructions;
   private final ObjectProperty< String > marketingTextProperty;
   private final PackageDimensions packageDimensions;
   
   public ProductDetail() {
      this.gtinProperty = new SimpleObjectProperty<>();
      this.tpnbProperty = new SimpleObjectProperty<>();
      this.tpncs = FXCollections.observableArrayList();
      this.descriptionProperty = new SimpleObjectProperty<>();
      this.brandProperty = new SimpleObjectProperty<>();
      this.quantityContents = new QuantityContents();
      this.characteristics = new ProductCharacteristics();
      this.gdas = FXCollections.observableHashMap();
      this.nutrition = new CalculatedNutrition();
      this.storageInstructions = FXCollections.observableArrayList();
      this.marketingTextProperty = new SimpleObjectProperty<>();
      this.packageDimensions = new PackageDimensions();
   }//End Constructor

   public ObjectProperty< String > gtin() {
      return gtinProperty;
   }//End Method

   public ObjectProperty< String > tpnb() {
      return tpnbProperty;
   }//End Method

   public ObservableList< String > tpncs() {
      return tpncs;
   }//End Method

   public ObjectProperty< String > description() {
      return descriptionProperty;
   }//End Method

   public ObjectProperty< String > brand() {
      return brandProperty;
   }//End Method

   public QuantityContents quantityContents() {
      return quantityContents;
   }//End Method

   public ProductCharacteristics characteristics() {
      return characteristics;
   }//End Method

   public ObservableMap< String, GuidelineDailyAmountReference > gdas() {
      return gdas;
   }//End Method

   public CalculatedNutrition nutrition() {
      return nutrition;
   }//End Method

   public ObservableList< String > storageInstructions() {
      return storageInstructions;
   }//End Method

   public ObjectProperty< String > marketingTextProperty() {
      return marketingTextProperty;
   }//End Method

   public PackageDimensions packageDimensions() {
      return packageDimensions;
   }//End Method
   
}//End Class
