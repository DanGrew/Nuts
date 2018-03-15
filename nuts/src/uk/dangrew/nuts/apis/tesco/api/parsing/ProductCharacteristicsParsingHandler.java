package uk.dangrew.nuts.apis.tesco.api.parsing;

import uk.dangrew.nuts.apis.tesco.model.api.ProductCharacteristics;

public class ProductCharacteristicsParsingHandler {
   
   private ProductCharacteristics characteristics;
   private ModelUpdater< ProductCharacteristics > updater;
   
   public void resetCharacteristics( ProductCharacteristics characteristics ){
      this.characteristics = characteristics;
      this.updater = new ModelUpdater<>( characteristics );
      
      this.characteristics.isFood().set( null );
      this.characteristics.isDrink().set( null );
      this.characteristics.healthScore().set( null );
      this.characteristics.isHazardous().set( null );
      this.characteristics.storageType().set( null );
      this.characteristics.isNonLiquidAnalgesic().set( null );
      this.characteristics.containsLoperamide().set( null );
   }//End Method
   
   public void applyCharacteristicsTo( ProductCharacteristics characteristicsToUpdate ) {
      updater.set( ProductCharacteristics::isFood, characteristicsToUpdate );
      updater.set( ProductCharacteristics::isDrink, characteristicsToUpdate );
      updater.set( ProductCharacteristics::healthScore, characteristicsToUpdate );
      updater.set( ProductCharacteristics::isHazardous, characteristicsToUpdate );
      updater.set( ProductCharacteristics::storageType, characteristicsToUpdate );
      updater.set( ProductCharacteristics::isNonLiquidAnalgesic, characteristicsToUpdate );
      updater.set( ProductCharacteristics::containsLoperamide, characteristicsToUpdate );
   }//End Method

   public void startProductCharacteristics() {
      //do nothing
   }//End Method
   
   public void finishProductCharacteristics() {
      //do nothing
   }//End Method
   
   public void setIsFood( Boolean value ) {
      characteristics.isFood().set( value );
   }//End Method
   
   public void setIsDrink( Boolean value ) {
      characteristics.isDrink().set( value );
   }//End Method
   
   public void setHealthScore( Double value ) {
      characteristics.healthScore().set( value );
   }//End Method
   
   public void setIsHazardous( Boolean value ) {
      characteristics.isHazardous().set( value );
   }//End Method
   
   public void setStorageType( String value ) {
      characteristics.storageType().set( value );
   }//End Method
   
   public void setIsNonLiquidAnalgesic( Boolean value ) {
      characteristics.isNonLiquidAnalgesic().set( value );
   }//End Method
   
   public void setContainsLoperamide( Boolean value ) {
      characteristics.containsLoperamide().set( value );
   }//End Method
   
}//End Class
