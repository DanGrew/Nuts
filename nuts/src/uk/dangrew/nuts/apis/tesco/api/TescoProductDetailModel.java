package uk.dangrew.nuts.apis.tesco.api;

import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
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
      
      productDetail.gdas().clear();
      productDetail.nutrition().per100Header().set( null );
      productDetail.nutrition().perServingHeader().set( null );
      productDetail.nutrition().energyInKj().valuePer100().set( null );
      productDetail.nutrition().energyInKj().valuePerServing().set( null );
      productDetail.nutrition().energyInKcal().valuePer100().set( null );
      productDetail.nutrition().energyInKcal().valuePerServing().set( null );
      productDetail.nutrition().fat().valuePer100().set( null );
      productDetail.nutrition().fat().valuePerServing().set( null );
      productDetail.nutrition().saturates().valuePer100().set( null );
      productDetail.nutrition().saturates().valuePerServing().set( null );
      productDetail.nutrition().carbohydrates().valuePer100().set( null );
      productDetail.nutrition().carbohydrates().valuePerServing().set( null );
      productDetail.nutrition().sugars().valuePer100().set( null );
      productDetail.nutrition().sugars().valuePerServing().set( null );
      productDetail.nutrition().fibre().valuePer100().set( null );
      productDetail.nutrition().fibre().valuePerServing().set( null );
      productDetail.nutrition().protein().valuePer100().set( null );
      productDetail.nutrition().protein().valuePerServing().set( null );
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
   
   public void finishProduct( String key ) {
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
      convenienceSet( ProductDetail::tpnc, description.productDetail() );
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
      
      convenienceSet( p -> p.nutrition().energyInKj().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().energyInKj().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().energyInKcal().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().energyInKcal().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().fat().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().fat().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().saturates().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().saturates().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().carbohydrates().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().carbohydrates().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().sugars().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().sugars().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().fibre().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().fibre().valuePerServing(), description.productDetail() );
      convenienceSet( p -> p.nutrition().protein().valuePer100(), description.productDetail() );
      convenienceSet( p -> p.nutrition().protein().valuePerServing(), description.productDetail() );
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
   
   private < TypeT > void convenienceSet( Function< ProductDetail, ObjectProperty< TypeT > > propertyRetriever, ProductDetail detailToUpdate ) {
      propertyRetriever.apply( detailToUpdate ).set( propertyRetriever.apply( productDetail ).get() );
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
      currentGdaReference = new GuidelineDailyAmountReference();
      gdaHandler.setCurrentGdas( currentGdaReference.gda() );
   }//End Method
   
   public void finishGdas( String key ) {
      productDetail.gdas().put( currentGdaReference.description(), currentGdaReference );
   }//End Method
   
   public void startedGdaRefObject( String key ) {
      //do nothing
   }//End Method
   
   public void finishedGdaRefObject( String key ) {
    //do nothing
   }//End Method
   
   public void startedGdaRefArray( String key ) {
    //do nothing
   }//End Method
   
   public void finishedGdaRefArray( String key ) {
    //do nothing
   }//End Method

   public void setGdaDescription( String key, String value ) {
      currentGdaReference.setDescription( value );
   }//End Method
   
   public void addHeader( String key, String value ) {
      currentGdaReference.headers().add( value );
   }//End Method
   
   public void addFooter( String key, String value ) {
      currentGdaReference.footers().add( value );
   }//End Method
   
   public void addStorage( String key, String value ) {
      productDetail.storageInstructions().add( value );
   }//End Method
   
   public void setMarketingText( String key, String value ) {
      productDetail.marketingTextProperty().set( value );
   }//End Method
   
   public void startedPackageDimensionsObject( String key ) {
      //do nothing
   }//End Method
   
   public void finishedPackageDimensionsObject( String key ) {
      //do nothing      
   }//End Method
   
   public void startedPackageDimensionsArray( String key ) {
      //do nothing
   }//End Method
   
   public void finishedPackageDimensionsArray( String key ) {
      //do nothing
   }//End Method
   
   public void setPackageDimensionsNumber( String key, Double value ) {
      productDetail.packageDimensions().number().set( value );
   }//End Method
   
   public void setPackageDimensionsHeight( String key, Double value ) {
      productDetail.packageDimensions().height().set( value );
   }//End Method
   
   public void setPackageDimensionsWidth( String key, Double value ) {
      productDetail.packageDimensions().width().set( value );
   }//End Method
   
   public void setPackageDimensionsDepth( String key, Double value ) {
      productDetail.packageDimensions().depth().set( value );
   }//End Method
   
   public void setPackageDimensionsUom( String key, String value ) {
      productDetail.packageDimensions().dimensionUom().set( value );
   }//End Method
   
   public void setPackageDimensionsWeight( String key, Double value ) {
      productDetail.packageDimensions().weight().set( value );
   }//End Method
   
   public void setPackageDimensionsWeightUom( String key, String value ) {
      productDetail.packageDimensions().weightUom().set( value );
   }//End Method
   
   public void setPackageDimensionsVolume( String key, Double value ) {
      productDetail.packageDimensions().volume().set( value );
   }//End Method
   
   public void setPackageDimensionsVolumeUom( String key, String value ) {
      productDetail.packageDimensions().volumeUom().set( value );
   }//End Method
   
   public void setName( String key, String value ) {
      gdaHandler.setName( key, value );
      nutritionHandler.setName( key, value );
   }//End Method
   
   public GdaValuesParsingHandler gdaHandler() {
      return gdaHandler;
   }//End Method
   
   public CalculatedNutritionParsingHandler nutritionHandler() {
      return nutritionHandler;
   }//End Method
   
}//End Class
