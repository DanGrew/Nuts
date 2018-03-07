package uk.dangrew.nuts.apis.tesco.api;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayParseHandler;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayWithObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.key.JsonKeyParseHandler;
import uk.dangrew.jupa.json.parse.handle.key.JsonObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.BooleanParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.DoubleParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;

public class TescoProductDetailParser extends JsonParser {

   private static final String REUSED_VALUES = "values";
   private static final String REUSED_NAME = "name";
   
   private static final String ARRAY_PRODUCTS = "products";
   
   //object within products
   private static final String GTIN = "gtin";
   private static final String TPNB = "tpnb";
   private static final String TPNC = "tpnc";
   private static final String DESCRIPTION = "description";
   private static final String BRAND = "brand";
   
   private static final String QUANTITY_CONTENTS = "qtyContents";
   //properties within contents
   private static final String QUANTITY = "quantity";
   private static final String TOTAL_QUANTITY = "totalQuantity";
   private static final String QUANTITY_UOM = "quantityUom";
   private static final String NET_CONTENTS = "netContents";
   private static final String AVERAGE_MEASURE = "avgMeasure";
        
   private static final String PRODUCT_CHARACTERISTICS = "productCharacteristics";
   //properties within characteristics
   private static final String IS_FOOD = "isFood";
   private static final String IS_DRINK = "isDrink";
   private static final String HEALTH_SCORE = "healthScore";
   private static final String IS_HAZARDOUS = "isHazardous";
   private static final String STORAGE_TYPE = "storageType";
   private static final String IS_NON_LIQUID_ANALGESIC = "isNonLiquidAnalgesic";
   private static final String CONTAINS_LOPERAMIDE = "containsLoperamide";
        
   //this provides the tag with the colours of recommended per day
   private static final String GUIDELINE_DAILY_AMOUNTS = "gda";
   //properties within gda
   private static final String ARRAY_GDA_REFS = "gdaRefs";
   //array with one object with properties
   private static final String GDA_DESCRIPTION = "gdaDescription";
   //simple value array
   private static final String ARRAY_HEADERS = "headers";
   //simple value array
   private static final String ARRAY_FOOTERS = "footers";
   
   private static final String ARRAY_NUTRITIONAL_VALUES = REUSED_VALUES;
   //properties within an individual value
   private static final String NUTRITIONAL_NAME = REUSED_NAME;
   //watch out for name clash
   private static final String ARRAY_VALUE_NUMBERS = REUSED_VALUES;
   private static final String PERCENT = "percent";
   //optional
   private static final String RATING = "rating";
   
   private static final String CALCULATED_NUTRITION = "calcNutrition";
   //properties within calculated nutrition
   private static final String PER_100_HEADER = "per100Header";
   private static final String PER_SERVING_HEADER = "perServingHeader";
   
   //nutrients provided in array
   //each object has each columns value
   //100 and serving separated on webpage by kj split
   //array of objects
   private static final String ARRAY_CALCULATED_NUTRIENTS = "calcNutrients";
   //name clash
   private static final String NUTRIENT_NAME = REUSED_NAME;
   //optional
   private static final String VALUE_PER_100 = "valuePer100";
   //optional
   private static final String VALUE_PER_SERVING = "valuePerServing";
   
   //simple array of values
   private static final String STORAGE = "storage";
   private static final String MARKETING_TEXT = "marketingText";
   
   //array with single object
   private static final String PACKAGE_DIMENSIONS = "pkgDimensions";
   private static final String NUMBER = "no";
   private static final String HEIGHT = "height";
   private static final String WIDTH = "width";
   private static final String DEPTH = "depth";
   private static final String DIMENSION_UOM = "dimensionUom";
   private static final String WEIGHT = "weight";
   private static final String WEIGHT_UOM = "weightUom";
   private static final String VOLUME = "volume";
   private static final String VOLUME_UOM = "volumeUom";

   //array of something, unknown
   private static final String PRODUCT_ATTRIBUTES = "productAttributes";
   
   public TescoProductDetailParser( TescoFoodDescriptionStore descriptionStore ) {
      this( new TescoProductDetailModel( descriptionStore ) );
   }//End Constructor
   
