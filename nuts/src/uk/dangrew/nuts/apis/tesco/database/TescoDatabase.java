package uk.dangrew.nuts.apis.tesco.database;

public class TescoDatabase {

   private final TescoFoodDescriptionStore descriptions;
   private final TescoFoodItemStore items;
   
   public TescoDatabase() {
      this.descriptions = new TescoFoodDescriptionStore();
      this.items = new TescoFoodItemStore();
   }//End Constructor
   
   public TescoFoodDescriptionStore descriptionStore() {
      return descriptions;
   }//End Method

   public TescoFoodItemStore itemStore() {
      return items;
   }//End Method
   
}//End Class
