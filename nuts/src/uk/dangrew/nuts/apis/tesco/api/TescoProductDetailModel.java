package uk.dangrew.nuts.apis.tesco.api;

import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.item.ProductDetail;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoProductDetailModel {
   
   private final TescoFoodDescriptionStore store;
   private final ProductDetail productDetail;
   
   public TescoProductDetailModel( TescoFoodDescriptionStore store ) {
      this.store = store;
      this.productDetail = new ProductDetail();
   }//End Constructor
   
   public void startProduct( String key ) {
      productDetail.gtin().set( null );
      productDetail.tpnb().set( null );
      productDetail.tpnc().set( null );
      productDetail.description().set( null );
      productDetail.brand().set( null );
      
      productDetail.quantityContents().quantity().set( null );
      productDetail.quantityContents().totalQuantity().set( null );
      productDetail.quantityContents().quantityUom().set( null );
      productDetail.quantityContents().netContents().set( null );
      productDetail.quantityContents().averageMeasure().set( null );
      
      productDetail.characteristics().isFood().set( null );
      productDetail.characteristics().isDrink().set( null );
      productDetail.characteristics().healthScore().set( null );
      productDetail.characteristics().isHazardous().set( null );
      productDetail.characteristics().storageType().set( null );
      productDetail.characteristics().isNonLiquidAnalgesic().set( null );
      productDetail.characteristics().containsLoperamide().set( null );
   }//End Method
   
   public void finishProduct( String key ) {
      TescoFoodDescription description = store.get( productDetail.tpnb().get() );
      if ( description == null ) {
         description = store.createConcept(  
                  productDetail.tpnb().get(), 
                  productDetail.description().get() 
         );
      }
      
      description.productDetail().gtin().set( productDetail.gtin().get() );
      description.productDetail().tpnb().set( productDetail.tpnb().get() );
      description.productDetail().tpnc().set( productDetail.tpnc().get() );
      description.productDetail().description().set( productDetail.description().get() );
      description.productDetail().brand().set( productDetail.brand().get() );
      
      description.productDetail().quantityContents().quantity().set( productDetail.quantityContents().quantity().get() );
      description.productDetail().quantityContents().totalQuantity().set( productDetail.quantityContents().totalQuantity().get() );
      description.productDetail().quantityContents().quantityUom().set( productDetail.quantityContents().quantityUom().get() );
      description.productDetail().quantityContents().netContents().set( productDetail.quantityContents().netContents().get() );
      description.productDetail().quantityContents().averageMeasure().set( productDetail.quantityContents().averageMeasure().get() );
      
      description.productDetail().characteristics().isFood().set( productDetail.characteristics().isFood().get() );
      description.productDetail().characteristics().isDrink().set( productDetail.characteristics().isDrink().get() );
      description.productDetail().characteristics().healthScore().set( productDetail.characteristics().healthScore().get() );
      description.productDetail().characteristics().isHazardous().set( productDetail.characteristics().isHazardous().get() );
      description.productDetail().characteristics().storageType().set( productDetail.characteristics().storageType().get() );
      description.productDetail().characteristics().isNonLiquidAnalgesic().set( productDetail.characteristics().isNonLiquidAnalgesic().get() );
      description.productDetail().characteristics().containsLoperamide().set( productDetail.characteristics().containsLoperamide().get() );
   }//End Method
   
   public void setGtin( String key, String value ) {
      productDetail.gtin().set( value );
   }//End Method
   
   public void setTpnb( String key, String value ) {
      productDetail.tpnb().set( value );
   }//End Method
   
   public void setTpnc( String key, String value ) {
      productDetail.tpnc().set( value );
   }//End Method
   
   public void setDescription( String key, String value ) {
      productDetail.description().set( value );
   }//End Method
   
   public void setBrand( String key, String value ) {
      productDetail.brand().set( value );
   }//End Method
   
   public void startQuantityContents( String key ) {
      //do nothing
   }//End Method
   
   public void finishQuantityContents( String key ) {
      //do nothing
   }//End Method
   
   public void setQuantity( String key, Double value ) {
      productDetail.quantityContents().quantity().set( value );
   }//End Method
   
   public void setTotalQuantity( String key, Double value ) {
      productDetail.quantityContents().totalQuantity().set( value );
   }//End Method
   
   public void setQuantityUom( String key, String value ) {
      productDetail.quantityContents().quantityUom().set( value );
   }//End Method
   
   public void setNetContents( String key, String value ) {
      productDetail.quantityContents().netContents().set( value );
   }//End Method
   
   public void setAverageMeasure( String key, String value ) {
      productDetail.quantityContents().averageMeasure().set( value );
   }//End Method
   
   public void startProductCharacteristics( String key ) {
      //do nothing
   }//End Method
   
   public void finishProductCharacteristics( String key ) {
      //do nothing
   }//End Method
   
   public void setIsFood( String key, Boolean value ) {
      productDetail.characteristics().isFood().set( value );
   }//End Method
   
   public void setIsDrink( String key, Boolean value ) {
      productDetail.characteristics().isDrink().set( value );
   }//End Method
   
   public void setHealthScore( String key, Double value ) {
      productDetail.characteristics().healthScore().set( value );
   }//End Method
   
   public void setIsHazardous( String key, Boolean value ) {
      productDetail.characteristics().isHazardous().set( value );
   }//End Method
   
   public void setStorageType( String key, String value ) {
      productDetail.characteristics().storageType().set( value );
   }//End Method
   
   public void setIsNonLiquidAnalgesic( String key, Boolean value ) {
      productDetail.characteristics().isNonLiquidAnalgesic().set( value );
   }//End Method
   
   public void setContainsLoperamide( String key, Boolean value ) {
      productDetail.characteristics().containsLoperamide().set( value );
   }//End Method
   
   public void startGdas( String key ) {
      
   }//End Method
   
   public void finishGdas( String key ) {
      
   }//End Method
   
   public void startedGdaRefObject( String key ) {
      
   }//End Method
   
   public void finishedGdaRefObject( String key ) {
      
   }//End Method
   
   public void startedGdaRefArray( String key ) {
      
   }//End Method
   
   public void finishedGdaRefArray( String key ) {
      
   }//End Method

   public void setGdaDescription( String key, String value ) {
      
   }//End Method
   
   public void addHeader( String key, String value ) {
      
   }//End Method
   
   public void addFooter( String key, String value ) {
      
   }//End Method
   
   public void setPercent( String key, String value ) {
      
   }//End Method
   
   public void setRating( String key, String value ) {
      
   }//End Method
   
   public void startedCalculatedNutrition( String key ) {
      
   }//End Method
   
   public void finishedCalculatedNutrition( String key ) {
      
   }//End Method
   
   public void setPer100Header( String key, String value ) {
      
   }//End Method
   
   public void setPerServingHeader( String key, String value ) {
      
   }//End Method
   
   public void startedCalculatedNutrientsObject( String key ) {
      
   }//End Method
   
   public void finishedCalculatedNutrientsObject( String key ) {
      
   }//End Method
   
   public void startedCalculatedNutrientsArray( String key ) {
      
   }//End Method
   
   public void finishedCalculatedNutrientsArray( String key ) {
      
   }//End Method
   
   public void setValuePer100( String key, String value ) {
      
   }//End Method
   
   public void setValuePerServing( String key, String value ) {
      
   }//End Method
   
   public void addStorage( String key, String value ) {
      
   }//End Method
   
   public void setMarketingText( String key, String value ) {
      
   }//End Method
   
   public void startedPackageDimensionsObject( String key ) {
      
   }//End Method
   
   public void finishedPackageDimensionsObject( String key ) {
      
   }//End Method
   
   public void startedPackageDimensionsArray( String key ) {
      
   }//End Method
   
   public void finishedPackageDimensionsArray( String key ) {
      
   }//End Method
   
   public void setPackageDimensionsNumber( String key, Double value ) {
      
   }//End Method
   
   public void setPackageDimensionsHeight( String key, Double value ) {
      
   }//End Method
   
   public void setPackageDimensionsWidth( String key, Double value ) {
      
   }//End Method
   
   public void setPackageDimensionsDepth( String key, Double value ) {
      
   }//End Method
   
   public void setPackageDimensionsUom( String key, String value ) {
      
   }//End Method
   
   public void setPackageDimensionsWeight( String key, Double value ) {
      
   }//End Method
   
   public void setPackageDimensionsWeightUom( String key, String value ) {
      
   }//End Method
   
   public void setPackageDimensionsVolume( String key, Double value ) {
      
   }//End Method
   
   public void setPackageDimensionsVolumeUom( String key, String value ) {
      
   }//End Method
   
   public void addValueForArrayValue( String key, String value ) {
      
   }//End Method
   
   public void handleValuesStartObject( String key ) {
      
   }//End Method
   
   public void handleValuesEndObject( String key ) {
      
   }//End Method
   
   public void handleValuesStartArray( String key ) {
      
   }//End Method
   
   public void handleValuesEndArray( String key ) {
      
   }//End Method
   
   public void setName( String key, String value ) {
      
   }//End Method
   
}//End Class