   TescoProductDetailParser( TescoProductDetailModel model ) {
      GdaValuesParsingHandler gdaValueParser = model.gdaHandler();
      CalculatedNutritionParsingHandler nutritionParser = model.nutritionHandler();
      
      when( REUSED_VALUES, new StringParseHandle( new JsonKeyParseHandler<>( 
               gdaValueParser::addValueForArrayValue, 
               gdaValueParser::handleValuesStartObject, 
               gdaValueParser::handleValuesEndObject,
               gdaValueParser::handleValuesStartArray, 
               gdaValueParser::handleValuesEndArray
      ) ) );
      
      when( REUSED_NAME         , new StringParseHandle( model::setName ) );
      
      when( ARRAY_PRODUCTS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               model::startProduct, model::finishProduct, null, null 
      ) ) );
      
      when( GTIN        , new StringParseHandle( model::setGtin ) );
      when( TPNB        , new StringParseHandle( model::setTpnb ) );
      when( TPNC        , new StringParseHandle( model::setTpnc ) );
      when( DESCRIPTION , new StringParseHandle( model::setDescription ) );
      when( BRAND       , new StringParseHandle( model::setBrand ) );
      
      when( QUANTITY_CONTENTS, new StringParseHandle( new JsonObjectParseHandler<>(  
               model::startQuantityContents, model::finishQuantityContents
      ) ) );

      when( QUANTITY        , new DoubleParseHandle( model::setQuantity ) );
      when( TOTAL_QUANTITY  , new DoubleParseHandle( model::setTotalQuantity ) );
      when( QUANTITY_UOM    , new StringParseHandle( model::setQuantityUom ) );
      when( NET_CONTENTS    , new StringParseHandle( model::setNetContents ) );
      when( AVERAGE_MEASURE , new StringParseHandle( model::setAverageMeasure ) );
           
      when( PRODUCT_CHARACTERISTICS, new StringParseHandle( new JsonObjectParseHandler<>(  
               model::startProductCharacteristics, model::finishProductCharacteristics
      ) ) );
      
      when( IS_FOOD                  , new BooleanParseHandle( model::setIsFood ) );
      when( IS_DRINK                 , new BooleanParseHandle( model::setIsDrink ) );
      when( HEALTH_SCORE             , new DoubleParseHandle( model::setHealthScore ) );
      when( IS_HAZARDOUS             , new BooleanParseHandle( model::setIsHazardous ) );
      when( STORAGE_TYPE             , new StringParseHandle( model::setStorageType ) );
      when( IS_NON_LIQUID_ANALGESIC  , new BooleanParseHandle( model::setIsNonLiquidAnalgesic ) );
      when( CONTAINS_LOPERAMIDE      , new BooleanParseHandle( model::setContainsLoperamide ) );
           
      when( GUIDELINE_DAILY_AMOUNTS , new StringParseHandle( new JsonObjectParseHandler<>( 
               model::startGdas, model::finishGdas
      ) ) );
      when( ARRAY_GDA_REFS , new StringParseHandle( new JsonArrayWithObjectParseHandler<>(  
               model::startedGdaRefObject, model::finishedGdaRefObject,
               model::startedGdaRefArray, model::finishedGdaRefArray
      ) ) );
      
      when( GDA_DESCRIPTION, new StringParseHandle( model::setGdaDescription ) );
      when( ARRAY_HEADERS, new StringParseHandle( new JsonArrayParseHandler<>(  
               model::addHeader, null, null
      ) ) );
      when( ARRAY_FOOTERS, new StringParseHandle( new JsonArrayParseHandler<>(  
               model::addFooter, null, null
      ) ) );

      when( PERCENT             , new StringParseHandle( gdaValueParser::setPercent ) );
      when( RATING              , new StringParseHandle( gdaValueParser::setRating ) );
      
      when( CALCULATED_NUTRITION , new StringParseHandle( new JsonObjectParseHandler<>( 
               nutritionParser::startedCalculatedNutrition, nutritionParser::finishedCalculatedNutrition
      ) ) );
      when( PER_100_HEADER       , new StringParseHandle( nutritionParser::setPer100Header ) );
      when( PER_SERVING_HEADER   , new StringParseHandle( nutritionParser::setPerServingHeader ) );
      
      when( ARRAY_CALCULATED_NUTRIENTS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>(
               nutritionParser::startedCalculatedNutrientsObject, 
               nutritionParser::finishedCalculatedNutrientsObject, 
               nutritionParser::startedCalculatedNutrientsArray, 
               nutritionParser::finishedCalculatedNutrientsArray 
      ) ) );
      when( VALUE_PER_100     , new StringParseHandle( nutritionParser::setValuePer100 ) );
      when( VALUE_PER_SERVING , new StringParseHandle( nutritionParser::setValuePerServing ) );
      
      when( STORAGE, new StringParseHandle( new JsonArrayParseHandler<>( 
               model::addStorage, null, null 
      ) ) ); 
      when( MARKETING_TEXT, new StringParseHandle( model::setMarketingText ) );
      
      when( PACKAGE_DIMENSIONS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               model::startedPackageDimensionsObject, 
               model::finishedPackageDimensionsObject, 
               model::startedPackageDimensionsArray, 
               model::finishedPackageDimensionsArray 
      ) ) );
      
      when( NUMBER        , new DoubleParseHandle( model::setPackageDimensionsNumber ) );
      when( HEIGHT        , new DoubleParseHandle( model::setPackageDimensionsHeight ) );
      when( WIDTH         , new DoubleParseHandle( model::setPackageDimensionsWidth ) );
      when( DEPTH         , new DoubleParseHandle( model::setPackageDimensionsDepth ) );
      when( DIMENSION_UOM , new StringParseHandle( model::setPackageDimensionsUom ) );
      when( WEIGHT        , new DoubleParseHandle( model::setPackageDimensionsWeight ) );
      when( WEIGHT_UOM    , new StringParseHandle( model::setPackageDimensionsWeightUom ) );
      when( VOLUME        , new DoubleParseHandle( model::setPackageDimensionsVolume ) );
      when( VOLUME_UOM    , new StringParseHandle( model::setPackageDimensionsVolumeUom ) );
   }//End Constructor
   
}//End Class
