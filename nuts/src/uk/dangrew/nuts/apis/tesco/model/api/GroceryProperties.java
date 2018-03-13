package uk.dangrew.nuts.apis.tesco.model.api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GroceryProperties {

   private final ObjectProperty< String > imageProperty;
   private final ObjectProperty< String > superDepartmentProperty;
   private final ObjectProperty< String > tpnbProperty;
   private final ObjectProperty< Double > unitOfSaleProperty;
   private final ObservableList< String > descriptionProperty;
   private final ObjectProperty< String > unitQuantityProperty;
   private final ObjectProperty< String > promotionDescriptionProperty;
   private final ObjectProperty< String > contentsMeasureTypeProperty;
   private final ObjectProperty< String > nameProperty;
   private final ObjectProperty< Double > averageSellingUnitWeightProperty;
   private final ObjectProperty< String > idProperty;
   private final ObjectProperty< Double > contentsQuantityProperty;
   private final ObjectProperty< String > departmentProperty;
   private final ObjectProperty< Double > priceProperty;
   private final ObjectProperty< Double > unitPriceProperty;
   private final ObjectProperty< Boolean > isSpecialOfferProperty;
   
   public GroceryProperties() {
      this.imageProperty = new SimpleObjectProperty<>();
      this.superDepartmentProperty = new SimpleObjectProperty<>();
      this.tpnbProperty = new SimpleObjectProperty<>();
      this.unitOfSaleProperty = new SimpleObjectProperty<>();
      this.descriptionProperty = FXCollections.observableArrayList();
      this.unitQuantityProperty = new SimpleObjectProperty<>();
      this.promotionDescriptionProperty = new SimpleObjectProperty<>();
      this.contentsMeasureTypeProperty = new SimpleObjectProperty<>();
      this.nameProperty = new SimpleObjectProperty<>();
      this.averageSellingUnitWeightProperty = new SimpleObjectProperty<>();
      this.idProperty = new SimpleObjectProperty<>();
      this.contentsQuantityProperty = new SimpleObjectProperty<>();
      this.departmentProperty = new SimpleObjectProperty<>();
      this.priceProperty = new SimpleObjectProperty<>();
      this.unitPriceProperty = new SimpleObjectProperty<>();
      this.isSpecialOfferProperty = new SimpleObjectProperty<>();
   }//End Constructor
   
   public ObjectProperty< String > image() {
      return imageProperty;
   }//End Method

   public ObjectProperty< String > superDepartment() {
      return superDepartmentProperty;
   }//End Method

   public ObjectProperty< String > tpnb() {
      return tpnbProperty;
   }//End Method

   public ObjectProperty< Double > unitOfSale() {
      return unitOfSaleProperty;
   }//End Method

   public ObservableList< String > description() {
      return descriptionProperty;
   }//End Method

   public ObjectProperty< String > unitQuantity() {
      return unitQuantityProperty;
   }//End Method

   public ObjectProperty< String > promotionDescription() {
      return promotionDescriptionProperty;
   }//End Method

   public ObjectProperty< String > contentsMeasureType() {
      return contentsMeasureTypeProperty;
   }//End Method

   public ObjectProperty< String > name() {
      return nameProperty;
   }//End Method

   public ObjectProperty< Double > averageSellingUnitWeight() {
      return averageSellingUnitWeightProperty;
   }//End Method

   public ObjectProperty< String > id() {
      return idProperty;
   }//End Method

   public ObjectProperty< Double > contentsQuantity() {
      return contentsQuantityProperty;
   }//End Method

   public ObjectProperty< String > department() {
      return departmentProperty;
   }//End Method

   public ObjectProperty< Double > price() {
      return priceProperty;
   }//End Method

   public ObjectProperty< Double > unitPrice() {
      return unitPriceProperty;
   }//End Method

   public ObjectProperty< Boolean > isSpecialOffer() {
      return isSpecialOfferProperty;
   }//End Method
   
}//End Class
