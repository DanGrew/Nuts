package uk.dangrew.nuts.apis.tesco.api;

import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.item.GuidelineDailyAmountReference;
import uk.dangrew.nuts.apis.tesco.item.ProductDetail;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoProductDetailModel {
   
   private final TescoFoodDescriptionStore store;
   private final ProductDetail productDetail;
   private final GdaValuesParsingHandler gdaHandler;
   private final CalculatedNutritionParsingHandler nutritionHandler;
   
   private GuidelineDailyAmountReference currentGdaReference;
   
   public TescoProductDetailModel( TescoFoodDescriptionStore store ) {
      this.store = store;
      this.productDetail = new ProductDetail();
      this.gdaHandler = new GdaValuesParsingHandler();
      this.nutritionHandler = new CalculatedNutritionParsingHandler();
   }//End Constructor
   
   public void startProductArray() {
      productDetail.gtin().set( null );
      productDetail.tpnb().set( null );
      productDetail.tpncs().clear();
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
      
      productDetail.gdas().clear();
      productDetail.nutrition().per100Header().set( null );
      productDetail.nutrition().perServingHeader().set( null );
      productDetail.nutrition().energyInKj().name().set( null );
      productDetail.nutrition().energyInKj().valuePer100().set( null );
      productDetail.nutrition().energyInKj().valuePerServing().set( null );
      productDetail.nutrition().energyInKcal().name().set( null );
      productDetail.nutrition().energyInKcal().valuePer100().set( null );
      productDetail.nutrition().energyInKcal().valuePerServing().set( null );
      productDetail.nutrition().fat().name().set( null );
      productDetail.nutrition().fat().valuePer100().set( null );
      productDetail.nutrition().fat().valuePerServing().set( null );
      productDetail.nutrition().saturates().name().set( null );
      productDetail.nutrition().saturates().valuePer100().set( null );
      productDetail.nutrition().saturates().valuePerServing().set( null );
      productDetail.nutrition().carbohydrates().name().set( null );
      productDetail.nutrition().carbohydrates().valuePer100().set( null );
      productDetail.nutrition().carbohydrates().valuePerServing().set( null );
      productDetail.nutrition().sugars().name().set( null );
      productDetail.nutrition().sugars().valuePer100().set( null );
      productDetail.nutrition().sugars().valuePerServing().set( null );
      productDetail.nutrition().fibre().name().set( null );
      productDetail.nutrition().fibre().valuePer100().set( null );
      productDetail.nutrition().fibre().valuePerServing().set( null );
      productDetail.nutrition().protein().name().set( null );
      productDetail.nutrition().protein().valuePer100().set( null );
      productDetail.nutrition().protein().valuePerServing().set( null );
      productDetail.nutrition().salt().name().set( null );
      productDetail.nutrition().salt().valuePer100().set( null );
      productDetail.nutrition().salt().valuePerServing().set( null );
      
      nutritionHandler.setCurrentNutrition( productDetail.nutrition() );
      
      productDetail.storageInstructions().clear();
      productDetail.marketingTextProperty().set( null );
      
      productDetail.packageDimensions().number().set( null );
      productDetail.packageDimensions().height().set( null );
      productDetail.packageDimensions().width().set( null );
      productDetail.packageDimensions().depth().set( null );
      productDetail.packageDimensions().dimensionUom().set( null );
      productDetail.packageDimensions().weight().set( null );
      productDetail.packageDimensions().weightUom().set( null );
      productDetail.packageDimensions().volume().set( null );
      productDetail.packageDimensions().volumeUom().set( null );
   }//End Method
   
   public void finishProductArray() {
      String tpnb = productDetail.tpnb().get();
      tpnb = Integer.valueOf( tpnb ).toString();
      
      TescoFoodDescription description = store.get( tpnb );
      if ( description == null ) {
         description = store.createConcept(  
                  productDetail.tpnb().get(), 
                  productDetail.description().get() 
         );
      }
      
      convenienceSet( ProductDetail::gtin, description.productDetail() );
      convenienceSet( ProductDetail::tpnb, description.productDetail() );
      description.productDetail().tpncs().addAll( productDetail.tpncs() );
      convenienceSet( ProductDetail::description, description.productDetail() );
      convenienceSet( ProductDetail::brand, description.productDetail() );

      convenienceSet( p -> p.quantityContents().quantity(), description.productDetail() );
      convenienceSet( p -> p.quantityContents().totalQuantity(), description.productDetail() );
      convenienceSet( p -> p.quantityContents().quantityUom(), description.productDetail() );
      convenienceSet( p -> p.quantityContents().netContents(), description.productDetail() );
      convenienceSet( p -> p.quantityContents().averageMeasure(), description.productDetail() );
      
      convenienceSet( p -> p.characteristics().isFood(), description.productDetail() );
      convenienceSet( p -> p.characteristics().isDrink(), description.productDetail() );
      convenienceSet( p -> p.characteristics().healthScore(), description.productDetail() );
      convenienceSet( p -> p.characteristics().isHazardous(), description.productDetail() );
      convenienceSet( p -> p.characteristics().storageType(), description.productDetail() );
      convenienceSet( p -> p.characteristics().isNonLiquidAnalgesic(), description.productDetail() );
      convenienceSet( p -> p.characteristics().containsLoperamide(), description.productDetail() );
      
      for ( String gda : productDetail.gdas().keySet() ) {
         GuidelineDailyAmountReference gdar = new GuidelineDailyAmountReference();
         gdar.setDescription( gda );
         description.productDetail().gdas().put( gda, gdar );
         
         gdar.headers().addAll( productDetail.gdas().get( gda ).headers() );
         gdar.footers().addAll( productDetail.gdas().get( gda ).footers() );
         
         convenienceSet( p -> p.gdas().get( gda ).gda().energyGda().energyInKcal(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().energyGda().amount(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().energyGda().energyInKj(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().energyGda().percent(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().energyGda().rating(), description.productDetail() );
         
         convenienceSet( p -> p.gdas().get( gda ).gda().fatGda().amount(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().fatGda().percent(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().fatGda().rating(), description.productDetail() );
         
         convenienceSet( p -> p.gdas().get( gda ).gda().saturatesGda().amount(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().saturatesGda().percent(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().saturatesGda().rating(), description.productDetail() );
         
         convenienceSet( p -> p.gdas().get( gda ).gda().sugarsGda().amount(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().sugarsGda().percent(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().sugarsGda().rating(), description.productDetail() );
         
         convenienceSet( p -> p.gdas().get( gda ).gda().saltGda().amount(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().saltGda().percent(), description.productDetail() );
         convenienceSet( p -> p.gdas().get( gda ).gda().saltGda().rating(), description.productDetail() );
      }
      
      convenienceSet( p -> p.nutrition().per100Header(), description.productDetail() );
      convenienceSet( p -> p.nutrition().perServingHeader(), description.productDetail() );
      
      convenienceSet( p -> p.nutrition().energyInKj().name(), description.productDetail() );
      convenienceSet( p -> p.nutrition().energyInKj().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().energyInKj().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().energyInKcal().name(), description.productDetail() );
      convenienceSet( p -> p.nutrition().energyInKcal().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().energyInKcal().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().fat().name(), description.productDetail() );
      convenienceSet( p -> p.nutrition().fat().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().fat().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().saturates().name(), description.productDetail() );
      convenienceSet( p -> p.nutrition().saturates().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().saturates().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().carbohydrates().name(), description.productDetail() );
      convenienceSet( p -> p.nutrition().carbohydrates().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().carbohydrates().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().sugars().name(), description.productDetail() );
      convenienceSet( p -> p.nutrition().sugars().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().sugars().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().fibre().name(), description.productDetail() );
      convenienceSet( p -> p.nutrition().fibre().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().fibre().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().protein().name(), description.productDetail() );
      convenienceSet( p -> p.nutrition().protein().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().protein().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().salt().name(), description.productDetail() );
      convenienceSet( p -> p.nutrition().salt().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().salt().valuePerServing(), description.productDetail() );
      
      description.productDetail().storageInstructions().addAll( productDetail.storageInstructions() );
      convenienceSet( ProductDetail::marketingTextProperty, description.productDetail() );
      
      convenienceSet( p -> p.packageDimensions().number(), description.productDetail() );
      convenienceSet( p -> p.packageDimensions().height(), description.productDetail() );
      convenienceSet( p -> p.packageDimensions().width(), description.productDetail() );
      convenienceSet( p -> p.packageDimensions().depth(), description.productDetail() );
      convenienceSet( p -> p.packageDimensions().dimensionUom(), description.productDetail() );
      convenienceSet( p -> p.packageDimensions().weight(), description.productDetail() );
      convenienceSet( p -> p.packageDimensions().weightUom(), description.productDetail() );
      convenienceSet( p -> p.packageDimensions().volume(), description.productDetail() );
      convenienceSet( p -> p.packageDimensions().volumeUom(), description.productDetail() );
   }//End Method
   
   public void startProduct() {
      //do nothing - parse altogether, overriding if necessary
   }//End Method
   
   public void finishProduct() {
    //do nothing - parse altogether, overriding if necessary
   }//End Method
   
   private < TypeT > void convenienceSet( Function< ProductDetail, ObjectProperty< TypeT > > propertyRetriever, ProductDetail detailToUpdate ) {
      propertyRetriever.apply( detailToUpdate ).set( propertyRetriever.apply( productDetail ).get() );
   }//End Method
   
   private void convienceListAdd( ObservableList< String > list, String value ) {
      if ( list.contains( value ) ) {
         return;
      }
      list.add( value );
   }
   
   public void setGtin( String value ) {
      productDetail.gtin().set( value );
   }//End Method
   
   public void setTpnb( String value ) {
      productDetail.tpnb().set( value );
   }//End Method
   
   public void setTpnc( String value ) {
      convienceListAdd( productDetail.tpncs(), value );
   }//End Method
   
   public void setDescription( String value ) {
      productDetail.description().set( value );
   }//End Method
   
   public void setBrand( String value ) {
      productDetail.brand().set( value );
   }//End Method
   
   public void startQuantityContents() {
      //do nothing
   }//End Method
   
   public void finishQuantityContents() {
      //do nothing
   }//End Method
   
   public void setQuantity( Double value ) {
      productDetail.quantityContents().quantity().set( value );
   }//End Method
   
   public void setTotalQuantity( Double value ) {
      productDetail.quantityContents().totalQuantity().set( value );
   }//End Method
   
   public void setQuantityUom( String value ) {
      productDetail.quantityContents().quantityUom().set( value );
   }//End Method
   
   public void setNetContents( String value ) {
      productDetail.quantityContents().netContents().set( value );
   }//End Method
   
   public void setAverageMeasure( String value ) {
      productDetail.quantityContents().averageMeasure().set( value );
   }//End Method
   
   public void startProductCharacteristics() {
      //do nothing
   }//End Method
   
   public void finishProductCharacteristics() {
      //do nothing
   }//End Method
   
   public void setIsFood( Boolean value ) {
      productDetail.characteristics().isFood().set( value );
   }//End Method
   
   public void setIsDrink( Boolean value ) {
      productDetail.characteristics().isDrink().set( value );
   }//End Method
   
   public void setHealthScore( Double value ) {
      productDetail.characteristics().healthScore().set( value );
   }//End Method
   
   public void setIsHazardous( Boolean value ) {
      productDetail.characteristics().isHazardous().set( value );
   }//End Method
   
   public void setStorageType( String value ) {
      productDetail.characteristics().storageType().set( value );
   }//End Method
   
   public void setIsNonLiquidAnalgesic( Boolean value ) {
      productDetail.characteristics().isNonLiquidAnalgesic().set( value );
   }//End Method
   
   public void setContainsLoperamide( Boolean value ) {
      productDetail.characteristics().containsLoperamide().set( value );
   }//End Method
   
   public void startGdas() {
      currentGdaReference = new GuidelineDailyAmountReference();
      gdaHandler.setCurrentGdas( currentGdaReference.gda() );
   }//End Method
   
   public void finishGdas() {
      productDetail.gdas().put( currentGdaReference.description(), currentGdaReference );
   }//End Method
   
   public void startedGdaRefObject() {
      //do nothing
   }//End Method
   
   public void finishedGdaRefObject() {
    //do nothing
   }//End Method
   
   public void startedGdaRefArray() {
    //do nothing
   }//End Method
   
   public void finishedGdaRefArray() {
    //do nothing
   }//End Method

   public void setGdaDescription( String value ) {
      currentGdaReference.setDescription( value );
   }//End Method
   
   public void addHeader( String value ) {
      convienceListAdd( currentGdaReference.headers(), value );
   }//End Method
   
   public void addFooter( String value ) {
      convienceListAdd( currentGdaReference.footers(), value );
   }//End Method
   
   public void addStorage( String value ) {
      convienceListAdd( productDetail.storageInstructions(), value );
   }//End Method
   
   public void setMarketingText( String value ) {
      productDetail.marketingTextProperty().set( value );
   }//End Method
   
   public void startedPackageDimensionsObject() {
      //do nothing
   }//End Method
   
   public void finishedPackageDimensionsObject() {
      //do nothing      
   }//End Method
   
   public void startedPackageDimensionsArray() {
      //do nothing
   }//End Method
   
   public void finishedPackageDimensionsArray() {
      //do nothing
   }//End Method
   
   public void setPackageDimensionsNumber( Double value ) {
      productDetail.packageDimensions().number().set( value );
   }//End Method
   
   public void setPackageDimensionsHeight( Double value ) {
      productDetail.packageDimensions().height().set( value );
   }//End Method
   
   public void setPackageDimensionsWidth( Double value ) {
      productDetail.packageDimensions().width().set( value );
   }//End Method
   
   public void setPackageDimensionsDepth( Double value ) {
      productDetail.packageDimensions().depth().set( value );
   }//End Method
   
   public void setPackageDimensionsUom( String value ) {
      productDetail.packageDimensions().dimensionUom().set( value );
   }//End Method
   
   public void setPackageDimensionsWeight( Double value ) {
      productDetail.packageDimensions().weight().set( value );
   }//End Method
   
   public void setPackageDimensionsWeightUom( String value ) {
      productDetail.packageDimensions().weightUom().set( value );
   }//End Method
   
   public void setPackageDimensionsVolume( Double value ) {
      productDetail.packageDimensions().volume().set( value );
   }//End Method
   
   public void setPackageDimensionsVolumeUom( String value ) {
      productDetail.packageDimensions().volumeUom().set( value );
   }//End Method
   
   public void setName( String value ) {
      gdaHandler.setName( value );
      nutritionHandler.setName( value );
   }//End Method
   
   public GdaValuesParsingHandler gdaHandler() {
      return gdaHandler;
   }//End Method
   
   public CalculatedNutritionParsingHandler nutritionHandler() {
      return nutritionHandler;
   }//End Method
   
}//End Class
