package uk.dangrew.nuts.apis.tesco.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoParseRecorder {

   private final TescoFoodDescriptionStore store;
   private final List< String > tpnbsParsed;
   
   public TescoParseRecorder( TescoFoodDescriptionStore descriptionStore, TescoGrocerySearchParser groceryParser ) {
      this.tpnbsParsed = new ArrayList<>();
      this.store = descriptionStore;
      
      groceryParser.intercept( TescoGrocerySearchParser.TPNB, new StringParseHandle( this::handleTpnb ) );
   }//End Constructor
   
   public List< TescoFoodDescription > getAndClearRecordedDescriptions() {
      List< TescoFoodDescription > result = tpnbsParsed.stream().map( tpnb -> store.objectList()
               .stream()
               .filter( f -> f.tpnb().get().equalsIgnoreCase( tpnb ) )
               .findFirst().orElse( null ) 
      ).filter( Objects::nonNull ).collect( Collectors.toList() );
      
      tpnbsParsed.clear();
      return result;
   }//End Method
   
   private void handleTpnb( String key, String value ) {
      tpnbsParsed.add( value );
   }//End Method
   
}//End Class
