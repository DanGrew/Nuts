package uk.dangrew.nuts.apis.tesco.item;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PackageDimensions {

   private final ObjectProperty< Double > number;
   private final ObjectProperty< Double > height;
   private final ObjectProperty< Double > width;
   private final ObjectProperty< Double > depth;
   private final ObjectProperty< String > dimensionUom;
   private final ObjectProperty< Double > weight;
   private final ObjectProperty< String > weightUom;
   private final ObjectProperty< Double > volume;
   private final ObjectProperty< String > volumeUom;
   
   public PackageDimensions() {
      this.number = new SimpleObjectProperty<>();
      this.height = new SimpleObjectProperty<>();
      this.width = new SimpleObjectProperty<>();
      this.depth = new SimpleObjectProperty<>();
      this.dimensionUom = new SimpleObjectProperty<>();
      this.weight = new SimpleObjectProperty<>();
      this.weightUom = new SimpleObjectProperty<>();
      this.volume = new SimpleObjectProperty<>();
      this.volumeUom = new SimpleObjectProperty<>();      
   }//End Constructor

   public ObjectProperty< Double > number() {
      return number;
   }//End Method

   public ObjectProperty< Double > height() {
      return height;
   }//End Method

   public ObjectProperty< Double > width() {
      return width;
   }//End Method

   public ObjectProperty< Double > depth() {
      return depth;
   }//End Method

   public ObjectProperty< String > dimensionUom() {
      return dimensionUom;
   }//End Method

   public ObjectProperty< Double > weight() {
      return weight;
   }//End Method

   public ObjectProperty< String > weightUom() {
      return weightUom;
   }//End Method

   public ObjectProperty< Double > volume() {
      return volume;
   }//End Method

   public ObjectProperty< String > volumeUom() {
      return volumeUom;
   }//End Method
   
}//End Class
