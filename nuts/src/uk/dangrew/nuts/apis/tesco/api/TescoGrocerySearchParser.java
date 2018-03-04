package uk.dangrew.nuts.apis.tesco.api;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayParseHandler;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayWithObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.BooleanParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.DoubleParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;

public class TescoGrocerySearchParser extends JsonParser {

   private static final String ARRAY_RESULTS = "results";
   private static final String ARRAY_DESCRIPTION = "description";
   
   private static final String IMAGE = "image";
   private static final String SUPER_DEPARTMENT = "superDepartment";
   static final String TPNB = "tpnb";
   private static final String UNIT_OF_SALE = "UnitOfSale";
   private static final String UNIT_QUANTITY = "UnitQuantity";
   private static final String PROMOTION_DESCRIPTION = "PromotionDescription";
   private static final String CONTENTS_MEASURE_TYPE = "ContentsMeasureType";
   private static final String NAME = "name";
   private static final String AVERAGE_SELLING_UNIT_WEIGHT = "AverageSellingUnitWeight";
   private static final String ID = "id";
   private static final String CONTENTS_QUANTITY = "ContentsQuantity";
   private static final String DEPARTMENT = "department";
   private static final String PRICE = "price";
   private static final String UNIT_PRICE = "unitprice";
   private static final String IS_SPECIAL_OFFER = "IsSpecialOffer";
   
   public TescoGrocerySearchParser( TescoFoodDescriptionStore descriptionStore ) {
      this( descriptionStore, new TescoGrocerySearchModel( descriptionStore ) );
   }//End Constructor
   
   TescoGrocerySearchParser( TescoFoodDescriptionStore store, TescoGrocerySearchModel model ) {
      when( ARRAY_RESULTS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               model::startResult, model::finishResult, null, null 
      ) ) );
      
      when( ARRAY_DESCRIPTION, new StringParseHandle( new JsonArrayParseHandler<>(  
               model::addDescription, null, null 
      ) ) );
      
      when( IMAGE                      , new StringParseHandle( model::setImageLink ) );
      when( SUPER_DEPARTMENT           , new StringParseHandle( model::setSuperDepartment ) );
      when( TPNB                       , new StringParseHandle( model::setTpnb ) );
      when( UNIT_OF_SALE               , new DoubleParseHandle( model::setUnitOfSale ) );
      when( UNIT_QUANTITY              , new StringParseHandle( model::setUnitQuantity ) );
      when( PROMOTION_DESCRIPTION      , new StringParseHandle( model::setPromotionDescription ) );
      when( CONTENTS_MEASURE_TYPE      , new StringParseHandle( model::setContentsMeasureType ) );
      when( NAME                       , new StringParseHandle( model::setName ) );
      when( AVERAGE_SELLING_UNIT_WEIGHT, new DoubleParseHandle( model::setAverageSellingUnitWeight ) );
      when( ID                         , new StringParseHandle( model::setId ) );
      when( CONTENTS_QUANTITY          , new DoubleParseHandle( model::setContentsQuantity ) );
      when( DEPARTMENT                 , new StringParseHandle( model::setDepartment ) );
      when( PRICE                      , new DoubleParseHandle( model::setPrice ) );
      when( UNIT_PRICE                 , new DoubleParseHandle( model::setUnitPrice ) );
      when( IS_SPECIAL_OFFER           , new BooleanParseHandle( model::setIsSpecialOffer ) );
   }//End Constructor
   
}//End Class
