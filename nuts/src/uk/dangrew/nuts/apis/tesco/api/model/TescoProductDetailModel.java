package uk.dangrew.nuts.apis.tesco.api.model;

import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.apis.tesco.api.parsing.CalculatedNutritionParsingHandler;
import uk.dangrew.nuts.apis.tesco.api.parsing.GdaValuesParsingHandler;
import uk.dangrew.nuts.apis.tesco.api.parsing.ProductCharacteristicsParsingHandler;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.graphics.selection.TescoStringParser;
import uk.dangrew.nuts.apis.tesco.model.api.GuidelineDailyAmountReference;
import uk.dangrew.nuts.apis.tesco.model.api.ProductDetail;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;

public class TescoProductDetailModel {
   
   private final TescoFoodDescriptionStore store;
   private final ProductDetail productDetail;
   
   private final GdaValuesParsingHandler gdaHandler;
   private final ProductCharacteristicsParsingHandler characteristicsHandler;
   private final CalculatedNutritionParsingHandler nutritionHandler;
   
   private final TescoStringParser stringParser;
   
   private GuidelineDailyAmountReference currentGdaReference;
   
   public TescoProductDetailModel( TescoFoodDescriptionStore store ) {
      this.store = store;
      this.productDetail = new ProductDetail();
      this.gdaHandler = new GdaValuesParsingHandler();
      this.characteristicsHandler = new ProductCharacteristicsParsingHandler();
      this.nutritionHandler = new CalculatedNutritionParsingHandler();
      this.stringParser = new TescoStringParser();
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
      
      characteristicsHandler().resetCharacteristics( productDetail.characteristics() );
      productDetail.gdas().clear();
      nutritionHandler().resetNutrition( productDetail.nutrition() );
      
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
      String tpnb = stringParser.removeLeadingZerosFromInteger( productDetail.tpnb().get() );
      TescoFoodDescription descriptionInDatabase = store.get( tpnb );
      if ( descriptionInDatabase == null ) {
         descriptionInDatabase = store.createConcept(  
                  productDetail.tpnb().get(), 
                  productDetail.description().get() 
         );
      }
      
      convenienceSet( ProductDetail::gtin, descriptionInDatabase.productDetail() );
      convenienceSet( ProductDetail::tpnb, descriptionInDatabase.productDetail() );
      descriptionInDatabase.productDetail().tpncs().addAll( productDetail.tpncs() );
      convenienceSet( ProductDetail::description, descriptionInDatabase.productDetail() );
      convenienceSet( ProductDetail::brand, descriptionInDatabase.productDetail() );

      convenienceSet( p -> p.quantityContents().quantity(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.quantityContents().totalQuantity(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.quantityContents().quantityUom(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.quantityContents().netContents(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.quantityContents().averageMeasure(), descriptionInDatabase.productDetail() );
      
      characteristicsHandler().applyCharacteristicsTo( descriptionInDatabase.productDetail().characteristics() );
      
      for ( int i = 0; i < productDetail.gdas().size(); i++ ) {
         GuidelineDailyAmountReference parsedGda = productDetail.gdas().get( i );
         GuidelineDailyAmountReference gdaToUpdate = new GuidelineDailyAmountReference();
         descriptionInDatabase.productDetail().gdas().add( gdaToUpdate );
         
         gdaToUpdate.headers().addAll( parsedGda.headers() );
         gdaToUpdate.footers().addAll( parsedGda.footers() );
         
         final int gdaIndex = i;
         convenienceSet( p -> p.gdas().get( gdaIndex ).description(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().energyGda().energyInKcal(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().energyGda().amount(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().energyGda().energyInKj(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().energyGda().percent(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().energyGda().rating(), descriptionInDatabase.productDetail() );
         
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().fatGda().amount(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().fatGda().percent(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().fatGda().rating(), descriptionInDatabase.productDetail() );
         
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().saturatesGda().amount(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().saturatesGda().percent(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().saturatesGda().rating(), descriptionInDatabase.productDetail() );
         
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().sugarsGda().amount(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().sugarsGda().percent(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().sugarsGda().rating(), descriptionInDatabase.productDetail() );
         
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().saltGda().amount(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().saltGda().percent(), descriptionInDatabase.productDetail() );
         convenienceSet( p -> p.gdas().get( gdaIndex ).gda().saltGda().rating(), descriptionInDatabase.productDetail() );
      }
      
      nutritionHandler().applyNutritionTo( descriptionInDatabase.productDetail().nutrition() );
      
      descriptionInDatabase.productDetail().storageInstructions().addAll( productDetail.storageInstructions() );
      convenienceSet( ProductDetail::marketingTextProperty, descriptionInDatabase.productDetail() );
      
      convenienceSet( p -> p.packageDimensions().number(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.packageDimensions().height(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.packageDimensions().width(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.packageDimensions().depth(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.packageDimensions().dimensionUom(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.packageDimensions().weight(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.packageDimensions().weightUom(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.packageDimensions().volume(), descriptionInDatabase.productDetail() );
      convenienceSet( p -> p.packageDimensions().volumeUom(), descriptionInDatabase.productDetail() );
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
   
   public void startGdas() {
      currentGdaReference = new GuidelineDailyAmountReference();
      gdaHandler().setCurrentGdas( currentGdaReference.gda() );
   }//End Method
   
   public void finishGdas() {
      productDetail.gdas().add( currentGdaReference );
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
      currentGdaReference.description().set( value );
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
      gdaHandler().setName( value );
      nutritionHandler().setName( value );
   }//End Method
   
   public GdaValuesParsingHandler gdaHandler() {
      return gdaHandler;
   }//End Method
   
   public ProductCharacteristicsParsingHandler characteristicsHandler() {
      return characteristicsHandler;
   }//End Method
   
   public CalculatedNutritionParsingHandler nutritionHandler() {
      return nutritionHandler;
   }//End Method
   
}//End Class
